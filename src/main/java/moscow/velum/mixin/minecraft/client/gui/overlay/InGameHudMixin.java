package moscow.velum.mixin.minecraft.client.gui.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import globals.client.snowball.FakeFrozenTicksAccess;
import moscow.velum.mixin.accessors.InGameHudAccessor;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import pyvelum.events.render.HudRenderEvent;
import pyvelum.events.render.PostHudRenderEvent;
import pyvelum.events.render.PreHudRenderEvent;
import pyvelum.utility.render.ColorRGBA;
import pyvelum.utility.render.CustomDrawContext;
import velum.client.IIIiIiIi_Class22;
import velum.client.BeautifullyModule;
import velum.client.IIiIIi_Class10;
import velum.client.RemovalsModule;
import velum.client.IIiIiI_Class11;
import velum.client.IiIIiiIii_Class156;
import velum.client.IiIiIiIIi_Class170;
import velum.client.IiIiIiIiI_Class171;
import velum.client.VelumClient;
import velum.client.IiiiIiIii_Class236;
import velum.client.IiiiIiiII_Class237;
import velum.client.iIIIiiIii_Class284;
import velum.client.iIIIiiiII_Class285;
import velum.client.iIIiIIiIi_Class294;
import velum.client.iIiiIIiiI_Class359;
import velum.client.iIiiIIiii_Class360;
import velum.client.iIiiiiIii_Class380;
import velum.client.iiIIIIiii_Class392;

