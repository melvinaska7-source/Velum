package velum.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Velum's independent Premium ClickGUI.  It reproduces the compact WexSide-like
 * information architecture (sidebar + module browser + selected-module pane)
 * without bundling or copying WexSide implementation/assets.
 */
public final class PremiumMenuScreen extends Screen {
    private static final int BG = 0xD9080A0F;
    private static final int PANEL = 0xF014151C;
    private static final int PANEL_2 = 0xF01B1C24;
    private static final int PANEL_3 = 0xF021222B;
    private static final int LINE = 0xFF292B35;
    private static final int TEXT = 0xFFF3F4F7;
    private static final int MUTED = 0xFF8D909A;
    private static final int ACCENT = 0xFF8B6CFF;
    private static final int ACCENT_SOFT = 0xFF30254F;
    private static final float W = 488.0f;
    private static final float H = 318.0f;

    private ModuleCategory category = ModuleCategory.COMBAT;
    private Module selected;
    private String search = "";
    private float open = 0.0f;
    private long lastFrame;
    private boolean closing;

    public PremiumMenuScreen() {
        super(Text.literal("Velum Premium"));
        this.lastFrame = System.nanoTime();
    }

    @Override
    protected void init() {
        this.lastFrame = System.nanoTime();
        this.open = 0.0f;
        this.closing = false;
    }

    private float tickAnimation() {
        long now = System.nanoTime();
        float dt = Math.min(0.05f, (now - lastFrame) / 1_000_000_000.0f);
        lastFrame = now;
        float target = closing ? 0.0f : 1.0f;
        float speed = closing ? 14.0f : 11.0f;
        open += (target - open) * Math.min(1.0f, dt * speed);
        return ease(open);
    }

    private static float ease(float x) {
        x = Math.max(0.0f, Math.min(1.0f, x));
        return 1.0f - (float)Math.pow(1.0f - x, 3.0);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        float a = tickAnimation();
        int alpha = Math.max(0, Math.min(255, (int)(180.0f * a)));
        context.fill(0, 0, width, height, (alpha << 24) | 0x05060A);

        float scale = 0.94f + 0.06f * a;
        int x = Math.round(width / 2.0f - W * scale / 2.0f);
        int y = Math.round(height / 2.0f - H * scale / 2.0f);
        int w = Math.round(W * scale);
        int h = Math.round(H * scale);

        context.fill(x + 2, y + 3, x + w + 2, y + h + 4, 0x45000000);
        context.fill(x, y, x + w, y + h, PANEL);
        context.fill(x + 1, y + 1, x + w - 1, y + 45, PANEL_2);
        context.fill(x + 1, y + 45, x + 90, y + h - 1, PANEL_2);
        context.fill(x + 90, y + 45, x + 91, y + h - 1, LINE);

        drawHeader(context, x, y, w, scale, a);
        drawSidebar(context, x, y, scale, mouseX, mouseY);
        drawModules(context, x, y, w, h, scale, mouseX, mouseY);
        drawSelected(context, x, y, w, h, scale, mouseX, mouseY);

        if (closing && open < 0.015f) {
            MinecraftClient.getInstance().setScreen(null);
        }
    }

    private void drawHeader(DrawContext c, int x, int y, int w, float s, float a) {
        c.drawTextWithShadow(textRenderer, Text.literal("VELUM"), x + 15, y + 13, TEXT);
        c.drawTextWithShadow(textRenderer, Text.literal("PREMIUM"), x + 62, y + 13, ACCENT);
        c.fill(x + 15, y + 32, x + 73, y + 33, ACCENT);
        c.fill(x + w - 139, y + 11, x + w - 15, y + 34, 0xFF191A21);
        c.drawTextWithShadow(textRenderer, Text.literal(search.isEmpty() ? "Search modules" : search), x + w - 130, y + 17, MUTED);
    }

    private void drawSidebar(DrawContext c, int x, int y, float s, int mx, int my) {
        int yy = y + 58;
        for (ModuleCategory cat : ModuleCategory.values()) {
            boolean active = cat == category;
            int color = active ? ACCENT_SOFT : 0x00000000;
            c.fill(x + 7, yy - 3, x + 83, yy + 20, color);
            c.drawTextWithShadow(textRenderer, Text.literal(cat.I_method_b23d1194()), x + 17, yy + 4, active ? TEXT : MUTED);
            yy += 31;
        }
        c.drawTextWithShadow(textRenderer, Text.literal("CONFIGS"), x + 17, y + 230, MUTED);
        c.drawTextWithShadow(textRenderer, Text.literal("Profiles"), x + 17, y + 249, TEXT);
        c.drawTextWithShadow(textRenderer, Text.literal("Settings"), x + 17, y + 268, TEXT);
    }

