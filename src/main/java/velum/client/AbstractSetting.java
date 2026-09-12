package velum.client;

import java.util.function.BooleanSupplier;
import lombok.Generated;
import org.jetbrains.annotations.NotNull;
import velum.client.Setting;
import velum.client.IIiiiIIIi_Class114;
import velum.client.IIiiiIIiI_Class115;

public abstract class AbstractSetting
implements Setting {
    protected final String I_field_523beb0a;
    private final IIiiiIIiI_Class115 I_field_ba123a8c;
    @NotNull
    private final BooleanSupplier I_field_7bf34412;

    public AbstractSetting(@NotNull IIiiiIIiI_Class115 iIiiiIIiI_Class115, String string, @NotNull BooleanSupplier booleanSupplier) {
        this.I_field_ba123a8c = iIiiiIIiI_Class115;
        this.I_field_523beb0a = string;
        this.I_field_7bf34412 = booleanSupplier;
        this.registerWithOwner(iIiiiIIiI_Class115);
    }

    public AbstractSetting(@NotNull IIiiiIIiI_Class115 iIiiiIIiI_Class115, String string) {
        this(iIiiiIIiI_Class115, string, () -> false);
    }

    @Override
    public final void registerWithOwner(IIiiiIIiI_Class115 iIiiiIIiI_Class115) {
        iIiiiIIiI_Class115.getSettings().add(this);
    }

    public final void notifyChanged() {
        IIiiiIIIi_Class114.I_method_d0b91cc7(this);
        // Settings are serialized by the active config. This makes boolean/mode/slider/
        // color/text/keybind-setting changes persist even when the GUI is not closed.
        if (VelumClient.getInstance().I_method_5198232b() != null) {
            VelumClient.getInstance().I_method_5198232b().II_method_1fbeeff5();
            if (VelumClient.getInstance().I_method_7a4c3f0b().I_method_3272b401("client") instanceof IiIIiIiI_Class75 clientConfig) {
                clientConfig.scheduleAutoSave();
            }
        }
    }

    @Override
    public final String getDescriptionKey() {
        return this.getName() + ".description";
    }

    @Override
    @Generated
    public String getName() {
        return this.I_field_523beb0a;
    }

    @Generated
    public IIiiiIIiI_Class115 I_method_3ac66f15() {
        return this.I_field_ba123a8c;
    }

    @Override
    @NotNull
    @Generated
    public BooleanSupplier getHiddenCondition() {
        return this.I_field_7bf34412;
    }
}

