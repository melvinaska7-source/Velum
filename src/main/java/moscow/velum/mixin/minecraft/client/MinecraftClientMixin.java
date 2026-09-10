package moscow.velum.mixin.minecraft.client;

import moscow.velum.mixin.accessors.ItemCooldownEntryAccessor;
import moscow.velum.mixin.accessors.ItemCooldownManagerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import pyvelum.events.game.GameTickEvent;
import velum.client.IIIiIiIi_Class22;
import velum.client.NoDelayModule;
import velum.client.IIiiiiiI_Class63;
import velum.client.IiIiIIII_Class81;
import velum.client.IiIiIiii_Class88;
import velum.client.IiIiii_Class24;
import velum.client.VelumClient;
import velum.client.IiiIIiiii_Class208;
import velum.client.iIIIIiIII_Class265;
import velum.client.iIIiIiiII_Class301;
import velum.client.iiIIIiIii_Class396;
import velum.client.iiIIIiiIi_Class398;
import velum.client.iiI_Class7;
import velum.client.iiiIiIi_Class118;

@Mixin(value={MinecraftClient.class})
public class MinecraftClientMixin {
    @Shadow
    private int field_1752;
    @Unique
    private long velum$lastUseCooldownAlertMs;
    @Unique
    private Item velum$lastUseCooldownAlertItem;

    @Inject(method={"onResolutionChanged"}, at={@At(value="RETURN")})
    public void onResolutionChanged(CallbackInfo callbackInfo) {
        iiI_Class7.invalidateLayout();
    }

