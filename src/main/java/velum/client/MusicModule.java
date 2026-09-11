package velum.client;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import ua.mintantileak.spk.Compile;

/**
 * Velum local music module.
 *
 * Tracks are read from <runDirectory>/Velum/music/*.ogg.
 * The module itself is the player: enable = play, disable = stop.
 * Right-clicking the module exposes a normal ModeSetting with the available tracks.
 */
@ModuleInfo(name = "Music", category = ModuleCategory.VISUALS, III_method_a89e5834 = "modules.descriptions.music")
public class MusicModule extends Module {
    private final ModeSetting trackSetting;
    private String trackSignature = "";
    private String lastRequestedTrack = "";

    public MusicModule() {
        this.trackSetting = new ModeSetting(this, "Track");
        this.syncTracks(true);

        ClientTickEvents.END_CLIENT_TICK.register(client -> this.tickMusic());
    }

    @Compile(obfuscation = 4)
    private void tickMusic() {
        VelumLocalMusic music = VelumLocalMusic.getInstance();
        List<Path> tracks = music.getTracks();
        this.syncTracks(false);

        if (!this.isEnabled()) {
            return;
        }

        Path selected = this.getSelectedTrack(tracks);
        if (selected == null) {
            this.setEnabled(false, true);
            return;
        }

        String selectedKey = selected.toAbsolutePath().normalize().toString();
        Path current = music.getCurrentPath();

        // Changing the Track setting while Music is enabled immediately switches song.
        if (current == null || !selected.equals(current) || !selectedKey.equals(this.lastRequestedTrack)) {
            if (!music.play(selected)) {
                this.setEnabled(false, true);
                return;
            }
            this.lastRequestedTrack = selectedKey;
            return;
        }

        // A finished track disables the module instead of starting the next one.
        if (!music.isPlaying() && !music.isPaused()) {
            long duration = music.getDurationMs();
            long position = music.getPositionMs();
            if (duration > 0L && position >= duration - 350L) {
                this.setEnabled(false, true);
            } else if (current != null && !music.isAvailable()) {
                this.setEnabled(false, true);
            }
        }
    }

    private void syncTracks(boolean force) {
        List<Path> tracks = VelumLocalMusic.getInstance().getTracks();
        StringBuilder signatureBuilder = new StringBuilder();
        List<String> names = new ArrayList<>(tracks.size());

        for (Path path : tracks) {
            String name = displayName(path);
            names.add(name);
            signatureBuilder.append(name).append('\u0000');
        }

        String newSignature = signatureBuilder.toString();
        if (!force && newSignature.equals(this.trackSignature)) {
            return;
        }

        this.trackSignature = newSignature;
        String currentSelection = this.trackSetting.i_method_f85f3850() == null
            ? null
            : this.trackSetting.i_method_f85f3850().getName();

        this.trackSetting.I_method_15e42ca0(names.toArray(String[]::new));

        if (currentSelection != null) {
            for (ModeSetting.Nested1_42765c60 option : this.trackSetting.I_method_e1d4a248()) {
                if (option.getName().equalsIgnoreCase(currentSelection)) {
                    option.select();
                    break;
                }
            }
        }
    }

    private Path getSelectedTrack(List<Path> tracks) {
        ModeSetting.Nested1_42765c60 selected = this.trackSetting.i_method_f85f3850();
        if (selected == null) {
            return tracks.isEmpty() ? null : tracks.getFirst();
        }

        String selectedName = selected.getName();
        for (Path path : tracks) {
            if (displayName(path).equalsIgnoreCase(selectedName)) {
                return path;
            }
        }
        return tracks.isEmpty() ? null : tracks.getFirst();
    }

    private static String displayName(Path path) {
        String name = path.getFileName().toString();
        if (name.toLowerCase(Locale.ROOT).endsWith(".ogg")) {
            name = name.substring(0, name.length() - 4);
        }
        return name.replace('_', ' ');
    }

    @Override
    public void onEnable() {
        this.syncTracks(true);
        Path selected = this.getSelectedTrack(VelumLocalMusic.getInstance().getTracks());
        if (selected == null || !VelumLocalMusic.getInstance().play(selected)) {
            this.setEnabled(false, true);
            return;
        }
        this.lastRequestedTrack = selected.toAbsolutePath().normalize().toString();
    }

    @Override
    public void onDisable() {
        VelumLocalMusic.getInstance().stop();
        this.lastRequestedTrack = "";
    }

    public ModeSetting getTrackSetting() {
        return this.trackSetting;
    }
}
