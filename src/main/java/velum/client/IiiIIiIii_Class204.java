package velum.client;

import globals.client.WorldKey;
import java.time.LocalTime;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import pyvelum.utility.render.ColorRGBA;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

/**
 * Главное меню: скин+ник в углу, приветствие по центру, 3 кнопки
 * (Одиночная / Сетевая / Сменить аккаунт), нижний ряд ссылок
 * (Настройки / Выход), футер. Весь текст захардкожен (без lang).
 */
public class IiiIIiIii_Class204 extends ii_Class4 implements iIIiIIiIi_Class294 {
   private static boolean I_field_5a;
   // защита от гонки с фоновым прогревом атласа шрифтов на старте клиента:
   // не резолвим/не рисуем текст первые ~1.2с после создания экрана.
   private static final long I_field_bootAt = System.currentTimeMillis();
   private static final long I_field_textGraceMs = 1200L;

   private static final int I_BTN_COUNT = 3;
   private static final String[] I_btnLabel = {"Одиночная игра", "Сетевая игра", "Сменить аккаунт"};
   private static final boolean[] I_btnAccent = {false, false, true};
   private final IiiiIiIii_Class236[] I_btnHover = new IiiiIiIii_Class236[]{
      new IiiiIiIii_Class236(420L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(420L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(420L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac)
   };
   private final float[] I_btnX = new float[I_BTN_COUNT];
   private final float[] I_btnY = new float[I_BTN_COUNT];
   private final float[] I_btnW = new float[I_BTN_COUNT];
   private final float[] I_btnH = new float[I_BTN_COUNT];

   private static final String[] I_navLabel = {"Настройки", "Выход"};
   private final IiiiIiIii_Class236[] I_navHover = new IiiiIiIii_Class236[]{
      new IiiiIiIii_Class236(420L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac),
      new IiiiIiIii_Class236(420L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac)
   };
   private final float[] I_navX = new float[2];
   private final float[] I_navY = new float[2];
   private final float[] I_navW = new float[2];

   private final AccountsWidget I_accounts = new AccountsWidget();

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

   private static String I_greeting() {
      int var0 = LocalTime.now().getHour();
      if (var0 < 6) {
         return "Доброй ночи";
      } else if (var0 < 12) {
         return "Доброе утро";
      } else {
         return var0 < 18 ? "Добрый день" : "Добрый вечер";
      }
   }

   private void I_runAction(int var1) {
      switch (var1) {
         case 0 -> I_field_3a9bda27.setScreen(new SelectWorldScreen(this));
         case 1 -> I_field_3a9bda27.setScreen(new MultiplayerScreen(this));
         case 2 -> this.I_accounts.openWidget();
      }
   }

   @Override
   public void render(III var1) {
      var1.drawRoundedRect(0.0F, 0.0F, this.width, this.height, IIiii_Class8.I_field_2d98a52c, new ColorRGBA(10.0F, 11.0F, 14.0F).withAlpha(255.0F));
      boolean var2 = System.currentTimeMillis() - I_field_bootAt > I_field_textGraceMs;

      if (var2) {
         this.I_drawPlayerInfo(var1);
      }

      float var3 = this.width / 2.0F;
      float var4 = this.height / 2.0F;

      if (var2) {
         IIiIIi_Class10 var5 = IIiIiI_Class11.ii_field_857c0621.I_method_3a2d5e3(20.0F);
         float var6 = var5.I_method_2c375926("velocity.");
         var1.drawText(var5, "velocity.", var3 - var6 / 2.0F, var4 - 118.0F, ColorRGBA.WHITE);

         IIiIIi_Class10 var7 = IIiIiI_Class11.I_field_857c0621.I_method_3a2d5e3(12.0F);
         String var8 = I_greeting() + ", " + I_field_3a9bda27.getSession().getUsername();
         float var9 = var7.I_method_2c375926(var8);
         var1.drawText(var7, var8, var3 - var9 / 2.0F, var4 - 88.0F, new ColorRGBA(210.0F, 210.0F, 215.0F));

         IIiIIi_Class10 var10 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F);
         String var11 = "Добро пожаловать в Velum — лучший Minecraft-клиент.";
         float var12 = var10.I_method_2c375926(var11);
         var1.drawText(var10, var11, var3 - var12 / 2.0F, var4 - 74.0F, new ColorRGBA(130.0F, 131.0F, 135.0F));
      }

      this.I_drawButtons(var1, var2, var3, var4);
      this.I_drawNav(var1, var2, var3, var4 + 78.0F);

      if (var2) {
         IIiIIi_Class10 var13 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(6.0F);
         float var14 = this.height - 24.0F;
         String var15 = "Используя Velum, вы соглашаетесь с условиями использования";
         String var16 = "и политикой конфиденциальности.";
         var1.drawCenteredText(var13, var15, var3, var14, new ColorRGBA(55.0F, 56.0F, 60.0F));
         var1.drawCenteredText(var13, var16, var3, var14 + 9.0F, new ColorRGBA(55.0F, 56.0F, 60.0F));
      }

      this.I_accounts.render(var1, this.width, this.height, var1.I_method_b1c3e152(), var1.i_method_b1d26d32());
   }

   private void I_drawPlayerInfo(III var1) {
      float var2 = 12.0F;
      float var3 = 12.0F;
      float var4 = 18.0F;

      if (IIIIi_Class2.Ii_method_57601446()) {
         var1.drawRoundedTexture(
            IIIIi_Class2.I_method_79e9d9ee(), var2, var3, var4, var4, IIiii_Class8.I_method_893b2757(4.0F), ColorRGBA.WHITE
         );
      } else {
         var1.drawRoundedRect(var2, var3, var4, var4, IIiii_Class8.I_method_893b2757(4.0F), new ColorRGBA(40.0F, 40.0F, 46.0F));
      }

      float var5 = var2 + var4 + 6.0F;
      IIiIIi_Class10 var6 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(6.0F);
      IIiIIi_Class10 var7 = IIiIiI_Class11.I_field_857c0621.I_method_3a2d5e3(8.0F);
      var1.drawText(var6, "Вы вошли как", var5, var3 + 1.0F, new ColorRGBA(130.0F, 131.0F, 135.0F));
      var1.drawText(var7, I_field_3a9bda27.getSession().getUsername(), var5, var3 + 9.0F, ColorRGBA.WHITE);
   }

   private void I_drawButtons(III var1, boolean var2, float var3, float var4) {
      float var5 = 230.0F;
      float var6 = 26.0F;
      float var7 = 6.0F;
      float var8 = var4 - 34.0F;
      int var9 = var1.I_method_b1c3e152();
      int var10 = var1.i_method_b1d26d32();
      IIiIIi_Class10 var11 = IIiIiI_Class11.I_field_857c0621.I_method_3a2d5e3(9.0F);

      for (int var12 = 0; var12 < I_BTN_COUNT; ++var12) {
         float var13 = var3 - var5 / 2.0F;
         float var14 = var8 + var12 * (var6 + var7);
         this.I_btnX[var12] = var13;
         this.I_btnY[var12] = var14;
         this.I_btnW[var12] = var5;
         this.I_btnH[var12] = var6;

         boolean var15 = var9 >= var13 && var9 <= var13 + var5 && var10 >= var14 && var10 <= var14 + var6;
         this.I_btnHover[var12].I_method_edd72835(var15);
         float var16 = this.I_btnHover[var12].I_method_6ac4da6f();

         ColorRGBA var17;
         if (I_btnAccent[var12]) {
            var17 = new ColorRGBA(37.0F + 22.0F * var16, 99.0F + 31.0F * var16, 235.0F + 11.0F * var16);
         } else {
            var17 = new ColorRGBA(20.0F + 8.0F * var16, 23.0F + 8.0F * var16, 29.0F + 9.0F * var16);
         }

         var1.drawRoundedRect(var13, var14, var5, var6, IIiii_Class8.I_method_893b2757(10.0F), var17);

         if (var2) {
            ColorRGBA var18 = I_btnAccent[var12] ? ColorRGBA.WHITE : (var16 > 0.5F ? ColorRGBA.WHITE : new ColorRGBA(150.0F, 152.0F, 158.0F));
            float var19 = var11.I_method_2c375926(I_btnLabel[var12]);
            var1.drawText(var11, I_btnLabel[var12], var13 + (var5 - var19) / 2.0F, var14 + (var6 - var11.I_method_a649725c()) / 2.0F, var18);
         }
      }
   }

   private void I_drawNav(III var1, boolean var2, float var3, float var4) {
      float var5 = 80.0F;
      int var6 = var1.I_method_b1c3e152();
      int var7 = var1.i_method_b1d26d32();
      IIiIIi_Class10 var8 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F);
      float var9 = var3 - var5 / 2.0F;

      for (int var10 = 0; var10 < 2; ++var10) {
         float var11 = var9 + var10 * var5;
         float var12 = var8.I_method_2c375926(I_navLabel[var10]);
         this.I_navX[var10] = var11 - var12 / 2.0F - 12.0F;
         this.I_navY[var10] = var4 - 8.0F;
         this.I_navW[var10] = var12 + 24.0F;

         boolean var13 = var6 >= this.I_navX[var10]
            && var6 <= this.I_navX[var10] + this.I_navW[var10]
            && var7 >= this.I_navY[var10]
            && var7 <= this.I_navY[var10] + 16.0F;
         this.I_navHover[var10].I_method_edd72835(var13);
         float var14 = this.I_navHover[var10].I_method_6ac4da6f();

         if (var2) {
            ColorRGBA var15 = new ColorRGBA(90.0F + 165.0F * var14, 92.0F + 163.0F * var14, 98.0F + 157.0F * var14);
            var1.drawCenteredText(var8, I_navLabel[var10], var11, var4, var15);
         }
      }
   }

   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (this.I_accounts.onMouseClicked(I_field_3a9bda27, var1, var3, var5.I_method_6d899712())) {
         return;
      }

