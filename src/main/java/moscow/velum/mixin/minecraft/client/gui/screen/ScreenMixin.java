package moscow.velum.mixin.minecraft.client.gui.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import velum.client.IIIiIiIi_Class22;
import velum.client.VelumClient;
import velum.client.iIIiIIiIi_Class294;

@Mixin(value={Screen.class})
public class ScreenMixin
implements iIIiIIiIi_Class294 {
    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void velum$markScreenRender(CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_40efa7e8(true);
    }

    @Inject(method={"render"}, at={@At(value="RETURN")})
    private void velum$unmarkScreenRender(CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_40efa7e8(false);
    }

    @Inject(method={"handleTextClick"}, at={@At(value="HEAD")}, cancellable=true)
    private void onHandleTextClick(Style style, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        String string;
        ClickEvent clickEvent = style.getClickEvent();
        if (clickEvent != null && clickEvent.getAction() == ClickEvent.Action.RUN_COMMAND && (string = clickEvent.getValue()).startsWith(VelumClient.getInstance().I_method_b4d046cb().I_method_80ea594())) {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient.player != null) {
                VelumClient.getInstance().I_method_b4d046cb().I_method_9e86975a(string);
                callbackInfoReturnable.setReturnValue(true);
                callbackInfoReturnable.cancel();
            }
        }
    }
}

