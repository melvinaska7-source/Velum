package velum.client;

import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket; // ВАЖНО ДЛЯ 1.21.4
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
    private static final int PLACE_TIMEOUT_TICKS = 20;
    private final iiIiIIiii_Class424 I_field_991c1e8c = new iiIiIIiii_Class424();
    private final iiIiIIiii_Class424 i_field_991c1e8c = new iiIiIIiii_Class424();
    private BlockPos I_field_670402ba;
    private int i_field_49 = 0;
    private int II_field_49 = -1;

    private final IiIIIiII_Class69<MouseEvent> I_field_3d936f41 = var1 -> {
        if (I_field_3a9bda27.player != null && I_field_3a9bda27.world != null) {
            if (I_field_3a9bda27.currentScreen == null) {
                if (var1.getButton() == 1 && var1.getAction() == 1) {
                    if (I_field_3a9bda27.player.getMainHandStack().isEmpty()
                            || I_field_3a9bda27.player.getMainHandStack().getItem() instanceof SwordItem) {
                        if (I_field_3a9bda27.crosshairTarget instanceof BlockHitResult var2) {
                            if (I_field_3a9bda27.world.getBlockState(var2.getBlockPos()).isOf(Blocks.OBSIDIAN)) {
                                BlockPos var4 = var2.getBlockPos().up();
                                if (I_field_3a9bda27.world.getBlockState(var4).isAir()) {
                                    this.I_field_670402ba = var4.toImmutable();
                                    this.i_field_49 = 0;
                                    this.II_field_49 = -1;
                                }
                            }
                        }
                    }
                }
            }
        }
    };

    @Override
    public void onEnable() {
        super.onEnable();
        // ЕСЛИ БАЗОВЫЙ КЛАСС НЕ РЕГИСТРИРУЕТ СОБЫТИЯ САМ, РАСКОММЕНТИРУЙ ЭТО:
        // EventBus.subscribe(I_field_3d936f41);
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.I_field_670402ba = null;
        this.II_field_49 = -1;
        this.i_field_49 = 0;
        // EventBus.unsubscribe(I_field_3d936f41);
    }

    @Override
    @Compile(obfuscation = 4)
    public void II_method_6642fd22() {
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.world == null) return;

        // --- ЭТАП 1: ставим кристалл ---
        if (this.I_field_670402ba != null) {
            if (!I_field_3a9bda27.world.getBlockState(this.I_field_670402ba.down()).isOf(Blocks.OBSIDIAN)
                    || !I_field_3a9bda27.world.getBlockState(this.I_field_670402ba).isAir()) {
                this.I_field_670402ba = null;
            } else {
                int var1 = this.I_method_2d8f7a44();
                if (var1 == -1) {
                    this.I_field_670402ba = null;
                } else if (this.I_field_991c1e8c.I_method_58432069(100L)) {
                    this.I_method_704015b1(var1, this.I_field_670402ba);
                    this.I_field_991c1e8c.I_method_23e11e3f();
                }
            }
            super.II_method_6642fd22();
            return;
        }

        // --- ЭТАП 2: атакуем поставленный кристалл ---
        EndCrystalEntity var2 = this.II_method_9b21c4d5();
        if (var2 != null) {
            if (this.i_field_991c1e8c.I_method_58432069(150L)
                    && I_field_3a9bda27.player.getAttackCooldownProgress(1.0F) >= 1.0F) {
                Vec3d var3 = var2.getPos().add(0.0, 0.5, 0.0);
                float[] var4 = this.I_method_c10d9523(var3);
                VelumClient.getInstance().I_method_58785402().I_method_a10b10c7(new iiIIiIIii_Class404(var4[0], var4[1]));
                
                I_field_3a9bda27.interactionManager.attackEntity(I_field_3a9bda27.player, var2);
                I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);
                
                this.i_field_991c1e8c.I_method_23e11e3f();
                this.II_field_49 = -1;
            }
        }
        super.II_method_6642fd22();
    }

    private EndCrystalEntity II_method_9b21c4d5() {
        if (this.II_field_670402ba_check()) return null;
        
        Box var1 = new Box(
                this.I_field_670402ba.getX(), this.I_field_670402ba.getY(), this.I_field_670402ba.getZ(),
                this.I_field_670402ba.getX() + 1.0, this.I_field_670402ba.getY() + 2.0, this.I_field_670402ba.getZ() + 1.0
        );
        EndCrystalEntity var2 = null;
        for (Entity var6 : I_field_3a9bda27.world.getOtherEntities(null, var1)) {
            if (var6 instanceof EndCrystalEntity var7 && var7.isAlive()) {
                var2 = var7;
                break;
            }
        }
        if (var2 != null) {
            this.II_field_49 = var2.getId();
            return var2;
        }
        if (++this.i_field_49 > PLACE_TIMEOUT_TICKS) {
            this.II_field_49 = -1;
            this.i_field_49 = 0;
        }
        return null;
    }

    private boolean II_field_670402ba_check() {
        return this.II_field_49 != -1 ? false : this.I_field_670402ba == null;
    }

    private int I_method_2d8f7a44() {
        for (int var1 = 0; var1 < 36; var1++) {
            ItemStack var2 = I_field_3a9bda27.player.getInventory().getStack(var1);
            if (!var2.isEmpty() && var2.getItem() == Items.END_CRYSTAL) return var1;
        }
        return -1;
    }

    private void I_method_704015b1(int var1, BlockPos var2) {
        int currentSlot = I_field_3a9bda27.player.getInventory().selectedSlot;
        
        if (var1 >= 9 && var1 <= 35) {
            I_field_3a9bda27.interactionManager.clickSlot(
                    I_field_3a9bda27.player.playerScreenHandler.syncId,
                    var1, currentSlot, SlotActionType.SWAP, I_field_3a9bda27.player
            );
            var1 = currentSlot;
        }

        // ФИКС ДЛЯ 1.21.4: Отправляем пакет на сервер, иначе interactBlock будет отклонён
        if (var1 != currentSlot) {
            I_field_3a9bda27.player.getInventory().selectedSlot = var1;
            I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(var1));
        } else {
            I_field_3a9bda27.player.getInventory().selectedSlot = var1;
        }

        BlockPos var3 = var2.down();
        Vec3d var4 = new Vec3d(var3.getX() + 0.5, var3.getY() + 1.0, var3.getZ() + 0.5);
        float[] var5 = this.I_method_c10d9523(var4);
        VelumClient.getInstance().I_method_58785402().I_method_a10b10c7(new iiIIiIIii_Class404(var5[0], var5[1]));

        BlockHitResult var7 = new BlockHitResult(var4, Direction.UP, var3, false);
        I_field_3a9bda27.interactionManager.interactBlock(I_field_3a9bda27.player, Hand.MAIN_HAND, var7);
        I_field_3a9bda27.player.swingHand(Hand.MAIN_HAND);

        // Возвращаем слот
        if (var1 != currentSlot) {
            I_field_3a9bda27.player.getInventory().selectedSlot = currentSlot;
            I_field_3a9bda27.player.networkHandler.sendPacket(new UpdateSelectedSlotC2SPacket(currentSlot));
        } else {
            I_field_3a9bda27.player.getInventory().selectedSlot = currentSlot;
        }
    }

    private float[] I_method_c10d9523(Vec3d var1) {
        Vec3d var2 = new Vec3d(
                I_field_3a9bda27.player.getX(),
                I_field_3a9bda27.player.getY() + I_field_3a9bda27.player.getEyeHeight(I_field_3a9bda27.player.getPose()),
                I_field_3a9bda27.player.getZ()
        );
        double var3 = var1.x - var2.x;
        double var5 = var1.y - var2.y;
        double var7 = var1.z - var2.z;
        double var9 = Math.sqrt(var3 * var3 + var7 * var7);
        float var11 = (float) Math.toDegrees(Math.atan2(var7, var3)) - 90.0F;
        float var12 = (float) (-Math.toDegrees(Math.atan2(var5, var9)));
        return new float[]{
                I_field_3a9bda27.player.getYaw() + MathHelper.wrapDegrees(var11 - I_field_3a9bda27.player.getYaw()),
                I_field_3a9bda27.player.getPitch() + MathHelper.wrapDegrees(var12 - I_field_3a9bda27.player.getPitch())
        };
    }
}