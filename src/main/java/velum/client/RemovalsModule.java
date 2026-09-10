package velum.client;

import lombok.Generated;
import pyvelum.events.player.ClientPlayerTickEvent;
import pyvelum.events.render.Render3DEvent;
import velum.client.FreeCameraModule;
import velum.client.IIiiiIIiI_Class115;
import velum.client.BooleanSetting;
import velum.client.MultiSelectSetting;
import velum.client.IiIIIiII_Class69;
import velum.client.ModuleCategory;
import velum.client.VelumClient;
import velum.client.ModuleInfo;
import velum.client.iIiiIIIIi_Class354;
import velum.client.Module;
import ua.mintantileak.spk.Compile;

@ModuleInfo(name="Removals", category=ModuleCategory.VISUALS, iI_method_476ab839=true, III_method_a89e5834="modules.descriptions.removals")
public class RemovalsModule
extends Module {
    private double I_field_44;
    private MultiSelectSetting I_field_bbe3ba6c;
    private MultiSelectSetting.Nested1_42856060 I_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 i_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 II_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 Ii_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 iI_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 ii_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 III_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IIi_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IiI_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 Iii_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 iII_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 iIi_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 iiI_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 iii_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IIII_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IIIi_field_51de8227;
    private BooleanSetting I_field_ba20ca4c;
    private MultiSelectSetting i_field_bbe3ba6c;
    private MultiSelectSetting.Nested1_42856060 IIiI_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IIii_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IiII_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IiIi_field_51de8227;
    private MultiSelectSetting.Nested1_42856060 IiiI_field_51de8227;
    private final IiIIIiII_Class69<ClientPlayerTickEvent> I_field_3d936f41 = clientPlayerTickEvent -> {
        if (this.iii_field_51de8227.isSelected()) {
            RemovalsModule.I_field_3a9bda27.options.getFovEffectScale().setValue(0.0);
        }
    };
    private final IiIIIiII_Class69<Render3DEvent> i_field_3d936f41 = render3DEvent -> {
        boolean bl = VelumClient.getInstance().getModuleManager().getModule(FreeCameraModule.class).IiI_method_460b62c3();
        iIiiIIIIi_Class354.I_method_9eafb553(render3DEvent.getTickDelta(), this.isEnabled() && this.ii_field_51de8227.isSelected() && this.I_field_ba20ca4c.i_method_9b12da03() && !bl);
        iIiiIIIIi_Class354.I_method_9684a23f(render3DEvent);
    };

    public RemovalsModule() {
        this.IiI_method_2244b29f();
    }

    @Compile(obfuscation=4)
    private void IiI_method_2244b29f() {
        this.I_field_bbe3ba6c = new MultiSelectSetting(this, "modules.settings.removals.effects");
        this.I_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.hurtCam").select();
        this.i_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.scoreboard");
        this.II_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.bossBar");
        this.Ii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.portal").select();
        this.iI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.fire").select();
        this.ii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.clip").select();
        this.III_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.breakParticles");
        this.IIi_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.totem").select();
        this.IiI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.water");
        this.Iii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.nausea").select();
        this.iII_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.blindness").select();
        this.iIi_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.darkness").select();
        this.iiI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.pumpkin").select();
        this.iii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.fov").select();
        this.IIII_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.weather").select();
        this.IIIi_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.I_field_bbe3ba6c, "modules.settings.removals.glowing");
        this.I_field_ba20ca4c = new BooleanSetting((IIiiiIIiI_Class115)this, "modules.settings.removals.clipTransparency", () -> !this.ii_field_51de8227.isSelected()).I_method_decd82b5();
        this.i_field_bbe3ba6c = new MultiSelectSetting(this, "modules.settings.removals.sounds");
        this.IIiI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.i_field_bbe3ba6c, "modules.settings.removals.beacon").select();
        this.IIii_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.i_field_bbe3ba6c, "modules.settings.removals.phantoms").select();
        this.IiII_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.i_field_bbe3ba6c, "modules.settings.removals.weatherSound").select();
        this.IiIi_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.i_field_bbe3ba6c, "modules.settings.removals.waterSound");
        this.IiiI_field_51de8227 = new MultiSelectSetting.Nested1_42856060(this.i_field_bbe3ba6c, "modules.settings.removals.lavaSound");
    }

    @Override
    public void onEnable() {
        this.I_field_44 = (Double)RemovalsModule.I_field_3a9bda27.options.getFovEffectScale().getValue();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        RemovalsModule.I_field_3a9bda27.options.getFovEffectScale().setValue(this.I_field_44);
        iIiiIIIIi_Class354.I_method_b0c7f5ff();
        super.onDisable();
    }

    @Generated
    public double I_method_c7d2e20d() {
        return this.I_field_44;
    }

    @Generated
    public MultiSelectSetting I_method_c19582f5() {
        return this.I_field_bbe3ba6c;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 I_method_b2404c70() {
        return this.I_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 i_method_b8484850() {
        return this.i_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 II_method_92acf0f3() {
        return this.II_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 Ii_method_98b4ecd3() {
        return this.Ii_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 iI_method_4da47113() {
        return this.iI_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 ii_method_53ac6cf3() {
        return this.ii_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 III_method_bfd4dcd0() {
        return this.III_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IIi_method_c5dcd8b0() {
        return this.IIi_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IiI_method_7acc5cf0() {
        return this.IiI_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 Iii_method_80d458d0() {
        return this.Iii_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 iII_method_63cd60b0() {
        return this.iII_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 iIi_method_69d55c90() {
        return this.iIi_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 iiI_method_1ec4e0d0() {
        return this.iiI_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 iii_method_24ccdcb0() {
        return this.iii_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IIII_method_37aa6c93() {
        return this.IIII_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IIIi_method_3db26873() {
        return this.IIIi_field_51de8227;
    }

    @Generated
    public BooleanSetting I_method_bfd292d5() {
        return this.I_field_ba20ca4c;
    }

    @Generated
    public MultiSelectSetting i_method_d72af6d5() {
        return this.i_field_bbe3ba6c;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IIiI_method_f2a1ecb3() {
        return this.IIiI_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IIii_method_f8a9e893() {
        return this.IIii_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IiII_method_dba2f073() {
        return this.IiII_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IiIi_method_e1aaec53() {
        return this.IiIi_field_51de8227;
    }

    @Generated
    public MultiSelectSetting.Nested1_42856060 IiiI_method_969a7093() {
        return this.IiiI_field_51de8227;
    }

    @Generated
    public IiIIIiII_Class69<ClientPlayerTickEvent> I_method_fbb1f198() {
        return this.I_field_3d936f41;
    }

    @Generated
    public IiIIIiII_Class69<Render3DEvent> i_method_25ae81b8() {
        return this.i_field_3d936f41;
    }
}

