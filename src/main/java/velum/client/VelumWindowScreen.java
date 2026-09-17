package velum.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import pyvelum.utility.render.ColorRGBA;

/**
 * Velum Window: a deliberately different, single-surface ClickGUI.
 * It uses Velum's existing Liquid Glass renderer instead of the old component layout.
 */
public final class VelumWindowScreen extends IiiIIiiiI_Class207 {
    private static final float WINDOW_W = 760.0f;
    private static final float WINDOW_H = 470.0f;
    private static final float RADIUS = 20.0f;

    private static final ColorRGBA BACKDROP = new ColorRGBA(4, 6, 11, 118);
    private static final ColorRGBA GLASS = new ColorRGBA(8, 10, 17, 218);
    private static final ColorRGBA GLASS_SOFT = new ColorRGBA(16, 18, 28, 142);
    private static final ColorRGBA WHITE = new ColorRGBA(245, 247, 255, 255);
    private static final ColorRGBA MUTED = new ColorRGBA(151, 158, 178, 255);
    private static final ColorRGBA LINE = new ColorRGBA(255, 255, 255, 25);
    private static final ColorRGBA BLUE = new ColorRGBA(110, 132, 255, 255);
    private static final ColorRGBA VIOLET = new ColorRGBA(164, 111, 255, 255);
    private static final ColorRGBA ENABLED = new ColorRGBA(112, 139, 255, 255);

    private ModuleCategory category = ModuleCategory.COMBAT;
    private ModuleEntry selected;
    private String search = "";
    private long openedAt;
    private float scroll;

    public VelumWindowScreen() {
        this.openedAt = System.currentTimeMillis();
        this.closing = false;
    }

    @Override
    public void init() {
        super.init();
        this.openedAt = System.currentTimeMillis();
        this.scroll = 0.0f;
    }

    @Override
    public void render(III ctx) {
        final double mouseX = ctx.I_method_b1c3e152();
        final double mouseY = ctx.i_method_b1d26d32();
        final float t = Math.min(1.0f, (System.currentTimeMillis() - openedAt) / 260.0f);
        final float eased = 1.0f - (float)Math.pow(1.0f - t, 3.0);
        final float scale = 0.965f + 0.035f * eased;
        final float alpha = eased;

        ctx.fill(0, 0, width, height, BACKDROP.withAlpha(118.0f * alpha).getRGB());

        float w = WINDOW_W;
        float h = Math.min(WINDOW_H, height - 28.0f);
        if (w > width - 28.0f) w = width - 28.0f;
        float x = (width - w) * 0.5f;
        float y = (height - h) * 0.5f;

        ctx.getMatrices().push();
        ctx.getMatrices().translate(width * 0.5f, height * 0.5f, 0.0f);
        ctx.getMatrices().scale(scale, scale, 1.0f);
        ctx.getMatrices().translate(-width * 0.5f, -height * 0.5f, 0.0f);

        IIiii_Class8 shape = IIiii_Class8.I_method_893b2757(RADIUS);
        IIiii_Class8 inner = IIiii_Class8.I_method_893b2757(RADIUS - 2.0f);

        // Deep shadow + real backdrop blur + liquid glass.
        ctx.drawShadow(x - 8.0f, y - 6.0f, w + 16.0f, h + 14.0f, 26.0f, shape,
                new ColorRGBA(0, 0, 0, 135.0f * alpha));
        ctx.drawBlurredRect(x, y, w, h, 38.0f, 8.0f, shape,
                ColorRGBA.WHITE.withAlpha(155.0f * alpha));
        ctx.drawLiquidGlass(x, y, w, h, RADIUS, 0.10f, shape,
                ColorRGBA.WHITE.withAlpha(228.0f * alpha));
        ctx.drawRoundedRect(x, y, w, h, inner, GLASS.withAlpha(218.0f * alpha));

        // A restrained top-to-bottom glass tint.
        ctx.drawRoundedRect(x + 1.0f, y + 1.0f, w - 2.0f, 86.0f, IIiii_Class8.I_method_893b2757(RADIUS - 1.0f),
                new ColorRGBA(24, 28, 48, 74.0f * alpha));
        ctx.drawRoundedBorder(x, y, w, h, 1.0f, shape, new ColorRGBA(255, 255, 255, 38.0f * alpha));

        drawSidebar(ctx, x, y, h, alpha);
        drawHeader(ctx, x, y, w, alpha);
        drawModules(ctx, x, y, w, h, alpha);
        drawDetails(ctx, x, y, w, h, alpha);

        ctx.getMatrices().pop();
    }

