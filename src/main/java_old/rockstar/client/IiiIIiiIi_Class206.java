package rockstar.client;

import java.util.ArrayList;
import java.util.Iterator;
import lombok.Generated;
import net.minecraft.util.math.MathHelper;
import pyrock.utility.render.ColorRGBA;
import rockstar.client.III;
import rockstar.client.IIiIIi_Class10;
import rockstar.client.IIiIiI_Class11;
import rockstar.client.IIiii_Class8;
import rockstar.client.IiIiIIII_Class81;
import rockstar.client.iIIiIIiIi_Class294;
import rockstar.client.iIIiiiiiI_Class319;
import rockstar.client.iiiIiii_Class120;

public class IiiIIiiIi_Class206
implements iIIiIIiIi_Class294 {
    private static final String[] I_field_6dccaaa5 = new String[]{"image/mainmenu/day1", "image/mainmenu/day2", "image/mainmenu/day3", "image/mainmenu/day4", "image/mainmenu/day5", "image/mainmenu/day6", "image/mainmenu/night1", "image/mainmenu/night2", "image/mainmenu/night3"};
    private static final String[] i_field_6dccaaa5 = new String[]{"mainmenu.wallpaper.day1", "mainmenu.wallpaper.day2", "mainmenu.wallpaper.day3", "mainmenu.wallpaper.day4", "mainmenu.wallpaper.day5", "mainmenu.wallpaper.day6", "mainmenu.wallpaper.night1", "mainmenu.wallpaper.night2", "mainmenu.wallpaper.night3"};
    private boolean I_field_5a;
    private float I_field_46;
    private int I_field_49;
    private float i_field_46;
    private int i_field_49;
    private float II_field_46 = 1.7777778f;
    private boolean i_field_5a;
    private boolean II_field_5a;
    private double I_field_44;
    private double i_field_44;
    private double II_field_44;
    private float Ii_field_46;
    private int II_field_49 = 1;
    private int Ii_field_49 = 1;
    private long I_field_4a = System.currentTimeMillis();
    private int iI_field_49;

    public static int I_method_2d8cb612() {
        return 6;
    }

    public static int i_method_2d9b41f2() {
        return I_field_6dccaaa5.length;
    }

    public IiiIIiiIi_Class206(int n) {
        this.I_field_49 = MathHelper.clamp((int)n, (int)0, (int)(I_field_6dccaaa5.length - 1));
        this.i_field_46 = this.I_field_49;
        this.i_field_49 = this.I_field_49;
    }

    public boolean I_method_2d8cb623() {
        return this.I_field_5a || this.I_field_46 > 0.001f;
    }

    public void I_method_2d8cb61f() {
        this.I_field_5a = true;
        this.i_field_46 = this.I_field_49;
        this.i_field_49 = this.I_field_49;
        this.i_field_5a = false;
        this.II_field_5a = false;
    }

    public void i_method_2d9b41ff() {
        this.I_field_5a = false;
        this.i_field_5a = false;
        this.II_field_5a = false;
        this.i_field_49 = this.I_field_49;
    }

    public void I_method_493e8287(III iII, int n3, int n4, float f, float f2, float f3, float f4) {
        float f5;
        this.II_field_49 = Math.max(1, n3);
        this.Ii_field_49 = Math.max(1, n4);
        if (this.iI_field_49 < I_field_6dccaaa5.length) {
            iiiIiii_Class120.I_method_601d95f8(I_field_6dccaaa5[this.iI_field_49]);
            ++this.iI_field_49;
        }
        if ((f5 = iiiIiii_Class120.I_method_6de45459(I_field_6dccaaa5[this.I_field_49])) > 0.0f) {
            this.II_field_46 = f5;
        }
        long l = System.currentTimeMillis();
        float f6 = Math.min(0.1f, (float)(l - this.I_field_4a) / 1000.0f);
        this.I_field_4a = l;
        float f7 = 1.0f - (float)Math.pow(9.0E-4f, f6);
        float f8 = this.I_field_5a ? 1.0f : 0.0f;
        this.I_field_46 += (f8 - this.I_field_46) * f7;
        if (Math.abs(f8 - this.I_field_46) < 8.0E-4f) {
            this.I_field_46 = f8;
        }
        float f9 = this.I_field_46;
        if (!this.II_field_5a) {
            this.i_field_46 += ((float)this.i_field_49 - this.i_field_46) * f7;
            if (Math.abs((float)this.i_field_49 - this.i_field_46) < 8.0E-4f) {
                this.i_field_46 = this.i_field_49;
            }
        }
        float f10 = this.I_method_fd460aaf(n3, n4) * iIIiiiiiI_Class319.I_method_5f7ff5cf(1.05f - 0.05f * f3, 1.0, f9) * (1.0f - 0.045f * f4);
        float f11 = this.i_method_33e11e8f(n3, n4);
        float f12 = iIIiiiiiI_Class319.I_method_5f7ff5cf(f10, f11, f9);
        float f13 = f12 / this.II_field_46;
        float f14 = iIIiiiiiI_Class319.I_method_5f7ff5cf(0.0, f11 * 1.16f, f9);
        float f15 = (float)n3 / 2.0f - f * (1.0f - f9);
        float f16 = (float)n4 / 2.0f - f2 * (1.0f - f9) - 4.0f * f9;
        float f17 = 18.0f * f9;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < I_field_6dccaaa5.length; ++i) {
            arrayList.add(i);
        }
        arrayList.sort((n, n2) -> Float.compare(Math.abs((float)n2.intValue() - this.i_field_46), Math.abs((float)n.intValue() - this.i_field_46)));
        Iterator iterator = arrayList.iterator();
        while (iterator.hasNext()) {
            int n5 = (Integer)iterator.next();
            float f18 = (float)n5 - this.i_field_46;
            float f19 = Math.abs(f18);
            if (f19 > 1.85f) continue;
            float f20 = Math.min(1.0f, f19);
            float f21 = 1.0f - 0.14f * f20;
            float f22 = f12 * f21;
            float f23 = f13 * f21;
            float f24 = f15 + f18 * f14 - f22 / 2.0f;
            float f25 = f16 - f23 / 2.0f;
            float f26 = Math.max(f9, Math.max(0.0f, 1.0f - f19 * 6.0f));
            float f27 = (1.0f - 0.4f * f20) * f26;
            if (f27 <= 0.003f) continue;
            IIiii_Class8 iIiii_Class8 = IIiii_Class8.I_method_893b2757(f17);
            if (f9 > 0.01f) {
                iII.drawShadow(f24, f25 + 6.0f * f9, f22, f23, 16.0f * f9, iIiii_Class8, ColorRGBA.BLACK.withAlpha(150.0f * f9 * f27));
            }
            iII.drawRoundedTexture(iiiIiii_Class120.I_method_601d95f8(I_field_6dccaaa5[n5]), f24, f25, f22, f23, iIiii_Class8, ColorRGBA.WHITE.withAlpha(255.0f * f27));
            if (!(f20 > 0.003f)) continue;
            iII.drawRoundedRect(f24, f25, f22, f23, iIiii_Class8, ColorRGBA.BLACK.withAlpha(95.0f * f20 * f9));
        }
    }

    public void I_method_1146ee07(III iII, int n, int n2) {
        float f;
        float f2;
        float f3;
        float f4 = this.I_field_46;
        if (f4 <= 0.003f) {
            return;
        }
        IIiIIi_Class10 iIiIIi_Class10 = IIiIiI_Class11.Ii_field_857c0621.I_method_3a2d5e3(21.0f);
        float f5 = this.I_method_b87e5290(n, n2, iIiIIi_Class10) - 14.0f * (1.0f - f4);
        float f6 = 140.0f;
        for (int i = 0; i < I_field_6dccaaa5.length; ++i) {
            f3 = (float)i - this.i_field_46;
            if (Math.abs(f3) >= 1.0f) continue;
            f2 = 1.0f - Math.abs(f3);
            f2 *= f2;
            f = (float)n / 2.0f + f3 * f6;
            String string = IiIiIIII_Class81.I_method_f25a980a(i_field_6dccaaa5[i]);
            iII.drawCenteredTextWithShadow(iIiIIi_Class10, string, f, f5, ColorRGBA.WHITE.withAlpha(255.0f * f4 * f2), ColorRGBA.BLACK.withAlpha(130.0f * f4 * f2), 1.0f, 2.0f, 4.0f);
        }
        float f7 = (float)n2 - 26.0f;
        f3 = 10.0f;
        f2 = 6.0f;
        f = (float)n / 2.0f - (float)(I_field_6dccaaa5.length - 1) * f3 / 2.0f;
        for (int i = 0; i < I_field_6dccaaa5.length; ++i) {
            float f8 = 1.0f - Math.min(1.0f, Math.abs((float)i - this.i_field_46));
            float f9 = (0.3f + 0.7f * f8) * f4;
            iII.drawRoundedRect(f + (float)i * f3 - f2 / 2.0f, f7 - f2 / 2.0f, f2, f2, IIiii_Class8.I_method_893b2757(f2 / 2.0f), ColorRGBA.WHITE.withAlpha(255.0f * f9));
        }
    }

    public boolean I_method_ab3304c8(double d, double d2, int n) {
        if (!this.I_field_5a) {
            return false;
        }
        if (n == 1) {
            this.i_method_2d9b41ff();
            return true;
        }
        if (n != 0) {
            return true;
        }
        this.i_field_5a = true;
        this.II_field_5a = false;
        this.I_field_44 = d;
        this.i_field_44 = d2;
        this.II_field_44 = d;
        this.Ii_field_46 = this.i_field_46;
        return true;
    }

    public boolean i_method_47fa6ce8(double d, double d2, int n) {
        if (!this.I_field_5a || !this.i_field_5a || n != 0) {
            return this.I_field_5a;
        }
        if (Math.abs(d - this.I_field_44) > 4.0 || Math.abs(d2 - this.i_field_44) > 4.0) {
            this.II_field_5a = true;
        }
        if (this.II_field_5a) {
            float f = Math.max(1.0f, this.i_method_33e11e8f(this.II_field_49, this.Ii_field_49) * 1.16f);
            this.i_field_46 = MathHelper.clamp((float)(this.Ii_field_46 - (float)(d - this.II_field_44) / f), (float)0.0f, (float)((float)I_field_6dccaaa5.length - 1.0f));
            this.i_field_49 = MathHelper.clamp((int)Math.round(this.i_field_46), (int)0, (int)(I_field_6dccaaa5.length - 1));
        }
        return true;
    }

    public boolean II_method_5d11b2e5(double d, double d2, int n) {
        if (!this.I_field_5a || n != 0 || !this.i_field_5a) {
            this.i_field_5a = false;
            return this.I_field_5a;
        }
        this.i_field_5a = false;
        if (this.II_field_5a) {
            this.II_field_5a = false;
            this.i_field_49 = MathHelper.clamp((int)Math.round(this.i_field_46), (int)0, (int)(I_field_6dccaaa5.length - 1));
            return true;
        }
        int n2 = MathHelper.clamp((int)Math.round(this.i_field_46), (int)0, (int)(I_field_6dccaaa5.length - 1));
        float f = Math.max(1.0f, this.i_method_33e11e8f(this.II_field_49, this.Ii_field_49) * 1.16f);
        int n3 = MathHelper.clamp((int)Math.round(this.i_field_46 + (float)(d - (double)this.II_field_49 / 2.0) / f), (int)0, (int)(I_field_6dccaaa5.length - 1));
        if (n3 == n2 && this.I_method_fd43b223(d, d2)) {
            this.I_field_49 = n2;
            this.i_method_2d9b41ff();
        } else if (n3 != n2) {
            this.i_field_49 = n3;
        }
        return true;
    }

    public boolean I_method_840a6e03(double d) {
        if (!this.I_field_5a) {
            return false;
        }
        this.i_field_49 = MathHelper.clamp((int)(this.i_field_49 + (d > 0.0 ? -1 : 1)), (int)0, (int)(I_field_6dccaaa5.length - 1));
        return true;
    }

    public boolean I_method_840a80c8(int n) {
        if (!this.I_field_5a) {
            return false;
        }
        if (n == 0) {
            return true;
        }
        int n2 = this.II_field_5a ? Math.round(this.i_field_46) : this.i_field_49;
        this.i_field_49 = MathHelper.clamp((int)(n2 + Integer.signum(n)), (int)0, (int)(I_field_6dccaaa5.length - 1));
        this.i_field_5a = false;
        this.II_field_5a = false;
        return true;
    }

    private boolean I_method_fd43b223(double d, double d2) {
        float f = this.i_method_33e11e8f(this.II_field_49, this.Ii_field_49);
        float f2 = f / this.II_field_46;
        float f3 = (float)this.II_field_49 / 2.0f;
        float f4 = (float)this.Ii_field_49 / 2.0f - 4.0f;
        return d >= (double)(f3 - f / 2.0f) && d <= (double)(f3 + f / 2.0f) && d2 >= (double)(f4 - f2 / 2.0f) && d2 <= (double)(f4 + f2 / 2.0f);
    }

    private float I_method_b87e5290(int n, int n2, IIiIIi_Class10 iIiIIi_Class10) {
        float f = (float)n2 / 2.0f - 4.0f - this.i_method_33e11e8f(n, n2) / this.II_field_46 / 2.0f;
        float f2 = Math.min(42.0f, (float)n2 * 0.13f);
        float f3 = f - iIiIIi_Class10.iI_method_24b6c285() - MathHelper.clamp((float)((float)n2 * 0.045f), (float)10.0f, (float)28.0f);
        return Math.max(6.0f, Math.min(f2, f3));
    }

    private float I_method_fd460aaf(int n, int n2) {
        float f;
        float f2;
        float f3 = (float)n / Math.max(1.0f, (float)n2);
        if (f3 > this.II_field_46) {
            f2 = n;
            f = (float)n / this.II_field_46;
        } else {
            f = n2;
            f2 = (float)n2 * this.II_field_46;
        }
        float f4 = 28.0f;
        float f5 = Math.max(((float)n + f4 * 2.0f) / f2, ((float)n2 + f4 * 2.0f) / f);
        f5 = Math.max(f5, 1.06f);
        return f2 * f5;
    }

    private float i_method_33e11e8f(int n, int n2) {
        float f = (float)n * 0.52f;
        float f2 = (float)n2 * 0.62f;
        if (f / this.II_field_46 > f2) {
            f = f2 * this.II_field_46;
        }
        return f;
    }

    @Generated
    public boolean i_method_2d9b4203() {
        return this.I_field_5a;
    }

    @Generated
    public float I_method_2d8cb60f() {
        return this.I_field_46;
    }

    @Generated
    public int II_method_84190515() {
        return this.I_field_49;
    }
}

