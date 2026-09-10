package pyvelum.classes.settings;

import jep.python.PyCallable;
import lombok.Generated;
import pyvelum.classes.PyEspElement;
import pyvelum.classes.PyHudElement;
import pyvelum.classes.PyModule;
import velum.client.IIiiIIiii_Class104;
import velum.client.ButtonSetting;
import velum.client.IiIiiiiI_Class95;

public class PyButtonSetting {
    private final ButtonSetting setting;

    public PyButtonSetting(PyModule pyModule, String string) {
        this.setting = new ButtonSetting(pyModule.getModule(), string);
        if (!(pyModule.getModule() instanceof IiIiiiiI_Class95)) {
            IIiiIIiii_Class104.I_method_9fdd7f66(pyModule.getModule(), this.setting);
        }
    }

    public PyButtonSetting(PyHudElement pyHudElement, String string) {
        this.setting = new ButtonSetting(pyHudElement, string);
    }

    public PyButtonSetting(PyEspElement pyEspElement, String string) {
        this.setting = new ButtonSetting(pyEspElement.getElement(), string);
    }

    public PyButtonSetting(ButtonSetting iIiiiiIII_Class121) {
        this.setting = iIiiiiIII_Class121;
    }

    public PyButtonSetting action(PyCallable pyCallable) {
        this.setting.I_method_f05556b3(() -> {
            try {
                pyCallable.call(new Object[0]);
            }
            catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        return this;
    }

    public void click() {
        this.setting.I_method_7db574f5().run();
    }

    @Generated
    public ButtonSetting getSetting() {
        return this.setting;
    }
}

