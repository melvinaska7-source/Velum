package velum.client;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import pyvelum.events.render.HudRenderEvent;
import pyvelum.events.render.Render3DEvent;
import pyvelum.utility.render.ColorRGBA;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

public class IiiIiIiiI_Class215 extends IiiIIiiiI_Class207 implements IiiIiIIiI_Class211 {
   private static final IIii_Class4 I_field_f93678c1 = IIii_Class4.I_method_3682ece9(160L, IiiiIiiII_Class237.IIII_field_dd60aac);
   private static final float I_field_46 = 488.0F;
   private static final float i_field_46 = 318.0F;
   private static final float II_field_46 = 33.0F;
   private static final float Ii_field_46 = 101.0F;
   private static final float iI_field_46 = 24.0F;
   private static final float ii_field_46 = 11.0F;
   private static final float III_field_46 = 10.5F;
   private static final float IIi_field_46 = 2.0F;
   private static final float IiI_field_46 = 2.0F;
   private static final float Iii_field_46 = 22.0F;
   private static final float iII_field_46 = 5.5F;
   private static final float iIi_field_46 = 5.0F;
   private static final float iiI_field_46 = 72.0F;
   private static final float iii_field_46 = 5.0F;
   private static final float IIII_field_46 = 1.0F;
   private static final float IIIi_field_46 = 3.0F;
   private static final float IIiI_field_46 = 0.68F;
   private static final float IIii_field_46 = 5.0F;
   private static final float IiII_field_46 = 2.0F;
   private static final float IiIi_field_46 = -3.0F;
   private static final float IiiI_field_46 = -9.0F;
   private static final float Iiii_field_46 = 7.0F;
   private static final float iIII_field_46 = 5.0F;
   private static final float iIIi_field_46 = 90.0F;
   private static final float iIiI_field_46 = 17.0F;
   private static final float iIii_field_46 = 5.0F;
   private static final float iiII_field_46 = 7.0F;
   private static final float iiIi_field_46 = 6.0F;
   private static final float iiiI_field_46 = 300.0F;
   private static final float iiii_field_46 = 0.35F;
   private static final float IIIII_field_46 = 12.0F;
   private static final float IIIIi_field_46 = 300.0F;
   private static final float IIIiI_field_46 = 1600.0F;
   private static final float IIIii_field_46 = 0.4F;
   private final Map<ModuleEntry, iii_Class8> I_field_a567c40b = new IdentityHashMap<>();
   private final Map<Setting, iii_Class8> i_field_a567c40b = new IdentityHashMap<>();
   private ModuleCategory I_field_75f1ef41;
   private ModuleEntry I_field_75e36361;
   private ModuleEntry i_field_75e36361;
   private iii_Class8 I_field_b584628c;
   private float IIiII_field_46;
   private iii_Class8 i_field_b584628c;
   private iii_Class8 II_field_b584628c;
   private iii_Class8 Ii_field_b584628c;
   private iii_Class8 iI_field_b584628c;
   private Iii_Class4 I_field_b575d6ac;
   private Iii_Class4 i_field_b575d6ac;
   private IiIiIIIII_Class161 I_field_fdef2cc;
   private iiiI_Class15 I_field_fb07f0e1;
   private IIIIi_Class2 I_field_2d89a12c;
   private iiI_Class7 I_field_b5845eac;
   private iii_Class8 ii_field_b584628c;
   private long I_field_4a;
   private float IIiIi_field_46;
   private float IIiiI_field_46;
   private float IIiii_field_46;
   private float IiIII_field_46;
   private List<ModuleEntry> I_field_7865b31;
   private List<Setting> i_field_7865b31;
   private int I_field_49;
   private ModuleEntry II_field_75e36361;
   private String I_field_523beb0a;
   private float IiIIi_field_46;
   private long i_field_4a;
   private long II_field_4a;
   private long Ii_field_4a;
   Vec3d I_field_5bba0d50;
   private Vec3d i_field_5bba0d50;
   Vec3d II_field_5bba0d50;
   Vec3d Ii_field_5bba0d50;
   private float IiIiI_field_46;
   private float IiIii_field_46;
   private float IiiII_field_46;
   private float IiiIi_field_46;
   private float IiiiI_field_46;
   private float Iiiii_field_46;
   private float iIIII_field_46;
   private float iIIIi_field_46;
   static final iIiiIIiII_Class357 I_field_cb35e4c = new iIiiIIiII_Class357(false).I_method_cc37aab5();
   static boolean I_field_5a;
   static boolean i_field_5a;
   static IiiIiIiiI_Class215 I_field_a5e73e6c;

   @Override
   protected boolean lowDrawBatching() {
      return true;
   }

   public IiiIiIiiI_Class215() {
      this.I_field_75f1ef41 = ModuleCategory.COMBAT;
      this.IIiiI_field_46 = Float.NaN;
      this.IIiii_field_46 = Float.NaN;
      this.IiIII_field_46 = Float.NaN;
      this.I_field_7865b31 = List.of();
      this.i_field_7865b31 = List.of();
      this.I_field_49 = -1;
      this.I_field_523beb0a = "";
      IIIIi_Class2.III_method_92ee7b3f();
   }

   @Compile(
      obfuscation = 4
   )
   @Override
   public void init() {
      super.init();
      this.closing = false;
      this.contentAlpha = 1.0F;
      this.i_field_75e36361 = null;
      this.IiIIi_field_46 = 0.0F;
      this.i_field_4a = 0L;
      this.II_field_4a = System.currentTimeMillis();
      this.IIiiI_field_46 = Float.NaN;
      iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
      this.clearRoots();
      this.IiiII_field_46 = Math.min(488.0F, Math.max(360.0F, this.width - 12.0F));
      this.IiiIi_field_46 = Math.min(318.0F, Math.max(235.0F, this.height - 12.0F));
      this.IiIiI_field_46 = Math.round((this.width - this.IiiII_field_46) / 2.0F);
      this.IiIii_field_46 = Math.round((this.height - this.IiiIi_field_46) / 2.0F);
      this.IiiiI_field_46 = this.IiiII_field_46 * 33.0F / 488.0F;
      this.Iiiii_field_46 = this.IiiII_field_46 * 101.0F / 488.0F;
      this.iIIII_field_46 = this.IiiII_field_46 - this.IiiiI_field_46 - this.Iiiii_field_46;
      this.IIiII_field_46 = Math.max(80.0F, (this.iIIII_field_46 - 22.0F - 5.0F - 5.0F) / 2.0F);
      this.iIIIi_field_46 = this.IiiIi_field_46 * 24.0F / 318.0F;
      if (this.I_field_fdef2cc == null) {
         this.I_field_fdef2cc = new IiIiIIIII_Class161(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F));
         this.I_field_fdef2cc.II_method_e557b20c(IiIiIIII_Class81.I_method_f25a980a("search"));
      }

