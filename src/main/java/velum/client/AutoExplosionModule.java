package velum.client;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
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

    private static final double REACH = 4.5;
    private static final int MAX_WAIT_TICKS = 40;

    // --- Состояние ---
    private BlockPos targetPos;
    private int state; // 0 = старт, 1 = подготовка слота, 2 = ожидание смены слота, 3 = установка, 4 = ожидание сущности, 5 = атака
    private int ticksWaited;
    private int originalSlot;
    private int crystalSlot;

    private final iiIiIIiii_Class424 actionCooldown = new iiIiIIiii_Class424();

    private final IiIIIiII_Class69<MouseEvent> mouseListener = event -> {
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.world == null) return;
        if (I_field_3a9bda27.currentScreen != null) return;

        // Триггер: ПКМ нажатие (с ЛЮБЫМ предметом в руке)
        if (event.getButton() != 1 || event.getAction() != 1) return;

        if (!(I_field_3a9bda27.crosshairTarget instanceof BlockHitResult hit)) return;

        BlockPos clickedPos = hit.getBlockPos();
        // Проверяем, что кликнули по обсидиану (можно убрать эту проверку, если нужно ставить на любой блок)
        if (!I_field_3a9bda27.world.getBlockState(clickedPos).isOf(Blocks.OBSIDIAN)) return;

        BlockPos above = clickedPos.up();
        
        // Сохраняем исходные данные
        this.targetPos = above.toImmutable();
        this.originalSlot = I_field_3a9bda27.player.getInventory().selectedSlot;
        this.ticksWaited = 0;
        
        // ПРИОРИТЕТ: Если кристалл уже есть, сразу переходим к атаке (пропускаем установку)
        if (findCrystalNear(this.targetPos) != null) {
            this.state = 5; 
        } else {
            this.state = 1; // Начинаем процесс установки
        }
        
        // Если твой фреймворк требует ручной подписки, раскомментируй:
        // EventBus.subscribe(this.mouseListener);
    };

    @Override
    public void onEnable() {
        super.onEnable();
        reset();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        reset();
    }

    @Override
    @Compile(obfuscation = 4)
    public void II_method_6642fd22() { // onTick
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.world == null || this.targetPos == null) {
            super.II_method_6642fd22();
            return;
        }

        this.ticksWaited++;
        if (this.ticksWaited > MAX_WAIT_TICKS) {
            returnToOriginalSlot();
            reset();
            super.II_method_6642fd22();
            return;
        }

        // Проверка дистанции
        Vec3d center = new Vec3d(this.targetPos.getX() + 0.5, this.targetPos.getY() + 0.5, this.targetPos.getZ() + 0.5);
        if (I_field_3a9bda27.player.getEyePos().distanceTo(center) > REACH + 1.0) {
            returnToOriginalSlot();
            reset();
            super.II_method_6642fd22();
            return;
        }

        // --- ФАЗА 1: Поиск и подготовка слота ---
        if (this.state == 1) {
            this.crystalSlot = findCrystalSlot();
            if (this.crystalSlot == -1) {
                reset(); // Нет кристаллов, отмена
                super.II_method_6642fd22();
                return;
            }

            // Если кристалл не в хотбаре (9-35), делаем быстрый свап в текущий слот хотбара
            if (this.crystalSlot >= 9) {
                I_field_3a9bda27.interactionManager.clickSlot(
                        I_field_3a9bda27.player.playerScreenHandler.syncId,
                        this.crystalSlot, this.originalSlot, SlotActionType.SWAP, I_field_3a9bda27.player
                );
                this.crystalSlot = this.originalSlot; // Теперь кристалл в нашем оригинальном слоте
            }

            this.state = 2;
            this.ticksWaited = 0;
        }

        // --- ФАЗА 2: Ожидание регистрации смены слота на сервере (1 тик) ---
        else if (this.state == 2) {
            if (this.ticksWaited >= 1) {
                // Меняем выбранный слот на клиенте и отправляем пакет
                I_field_3a9bda27.player.getInventory().selectedSlot = this.crystalSlot;
                I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.crystalSlot));
                
                this.state = 3;
                this.ticksWaited = 0;
            }
        }

        // --- ФАЗА 3: Установка кристалла ---
        else if (this.state == 3) {
            if (this.ticksWaited >= 1) { // Ждем 1 тик после смены слота, чтобы сервер не отклонил действие
                placeCrystalPacket(this.targetPos.down());
                this.state = 4;
                this.ticksWaited = 0;
            }
        }

        // --- ФАЗА 4: Ожидание появления сущности кристалла ---
        else if (this.state == 4) {
            // Ждем минимум 2 тика, чтобы сервер успел создать сущность и отправить пакет клиенту
            if (this.ticksWaited >= 2) {
                this.state = 5; // Переходим к атаке
            }
        }

        // --- ФАЗА 5: Атака (Приоритетная) ---
        else if (this.state == 5) {
            EndCrystalEntity crystal = findCrystalNear(this.targetPos);
            
            if (crystal == null) {
                // Если кристалл исчез (уже взорвался или античит удалил), завершаем
                returnToOriginalSlot();
                reset();
                super.II_method_6642fd22();
                return;
            }

            if (!actionCooldown.I_method_58432069(50L)) { // Небольшая задержка для стабильности
                super.II_method_6642fd22();
                return;
            }

            // Поворот на кристалл
            Vec3d aim = crystal.getPos().add(0.0, 0.5, 0.0);
            float[] rot = calculateRotations(aim);
            VelumClient.getInstance().I_method_58785402()
                    .I_method_a10b10c7(new iiIIiIIii_Class404(rot[0], rot[1]));

            // Отправляем пакет атаки по сущности (самый надежный способ)
            PlayerInteractEntityC2SPacket attackPacket = PlayerInteractEntityC2SPacket.attack(crystal, I_field_3a9bda27.player.isSneaking());
            I_field_3a9bda27.player.networkHandler.sendPacket(attackPacket);
            
            // Клиентская анимация взмаха
            I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);

            // Завершение
            returnToOriginalSlot();
            reset();
        }

        super.II_method_6642fd22();
    }

    // ------------------------------------------------------------------
    // Helpers
    // ------------------------------------------------------------------

    private void reset() {
        this.targetPos = null;
        this.state = 0;
        this.ticksWaited = 0;
        this.crystalSlot = -1;
    }

    private void returnToOriginalSlot() {
        if (I_field_3a9bda27.player == null) return;
        int currentSlot = I_field_3a9bda27.player.getInventory().selectedSlot;
        if (currentSlot != this.originalSlot) {
            I_field_3a9bda27.player.getInventory().selectedSlot = this.originalSlot;
            I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(this.originalSlot));
        }
    }

    private EndCrystalEntity findCrystalNear(BlockPos pos) {
        Box box = new Box(pos).expand(1.0, 1.5, 1.0);
        EndCrystalEntity closest = null;
        double closestSq = Double.MAX_VALUE;

        for (Entity entity : I_field_3a9bda27.world.getOtherEntities(null, box)) {
            if (!(entity instanceof EndCrystalEntity crystal)) continue;
            if (!crystal.isAlive()) continue;

            double d = crystal.squaredDistanceTo(I_field_3a9bda27.player);
            if (d < closestSq) {
                closestSq = d;
                closest = crystal;
            }
        }
        return closest;
    }

    /** Ищет кристалл. Приоритет: хотбар (0-8), затем инвентарь (9-35) */
    private int findCrystalSlot() {
        for (int i = 0; i < 9; i++) {
            ItemStack stack = I_field_3a9bda27.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() == Items.END_CRYSTAL) return i;
        }
        for (int i = 9; i < 36; i++) {
            ItemStack stack = I_field_3a9bda27.player.getInventory().getStack(i);
            if (!stack.isEmpty() && stack.getItem() == Items.END_CRYSTAL) return i;
        }
        return -1;
    }

    private void placeCrystalPacket BlockPos obsidianPos) {
        Vec3d hitVec = new Vec3d(obsidianPos.getX() + 0.5, obsidianPos.getY() + 1.0, obsidianPos.getZ() + 0.5);
        float[] rot = calculateRotations(hitVec);
        
        // Синхронизируем поворот
        VelumClient.getInstance().I_method_58785402()
                .I_method_a10b10c7(new iiIIiIIii_Class404(rot[0], rot[1]));

        // Отправляем пакет взаимодействия с блоком (установка)
        BlockHitResult hit = new BlockHitResult(hitVec, Direction.UP, obsidianPos, false);
        PlayerInteractBlockC2SPacket placePacket = new PlayerInteractBlockC2SPacket(Hand.MAIN_HAND, hit, 0);
        I_field_3a9bda27.player.networkHandler.sendPacket(placePacket);

        I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);
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