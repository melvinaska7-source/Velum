package velum.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import org.jetbrains.annotations.NotNull;

/** A non-editable setting used for live informational values. */
public final class ReadOnlyInfoSetting extends AbstractSetting {
    private String value;
    private final Supplier<String> displaySupplier;

    public ReadOnlyInfoSetting(@NotNull IIiiiIIiI_Class115 parent, String key, String initialValue) {
        this(parent, key, initialValue, null);
    }

    public ReadOnlyInfoSetting(@NotNull IIiiiIIiI_Class115 parent, String key, String initialValue,
                               Supplier<String> displaySupplier) {
        super(parent, key);
        this.value = initialValue;
        this.displaySupplier = displaySupplier;
    }

    public void setValue(String value) {
        if (Objects.equals(this.value, value)) return;
        this.value = value;
        this.notifyChanged();
    }

    public String getValue() {
        return value;
    }

    private String displayValue() {
        String supplied = displaySupplier == null ? null : displaySupplier.get();
        return supplied == null ? value : supplied;
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(value == null ? "" : value);
    }

    @Override
    public void fromJson(JsonElement jsonElement) {
        if (jsonElement != null && jsonElement.isJsonPrimitive()
                && jsonElement.getAsJsonPrimitive().isString()) {
            this.value = jsonElement.getAsString();
        }
    }

    @Override
    public iii_Class8 createComponent() {
        iii_Class8 component = new iii_Class8()
            .I_method_8939bffd(
                new iiII_Class13(
                    IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F),
                    () -> IiIiIIII_Class81.I_method_f25a980a(displayValue())
                ).i_method_6b618aeb().fill()
            )
            .I_method_3301fdd(iII_Class5.II_field_b583e68c)
            .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
            .I_method_7e2bb176(IIII.I_method_12c9a0cb(6.0F, 0.0F, 0.0F, 0.0F))
            .III_method_9dc17555();
        component.interactive(false);
        return component;
    }
}
