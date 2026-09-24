package velum.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.util.InputUtil;
import pyvelum.utility.render.ColorRGBA;

/***
 * ClickGUI "Комета" — независимый режим меню (MenuModule.mode = cometa).
 * В отличие от "Новая"/"Панели", НЕ зависит от общей системы окон/компонентов
 * (IiiIIiiiI_Class207, IiiIiIIII_Class209, iii_Class8) — только от
 * VelumClient.getModuleManager() и самих Setting-объектов. Стиль — по мотивам
 * ClickGuiScreen из исходников Кометы.
 */
public class CometaClickGuiScreen extends ii_Class4 implements iIIiIIiIi_Class294 {
   private static final ModuleCategory[] I_categories = ModuleCategory.values();
   private static final ColorRGBA I_accent = new ColorRGBA(64.0F, 132.0F, 255.0F);

   private int I_selectedCategory = 0;
   private float I_scroll = 0.0F;
   private final Set<String> I_expanded = new HashSet<>();
   private KeybindSetting I_listening;

   private static final float I_sideX = 20.0F;
   private static final float I_sideW = 120.0F;
   private static final float I_panelGap = 10.0F;
   private static final float I_rowH = 22.0F;
   private static final float I_settingH = 20.0F;

   @Override
   public void render(III var1) {
      var1.drawRoundedRect(0.0F, 0.0F, this.width, this.height, IIiii_Class8.I_field_2d98a52c, new ColorRGBA(0.0F, 0.0F, 0.0F).withAlpha(110.0F));

      float var2 = 20.0F;
      float var3 = this.height - 40.0F;

      IIiIIi_Class10 var4 = IIiIiI_Class11.I_field_857c0621.I_method_3a2d5e3(9.0F);
      IIiIIi_Class10 var5 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F);

      var1.drawRoundedRect(I_sideX, var2, I_sideW, var3, IIiii_Class8.I_method_893b2757(8.0F), new ColorRGBA(14.0F, 16.0F, 24.0F).withAlpha(232.0F));
      var1.drawRoundedBorder(I_sideX, var2, I_sideW, var3, 0.6F, IIiii_Class8.I_method_893b2757(8.0F), ColorRGBA.WHITE.withAlpha(20.0F));

      int var6 = var1.I_method_b1c3e152();
      int var7 = var1.i_method_b1d26d32();

      for (int var8 = 0; var8 < I_categories.length; ++var8) {
         float var9 = var2 + 8.0F + var8 * 30.0F;
         float var10 = I_sideX + 8.0F;
         float var11 = I_sideW - 16.0F;
         boolean var12 = var8 == this.I_selectedCategory;
         boolean var13 = var6 >= var10 && var6 <= var10 + var11 && var7 >= var9 && var7 <= var9 + 24.0F;

         if (var12) {
            var1.drawRoundedRect(var10, var9, var11, 24.0F, IIiii_Class8.I_method_893b2757(6.0F), I_accent.withAlpha(210.0F));
         } else if (var13) {
            var1.drawRoundedRect(var10, var9, var11, 24.0F, IIiii_Class8.I_method_893b2757(6.0F), ColorRGBA.WHITE.withAlpha(18.0F));
         }

         ColorRGBA var14 = var12 ? ColorRGBA.WHITE : new ColorRGBA(150.0F, 150.0F, 156.0F);
         var1.drawText(var4, I_categories[var8].I_method_b23d1194(), var10 + 10.0F, var9 + (24.0F - var4.I_method_a649725c()) / 2.0F, var14);
      }

      float var15 = I_sideX + I_sideW + I_panelGap;
      float var16 = this.width - var15 - 20.0F;
      var1.drawRoundedRect(var15, var2, var16, var3, IIiii_Class8.I_method_893b2757(8.0F), new ColorRGBA(14.0F, 16.0F, 24.0F).withAlpha(232.0F));
      var1.drawRoundedBorder(var15, var2, var16, var3, 0.6F, IIiii_Class8.I_method_893b2757(8.0F), ColorRGBA.WHITE.withAlpha(20.0F));

      List<ModuleEntry> var17 = this.I_visibleModules();
      float var18 = var2 + 10.0F - this.I_scroll;

      for (ModuleEntry var20 : var17) {
         float var21 = this.I_drawModule(var1, var20, var15 + 10.0F, var18, var16 - 20.0F, var2, var2 + var3, var4, var5, var6, var7);
         var18 += var21 + 6.0F;
      }

      float var22 = Math.max(0.0F, var18 + this.I_scroll - (var2 + 10.0F) - (var3 - 20.0F));
      this.I_scroll = Math.max(0.0F, Math.min(this.I_scroll, var22));

