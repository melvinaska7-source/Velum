package velum.client;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import pyvelum.events.game.GameTickEvent;
import pyvelum.events.player.ClientPlayerTickEvent;
import pyvelum.events.render.HudRenderEvent;
import pyvelum.events.window.KeyPressEvent;
import pyvelum.events.window.MouseEvent;
import velum.client.AntiAimModule;
import velum.client.AssistModule;
import velum.client.AuctionModule;
import velum.client.AutoAcceptModule;
import velum.client.AutoAuthModule;
import velum.client.AutoBuyModule;
import velum.client.AutoDuelsModule;
import velum.client.AutoJoinModule;
import velum.client.AutoResellModule;
import velum.client.BaseFinderModule;
import velum.client.DeathCordsModule;
import velum.client.EffectRemoverModule;
import velum.client.FastItemUseModule;
import velum.client.GlobalsMenuModule;
import velum.client.InventoryBuilderModule;
import velum.client.InventoryCleanerModule;
import velum.client.ItemPickupModule;
import velum.client.KtLeaveModule;
import velum.client.NameProtectModule;
import velum.client.PanicModule;
import velum.client.RussianRouletteModule;
import velum.client.SoundsModule;
import velum.client.TestModule;
import velum.client.WebUtilsModule;
import velum.client.AutoEatModule;
import velum.client.AutoFarmModule;
import velum.client.AutoInvisibleModule;
import velum.client.AutoLeaveModule;
import velum.client.AutoShulkerModule;
import velum.client.AutoSwapModule;
import velum.client.BlinkModule;
import velum.client.BootsSwapModule;
import velum.client.ClanUpgradeModule;
import velum.client.ClickThroughModule;
import velum.client.ElytraUtilsModule;
import velum.client.FreeCameraModule;
import velum.client.GuiMoveModule;
import velum.client.InventoryUtilsModule;
import velum.client.MiddleClickModule;
import velum.client.MineHelperModule;
import velum.client.NoDelayModule;
import velum.client.NoFallModule;
import velum.client.NoInteractModule;
import velum.client.NoPushModule;
import velum.client.NoRotateModule;
import velum.client.NukerModule;
import velum.client.PlayerUtilsModule;
import velum.client.ScaffoldModule;
import velum.client.StealerModule;
import velum.client.TargetPearlModule;
import velum.client.TrackerModule;
import velum.client.AmbienceModule;
import velum.client.AntiInvisibleModule;
import velum.client.BeautifullyModule;
import velum.client.CustomFogModule;
import velum.client.DonateEffectsModule;
import velum.client.EspModule;
import velum.client.InterfaceModule;
import velum.client.KillEffectsModule;
import velum.client.MenuModule;
import velum.client.ObjectInfoModule;
import velum.client.PredictionModule;
import velum.client.RemovalsModule;
import velum.client.SoundEspModule;
import velum.client.StorageEspModule;
import velum.client.SwingAnimationModule;
import velum.client.TntTimerModule;
import velum.client.TargetEspModule;
import velum.client.ViewModelModule;
import velum.client.WardenHelperModule;
import velum.client.WaypointsModule;
import velum.client.WorldModule;
import velum.client.XRayModule;
import velum.client.IiIIIiII_Class69;
import velum.client.ModuleEntry;
import velum.client.VelumClient;
import velum.client.iIIIIIIII_Class257;
import velum.client.iiIIiIiI_Class203;
import velum.client.Module;
import velum.client.AimAssistModule;
import velum.client.AimBotModule;
import velum.client.AntiBotModule;
import velum.client.AuraModule;
import velum.client.AutoAnchorModule;
import velum.client.AutoArmorModule;
import velum.client.AutoExplosionModule;
import velum.client.AutoPotionModule;
import velum.client.AutoSoupModule;
import velum.client.AutoThrowModule;
import velum.client.AutoTotemModule;
import velum.client.BackTrackModule;
import velum.client.CriticalsModule;
import velum.client.ElytraTargetModule;
import velum.client.HitboxesModule;
import velum.client.KnockbackTweaksModule;
import velum.client.TriggerBotModule;
import velum.client.TrainerModule;
import velum.client.VelocityModule;
import velum.client.AirStuckModule;
import velum.client.AutoSprintModule;
import velum.client.ElytraStrafeModule;
import velum.client.FlightModule;
import velum.client.GrimGlideModule;
import velum.client.HighJumpModule;
import velum.client.NoSlowModule;
import velum.client.SpeedModule;
import velum.client.SpiderModule;
import velum.client.StrafeModule;
import velum.client.SuperFireworkModule;
import velum.client.TimerModule;
import velum.client.WaterSpeedModule;
import velum.client.AdminskyModule;
import ua.mintantileak.spk.Compile;

