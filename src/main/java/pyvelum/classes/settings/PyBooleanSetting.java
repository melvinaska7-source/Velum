package pyvelum.classes.settings;

import lombok.Generated;
import pyvelum.classes.PyEspElement;
import pyvelum.classes.PyHudElement;
import pyvelum.classes.PyModule;
import velum.client.IIiiIIiii_Class104;
import velum.client.BooleanSetting;
import velum.client.IiIiiiiI_Class95;

public class PyBooleanSetting {
    private final BooleanSetting setting;

    public PyBooleanSetting(PyModule pyModule, String string) {
        this.setting = new BooleanSetting(pyModule.getModule(), string);
        this.setting.I_method_4e1408b5(false);
        if (!(pyModule.getModule() instanceof IiIiiiiI_Class95)) {
            IIiiIIiii_Class104.I_method_9fdd7f66(pyModule.getModule(), this.setting);
        }
    }

    public PyBooleanSetting(PyHudElement pyHudElement, String string) {
        this.setting = new BooleanSetting(pyHudElement, string);
        this.setting.I_method_4e1408b5(false);
    }

    public PyBooleanSetting(PyEspElement pyEspElement, String string) {
        this.setting = new BooleanSetting(pyEspElement.getElement(), string);
        this.setting.I_method_4e1408b5(false);
    }

    public PyBooleanSetting(BooleanSetting iIiiiIiii_Class120) {
        this.setting = iIiiiIiii_Class120;
    }

    public PyBooleanSetting set(boolean bl) {
        IIiiIIiii_Class104.I_method_dacf28a7(this.setting);
        this.setting.I_method_4e1408b5(bl);
        return this;
    }

    public boolean get() {
        return this.setting.i_method_9b12da03();
    }

    public PyBooleanSetting toggle() {
        IIiiIIiii_Class104.I_method_dacf28a7(this.setting);
        this.setting.toggle();
        return this;
    }

    @Generated
    public BooleanSetting getSetting() {
        return this.setting;
    }
}

