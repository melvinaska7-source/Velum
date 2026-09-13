package velum.client;

import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyvelum.events.game.AttackEvent;

@ModuleInfo(
    name = "Trainer",
    category = ModuleCategory.COMBAT,
    III_method_a89e5834 = "modules.descriptions.trainer"
)
public final class TrainerModule extends Module {
    private static final UUID TRAINER_UUID = UUID.nameUUIDFromBytes("Velum-Trainer".getBytes(StandardCharsets.UTF_8));
    private static TrainerModule INSTANCE;

    private final ModeSetting difficulty = new ModeSetting(this, "modules.settings.trainer.difficulty");
    private final ModeSetting.Nested1_42765c60 easy = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.easy").select();
    private final ModeSetting.Nested1_42765c60 medium = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.medium");
    private final ModeSetting.Nested1_42765c60 hard = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.hard");
    private final ModeSetting.Nested1_42765c60 cheater = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.cheater");
    private final SliderSetting rotationSpeed = new SliderSetting(
        this,
        "modules.settings.trainer.rotation_speed",
        () -> difficulty.I_method_ce989e26(cheater)
    ).I_method_c8c9a7d7(1.0F).i_method_65e2aff7(30.0F).II_method_b0f56334(0.5F).Ii_method_4e0e6b54(8.0F).I_method_d41e7abf("deg/s");
    private final TextSetting bestRecord = new TextSetting(this, "modules.settings.trainer.best_record")
        .I_method_2dbacd5a(14).I_method_ec08f9eb(true).I_method_104de33f("Ваш рекорд: --:--:--");

    private IiiiiiiIi_Class254 trainer;
    private boolean awakened;
    private int totemsRemaining;
    private long startTime;
    private long lastSwing;
    private int deathDelay;
    private double behaviorAngle;
    private int behaviorTicks;
    private boolean strafeLeft;

    private final IiIIIiII_Class69<AttackEvent> attackListener = event -> {
        if (!isEnabled() || trainer == null || event.getEntity() != trainer || trainer.hurtTime != 0 || deathDelay > 0) {
            return;
        }

        if (!awakened) {
            awakened = true;
            startTime = System.currentTimeMillis();
        }

        playHitAnimation();

        if (totemsRemaining <= 0) {
            return;
        }

        totemsRemaining--;

        if (totemsRemaining > 0) {
            trainer.setHealth(0.0F);
            new EntityStatusS2CPacket(trainer, (byte) 35).apply(I_field_3a9bda27.player.networkHandler);
            trainer.setHealth(20.0F);
            updateTotemVisual();
            return;
        }

        trainer.setHealth(0.0F);
        I_field_3a9bda27.world.playSound(
            I_field_3a9bda27.player,
            trainer.getX(), trainer.getY(), trainer.getZ(),
            SoundEvents.ENTITY_PLAYER_DEATH,
            SoundCategory.PLAYERS,
            1.0F,
            1.0F
        );
        updateTotemVisual();
        updateBestRecord();
        deathDelay = 12;
    };

    public TrainerModule() {
        INSTANCE = this;
    }

    public static boolean isTrainer(Entity entity) {
        return INSTANCE != null && entity != null && entity == INSTANCE.trainer;
    }

    public static boolean isRunning() {
        return INSTANCE != null && INSTANCE.isEnabled() && INSTANCE.trainer != null;
    }

    public static String timerText() {
        if (INSTANCE == null || !INSTANCE.awakened) {
            return "00:00:00";
        }
        return INSTANCE.formatTime(System.currentTimeMillis() - INSTANCE.startTime);
    }

    public static String currentTimer() {
        return timerText();
    }

    /** Event-bus accessor. */
    public IiIIIiII_Class69<AttackEvent> getTrainerAttackListener() {
        return attackListener;
    }

