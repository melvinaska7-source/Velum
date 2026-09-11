package velum.client;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Local .ogg player for Velum.
 *
 * On Android this uses android.media.MediaPlayer through reflection, so the
 * desktop build does not need the Android SDK as a compile-time dependency.
 * The music folder lives next to the Minecraft run directory:
 *   .minecraft/Velum/music/
 */
public final class VelumLocalMusic {
    private static final VelumLocalMusic INSTANCE = new VelumLocalMusic();
    private static final long POLL_MS = 200L;

    private final List<Path> tracks = new CopyOnWriteArrayList<>();
    private final Path musicDirectory;
    private final Object lock = new Object();
    private volatile Object player;
    private volatile Path current;
    private volatile boolean prepared;
    private volatile boolean paused;
    private volatile boolean available;
    private volatile float volume = 1.0F;
    private volatile int index = -1;
    private volatile long lastScan;
    private Thread poller;

    private VelumLocalMusic() {
        Path runDir = net.minecraft.client.MinecraftClient.getInstance().runDirectory.toPath();
        this.musicDirectory = runDir.resolve("Velum").resolve("music");
        this.available = hasAndroidMediaPlayer();
        ensureDirectory();
        rescan();
        startPoller();
    }

    public static VelumLocalMusic getInstance() {
        return INSTANCE;
    }

    public Path getMusicDirectory() {
        return musicDirectory;
    }

    public boolean isAvailable() {
        return available;
    }

    public List<Path> getTracks() {
        if (System.currentTimeMillis() - lastScan > 2500L) {
            rescan();
        }
        return List.copyOf(tracks);
    }

    public String getCurrentTitle() {
        Path path = current;
        return path == null ? "" : displayName(path);
    }

    public Path getCurrentPath() {
        return current;
    }

    public boolean isPlaying() {
        Object p = player;
        if (p == null || !prepared) return false;
        try {
            return (Boolean) method(p, "isPlaying").invoke(p);
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isPaused() {
        return paused && player != null && prepared;
    }

    public long getPositionMs() {
        Object p = player;
        if (p == null || !prepared) return 0L;
        try {
            return Math.max(0, ((Number) method(p, "getCurrentPosition").invoke(p)).longValue());
        } catch (Exception ignored) {
            return 0L;
        }
    }

    public long getDurationMs() {
        Object p = player;
        if (p == null || !prepared) return 0L;
        try {
            return Math.max(0, ((Number) method(p, "getDuration").invoke(p)).longValue());
        } catch (Exception ignored) {
            return 0L;
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float value) {
        volume = Math.max(0.0F, Math.min(1.0F, value));
        Object p = player;
        if (p == null) return;
        try {
            method(p, "setVolume", float.class, float.class).invoke(p, volume, volume);
        } catch (Exception ignored) {
        }
    }

    public boolean play(Path path) {
        if (path == null || !Files.isRegularFile(path)) return false;
        if (!available) return false;
        synchronized (lock) {
            try {
                releaseLocked();
                Class<?> cls = Class.forName("android.media.MediaPlayer");
                Object p = cls.getConstructor().newInstance();
                method(p, "setDataSource", String.class).invoke(p, path.toAbsolutePath().toString());
                method(p, "prepare").invoke(p);
                method(p, "setVolume", float.class, float.class).invoke(p, volume, volume);
                method(p, "start").invoke(p);
                player = p;
                current = path;
                index = tracks.indexOf(path);
                prepared = true;
                paused = false;
                return true;
            } catch (Exception e) {
                releaseLocked();
                return false;
            }
        }
    }

    public boolean playIndex(int requestedIndex) {
        List<Path> list = getTracks();
        if (requestedIndex < 0 || requestedIndex >= list.size()) return false;
        return play(list.get(requestedIndex));
    }

    public boolean toggle() {
        Object p = player;
        if (p == null || !prepared) {
            return tracks.isEmpty() ? false : playIndex(index >= 0 ? index : 0);
        }
        synchronized (lock) {
            try {
                if ((Boolean) method(p, "isPlaying").invoke(p)) {
                    method(p, "pause").invoke(p);
                    paused = true;
                } else {
                    method(p, "start").invoke(p);
                    paused = false;
                }
                return true;
            } catch (Exception ignored) {
                return false;
            }
        }
    }

    public boolean next() {
        List<Path> list = getTracks();
        if (list.isEmpty()) return false;
        int next = index < 0 ? 0 : (index + 1) % list.size();
        return playIndex(next);
    }

    public boolean previous() {
        List<Path> list = getTracks();
        if (list.isEmpty()) return false;
        int previous = index <= 0 ? list.size() - 1 : index - 1;
        return playIndex(previous);
    }

    public boolean stop() {
        synchronized (lock) {
            if (player == null) return false;
            releaseLocked();
            current = null;
            prepared = false;
            paused = false;
            return true;
        }
    }

    public void seek(long positionMs) {
        Object p = player;
        if (p == null || !prepared) return;
        try {
            int duration = ((Number) method(p, "getDuration").invoke(p)).intValue();
            int position = (int) Math.max(0L, Math.min((long) duration, positionMs));
            method(p, "seekTo", int.class).invoke(p, position);
        } catch (Exception ignored) {
        }
    }

    public void rescan() {
        ensureDirectory();
        ArrayList<Path> found = new ArrayList<>();
        try (var stream = Files.list(musicDirectory)) {
            stream.filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().toLowerCase(Locale.ROOT).endsWith(".ogg"))
                .sorted(Comparator.comparing(path -> path.getFileName().toString().toLowerCase(Locale.ROOT)))
                .forEach(found::add);
        } catch (IOException ignored) {
        }
        tracks.clear();
        tracks.addAll(found);
        lastScan = System.currentTimeMillis();
        if (current != null) {
            index = tracks.indexOf(current);
        }
    }

    private void ensureDirectory() {
        try {
            Files.createDirectories(musicDirectory);
        } catch (IOException ignored) {
        }
    }

    private void startPoller() {
        poller = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(POLL_MS);
                    Object p = player;
                    if (p == null || !prepared) continue;
                    boolean playing = isPlaying();
                    // Track changes are controlled by MusicModule. When a track
                    // reaches the end, the module disables itself instead of
                    // silently jumping to the next song.
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Throwable ignored) {
                }
            }
        }, "velum-local-music");
        poller.setDaemon(true);
        poller.setPriority(Thread.MIN_PRIORITY);
        poller.start();
    }

    private void releaseLocked() {
        Object p = player;
        player = null;
        prepared = false;
        paused = false;
        if (p == null) return;
        try {
            method(p, "stop").invoke(p);
        } catch (Exception ignored) {
        }
        try {
            method(p, "release").invoke(p);
        } catch (Exception ignored) {
        }
    }

    private static boolean hasAndroidMediaPlayer() {
        try {
            Class.forName("android.media.MediaPlayer");
            return true;
        } catch (Throwable ignored) {
            return false;
        }
    }

    private static Method method(Object object, String name, Class<?>... types) throws NoSuchMethodException {
        return object.getClass().getMethod(name, types);
    }

    private static String displayName(Path path) {
        String name = path.getFileName().toString();
        if (name.toLowerCase(Locale.ROOT).endsWith(".ogg")) {
            name = name.substring(0, name.length() - 4);
        }
        return name.replace('_', ' ');
    }
}