public class ModuleManager {
    private final Map<Class<? extends ModuleEntry>, ModuleEntry> I_field_a567c40b = new IdentityHashMap<Class<? extends ModuleEntry>, ModuleEntry>();
    private final List<ModuleEntry> I_field_7865b31 = new ArrayList<ModuleEntry>();
    private static int I_field_49;
    private final IiIIIiII_Class69<ClientPlayerTickEvent> I_field_3d936f41;
    private final IiIIIiII_Class69<HudRenderEvent> i_field_3d936f41;
    private final IiIIIiII_Class69<KeyPressEvent> II_field_3d936f41 = keyPressEvent -> {
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        if (keyPressEvent.getAction() != 1) {
            return;
        }
        int n = iIIIIIIII_Class257.I_method_e761de12();
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isAvailable() || !iIIIIIIII_Class257.I_method_37b59828(iiIiiIii_Class92.getKeybind(), keyPressEvent.getKey(), n)) continue;
            iiIiiIii_Class92.toggle();
        }
    };
    private final IiIIIiII_Class69<MouseEvent> Ii_field_3d936f41 = mouseEvent -> {
        if (MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        if (mouseEvent.getAction() != 1) {
            return;
        }
        int n = iIIIIIIII_Class257.I_method_e761de12();
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isAvailable() || !iIIIIIIII_Class257.I_method_37b59828(iiIiiIii_Class92.getKeybind(), mouseEvent.getButton(), n)) continue;
            iiIiiIii_Class92.toggle();
        }
    };
    private final IiIIIiII_Class69<GameTickEvent> iI_field_3d936f41 = gameTickEvent -> {
        if (!Module.ii_method_af18f3f9()) {
            return;
        }
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isEnabled() || iiIiiIii_Class92.isAvailable()) continue;
            iiIiiIii_Class92.setEnabled(false, true);
        }
    };

    public static void I_method_7056a46c() {
        ++I_field_49;
    }

    public ModuleManager(IiIIIiII_Class69<ClientPlayerTickEvent> iiIIIiII_Class69, IiIIIiII_Class69<HudRenderEvent> iiIIIiII_Class692) {
        this.I_field_3d936f41 = iiIIIiII_Class69;
        this.i_field_3d936f41 = iiIIIiII_Class692;
        VelumClient.getInstance().I_method_7897deab().I_method_2257cd48(this);
    }

    @Compile(obfuscation=4)
    public final void registerModules() {
        this.registerModule(new AuraModule());
        this.registerModule(new AimAssistModule());
        this.registerModule(new AutoTotemModule());
        this.registerModule(new TriggerBotModule());
        this.registerModule(new AimBotModule());
        this.registerModule(new AutoPotionModule());
        this.registerModule(new AutoThrowModule());
        this.registerModule(new AntiBotModule());
        this.registerModule(new VelocityModule());
        this.registerModule(new KnockbackTweaksModule());
        this.registerModule(new AutoArmorModule());
        this.registerModule(new AutoExplosionModule());
        this.registerModule(new AutoAnchorModule());
        this.registerModule(new BackTrackModule());
        this.registerModule(new HitboxesModule());
        this.registerModule(new ElytraTargetModule());
        this.registerModule(new CriticalsModule());
        this.registerModule(new AutoSoupModule());
        this.registerModule(new AutoSprintModule());
        this.registerModule(new SuperFireworkModule());
        this.registerModule(new WebUtilsModule());
        this.registerModule(new StrafeModule());
        this.registerModule(new FlightModule());
        this.registerModule(new GrimGlideModule());
        this.registerModule(new SpeedModule());
        this.registerModule(new TimerModule());
        this.registerModule(new NoSlowModule());
        this.registerModule(new HighJumpModule());
        this.registerModule(new WaterSpeedModule());
        this.registerModule(new AirStuckModule());
        this.registerModule(new SpiderModule());
        this.registerModule(new ElytraStrafeModule());
        this.registerModule(new MenuModule());
        this.registerModule(new EspModule());
        this.registerModule(new WaypointsModule());
        this.registerModule(new RemovalsModule());
        this.registerModule(new AmbienceModule());
        this.registerModule(new SwingAnimationModule());
        this.registerModule(new SoundEspModule());
        this.registerModule(new TntTimerModule());
        this.registerModule(new WardenHelperModule());
        this.registerModule(new BeautifullyModule());
        this.registerModule(new ViewModelModule());
        this.registerModule(new BlinkModule());
        this.registerModule(new InterfaceModule());
        this.registerModule(new TargetEspModule());
        this.registerModule(new StorageEspModule());
        this.registerModule(new XRayModule());
        this.registerModule(new AntiInvisibleModule());
        this.registerModule(new CustomFogModule());
        this.registerModule(new WorldModule());
        this.registerModule(new KillEffectsModule());
        this.registerModule(new PredictionModule());
        this.registerModule(new DonateEffectsModule());
        this.registerModule(new InventoryCleanerModule());
        this.registerModule(new AutoFarmModule());
        this.registerModule(new AutoInvisibleModule());
        this.registerModule(new ClickThroughModule());
        this.registerModule(new MineHelperModule());
        this.registerModule(new TargetPearlModule());
        this.registerModule(new StealerModule());
        this.registerModule(new MiddleClickModule());
        this.registerModule(new TrackerModule());
        this.registerModule(new InventoryUtilsModule());
        this.registerModule(new AutoEatModule());
        this.registerModule(new ClanUpgradeModule());
        this.registerModule(new FreeCameraModule());
        this.registerModule(new NoDelayModule());
        this.registerModule(new PlayerUtilsModule());
        this.registerModule(new NoPushModule());
        this.registerModule(new BootsSwapModule());
        this.registerModule(new ItemPickupModule());
        this.registerModule(new AutoShulkerModule());
        this.registerModule(new ScaffoldModule());
        this.registerModule(new ObjectInfoModule());
        this.registerModule(new NukerModule());
        this.registerModule(new NoRotateModule());
        this.registerModule(new NoInteractModule());
        this.registerModule(new NoFallModule());
        this.registerModule(new EffectRemoverModule());
        this.registerModule(new NameProtectModule());
        this.registerModule(new GlobalsMenuModule());
        this.registerModule(new ElytraUtilsModule());
        this.registerModule(new FastItemUseModule());
        this.registerModule(new AutoResellModule());
        this.registerModule(new BaseFinderModule());
        this.registerModule(new PanicModule());
        this.registerModule(new AuctionModule());
        this.registerModule(new InventoryBuilderModule());
        this.registerModule(new AutoAcceptModule());
        this.registerModule(new DeathCordsModule());
        this.registerModule(new AutoLeaveModule());
        this.registerModule(new KtLeaveModule());
        this.registerModule(new AutoSwapModule());
        this.registerModule(new RussianRouletteModule());
        this.registerModule(new AutoDuelsModule());
        this.registerModule(new AutoAuthModule());
        this.registerModule(new AutoJoinModule());
        this.registerModule(new GuiMoveModule());
        this.registerModule(new TestModule());
        this.registerModule(new AdminskyModule());
        this.registerModule(new AutoBuyModule());
        this.registerModule(new AssistModule());
        this.registerModule(new SoundsModule());
        this.registerModule(new AntiAimModule());
        this.snapshotModuleSettings();
    }

    @Compile(obfuscation=1)
    public final void enableDefaultModules() {
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!iiIiiIii_Class92.isEnabledByDefault()) continue;
            iiIiiIii_Class92.enable();
        }
    }

    public final void registerModule(Module Module2) {
        this.I_field_a567c40b.put(Module2.getClass(), Module2);
        this.I_field_7865b31.add(Module2);
    }

    public final <T extends ModuleEntry> T getModuleByName(String string) {
        return (T)this.I_field_7865b31.stream().filter(iiIiiIii_Class92 -> iiIiiIii_Class92.getName().replace(" ", "").equalsIgnoreCase(string) || iiIiiIii_Class92.getName().equalsIgnoreCase(string)).findFirst().orElseThrow(() -> new iiIIiIiI_Class203(string));
    }

    public final <T extends ModuleEntry> T getModule(Class<T> clazz) {
        return (T)((ModuleEntry)clazz.cast(this.I_field_a567c40b.get(clazz)));
    }

    public final void snapshotModuleSettings() {
        for (ModuleEntry iiIiiIii_Class92 : this.getModules()) {
            if (!(iiIiiIii_Class92 instanceof Module)) continue;
            Module Module2 = (Module)iiIiiIii_Class92;
            Module2.III_method_fbb67e0c();
        }
    }

    @Generated
    public List<ModuleEntry> getModules() {
        return this.I_field_7865b31;
    }

    @Generated
    public static int I_method_7056a45f() {
        return I_field_49;
    }

    @Generated
    public IiIIIiII_Class69<ClientPlayerTickEvent> I_method_1bea6feb() {
        return this.I_field_3d936f41;
    }

    @Generated
    public IiIIIiII_Class69<HudRenderEvent> i_method_45e7000b() {
        return this.i_field_3d936f41;
    }
}

