package rockstar.client;

import globals.client.WorldKey;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.util.math.MathHelper;
import pyrock.utility.render.ColorRGBA;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

public class IiiIIiIii_Class204 extends ii_Class4 implements iIIiIIiIi_Class294 {
   private static boolean I_field_5a;
   private static final List<IiiIIiIiI_Class203> I_field_7865b31 = new ArrayList<>();
   private static IiiIIiiIi_Class206 I_field_710ea66c;
   private static IiiIIiiII_Class205 I_field_710ea28c;
   private static final long I_field_4a = 1500L;
   private boolean i_field_5a;
   private long i_field_4a;
   private double I_field_44;
   private double i_field_44;
   private float I_field_46;
   private boolean II_field_5a;
   private final IiiiIiIii_Class236 I_field_dc7facc = new IiiiIiIii_Class236(800L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac);
   private final ColorRGBA I_field_d0c8ec5 = new ColorRGBA(171.0F, 254.0F, 255.0F);
   private final ColorRGBA i_field_d0c8ec5 = new ColorRGBA(203.0F, 254.0F, 255.0F);
   private final long II_field_4a = this.I_field_dc7facc.I_method_6ac4da73();
   private final iiIiIIiii_Class424 I_field_991c1e8c = new iiIiIIiii_Class424();
   private float i_field_46;
   private float II_field_46;
   private long Ii_field_4a = System.currentTimeMillis();
   private final iiIiIIiii_Class424 i_field_991c1e8c = new iiIiIIiii_Class424();
   private boolean Ii_field_5a;
   private final IiiiIiiIi_Class238 I_field_dd60e8c = new IiiiIiiIi_Class238(300L, new ColorRGBA(255.0F, 255.0F, 255.0F), IiiiIiiII_Class237.Iii_field_dd60aac);

   @Compile(
      obfuscation = 4
   )
   public void init() {
      String var1 = "image/mainmenu/icons/";
      I_method_f0183f35();
      I_method_f0183b55();
      if (!I_field_5a) {
         I_field_7865b31.clear();
         I_field_7865b31.add(new IiiIIiIiI_Class203(var1 + "single.png", 12.0F, () -> I_field_3a9bda27.setScreen(new SelectWorldScreen(this))));
         I_field_7865b31.add(new IiiIIiIiI_Class203(var1 + "multi.png", 12.0F, () -> I_field_3a9bda27.setScreen(new MultiplayerScreen(this))));
         I_field_7865b31.add(
            new IiiIIiIiI_Class203(var1 + "settings.png", 12.0F, () -> I_field_3a9bda27.setScreen(new OptionsScreen(this, I_field_3a9bda27.options)))
         );
         I_field_7865b31.add(new IiiIIiIiI_Class203(var1 + "quit.png", 14.0F, () -> {
            if (RockstarClient.getInstance().getModuleManager().getModule(SoundsModule.class).Ii_method_54c1f4f3().isSelected()) {
               iiIIiiiIi_Class414.iI_method_8d7ec562();
            }

            I_field_3a9bda27.stop();
         }));
         I_field_5a = true;

         try {
            if (RockstarClient.getInstance().getModuleManager().getModule(SoundsModule.class).isEnabled()
               && RockstarClient.getInstance().getModuleManager().getModule(SoundsModule.class).II_method_4eb9f913().isSelected()) {
               iiIIiiiIi_Class414.i_method_fc4deddf();
            }
         } catch (Throwable var3) {
            RockstarClient.I_field_ab0f6068
               .warn(
                  "\u0433\u043e\u043b\u043e\u0441 \u043f\u0440\u0438\u0432\u0435\u0442\u0441\u0442\u0432\u0438\u044f \u043d\u0435 \u043f\u0440\u043e\u0438\u0433\u0440\u0430\u043b\u0441\u044f: {}",
                  var3.toString()
               );
         }
      }

      IIIIi_Class2.III_method_92ee7b3f();
      RockstarClient.getInstance()
         .I_method_cd3d46d0()
         .info(I_field_3a9bda27.getSession().getUsername(), "", "", "", WorldKey.local().world(), "all", Profile.getUsername());
      RockstarClient.getInstance().I_method_cd3d46d0().update("main_menu");
      super.init();
   }