    private void drawSidebar(III ctx, float x, float y, float h, float alpha) {
        float sidebarW = 178.0f;
        ctx.drawRoundedRect(x + 10.0f, y + 10.0f, sidebarW, h - 20.0f,
                IIiii_Class8.I_method_893b2757(15.0f), GLASS_SOFT.withAlpha(115.0f * alpha));

        text(ctx, "VELUM", x + 27.0f, y + 27.0f, WHITE.withAlpha(255.0f * alpha), true);
        text(ctx, "WINDOW", x + 27.0f, y + 44.0f, MUTED.withAlpha(190.0f * alpha), false);

        ModuleCategory[] categories = ModuleCategory.values();
        float cy = y + 88.0f;
        for (ModuleCategory cat : categories) {
            boolean active = cat == category;
            boolean hover = inside(mouseX, mouseY, x + 20.0f, cy - 3.0f, sidebarW - 20.0f, 36.0f);
            float a = active ? 155.0f : (hover ? 72.0f : 22.0f);
            ColorRGBA fill = active ? new ColorRGBA(91, 112, 230, a) : new ColorRGBA(255, 255, 255, a);
            ctx.drawRoundedRect(x + 20.0f, cy, sidebarW - 40.0f, 32.0f,
                    IIiii_Class8.I_method_893b2757(9.0f), fill.withAlpha(a * alpha));
            if (active) {
                ctx.drawRoundedRect(x + 20.0f, cy + 8.0f, 2.5f, 16.0f,
                        IIiii_Class8.I_method_893b2757(2.0f), BLUE.withAlpha(230.0f * alpha));
            }
            text(ctx, cat.I_method_b23d1194(), x + 34.0f, cy + 10.0f,
                    (active ? WHITE : MUTED).withAlpha((active ? 255.0f : 220.0f) * alpha), active);
            cy += 40.0f;
        }

        text(ctx, "ESC", x + 28.0f, y + h - 34.0f, MUTED.withAlpha(130.0f * alpha), false);
        text(ctx, "close", x + 61.0f, y + h - 34.0f, MUTED.withAlpha(130.0f * alpha), false);
    }

    private void drawHeader(III ctx, float x, float y, float w, float alpha) {
        float left = x + 208.0f;
        text(ctx, category.I_method_b23d1194(), left, y + 29.0f, WHITE.withAlpha(255.0f * alpha), true);
        text(ctx, "Modules", left, y + 49.0f, MUTED.withAlpha(190.0f * alpha), false);

        float searchX = x + w - 224.0f;
        ctx.drawRoundedRect(searchX, y + 20.0f, 190.0f, 34.0f,
                IIiii_Class8.I_method_893b2757(10.0f), new ColorRGBA(255, 255, 255, 20.0f * alpha));
        String label = search.isEmpty() ? "Search modules" : search;
        text(ctx, "⌕  " + label, searchX + 13.0f, y + 31.0f,
                (search.isEmpty() ? MUTED : WHITE).withAlpha(205.0f * alpha), false);
        ctx.drawRoundedBorder(searchX, y + 20.0f, 190.0f, 34.0f, 0.7f,
                IIiii_Class8.I_method_893b2757(10.0f), LINE.withAlpha(255.0f * alpha));
    }

