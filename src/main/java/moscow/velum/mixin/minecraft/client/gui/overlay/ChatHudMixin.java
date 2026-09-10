package moscow.velum.mixin.minecraft.client.gui.overlay;

import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import velum.client.BeautifullyModule;
import velum.client.IiiiIiiII_Class237;

@Mixin(value={ChatHud.class})
public abstract class ChatHudMixin {
    @Unique
    private static final float VELUM_FADE_TICKS = 4.0f;
    @Unique
    private static final float VELUM_SHIFT = 12.0f;
    @Unique
    private static final float VELUM_BAR_HEIGHT = 16.0f;
    @Shadow
    @Final
    private List<ChatHudLine.Visible> field_2064;
    @Shadow
    private int field_2066;
    @Unique
    private static boolean velum$animate;
    @Unique
    private static float velum$tick;
    @Unique
    private static boolean velum$focused;
    @Unique
    private static boolean velum$wasFocused;
    @Unique
    private static long velum$openStart;
    @Unique
    private static long velum$closeStart;
    @Unique
    private static int velum$focusedLines;
    @Unique
    private static float velum$lineOpacity;
    @Unique
    private static float velum$lineAlpha;
    @Unique
    private static float velum$lineShift;
    @Unique
    private static int velum$linesBefore;
    @Unique
    private static float velum$slideLines;
    @Unique
    private static long velum$slideStart;
    @Unique
    private static boolean velum$shifted;

    @Shadow
    public abstract int method_44752();