   private static IiiIIiiIi_Class206 I_method_f0183f35() {
      if (I_field_710ea66c == null) {
         int var0 = LocalTime.now().getHour();
         boolean var1 = var0 >= 6 && var0 < 19;
         int var2 = IiiIIiiIi_Class206.I_method_2d8cb612();
         int var3 = IiiIIiiIi_Class206.i_method_2d9b41f2() - var2;
         int var4 = var1 ? ThreadLocalRandom.current().nextInt(var2) : var2 + ThreadLocalRandom.current().nextInt(Math.max(1, var3));
         I_field_710ea66c = new IiiIIiiIi_Class206(var4);
      }

      return I_field_710ea66c;
   }

   private static IiiIIiiII_Class205 I_method_f0183b55() {
      if (I_field_710ea28c == null) {
         I_field_710ea28c = new IiiIIiiII_Class205(() -> I_method_f0183f35().I_method_2d8cb61f());
      }

      return I_field_710ea28c;
   }

   @Override
   public void render(III var1) {
      IIiIIi_Class10 var2 = IIiIiI_Class11.ii_field_857c0621.I_method_3a2d5e3(65.0F);
      IIiIIi_Class10 var3 = IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(16.0F);
      IIiIIi_Class10 var4 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(10.0F);
      float var5 = 1.0F - I_method_f0183f35().I_method_2d8cb60f();
      float var6 = 80.0F;
      float var7 = 205.0F * (1.0F - this.I_field_dc7facc.I_method_6ac4da6f()) * var5;
      float var8 = iIIiiiiiI_Class319.I_method_5f7ff5cf(var6, -120.0, this.I_field_dc7facc.I_method_6ac4da6f());
      this.I_field_dc7facc.I_method_edd72835(this.II_field_5a);
      var1.drawRoundedRect(0.0F, 0.0F, this.width, this.height, IIiii_Class8.I_field_2d98a52c, ColorRGBA.BLACK);
      long var9 = System.currentTimeMillis();
      float var11 = Math.min(0.1F, (float)(var9 - this.Ii_field_4a) / 1000.0F);
      this.Ii_field_4a = var9;
      this.I_method_fca6301b(var9, var11);
      float var12 = this.width / 2.0F;
      float var13 = this.height / 2.0F;
      float var14 = (10.0F + 6.0F * this.I_field_dc7facc.I_method_6ac4da6f()) * var5;
      float var15 = MathHelper.clamp((var1.I_method_b1c3e152() - var12) / Math.max(1.0F, var12), -1.0F, 1.0F) * var14;
      float var16 = MathHelper.clamp((var1.i_method_b1d26d32() - var13) / Math.max(1.0F, var13), -1.0F, 1.0F) * var14;
      float var17 = 1.0F - (float)Math.pow(0.0025F, var11);
      this.i_field_46 = this.i_field_46 + (var15 - this.i_field_46) * var17;
      this.II_field_46 = this.II_field_46 + (var16 - this.II_field_46) * var17;
      I_method_f0183f35()
         .I_method_493e8287(var1, this.width, this.height, this.i_field_46, this.II_field_46, this.I_field_dc7facc.I_method_6ac4da6f(), this.I_field_46);
      boolean var18 = I_field_3a9bda27.getOverlay() != null;
      float var19 = var18 ? 0.5F : 1.4F;
      float var20 = (var18 ? 1.0F : 1.0F - this.I_field_dc7facc.I_method_6ac4da6f()) * var5;
      if (var20 > 0.01F) {
         iIiiIIiii_Class360.I_field_6425294c.I_method_533784a1(var19);
         var1.drawBlurredRect(0.0F, 0.0F, this.width, this.height, 15.0F, IIiii_Class8.I_field_2d98a52c, ColorRGBA.WHITE.withAlpha(255.0F * var20));
      }

      float var21 = Math.min(130.0F, this.height * 0.38F);
      var1.drawRoundedRect(
         0.0F,
         this.height - var21,
         this.width,
         var21,
         IIiii_Class8.I_field_2d98a52c,
         new IiiII_Class13(new ColorRGBA(0.0F, 0.0F, 0.0F, 0.0F), new ColorRGBA(0.0F, 0.0F, 0.0F, (int)(90.0F * var5)))
      );
      if (this.i_field_991c1e8c.I_method_58432069(250L)) {
         double var22 = I_field_3a9bda27.getWindow().getScaleFactor();
         float var24 = var6 + var2.I_method_a649725c() * 0.5F;
         ColorRGBA var25 = ColorRGBA.fromPixel(this.width / 2.0F * (float)var22, I_field_3a9bda27.getWindow().getHeight() - var24 * (float)var22);
         this.Ii_field_5a = (var25.getRed() + var25.getGreen() + var25.getBlue()) / 3.0F > 120.0F;
         this.i_field_991c1e8c.I_method_23e11e3f();
      }

      this.I_field_dd60e8c.I_method_8895420(this.Ii_field_5a ? new ColorRGBA(28.0F, 28.0F, 30.0F) : ColorRGBA.WHITE);
      ColorRGBA var40 = this.I_field_dd60e8c.I_method_915b8ae().withAlpha(var7);
      String var23 = iIIIIiIiI_Class267.II_method_6d1277fe();
      var1.drawCenteredText(var3, var23, this.width / 2.0F, var8 - 23.0F, var40);
      String var41 = iIIIIiIiI_Class267.i_method_8e352841();
      String[] var42 = var41.split(":");
      String var26 = var42.length > 0 ? var42[0] : var41;
      String var27 = var42.length > 1 ? var42[1] : "";
      float var28 = var2.I_method_2c375926(var26);
      float var29 = var2.I_method_2c375926(":");
      float var30 = var2.I_method_2c375926(var27);
      float var31 = var28 + var29 + var30;
      float var32 = this.width / 2.0F - var31 / 2.0F;
      float var33 = 1.0F + 0.008F * (float)Math.sin((float)var9 * 0.00185F);
      iIiiiIIiI_Class371.II_method_e18635ac(var1.getMatrices(), this.width / 2.0F, var8 + var2.I_method_a649725c() / 2.0F, var33);
      float var34 = var8 - var2.I_method_a649725c() * 0.1F;
      var1.drawText(var2, var26, var32, var8, var40);
      var1.drawText(var2, ":", var32 + var28, var34, var40);
      var1.drawText(var2, var27, var32 + var28 + var29, var8, var40);
      iIiiiIIiI_Class371.I_method_10503b11(var1.getMatrices());
      float var35 = (1.0F - this.I_field_dc7facc.I_method_6ac4da6f()) * var5;
      float var36 = 6.0F * this.I_field_dc7facc.I_method_6ac4da6f();
      var1.drawCenteredText(
         var4, IiIiIIII_Class81.I_method_f25a980a("mainmenu.next"), this.width / 2.0F, this.height - 70 + var36, ColorRGBA.WHITE.withAlpha(180.0F * var35)
      );
      if (this.height > 400) {
         float var37 = 26.0F;
         float var38 = this.width / 2.0F - var37 / 2.0F;
         float var39 = this.height - 54 + var36;
         if (IIIIi_Class2.Ii_method_57601446()) {
            var1.drawRoundedTexture(
               IIIIi_Class2.I_method_79e9d9ee(),
               var38,
               var39,
               var37,
               var37,
               IIiii_Class8.I_method_893b2757(var37 / 2.0F),
               ColorRGBA.WHITE.withAlpha(255.0F * var35)
            );
         } else {
            var1.drawRoundedRect(var38, var39, var37, var37, IIiii_Class8.I_method_893b2757(var37 / 2.0F), ColorRGBA.WHITE.withAlpha(45.0F * var35));
         }
      }

      var1.drawCenteredText(
         IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(11.0F),
         Profile.getUsername(),
         this.width / 2.0F,
         this.height - 22 + var36,
         ColorRGBA.WHITE.withAlpha(255.0F * var35)
      );
      float var43 = 0.0F;

      for (IiiIIiIiI_Class203 var45 : I_field_7865b31) {
         var45.I_method_86c997b5()
            .I_method_edd72835(
               I_field_7865b31.size() - I_field_7865b31.indexOf(var45) > (1.0F - this.I_field_dc7facc.I_method_6ac4da6f()) * I_field_7865b31.size() + 0.5F
            );
         var45.set(
            this.width / 2.0F - 69.0F + var43,
            (this.height > 500 ? this.height / 2.0F + 20.0F : this.height / 1.25F) - 5.0F - 10.0F * var45.I_method_86c997b5().I_method_6ac4da6f(),
            30.0F,
            30.0F
         );
         var43 += var45.getWidth() + 6.0F;
         var45.I_method_b0823999(var1, var5);
      }

      if (this.I_method_ce2e8263()) {
         RockstarClient.getInstance().I_method_35687482().I_method_35128395().render(var1, var5);
      }

      I_method_f0183f35().I_method_1146ee07(var1, this.width, this.height);
      I_method_f0183b55().I_method_3e377827(var1, this.width, this.height, var5, this.I_field_dc7facc.I_method_6ac4da6f());
   }

