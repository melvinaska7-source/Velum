package moscow.velum.mixin.minecraft.client.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import velum.client.RemovalsModule;
import velum.client.VelumClient;

@Mixin(value={ParticleManager.class})
public abstract class ParticleManagerMixin {
    @Inject(method={"addBlockBreakParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddBlockBreakParticles(BlockPos blockPos, BlockState blockState, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.III_method_bfd4dcd0().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"addBlockBreakingParticles"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddBlockBreakingParticles(BlockPos blockPos, Direction direction, CallbackInfo callbackInfo) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.III_method_bfd4dcd0().isSelected()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"addParticle"}, at={@At(value="HEAD")}, cancellable=true)
    private void onAddParticle(ParticleEffect particleEffect, double d, double d2, double d3, double d4, double d5, double d6, CallbackInfoReturnable<Particle> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.isEnabled() && iIiIiIIiI_Class83.IIII_method_37aa6c93().isSelected() && particleEffect.getType() == ParticleTypes.RAIN) {
            callbackInfoReturnable.cancel();
        }
    }
}