    private void drawModules(III ctx, float x, float y, float w, float h, float alpha) {
        float left = x + 208.0f;
        float top = y + 76.0f;
        float right = x + w - 220.0f;
        float cardW = Math.max(190.0f, (right - left - 12.0f) / 2.0f);
        List<ModuleEntry> modules = filteredModules();
        float cardH = 62.0f;
        int visible = Math.min(modules.size(), 8);

        for (int i = 0; i < visible; i++) {
            ModuleEntry module = modules.get(i);
            int col = i % 2;
            int row = i / 2;
            float mx = left + col * (cardW + 12.0f);
            float my = top + row * (cardH + 10.0f) - scroll;
            if (my + cardH < top || my > y + h - 16.0f) continue;

            boolean active = module.isEnabled();
            boolean hover = inside(mouseX, mouseY, mx, my, cardW, cardH);
            boolean chosen = module == selected;
            ColorRGBA base = active ? new ColorRGBA(49, 57, 101, 105) : new ColorRGBA(255, 255, 255, hover ? 24 : 14);
            if (chosen) base = new ColorRGBA(91, 100, 175, 100);

            ctx.drawRoundedRect(mx, my, cardW, cardH, IIiii_Class8.I_method_893b2757(13.0f), base.withAlpha(base.getAlpha() * alpha));
            if (active) {
                ctx.drawRoundedRect(mx + 10.0f, my + 12.0f, 3.0f, cardH - 24.0f,
                        IIiii_Class8.I_method_893b2757(2.0f), ENABLED.withAlpha(225.0f * alpha));
            }
            ctx.drawRoundedBorder(mx, my, cardW, cardH, 0.7f, IIiii_Class8.I_method_893b2757(13.0f),
                    (chosen ? new ColorRGBA(136, 147, 255, 80) : LINE).withAlpha(255.0f * alpha));

            text(ctx, module.getName(), mx + 22.0f, my + 16.0f,
                    WHITE.withAlpha(235.0f * alpha), active);
            String state = active ? "Enabled" : "Disabled";
            text(ctx, state, mx + 22.0f, my + 37.0f,
                    (active ? BLUE : MUTED).withAlpha(190.0f * alpha), false);
        }

        if (modules.size() > visible) {
            text(ctx, "Scroll for more", left, y + h - 25.0f, MUTED.withAlpha(120.0f * alpha), false);
        }
    }

    private void drawDetails(III ctx, float x, float y, float w, float h, float alpha) {
        float px = x + w - 205.0f;
        float py = y + 76.0f;
        float pw = 180.0f;
        float ph = h - 92.0f;
        ctx.drawRoundedRect(px, py, pw, ph, IIiii_Class8.I_method_893b2757(14.0f),
                new ColorRGBA(255, 255, 255, 16.0f * alpha));
        ctx.drawRoundedBorder(px, py, pw, ph, 0.7f, IIiii_Class8.I_method_893b2757(14.0f), LINE.withAlpha(220.0f * alpha));

        if (selected == null) {
            text(ctx, "Select a module", px + 18.0f, py + 24.0f, WHITE.withAlpha(235.0f * alpha), true);
            text(ctx, "Click a module to view", px + 18.0f, py + 48.0f, MUTED.withAlpha(175.0f * alpha), false);
            text(ctx, "its information.", px + 18.0f, py + 64.0f, MUTED.withAlpha(175.0f * alpha), false);
            return;
        }

        text(ctx, selected.getName(), px + 18.0f, py + 24.0f, WHITE.withAlpha(255.0f * alpha), true);
        text(ctx, selected.isEnabled() ? "ACTIVE" : "INACTIVE", px + 18.0f, py + 45.0f,
                (selected.isEnabled() ? BLUE : MUTED).withAlpha(220.0f * alpha), true);

        String desc = selected.i_method_bf522194();
        if (desc != null && !desc.isBlank() && !desc.equals("")) {
            text(ctx, desc, px + 18.0f, py + 76.0f, MUTED.withAlpha(180.0f * alpha), false);
        }

        float by = py + ph - 52.0f;
        ctx.drawRoundedRect(px + 14.0f, by, pw - 28.0f, 34.0f,
                IIiii_Class8.I_method_893b2757(9.0f),
                selected.isEnabled() ? new ColorRGBA(85, 104, 220, 130) : new ColorRGBA(255, 255, 255, 22));
        text(ctx, selected.isEnabled() ? "Enabled" : "Enable module", px + 27.0f, by + 11.0f,
                WHITE.withAlpha(235.0f * alpha), true);
    }