      if (this.I_listening != null) {
         var1.drawCenteredText(var5, "нажмите клавишу...", var15 + var16 / 2.0F, var2 + var3 - 14.0F, ColorRGBA.WHITE.withAlpha(160.0F));
      }
   }

   private List<ModuleEntry> I_visibleModules() {
      List<ModuleEntry> var1 = new ArrayList<>();
      for (ModuleEntry var3 : VelumClient.getInstance().getModuleManager().getModules()) {
         if (var3.getCategory() == I_categories[this.I_selectedCategory] && var3.isAvailable()) {
            var1.add(var3);
         }
      }
      return var1;
   }

   /**
    * Рисует один модуль (и его настройки, если раскрыт). Возвращает суммарную высоту блока.
    * Кликабельные зоны обрабатываются тут же по факту клика (var10/var11 — координаты клика,
    * var9 == true если сейчас обрабатывается клик, а не просто отрисовка).
    */
   private float I_drawModule(
      III var1, ModuleEntry var2, float var3, float var4, float var5, float var6, float var7, IIiIIi_Class10 var8, IIiIIi_Class10 var9, int var10, int var11
   ) {
      boolean var12 = var4 + I_rowH >= var6 && var4 <= var7;
      boolean var13 = this.I_expanded.contains(var2.getName());
      boolean var14 = var10 >= var3 && var10 <= var3 + var5 && var11 >= var4 && var11 <= var4 + I_rowH && var4 >= var6 && var4 + I_rowH <= var7;

      if (var12) {
         var1.drawRoundedRect(var3, var4, var5, I_rowH, IIiii_Class8.I_method_893b2757(6.0F), new ColorRGBA(22.0F, 19.0F, 30.0F).withAlpha(var14 ? 200.0F : 140.0F));

         boolean var15 = var2.isEnabled();
         float var16 = 28.0F;
         float var17 = 14.0F;
         float var18 = var3 + var5 - var16 - 6.0F;
         float var19 = var4 + (I_rowH - var17) / 2.0F;
         var1.drawRoundedRect(var18, var19, var16, var17, IIiii_Class8.I_method_893b2757(var17 / 2.0F), var15 ? I_accent.withAlpha(230.0F) : new ColorRGBA(60.0F, 60.0F, 66.0F).withAlpha(220.0F));
         float var20 = var15 ? var18 + var16 - var17 + 1.0F : var18 + 1.0F;
         var1.drawRoundedRect(var20, var19 + 1.0F, var17 - 2.0F, var17 - 2.0F, IIiii_Class8.I_method_893b2757((var17 - 2.0F) / 2.0F), ColorRGBA.WHITE);

         var1.drawText(var8, var2.getName(), var3 + 8.0F, var4 + (I_rowH - var8.I_method_a649725c()) / 2.0F, var15 ? ColorRGBA.WHITE : new ColorRGBA(190.0F, 190.0F, 196.0F));
      }

      float var21 = I_rowH;

      if (var13) {
         float var22 = var4 + I_rowH + 4.0F;
         for (Setting var24 : var2.getSettings()) {
            if (!var24.isVisible()) {
               continue;
            }
            float var25 = this.I_drawSetting(var1, var24, var3 + 8.0F, var22, var5 - 16.0F, var6, var7, var9, var10, var11);
            var22 += var25 + 4.0F;
         }
         var21 = var22 - var4 + 4.0F;
      }

      return var21;
   }

   private float I_drawSetting(III var1, Setting var2, float var3, float var4, float var5, float var6, float var7, IIiIIi_Class10 var8, int var9, int var10) {
      boolean var11 = var4 + I_settingH >= var6 && var4 <= var7;

      if (var2 instanceof BooleanSetting var12) {
         boolean var13 = var12.i_method_9b12da03();
         if (var11) {
            boolean var14 = var9 >= var3 && var9 <= var3 + var5 && var10 >= var4 && var10 <= var4 + I_settingH;
            var1.drawRoundedRect(var3 + 1.0F, var4 + 5.0F, 10.0F, 10.0F, IIiii_Class8.I_method_893b2757(3.0F), var13 ? I_accent.withAlpha(230.0F) : new ColorRGBA(70.0F, 70.0F, 76.0F).withAlpha(var14 ? 220.0F : 180.0F));
            var1.drawText(var8, IiIiIIII_Class81.I_method_f25a980a(var2.getName()), var3 + 18.0F, var4 + (I_settingH - var8.I_method_a649725c()) / 2.0F, new ColorRGBA(205.0F, 205.0F, 210.0F));
         }
         return I_settingH;
      }

      if (var2 instanceof SliderSetting var15) {
         if (var11) {
            var1.drawText(var8, IiIiIIII_Class81.I_method_f25a980a(var2.getName()) + var15.II_method_d429a7de(), var3, var4, new ColorRGBA(205.0F, 205.0F, 210.0F));
            float var16 = var4 + var8.I_method_a649725c() + 4.0F;
            float var17 = MathHelper_clamp01((var15.Ii_method_a20abcd2() - var15.I_method_b2a48e2f()) / Math.max(1.0E-4F, var15.i_method_b2b31a0f() - var15.I_method_b2a48e2f()));
            var1.drawRoundedRect(var3, var16, var5, 3.0F, IIiii_Class8.I_method_893b2757(1.5F), new ColorRGBA(60.0F, 60.0F, 66.0F).withAlpha(220.0F));
            var1.drawRoundedRect(var3, var16, var5 * var17, 3.0F, IIiii_Class8.I_method_893b2757(1.5F), I_accent.withAlpha(230.0F));
         }
         return I_settingH + 6.0F;
      }

      if (var2 instanceof ModeSetting var18) {
         if (var11) {
            var1.drawText(var8, IiIiIIII_Class81.I_method_f25a980a(var2.getName()), var3, var4, new ColorRGBA(205.0F, 205.0F, 210.0F));
         }
         float var19 = var4 + var8.I_method_a649725c() + 4.0F;
         float var20 = var3;
         for (ModeSetting.Nested1_42765c60 var22 : var18.I_method_e1d4a248()) {
            String var23 = IiIiIIII_Class81.I_method_f25a980a(var22.getName());
            float var24 = var8.I_method_2c375926(var23) + 12.0F;
            if (var19 + I_settingH >= var6 && var19 <= var7) {
               boolean var25 = var22.isSelected();
               boolean var26 = var9 >= var20 && var9 <= var20 + var24 && var10 >= var19 && var10 <= var19 + I_settingH - 4.0F;
               var1.drawRoundedRect(
                  var20, var19, var24, I_settingH - 4.0F, IIiii_Class8.I_method_893b2757(5.0F), var25 ? I_accent.withAlpha(220.0F) : new ColorRGBA(40.0F, 40.0F, 46.0F).withAlpha(var26 ? 210.0F : 150.0F)
               );
               var1.drawCenteredText(var8, var23, var20 + var24 / 2.0F, var19 + (I_settingH - 4.0F - var8.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE);
            }
            var20 += var24 + 4.0F;
            if (var20 > var3 + var5 - 20.0F) {
               var20 = var3;
               var19 += I_settingH;
            }
         }
         return var19 - var4 + I_settingH;
      }

      if (var2 instanceof KeybindSetting var27) {
         if (var11) {
            var1.drawText(var8, IiIiIIII_Class81.I_method_f25a980a(var2.getName()), var3, var4 + (I_settingH - 8.0F) / 2.0F, new ColorRGBA(205.0F, 205.0F, 210.0F));
            String var28 = this.I_listening == var27 ? "..." : this.I_keyName(var27.I_method_fa6281d2());
            float var29 = 70.0F;
            float var30 = var3 + var5 - var29;
            boolean var31 = var9 >= var30 && var9 <= var30 + var29 && var10 >= var4 && var10 <= var4 + I_settingH - 4.0F;
            var1.drawRoundedRect(var30, var4, var29, I_settingH - 4.0F, IIiii_Class8.I_method_893b2757(4.0F), new ColorRGBA(40.0F, 40.0F, 46.0F).withAlpha(this.I_listening == var27 ? 230.0F : (var31 ? 210.0F : 150.0F)));
            var1.drawCenteredText(var8, var28, var30 + var29 / 2.0F, var4 + (I_settingH - 4.0F - var8.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE);
         }
         return I_settingH;
      }

      return 0.0F;
   }

   private String I_keyName(int var1) {
      if (var1 < 0) {
         return "—";
      }
      try {
         return InputUtil.fromKeyCode(var1, 0).getLocalizedText().getString().toUpperCase();
      } catch (Throwable var2) {
         return "#" + var1;
      }
   }

   private static float MathHelper_clamp01(float var0) {
      return var0 < 0.0F ? 0.0F : Math.min(var0, 1.0F);
   }

   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (var5.I_method_6d899712() != 0) {
         super.onMouseClicked(var1, var3, var5);
         return;
      }

      float var6 = 20.0F;
      float var7 = this.height - 40.0F;

      for (int var8 = 0; var8 < I_categories.length; ++var8) {
         float var9 = var6 + 8.0F + var8 * 30.0F;
         float var10 = I_sideX + 8.0F;
         float var11 = I_sideW - 16.0F;
         if (var1 >= var10 && var1 <= var10 + var11 && var3 >= var9 && var3 <= var9 + 24.0F) {
            this.I_selectedCategory = var8;
            this.I_scroll = 0.0F;
            return;
         }
      }

      float var26 = I_sideX + I_sideW + I_panelGap;
      float var27 = this.width - var26 - 20.0F;
      float var28 = var6 + 10.0F - this.I_scroll;

      for (ModuleEntry var30 : this.I_visibleModules()) {
         float var31 = var28;
         float var32 = var26 + 10.0F;
         float var33 = var27 - 20.0F;

         if (var1 >= var32 && var1 <= var32 + var33 && var3 >= var31 && var3 <= var31 + I_rowH && var31 >= var6 && var31 + I_rowH <= var6 + var7) {
            float var34 = 28.0F;
            float var35 = var32 + var33 - var34 - 6.0F;
            if (var1 >= var35) {
               var30.setEnabled(!var30.isEnabled(), true);
            } else if (this.I_expanded.contains(var30.getName())) {
               this.I_expanded.remove(var30.getName());
            } else {
               this.I_expanded.add(var30.getName());
            }
            return;
         }

         float var36 = I_rowH;
         if (this.I_expanded.contains(var30.getName())) {
            float var37 = var31 + I_rowH + 4.0F;
            for (Setting var39 : var30.getSettings()) {
               if (!var39.isVisible()) {
                  continue;
               }
               float var40 = this.I_handleSettingClick(var39, var32 + 8.0F, var37, var33 - 16.0F, var1, var3);
               if (var40 < 0.0F) {
                  return;
               }
               var37 += var40 + 4.0F;
            }
            var36 = var37 - var31 + 4.0F;
         }
         var28 += var36 + 6.0F;
      }

      super.onMouseClicked(var1, var3, var5);
   }

   /**
    * Возвращает высоту блока настройки. Если клик попал в интерактивную часть —
    * применяет действие и возвращает -1 (сигнал "клик обработан, остановиться").
    */
   private float I_handleSettingClick(Setting var1, float var2, float var3, float var4, double var5, double var7) {
      IIiIIi_Class10 var9 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F);

      if (var1 instanceof BooleanSetting var10) {
         if (var5 >= var2 && var5 <= var2 + var4 && var7 >= var3 && var7 <= var3 + I_settingH) {
            var10.toggle();
            return -1.0F;
         }
         return I_settingH;
      }

      if (var1 instanceof SliderSetting var11) {
         float var12 = var3 + var9.I_method_a649725c() + 4.0F;
         if (var5 >= var2 && var5 <= var2 + var4 && var7 >= var12 - 3.0F && var7 <= var12 + 6.0F) {
            float var13 = MathHelper_clamp01((float) ((var5 - var2) / var4));
            float var14 = var11.I_method_b2a48e2f() + (var11.i_method_b2b31a0f() - var11.I_method_b2a48e2f()) * var13;
            float var15 = var11.II_method_a1fc30f2();
            if (var15 > 0.0F) {
               var14 = Math.round(var14 / var15) * var15;
            }
            var11.I_method_a1eda161(var14);
            return -1.0F;
         }
         return I_settingH + 6.0F;
      }

      if (var1 instanceof ModeSetting var16) {
         float var17 = var3 + var9.I_method_a649725c() + 4.0F;
         float var18 = var2;
         for (ModeSetting.Nested1_42765c60 var20 : var16.I_method_e1d4a248()) {
            String var21 = IiIiIIII_Class81.I_method_f25a980a(var20.getName());
            float var22 = var9.I_method_2c375926(var21) + 12.0F;
            if (var5 >= var18 && var5 <= var18 + var22 && var7 >= var17 && var7 <= var17 + I_settingH - 4.0F) {
               var20.select();
               return -1.0F;
            }
            var18 += var22 + 4.0F;
            if (var18 > var2 + var4 - 20.0F) {
               var18 = var2;
               var17 += I_settingH;
            }
         }
         return var17 - var3 + I_settingH;
      }

      if (var1 instanceof KeybindSetting var23) {
         float var24 = 70.0F;
         float var25 = var2 + var4 - var24;
         if (var5 >= var25 && var5 <= var25 + var24 && var7 >= var3 && var7 <= var3 + I_settingH - 4.0F) {
            this.I_listening = var23;
            return -1.0F;
         }
         return I_settingH;
      }

      return 0.0F;
   }

   @Override
   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.I_scroll = Math.max(0.0F, this.I_scroll - (float) verticalAmount * 20.0F);
      return true;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.I_listening != null) {
         if (keyCode != 256) {
            this.I_listening.I_method_51ee2d04(keyCode);
         }
         this.I_listening = null;
         return true;
      }
      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean shouldCloseOnEsc() {
      return this.I_listening == null;
   }
}