    @Shadow
    public abstract double method_1814();

    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="HEAD")})
    private void velum$rememberLineCount(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
        velum$linesBefore = this.field_2064.size();
    }

    @Inject(method={"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at={@At(value="TAIL")})
    private void velum$startSlide(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
        if (!BeautifullyModule.IiI_method_b163c683() || this.field_2066 > 0) {
            return;
        }
        int n = this.field_2064.size() - velum$linesBefore;
        if (n <= 0) {
            return;
        }
        velum$slideLines = Math.min(4, n);
        velum$slideStart = System.currentTimeMillis();
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void velum$beginRender(DrawContext drawContext, int n, int n2, int n3, boolean bl, CallbackInfo callbackInfo) {
        velum$animate = BeautifullyModule.IiI_method_b163c683();
        velum$tick = (float)n + MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
        velum$focused = bl;
        velum$lineOpacity = 1.0f;
        velum$lineAlpha = 1.0f;
        velum$lineShift = 0.0f;
        velum$shifted = false;
        if (bl != velum$wasFocused) {
            velum$wasFocused = bl;
            if (bl) {
                velum$openStart = System.currentTimeMillis();
            } else {
                velum$closeStart = System.currentTimeMillis();
            }
        }
        if (!velum$animate || velum$slideStart == Long.MIN_VALUE) {
            return;
        }
        float f = (float)(System.currentTimeMillis() - velum$slideStart) / 200.0f;
        if (f >= 1.0f || f < 0.0f) {
            return;
        }
        float f2 = 1.0f - IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
        float f3 = f2 * velum$slideLines * this.method_44752() * (float)this.method_1814();
        if (f3 <= 0.05f) {
            return;
        }
        drawContext.enableScissor(0, 0, drawContext.getScaledWindowWidth(), drawContext.getScaledWindowHeight() - 40);
        drawContext.getMatrices().push();
        drawContext.getMatrices().translate(0.0f, f3, 0.0f);
        velum$shifted = true;
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void velum$endRender(DrawContext drawContext, int n, int n2, int n3, boolean bl, CallbackInfo callbackInfo) {
        if (velum$shifted) {
            velum$shifted = false;
            drawContext.getMatrices().pop();
            drawContext.disableScissor();
        }
        this.velum$drawClosingInput(drawContext);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Ljava/util/List;get(I)Ljava/lang/Object;", ordinal=0))
    private Object velum$captureLine(List<ChatHudLine.Visible> list, int n) {
        float f;
        ChatHudLine.Visible visible = list.get(n);
        velum$lineOpacity = 1.0f;
        velum$lineAlpha = 1.0f;
        velum$lineShift = 0.0f;
        if (!velum$animate || !(visible instanceof ChatHudLine.Visible)) {
            return visible;
        }
        ChatHudLine.Visible visible2 = visible;
        float f2 = ChatHudMixin.velum$life(velum$tick - (float)visible2.addedTime());
        if (velum$focused) {
            velum$lineAlpha = f = MathHelper.lerp((float)ChatHudMixin.velum$openProgress(), (float)f2, (float)1.0f);
        } else {
            velum$lineOpacity = f = Math.max(f2, ChatHudMixin.velum$closeFade());
        }
        velum$lineShift = (1.0f - f) * 12.0f;
        return visible;
    }

    @ModifyConstant(method={"render"}, constant={@Constant(intValue=200)})
    private int velum$keepClosingLines(int n) {
        if (!velum$animate || velum$focused || ChatHudMixin.velum$closeFade() <= 0.0f) {
            return n;
        }
        return Integer.MAX_VALUE;
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHud;getMessageOpacityMultiplier(I)D"))
    private static double velum$messageOpacity(int n) {
        if (!velum$animate) {
            return ChatHudMixin.velum$life(n);
        }
        return velum$lineOpacity;
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/hud/ChatHud;getVisibleLineCount()I"))
    private int velum$visibleLineCount(ChatHud chatHud) {
        int n = chatHud.getVisibleLineCount();
        if (velum$focused) {
            velum$focusedLines = n;
            return n;
        }
        if (!velum$animate || ChatHudMixin.velum$closeFade() <= 0.0f) {
            return n;
        }
        return Math.max(n, velum$focusedLines);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal=0))
    private void velum$fillLine(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        this.velum$fillAnimated(drawContext, n, n2, n3, n4, n5);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;fill(IIIII)V", ordinal=1))
    private void velum$fillIndicator(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        this.velum$fillAnimated(drawContext, n, n2, n3, n4, n5);
    }

    @Redirect(method={"render"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I", ordinal=0))
    private int velum$drawLine(DrawContext drawContext, TextRenderer textRenderer, OrderedText orderedText, int n, int n2, int n3) {
        if (!ChatHudMixin.velum$lineAnimated()) {
            return drawContext.drawTextWithShadow(textRenderer, orderedText, n, n2, n3);
        }
        int n4 = ChatHudMixin.velum$fade(n3);
        if (ChatHudMixin.velum$invisible(n4)) {
            return 0;
        }
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.translate(-velum$lineShift, 0.0f, 0.0f);
        int n5 = drawContext.drawTextWithShadow(textRenderer, orderedText, n, n2, n4);
        matrixStack.translate(velum$lineShift, 0.0f, 0.0f);
        return n5;
    }

    @Unique
    private void velum$fillAnimated(DrawContext drawContext, int n, int n2, int n3, int n4, int n5) {
        if (!ChatHudMixin.velum$lineAnimated()) {
            drawContext.fill(n, n2, n3, n4, n5);
            return;
        }
        int n6 = ChatHudMixin.velum$fade(n5);
        if (ChatHudMixin.velum$invisible(n6)) {
            return;
        }
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.translate(-velum$lineShift, 0.0f, 0.0f);
        drawContext.fill(n, n2, n3, n4, n6);
        matrixStack.translate(velum$lineShift, 0.0f, 0.0f);
    }

    @Unique
    private void velum$drawClosingInput(DrawContext drawContext) {
        if (!velum$animate || velum$focused) {
            return;
        }
        float f = ChatHudMixin.velum$closeFade();
        if (f <= 0.0f) {
            return;
        }
        int n = drawContext.getScaledWindowWidth();
        int n2 = drawContext.getScaledWindowHeight();
        int n3 = MinecraftClient.getInstance().options.getTextBackgroundColor(Integer.MIN_VALUE);
        int n4 = MathHelper.clamp((int)((int)((float)(n3 >>> 24) * f)), (int)0, (int)255);
        MatrixStack matrixStack = drawContext.getMatrices();
        matrixStack.push();
        matrixStack.translate(0.0f, (1.0f - f) * 16.0f, 0.0f);
        drawContext.fill(2, n2 - 14, n - 2, n2 - 2, n3 & 0xFFFFFF | n4 << 24);
        matrixStack.pop();
    }

    @Unique
    private static boolean velum$lineAnimated() {
        return velum$lineShift != 0.0f || velum$lineAlpha < 1.0f;
    }

    @Unique
    private static float velum$life(float f) {
        float f2 = MathHelper.clamp((float)((200.0f - f) / 4.0f), (float)0.0f, (float)1.0f);
        return f2 * f2;
    }

    @Unique
    private static float velum$openProgress() {
        if (velum$openStart == Long.MIN_VALUE) {
            return 1.0f;
        }
        float f = (float)(System.currentTimeMillis() - velum$openStart) / 200.0f;
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        return IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
    }

    @Unique
    private static float velum$closeFade() {
        if (velum$closeStart == Long.MIN_VALUE) {
            return 0.0f;
        }
        float f = (float)(System.currentTimeMillis() - velum$closeStart) / 200.0f;
        if (f <= 0.0f) {
            return 1.0f;
        }
        if (f >= 1.0f) {
            return 0.0f;
        }
        return 1.0f - IiiiIiiII_Class237.IIII_field_dd60aac.ease(f, 0.0f, 1.0f, 1.0f);
    }

    @Unique
    private static boolean velum$invisible(int n) {
        return (n & 0xFC000000) == 0;
    }

    @Unique
    private static int velum$fade(int n) {
        if (velum$lineAlpha >= 1.0f) {
            return n;
        }
        int n2 = MathHelper.clamp((int)((int)((float)(n >>> 24) * velum$lineAlpha)), (int)0, (int)255);
        return n & 0xFFFFFF | n2 << 24;
    }

    static {
        velum$openStart = Long.MIN_VALUE;
        velum$closeStart = Long.MIN_VALUE;
        velum$lineOpacity = 1.0f;
        velum$lineAlpha = 1.0f;
        velum$slideStart = Long.MIN_VALUE;
    }
}
