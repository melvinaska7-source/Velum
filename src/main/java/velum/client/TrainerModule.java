package velum.client;

import java.util.UUID;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.component.type.UnbreakableComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyvelum.events.game.AfterAttackEvent;

@ModuleInfo(
    name = "Trainer",
    category = ModuleCategory.COMBAT,
    III_method_a89e5834 = "modules.descriptions.trainer"
)
public final class TrainerModule extends Module {
    private static final UUID TRAINER_UUID = UUID.nameUUIDFromBytes("Velum-Trainer".getBytes());
    private static TrainerModule INSTANCE;

    private final ModeSetting difficulty = new ModeSetting(this, "modules.settings.trainer.difficulty");
    private final ModeSetting.Nested1_42765c60 easy = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.easy").select();
    private final ModeSetting.Nested1_42765c60 medium = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.medium");
    private final ModeSetting.Nested1_42765c60 hard = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.hard");
    private final ModeSetting.Nested1_42765c60 cheater = new ModeSetting.Nested1_42765c60(difficulty, "modules.settings.trainer.difficulty.cheater");
    private final SliderSetting rotationSpeed = new SliderSetting(this, "modules.settings.trainer.rotation_speed", () -> difficulty.I_method_ce989e26(cheater))
        .I_method_c8c9a7d7(1.0F).i_method_65e2aff7(30.0F).II_method_b0f56334(0.5F).Ii_method_4e0e6b54(8.0F)
        .I_method_d41e7abf("deg/s");
    private final TextSetting bestRecord = new TextSetting(this, "modules.settings.trainer.best_record")
        .I_method_2dbacd5a(14).I_method_ec08f9eb(true).I_method_104de33f("Ваш рекорд: --:--:--");

    private final IiIIIiII_Class69<AfterAttackEvent> afterAttack = event -> {
        if (!isEnabled() || trainer == null || event.getEntity() != trainer) return;
        if (!awakened) {
            awakened = true;
            startTime = System.currentTimeMillis();
        }
        if (totemsRemaining > 0) {
            totemsRemaining--;
            updateTotemVisual();
        }
        if (totemsRemaining <= 0) {
            finish(true);
        }
    };

    private OtherClientPlayerEntity trainer;
    private boolean awakened;
    private int totemsRemaining;
    private long startTime;
    private double orbitAngle;
    private long lastSwing;

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
        startTime = 0L;
        orbitAngle = 0.0D;
        lastSwing = 0L;

        trainer = new OtherClientPlayerEntity(I_field_3a9bda27.world, new com.mojang.authlib.GameProfile(TRAINER_UUID, "VelumTrainer"));
        Vec3d pos = I_field_3a9bda27.player.getPos().add(2.5D, 0.0D, 0.0D);
        trainer.refreshPositionAndAngles(pos.x, pos.y, pos.z, I_field_3a9bda27.player.getYaw() + 180.0F, 0.0F);
        trainer.setHealth(20.0F);
        equipArmor();
        updateTotemVisual();
        I_field_3a9bda27.world.addEntity(trainer);
    }

    @Override
    public void onDisable() {
        if (trainer != null) {
            trainer.discard();
            trainer = null;
        }
        awakened = false;
        totemsRemaining = 0;
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
            trainer.setVelocity(Vec3d.ZERO);
            trainer.setYaw(I_field_3a9bda27.player.getYaw() + 180.0F);
            trainer.setHeadYaw(trainer.getYaw());
            return;
        }

        updateMovement();
        updateAttackAnimation();
    }

    private void updateMovement() {
        Vec3d player = I_field_3a9bda27.player.getPos();
        Vec3d bot = trainer.getPos();
        double dx = player.x - bot.x;
        double dz = player.z - bot.z;
        double distance = Math.sqrt(dx * dx + dz * dz);
        String mode = difficulty.I_method_ce989e26(easy) ? "easy" : difficulty.I_method_ce989e26(medium) ? "medium" : difficulty.I_method_ce989e26(hard) ? "hard" : "cheater";

        if (difficulty.I_method_ce989e26(cheater)) {
            orbitAngle += Math.toRadians(rotationSpeed.Ii_method_a20abcd2()) / 20.0D;
            double radius = 2.2D;
            double targetX = player.x + Math.cos(orbitAngle) * radius;
            double targetZ = player.z + Math.sin(orbitAngle) * radius;
            double blend = 0.18D;
            trainer.setPosition(MathHelper.lerp(blend, bot.x, targetX), player.y, MathHelper.lerp(blend, bot.z, targetZ));
            facePlayer(player.x, player.z);
            return;
        }

        double speed = mode.equals("easy") ? 0.075D : mode.equals("medium") ? 0.105D : 0.145D;
        if (distance > 2.1D) {
            double nx = dx / Math.max(distance, 0.001D);
            double nz = dz / Math.max(distance, 0.001D);
            double strafe = mode.equals("easy") ? 0.0D : Math.sin(System.currentTimeMillis() / (mode.equals("medium") ? 500.0D : 300.0D)) * (mode.equals("medium") ? 0.45D : 0.7D);
            double sx = -nz * strafe;
            double sz = nx * strafe;
            trainer.setPosition(bot.x + (nx + sx) * speed, player.y, bot.z + (nz + sz) * speed);
        }
        facePlayer(player.x, player.z);
    }

    private void facePlayer(double x, double z) {
        double dx = x - trainer.getX();
        double dz = z - trainer.getZ();
        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0D);
        trainer.setYaw(yaw);
        trainer.setHeadYaw(yaw);
        trainer.setBodyYaw(yaw);
    }

    private void updateAttackAnimation() {
        if (System.currentTimeMillis() - lastSwing < 650L) return;
        if (trainer.squaredDistanceTo(I_field_3a9bda27.player) <= 3.2D * 3.2D) {
            trainer.swingHand(net.minecraft.util.Hand.MAIN_HAND);
            lastSwing = System.currentTimeMillis();
        }
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
                .getEntry(Enchantments.PROTECTION)
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
        trainer.equipStack(EquipmentSlot.OFFHAND, totemsRemaining > 0 ? new ItemStack(Items.TOTEM_OF_UNDYING) : ItemStack.EMPTY);
    }

    private void finish(boolean killed) {
        if (killed && awakened) {
            long elapsed = System.currentTimeMillis() - startTime;
            String old = bestRecord.II_method_da016c1e();
            long oldMillis = parseRecord(old);
            if (oldMillis <= 0L || elapsed < oldMillis) {
                bestRecord.I_method_79680a89("Ваш рекорд: " + formatTime(elapsed));
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
