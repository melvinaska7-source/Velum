package moscow.velum.mixin.minecraft.client.gui.overlay;

import moscow.velum.mixin.accessors.CameraAccessor;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pyvelum.events.render.CameraUpdateEvent;
import velum.client.FreeCameraModule;
import velum.client.RemovalsModule;
import velum.client.VelumClient;
import velum.client.iIIIiiiII_Class285;
import velum.client.iiIIiIIii_Class404;
import velum.client.AuraModule;
import velum.client.SpeedModule;

@Mixin(value={Camera.class})
public abstract class CameraMixin {
    @Shadow
    private Entity field_18711;
    @Shadow
    private boolean field_18719;
    @Shadow
    private float field_18721;
    @Shadow
    private float field_18722;
    @Unique
    private static final int velum$JITTER_FLIPS = 3;
    @Unique
    private static final int velum$RESET_TICKS = 10;
    @Unique
    private float velum$lastEyeHeight;
    @Unique
    private boolean velum$eyeHeightKnown;
    @Unique
    private float velum$heldEyeHeight;
    @Unique
    private int velum$eyeHeightFlips;
    @Unique
    private int velum$ticksSinceFlip;

    @Shadow
    public abstract void method_19322(Vec3d var1);

    @Shadow
    public abstract void method_19325(float var1, float var2);

    @Inject(method={"updateEyeHeight"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$stabilizeEyeHeight(CallbackInfo callbackInfo) {
        if (this.field_18711 == null) {
            return;
        }
        float f = CameraMixin.velum$eyeHeight(this.field_18711);
        if (!this.field_18711.isTouchingWater() || !CameraMixin.velum$auraOnHolyWorld()) {
            this.velum$eyeHeightKnown = false;
            this.velum$eyeHeightFlips = 0;
            this.velum$ticksSinceFlip = 0;
            this.velum$heldEyeHeight = f;
            this.field_18722 = this.field_18721;
            this.field_18721 += (f - this.field_18721) * 0.5f;
            callbackInfo.cancel();
            return;
        }
        if (!this.velum$eyeHeightKnown) {
            this.velum$eyeHeightKnown = true;
            this.velum$lastEyeHeight = f;
            this.velum$heldEyeHeight = f;
        }
        if (Math.abs(f - this.velum$lastEyeHeight) > 1.0E-4f) {
            this.velum$lastEyeHeight = f;
            this.velum$ticksSinceFlip = 0;
            ++this.velum$eyeHeightFlips;
        } else if (++this.velum$ticksSinceFlip >= 10) {
            this.velum$eyeHeightFlips = 0;
        }
        this.velum$heldEyeHeight = this.velum$eyeHeightFlips >= 3 ? Math.max(this.velum$heldEyeHeight, f) : f;
        this.field_18722 = this.field_18721;
        this.field_18721 += (this.velum$heldEyeHeight - this.field_18721) * 0.5f;
        callbackInfo.cancel();
    }

    @Unique
    private static boolean velum$auraOnHolyWorld() {
        AuraModule iiIiIIVelumClient102 = VelumClient.getInstance().getModuleManager().getModule(AuraModule.class);
        return iiIiIIVelumClient102 != null && iiIiIIVelumClient102.isEnabled() && iIIIiiiII_Class285.Ii_method_b349c526();
    }

    @Unique
    private static float velum$eyeHeight(Entity entity) {
        if (entity == MinecraftClient.getInstance().player && entity.getPose() == EntityPose.GLIDING && SpeedModule.Iii_method_2c01c5d0()) {
            return entity.getDimensions(EntityPose.STANDING).eyeHeight();
        }
        return entity.getStandingEyeHeight();
    }

    @Inject(method={"getSubmersionType"}, at={@At(value="HEAD")}, cancellable=true)
    private void getSubmergedFluidState(CallbackInfoReturnable<CameraSubmersionType> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83 == null || !iIiIiIIiI_Class83.isEnabled()) {
            return;
        }
        if (iIiIiIIiI_Class83.IiI_method_7acc5cf0().isSelected()) {
            callbackInfoReturnable.setReturnValue(CameraSubmersionType.NONE);
            return;
        }
        if (this.field_18719 && iIiIiIIiI_Class83.ii_method_53ac6cf3().isSelected() && !this.velum$focusedEntitySubmerged()) {
            callbackInfoReturnable.setReturnValue(CameraSubmersionType.NONE);
        }
    }

    @Unique
    private boolean velum$focusedEntitySubmerged() {
        return this.field_18711 != null && (this.field_18711.isSubmergedInWater() || this.field_18711.isInLava() || this.field_18711.inPowderSnow);
    }

    @Inject(method={"clipToSpace"}, at={@At(value="HEAD")}, cancellable=true)
    private void onClipToSpace(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        RemovalsModule iIiIiIIiI_Class83 = VelumClient.getInstance().getModuleManager().getModule(RemovalsModule.class);
        if (iIiIiIIiI_Class83.ii_method_53ac6cf3().isSelected() && iIiIiIIiI_Class83.isEnabled()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(f));
        }
    }

    @Inject(method={"update"}, at={@At(value="TAIL")})
    private void onUpdate(BlockView blockView, Entity entity, boolean bl, boolean bl2, float f, CallbackInfo callbackInfo) {
        FreeCameraModule iIIiIIiii_Class40 = VelumClient.getInstance().getModuleManager().getModule(FreeCameraModule.class);
        iIIiIIiii_Class40.IiI_method_460b62bf();
        if (iIIiIIiii_Class40.IiI_method_460b62c3()) {
            this.method_19322(iIIiIIiii_Class40.I_method_c6647d1b(f));
            iiIIiIIii_Class404 iiIIiIIii_Class4042 = iIIiIIiii_Class40.I_method_4e394bb7(f);
            this.method_19325(iiIIiIIii_Class4042.I_method_14534e0f(), iiIIiIIii_Class4042.i_method_1461d9ef());
            ((CameraAccessor)((Object)this)).setThirdPerson(true);
            return;
        }
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new CameraUpdateEvent((Camera)(Object)this, entity, bl, bl2, f));
    }
}

