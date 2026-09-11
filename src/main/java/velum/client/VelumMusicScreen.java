package velum.client;

import java.nio.file.Path;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import pyvelum.utility.render.ColorRGBA;

/** Compact local music browser/player styled around Velum's current theme. */
public final class VelumMusicScreen extends Screen {
    private final Screen parent;
    private final VelumLocalMusic music = VelumLocalMusic.getInstance();
    private int scroll;
    private long lastScan;

    public VelumMusicScreen(Screen parent) {
        super(Text.literal("Velum Music"));
        this.parent = parent;
    }

    public static void open(Screen parent) {
        MinecraftClient.getInstance().setScreen(new VelumMusicScreen(parent));
    }

    @Override
    protected void init() {
        music.rescan();
        lastScan = System.currentTimeMillis();
    }

    @Override
    public void tick() {
        if (System.currentTimeMillis() - lastScan > 2500L) {
            music.rescan();
            lastScan = System.currentTimeMillis();
        }
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        context.fill(0, 0, width, height, argb(235, 8, 8, 10));

        int panelW = Math.min(470, width - 32);
        int panelH = Math.min(330, height - 32);
        int left = (width - panelW) / 2;
        int top = (height - panelH) / 2;
        ColorRGBA accent = IiiiiIIIi_Class242.ii_field_d0c8ec5;
        ColorRGBA primary = IiiiiIIIi_Class242.iII_field_d0c8ec5;
        ColorRGBA muted = primary.mulAlpha(0.52F);

        context.fill(left, top, left + panelW, top + panelH, argb(245, 18, 17, 22));
        context.fill(left, top, left + panelW, top + 1, accent.getRGB());
        context.drawTextWithShadow(textRenderer, Text.literal("MUSIC"), left + 16, top + 14, primary.getRGB());
        context.drawTextWithShadow(textRenderer, Text.literal("Velum local player"), left + 16, top + 27, muted.getRGB());

        List<Path> tracks = music.getTracks();
        int listTop = top + 48;
        int rowH = 25;
        int visible = Math.max(1, (panelH - 125) / rowH);
        int maxScroll = Math.max(0, tracks.size() - visible);
        scroll = Math.max(0, Math.min(scroll, maxScroll));

        for (int i = 0; i < visible && i + scroll < tracks.size(); i++) {
            int index = i + scroll;
            Path track = tracks.get(index);
            int y = listTop + i * rowH;
            boolean hovered = mouseX >= left + 10 && mouseX <= left + panelW - 10 && mouseY >= y && mouseY < y + rowH - 2;
            boolean current = track.equals(getCurrentPath());
            int bg = current ? accent.mulAlpha(0.18F).getRGB() : (hovered ? primary.mulAlpha(0.07F).getRGB() : argb(0, 0, 0, 0));
            if ((bg >>> 24) != 0) context.fill(left + 10, y, left + panelW - 10, y + rowH - 2, bg);
            if (current) {
                context.drawTexture(RenderLayer::getGuiTextured, VelumClient.id("icons/music.png"), left + 14, y + 5, 0.0F, 0.0F, 14, 14, 14, 14);
            }
            context.drawTextWithShadow(textRenderer, Text.literal(displayName(track)), left + (current ? 34 : 17), y + 7, current ? accent.getRGB() : primary.getRGB());
        }

        if (tracks.isEmpty()) {
            context.drawCenteredTextWithShadow(textRenderer, Text.literal("Положи .ogg файлы в Velum/music"), width / 2, listTop + 35, muted.getRGB());
            context.drawCenteredTextWithShadow(textRenderer, Text.literal(music.getMusicDirectory().toString()), width / 2, listTop + 51, muted.getRGB());
        } else if (!music.isAvailable()) {
            context.drawCenteredTextWithShadow(textRenderer, Text.literal("Локальный плеер доступен на Android"), width / 2, listTop + visible * rowH + 3, accent.getRGB());
        }

        int controlsY = top + panelH - 61;
        context.fill(left + 10, controlsY - 9, left + panelW - 10, controlsY - 8, primary.mulAlpha(0.10F).getRGB());
        long duration = music.getDurationMs();
        long position = music.getPositionMs();
        float progress = duration <= 0 ? 0.0F : Math.max(0.0F, Math.min(1.0F, position / (float) duration));
        int barL = left + 14;
        int barR = left + panelW - 14;
        context.fill(barL, controlsY - 9, barR, controlsY - 7, primary.mulAlpha(0.12F).getRGB());
        context.fill(barL, controlsY - 9, barL + Math.round((barR - barL) * progress), controlsY - 7, accent.getRGB());

        String currentTitle = music.getCurrentTitle();
        String time = format(position) + " / " + format(duration);
        context.drawTextWithShadow(textRenderer, Text.literal(currentTitle.isEmpty() ? "No track" : currentTitle), left + 14, controlsY, primary.getRGB());
        context.drawTextWithShadow(textRenderer, Text.literal(time), left + panelW - 14 - textRenderer.getWidth(time), controlsY, muted.getRGB());

        int buttonY = top + panelH - 35;
        drawButton(context, left + 14, buttonY, 58, 20, "‹", mouseX, mouseY, music::previous);
        drawButton(context, left + 78, buttonY, 58, 20, music.isPlaying() ? "Ⅱ" : "▶", mouseX, mouseY, music::toggle);
        drawButton(context, left + 142, buttonY, 58, 20, "›", mouseX, mouseY, music::next);
        drawButton(context, left + 206, buttonY, 80, 20, "Stop", mouseX, mouseY, music::stop);
        drawButton(context, left + panelW - 88, buttonY, 74, 20, "Close", mouseX, mouseY, () -> MinecraftClient.getInstance().setScreen(parent));
    }

