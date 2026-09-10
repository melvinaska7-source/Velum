package moscow.velum.mixin.minecraft.world;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.LightmapTextureManager;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import velum.client.AmbienceModule;
import velum.client.RemovalsModule;
import velum.client.VelumClient;

@Mixin(value={LightmapTextureManager.class})
public class MixinLightmapTextureManager {
    @Shadow
    @Final
    private SimpleFramebuffer field_53101;
    @Shadow
    private boolean field_4135;
    @Unique
    private boolean velum$nightWasActive;

    @Inject(method={"update"}, at={@At(value="HEAD")})
    private void velum$forceNightModeRefresh(float f, CallbackInfo callbackInfo) {
        boolean bl;
        AmbienceModule iIiIIIiIi_Class70 = VelumClient.getInstance().getModuleManager().getModule(AmbienceModule.class);
        boolean bl2 = bl = iIiIIIiIi_Class70 != null && iIiIIIiIi_Class70.Iii_method_28e47243();
        if (bl || this.velum$nightWasActive) {
            this.field_4135 = true;
        }
        this.velum$nightWasActive = bl;
    }

    @Inject(method={"getDarknessFactor"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetDarknessFactor(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.iIi_method_69d55c90().isSelected()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
        }
    }

    @Inject(method={"update"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gl/SimpleFramebuffer;beginWrite(Z)V")})
    private void velum$applyNightModeUniforms(float f, CallbackInfo callbackInfo, @Local ShaderProgram shaderProgram) {
        AmbienceModule iIiIIIiIi_Class70 = VelumClient.getInstance().getModuleManager().getModule(AmbienceModule.class);
        if (iIiIIIiIi_Class70 == null || !iIiIIIiIi_Class70.Iii_method_28e47243()) {
            shaderProgram.getUniformOrDefault("VelumNightStrength").set(0.0f);
            shaderProgram.getUniformOrDefault("VelumNightTint").set(1.0f, 1.0f, 1.0f);
            return;
        }
        Vector3f vector3f = iIiIIIiIi_Class70.I_method_f1088e3a();
        float f2 = iIiIIIiIi_Class70.I_method_d49e05cf();
        shaderProgram.getUniformOrDefault("VelumNightTint").set(vector3f);
        shaderProgram.getUniformOrDefault("VelumNightStrength").set(f2);
    }
}

