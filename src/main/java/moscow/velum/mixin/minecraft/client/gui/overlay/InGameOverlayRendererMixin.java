package moscow.velum.mixin.minecraft.client.gui.overlay;

import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import velum.client.RemovalsModule;
import velum.client.VelumClient;

@Mixin(value={InGameOverlayRenderer.class})
public class InGameOverlayRendererMixin {
    @Inject(method={"renderFireOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderFireOverlayHook(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.iI_method_4da47113().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"renderInWallOverlay"}, at={@At(value="HEAD")}, cancellable=true)
    private static void renderInWallOverlayHook(Sprite sprite, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.ii_method_53ac6cf3().isSelected()) {
            callbackInfo.cancel();
        }
    }
}

