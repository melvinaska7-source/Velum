package velum.client;

import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import velum.client.SoundsModule;
import velum.client.KeybindSetting;
import velum.client.ModeSetting;
import velum.client.ModuleCategory;
import velum.client.VelumClient;
import velum.client.ModuleInfo;
import velum.client.IiiIIiiiI_Class207;
import velum.client.IiiIiIIII_Class209;
import velum.client.IiiIiIiiI_Class215;
import velum.client.IiiIiIiii_Class216;
import velum.client.iIIIIIIII_Class257;
import velum.client.Module;
import velum.client.iiIiIIIII_Class417;
import ua.mintantileak.spk.Compile;

@ModuleInfo(name="Menu", category=ModuleCategory.VISUALS, defaultKey=344, III_method_a89e5834="modules.descriptions.menu")
public class MenuModule
extends Module {
    private static final IiiIiIIII_Class209 I_field_a5d83a6c = new IiiIiIIII_Class209();
    private ModeSetting I_field_bbe33e6c;
    private ModeSetting.Nested1_42765c60 I_field_500d0627;
    private ModeSetting.Nested1_42765c60 i_field_500d0627;
    private ModeSetting.Nested1_42765c60 I_field_windowMode;
    private KeybindSetting I_field_ba20522c;
    private Screen I_field_bf52cf84;
    private IiiIiIiii_Class216 I_field_a5e7424c;
    private VelumWindowScreen I_field_window;
    private static boolean I_field_5a;

    public MenuModule() {
        this.Iii_method_c57aca5f();
    }

    @Compile(obfuscation=4)
    private void Iii_method_c57aca5f() {
        this.I_field_bbe33e6c = new ModeSetting(this, "modules.settings.menu.mode");
        this.I_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.menu.mode.dropdown");
        this.i_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.menu.mode.modern", "", () -> true);
        this.I_field_windowMode = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.menu.mode.window").select();
        this.I_field_ba20522c = new KeybindSetting(this, "modules.settings.menu.hide_key").I_method_4288e15a(342);
    }

    @Override
    public void onEnable() {
        boolean window = this.I_field_windowMode.isSelected();
        boolean bl = this.i_field_500d0627.isSelected();
        if (window && MenuModule.I_field_3a9bda27.currentScreen instanceof VelumWindowScreen) {
            return;
        }
        if (bl && MenuModule.I_field_3a9bda27.currentScreen instanceof IiiIiIiiI_Class215) {
            return;
        }
        if (!window && !bl && !(MenuModule.I_field_3a9bda27.currentScreen instanceof IiiIiIiii_Class216)) {
            return;
        }
        this.I_method_7e9b4d21();
        super.onEnable();
    }

    /** Opens the selected GUI and keeps the Menu lifecycle flag in sync. */
    public Screen I_method_7e9b4d21() {
        this.I_field_bf52cf84 = this.I_method_83034dbb();
        I_field_5a = true;
        I_field_3a9bda27.setScreen(this.I_field_bf52cf84);
        SoundsModule sounds = VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class);
        if (sounds != null && sounds.isEnabled()) {
            iiIiIIIII_Class417.I_field_34b2ba6c.I_method_87d2e181(sounds.I_method_45ee5ef());
        }
        return this.I_field_bf52cf84;
    }

    public Screen I_method_83034dbb() {
        if (this.I_field_windowMode.isSelected()) {
            if (this.I_field_window == null) this.I_field_window = new VelumWindowScreen();
            return this.I_field_window;
        }
        if (this.i_field_500d0627.isSelected()) {
            IiiIIiiiI_Class207 iiiIIiiiI_Class207 = VelumClient.getInstance().I_method_96982062();
            IiiIIiiiI_Class207 iiiIIiiiI_Class2072 = iiiIIiiiI_Class207 instanceof IiiIiIiiI_Class215 ? iiiIIiiiI_Class207 : new IiiIiIiiI_Class215();
            VelumClient.getInstance().I_method_577f3d5a(iiiIIiiiI_Class2072);
            return iiiIIiiiI_Class2072;
        }
        if (this.I_field_a5e7424c == null) {
            this.I_field_a5e7424c = new IiiIiIiii_Class216();
        }
        return this.I_field_a5e7424c;
    }

    public static boolean I_method_48514ce8(int n) {
        return !I_field_5a && MenuModule.i_method_4a143d08(n);
    }

    public static void II_method_c39aca01(int n) {
        MenuModule iIiIIiiIi_Class78 = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
        if (iIiIIiiIi_Class78 != null && iIIIIIIII_Class257.I_method_4da58b7(iIiIIiiIi_Class78.getKeybind()) == n) {
            I_field_5a = false;
        }
    }

    private static boolean i_method_4a143d08(int n) {
        MenuModule iIiIIiiIi_Class78 = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
        return iIiIIiiIi_Class78 != null && iIIIIIIII_Class257.I_method_967132c3(iIiIIiiIi_Class78.getKeybind(), n);
    }

    public static void IiI_method_c56c3e7f() {
        MenuModule iIiIIiiIi_Class78 = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
        MinecraftClient.getInstance().setScreen(iIiIIiiIi_Class78.I_method_83034dbb());
    }

    @Override
    public void onDisable() {
        I_field_5a = false;
        if (MenuModule.I_field_3a9bda27.currentScreen == this.I_field_bf52cf84) {
            I_field_3a9bda27.setScreen(null);
        }
        super.onDisable();
    }

    @Generated
    public ModeSetting.Nested1_42765c60 I_method_4bdd4450() {
        return this.i_field_500d0627;
    }

    @Generated
    public KeybindSetting I_method_20c0695() {
        return this.I_field_ba20522c;
    }
}

