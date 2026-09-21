package velum.client;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyvelum.events.window.MouseEvent;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
        name = "Auto Explosion",
        III_method_a89e5834 = "modules.descriptions.auto_explosion",
        category = ModuleCategory.COMBAT
)
public class AutoExplosionModule extends Module {

    /** Максимальная дистанция взаимодействия */
    private static final double REACH = 4.5;
    /** Сколько тиков ждать появления кристалла, прежде чем сдаться */
    private static final int PLACE_WAIT_TICKS = 40;
    /** Задержка между установкой и ударом */
    private static final int ATTACK_DELAY_TICKS = 2;

    // --- Состояние ---
    private BlockPos targetPos;
    private boolean crystalPlaced;
    private int placeTicks;
    private int attackDelayTicks;

    private final iiIiIIiii_Class424 placeCooldown = new iiIiIIiii_Class424();
    private final iiIiIIiii_Class424 attackCooldown = new iiIiIIiii_Class424();

    // --- Слушатель ПКМ ---
    private final IiIIIiII_Class69<MouseEvent> mouseListener = event -> {
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.world == null) return;
        if (I_field_3a9bda27.currentScreen != null) return;

        // ПКМ нажатие. Если не срабатывает — поменяй 1 на 0 в getAction().
        if (event.getButton() != 1 || event.getAction() != 1) return;

        ItemStack held = I_field_3a9bda27.player.getMainHandStack();
        if (!held.isEmpty() && !(held.getItem() instanceof SwordItem)) return;

        if (!(I_field_3a9bda27.crosshairTarget instanceof BlockHitResult hit)) return;

        BlockPos obsidian = hit.getBlockPos();
        if (!I_field_3a9bda27.world.getBlockState(obsidian).isOf(Blocks.OBSIDIAN)) return;

        BlockPos above = obsidian.up();
        if (!I_field_3a9bda27.world.getBlockState(above).isAir()) return;