    @Override
    public void onEnable() {
        if (I_field_3a9bda27.world == null || I_field_3a9bda27.player == null) {
            disable();
            return;
        }

        removeTrainer();
        awakened = false;
        totemsRemaining = 10;
        startTime = 0L;
        lastSwing = 0L;
        deathDelay = 0;
        behaviorAngle = 0.0D;
        behaviorTicks = 0;
        strafeLeft = true;

        GameProfile profile = new GameProfile(TRAINER_UUID, I_field_3a9bda27.player.getGameProfile().getName());
        profile.getProperties().putAll(I_field_3a9bda27.player.getGameProfile().getProperties());
        trainer = new IiiiiiiIi_Class254(I_field_3a9bda27.world, profile);

        Vec3d forward = I_field_3a9bda27.player.getRotationVec(1.0F);
        Vec3d pos = I_field_3a9bda27.player.getPos().add(forward.x * 2.8D, 0.0D, forward.z * 2.8D);
        trainer.refreshPositionAndAngles(pos.x, pos.y, pos.z, I_field_3a9bda27.player.getYaw() + 180.0F, 0.0F);
        trainer.setYaw(trainer.getYaw());
        trainer.setHeadYaw(trainer.getYaw());
        trainer.setBodyYaw(trainer.getYaw());
        trainer.setHealth(20.0F);
        trainer.setVelocity(Vec3d.ZERO);
        equipLoadout();
        updateTotemVisual();
        I_field_3a9bda27.world.addEntity(trainer);
    }

    @Override
    public void onDisable() {
        removeTrainer();
        awakened = false;
        totemsRemaining = 0;
        startTime = 0L;
        deathDelay = 0;
    }

    @Override
    public void II_method_6642fd22() {
        if (trainer == null || I_field_3a9bda27.world == null || I_field_3a9bda27.player == null) {
            return;
        }

        if (deathDelay > 0) {
            deathDelay--;
            if (deathDelay == 0) {
                disable();
            }
            return;
        }

        if (trainer.isRemoved()) {
            disable();
            return;
        }

        if (!awakened) {
            trainer.setVelocity(new Vec3d(0.0D, trainer.getVelocity().y, 0.0D));
            smoothLookAt(I_field_3a9bda27.player.getX(), I_field_3a9bda27.player.getY() + 1.45D, I_field_3a9bda27.player.getZ(), 8.0F);
            return;
        }

        updateBehavior();
        updateVisualAttack();
    }

    private void updateBehavior() {
        boolean isEasy = difficulty.I_method_ce989e26(easy);
        boolean isMedium = difficulty.I_method_ce989e26(medium);
        boolean isHard = difficulty.I_method_ce989e26(hard);
        boolean isCheater = difficulty.I_method_ce989e26(cheater);

        Vec3d playerPos = I_field_3a9bda27.player.getPos();
        Vec3d botPos = trainer.getPos();
        double dx = playerPos.x - botPos.x;
        double dz = playerPos.z - botPos.z;
        double distance = Math.sqrt(dx * dx + dz * dz);

        behaviorTicks++;
        if (behaviorTicks % (isEasy ? 38 : isMedium ? 24 : 12) == 0) {
            strafeLeft = !strafeLeft;
            behaviorAngle += (isEasy ? 0.20D : isMedium ? 0.42D : 0.70D);
        }

        if (isCheater) {
            updateCheaterMovement(playerPos, botPos);
            return;
        }

        double desiredDistance = isEasy ? 2.8D : isMedium ? 2.45D : 2.20D;
        double forwardAmount = distance > desiredDistance + 0.35D ? 1.0D : distance < desiredDistance - 0.55D ? -0.45D : 0.0D;
        double strafeAmount = isEasy ? Math.sin(behaviorTicks * 0.045D) * 0.25D
            : (strafeLeft ? 1.0D : -1.0D) * (isMedium ? 0.70D : 1.0D);

        double length = Math.max(distance, 0.001D);
        double nx = dx / length;
        double nz = dz / length;
        double px = -nz;
        double pz = nx;

        double moveX = nx * forwardAmount + px * strafeAmount;
        double moveZ = nz * forwardAmount + pz * strafeAmount;
        double moveLength = Math.sqrt(moveX * moveX + moveZ * moveZ);

        if (moveLength > 0.001D) {
            moveX /= moveLength;
            moveZ /= moveLength;
        }

        double speed = isEasy ? 0.085D : isMedium ? 0.105D : 0.125D;
        if (isHard && trainer.isSprinting()) {
            speed = 0.145D;
        }

        if (trainer.horizontalCollision && trainer.isOnGround()) {
            jump(0.42D);
        } else if (trainer.isOnGround() && shouldJump(isEasy, isMedium, isHard)) {
            jump(0.42D);
        }

        if (moveLength <= 0.001D) {
            trainer.setVelocity(new Vec3d(0.0D, trainer.getVelocity().y, 0.0D));
            trainer.setSprinting(false);
        } else {
            trainer.move(
                MovementType.SELF,
                new Vec3d(moveX * speed, trainer.getVelocity().y, moveZ * speed)
            );
            trainer.setSprinting(isHard || isMedium && distance > 4.0D);
        }

        smoothLookAt(playerPos.x, playerPos.y + 1.15D, playerPos.z, isEasy ? 9.0F : isMedium ? 13.0F : 18.0F);
    }

