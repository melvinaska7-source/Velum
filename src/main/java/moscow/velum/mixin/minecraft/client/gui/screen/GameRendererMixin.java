package moscow.velum.mixin.minecraft.client.gui.screen;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import pyvelum.events.render.GameRendererEvent;
import velum.client.IIIiIiIi_Class22;
import velum.client.RemovalsModule;
import velum.client.VelumClient;
import velum.client.iIiiIIiiI_Class359;
import velum.client.iIiiiiIII_Class377;
import velum.client.iiIIiIiII_Class405;

@Mixin(value={GameRenderer.class})
public abstract class GameRendererMixin {
    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;isFinishedLoading()Z", shift=At.Shift.AFTER)})
    public void triggerGameRendererEvent(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new GameRendererEvent());
    }

    @Inject(method={"render"}, at={@At(value="HEAD")})
    private void velum$resetDrawCalls(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
        iIiiIIiiI_Class359.i_method_132871ff();
    }

    @Inject(method={"renderWorld"}, at={@At(value="RETURN")})
    private void velum$applyWorldPatches(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_40ef6817(0);
    }

    @Redirect(method={"findCrosshairTarget"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/Entity;getRotationVec(F)Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d velum$useServerRotationForEntityRaytrace(Entity entity, float f) {
        if (entity != MinecraftClient.getInstance().player) {
            return entity.getRotationVec(f);
        }
        iiIIiIiII_Class405 iiIIiIiII_Class4052 = VelumClient.getInstance().I_method_58785402();
        return iiIIiIiII_Class4052.I_method_3d166e03() ? entity.getRotationVec(f) : iiIIiIiII_Class4052.II_method_f098f858().I_method_cbcdd559();
    }

    @Inject(method={"renderWorld"}, at={@At(value="INVOKE_STRING", target="Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V", args={"ldc=hand"})})
    private void onRenderWorld(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo, @Local(ordinal=0) Matrix4f matrix4f, @Local(ordinal=2) Matrix4f matrix4f2, @Local(ordinal=1) float f, @Local MatrixStack matrixStack) {
        iIiiiiIII_Class377.I_method_29c8dc1f(matrix4f2, matrix4f);
    }

    @Inject(method={"tiltViewWhenHurt"}, at={@At(value="HEAD")}, cancellable=true)
    private void tiltViewWhenHurtHook(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.I_method_b2404c70().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @Redirect(method={"renderWorld"}, at=@At(value="INVOKE", target="Lnet/minecraft/util/math/MathHelper;lerp(FFF)F"))
    private float renderWorldHook(float f, float f2, float f3) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.Iii_method_80d458d0().isSelected()) {
            return 0.0f;
        }
        return MathHelper.lerp((float)f, (float)f2, (float)f3);
    }
}

