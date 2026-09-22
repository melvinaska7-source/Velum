package velum.client;

import globals.client.WorldKey;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import pyvelum.utility.render.ColorRGBA;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

/**
 * Главное меню. Плоский тёмный фон + логотип "velocity." + 5 кнопок-"пилюль"
 * (2 ряда: 2 основные сверху, 3 второстепенные снизу). Никакого фонового
 * фото/параллакса/часов/ника — минимальный вид как в макете.
 */
public class IiiIIiIii_Class204 extends ii_Class4 implements iIIiIIiIi_Class294 {
   private static final int I_BTN_COUNT = 5;
   private static final String[] I_btnLabel = {"Одиночная игра", "Сетевая игра", "Настройки", "Аккаунты", "Выход"};
   private static final boolean[] I_btnPrimary = {true, false, false, false, false};
   private static boolean I_field_5a;

   private final IiiiIiIii_Class236[] I_btnHover = new IiiiIiIii_Class236[]{
      new IiiiIiIii_Class236(180L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(180L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(180L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(180L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(180L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac)
   };
   private final float[] I_btnX = new float[I_BTN_COUNT];
   private final float[] I_btnY = new float[I_BTN_COUNT];
   private final float[] I_btnW = new float[I_BTN_COUNT];
   private final float[] I_btnH = new float[I_BTN_COUNT];

   @Compile(
      obfuscation = 4
   )
   public void init() {
      if (!I_field_5a) {
         I_field_5a = true;

         try {
            if (VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class).isEnabled()
               && VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class).II_method_4eb9f913().isSelected()) {
               iiIIiiiIi_Class414.i_method_fc4deddf();
            }
         } catch (Throwable var3) {
            VelumClient.I_field_ab0f6068
               .warn(
                  "\u0433\u043e\u043b\u043e\u0441 \u043f\u0440\u0438\u0432\u0435\u0442\u0441\u0442\u0432\u0438\u044f \u043d\u0435 \u043f\u0440\u043e\u0438\u0433\u0440\u0430\u043b\u0441\u044f: {}",
                  var3.toString()
               );
         }
      }

      IIIIi_Class2.III_method_92ee7b3f();
      VelumClient.getInstance()
         .I_method_cd3d46d0()
         .info(I_field_3a9bda27.getSession().getUsername(), "", "", "", WorldKey.local().world(), "all", Profile.getUsername());
      VelumClient.getInstance().I_method_cd3d46d0().update("main_menu");
      super.init();
   }

   private void I_runAction(int var1) {
      switch (var1) {
         case 0 -> I_field_3a9bda27.setScreen(new SelectWorldScreen(this));
         case 1 -> I_field_3a9bda27.setScreen(new MultiplayerScreen(this));
         case 2 -> I_field_3a9bda27.setScreen(new OptionsScreen(this, I_field_3a9bda27.options));
         case 3 -> I_field_3a9bda27.setScreen(new AccountsScreen(this));
         case 4 -> {
            if (VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class).Ii_method_54c1f4f3().isSelected()) {
               iiIIiiiIi_Class414.iI_method_8d7ec562();
            }

            I_field_3a9bda27.stop();
         }
      }
   }

   @Override
   public void render(III var1) {
      var1.drawRoundedRect(0.0F, 0.0F, this.width, this.height, IIiii_Class8.I_field_2d98a52c, new ColorRGBA(18.0F, 18.0F, 23.0F).withAlpha(255.0F));

      IIiIIi_Class10 var2 = IIiIiI_Class11.ii_field_857c0621.I_method_3a2d5e3(65.0F);
      float var3 = var2.I_method_2c375926("velocity.");
      float var4 = this.height / 2.0F - 105.0F;
      var1.drawText(var2, "velocity.", this.width / 2.0F - var3 / 2.0F, var4, ColorRGBA.WHITE);

      float var5 = 114.0F;
      float var6 = 24.0F;
      float var7 = 5.0F;
      float var8 = var5 * 2.0F + var7;
      float var9 = 20.0F;
      float var10 = 4.0F;
      float var11 = (var8 - var10 * 2.0F) / 3.0F;
      float var12 = this.width / 2.0F;
      float var13 = var4 + var2.I_method_a649725c() + 40.0F;

      float[] var14 = new float[]{
         var12 - var5 - var7 / 2.0F,
         var12 + var7 / 2.0F,
         var12 - var8 / 2.0F,
         var12 - var8 / 2.0F + var11 + var10,
         var12 - var8 / 2.0F + (var11 + var10) * 2.0F
      };
      float[] var15 = new float[]{var13, var13, var13 + var6 + var10, var13 + var6 + var10, var13 + var6 + var10};
      float[] var16 = new float[]{var5, var5, var11, var11, var11};
      float[] var17 = new float[]{var6, var6, var9, var9, var9};

      IIiIIi_Class10 var18 = IIiIiI_Class11.Ii_field_857c0621.I_method_3a2d5e3(11.0F);
      IIiIIi_Class10 var19 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F);
      int var20 = var1.I_method_b1c3e152();
      int var21 = var1.i_method_b1d26d32();

      for (int var22 = 0; var22 < I_BTN_COUNT; ++var22) {
         this.I_btnX[var22] = var14[var22];
         this.I_btnY[var22] = var15[var22];
         this.I_btnW[var22] = var16[var22];
         this.I_btnH[var22] = var17[var22];

         float var23 = var14[var22];
         float var24 = var15[var22];
         float var25 = var16[var22];
         float var26 = var17[var22];
         boolean var27 = var20 >= var23 && var20 <= var23 + var25 && var21 >= var24 && var21 <= var24 + var26;
         boolean var28 = this.I_btnHover[var22].I_method_6ac4da6f() > 0.5F;
         if (var27 && !var28) {
            iIIIiIIiI_Class275.I_method_e7d43867(iIIIiIIIi_Class274.i_field_aa52e62c);
         }
         this.I_btnHover[var22].I_method_edd72835(var27);
         float var29 = this.I_btnHover[var22].I_method_6ac4da6f();
         IIiii_Class8 var30 = IIiii_Class8.I_method_893b2757(var26 / 2.0F);

         if (I_btnPrimary[var22]) {
            var1.drawRoundedRect(var23, var24, var25, var26, var30, new ColorRGBA(242.0F, 242.0F, 246.0F));
            float var31 = var18.I_method_2c375926(I_btnLabel[var22]);
            var1.drawText(var18, I_btnLabel[var22], var23 + (var25 - var31) / 2.0F, var24 + (var26 - var18.I_method_a649725c()) / 2.0F, new ColorRGBA(18.0F, 18.0F, 24.0F));
         } else {
            var1.drawRoundedRect(var23, var24, var25, var26, var30, new ColorRGBA(30.0F, 30.0F, 38.0F).withAlpha(205.0F));
            var1.drawRoundedBorder(var23, var24, var25, var26, 0.6F, var30, ColorRGBA.WHITE.withAlpha(20.0F + 55.0F * var29));
            float var32 = var19.I_method_2c375926(I_btnLabel[var22]);
            ColorRGBA var33 = var29 > 0.5F ? ColorRGBA.WHITE : new ColorRGBA(170.0F, 170.0F, 182.0F);
            var1.drawText(var19, I_btnLabel[var22], var23 + (var25 - var32) / 2.0F, var24 + (var26 - var19.I_method_a649725c()) / 2.0F, var33);
         }
      }
   }

   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (var5.I_method_6d899712() == 0) {
         for (int var6 = 0; var6 < I_BTN_COUNT; ++var6) {
            if (this.I_btnX[var6] != 0.0F
               && var1 >= this.I_btnX[var6]
               && var1 <= this.I_btnX[var6] + this.I_btnW[var6]
               && var3 >= this.I_btnY[var6]
               && var3 <= this.I_btnY[var6] + this.I_btnH[var6]
               && this.I_btnHover[var6].I_method_6ac4da6f() == 1.0F) {
               this.I_runAction(var6);
               return;
            }
         }
      }

      super.onMouseClicked(var1, var3, var5);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