    private void updateCheaterMovement(Vec3d playerPos, Vec3d botPos) {
        double speedDegrees = rotationSpeed.Ii_method_a20abcd2();
        behaviorAngle += Math.toRadians(speedDegrees) / 20.0D;

        double radius = 2.15D;
        double targetX = playerPos.x + Math.cos(behaviorAngle) * radius;
        double targetZ = playerPos.z + Math.sin(behaviorAngle) * radius;
        double dx = targetX - botPos.x;
        double dz = targetZ - botPos.z;
        double length = Math.sqrt(dx * dx + dz * dz);
        if (length > 0.001D) {
            double speed = 0.145D;
            trainer.move(MovementType.SELF, new Vec3d(dx / length * speed, trainer.getVelocity().y, dz / length * speed));
        }

        trainer.setSprinting(true);
        if (trainer.isOnGround() && behaviorTicks % 11 == 0) {
            jump(0.42D);
        }
        smoothLookAt(playerPos.x, playerPos.y + 1.2D, playerPos.z, (float)(speedDegrees / 20.0D + 4.0D));
    }

    private boolean shouldJump(boolean easy, boolean medium, boolean hard) {
        int interval = easy ? 85 : medium ? 48 : 30;
        return behaviorTicks % interval == 0 && Math.abs(trainer.getVelocity().y) < 0.08D;
    }

    private void jump(double strength) {
        trainer.setVelocity(trainer.getVelocity().x, strength, trainer.getVelocity().z);
    }

