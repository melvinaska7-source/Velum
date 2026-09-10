package velum.client;

import lombok.Generated;
import pyvelum.utility.render.ColorRGBA;
import pyvelum.utility.render.Rect;
import velum.client.III;
import velum.client.InterfaceModule;
import velum.client.IIiii_Class8;
import velum.client.VelumClient;
import velum.client.IiiiIiIii_Class236;
import velum.client.IiiiIiiII_Class237;
import velum.client.iIIIiIIIi_Class274;
import velum.client.iIIIiIIiI_Class275;

public class IiiIIiIiI_Class203
extends Rect {
    private final String I_field_523beb0a;
    private final float I_field_46;
    private final Runnable I_field_bac0a97e;
    private final ColorRGBA I_field_d0c8ec5 = new ColorRGBA(58.0f, 58.0f, 58.0f);
    private final IiiiIiIii_Class236 I_field_dc7facc = new IiiiIiIii_Class236(400L, 0.0f, IiiiIiiII_Class237.I_field_dd60aac);
    private final IiiiIiIii_Class236 i_field_dc7facc = new IiiiIiIii_Class236(300L, 0.0f, IiiiIiiII_Class237.III_field_dd60aac);

    public void I_method_b0823999(III iII, float f) {
        boolean bl;
        boolean bl2 = bl = this.hovered(iII.I_method_b1c3e152(), iII.i_method_b1d26d32()) && this.I_field_dc7facc.I_method_6ac4da6f() == 1.0f;
        if (bl && f > 0.5f) {
            iIIIiIIiI_Class275.I_method_e7d43867(iIIIiIIIi_Class274.i_field_aa52e62c);
        }
        this.i_field_dc7facc.I_method_edd72835(bl);
        if (InterfaceModule.IiI_method_c4cbfac3()) {
            iII.drawLiquidGlass(this.x - 1.0f, this.y - 1.0f, this.width + 2.0f, this.height + 2.0f, 7.0f, 0.08f, IIiii_Class8.I_method_893b2757(Math.min(this.width, this.height) / 2.0f), ColorRGBA.WHITE.withAlpha(255.0f * this.I_field_dc7facc.I_method_6ac4da6f() * f));
            iII.drawRoundedRect(this.x, this.y, this.width, this.height, IIiii_Class8.I_method_893b2757(Math.min(this.width, this.height) / 2.0f), this.I_field_d0c8ec5.withAlpha(255.0f * (0.15f * this.I_field_dc7facc.I_method_6ac4da6f() + 0.15f * this.i_field_dc7facc.I_method_6ac4da6f()) * f));
        } else if (InterfaceModule.iII_method_f9a41e83()) {
            iII.drawRoundedRect(this.x, this.y, this.width, this.height, IIiii_Class8.I_method_893b2757(Math.min(this.width, this.height) / 2.0f), this.I_field_d0c8ec5.withAlpha(255.0f * (0.33f * this.I_field_dc7facc.I_method_6ac4da6f() + 0.2f * this.i_field_dc7facc.I_method_6ac4da6f()) * f));
        }
        iII.drawTexture(VelumClient.id(this.I_field_523beb0a), this.x + (this.width - this.I_field_46) / 2.0f, this.y + (this.height - this.I_field_46) / 2.0f, this.I_field_46, this.I_field_46, ColorRGBA.WHITE.withAlpha(255.0f * this.I_field_dc7facc.I_method_6ac4da6f() * f));
    }

    public void I_method_f3cf064(double d, double d2, int n) {
        if (this.hovered(d, d2) && n == 0 && this.I_field_dc7facc.I_method_6ac4da6f() == 1.0f) {
            this.I_field_bac0a97e.run();
        }
    }

    @Generated
    public IiiIIiIiI_Class203(String string, float f, Runnable runnable) {
        this.I_field_523beb0a = string;
        this.I_field_46 = f;
        this.I_field_bac0a97e = runnable;
    }

    @Generated
    public IiiiIiIii_Class236 I_method_86c997b5() {
        return this.I_field_dc7facc;
    }
}