      iii_Class8 var1 = (new iii_Class8() {
            @Override
            protected void drawChildren(III var1, float var2) {
               iIiiiIiII_Class373.I_method_d5a5ee51(var1.getMatrices(), this.x(), this.y(), this.w(), this.h());
               super.drawChildren(var1, var2);
               iIiiiIiII_Class373.I_method_ff80a1df();
            }
         })
         .i_method_c88da8d5()
         .I_method_84672d76(IIii_Class4.II_field_f93678c1)
         .i_method_8c7d3515(this.IiiII_field_46, this.IiiIi_field_46)
         .I_method_f136b1d8((var1x, var2) -> this.I_method_c6137aff(var1x, var2));
      var1.iii_method_df275535();
      var1.snapSize();
      var1.snapAt(this.IiIiI_field_46, this.IiIii_field_46);
      var1.I_method_8939bffd(this.I_method_894df1d5());
      var1.I_method_8939bffd(this.i_method_97fcdb5());
      this.iI_field_b584628c = this.II_method_18597158();
      var1.I_method_8939bffd(this.iI_field_b584628c);
      this.Ii_field_b584628c = var1;
      this.add(var1);
      this.I_field_2d89a12c = new IIIIi_Class2(var1, this::I_method_74e4f766)
         .I_method_5a9bd0bd(this.i_field_b575d6ac)
         .I_method_562751de(this.I_field_b575d6ac, IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F));
      this.add(this.I_field_2d89a12c);
      this.add(this.I_field_2d89a12c.IIii_method_d1a53438());
      this.add(this.I_field_2d89a12c.IiII_method_6941c18());
      this.I_field_7865b31 = List.of();
      this.i_field_7865b31 = List.of();
      this.I_field_523beb0a = "\u0000";
      this.I_method_f6d12895(true);
      this.i_method_f89418b5(true);
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 I_method_894df1d5() {
      Iii_Class4 var1 = new Iii_Class4()
         .width(this.IiiiI_field_46)
         .height(this.iIIIi_field_46)
         .paint((var0, var1x) -> var0.drawIcon("logo", var1x.x() + var1x.w() / 2.0F - 5.5F, var1x.y() + 11.0F, 11.0F, IiiiiIIIi_Class242.ii_field_d0c8ec5))
         .draggable(iIi_Class6.i_field_b583ea6c);
      iii_Class8 var2 = new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(3.0F)
         .IIi_method_4dfc88d7(this.IiiiI_field_46);

      for (ModuleCategory var6 : ModuleCategory.values()) {
         Iii_Class4 var7 = new Iii_Class4()
            .size(17.0F, 17.0F)
            .padding(4.0F)
            .icon(
               "category/" + var6.I_method_b23d1194().toLowerCase(),
               9.0F,
               var1x -> this.I_method_af54788e()
                  .mix(IiiiiIIIi_Class242.ii_field_d0c8ec5, var1x.sig("selected"))
                  .mulAlpha(0.62F + 0.38F * var1x.sig("selected"))
            )
            .background(var1x -> I_method_f2c6fbd0(this.i_method_373b846e(), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.025F * var1x.hover()))
            .radius(4.0F)
            .bind("selected", () -> this.I_field_75f1ef41 == var6, I_field_f93678c1)
            .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
            .onClick(() -> this.I_method_f9822ea0(var6));
         var2.I_method_8939bffd(var7);
      }

      iii_Class8 var8 = new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(9.0F)
         .I_method_8939bffd(var1)
         .I_method_8939bffd(var2);
      this.i_field_b575d6ac = new Iii_Class4()
         .size(17.0F, 17.0F)
         .padding(4.0F)
         .icon("setting", 9.0F, var1x -> this.I_method_af54788e().mulAlpha(0.58F + 0.35F * var1x.hover()))
         .background(var1x -> I_method_f2c6fbd0(this.i_method_373b846e(), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.025F * var1x.hover()))
         .radius(4.0F)
         .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
         .onClick(this::i_method_9467d9ff);
      return new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 0.0F, 8.0F, 0.0F))
         .IIi_method_4dfc88d7(this.IiiiI_field_46)
         .IIi_method_1df35135()
         .I_method_8361fbbd(iIi_Class6.i_field_b583ea6c)
         .I_method_8939bffd(var8)
         .I_method_8939bffd(this.i_field_b575d6ac);
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 i_method_97fcdb5() {
      Iii_Class4 var1 = new Iii_Class4()
         .height(this.iIIIi_field_46)
         .fillWidth()
         .padding(IIII.I_method_12c9a0cb(5.0F, 8.0F, 1.0F, 8.0F))
         .text(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(9.0F), () -> this.I_field_75f1ef41.I_method_b23d1194(), var1x -> this.I_method_af54788e())
         .draggable(iIi_Class6.i_field_b583ea6c);
      this.I_field_b584628c = new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(2.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(1.0F, 5.0F, 5.0F, 4.0F))
         .IIi_method_4dfc88d7(this.Iiiii_field_46)
         .IiI_method_31d4c97(Math.max(0.0F, this.IiiIi_field_46 - this.iIIIi_field_46))
         .Ii_method_bb38d618()
         .I_method_3987f136(IiIi_Class6.I_field_f9448c81)
         .I_method_b65baecb(this::I_method_65a7e900);
      return new iii_Class8()
         .I_method_485bccf5()
         .IIi_method_4dfc88d7(this.Iiiii_field_46)
         .IiI_method_31d4c97(this.IiiIi_field_46)
         .I_method_8939bffd(var1)
         .I_method_8939bffd(this.I_field_b584628c);
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 II_method_18597158() {
      this.I_field_b575d6ac = new Iii_Class4()
         .size(81.0F, 12.0F)
         .radius(3.0F)
         .cursor(iIIIiIIIi_Class274.iI_field_aa52e62c)
         .onClick((var1x, var2x, var3x) -> {
            if (var1x == IiIII_Class9.I_field_2f4c8d6c && this.I_field_2d89a12c != null) {
               if (this.I_field_2d89a12c.II_method_57518866()) {
                  this.I_field_2d89a12c.I_method_df8f3415().I_method_400ceaa7(var2x, var3x, var1x);
               } else {
                  this.I_field_2d89a12c.II_method_57518862();
               }
            }
         })
         .paint(
            (var1x, var2x) -> {
               var1x.drawRoundedRect(
                  var2x.x(),
                  var2x.y(),
                  var2x.w(),
                  12.0F,
                  IIiii_Class8.I_method_893b2757(3.0F),
                  I_method_f2c6fbd0(this.II_method_1325cb91().withAlpha(173.40001F), this.I_method_af54788e(), 0.035F + 0.035F * var2x.hover())
               );
               var1x.drawIcon("search", var2x.x() + 3.5F, var2x.y() + 3.5F, 5.0F, this.I_method_af54788e().mulAlpha(0.48F));
               if (this.I_field_2d89a12c != null) {
                  IiIiIIIII_Class161 var3x = this.I_field_2d89a12c.I_method_df8f3415();
                  var3x.I_method_b043bcc(var2x.x() + 8.5F, var2x.y(), var2x.w() - 10.5F, 12.0F);
                  var3x.II_method_e557b20c(IiIiIIII_Class81.I_method_f25a980a("search"));
                  var3x.I_method_13a617e0(this.I_method_af54788e().mulAlpha(0.72F));
                  var3x.iI_method_a8609f1e(1.0F);
                  var3x.I_method_7ae26dda(var1x);
               }
            }
         );
      iii_Class8 var1 = (new iii_Class8() {
            @Override
            protected void drawChildren(III var1, float var2) {
               iIiiiIiII_Class373.I_method_d5a5ee51(var1.getMatrices(), this.x(), this.y() - 1.0F, this.w(), this.h() + 2.0F);
               super.drawChildren(var1, var2);
               iIiiiIiII_Class373.I_method_ff80a1df();
            }
         })
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.II_field_b5755e8c)
         .I_method_70a38517(1.0F)
         .i_method_8c7d3515(72.0F, 11.0F)
         .I_method_8939bffd(
            new Iii_Class4()
               .height(5.0F)
               .fillWidth()
               .text(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(6.0F), this::Ii_method_d800dfde, var1x -> this.I_method_af54788e())
               .textAlign(IIi_Class2.II_field_b5755e8c)
               .interactive(false)
         )
         .I_method_8939bffd(
            new Iii_Class4()
               .height(5.0F)
               .fillWidth()
               .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(6.0F), this::iI_method_1599839e, var1x -> this.I_method_af54788e().mulAlpha(0.52F))
               .textAlign(IIi_Class2.II_field_b5755e8c)
               .interactive(false)
         );
      Iii_Class4 var2 = new Iii_Class4()
         .size(12.0F, 12.0F)
         .interactive(false)
         .paint(
            (var0, var1x) -> var0.drawRoundedTexture(
               IIIIi_Class2.I_method_79e9d9ee(),
               var1x.x(),
               var1x.y(),
               var1x.w(),
               var1x.h(),
               IIiii_Class8.I_method_893b2757(var1x.w() / 2.0F),
               IiiiiIIIi_Class242.Ii_field_d0c8ec5
            )
         );
      iii_Class8 var3 = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(3.0F)
         .I_method_8939bffd(var1)
         .I_method_8939bffd(var2);
      iii_Class8 var4 = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 7.0F, 0.0F, 5.0F))
         .IiI_method_31d4c97(this.iIIIi_field_46)
         .III_method_9dc17555()
         .I_method_8361fbbd(iIi_Class6.i_field_b583ea6c)
         .I_method_8939bffd(this.I_field_b575d6ac)
         .I_method_8939bffd(var3);
      var4.snapSize();
      Iii_Class4 var5 = new Iii_Class4()
         .height(12.0F)
         .text(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(12.0F), this::i_method_a5af7881, var1x -> this.I_method_af54788e())
         .interactive(false);
      this.I_field_fb07f0e1 = new iiiI_Class15(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), this::I_method_94594e12, this::I_method_f6d0e8c4);
      iii_Class8 var6 = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .IiI_method_31d4c97(12.0F)
         .I_method_879bc687(() -> this.I_field_75e36361 != null)
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_8939bffd(this.I_field_fb07f0e1)
         .i_method_5db1deeb(this.I_field_fb07f0e1::I_method_10341dfa);
      iii_Class8 var7 = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(3.0F)
         .IiI_method_23cb1575()
         .I_method_8939bffd(var5)
         .I_method_8939bffd(var6);
      Iii_Class4 var8 = new Iii_Class4()
         .size(16.0F, 16.0F)
         .padding(3.5F)
         .icon("xmark", 9.0F, var1x -> this.I_method_af54788e().mulAlpha(0.8F + 0.2F * var1x.hover()))
         .radius(4.0F)
         .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
         .visibleWhen(() -> this.I_field_75e36361 != null)
         .onClick(() -> this.I_method_c2e71ac0(null));
      iii_Class8 var9 = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .IiI_method_31d4c97(12.0F)
         .III_method_9dc17555()
         .I_method_8939bffd(var7)
         .I_method_8939bffd(var8);
      iiII_Class13 var10 = new iiII_Class13(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), this::II_method_44e22fbe)
         .I_method_6da713d1(var1x -> this.I_method_af54788e().mulAlpha(0.58F))
         .i_method_6b618aeb()
         .II_method_4aef31a2();
      var10.interactive(false);
      iii_Class8 var11 = new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(1.0F)
         .IiI_method_31d4c97(22.0F)
         .III_method_9dc17555()
         .I_method_f136b1d8((var1x, var2x) -> this.i_method_f5052edf(var1x, var2x))
         .I_method_8939bffd(var9)
         .I_method_8939bffd(var10);
      this.i_field_b584628c = new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(5.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(27.5F, 0.0F, 2.0F, 0.0F))
         .III_method_9dc17555();
      this.i_field_b584628c.snapSize();
      float var12 = Math.max(0.0F, this.IiiIi_field_46 - this.iIIIi_field_46);
      float var13 = Math.max(80.0F, var12 - 10.5F - 2.0F);
      this.II_field_b584628c = new iii_Class8()
         .I_method_485bccf5()
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 5.0F, 0.0F, 0.0F))
         .IIi_method_4dfc88d7(this.iIIII_field_46 - 22.0F)
         .IiI_method_31d4c97(var13)
         .Ii_method_bb38d618()
         .II_method_d35e0034(80.0F)
         .I_method_3987f136(IiIi_Class6.I_field_f9448c81)
         .I_method_b65baecb(var1x -> {
            this.I_method_65a7e900(var1x);
            var1x.i_method_65326e43(-9.0F).I_method_4f0ff9cb(2.0F, 5.0F);
         })
         .I_method_8939bffd(this.i_field_b584628c);
      this.II_field_b584628c.snapSize();
      iii_Class8 var14 = new iii_Class8()
         .iI_method_c1109a58()
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(10.5F, 11.0F, 2.0F, 11.0F))
         .i_method_8c7d3515(this.iIIII_field_46, var12)
         .I_method_8939bffd(this.II_field_b584628c)
         .I_method_8939bffd(this.Ii_method_988b4d38())
         .I_method_8939bffd(this.iI_method_9e631178())
         .I_method_8939bffd(var11);
      var14.snapSize();
      iii_Class8 var15 = new iii_Class8()
         .I_method_485bccf5()
         .i_method_8c7d3515(this.iIIII_field_46, this.IiiIi_field_46)
         .I_method_8939bffd(var4)
         .I_method_8939bffd(var14);
      var15.snapSize();
      return var15;
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 Ii_method_988b4d38() {
      iii_Class8 var1 = new iii_Class8()
         .I_method_485bccf5()
         .I_method_4f3850da(2)
         .I_method_70a38517(5.0F)
         .I_method_8939bffd(this.I_method_358d385f("menu/swing", () -> "Swing Animations", iiIIIIII_Class193::new))
         .I_method_8939bffd(this.I_method_358d385f("menu/builder", () -> "Inventory Builder", IiiIiIiII_Class213::new))
         .I_method_8939bffd(this.I_method_358d385f("menu/esp", () -> "ESP", IiiiiIIi_Class122::new))
         .I_method_8939bffd(
            this.I_method_358d385f("menu/assist", () -> IiIiIIII_Class81.I_method_f25a980a("menu.modern.shortcuts.item_binds"), iIIiIiiI_Class151::new)
         )
         .I_method_8939bffd(this.I_method_358d385f("menu/autobuy", () -> "Auto Buy", IiiIIIiI_Class99::new))
         .I_method_8939bffd(
            this.I_method_358d385f(
               "menu/messager",
               () -> IiIiIIII_Class81.I_method_f25a980a("menu.modern.shortcuts.messenger"),
               () -> VelumClient.getInstance().I_method_449a4cbc()
            )
         );
      var1.snapSize();
      IIiIIi_Class10 var2 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F);
      Iii_Class4 var3 = new Iii_Class4()
         .text(var2, () -> IiIiIIII_Class81.I_method_f25a980a("menu.modern.shortcuts"), var1x -> this.I_method_af54788e().mulAlpha(0.9F))
         .interactive(false);
      return new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.i_field_f93600a1)
         .I_method_70a38517(6.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 0.0F, this.iIIIi_field_46 + 10.5F - 2.0F + var2.I_method_a649725c() + 6.0F, 0.0F))
         .IiI_method_23cb1575()
         .Ii_method_25479968(false)
         .I_method_e16e9925(() -> this.I_field_75e36361 == null, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .I_method_8939bffd(var3)
         .I_method_8939bffd(var1);
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 iI_method_9e631178() {
      Iii_Class4 var1 = new Iii_Class4()
         .text(
            IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F),
            () -> IiIiIIII_Class81.I_method_f25a980a("menu.modern.no_settings"),
            var1x -> this.I_method_af54788e().mulAlpha(0.55F)
         )
         .interactive(false);
      return new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.i_field_f93600a1)
         .IiI_method_23cb1575()
         .Ii_method_25479968(false)
         .I_method_e16e9925(this::i_method_9467da03, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .I_method_8939bffd(var1);
   }

   private boolean i_method_9467da03() {
      if (this.I_field_75e36361 == null) {
         return false;
      } else {
         for (Setting var2 : this.I_field_75e36361.getSettings()) {
            if (var2.isVisible()) {
               return false;
            }
         }

         return true;
      }
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 I_method_358d385f(String var1, Supplier<String> var2, Supplier<Screen> var3) {
      iii_Class8 var4 = new iii_Class8();
      Iii_Class4 var5 = new Iii_Class4()
         .fill()
         .fade()
         .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), var2, var2x -> this.I_method_af54788e().mulAlpha(0.72F + 0.28F * var4.hover()))
         .interactive(false);
      Iii_Class4 var6 = new Iii_Class4()
         .size(7.0F, 7.0F)
         .icon(var1, 7.0F, var2x -> this.I_method_af54788e().mulAlpha(0.62F + 0.38F * var4.hover()))
         .interactive(false);
      return var4.i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .i_method_8c7d3515(90.0F, 17.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 5.0F, 0.0F, 6.0F))
         .iI_method_e886414(4.0F)
         .I_method_e53800a9(
            var0 -> I_method_f2c6fbd0(IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.05F * var0.hover())
         )
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_8939bffd(var5)
         .I_method_8939bffd(var6)
         .I_method_c0ca36f3(() -> this.I_method_24b192a3((Screen)var3.get()));
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_24b192a3(Screen var1) {
      if (var1 != null) {
         if (this.I_field_fb07f0e1 != null) {
            this.I_field_fb07f0e1.I_method_d4d8842c();
         }

         MinecraftClient.getInstance().setScreen(var1);
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_c6137aff(III var1, iii_Class8 var2) {
      float var3 = var2.x();
      float var4 = var2.y();
      float var5 = var2.w();
      float var6 = var2.h();
      ColorRGBA var7 = this.Ii_method_9b0cd771();
      var1.drawClientRect(var3, var4, var5, var6, 1.0F, 0.0F, 2.0F, 12.0F, false, true);
      float var8 = var3 + this.IiiiI_field_46 - 1.0F;
      float var9 = var3 + this.IiiiI_field_46 + this.Iiiii_field_46 - 1.0F;
      var1.drawRect(var8, var4 + 1.0F, 1.0F, var6 - 2.0F, var7);
      var1.drawRect(var9, var4 + 1.0F, 1.0F, var6 - 2.0F, var7);
      var1.drawRect(var9 + 1.0F, var4 + this.iIIIi_field_46 - 1.0F, var5 - this.IiiiI_field_46 - this.Iiiii_field_46 - 1.0F, 1.0F, var7);
      if (!this.closing) {
         var1.drawRoundedBorder(var3, var4, var5, var6, 0.5F, IIiii_Class8.I_method_893b2757(12.0F), var7);
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void i_method_f5052edf(III var1, iii_Class8 var2) {
      if (!this.closing && this.I_field_75e36361 != null && this.i_field_b584628c != null) {
         iIiiiiIii_Class380.I_method_5f4b621f();
         iIiiIIiii_Class360.I_field_6424b12c.I_method_5711e8c1(1.0F);
         float var3 = var2.x() - 11.0F;
         float var4 = var2.x() + var2.w() + 11.0F;
         float var5 = var2.y() - 10.5F;
         float var6 = var2.h() + 10.5F + 22.0F;
         float var7 = 1.5F;
         float var8 = 0.5F;
         float var9 = 1.0F;
         IIiii_Class8 var10 = IIiii_Class8.I_method_893b2757(0.0F);
         ColorRGBA var11 = IiiiiIIIi_Class242.Ii_field_d0c8ec5;
         float var12 = 16.0F;
         float var13 = this.IiIiI_field_46 + var12;
         float var14 = this.IiIii_field_46 + var12;
         float var15 = this.IiiII_field_46 - 2.0F * var12;
         float var16 = this.IiiIi_field_46 - 2.0F * var12;
         iIiiiIiII_Class373.I_method_d5a5ee51(var1.getMatrices(), var3, var5 - 2.0F, var4 - var3, var6 + 6.0F);
         var1.drawBackdropBlur(var3, var5, var4 - var3, var6, var7, var8, var9, var13, var14, var15, var16, var10, var11);
         iIiiiIiII_Class373.I_method_ff80a1df();
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_f9822ea0(ModuleCategory var1) {
      if (this.I_field_75f1ef41 != var1) {
         if (this.I_field_fb07f0e1 != null) {
            this.I_field_fb07f0e1.I_method_d4d8842c();
         }

         this.I_field_75f1ef41 = var1;
         this.i_field_75e36361 = null;
         this.I_field_7865b31 = List.of();
         this.I_method_f6d12895(true);
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void i_method_9467d9ff() {
      if (this.I_field_2d89a12c != null) {
         this.I_field_2d89a12c.iI_method_59147882();
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_74e4f766(ModuleEntry var1, Setting var2) {
      if (var1 != null) {
         if (this.I_field_fb07f0e1 != null) {
            this.I_field_fb07f0e1.I_method_d4d8842c();
         }

         this.I_field_75f1ef41 = var1.getCategory();
         this.I_field_75e36361 = var1;
         this.I_field_7865b31 = List.of();
         this.i_field_7865b31 = List.of();
         this.I_method_f6d12895(true);
         this.i_method_f89418b5(true);
         if (this.II_field_b584628c != null) {
            this.II_field_b584628c.i_method_815a851f();
         }

         iiI_Class7 var3 = var2 != null ? this.i_field_a567c40b.get(var2) : this.I_field_a567c40b.get(var1);
         this.I_field_b5845eac = var3;
         this.ii_field_b584628c = var2 != null ? this.II_field_b584628c : this.I_field_b584628c;
         this.IIiIi_field_46 = var2 != null ? 7.0F : 3.0F;
         this.I_field_4a = var3 == null ? 0L : System.currentTimeMillis() + 1600L;
      }
   }

   @Override
   protected void afterRender(III var1) {
      this.I_method_f1003367(var1);
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_f1003367(III var1) {
      if (this.I_field_b5845eac != null) {
         long var2 = this.I_field_4a - System.currentTimeMillis();
         if (var2 > 0L && this.I_field_b5845eac.inFlow()) {
            float var4 = this.ii_field_b584628c == null ? 0.0F : this.ii_field_b584628c.Iii_method_61e30d8f();
            float var5 = this.I_field_b5845eac.x();
            float var6 = this.I_field_b5845eac.y() - var4;
            float var7 = this.I_field_b5845eac.w();
            float var8 = this.I_field_b5845eac.h();
            if (this.ii_field_b584628c != null) {
               float var9 = this.ii_field_b584628c.y();
               float var10 = this.ii_field_b584628c.y() + this.ii_field_b584628c.h();
               if (var6 < var9) {
                  var8 -= var9 - var6;
                  var6 = var9;
               }

               if (var6 + var8 > var10) {
                  var8 = var10 - var6;
               }
            }

            if (!(var7 <= 0.0F) && !(var8 <= 0.0F)) {
               float var11 = Math.min(1.0F, (float)var2 / 400.0F);
               var1.drawRoundedRect(
                  var5, var6, var7, var8, IIiii_Class8.I_method_893b2757(this.IIiIi_field_46), IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.18F * var11)
               );
            }
         } else {
            this.I_field_b5845eac = null;
         }
      }
   }

   private List<ModuleEntry> I_method_3cc8fa7(ModuleCategory var1) {
      return this.i_method_c69cda68().stream().filter(var1x -> var1x.getCategory() == var1).toList();
   }

   private List<ModuleEntry> i_method_c69cda68() {
      return VelumClient.getInstance()
         .getModuleManager()
         .getModules()
         .stream()
         .filter(ModuleEntry::isAvailable)
         .sorted(Comparator.comparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER))
         .toList();
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_f6d12895(boolean var1) {
      if (this.I_field_b584628c != null) {
         this.II_method_f6df6d22();
         String var2 = IiiIiiIiI_Class219.I_method_12a37157(this.I_field_fdef2cc == null ? "" : this.I_field_fdef2cc.I_method_d2579001());
         List var3 = var2.isEmpty() ? this.I_method_3cc8fa7(this.I_field_75f1ef41) : this.i_method_c69cda68();
         if (var1 || !I_method_77f02a05(this.I_field_7865b31, var3) || !var2.equals(this.I_field_523beb0a)) {
            ArrayList var4 = new ArrayList(var3);
            if (!var2.isEmpty()) {
               IdentityHashMap var5 = new IdentityHashMap();

               for (ModuleEntry var7 : (Iterable<ModuleEntry>)(Iterable<?>)var4) {
                  var5.put(var7, IiiIiiIiI_Class219.i_method_ca495fc6(IiiIiiIiI_Class219.I_method_12a37157(var7.getName()), var2));
               }

               var4.removeIf(var1x -> (Integer)var5.get(var1x) == Integer.MAX_VALUE);
               var4.sort(
                  Comparator.<ModuleEntry>comparingInt(var1x -> (Integer)var5.get(var1x))
                     .thenComparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER)
               );
            }

            this.I_method_9b0fe91a(var2, var4);
            ArrayList var8 = new ArrayList();

            for (ModuleEntry var10 : (Iterable<ModuleEntry>)(Iterable<?>)var4) {
               var8.add(this.I_field_a567c40b.computeIfAbsent(var10, this::I_method_bf3023f6));
            }

            this.I_field_b584628c.II_method_244860a9(var8);
            this.I_field_7865b31 = new ArrayList<>(var3);
            this.I_field_523beb0a = var2;
         }
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_9b0fe91a(String var1, List<ModuleEntry> var2) {
      boolean var3 = !this.I_field_523beb0a.isEmpty() && !this.I_field_523beb0a.equals("\u0000");
      if (var1.isEmpty()) {
         if (var3 && this.II_field_75e36361 != null && this.I_field_75e36361 != this.II_field_75e36361) {
            this.I_method_c2e71ac0(this.II_field_75e36361);
         }

         this.II_field_75e36361 = null;
      } else {
         if (!var3) {
            this.II_field_75e36361 = this.I_field_75e36361;
         }

         ModuleEntry var4 = var2.isEmpty() ? null : (ModuleEntry)var2.getFirst();
         if (this.I_field_75e36361 != var4) {
            this.I_method_c2e71ac0(var4);
         }
      }
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 I_method_bf3023f6(ModuleEntry var1) {
      Iii_Class4 var2 = new Iii_Class4()
         .fill()
         .fade()
         .text(
            IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F),
            () -> this.I_method_f93b1e0(var1),
            var1x -> this.I_method_af54788e()
               .mix(IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.5F * var1x.sig("enabled"))
               .mulAlpha(0.6F + 0.3F * var1x.sig("enabled") + 0.1F * var1x.sig("selected"))
         )
         .bind("enabled", var1::isEnabled, I_field_f93678c1)
         .bind("selected", () -> this.I_field_75e36361 == var1, I_field_f93678c1)
         .interactive(false);
      return new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .IiI_method_31d4c97(15.0F)
         .III_method_9dc17555()
         .I_method_7e2bb176(IIII.I_method_b1e865cb(0.0F, 5.0F))
         .iI_method_e886414(3.0F)
         .I_method_e53800a9(
            var0 -> I_method_f2c6fbd0(
               IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F),
               IiiiiIIIi_Class242.ii_field_d0c8ec5,
               0.08F * var0.sig("enabled") + 0.018F * var0.sig("selected") + 0.025F * var0.hover()
            )
         )
         .I_method_350d9c12("enabled", var1::isEnabled, I_field_f93678c1)
         .I_method_350d9c12("selected", () -> this.I_field_75e36361 == var1, I_field_f93678c1)
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_8939bffd(var2)
         .I_method_b60c4df8((var2x, var3, var4) -> this.I_method_cd0360e6(var1, var2x));
   }

   private String I_method_f93b1e0(ModuleEntry var1) {
      if (this.i_field_75e36361 != var1) {
         return var1.getName();
      } else {
         int var2 = iIIIIIIII_Class257.I_method_e761de12();
         if (var2 != 0) {
            return IiIiIIII_Class81.I_method_f25a980a("key") + ": " + iIIIIIIII_Class257.I_method_f90bbc5c(var2) + "...";
         } else {
            return var1.getKeybind() == -1
               ? IiIiIIII_Class81.I_method_f25a980a("menu.binding")
               : IiIiIIII_Class81.I_method_f25a980a("key") + ": " + iIIIIiIiI_Class267.I_method_a937749c(var1.getKeybind());
         }
      }
   }

   private void I_method_cd0360e6(ModuleEntry var1, IiIII_Class9 var2) {
      switch (var2) {
         case I_field_2f4c8d6c:
            var1.toggle();
            break;
         case i_field_2f4c8d6c:
            if (this.I_field_75e36361 != var1) {
               this.I_method_c2e71ac0(var1);
            }
            break;
         case II_field_2f4c8d6c:
            this.i_field_75e36361 = this.i_field_75e36361 == var1 ? null : var1;
      }
   }

   private void I_method_c2e71ac0(ModuleEntry var1) {
      if (this.I_field_fb07f0e1 != null) {
         this.I_field_fb07f0e1.I_method_d4d8842c();
      }

      this.I_field_75e36361 = var1;
      this.i_field_75e36361 = null;
      this.i_field_7865b31 = List.of();
      this.i_method_f89418b5(true);
      if (this.II_field_b584628c != null) {
         this.II_field_b584628c.i_method_815a851f();
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void II_method_f6df6d22() {
      int var1 = ModuleManager.I_method_7056a45f();
      if (var1 != this.I_field_49) {
         this.I_field_49 = var1;
         List var2 = VelumClient.getInstance().getModuleManager().getModules();
         if (this.i_field_75e36361 != null && !I_method_878ece75(var2, this.i_field_75e36361)) {
            this.i_field_75e36361 = null;
         }

         if (this.II_field_75e36361 != null && !I_method_878ece75(var2, this.II_field_75e36361)) {
            this.II_field_75e36361 = I_method_fce419a6(var2, this.II_field_75e36361);
         }

         IdentityHashMap var3 = new IdentityHashMap();

         for (ModuleEntry var5 : (Iterable<ModuleEntry>)(Iterable<?>)var2) {
            var3.put(var5, Boolean.TRUE);
         }

         this.I_field_a567c40b.keySet().removeIf(var1x -> !var3.containsKey(var1x));
         if (this.I_field_75e36361 != null && !var3.containsKey(this.I_field_75e36361)) {
            for (Setting var7 : this.i_field_7865b31) {
               this.i_field_a567c40b.remove(var7);
            }

            this.I_method_c2e71ac0(I_method_fce419a6(var2, this.I_field_75e36361));
         }
      }
   }

   private static boolean I_method_878ece75(List<ModuleEntry> var0, ModuleEntry var1) {
      for (ModuleEntry var3 : var0) {
         if (var3 == var1) {
            return true;
         }
      }

      return false;
   }

   private static ModuleEntry I_method_fce419a6(List<ModuleEntry> var0, ModuleEntry var1) {
      for (ModuleEntry var3 : var0) {
         if (var3.getCategory() == var1.getCategory() && var3.getName().equals(var1.getName())) {
            return var3;
         }
      }

      return null;
   }

   @Compile(
      obfuscation = 1
   )
   private void i_method_f89418b5(boolean var1) {
      if (this.i_field_b584628c != null) {
         List var2 = this.I_field_75e36361 == null ? List.of() : this.I_field_75e36361.getSettings();
         if (var1 || !I_method_77f02a05(this.i_field_7865b31, var2)) {
            if (!this.i_field_7865b31.isEmpty()) {
               IdentityHashMap var3 = new IdentityHashMap();

               for (Setting var5 : (Iterable<Setting>)(Iterable<?>)var2) {
                  var3.put(var5, Boolean.TRUE);
               }

               for (Setting var13 : this.i_field_7865b31) {
                  if (!var3.containsKey(var13)) {
                     this.i_field_a567c40b.remove(var13);
                  }
               }
            }

            ArrayList var10 = new ArrayList();
            ArrayList var12 = new ArrayList();
            ArrayList var14 = new ArrayList();
            ArrayList var6 = new ArrayList();
            int var7 = 0;

            for (Setting var9 : (Iterable<Setting>)(Iterable<?>)var2) {
               if (var9 instanceof SectionSetting) {
                  this.I_method_29cefde3(var10, var12, var14, var6);
                  var7 = 0;
                  var10.add(this.i_field_a567c40b.computeIfAbsent(var9, this::I_method_2396b05d));
               } else {
                  (var7++ % 2 == 0 ? var12 : var14).add(this.i_field_a567c40b.computeIfAbsent(var9, this::I_method_2396b05d));
                  var6.add(var9);
               }
            }

            this.I_method_29cefde3(var10, var12, var14, var6);
            this.i_field_b584628c.i_method_d2e03546(var10);
            this.i_field_7865b31 = new ArrayList<>(var2);
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }
   }

   @Compile(
      obfuscation = 1
   )
   private void I_method_29cefde3(List<iiI_Class7> var1, List<iiI_Class7> var2, List<iiI_Class7> var3, List<Setting> var4) {
      if (!var2.isEmpty() || !var3.isEmpty()) {
         List var5 = List.copyOf(var4);
         iii_Class8 var6 = new iii_Class8().I_method_485bccf5().I_method_70a38517(5.0F).IIi_method_4dfc88d7(this.IIiII_field_46).I_method_ef221966(var2);
         iii_Class8 var7 = new iii_Class8().I_method_485bccf5().I_method_70a38517(5.0F).IIi_method_4dfc88d7(this.IIiII_field_46).I_method_ef221966(var3);
         var6.snapSize();
         var7.snapSize();
         iii_Class8 var8 = new iii_Class8()
            .i_method_c88da8d5()
            .I_method_70a38517(5.0F)
            .I_method_483797dd(IIi_Class2.I_field_b5755e8c)
            .III_method_9dc17555()
            .I_method_e16e9925(() -> I_method_f37dbd14(var5), IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
            .IIiI_method_185ffdf8()
            .I_method_8939bffd(var6)
            .I_method_8939bffd(var7);
         var8.snapSize();
         var1.add(var8);
         var2.clear();
         var3.clear();
         var4.clear();
      }
   }

   private static boolean I_method_f37dbd14(List<Setting> var0) {
      for (Setting var2 : var0) {
         if (var2.isVisible()) {
            return true;
         }
      }

      return false;
   }

   private iii_Class8 I_method_2396b05d(Setting var1) {
      return var1 instanceof SectionSetting ? this.i_method_94fa687d(var1) : this.II_method_734a96fa(var1);
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 i_method_94fa687d(Setting var1) {
      iii_Class8 var2 = IiiIiiIIi_Class218.i_method_f520787d(var1);
      var2.I_method_7e2bb176(IIII.I_field_f935fcc1);
      var2.snapSize();
      iii_Class8 var3 = new iii_Class8()
         .I_method_485bccf5()
         .III_method_9dc17555()
         .I_method_e16e9925(var1::isVisible, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .IIiI_method_185ffdf8()
         .I_method_8939bffd(var2);
      var3.snapSize();
      return var3;
   }

   @Compile(
      obfuscation = 1
   )
   private iii_Class8 II_method_734a96fa(Setting var1) {
      iii_Class8 var2 = IiiIiiIIi_Class218.i_method_f520787d(var1);
      var2.snapSize();
      iii_Class8 var3 = new iii_Class8()
         .I_method_485bccf5()
         .III_method_9dc17555()
         .I_method_7e2bb176(IIII.II_method_714316ac(4.0F))
         .iI_method_e886414(7.0F)
         .I_method_e53800a9(var0 -> IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F))
         .I_method_e16e9925(var1::isVisible, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .IIiI_method_185ffdf8()
         .I_method_8939bffd(var2);
      var3.snapSize();
      return var3;
   }

   private String i_method_a5af7881() {
      return this.I_field_75e36361 == null ? "" : IiIiIIII_Class81.I_method_1410d1e5("menu.modern.settings", this.I_field_75e36361.getName());
   }

   private String II_method_44e22fbe() {
      return this.I_field_75e36361 == null ? "" : this.I_field_75e36361.i_method_bf522194();
   }

   private String Ii_method_d800dfde() {
      String var1 = Profile.getUsername();
      return var1 == null ? "" : var1;
   }

   private String iI_method_1599839e() {
      return "LEEK";
   }

   private int I_method_94594e12() {
      return this.I_field_75e36361 == null ? -1 : this.I_field_75e36361.getKeybind();
   }

   private void I_method_f6d0e8c4(int var1) {
      if (this.I_field_75e36361 != null) {
         this.I_field_75e36361.setKeybind(var1);
         this.III_method_e51d2e7f();
      }
   }

   @Compile(
      obfuscation = 1
   )
   @Override
   public void render(III var1) {
      this.Ii_method_f6edf902();
      this.iI_method_f8a25d42();
      GuiMoveModule.iII_method_9983ebf();
      if (!this.closing) {
         this.I_method_f6d12895(false);
         this.i_method_f89418b5(false);
      }

      if (this.II_field_b584628c != null) {
         float var2 = this.II_field_b584628c.Iii_method_61e30d8f();
         if (var2 != this.IIiiI_field_46) {
            this.IIiiI_field_46 = var2;
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }

      if (System.currentTimeMillis() - this.II_field_4a < 400L) {
         iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
      }

      if (this.Ii_field_b584628c != null) {
         this.IiIiI_field_46 = this.Ii_field_b584628c.x();
         this.IiIii_field_46 = this.Ii_field_b584628c.y();
         if (this.IiIiI_field_46 != this.IIiii_field_46 || this.IiIii_field_46 != this.IiIII_field_46) {
            this.IIiii_field_46 = this.IiIiI_field_46;
            this.IiIII_field_46 = this.IiIii_field_46;
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }

      float var4 = this.closing ? 1.0F : 0.7F + 0.3F * this.ii_method_f8b0e912();
      boolean var3 = Math.abs(var4 - 1.0F) > 1.0E-4F;
      if (var3) {
         var1.getMatrices().push();
         var1.getMatrices().translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
         var1.getMatrices().scale(var4, var4, 1.0F);
         var1.getMatrices().translate(-this.width / 2.0F, -this.height / 2.0F, 0.0F);
      }

      IiiIiIIIi_Class210.I_method_bd87019f(this, var1);
      super.render(var1);
      IiiIiIIIi_Class210.i_method_789a4d7f(this, var1);
      if (var3) {
         var1.getMatrices().pop();
      }
   }

   @Override
   public String I_method_8c872841() {
      return "modern";
   }

   @Override
   public float I_method_9833b22f() {
      return this.ii_method_f8b0e912();
   }

   @Override
   public float i_method_98423e0f() {
      return this.closing ? this.III_method_e51d2e6f() : 0.0F;
   }

   @Override
   public boolean I_method_9833b243() {
      return this.closing;
   }

   @Override
   public float II_method_6e518cf2() {
      return this.contentAlpha;
   }

   @Override
   public float Ii_method_6e6018d2() {
      return this.closing ? 1.0F : 0.7F + 0.3F * this.ii_method_f8b0e912();
   }

   @Override
   public List<IiiIiIIiI_Class211.Nested1_972f6c40> I_method_cb1d7a28() {
      return List.of(new IiiIiIIiI_Class211.Nested1_972f6c40("window", this.IiIiI_field_46, this.IiIii_field_46, this.IiiII_field_46, this.IiiIi_field_46));
   }

   public static IiiIiIiiI_Class215 I_method_6fb072f5() {
      return I_field_a5e73e6c;
   }

   private float ii_method_f8b0e912() {
      float var1 = Math.min(1.0F, Math.max(0.0F, (float)(System.currentTimeMillis() - this.II_field_4a) / 300.0F));
      return IiiiIiiII_Class237.I_field_dd60aac.ease(var1, 0.0F, 1.0F, 1.0F);
   }

   private void Ii_method_f6edf902() {
      long var1 = System.currentTimeMillis();
      if (this.closing) {
         this.IiIIi_field_46 = 0.0F;
         this.i_field_4a = var1;
         this.contentAlpha = 1.0F;
      } else {
         float var3 = this.i_field_4a == 0L ? 16.0F : Math.min(64.0F, (float)(var1 - this.i_field_4a));
         this.i_field_4a = var1;
         MenuModule var4 = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
         int var5 = var4 != null && var4.I_method_20c0695() != null ? var4.I_method_20c0695().I_method_fa6281d2() : -1;
         float var6 = I_method_f6d0e8c8(var5) ? 1.0F : 0.0F;
         float var7 = var3 / 300.0F;
         if (this.IiIIi_field_46 < var6) {
            this.IiIIi_field_46 = Math.min(var6, this.IiIIi_field_46 + var7);
         } else if (this.IiIIi_field_46 > var6) {
            this.IiIIi_field_46 = Math.max(var6, this.IiIIi_field_46 - var7);
         }

         this.contentAlpha = 1.0F - IiiiIiiII_Class237.III_field_dd60aac.ease(this.IiIIi_field_46, 0.0F, 1.0F, 1.0F);
      }
   }

   private void iI_method_f8a25d42() {
      if (!this.closing && !(this.contentAlpha >= 0.999F)) {
         long var1 = MinecraftClient.getInstance().getWindow().getHandle();
         boolean var3 = GLFW.glfwGetMouseButton(var1, 0) == 1;
         if (!var3 || iiI_Class7.spotlight() == null) {
            iii_Class8 var4 = null;

            for (Entry var6 : this.i_field_a567c40b.entrySet()) {
               iii_Class8 var7 = (iii_Class8)var6.getValue();
               if (var7.inFlow() && var7.hovered() && IiiIiiIIi_Class218.I_method_120ab0ab((Setting)var6.getKey())) {
                  var4 = var7;
                  break;
               }
            }

            iiI_Class7.spotlight(var4);
         }
      } else {
         iiI_Class7.spotlight(null);
      }
   }

   private static boolean I_method_f6d0e8c8(int var0) {
      return iIIIIIIII_Class257.II_method_98333a25(var0);
   }

   public void tick() {
      GuiMoveModule.iII_method_9983ebf();
      super.tick();
   }

   @Compile(
      obfuscation = 1
   )
   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (iiiI_Class15.i_method_3a56361e(var5)) {
         this.III_method_e51d2e7f();
      } else {
         boolean var6 = this.overlays.stream().anyMatch(var4 -> var4.alive() && var4.contains((float)var1, (float)var3));
         if (this.I_field_2d89a12c != null && !var6) {
            this.I_field_2d89a12c.I_method_911c871f((float)var1, (float)var3);
         }

         if (this.i_field_75e36361 != null) {
            if (var5 == IiIII_Class9.I_field_2f4c8d6c) {
               this.i_field_75e36361 = null;
               return;
            }

            if (var5 != IiIII_Class9.II_field_2f4c8d6c) {
               this.i_field_75e36361.setKeybind(iIIIIIIII_Class257.Ii_method_99f62a34(var5.I_method_6d899712()));
               this.i_field_75e36361 = null;
               this.III_method_e51d2e7f();
               return;
            }
         }

         super.onMouseClicked(var1, var3, var5);
      }
   }

   @Override
   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      if (this.i_field_75e36361 != null) {
         int var4 = iIIIIIIII_Class257.II_method_6e347cf5(keyCode, modifiers);
         if (var4 != Integer.MIN_VALUE) {
            this.i_field_75e36361.setKeybind(var4);
            this.i_field_75e36361 = null;
            this.III_method_e51d2e7f();
            return true;
         }
      }

      return super.keyReleased(keyCode, scanCode, modifiers);
   }

   @Override
   public void onMouseReleased(double var1, double var3, IiIII_Class9 var5) {
      boolean var6 = var5 == IiIII_Class9.I_field_2f4c8d6c && this.Ii_field_b584628c != null && this.Ii_field_b584628c.dragging();
      if (this.I_field_fdef2cc != null) {
         this.I_field_fdef2cc.i_method_6a097ac7(var1, var3, var5);
      }

      super.onMouseReleased(var1, var3, var5);
      if (var6) {
         this.ii_method_f8b0e922();
      }
   }

   private void ii_method_f8b0e922() {
      float var1 = this.Ii_field_b584628c.x();
      float var2 = this.Ii_field_b584628c.y();
      float var3 = this.Ii_field_b584628c.w();
      float var4 = this.Ii_field_b584628c.h();
      float var5 = Math.max(0.0F, var1);
      float var6 = Math.max(0.0F, var2);
      float var7 = Math.min((float)this.width, var1 + var3);
      float var8 = Math.min((float)this.height, var2 + var4);
      float var9 = Math.max(0.0F, var7 - var5);
      float var10 = Math.max(0.0F, var8 - var6);
      float var11 = Math.max(1.0F, var3 * var4);
      float var12 = 1.0F - var9 * var10 / var11;
      if (!(var12 < 0.35F)) {
         float var13 = Math.round((this.width - var3) / 2.0F);
         float var14 = Math.round((this.height - var4) / 2.0F);
         this.Ii_field_b584628c.iI_method_7009d1d8(var13, var14);
      }
   }

   @Compile(
      obfuscation = 1
   )
   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.i_field_75e36361 == null && !iiiI_Class15.i_method_d4e71010()) {
         if (Screen.hasControlDown() && keyCode == 90 && IIiiiIIIi_Class114.I_method_fe3ce603()) {
            return true;
         }

         if (Screen.hasControlDown() && keyCode == 89 && IIiiiIIIi_Class114.i_method_fe4b71e3()) {
            return true;
         }
      }

      if (this.i_field_75e36361 != null) {
         if (keyCode != 256 && keyCode != 261) {
            int var4 = iIIIIIIII_Class257.i_method_cd0c4692(keyCode, modifiers);
            if (var4 == Integer.MIN_VALUE) {
               return true;
            } else {
               this.i_field_75e36361.setKeybind(var4);
               this.i_field_75e36361 = null;
               this.III_method_e51d2e7f();
               return true;
            }
         } else {
            this.i_field_75e36361.setKeybind(-1);
            this.i_field_75e36361 = null;
            this.III_method_e51d2e7f();
            return true;
         }
      } else if (this.I_field_2d89a12c != null && !this.I_field_2d89a12c.II_method_57518866() && Screen.hasControlDown() && keyCode == 70) {
         this.I_field_2d89a12c.II_method_57518862();
         return true;
      } else if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      } else if (MenuModule.I_method_48514ce8(keyCode)) {
         this.close();
         return true;
      } else {
         return false;
      }
   }

   @Compile(
      obfuscation = 1
   )
   public void close() {
      if (!this.closing) {
         if (this.I_field_fdef2cc != null) {
            this.I_field_fdef2cc.I_method_fb5b3035(false);
         }

         SoundsModule var1 = VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class);
         if (var1 != null && var1.isEnabled()) {
            iiIiIIIII_Class417.I_field_34b2ba6c.I_method_7289b65f(var1.I_method_45ee5ef(), 1.0F);
         }

         MenuModule var2 = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
         if (var2 != null && var2.isEnabled()) {
            var2.disable();
         }

         super.close();
      }
   }

   @Override
   public void removed() {
      this.I_method_94594e1f();
      super.removed();
   }

   @Compile(
      obfuscation = 1
   )
   public void I_method_94594e1f() {
      if (!this.closing) {
         this.closing = true;
         iiI_Class7.spotlight(null);
         this.III_method_e51d2e7f();
         this.Ii_field_4a = System.currentTimeMillis();
         I_field_5a = false;
         i_field_5a = false;
         MinecraftClient var1 = MinecraftClient.getInstance();
         Camera var2 = var1.gameRenderer.getCamera();
         if (var2 != null && var1.player != null) {
            double var3 = Math.toRadians(var2.getYaw());
            double var5 = Math.toRadians(var2.getPitch());
            Vec3d var7 = new Vec3d(-Math.sin(var3) * Math.cos(var5), -Math.sin(var5), Math.cos(var3) * Math.cos(var5)).normalize();
            this.i_field_5bba0d50 = var7;
            this.II_field_5bba0d50 = var7.crossProduct(new Vec3d(0.0, 1.0, 0.0)).normalize();
            this.Ii_field_5bba0d50 = this.II_field_5bba0d50.crossProduct(var7).normalize();
            this.I_field_5bba0d50 = var2.getPos().add(var7.multiply(1.5));
         }

         I_field_a5e73e6c = this;
      }
   }

   float III_method_e51d2e6f() {
      return Math.min(1.0F, Math.max(0.0F, (float)(System.currentTimeMillis() - this.Ii_field_4a) / 1600.0F));
   }

   static void I_method_8bb2cef1(HudRenderEvent var0) {
      MinecraftClient var1 = MinecraftClient.getInstance();
      float var2 = var1.getWindow().getScaledWidth();
      float var3 = var1.getWindow().getScaledHeight();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      RenderSystem.setShaderTexture(0, I_field_cb35e4c.getColorAttachment());
      Matrix4f var4 = var0.getContext().getMatrices().peek().getPositionMatrix();
      int var5 = ColorRGBA.WHITE.getRGB();
      BufferBuilder var6 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      var6.vertex(var4, 0.0F, 0.0F, 0.0F).texture(0.0F, 1.0F).color(var5);
      var6.vertex(var4, 0.0F, var3, 0.0F).texture(0.0F, 0.0F).color(var5);
      var6.vertex(var4, var2, var3, 0.0F).texture(1.0F, 0.0F).color(var5);
      var6.vertex(var4, var2, 0.0F, 0.0F).texture(1.0F, 1.0F).color(var5);
      BufferRenderer.drawWithGlobalProgram(var6.end());
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.disableBlend();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void III_method_e51d2e7f() {
      VelumClient.getInstance().I_method_5198232b().II_method_1fbeeff5();
      VelumClient.getInstance().I_method_7a4c3f0b().II_method_d8a14015();
   }

   private ColorRGBA I_method_af54788e() {
      return IiiiiIIIi_Class242.iI_method_8e08d3b1();
   }

   private ColorRGBA i_method_373b846e() {
      return IiiiiIIIi_Class242.II_method_190e6391().withAlpha(102.0F);
   }

   private ColorRGBA II_method_1325cb91() {
      return this.i_method_373b846e();
   }

   private ColorRGBA Ii_method_9b0cd771() {
      return IiiiiIIIi_Class242.IiI_field_d0c8ec5.withAlpha(89.25F);
   }

   private void I_method_65a7e900(IiII_Class5 var1) {
      var1.i_method_65326e43(-3.0F)
         .II_method_e6aaaac(2.0F)
         .I_method_2a080a63(2.0F)
         .Ii_method_49950e8c(18.0F)
         .iI_method_388cc2cc(1.0F)
         .ii_method_73b726ac(1100.0F)
         .i_method_989a5f71(
            var1x -> this.I_method_af54788e().withAlpha(255.0F * (0.28F + 0.24F * var1x.I_method_af833bdc() + 0.28F * var1x.i_method_af91c7bc()))
         );
   }

   private static ColorRGBA I_method_f2c6fbd0(ColorRGBA var0, ColorRGBA var1, float var2) {
      return var0.mix(var1.withAlpha(var0.getAlpha()), var2);
   }

   private static boolean I_method_77f02a05(List<?> var0, List<?> var1) {
      if (var0.size() != var1.size()) {
         return false;
      } else {
         for (int var2 = 0; var2 < var0.size(); var2++) {
            if (var0.get(var2) != var1.get(var2)) {
               return false;
            }
         }

         return true;
      }
   }

   private static String I_method_5d441d97(String var0) {
      if (var0 != null && !var0.isBlank()) {
         String var1 = var0.toLowerCase();
         return Character.toUpperCase(var1.charAt(0)) + var1.substring(1);
      } else {
         return "";
      }
   }

   @Override
   public boolean shouldPause() {
      return false;
   }

   @Override
   public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
   }

   static {
      VelumClient.getInstance().I_method_7897deab().I_method_2257cd48(new IiiIiIiiI_Class215.Nested1_98f25c60());
   }

   static final class Nested1_98f25c60 {
      final IiIIIiII_Class69<HudRenderEvent> I_field_3d936f41 = var0 -> {
         IiiIiIiiI_Class215 var1 = IiiIiIiiI_Class215.I_field_a5e73e6c;
         if (var1 != null) {
            MinecraftClient var2 = MinecraftClient.getInstance();
            if (var1.III_method_e51d2e6f() >= 1.0F) {
               IiiIiIiiI_Class215.I_field_a5e73e6c = null;
               IiiIiIiiI_Class215.I_field_5a = false;
               IiiIiIiiI_Class215.i_field_5a = false;
            } else if (var2.currentScreen == null) {
               if (!IiiIiIiiI_Class215.I_field_5a) {
                  IiiIiIiiI_Class215.I_field_cb35e4c.setClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                  IiiIiIiiI_Class215.I_field_cb35e4c.I_method_53fdf4b5(true);
                  iIiiiiIii_Class380.I_field_5a = true;

                  try {
                     var1.render(III.I_method_5728d20f(var0.getContext(), -1, -1, var0.getTickDelta()));
                  } finally {
                     iIiiiiIii_Class380.I_field_5a = false;
                     IiiIiIiiI_Class215.I_field_cb35e4c.i_method_766109df();
                  }

                  IiiIiIiiI_Class215.I_field_5a = true;
               }

               if (!IiiIiIiiI_Class215.i_field_5a) {
                  IiiIiIiiI_Class215.I_method_8bb2cef1(var0);
               }
            }
         }
      };
      final IiIIIiII_Class69<Render3DEvent> i_field_3d936f41 = IiIIIiII_Class69.I_method_aec3ff5f(
         Integer.MIN_VALUE,
         var0 -> {
            IiiIiIiiI_Class215 var1 = IiiIiIiiI_Class215.I_field_a5e73e6c;
            if (var1 != null && IiiIiIiiI_Class215.I_field_5a && var1.I_field_5bba0d50 != null) {
               MinecraftClient var2 = MinecraftClient.getInstance();
               float var3 = var1.III_method_e51d2e6f();
               float var4 = Math.min(1.0F, var3 / 0.4F);
               float var5 = var3 <= 0.4F ? 0.0F : (var3 - 0.4F) / 0.6F;
               float var6 = IiiiIiiII_Class237.iI_field_dd60aac.ease(var4, 0.0F, 1.0F, 1.0F);
               float var7 = 1.0F - var6;
               float var8 = Math.min(1.0F, Math.max(0.0F, (var5 - 0.15F) / 0.85F));
               float var10 = (float)(
                  Math.tan(Math.toRadians(((Integer)var2.options.getFov().getValue()).intValue()) / 2.0) / Math.tan(Math.toRadians(110.0) / 2.0)
               );
               float var11 = (3.3F + var7) * var10;
               float var12 = var11 * ((float)var2.getWindow().getFramebufferWidth() / var2.getWindow().getFramebufferHeight());
               Vec3d var13 = var2.gameRenderer.getCamera().getPos();
               Vec3d var14 = var1.I_field_5bba0d50;
               Vec3d var15 = var1.II_field_5bba0d50.multiply(var12 / 2.0);
               Vec3d var16 = var1.Ii_field_5bba0d50.multiply(var11 / 2.0);
               Vec3d var17 = var14.subtract(var15).add(var16).subtract(var13);
               Vec3d var18 = var14.subtract(var15).subtract(var16).subtract(var13);
               Vec3d var19 = var14.add(var15).subtract(var16).subtract(var13);
               Vec3d var20 = var14.add(var15).add(var16).subtract(var13);
               RenderSystem.enableBlend();
               RenderSystem.defaultBlendFunc();
               RenderSystem.disableDepthTest();
               RenderSystem.disableCull();
               RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
               RenderSystem.setShaderTexture(0, IiiIiIiiI_Class215.I_field_cb35e4c.getColorAttachment());
               Matrix4f var21 = var0.getMatrices().peek().getPositionMatrix();
               int var22 = ColorRGBA.WHITE.withAlpha(255.0F * var7).getRGB();
               BufferBuilder var23 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
               var23.vertex(var21, (float)var17.x, (float)var17.y, (float)var17.z).texture(0.0F, 1.0F).color(var22);
               var23.vertex(var21, (float)var18.x, (float)var18.y, (float)var18.z).texture(0.0F, 0.0F).color(var22);
               var23.vertex(var21, (float)var19.x, (float)var19.y, (float)var19.z).texture(1.0F, 0.0F).color(var22);
               var23.vertex(var21, (float)var20.x, (float)var20.y, (float)var20.z).texture(1.0F, 1.0F).color(var22);
               BufferRenderer.drawWithGlobalProgram(var23.end());
               IiiIiIiiI_Class215.i_field_5a = true;
               RenderSystem.enableDepthTest();
               RenderSystem.enableCull();
               RenderSystem.disableBlend();
               RenderSystem.setShaderTexture(0, 0);
               RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
               if (var3 >= 1.0F) {
                  IiiIiIiiI_Class215.I_field_a5e73e6c = null;
                  IiiIiIiiI_Class215.I_field_5a = false;
               }
            }
         }
      );
   }
}
