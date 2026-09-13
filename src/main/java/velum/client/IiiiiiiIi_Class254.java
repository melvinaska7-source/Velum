package velum.client;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;

/**
 * Client-only player entity used by local training features.
 * Movement is fed through the normal LivingEntity/PlayerEntity movement
 * pipeline instead of teleporting the entity every tick.
 */
public class IiiiiiiIi_Class254 extends OtherClientPlayerEntity {
    public static final String I_field_523beb0a = "13371337-1337-abcd-ef00-deadbeef1337";

    public IiiiiiiIi_Class254(ClientWorld clientWorld, GameProfile gameProfile) {
        super(clientWorld, gameProfile);
    }

    public void I_method_5351321f() {
        this.unsetRemoved();
        this.clientWorld.addEntity((Entity)this);
    }

    public void i_method_535fbdff() {
        this.clientWorld.removeEntity(this.getId(), Entity.RemovalReason.DISCARDED);
        this.onRemoved();
    }

    /** Feeds normal player-style movement input for the next movement tick. */
    public void I_method_TrainerInput(float forward, float sideways, boolean jumping, boolean sprinting) {
        this.forwardSpeed = Math.max(-1.0F, Math.min(1.0F, forward));
        this.sidewaysSpeed = Math.max(-1.0F, Math.min(1.0F, sideways));
        this.setJumping(jumping);
        this.setSprinting(sprinting);
    }

    public void I_method_TrainerStop() {
        this.forwardSpeed = 0.0F;
        this.sidewaysSpeed = 0.0F;
        this.setJumping(false);
        this.setSprinting(false);
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        // Trainer is local and must not be physically knocked around by
        // client-side visual combat events.
    }
}
