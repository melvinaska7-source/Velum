package moscow.rockstar.mixin.minecraft.client;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import rockstar.client.AmbienceModule;
import rockstar.client.RockstarClient;
import rockstar.client.IiiiiIIIi_Class242;

@Mixin(value={ClientWorld.class})
public class ClientWorldMixin {
    @Inject(method={"getSkyColor"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetSkyColor(Vec3d vec3d, float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        AmbienceModule iIiIIIiIi_Class70 = RockstarClient.getInstance().getModuleManager().getModule(AmbienceModule.class);
        if (iIiIIIiIi_Class70.isEnabled() && iIiIIIiIi_Class70.I_method_d8b45030().isSelected()) {
            callbackInfoReturnable.setReturnValue((iIiIIIiIi_Class70.I_method_26f48695().i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e().getRGB() : iIiIIIiIi_Class70.I_method_28a872b5().I_method_bde5f08e().getRGB()));
        }
    }

    @Inject(method={"getCloudsColor"}, at={@At(value="HEAD")}, cancellable=true)
    private void onGetCloudsColor(float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        AmbienceModule iIiIIIiIi_Class70 = RockstarClient.getInstance().getModuleManager().getModule(AmbienceModule.class);
        if (iIiIIIiIi_Class70.isEnabled() && iIiIIIiIi_Class70.i_method_debc4c10().isSelected()) {
            callbackInfoReturnable.setReturnValue((iIiIIIiIi_Class70.I_method_26f48695().i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e().getRGB() : iIiIIIiIi_Class70.i_method_3e3de695().I_method_bde5f08e().getRGB()));
        }
    }
}

