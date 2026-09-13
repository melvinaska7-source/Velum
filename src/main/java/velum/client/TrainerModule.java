package velum.client;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import net.minecraft.block.BlockState;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
    private final SliderSetting rotationSpeed = new SliderSetting(this, "modules.settings.trainer.rotation_speed", () -> difficulty.I_method_ce989e26(cheater))
        .I_method_c8c9a7d7(1.0F).i_method_65e2aff7(30.0F).II_method_b0f56334(0.5F).Ii_method_4e0e6b54(8.0F)
        .I_method_d41e7abf("deg/s");
    private final ReadOnlyInfoSetting bestRecord = new ReadOnlyInfoSetting(
        this,
        "modules.settings.trainer.best_record",
        "Ваш рекорд: --:--:--"
    );

    private IiiiiiiIi_Class254 trainer;
    private boolean awakened;
    private int totemsRemaining;
    private float simulatedHealth;
    private long startTime;
    private double orbitAngle;
    private long lastSwing;
    private long nextJumpAt;
    private long lastStuckCheck;
    private double lastX;
    private double lastZ;
    private int stuckTicks;
    private long totemPulseUntil;


    /** Called directly from ClientPlayerInteractionManagerMixin after an entity attack. */
    public static void onDirectAttack(net.minecraft.entity.Entity entity) {
        if (INSTANCE == null || !INSTANCE.isEnabled()) return;
        INSTANCE.handleAttack(entity);
    }

    private void handleAttack(net.minecraft.entity.Entity entity) {
        if (trainer == null || entity != trainer) return;

        if (!awakened) {
            awakened = true;
            startTime = System.currentTimeMillis();
            simulatedHealth = 20.0F;
        }

        if (simulatedHealth > 0.0F) {
            double attackDamage = I_field_3a9bda27.player == null
                ? 1.0D
                : I_field_3a9bda27.player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE);
            float damage = (float)MathHelper.clamp(attackDamage, 1.0D, 20.0D);
            simulatedHealth -= damage;
        }

        if (simulatedHealth <= 0.0F) {
            simulatedHealth = 20.0F;
            totemsRemaining--;
            showTotemPulse();
            if (totemsRemaining <= 0) {
                finish(true);
            }
        }
    }

    public TrainerModule() {
        INSTANCE = this;
    }

    public static boolean isTrainer(net.minecraft.entity.Entity entity) {
        return INSTANCE != null && entity != null && entity == INSTANCE.trainer;
    }

    public static boolean isRunning() {
        return INSTANCE != null && INSTANCE.isEnabled() && INSTANCE.trainer != null;
    }

    public static String timerText() {
        if (INSTANCE == null || !INSTANCE.awakened) return "00:00:00";
        return INSTANCE.formatTime(System.currentTimeMillis() - INSTANCE.startTime);
    }

    public static String currentTimer() {
        return timerText();
    }

    @Override
    public void onEnable() {
        if (I_field_3a9bda27.world == null || I_field_3a9bda27.player == null) {
            disable();
            return;
        }

        if (trainer != null) trainer.discard();
        awakened = false;
        totemsRemaining = 10;
        simulatedHealth = 20.0F;
        startTime = 0L;
        orbitAngle = 0.0D;
        lastSwing = 0L;
        nextJumpAt = 0L;
        lastStuckCheck = System.currentTimeMillis();
        lastX = I_field_3a9bda27.player.getX();
        lastZ = I_field_3a9bda27.player.getZ();
        stuckTicks = 0;
        totemPulseUntil = 0L;

        com.mojang.authlib.GameProfile profile = new com.mojang.authlib.GameProfile(
            TRAINER_UUID,
            I_field_3a9bda27.player.getGameProfile().getName()
        );
        profile.getProperties().putAll(I_field_3a9bda27.player.getGameProfile().getProperties());

        trainer = new IiiiiiiIi_Class254(I_field_3a9bda27.world, profile);
        Vec3d pos = I_field_3a9bda27.player.getPos().add(2.8D, 0.0D, 0.0D);
        trainer.refreshPositionAndAngles(pos.x, pos.y, pos.z, I_field_3a9bda27.player.getYaw() + 180.0F, 0.0F);
        trainer.setHealth(20.0F);
        trainer.setMovementSpeed(0.10000000149F);
        trainer.I_method_TrainerStop();
        equipArmor();
        trainer.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        I_field_3a9bda27.world.addEntity(trainer);
        updateBestRecordDisplay();
    }

    @Override
    public void onDisable() {
        if (trainer != null) {
            trainer.I_method_TrainerStop();
            trainer.discard();
            trainer = null;
        }
        awakened = false;
        totemsRemaining = 0;
        simulatedHealth = 0.0F;
        startTime = 0L;
    }

    @Override
    public void II_method_6642fd22() {
        if (trainer == null || I_field_3a9bda27.world == null || I_field_3a9bda27.player == null) return;
        if (trainer.isRemoved()) {
            finish(false);
            return;
        }

        if (!awakened) {
            trainer.I_method_TrainerStop();
            trainer.setYaw(I_field_3a9bda27.player.getYaw() + 180.0F);
            trainer.setHeadYaw(trainer.getYaw());
            trainer.setBodyYaw(trainer.getYaw());
            return;
        }

        updateMovement();
        updateLook();
        if (totemPulseUntil > 0L && System.currentTimeMillis() >= totemPulseUntil) {
            trainer.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            totemPulseUntil = 0L;
        }
        updateBestRecordDisplay();
    }

    private void updateMovement() {
        Vec3d target = I_field_3a9bda27.player.getPos();
        Vec3d bot = trainer.getPos();
        double dx = target.x - bot.x;
        double dz = target.z - bot.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        long now = System.currentTimeMillis();

        boolean isEasy = difficulty.I_method_ce989e26(easy);
        boolean isMedium = difficulty.I_method_ce989e26(medium);
        boolean isHard = difficulty.I_method_ce989e26(hard);
        boolean isCheater = difficulty.I_method_ce989e26(cheater);

        float forward = 0.0F;
        float sideways = 0.0F;
        boolean sprint = false;
        boolean jump = false;

        if (isCheater) {
            orbitAngle += Math.toRadians(rotationSpeed.Ii_method_a20abcd2()) / 20.0D;
            double radius = 2.35D;
            double desiredX = target.x + Math.cos(orbitAngle) * radius;
            double desiredZ = target.z + Math.sin(orbitAngle) * radius;
            Vec3d desired = new Vec3d(desiredX - bot.x, 0.0D, desiredZ - bot.z);
            double len = Math.sqrt(desired.x * desired.x + desired.z * desired.z);
            if (len > 0.05D) {
                Vec3d direction = desired.multiply(1.0D / len);
                float[] input = worldRelativeInput(direction.x, direction.z, trainer.getYaw());
                forward = MathHelper.clamp(input[0], -1.0F, 1.0F);
                sideways = MathHelper.clamp(input[1], -1.0F, 1.0F);
            }
            sprint = true;
        } else {
            double preferred = isEasy ? 3.0D : isMedium ? 2.8D : 2.55D;
            if (distance > preferred + 0.35D) {
                Vec3d direction = new Vec3d(dx, 0.0D, dz).normalize();
                float[] input = worldRelativeInput(direction.x, direction.z, trainer.getYaw());
                forward = input[0];
                sideways = isEasy ? 0.0F : (float)(Math.sin(now / (isMedium ? 420.0D : 260.0D)) * (isMedium ? 0.45D : 0.70D));
                sprint = !isEasy;
            } else if (distance < preferred - 0.45D) {
                Vec3d direction = new Vec3d(-dx, 0.0D, -dz).normalize();
                float[] input = worldRelativeInput(direction.x, direction.z, trainer.getYaw());
                forward = input[0] * (isHard ? 0.75F : 0.55F);
                sideways = isHard ? (float)(Math.sin(now / 300.0D) * 0.45D) : 0.0F;
                sprint = false;
            } else {
                float strafe = isEasy ? 0.0F : (float)(Math.sin(now / (isMedium ? 520.0D : 340.0D)) * (isMedium ? 0.35D : 0.60D));
                sideways = strafe;
                forward = isHard ? 0.12F : 0.0F;
                sprint = isHard;
            }
        }

        if (shouldJump(dx, dz, distance, now)) {
            jump = true;
            nextJumpAt = now + (isEasy ? 900L : isMedium ? 550L : 360L);
        }

        if (now - lastStuckCheck >= 350L) {
            double moved = Math.hypot(trainer.getX() - lastX, trainer.getZ() - lastZ);
            if (Math.abs(forward) + Math.abs(sideways) > 0.25F && moved < 0.035D) {
                stuckTicks++;
            } else {
                stuckTicks = Math.max(0, stuckTicks - 1);
            }
            lastX = trainer.getX();
            lastZ = trainer.getZ();
            lastStuckCheck = now;
        }

        if (stuckTicks >= 2 && trainer.isOnGround()) {
            jump = true;
            nextJumpAt = now + 350L;
            stuckTicks = 0;
        }

        float movement = isEasy ? 0.085F : isMedium ? 0.105F : 0.125F;
        if (isCheater) movement = 0.135F;
        trainer.setMovementSpeed(movement);
        trainer.I_method_TrainerInput(forward, sideways, jump, sprint);
    }

    private boolean shouldJump(double dx, double dz, double distance, long now) {
        if (!trainer.isOnGround() || now < nextJumpAt) return false;
        if (distance < 1.5D) return false;
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len < 0.001D) return false;

        double lookX = dx / len;
        double lookZ = dz / len;
        BlockPos ahead = BlockPos.ofFloored(
            trainer.getX() + lookX * 0.72D,
            trainer.getY() + 0.05D,
            trainer.getZ() + lookZ * 0.72D
        );
        BlockState feet = I_field_3a9bda27.world.getBlockState(ahead);
        BlockState head = I_field_3a9bda27.world.getBlockState(ahead.up());
        BlockState aboveHead = I_field_3a9bda27.world.getBlockState(ahead.up(2));
        return !feet.isAir() && head.isAir() && aboveHead.isAir();
    }

    private float[] worldRelativeInput(double worldX, double worldZ, float yaw) {
        double radians = Math.toRadians(yaw);
        double forwardX = -Math.sin(radians);
        double forwardZ = Math.cos(radians);
        double rightX = Math.cos(radians);
        double rightZ = Math.sin(radians);
        float forward = (float)(worldX * forwardX + worldZ * forwardZ);
        float sideways = (float)(worldX * rightX + worldZ * rightZ);
        float length = MathHelper.sqrt(forward * forward + sideways * sideways);
        if (length > 1.0F) {
            forward /= length;
            sideways /= length;
        }
        return new float[]{forward, sideways};
    }

    private void updateLook() {
        Vec3d target = I_field_3a9bda27.player.getPos().add(0.0D, 1.35D, 0.0D);
        double dx = target.x - trainer.getX();
        double dy = target.y - trainer.getEyeY();
        double dz = target.z - trainer.getZ();
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        float targetYaw = (float)(Math.toDegrees(Math.atan2(-dx, dz)));
        float targetPitch = (float)(-Math.toDegrees(Math.atan2(dy, Math.max(horizontal, 0.001D))));

        float turn = difficulty.I_method_ce989e26(cheater)
            ? Math.max(10.0F, rotationSpeed.Ii_method_a20abcd2() / 20.0F)
            : difficulty.I_method_ce989e26(hard) ? 14.0F : difficulty.I_method_ce989e26(medium) ? 10.0F : 7.0F;

        float yaw = trainer.getYaw() + MathHelper.wrapDegrees(targetYaw - trainer.getYaw());
        yaw = MathHelper.lerp(MathHelper.clamp(turn / 180.0F, 0.0F, 1.0F), trainer.getYaw(), yaw);
        float pitch = MathHelper.clamp(targetPitch, -65.0F, 65.0F);
        pitch = MathHelper.lerp(0.25F, trainer.getPitch(), pitch);

        trainer.setYaw(yaw);
        trainer.setHeadYaw(yaw);
        trainer.setBodyYaw(MathHelper.lerp(0.35F, trainer.getBodyYaw(), yaw));
        trainer.setPitch(pitch);
    }


    private void equipArmor() {
        boolean diamond = difficulty.I_method_ce989e26(easy);
        trainer.equipStack(EquipmentSlot.HEAD, armor(diamond ? Items.DIAMOND_HELMET : Items.NETHERITE_HELMET));
        trainer.equipStack(EquipmentSlot.CHEST, armor(diamond ? Items.DIAMOND_CHESTPLATE : Items.NETHERITE_CHESTPLATE));
        trainer.equipStack(EquipmentSlot.LEGS, armor(diamond ? Items.DIAMOND_LEGGINGS : Items.NETHERITE_LEGGINGS));
        trainer.equipStack(EquipmentSlot.FEET, armor(diamond ? Items.DIAMOND_BOOTS : Items.NETHERITE_BOOTS));
        if (difficulty.I_method_ce989e26(cheater)) {
            trainer.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, Integer.MAX_VALUE, 2, false, false, true));
        }
    }

    private ItemStack armor(net.minecraft.item.Item item) {
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

    private void showTotemPulse() {
        if (trainer == null) return;
        totemPulseUntil = System.currentTimeMillis() + 650L;
        trainer.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
    }

    private void updateBestRecordDisplay() {
        if (!awakened) return;
        long elapsed = System.currentTimeMillis() - startTime;
        String current = bestRecord.getValue();
        long oldMillis = parseRecord(current);
        if (oldMillis > 0L && elapsed >= oldMillis) return;
        if (oldMillis <= 0L) {
            return;
        }
    }

    private void finish(boolean killed) {
        if (killed && awakened) {
            long elapsed = System.currentTimeMillis() - startTime;
            long oldMillis = parseRecord(bestRecord.getValue());
            if (oldMillis <= 0L || elapsed < oldMillis) {
                bestRecord.setValue("Ваш рекорд: " + formatTime(elapsed));
            }
        }
        disable();
    }

    private long parseRecord(String value) {
        if (value == null) return 0L;
        int p = value.lastIndexOf(' ');
        if (p < 0) return 0L;
        String t = value.substring(p + 1);
        String[] parts = t.split(":");
        if (parts.length != 3) return 0L;
        try {
            return Long.parseLong(parts[0]) * 60000L + Long.parseLong(parts[1]) * 1000L + Long.parseLong(parts[2]) * 10L;
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
}