    private void smoothLookAt(double x, double y, double z, float maxTurn) {
        double dx = x - trainer.getX();
        double dy = y - trainer.getEyeY();
        double dz = z - trainer.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        if (horizontal < 0.0001D) {
            return;
        }

        float targetYaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0D);
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, horizontal)));
        float yaw = approachAngle(trainer.getYaw(), targetYaw, maxTurn);
        float pitch = approachAngle(trainer.getPitch(), targetPitch, Math.max(3.0F, maxTurn * 0.65F));

        trainer.setYaw(yaw);
        trainer.setPitch(pitch);
        trainer.setHeadYaw(yaw);
        trainer.setBodyYaw(approachAngle(trainer.getBodyYaw(), yaw, Math.max(4.0F, maxTurn * 0.55F)));
    }

    private float approachAngle(float current, float target, float maxChange) {
        float delta = MathHelper.wrapDegrees(target - current);
        return MathHelper.wrapDegrees(current + MathHelper.clamp(delta, -maxChange, maxChange));
    }

    private void updateVisualAttack() {
        long now = System.currentTimeMillis();
        boolean easy = difficulty.I_method_ce989e26(this.easy);
        boolean medium = difficulty.I_method_ce989e26(this.medium);
        long interval = easy ? 950L : medium ? 680L : difficulty.I_method_ce989e26(this.hard) ? 470L : 330L;

        if (now - lastSwing < interval || trainer.squaredDistanceTo(I_field_3a9bda27.player) > 3.4D * 3.4D) {
            return;
        }

        boolean critical = !trainer.isOnGround() && trainer.getVelocity().y < 0.0D;
        trainer.swingHand(Hand.MAIN_HAND);
        I_field_3a9bda27.world.playSound(
            I_field_3a9bda27.player,
            trainer.getX(), trainer.getY(), trainer.getZ(),
            critical ? SoundEvents.ENTITY_PLAYER_ATTACK_CRIT : SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP,
            SoundCategory.PLAYERS,
            0.85F,
            critical ? 1.05F : 1.0F
        );

        if (critical) {
            for (int i = 0; i < 6; i++) {
                I_field_3a9bda27.world.addParticle(
                    ParticleTypes.CRIT,
                    trainer.getX() + (Math.random() - 0.5D) * 0.7D,
                    trainer.getBodyY(0.65D) + Math.random() * 0.8D,
                    trainer.getZ() + (Math.random() - 0.5D) * 0.7D,
                    (Math.random() - 0.5D) * 0.12D,
                    0.08D,
                    (Math.random() - 0.5D) * 0.12D
                );
            }
        }

        lastSwing = now;
    }

    private void playHitAnimation() {
        I_field_3a9bda27.world.playSound(
            I_field_3a9bda27.player,
            trainer.getX(), trainer.getY(), trainer.getZ(),
            SoundEvents.ENTITY_PLAYER_HURT,
            SoundCategory.PLAYERS,
            1.0F,
            1.0F
        );
        trainer.onDamaged(I_field_3a9bda27.world.getDamageSources().generic());
    }

    private void equipLoadout() {
        boolean diamond = difficulty.I_method_ce989e26(easy);
        trainer.equipStack(EquipmentSlot.HEAD, armor(diamond ? Items.DIAMOND_HELMET : Items.NETHERITE_HELMET));
        trainer.equipStack(EquipmentSlot.CHEST, armor(diamond ? Items.DIAMOND_CHESTPLATE : Items.NETHERITE_CHESTPLATE));
        trainer.equipStack(EquipmentSlot.LEGS, armor(diamond ? Items.DIAMOND_LEGGINGS : Items.NETHERITE_LEGGINGS));
        trainer.equipStack(EquipmentSlot.FEET, armor(diamond ? Items.DIAMOND_BOOTS : Items.NETHERITE_BOOTS));
        trainer.equipStack(EquipmentSlot.MAINHAND, new ItemStack(diamond ? Items.DIAMOND_SWORD : Items.NETHERITE_SWORD));

        if (difficulty.I_method_ce989e26(cheater)) {
            trainer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 2, false, false, true));
        }
    }

    private ItemStack armor(Item item) {
        ItemStack stack = new ItemStack(item);
        stack.set(DataComponentTypes.UNBREAKABLE, new UnbreakableComponent(true));

        if (I_field_3a9bda27.world != null) {
            RegistryEntry<Enchantment> protection = I_field_3a9bda27.world.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT)
                .getEntry(Enchantments.PROTECTION.getValue())
                .orElse(null);
            if (protection != null) {
                ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(
                    stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT)
                );
                builder.set(protection, 4);
                stack.set(DataComponentTypes.ENCHANTMENTS, builder.build());
            }
        }
        return stack;
    }

    private void updateTotemVisual() {
        if (trainer == null) {
            return;
        }
        trainer.equipStack(EquipmentSlot.OFFHAND, totemsRemaining > 0 ? new ItemStack(Items.TOTEM_OF_UNDYING) : ItemStack.EMPTY);
    }

    private void updateBestRecord() {
        if (!awakened) {
            return;
        }
        long elapsed = System.currentTimeMillis() - startTime;
        long oldMillis = parseRecord(bestRecord.II_method_da016c1e());
        if (oldMillis <= 0L || elapsed < oldMillis) {
            bestRecord.I_method_79680a89("Ваш рекорд: " + formatTime(elapsed));
        }
    }

    private long parseRecord(String value) {
        if (value == null) {
            return 0L;
        }
        int p = value.lastIndexOf(' ');
        if (p < 0) {
            return 0L;
        }
        String[] parts = value.substring(p + 1).split(":");
        if (parts.length != 3) {
            return 0L;
        }
        try {
            return Long.parseLong(parts[0]) * 60000L
                + Long.parseLong(parts[1]) * 1000L
                + Long.parseLong(parts[2]) * 10L;
        } catch (NumberFormatException ignored) {
            return 0L;
        }
    }

    private String formatTime(long millis) {
        long minutes = millis / 60000L;
        long seconds = (millis / 1000L) % 60L;
        long centiseconds = (millis / 10L) % 100L;
        return String.format("%02d:%02d:%02d", minutes, seconds, centiseconds);
    }

    private void removeTrainer() {
        if (trainer != null) {
            trainer.i_method_535fbdff();
            trainer = null;
        }
    }
}