    private List<Module> modules() {
        List<Module> result = new ArrayList<>();
        for (ModuleEntry entry : VelumClient.getInstance().getModuleManager().getModules()) {
            if (entry instanceof Module m && m.getCategory() == category && m.isAvailable()) {
                if (search.isEmpty() || m.getName().toLowerCase().contains(search.toLowerCase())) result.add(m);
            }
        }
        result.sort(Comparator.comparing(Module::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private void drawModules(DrawContext c, int x, int y, int w, int h, float s, int mx, int my) {
        int left = x + 100;
        int right = x + w - 15;
        int top = y + 56;
        int cardW = 132;
        int gap = 7;
        int col = 0;
        int row = 0;
        for (Module m : modules()) {
            int cx = left + col * (cardW + gap);
            int cy = top + row * 43;
            if (cx + cardW > right) { col = 0; row++; cx = left; cy = top + row * 43; }
            boolean selected = m == this.selected;
            boolean hover = mx >= cx && mx <= cx + cardW && my >= cy && my <= cy + 36;
            int bg = selected ? ACCENT_SOFT : (hover ? PANEL_3 : PANEL_2);
            c.fill(cx, cy, cx + cardW, cy + 36, bg);
            c.fill(cx, cy, cx + 3, cy + 36, m.isEnabled() ? ACCENT : LINE);
            c.drawTextWithShadow(textRenderer, Text.literal(m.getName()), cx + 10, cy + 7, TEXT);
            c.drawTextWithShadow(textRenderer, Text.literal(m.isEnabled() ? "ON" : "OFF"), cx + 10, cy + 22, m.isEnabled() ? ACCENT : MUTED);
            col++;
        }
    }

    private void drawSelected(DrawContext c, int x, int y, int w, int h, float s, int mx, int my) {
        if (selected == null) return;
        int px = x + 100;
        int py = y + h - 82;
        c.fill(px, py, x + w - 15, y + h - 15, PANEL_3);
        c.drawTextWithShadow(textRenderer, Text.literal(selected.getName()), px + 10, py + 10, TEXT);
        c.drawTextWithShadow(textRenderer, Text.literal(selected.isEnabled() ? "Enabled" : "Disabled"), px + 10, py + 25, selected.isEnabled() ? ACCENT : MUTED);
        c.drawTextWithShadow(textRenderer, Text.literal(selected.getSettings().size() + " settings"), px + 10, py + 40, MUTED);
        c.drawTextWithShadow(textRenderer, Text.literal("LMB: toggle   RMB: open settings"), px + 10, py + 55, MUTED);
    }

    private boolean inside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        float scale = 1.0f;
        int x = Math.round(width / 2.0f - W * scale / 2.0f);
        int y = Math.round(height / 2.0f - H * scale / 2.0f);
        if (button == 1) {
            if (inside((int)mouseX, (int)mouseY, x + 100, y + 56, (int)W - 115, (int)H - 140)) {
                for (Module m : modules()) {
                    // Context menu behaviour: RMB selects the module and shows its information pane.
                    if (selected == m) { break; }
                }
            }
        }
        if (mouseX >= x + 7 && mouseX <= x + 83 && mouseY >= y + 55 && mouseY <= y + 210) {
            int index = ((int)mouseY - (y + 55)) / 31;
            if (index >= 0 && index < ModuleCategory.values().length) {
                category = ModuleCategory.values()[index];
                selected = null;
                return true;
            }
        }
        List<Module> list = modules();
        int left = x + 100;
        int top = y + 56;
        int cardW = 132;
        int gap = 7;
        int col = 0, row = 0;
        for (Module m : list) {
            int cx = left + col * (cardW + gap);
            int cy = top + row * 43;
            if (cx + cardW > x + W - 15) { col = 0; row++; cx = left; cy = top + row * 43; }
            if (inside((int)mouseX, (int)mouseY, cx, cy, cardW, 36)) {
                selected = m;
                if (button == 0) m.toggle();
                return true;
            }
            col++;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (Character.isLetterOrDigit(chr) || chr == '_' || chr == ' ') {
            search += chr;
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE || keyCode == GLFW.GLFW_KEY_RIGHT_SHIFT) {
            if (!closing) closing = true;
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_BACKSPACE && !search.isEmpty()) {
            search = search.substring(0, search.length() - 1);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() { return false; }
}