    @Inject(method={"tick"}, at={@At(value="HEAD")})
    public void tick(CallbackInfo callbackInfo) {
        iIIIIiIII_Class265.I_method_6fefbe3f();
        VelumClient.getInstance().I_method_7897deab().I_method_e7f802ad(new GameTickEvent());
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/GameRenderer;render(Lnet/minecraft/client/render/RenderTickCounter;Z)V", shift=At.Shift.BEFORE)})
    private void velum$beginCaptureFrame(boolean bl, CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.i_method_f1a2a44c();
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/render/GameRenderer;render(Lnet/minecraft/client/render/RenderTickCounter;Z)V", shift=At.Shift.AFTER)})
    private void velum$applyRemainingPatches(boolean bl, CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_40ef6817(2);
    }

    @Inject(method={"render"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/util/Window;swapBuffers(Lnet/minecraft/client/util/tracy/TracyFrameCapturer;)V", shift=At.Shift.BEFORE)})
    private void velum$finishCaptureFrame(boolean bl, CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.II_method_40fdec75();
        IiIiIiii_Class88.I_method_84902c6c();
    }

    @Inject(method={"<init>"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;onResolutionChanged()V")})
    public void initializeClient(RunArgs runArgs, CallbackInfo callbackInfo) {
        IiIiii_Class24.I_method_3fd6ba2c();
    }

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    public void endInitialize(RunArgs runArgs, CallbackInfo callbackInfo) {
        iiIIIiIii_Class396 iiIIIiIii_Class3962 = iiIIIiIii_Class396.I_method_58cff815(16, 16);
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/combat.penis"));
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/movement.penis"));
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/visuals.penis"));
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/player.penis"));
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/other.penis"));
        iiIIIiIii_Class3962.I_method_8b098de0(VelumClient.id("penises/search.penis"));
        iiIIIiIii_Class3962.I_method_13b30a5f();
        iiIIIiIii_Class396 iiIIIiIii_Class3963 = iiIIIiIii_Class396.I_method_58cff815(12, 12);
        iiIIIiIii_Class3963.I_method_8b098de0(VelumClient.id("penises/check_enable.penis"));
        iiIIIiIii_Class3963.I_method_8b098de0(VelumClient.id("penises/check_disable.penis"));
        iiIIIiIii_Class3963.I_method_13b30a5f();
        for (IiiIIiiii_Class208 iiiIIiiii_Class208 : IiiIIiiii_Class208.values()) {
            try {
                iiiIIiiii_Class208.I_method_747b2887(new iiIIIiiIi_Class398(VelumClient.id("penises/" + iiiIIiiii_Class208.I_method_69cc3c41().toLowerCase() + ".penis")));
            }
            catch (RuntimeException runtimeException) {
                // empty catch block
            }
        }
    }

    @Inject(method={"cleanUpAfterCrash"}, at={@At(value="HEAD")})
    private void saveConfigAfterCrash(CallbackInfo callbackInfo) {
        VelumClient ii_Class2 = VelumClient.I_field_502d1741;
        if (ii_Class2.i_method_e8604970()) {
            return;
        }
        IIiiiiiI_Class63 iIiiiiiI_Class63 = ii_Class2.I_method_5198232b();
        if (iIiiiiiI_Class63 == null) {
            return;
        }
        iIiiiiiI_Class63.II_method_1fbeeff5();
    }

    @Inject(method={"stop"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/MinecraftClient;close()V", shift=At.Shift.AFTER)})
    public void shutdownClient(CallbackInfo callbackInfo) {
        IIIiIiIi_Class22.I_method_f194186c();
        IiIiii_Class24.i_method_3fe5460c();
    }

    @Inject(method={"getWindowTitle"}, at={@At(value="HEAD")}, cancellable=true)
    public void changeWindowTitle(CallbackInfoReturnable<String> callbackInfoReturnable) {
        IiIiii_Class24.I_method_e10e7018(callbackInfoReturnable);
    }

    @Inject(method={"setScreen"}, at={@At(value="HEAD")}, cancellable=true)
    private void velum$silentPeekSuppress(Screen screen, CallbackInfo callbackInfo) {
        if (iIIiIiiII_Class301.I_method_83d556c7(screen)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"doItemUse"}, at={@At(value="TAIL")})
    private void resetItemUseCooldown(CallbackInfo callbackInfo) {
        NoDelayModule iIIiIiiII_Class45 = VelumClient.getInstance().getModuleManager().getModule(NoDelayModule.class);
        if (iIIiIiiII_Class45.isEnabled() && iIIiIiiII_Class45.i_method_5c40d2f5().i_method_9b12da03()) {
            this.field_1752 = iIIiIiiII_Class45.II_method_d16c44d5();
        }
    }

    @Inject(method={"doItemUse"}, at={@At(value="HEAD")})
    private void velum$alertItemUseCooldown(CallbackInfo callbackInfo) {
        MinecraftClient minecraftClient = (MinecraftClient)(Object)this;
        if (minecraftClient.player == null) {
            return;
        }
        ItemStack itemStack = minecraftClient.player.getMainHandStack();
        if (itemStack.isEmpty() || !minecraftClient.player.getItemCooldownManager().isCoolingDown(itemStack)) {
            itemStack = minecraftClient.player.getOffHandStack();
        }
        if (itemStack.isEmpty() || !minecraftClient.player.getItemCooldownManager().isCoolingDown(itemStack)) {
            return;
        }
        float f = this.velum$getRemainingCooldownSeconds(minecraftClient, itemStack);
        if (f <= 0.01f) {
            return;
        }
        long l = System.currentTimeMillis();
        Item item = itemStack.getItem();
        if (item == this.velum$lastUseCooldownAlertItem && l - this.velum$lastUseCooldownAlertMs < 600L) {
            return;
        }
        this.velum$lastUseCooldownAlertMs = l;
        this.velum$lastUseCooldownAlertItem = item;
        VelumClient.getInstance().I_method_5cb1af22().I_method_67864747(new iiiIiIi_Class118(IiIiIIII_Class81.I_method_1410d1e5("alerts.cooldown", Float.valueOf(f)), itemStack));
    }

    @Unique
    private float velum$getRemainingCooldownSeconds(MinecraftClient minecraftClient, ItemStack itemStack) {
        ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor)(Object)minecraftClient.player.getItemCooldownManager();
        Identifier identifier = itemCooldownManagerAccessor.velum$getGroup(itemStack);
        Object object = itemCooldownManagerAccessor.velum$getEntries().get(identifier);
        if (object == null) {
            return 0.0f;
        }
        int n = ((ItemCooldownEntryAccessor)(Object)object).velum$getEndTick() - itemCooldownManagerAccessor.velum$getTick();
        return Math.max(0.0f, (float)n / 20.0f);
    }
}