    private Path getCurrentPath() {
        String title = music.getCurrentTitle();
        if (title.isEmpty()) return null;
        for (Path path : music.getTracks()) {
            if (displayName(path).equals(title)) return path;
        }
        return null;
    }

    private void drawButton(DrawContext context, int x, int y, int w, int h, String label, int mouseX, int mouseY, Runnable action) {
        ColorRGBA accent = IiiiiIIIi_Class242.ii_field_d0c8ec5;
        ColorRGBA text = IiiiiIIIi_Class242.iII_field_d0c8ec5;
        boolean hover = mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
        context.fill(x, y, x + w, y + h, (hover ? accent.mulAlpha(0.20F) : text.mulAlpha(0.06F)).getRGB());
        context.drawCenteredTextWithShadow(textRenderer, Text.literal(label), x + w / 2, y + 6, hover ? accent.getRGB() : text.getRGB());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) return super.mouseClicked(mouseX, mouseY, button);
        int panelW = Math.min(470, width - 32);
        int panelH = Math.min(330, height - 32);
        int left = (width - panelW) / 2;
        int top = (height - panelH) / 2;
        int listTop = top + 48;
        int rowH = 25;
        int visible = Math.max(1, (panelH - 125) / rowH);
        List<Path> tracks = music.getTracks();
        for (int i = 0; i < visible && i + scroll < tracks.size(); i++) {
            int y = listTop + i * rowH;
            if (mouseX >= left + 10 && mouseX <= left + panelW - 10 && mouseY >= y && mouseY < y + rowH - 2) {
                music.play(tracks.get(i + scroll));
                return true;
            }
        }

        int controlsY = top + panelH - 61;
        long duration = music.getDurationMs();
        if (duration > 0 && mouseY >= controlsY - 15 && mouseY <= controlsY + 7 && mouseX >= left + 14 && mouseX <= left + panelW - 14) {
            float ratio = (float) ((mouseX - (left + 14)) / (double) (panelW - 28));
            music.seek((long) (duration * Math.max(0.0, Math.min(1.0, ratio))));
            return true;
        }

        int buttonY = top + panelH - 35;
        if (inside(mouseX, mouseY, left + 14, buttonY, 58, 20)) return music.previous();
        if (inside(mouseX, mouseY, left + 78, buttonY, 58, 20)) return music.toggle();
        if (inside(mouseX, mouseY, left + 142, buttonY, 58, 20)) return music.next();
        if (inside(mouseX, mouseY, left + 206, buttonY, 80, 20)) return music.stop();
        if (inside(mouseX, mouseY, left + panelW - 88, buttonY, 74, 20)) {
            MinecraftClient.getInstance().setScreen(parent);
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        scroll += verticalAmount < 0 ? 1 : -1;
        int visible = Math.max(1, (Math.min(330, height - 32) - 125) / 25);
        scroll = Math.max(0, Math.min(Math.max(0, music.getTracks().size() - visible), scroll));
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            MinecraftClient.getInstance().setScreen(parent);
            return true;
        }
        if (keyCode == 32) return music.toggle();
        if (keyCode == 262) return music.next();
        if (keyCode == 263) return music.previous();
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private static boolean inside(double mouseX, double mouseY, int x, int y, int w, int h) {
        return mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
    }

    private static String displayName(Path path) {
        String name = path.getFileName().toString();
        if (name.toLowerCase(java.util.Locale.ROOT).endsWith(".ogg")) name = name.substring(0, name.length() - 4);
        return name.replace('_', ' ');
    }

    private static String format(long ms) {
        if (ms <= 0) return "00:00";
        long total = ms / 1000L;
        return String.format("%02d:%02d", total / 60L, total % 60L);
    }

    private static int argb(int a, int r, int g, int b) {
        return ((a & 255) << 24) | ((r & 255) << 16) | ((g & 255) << 8) | (b & 255);
    }
}
