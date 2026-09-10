package moscow.velum.mixin.minecraft.client.gui.screen;

import java.util.Optional;
import java.util.function.Consumer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import velum.client.III;
import velum.client.IiIIiIiii_Class152;
import velum.client.VelumClient;
import velum.client.IiiiIiIii_Class236;
import velum.client.IiiiIiiII_Class237;
import velum.client.IiiiiIIIi_Class242;
import velum.client.iIIiIIiIi_Class294;
import velum.client.iIIiIIiiI_Class295;
import velum.client.iIiiIIiiI_Class359;

@Mixin(value={SplashOverlay.class})
public class SplashOverlayMixin
implements iIIiIIiIi_Class294,
iIIiIIiiI_Class295 {
    @Unique
    private IiIIiIiii_Class152 daunGif;
    @Unique
    private IiiiIiIii_Class236 fadeOutAnimation;
    @Shadow
    private long field_17771;
    @Final
    @Shadow
    private Consumer<Optional<Throwable>> field_18218;
    @Shadow
    @Final
    private ResourceReload field_17767;
    @Shadow
    @Final
    private boolean field_18219;
    @Shadow
    private long field_18220;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void init(MinecraftClient minecraftClient, ResourceReload resourceReload, Consumer<Optional<Throwable>> consumer, boolean bl, CallbackInfo callbackInfo) {
        if (VelumClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        this.daunGif = new IiIIiIiii_Class152(VelumClient.id("gifs/loading.gif"), 100.0f, 100.0f, 100.0f, 100.0f);
        this.fadeOutAnimation = new IiiiIiIii_Class236(3000L, 1.0f, IiiiIiiII_Class237.IIIi_field_dd60aac);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Inject(method={"render"}, at={@At(value="HEAD")}, cancellable=true)
    private void replaceRendering(DrawContext drawContext, int n, int n2, float f, CallbackInfo callbackInfo) {
        float f2;
        if (VelumClient.getInstance().i_method_e8604970()) {
            return;
        }
        callbackInfo.cancel();
        int n3 = drawContext.getScaledWindowWidth();
        int n4 = drawContext.getScaledWindowHeight();
        III iII = III.I_method_5728d20f(drawContext, 0, 0, f);
        long l = Util.getMeasuringTimeMs();
        if (this.field_18219 && this.field_18220 == -1L) {
            this.field_18220 = l;
        }
        float f3 = this.field_17771 > -1L ? (float)(l - this.field_17771) / 1000.0f : -1.0f;
        float f4 = f2 = this.field_18220 > -1L ? (float)(l - this.field_18220) / 500.0f : -1.0f;
        if (f3 >= 1.0f) {
            if (SplashOverlayMixin.I_field_3a9bda27.currentScreen != null) {
                SplashOverlayMixin.I_field_3a9bda27.currentScreen.render(drawContext, 0, 0, f);
            }
            int n5 = MathHelper.ceil((float)((1.0f - MathHelper.clamp((float)(f3 - 1.0f), (float)0.0f, (float)1.0f)) * 255.0f));
            drawContext.fill(RenderLayer.getGuiOverlay(), 0, 0, n3, n4, IiiiiIIIi_Class242.iI_field_d0c8ec5.withAlpha(n5).getRGB());
        } else if (this.field_18219 && SplashOverlayMixin.I_field_3a9bda27.currentScreen != null && f2 < 1.0f) {
            SplashOverlayMixin.I_field_3a9bda27.currentScreen.render(drawContext, n, n2, f);
            int n6 = MathHelper.ceil((double)(MathHelper.clamp((double)f2, (double)0.15, (double)1.0) * 255.0));
            drawContext.fill(RenderLayer.getGuiOverlay(), 0, 0, n3, n4, IiiiiIIIi_Class242.iI_field_d0c8ec5.withAlpha(n6).getRGB());
        }
        if (f3 < 1.0f) {
            float f5;
            float f6;
            float f7;
            float f8;
            float f9;
            float f10;
            float f11 = I_field_73c6d26c.I_method_804ee20f();
            float f12 = f11 / (f10 = I_field_73c6d26c.i_method_805d6def());
            if (f12 > (f9 = 1.7777778f)) {
                f8 = f11;
                f7 = f11 / f9;
                f6 = 0.0f;
                f5 = (f10 - f7) / 2.0f;
            } else {
                f7 = f10;
                f8 = f10 * f9;
                f6 = (f11 - f8) / 2.0f;
                f5 = 0.0f;
            }
            this.daunGif.I_method_b043bcc(f6, f5, f8, f7);
            this.daunGif.iI_method_dc161abe(1.0f);
            iIiiIIiiI_Class359.II_method_5031d522();
            try {
                this.daunGif.I_method_7ae26dda(iII);
            }
            finally {
                iIiiIIiiI_Class359.Ii_method_50406102();
            }
        }
        if (f3 >= 2.0f) {
            I_field_3a9bda27.setOverlay(null);
            this.daunGif.i_method_d4ce41ff();
        }
        if (this.field_17771 == -1L && this.field_17767.isComplete() && (!this.field_18219 || f2 >= 2.0f)) {
            try {
                this.field_17767.throwException();
                this.field_18218.accept(Optional.empty());
            }
            catch (Throwable throwable) {
                this.field_18218.accept(Optional.of(throwable));
            }
            this.field_17771 = l;
            if (SplashOverlayMixin.I_field_3a9bda27.currentScreen != null) {
                SplashOverlayMixin.I_field_3a9bda27.currentScreen.init(I_field_3a9bda27, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight());
            }
        }
    }
}

