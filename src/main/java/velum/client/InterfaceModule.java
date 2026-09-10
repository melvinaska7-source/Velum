package velum.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import lombok.Generated;
import velum.client.MultiSelectSetting;
import velum.client.IiIiIIIii_Class164;
import velum.client.IiIiIIiII_Class165;
import velum.client.ModuleCategory;
import velum.client.VelumClient;
import velum.client.ModuleInfo;
import velum.client.iII_Class5;
import velum.client.iIIiIIiiI_Class295;
import velum.client.Module;
import velum.client.iii_Class8;
import ua.mintantileak.spk.Compile;

@ModuleInfo(name="Interface", category=ModuleCategory.VISUALS, iI_method_476ab839=true, ii_method_47794419=true)
public class InterfaceModule
extends Module {
    private final Map<IiIiIIiII_Class165, MultiSelectSetting.Nested1_42856060> I_field_a567c40b = new IdentityHashMap<IiIiIIiII_Class165, MultiSelectSetting.Nested1_42856060>();
    private MultiSelectSetting I_field_bbe3ba6c;

    public InterfaceModule() {
        this.IiI_method_c4cbfabf();
    }

    @Compile(obfuscation=4)
    private void IiI_method_c4cbfabf() {
        this.I_field_bbe3ba6c = new MultiSelectSetting(this, "modules.settings.interface.elements"){

            @Override
            public JsonElement toJson() {
                return new JsonObject();
            }

            @Override
            public void fromJson(JsonElement jsonElement) {
            }

            @Override
            public boolean isValidJson(JsonElement jsonElement) {
                return jsonElement != null && jsonElement.isJsonObject();
            }

            @Override
            public iii_Class8 createComponent() {
                InterfaceModule.this.Iii_method_c4da869f();
                iii_Class8 iii_Class82 = super.createComponent().III_method_9dc17555();
                return new iii_Class8(){

                    @Override
                    protected void onTick(float f, float f2, float f3) {
                        InterfaceModule.this.Iii_method_c4da869f();
                        super.onTick(f, f2, f3);
                    }
                }.I_method_3301fdd(iII_Class5.I_field_b583e68c).I_method_8939bffd(iii_Class82);
            }
        };
        this.Iii_method_c4da869f();
    }

    void Iii_method_c4da869f() {
        List<MultiSelectSetting.Nested1_42856060> list;
        IiIiIIIii_Class164 iiIiIIIii_Class164 = VelumClient.getInstance().I_method_35687482();
        if (iiIiIIIii_Class164 == null || this.I_field_bbe3ba6c == null) {
            return;
        }
        List<IiIiIIiII_Class165> list2 = iiIiIIIii_Class164.III_method_812fc188();
        List<MultiSelectSetting.Nested1_42856060> list3 = this.I_field_bbe3ba6c.I_method_45aaa648();
        if (!this.I_method_11cc0e25(list3, list2)) {
            list = new ArrayList<MultiSelectSetting.Nested1_42856060>(list2.size());
            for (IiIiIIiII_Class165 object : list2) {
                list.add(this.I_field_a567c40b.computeIfAbsent(object, iiIiIIiII_Class165 -> new Nested1_a7d56c40(this.I_field_bbe3ba6c, (IiIiIIiII_Class165)iiIiIIiII_Class165)));
            }
            this.I_field_a567c40b.keySet().removeIf(iiIiIIiII_Class165 -> !list2.contains(iiIiIIiII_Class165));
            list3.clear();
            list3.addAll(list);
        }
        list = this.I_field_bbe3ba6c.i_method_faf6de68();
        list.removeIf(nested1_42856060 -> !list3.contains(nested1_42856060));
        for (MultiSelectSetting.Nested1_42856060 nested1_428560602 : list3) {
            boolean bl = nested1_428560602.isSelected();
            if (bl == list.contains(nested1_428560602)) continue;
            if (bl) {
                list.add(nested1_428560602);
                continue;
            }
            list.remove(nested1_428560602);
        }
    }

    private boolean I_method_11cc0e25(List<MultiSelectSetting.Nested1_42856060> list, List<IiIiIIiII_Class165> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        for (int i = 0; i < list2.size(); ++i) {
            if (this.I_field_a567c40b.get(list2.get(i)) == list.get(i)) continue;
            return false;
        }
        return true;
    }

    public static boolean IiI_method_c4cbfac3() {
        return false;
    }

    public static float I_method_fdcdb22f() {
        return 0.0f;
    }

    public static float i_method_fddc3e0f() {
        return 1.0f;
    }

    public static float II_method_bbf78cf2() {
        return 0.79f;
    }

    public static float Ii_method_bc0618d2() {
        return 12.0f;
    }

    public static float iI_method_bdba7d12() {
        return 2.0f;
    }

    public static float ii_method_bdc908f2() {
        return 1.0f;
    }

    public static boolean Iii_method_c4da86a3() {
        return false;
    }

    public static boolean iII_method_f9a41e83() {
        return true;
    }

    @Generated
    public Map<IiIiIIiII_Class165, MultiSelectSetting.Nested1_42856060> i_method_7dbae14() {
        return this.I_field_a567c40b;
    }

    @Generated
    public MultiSelectSetting I_method_1ace6b15() {
        return this.I_field_bbe3ba6c;
    }

    static final class Nested1_a7d56c40
    extends MultiSelectSetting.Nested1_42856060 {
        private final IiIiIIiII_Class165 I_field_fed7eac;

        Nested1_a7d56c40(MultiSelectSetting iIiiiiiii_Class128, IiIiIIiII_Class165 iiIiIIiII_Class165) {
            super(iIiiiiiii_Class128, iiIiIIiII_Class165.getName());
            this.I_field_fed7eac = iiIiIIiII_Class165;
        }

        @Override
        public boolean isSelected() {
            return this.I_field_fed7eac.isShowing();
        }

        @Override
        public MultiSelectSetting.Nested1_42856060 toggle() {
            boolean bl;
            boolean bl2 = bl = !this.I_field_fed7eac.isShowing();
            if (bl && this.I_field_fed7eac.getX() == 0.0f && this.I_field_fed7eac.getY() == 0.0f) {
                this.I_field_fed7eac.pos(iIIiIIiiI_Class295.I_field_73c6d26c.I_method_804ee20f() / 2.0f, iIIiIIiiI_Class295.I_field_73c6d26c.i_method_805d6def() / 2.0f);
            }
            this.I_field_fed7eac.setShowing(bl);
            if (bl) {
                this.select();
            } else {
                this.deselect();
            }
            VelumClient.getInstance().I_method_7a4c3f0b().i_method_af563376("client");
            return this;
        }
    }
}

