package moscow.velum.mixin.minecraft.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pyvelum.events.game.AttackEvent;
import pyvelum.events.game.PostAttackEvent;
import pyvelum.events.player.KeepSprintEvent;
import velum.client.NoPushModule;
import velum.client.VelumClient;
import velum.client.iiIIiIiII_Class405;
import velum.client.iiIIiiIIi_Class410;
import velum.client.iiIiIiIII_Class425;
import velum.client.iiIiiIiIi_Class438;
import velum.client.HitboxesModule;
import velum.client.iiiIIIiii_Class456;
import velum.client.iiiIiIIII_Class465;

@Mixin({PlayerEntity.class})
public class PlayerEntityMixin {
   @Inject(
      method = {"attack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void attackAHook2(Entity var1, CallbackInfo var2) {
      HitboxesModule var3 = VelumClient.getInstance().getModuleManager().getModule(HitboxesModule.class);
      if (var1 instanceof LivingEntity && var3.isEnabled() && var3.I_method_79b78f62().Ii_method_a20abcd2() <= 0.0F) {
         var2.cancel();
      } else {
         AttackEvent var4 = new AttackEvent(var1);
         VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(var4);
         if (var4.isCancelled()) {
            var2.cancel();
         }
      }
   }

   @Inject(
      method = {"attack"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V",
         ordinal = 0
      )},
      cancellable = true
   )
   private void onAttackSetVelocity(Entity var1, CallbackInfo var2) {
      KeepSprintEvent var3 = new KeepSprintEvent();
      VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(var3);
      if (var3.isCancelled()) {
         var2.cancel();
      }
   }

   @Inject(
      method = {"attack"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void attackAHook(Entity var1, CallbackInfo var2) {
      PostAttackEvent var3 = new PostAttackEvent(var1);
      VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(var3);
   }

   @Inject(
      method = {"isPushedByFluids"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void removePushFromFluids(CallbackInfoReturnable<Boolean> var1) {
      NoPushModule var2 = VelumClient.getInstance().getModuleManager().getModule(NoPushModule.class);
      PlayerEntity var3 = (PlayerEntity)(Object)this;
      if (var3 == MinecraftClient.getInstance().player && var2.isEnabled() && var2.i_method_6b2f8090().isSelected()) {
         var1.setReturnValue(false);
      }
   }

   @Inject(
      method = {"clipAtLedge"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void pathfinder$safewalkClipAtLedge(CallbackInfoReturnable<Boolean> var1) {
      if (!var1.getReturnValueZ()) {
         if (iiIiIiIII_Class425.I_field_5a) {
            if (iiIiiIiIi_Class438.I_method_1047da03()) {
               if ((Object)this == MinecraftClient.getInstance().player) {
                  if (iiIiiIiIi_Class438.I_method_12f562b5().I_method_d87feab5().I_method_7d4e6203()) {
                     if (!MinecraftClient.getInstance().player.isTouchingWater()) {
                        iiiIIIiii_Class456 var2 = iiIiiIiIi_Class438.I_method_12f562b5().I_method_3bb8fab5();
                        if (var2 != null) {
                           iiiIiIIII_Class465 var3 = var2.I_method_5aae9eb5();
                           if (var3 != null) {
                              if (var3.I_method_37257e03()) {
                                 if (var3.i_method_5e5afeb5().i_method_6d2ed9f2() >= var3.I_method_48c58ad5().i_method_6d2ed9f2()) {
                                    iiiIiIIII_Class465 var4 = var3.I_method_7d9daab5();
                                    if (var4 == null
                                       || var4.I_method_37257e03()
                                          && var4.i_method_5e5afeb5().i_method_6d2ed9f2() >= var4.I_method_48c58ad5().i_method_6d2ed9f2()
                                       || !var3.I_method_ad8aa223(0.85)) {
                                       var1.setReturnValue(true);
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Redirect(
      method = {"travel(Lnet/minecraft/util/math/Vec3d;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;getRotationVector()Lnet/minecraft/util/math/Vec3d;"
      )
   )
   private Vec3d redirectGetRotationVectorInTravel(PlayerEntity var1) {
      if (var1 != MinecraftClient.getInstance().player) {
         return var1.getRotationVector();
      } else {
         iiIIiIiII_Class405 var2 = VelumClient.getInstance().I_method_58785402();
         iiIIiiIIi_Class410 var3 = var2.I_method_fcdff2d5();
         return !var2.I_method_3d166e03() && var3 != null && var3.I_method_384472d5().I_method_778be603()
            ? var2.II_method_f098f858().I_method_cbcdd559()
            : var1.getRotationVector();
      }
   }
}
