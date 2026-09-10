package moscow.velum.mixin.minecraft.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import pyvelum.events.game.FireworkEvent;
import velum.client.VelumClient;
import velum.client.iIIiIIiIi_Class294;
import velum.client.iiIIiIIii_Class404;
import velum.client.iiIIiIiII_Class405;
import velum.client.iiIIiiIII_Class409;

@Mixin(value={FireworkRocketEntity.class})
public abstract class FireworkRocketEntityMixin
implements iIIiIIiIi_Class294 {
    @Redirect(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"))
    private void redirectSetVelocity(LivingEntity livingEntity, Vec3d vec3d) {
        FireworkRocketEntity fireworkRocketEntity = (FireworkRocketEntity)(Object)this;
        FireworkEvent fireworkEvent = new FireworkEvent(livingEntity, vec3d, fireworkRocketEntity);
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(fireworkEvent);
        livingEntity.setVelocity(fireworkEvent.getVelocity());
    }

    @Redirect(method={"tick"}, at=@At(value="INVOKE", target="Lnet/minecraft/entity/LivingEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"))
    private Vec3d redirectGetRotationVector(LivingEntity livingEntity) {
        iiIIiIiII_Class405 iiIIiIiII_Class4052;
        if (livingEntity == FireworkRocketEntityMixin.I_field_3a9bda27.player && (iiIIiIiII_Class4052 = VelumClient.getInstance().I_method_58785402()) != null && iiIIiIiII_Class4052.I_method_fcdfeef5() != iiIIiiIII_Class409.I_field_34a3b28c) {
            iiIIiIIii_Class404 iiIIiIIii_Class4042 = iiIIiIiII_Class4052.II_method_f098f858();
            return Vec3d.fromPolar((float)iiIIiIIii_Class4042.i_method_1461d9ef(), (float)iiIIiIIii_Class4042.I_method_14534e0f());
        }
        return livingEntity.getRotationVector();
    }
}