    private List<ModuleEntry> filteredModules() {
        List<ModuleEntry> result = new ArrayList<>();
        String q = search.trim().toLowerCase();
        for (ModuleEntry module : VelumClient.getInstance().getModuleManager().getModules()) {
            if (module.getCategory() != category || !module.isAvailable()) continue;
            if (module instanceof MenuModule || module instanceof GlobalsMenuModule) continue;
            if (!q.isEmpty() && !module.getName().toLowerCase().contains(q)) continue;
            result.add(module);
        }
        result.sort(Comparator.comparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER));
        return result;
    }

    private void text(III ctx, String value, float x, float y, ColorRGBA color, boolean shadow) {
        if (shadow) ctx.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal(value), (int)x, (int)y, color.getRGB());
        else ctx.drawText(MinecraftClient.getInstance().textRenderer, Text.literal(value), (int)x, (int)y, color.getRGB(), false);
    }

    private static boolean inside(double mx, double my, float x, float y, float w, float h) {
        return mx >= x && mx <= x + w && my >= y && my <= y + h;
    }

    @Override
    public void onMouseClicked(double mouseX, double mouseY, IiIII_Class9 button) {
        if (button != IiIII_Class9.I_field_2f4c8d6c) return;
        float w = Math.min(WINDOW_W, width - 28.0f);
        float h = Math.min(WINDOW_H, height - 28.0f);
        float x = (width - w) * 0.5f;
        float y = (height - h) * 0.5f;

        if (!inside(mouseX, mouseY, x, y, w, h)) return;

        float sidebarW = 178.0f;
        float cy = y + 88.0f;
        for (ModuleCategory cat : ModuleCategory.values()) {
            if (inside(mouseX, mouseY, x + 20.0f, cy - 3.0f, sidebarW - 40.0f, 38.0f)) {
                category = cat;
                selected = null;
                scroll = 0.0f;
                return;
            }
            cy += 40.0f;
        }

        float left = x + 208.0f;
        float right = x + w - 220.0f;
        float cardW = Math.max(190.0f, (right - left - 12.0f) / 2.0f);
        List<ModuleEntry> modules = filteredModules();
        for (int i = 0; i < Math.min(modules.size(), 8); i++) {
            int col = i % 2;
            int row = i / 2;
            float mx = left + col * (cardW + 12.0f);
            float my = y + 76.0f + row * 72.0f - scroll;
            if (!inside(mouseX, mouseY, mx, my, cardW, 62.0f)) continue;
            ModuleEntry module = modules.get(i);
            selected = module;
            module.setEnabled(!module.isEnabled(), true);
            return;
        }

        return;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        float max = Math.max(0.0f, ((filteredModules().size() + 1) / 2) * 72.0f - 300.0f);
        scroll = Math.max(0.0f, Math.min(max, scroll - (float)verticalAmount * 34.0f));
        return true;
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        if (Character.isLetterOrDigit(chr) || chr == ' ' || chr == '_' || chr == '-') {
            search += chr;
            scroll = 0.0f;
            return true;
        }
        return super.charTyped(chr, modifiers);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256) {
            close();
            return true;
        }
        if (keyCode == 259 && !search.isEmpty()) {
            search = search.substring(0, search.length() - 1);
            scroll = 0.0f;
            return true;
        }
        if (MenuModule.I_method_48514ce8(keyCode)) {
            close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void close() {
        MenuModule menu = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
        if (menu != null && menu.isEnabled()) menu.disable();
        else MinecraftClient.getInstance().setScreen(null);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        // The glass/backdrop is rendered explicitly in render().
    }
}