   private void I_method_fca6301b(long var1, float var3) {
      if (this.i_field_5a && !I_method_f0183f35().i_method_2d9b4203() && var1 - this.i_field_4a >= 1500L) {
         this.i_field_5a = false;
         this.II_field_5a = false;
         I_method_f0183f35().I_method_2d8cb61f();
      }

      float var4 = this.i_field_5a && !I_method_f0183f35().i_method_2d9b4203() ? MathHelper.clamp((float)(var1 - this.i_field_4a) / 1500.0F, 0.0F, 1.0F) : 0.0F;
      this.I_field_46 = this.I_field_46 + (var4 - this.I_field_46) * (1.0F - (float)Math.pow(6.0E-4F, var3));
   }

   @Compile(
      obfuscation = 1
   )
   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (I_method_f0183f35().i_method_2d9b4203()) {
         I_method_f0183f35().I_method_ab3304c8(var1, var3, var5.I_method_6d899712());
      } else if (!I_method_f0183b55().I_method_22a524a8(var1, var3, var5.I_method_6d899712())) {
         if (!this.I_method_ce2e8263()
            || !RockstarClient.getInstance().I_method_35687482().I_method_35128395().I_method_60a3f448((float)var1, (float)var3, var5.I_method_6d899712())) {
            for (IiiIIiIiI_Class203 var7 : I_field_7865b31) {
               if (var7.hovered(var1, var3) && var7.I_method_86c997b5().I_method_6ac4da6f() == 1.0F) {
                  var7.I_method_f3cf064(var1, var3, var5.I_method_6d899712());
                  return;
               }
            }

            if (var5.I_method_6d899712() == 0) {
               this.i_field_5a = true;
               this.i_field_4a = System.currentTimeMillis();
               this.I_field_44 = var1;
               this.i_field_44 = var3;
            }

            super.onMouseClicked(var1, var3, var5);
         }
      }
   }

   @Override
   public void onMouseReleased(double var1, double var3, IiIII_Class9 var5) {
      if (I_method_f0183f35().i_method_2d9b4203()) {
         I_method_f0183f35().II_method_5d11b2e5(var1, var3, var5.I_method_6d899712());
      } else {
         if (this.i_field_5a && var5.I_method_6d899712() == 0) {
            this.i_field_5a = false;
            if (System.currentTimeMillis() - this.i_field_4a < 1500L && this.I_field_991c1e8c.I_method_58432069(this.II_field_4a)) {
               this.II_field_5a = !this.II_field_5a;
               this.I_field_991c1e8c.I_method_23e11e3f();
            }
         }
      }
   }

   @Override
   public void onMouseDragged(double var1, double var3, IiIII_Class9 var5, double var6, double var8) {
      if (I_method_f0183f35().i_method_2d9b4203()) {
         I_method_f0183f35().i_method_47fa6ce8(var1, var3, var5.I_method_6d899712());
      } else {
         if (this.i_field_5a && (Math.abs(var1 - this.I_field_44) > 6.0 || Math.abs(var3 - this.i_field_44) > 6.0)) {
            this.i_field_5a = false;
         }
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (I_method_f0183f35().i_method_2d9b4203()) {
         I_method_f0183f35().I_method_840a6e03(verticalAmount);
         return true;
      } else {
         return super.mouseScrolled(mouseX, mouseY, horizontalAmount, verticalAmount);
      }
   }

   @Compile(
      obfuscation = 1
   )
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (I_method_f0183f35().i_method_2d9b4203()) {
         if (keyCode == 256) {
            I_method_f0183f35().i_method_2d9b41ff();
         }

         if (keyCode == 263) {
            I_method_f0183f35().I_method_840a80c8(-1);
         }

         if (keyCode == 262) {
            I_method_f0183f35().I_method_840a80c8(1);
         }

         return true;
      } else if (I_method_f0183b55().I_method_22ede008(keyCode, scanCode, modifiers)) {
         return true;
      } else {
         if (keyCode == 69) {
            RockstarClient.getInstance().I_method_9a720c62().I_method_bcf65f();
         }

         if (keyCode == 82) {
            MinecraftClient.getInstance().setScreen(new MultiplayerScreen(this));
         }

         if (keyCode == 84) {
            MinecraftClient.getInstance().setScreen(new SelectWorldScreen(this));
         }

         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   private boolean I_method_ce2e8263() {
      return RockstarClient.getInstance().I_method_be969482().I_method_f1206e83()
         && RockstarClient.getInstance()
            .I_method_35687482()
            .I_method_35128395()
            .i_method_c0baaa28()
            .stream()
            .anyMatch(var0 -> var0 instanceof IiiIIIIIi_Class194);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
