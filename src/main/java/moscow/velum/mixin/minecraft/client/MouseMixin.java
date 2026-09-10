package moscow.velum.mixin.minecraft.client;

import net.minecraft.client.Mouse;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pyvelum.events.window.MouseButtonEvent;
import pyvelum.events.window.MouseEvent;
import pyvelum.events.window.MouseMoveEvent;
import pyvelum.events.window.MouseScrollEvent;
import pyvelum.events.window.ScrollEvent;
import velum.client.VelumClient;
import velum.client.iIIIiIIIi_Class274;
import velum.client.iIIIiIIiI_Class275;
import velum.client.iIIiIIiIi_Class294;

@Mixin(value={Mouse.class})
public class MouseMixin
implements iIIiIIiIi_Class294 {
    @Unique
    private double velum$lastCursorX;
    @Unique
    private double velum$lastCursorY;

    @Inject(method={"tick()V"}, at={@At(value="RETURN")})
    private void tick(CallbackInfo callbackInfo) {
        if (iIIIiIIiI_Class275.I_method_696f36b5() != iIIIiIIiI_Class275.i_method_7f04aa95()) {
            GLFW.glfwSetCursor((long)I_field_3a9bda27.getWindow().getHandle(), (long)iIIIiIIiI_Class275.I_method_696f36b5().I_method_a72b15d3());
        }
        iIIIiIIiI_Class275.i_method_84ed4087(iIIIiIIiI_Class275.I_method_696f36b5());
        iIIIiIIiI_Class275.I_method_e7d43867(iIIIiIIIi_Class274.I_field_aa52e62c);
    }

    @Inject(method={"onMouseButton"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseButton(long l, int n, int n2, int n3, CallbackInfo callbackInfo) {
        if (n2 == 1) {
            if (GLFW.glfwGetKey((long)l, (int)292) == 1) {
                return;
            }
            VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new MouseEvent(n, n2));
        }
        MouseButtonEvent mouseButtonEvent = new MouseButtonEvent(n, n2, n3);
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(mouseButtonEvent);
        if (mouseButtonEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"onMouseScroll"}, at={@At(value="HEAD")}, cancellable=true)
    private void onMouseScroll(long l, double d, double d2, CallbackInfo callbackInfo) {
        if (d2 != 0.0) {
            VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new MouseScrollEvent(d2));
        }
        ScrollEvent scrollEvent = new ScrollEvent(d, d2);
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(scrollEvent);
        if (scrollEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"onCursorPos"}, at={@At(value="HEAD")})
    private void onCursorPos(long l, double d, double d2, CallbackInfo callbackInfo) {
        if (l != I_field_3a9bda27.getWindow().getHandle()) {
            return;
        }
        double d3 = d - this.velum$lastCursorX;
        double d4 = d2 - this.velum$lastCursorY;
        this.velum$lastCursorX = d;
        this.velum$lastCursorY = d2;
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new MouseMoveEvent(d, d2, d3, d4));
    }
}

