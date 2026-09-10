package velum.client;

import pyvelum.utility.render.ColorRGBA;
import velum.client.MultiSelectSetting;
import velum.client.IiIiIIII_Class81;
import velum.client.IiIiiIIII_Class177;
import velum.client.IiIiiiIiI_Class187;
import velum.client.IiiiiiiII_Class253;
import velum.client.iIIIiiiII_Class285;

public class IiiIIIIii_Class196
extends IiIiiiIiI_Class187 {
    public IiiIIIIii_Class196(MultiSelectSetting iIiiiiiii_Class128) {
        super(iIiiiiiii_Class128, "pvp");
    }

    @Override
    public void prepare(IiIiiIIII_Class177 iiIiiIIII_Class177) {
        this.I_method_746c5e3b("s", iIIIiiiII_Class285.I_field_49, IiIiIIII_Class81.I_method_f25a980a("hud.pvp_mode"), new ColorRGBA(185.0f, 28.0f, 28.0f));
        super.prepare(iiIiiIIII_Class177);
    }

    @Override
    public boolean canShow() {
        return iIIIiiiII_Class285.I_field_5a && IiiiiiiII_Class253.i_method_1cc4aa23();
    }
}