        this.targetPos = above.toImmutable();
        this.crystalPlaced = false;
        this.placeTicks = 0;
        this.attackDelayTicks = 0;
    };

    @Override
    public void onEnable() {
        super.onEnable();
        // Если базовый Module не подписывает листенеры сам — раскомментируй:
        // EventBus.subscribe(mouseListener);

        reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        // EventBus.unsubscribe(mouseListener);

        reset();
    }

    @Override
    @Compile(obfuscation = 4)
    public void II_method_6642fd22() {
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.world == null) {
            super.II_method_6642fd22();
            return;
        }
        if (this.targetPos == null) {
            super.II_method_6642fd22();
            return;
        }

        // Обсидиан должен быть на месте
        if (!I_field_3a9bda27.world.getBlockState(this.targetPos.down()).isOf(Blocks.OBSIDIAN)) {
            reset();
            super.II_method_6642fd22();
            return;
        }

        // Дистанция
        Vec3d center = new Vec3d(
                this.targetPos.getX() + 0.5,
                this.targetPos.getY() + 0.5,
                this.targetPos.getZ() + 0.5
        );
        if (I_field_3a9bda27.player.getEyePos().distanceTo(center) > REACH + 1.0) {
            reset();
            super.II_method_6642fd22();
            return;
        }

        // ---------- ФАЗА 1: ставим кристалл ----------
        if (!this.crystalPlaced) {
            this.placeTicks++;

            if (this.placeTicks > PLACE_WAIT_TICKS) {
                reset();
                super.II_method_6642fd22();
                return;
            }

            if (this.placeCooldown.I_method_58432069(100L)) {
                int slot = findCrystalSlot();
                if (slot == -1) {
                    reset();
                    super.II_method_6642fd22();
                    return;
                }
                placeCrystal(slot, this.targetPos);
                this.placeCooldown.I_method_23e11e3f();
                this.crystalPlaced = true;
                this.attackDelayTicks = 0;
                this.attackCooldown.I_method_23e11e3f();
            }

            super.II_method_6642fd22();
            return;
        }

        // ---------- ФАЗА 2: ждём и бьём ----------
        this.attackDelayTicks++;

        EndCrystalEntity crystal = findCrystalNear(this.targetPos);
        if (crystal == null) {
            if (this.attackDelayTicks > PLACE_WAIT_TICKS) {
                reset();
            }
            super.II_method_6642fd22();
            return;
        }

        if (this.attackDelayTicks < ATTACK_DELAY_TICKS) {
            super.II_method_6642fd22();
            return;
        }
        if (I_field_3a9bda27.player.getAttackCooldownProgress(1.0F) < 1.0F) {
            super.II_method_6642fd22();
            return;
        }
        if (!this.attackCooldown.I_method_58432069(100L)) {
            super.II_method_6642fd22();
            return;
        }

        // Ротация на кристалл
        Vec3d aim = crystal.getPos().add(0.0, 0.5, 0.0);
        float[] rot = calculateRotations(aim);
        VelumClient.getInstance().I_method_58785402()
                .I_method_a10b10c7(new iiIIiIIii_Class404(rot[0], rot[1]));

        // Удар
        I_field_3a9bda27.interactionManager.attackEntity(I_field_3a9bda27.player, crystal);
        I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);

        this.attackCooldown.I_method_23e11e3f();
        reset();

        super.II_method_6642fd22();
    }

    // ------------------------------------------------------------------
    //  Helpers
    // ------------------------------------------------------------------

    private void reset() {
        this.targetPos = null;
        this.crystalPlaced = false;
        this.placeTicks = 0;
        this.attackDelayTicks = 0;
    }

    private EndCrystalEntity findCrystalNear(BlockPos pos) {
        Box box = new Box(pos).expand(1.5, 2.0, 1.5);
        EndCrystalEntity closest = null;
        double closestSq = Double.MAX_VALUE;

        for (Entity entity : I_field_3a9bda27.world.getOtherEntities(null, box)) {
            if (!(entity instanceof EndCrystalEntity crystal)) continue;
            if (!crystal.isAlive()) continue;

            double d = crystal.squaredDistanceTo(I_field_3a9bda27.player);
            if (d > (REACH + 1.0) * (REACH + 1.0)) continue;
            if (d < closestSq) {
                closestSq = d;
                closest = crystal;
            }
        }
        return closest;
    }

    /** Слот с энд-кристаллом: 0..8 хотбар, 9..35 инвентарь */
    private int findCrystalSlot() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = I_field_3a9bda27.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() == Items.END_CRYSTAL) return i;
        }
        return -1;
    }

    private void placeCrystal(int slot, BlockPos abovePos) {
        int prevSlot = I_field_3a9bda27.player.getInventory().selectedSlot;
        int originalSlot = slot;
        boolean didSwap = false;

        // Кристалл в основном инвентаре → свопаем в текущий хотбар-слот
        if (slot >= 9 && slot <= 35) {
            I_field_3a9bda27.interactionManager.clickSlot(
                    I_field_3a9bda27.player.playerScreenHandler.syncId,
                    slot, prevSlot, SlotActionType.SWAP, I_field_3a9bda27.player
            );
            slot = prevSlot;
            didSwap = true;
        }

        // Переключаемся, если кристалл в другом слоте хотбара
        if (slot != prevSlot) {
            I_field_3a9bda27.player.getInventory().selectedSlot = slot;
            I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(slot));
        }

        // Вектор клика — центр верхней грани обсидиана
        BlockPos clickPos = abovePos.down();
        Vec3d hitVec = new Vec3d(
                clickPos.getX() + 0.5,
                clickPos.getY() + 1.0,
                clickPos.getZ() + 0.5
        );

        float[] rot = calculateRotations(hitVec);
        VelumClient.getInstance().I_method_58785402()
                .I_method_a10b10c7(new iiIIiIIii_Class404(rot[0], rot[1]));

        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, clickPos, false);
        I_field_3a9bda27.interactionManager.interactBlock(
                I_field_3a9bda27.player, Hand.MAIN_HAND, hit
        );
        I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);

        // Возвращаем хотбар-слот
        if (slot != prevSlot) {
            I_field_3a9bda27.player.getInventory().selectedSlot = prevSlot;
            I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(prevSlot));
        }

        // Обратный swap — вернуть кристалл в его слот
        if (didSwap) {
            I_field_3a9bda27.interactionManager.clickSlot(
                    I_field_3a9bda27.player.playerScreenHandler.syncId,
                    originalSlot, prevSlot, SlotActionType.SWAP, I_field_3a9bda27.player
            );
        }
    }

    private float[] calculateRotations(Vec3d target) {
        Vec3d eyes = I_field_3a9bda27.player.getEyePos();
        double dx = target.x - eyes.x;
        double dy = target.y - eyes.y;
        double dz = target.z - eyes.z;
        double horizontal = Math.sqrt(dx * dx + dz * dz);

        float yaw = (float) Math.toDegrees(Math.atan2(dz, dx)) - 90.0F;
        float pitch = (float) (-Math.toDegrees(Math.atan2(dy, horizontal)));

        float curYaw = I_field_3a9bda27.player.getYaw();
        float curPitch = I_field_3a9bda27.player.getPitch();

        return new float[]{
                curYaw + MathHelper.wrapDegrees(yaw - curYaw),
                curPitch + MathHelper.wrapDegrees(pitch - curPitch)
        };
    }
}