      if (var5.I_method_6d899712() == 0) {
         for (int var6 = 0; var6 < I_BTN_COUNT; ++var6) {
            if (this.I_btnX[var6] != 0.0F
               && var1 >= this.I_btnX[var6]
               && var1 <= this.I_btnX[var6] + this.I_btnW[var6]
               && var3 >= this.I_btnY[var6]
               && var3 <= this.I_btnY[var6] + this.I_btnH[var6]) {
               this.I_runAction(var6);
               return;
            }
         }

         if (var1 >= this.I_navX[0] && var1 <= this.I_navX[0] + this.I_navW[0] && var3 >= this.I_navY[0] && var3 <= this.I_navY[0] + 16.0) {
            I_field_3a9bda27.setScreen(new OptionsScreen(this, I_field_3a9bda27.options));
            return;
         }

         if (var1 >= this.I_navX[1] && var1 <= this.I_navX[1] + this.I_navW[1] && var3 >= this.I_navY[1] && var3 <= this.I_navY[1] + 16.0) {
            if (VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class).Ii_method_54c1f4f3().isSelected()) {
               iiIIiiiIi_Class414.iI_method_8d7ec562();
            }

            I_field_3a9bda27.stop();
            return;
         }
      }

      super.onMouseClicked(var1, var3, var5);
   }

   @Override
   public boolean charTyped(char chr, int modifiers) {
      return this.I_accounts.onCharTyped(chr) || super.charTyped(chr, modifiers);
   }

   @Compile(
      obfuscation = 1
   )
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return this.I_accounts.onKeyPressed(keyCode) || super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }
}