@Mixin(value={InGameHud.class})
public class InGameHudMixin
implements iIIiIIiIi_Class294 {
    @Shadow
    @Final
    private static Identifier field_27960;
    @Shadow
    @Final
    private PlayerListHud field_2015;
    @Unique
    private IiiiIiIii_Class236 velum$tabAnimation;
    @Unique
    private boolean velum$mainHudShifted;
    @Unique
    private boolean velum$expLevelShifted;

    @Unique
    private IiiiIiIii_Class236 velum$tabAnimation() {
        if (this.velum$tabAnimation == null) {
            this.velum$tabAnimation = new IiiiIiIii_Class236(200L, IiiiIiiII_Class237.IIII_field_dd60aac);
        }
        return this.velum$tabAnimation;
    }

    @Inject(method={"renderPlayerList"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$animatePlayerList(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (!BeautifullyModule.Iii_method_b1725263()) {
            return;
        }
        if (InGameHudMixin.I_field_3a9bda27.world == null || InGameHudMixin.I_field_3a9bda27.player == null || InGameHudMixin.I_field_3a9bda27.player.networkHandler == null) {
            return;
        }
        Scoreboard scoreboard = InGameHudMixin.I_field_3a9bda27.world.getScoreboard();
        ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST);
        boolean bl = InGameHudMixin.I_field_3a9bda27.options.playerListKey.isPressed() && (!I_field_3a9bda27.isInSingleplayer() || InGameHudMixin.I_field_3a9bda27.player.networkHandler.getListedPlayerListEntries().size() > 1 || scoreboardObjective != null);
        callbackInfo.cancel();
        IiiiIiIii_Class236 iiiiIiIii_Class236 = this.velum$tabAnimation();
        iiiiIiIii_Class236.I_method_edd6ec25(200L);
        iiiiIiIii_Class236.I_method_df2f9087(bl ? IiiiIiiII_Class237.i_field_dd60aac : IiiiIiiII_Class237.iii_field_dd60aac);
        float f = iiiiIiIii_Class236.I_method_edd6dd11(bl ? 1.0f : 0.0f);
        this.field_2015.setVisible(bl);
        if (f <= 0.005f) {
            return;
        }
        float f2 = MathHelper.clamp((float)f, (float)0.0f, (float)1.0f);
        float f3 = drawContext.getScaledWindowWidth();
        float f4 = 0.96f + 0.04f * f;
        drawContext.draw();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)f2);
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(f3 / 2.0f, 0.0f, 0.0f);
        drawContext.getMatrices().scale(f4, f4, 1.0f);
        drawContext.getMatrices().translate(-f3 / 2.0f, (f - 1.0f) * 10.0f, 0.0f);
        this.field_2015.render(drawContext, drawContext.getScaledWindowWidth(), scoreboard, scoreboardObjective);
        drawContext.draw();
        drawContext.getMatrices().pop();
        RenderSystem.setShaderColor((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @Redirect(method={"clear"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHud;clear(Z)V"))
    private void velum$keepChatHistory(ChatHud chatHud, boolean bl) {
        if (BeautifullyModule.iIi_method_e64a7623()) {
            return;
        }
        chatHud.clear(bl);
    }

    @Inject(method={"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderScoreboardSidebarHook(DrawContext drawContext, ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83;
        if (scoreboardObjective.getDisplayName().getString().contains("\u0410\u043d\u0430\u0440\u0445\u0438\u044f") && (iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.I_field_ac164e6c) || iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.i_field_ac164e6c))) {
            try {
                iIIIiiiII_Class285.i_field_49 = Integer.parseInt(scoreboardObjective.getDisplayName().getString().split("-")[1].trim());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (scoreboardObjective.getDisplayName().getString().contains("\u0413\u0440\u0438\u0444\u0435\u0440\u0441\u043a\u0438\u0439") && iIIIiiiII_Class285.i_field_5a) {
            try {
                iIIIiiiII_Class285.II_field_49 = Integer.parseInt(scoreboardObjective.getDisplayName().getString().split("-")[1].trim());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if (scoreboardObjective.getDisplayName().getString().contains("\u0413\u0420\u0418\u0424") && iIIIiiiII_Class285.I_method_456510cb(iIIIiiIii_Class284.II_field_ac164e6c)) {
            try {
                iIIIiiiII_Class285.Ii_field_49 = Integer.parseInt(scoreboardObjective.getDisplayName().getString().split("#")[1].trim());
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        if ((iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class)).isEnabled() && iIiIiIIiI_Class83.i_method_b8484850().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderPortalOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void renderPortalOverlayHook(DrawContext drawContext, float f, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.Ii_method_98b4ecd3().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @ModifyArgs(method={"renderMiscOverlays"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/InGameHud;renderOverlay(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/util/Identifier;F)V", ordinal=0))
    private void onRenderPumpkinOverlay(Args args) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.iiI_method_1ec4e0d0().isSelected()) {
            args.set(2, (Object)Float.valueOf(0.0f));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Inject(method={"render"}, at={@At(value="HEAD")})
    public void triggerPreHudRenderEvent(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_40ef6817(0);
        CustomDrawContext customDrawContext = CustomDrawContext.of(drawContext);
        iIiiIIiiI_Class359.II_method_5031d522();
        try {
            iIiiIIiii_Class360.I_field_6425294c.i_method_affcedf();
            VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new PreHudRenderEvent(customDrawContext, renderTickCounter.getTickDelta(false)));
        }
        finally {
            iIiiIIiiI_Class359.Ii_method_50406102();
        }
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    public void triggerPostHudRenderEvent(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        CustomDrawContext customDrawContext = CustomDrawContext.of(drawContext);
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new PostHudRenderEvent(customDrawContext, renderTickCounter.getTickDelta(false)));
        drawContext.draw();
        iIiiiiIii_Class380.I_method_5f4b621f();
        IIIiIiIi_Class22.I_method_40ef6817(1);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void velum$renderDrawCalls(CustomDrawContext customDrawContext) {
        IIiIIi_Class10 iIiIIi_Class10 = IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(8.0f);
        String string = "Draw calls: " + iIiiIIiiI_Class359.I_method_1319e612();
        float f = 4.0f;
        float f2 = iIiIIi_Class10.I_method_2c375926(string) + f * 2.0f;
        float f3 = iIiIIi_Class10.I_method_a649725c() + f * 2.0f;
        float f4 = 4.0f;
        float f5 = 4.0f;
        iIiiIIiiI_Class359.II_method_5031d522();
        try {
            iiIIIIiii_Class392 iiIIIIiii_Class3922 = new iiIIIIiii_Class392(iIiIIi_Class10.I_method_36d49b4b(), 3.0f);
            iiIIIIiii_Class3922.I_method_97950030(customDrawContext.getMatrices().peek().getPositionMatrix(), f4, f5, f2, f3, new ColorRGBA(12.0f, 12.0f, 12.0f, 180.0f));
            iiIIIIiii_Class3922.I_method_2cf0d2ea(customDrawContext.getMatrices().peek().getPositionMatrix(), string, iIiIIi_Class10.iI_method_24b6c285(), f4 + f, f5 + f, 0.0f, ColorRGBA.WHITE.getRGB());
            iiIIIIiii_Class3922.I_method_874ac61f();
        }
        finally {
            iIiiIIiiI_Class359.Ii_method_50406102();
        }
    }

    @Inject(method={"renderMainHud"}, at={@At(value="HEAD")})
    private void velum$shiftVanillaMainHud(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        float f = IiIiIiIIi_Class170.I_method_d7c8c66f();
        if (f == 0.0f) {
            return;
        }
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(0.0f, -f, 0.0f);
        this.velum$mainHudShifted = true;
    }

    @Inject(method={"renderMainHud"}, at={@At(value="TAIL")})
    private void triggerHudRenderEvent(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (this.velum$mainHudShifted) {
            this.velum$mainHudShifted = false;
            drawContext.getMatrices().pop();
        }
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        CustomDrawContext customDrawContext = CustomDrawContext.of(drawContext);
        IiIIiiIii_Class156.I_field_5a = false;
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new HudRenderEvent(customDrawContext, renderTickCounter.getTickDelta(false)));
    }

    @Inject(method={"renderHotbar"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$hideVanillaHotbar(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        if (VelumClient.getInstance().I_method_35687482() == null) {
            return;
        }
        IiIiIiIIi_Class170 iiIiIiIIi_Class170 = VelumClient.getInstance().I_method_35687482().I_method_3a63b5();
        if (iiIiIiIIi_Class170 != null && iiIiIiIIi_Class170.isShowing() && iiIiIiIIi_Class170.show()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderStatusBars"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$hideVanillaStatusBars(DrawContext drawContext, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        if (VelumClient.getInstance().I_method_35687482() == null) {
            return;
        }
        IiIiIiIIi_Class170 iiIiIiIIi_Class170 = VelumClient.getInstance().I_method_35687482().I_method_3a63b5();
        if (iiIiIiIIi_Class170 != null && iiIiIiIIi_Class170.isShowing() && iiIiIiIIi_Class170.show()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderExperienceBar"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$hideVanillaExpBar(DrawContext drawContext, int n, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        if (VelumClient.getInstance().I_method_35687482() == null) {
            return;
        }
        IiIiIiIIi_Class170 iiIiIiIIi_Class170 = VelumClient.getInstance().I_method_35687482().I_method_3a63b5();
        if (iiIiIiIIi_Class170 != null && iiIiIiIIi_Class170.isShowing() && iiIiIiIIi_Class170.show()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderExperienceLevel"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$hideVanillaExpLevel(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        if (VelumClient.getInstance().I_method_35687482() == null) {
            return;
        }
        IiIiIiIIi_Class170 iiIiIiIIi_Class170 = VelumClient.getInstance().I_method_35687482().I_method_3a63b5();
        if (iiIiIiIIi_Class170 != null && iiIiIiIIi_Class170.isShowing() && iiIiIiIIi_Class170.show()) {
            callbackInfo.cancel();
            return;
        }
        float f = IiIiIiIIi_Class170.I_method_d7c8c66f();
        if (f == 0.0f) {
            return;
        }
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(0.0f, -f, 0.0f);
        this.velum$expLevelShifted = true;
    }

    @Inject(method={"renderExperienceLevel"}, at={@At(value="RETURN")})
    private void velum$unshiftVanillaExpLevel(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (!this.velum$expLevelShifted) {
            return;
        }
        this.velum$expLevelShifted = false;
        drawContext.getMatrices().pop();
    }

    @Inject(method={"renderStatusEffectOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$hideStatusEffects(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        if (VelumClient.getInstance().I_method_35687482() == null) {
            return;
        }
        boolean bl = VelumClient.getInstance().I_method_35687482().III_method_812fc188().stream().filter(iiIiIIiII_Class165 -> iiIiIIiII_Class165 instanceof IiIiIiIiI_Class171).anyMatch(iiIiIIiII_Class165 -> iiIiIIiII_Class165.isShowing() && iiIiIIiII_Class165.show());
        if (bl) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderMiscOverlays"}, at={@At(value="TAIL")})
    private void velum$renderFakeFrozenOverlay(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        ClientPlayerEntity clientPlayerEntity = InGameHudMixin.I_field_3a9bda27.player;
        if (!(clientPlayerEntity instanceof FakeFrozenTicksAccess)) {
            return;
        }
        FakeFrozenTicksAccess fakeFrozenTicksAccess = (FakeFrozenTicksAccess)clientPlayerEntity;
        int n = fakeFrozenTicksAccess.velum$getFakeFrozenTicks();
        if (n <= 0) {
            return;
        }
        float f = Math.min(1.0f, (float)n / (float)InGameHudMixin.I_field_3a9bda27.player.getMinFreezeDamageTicks());
        ((InGameHudAccessor)((Object)this)).velum$renderOverlay(drawContext, field_27960, f);
    }
}

