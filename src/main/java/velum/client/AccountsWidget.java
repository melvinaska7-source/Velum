package velum.client;

import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import moscow.velum.mixin.minecraft.client.IMinecraftClient;
import net.minecraft.client.session.Session;
import net.minecraft.util.math.MathHelper;
import pyvelum.utility.render.ColorRGBA;

/**
 * Встроенный оверлей смены (оффлайн) аккаунта, рисуется поверх главного меню.
 * Не Screen — обычный виджет, которым владеет IiiIIiIii_Class204.
 */
public class AccountsWidget {
   private static final Pattern I_namePattern = Pattern.compile("^[A-Za-z0-9_]{3,16}$");
   private static final File I_saveFile = new File(IiIIiIII_Class73.I_field_3a58077, "accounts.json");
   private static final Type I_listType = new TypeToken<List<String>>() {}.getType();

   public boolean open;
   private boolean I_target;
   private final IiiiIiIii_Class236 I_anim = new IiiiIiIii_Class236(380L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac);
   private final List<String> I_accounts = new ArrayList<>();
   private final StringBuilder I_input = new StringBuilder();
   private String I_error;
   private long I_errorUntil;
   private boolean I_loaded;

   private float I_cardX;
   private float I_cardY;
   private float I_cardW;
   private float I_cardH;

   public void openWidget() {
      if (!this.I_loaded) {
         this.I_load();
         this.I_loaded = true;
      }
      this.open = true;
      this.I_target = true;
      this.I_anim.I_method_edd72835(true);
   }

   private void I_closeWidget() {
      this.I_target = false;
      this.I_anim.I_method_edd72835(false);
   }

   private void I_load() {
      this.I_accounts.clear();
      if (I_saveFile.isFile()) {
         try (FileReader var1 = new FileReader(I_saveFile, StandardCharsets.UTF_8)) {
            List<String> var2 = IiIIiIII_Class73.I_field_fbd77e28.fromJson(var1, I_listType);
            if (var2 != null) {
               this.I_accounts.addAll(var2);
            }
         } catch (Exception var3) {
            VelumClient.I_field_ab0f6068.warn("[Accounts] load failed: {}", var3.toString());
         }
      }
   }

   private void I_save() {
      try {
         if (!IiIIiIII_Class73.I_field_3a58077.exists()) {
            IiIIiIII_Class73.I_field_3a58077.mkdirs();
         }
         try (FileWriter var1 = new FileWriter(I_saveFile, StandardCharsets.UTF_8)) {
            IiIIiIII_Class73.I_field_fbd77e28.toJson(this.I_accounts, I_listType, var1);
         }
      } catch (Exception var2) {
         VelumClient.I_field_ab0f6068.warn("[Accounts] save failed: {}", var2.toString());
      }
   }

   private void I_showError(String var1) {
      this.I_error = var1;
      this.I_errorUntil = System.currentTimeMillis() + 2500L;
   }

   private void I_addCurrentInput() {
      String var1 = this.I_input.toString().trim();
      if (!I_namePattern.matcher(var1).matches()) {
         this.I_showError("3-16 симв., A-Z 0-9 _");
         return;
      }
      for (String var2 : this.I_accounts) {
         if (var2.equalsIgnoreCase(var1)) {
            this.I_showError("уже есть в списке");
            return;
         }
      }
      this.I_accounts.add(var1);
      this.I_input.setLength(0);
      this.I_save();
   }

   private void I_remove(int var1) {
      if (var1 >= 0 && var1 < this.I_accounts.size()) {
         this.I_accounts.remove(var1);
         this.I_save();
      }
   }

   private static UUID I_offlineUuid(String var0) {
      return UUID.nameUUIDFromBytes(("OfflinePlayer:" + var0).getBytes(StandardCharsets.UTF_8));
   }

   private void I_switchTo(net.minecraft.client.MinecraftClient var0, String var1) {
      Session var2 = new Session(var1, I_offlineUuid(var1), "0", Optional.empty(), Optional.empty(), Session.AccountType.LEGACY);
      ((IMinecraftClient) var0).setSession(var2);
      VelumClient.I_field_ab0f6068.info("[Accounts] switched to: {}", var1);
      this.I_closeWidget();
   }

   private float I_rowY(int var1) {
      return this.I_cardY + 56.0F + var1 * 24.0F;
   }

   public void render(III iII, int screenW, int screenH, int mouseX, int mouseY) {
      this.I_anim.I_method_edd72835(this.I_target);
      float var100 = this.I_anim.I_method_6ac4da6f();
      this.open = this.I_target || var100 > 0.001F;
      if (!this.open) {
         return;
      }

      iII.drawRoundedRect(0.0F, 0.0F, screenW, screenH, IIiii_Class8.I_field_2d98a52c, new ColorRGBA(0.0F, 0.0F, 0.0F).withAlpha(150.0F * var100));

      int var1 = Math.max(1, this.I_accounts.size());
      this.I_cardW = 260.0F;
      this.I_cardH = 92.0F + var1 * 24.0F;
      this.I_cardX = screenW / 2.0F - this.I_cardW / 2.0F;
      this.I_cardY = screenH / 2.0F - this.I_cardH / 2.0F;

      float var101 = 0.88F + 0.12F * var100;
      iIiiiIIiI_Class371.II_method_e18635ac(iII.getMatrices(), this.I_cardX + this.I_cardW / 2.0F, this.I_cardY + this.I_cardH / 2.0F, var101);

      iII.drawRoundedRect(this.I_cardX, this.I_cardY, this.I_cardW, this.I_cardH, IIiii_Class8.I_method_893b2757(10.0F), new ColorRGBA(20.0F, 21.0F, 26.0F).withAlpha(250.0F * var100));
      iII.drawRoundedBorder(this.I_cardX, this.I_cardY, this.I_cardW, this.I_cardH, 0.6F, IIiii_Class8.I_method_893b2757(10.0F), ColorRGBA.WHITE.withAlpha(28.0F * var100));

      if (var100 < 0.6F) {
         iIiiiIIiI_Class371.I_method_10503b11(iII.getMatrices());
         return;
      }

      float var102 = MathHelper.clamp((var100 - 0.6F) / 0.4F, 0.0F, 1.0F);

      IIiIIi_Class10 var2 = IIiIiI_Class11.I_field_857c0621.I_method_3a2d5e3(10.0F);
      IIiIIi_Class10 var3 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F);
      IIiIIi_Class10 var4 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F);

      iII.drawCenteredText(var2, "Смена аккаунта", this.I_cardX + this.I_cardW / 2.0F, this.I_cardY + 14.0F, ColorRGBA.WHITE.withAlpha(255.0F * var102));

      float var5 = this.I_cardX + this.I_cardW - 24.0F;
      boolean var6 = mouseX >= var5 && mouseX <= var5 + 16.0F && mouseY >= this.I_cardY + 10.0F && mouseY <= this.I_cardY + 26.0F;
      iII.drawCenteredText(var3, "x", var5 + 8.0F, this.I_cardY + 12.0F, (var6 ? ColorRGBA.WHITE : new ColorRGBA(140.0F, 140.0F, 150.0F)).withAlpha(255.0F * var102));

      float var7 = this.I_cardX + 16.0F;
      float var8 = this.I_cardW - 32.0F;

      if (this.I_accounts.isEmpty()) {
         iII.drawCenteredText(var4, "список пуст — добавь ник ниже", this.I_cardX + this.I_cardW / 2.0F, this.I_rowY(0) + 7.0F, ColorRGBA.WHITE.withAlpha(120.0F * var102));
      }

      for (int var9 = 0; var9 < this.I_accounts.size(); ++var9) {
         String var10 = this.I_accounts.get(var9);
         float var11 = this.I_rowY(var9);
         float var12 = var8 - 22.0F;
         boolean var13 = mouseX >= var7 && mouseX <= var7 + var12 && mouseY >= var11 && mouseY <= var11 + 18.0F;
         iII.drawRoundedRect(var7, var11, var12, 18.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(32.0F, 32.0F, 40.0F).withAlpha((var13 ? 220.0F : 150.0F) * var102));
         iII.drawText(var3, var10, var7 + 8.0F, var11 + (18.0F - var3.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(255.0F * var102));

         float var14 = var7 + var8 - 18.0F;
         boolean var15 = mouseX >= var14 && mouseX <= var14 + 18.0F && mouseY >= var11 && mouseY <= var11 + 18.0F;
         iII.drawRoundedRect(var14, var11, 18.0F, 18.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(64.0F, 24.0F, 24.0F).withAlpha((var15 ? 220.0F : 120.0F) * var102));
         iII.drawCenteredText(var4, "x", var14 + 9.0F, var11 + (18.0F - var4.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(255.0F * var102));
      }

      float var16 = this.I_rowY(this.I_accounts.size()) + 4.0F;
      float var17 = var8 - 60.0F;
      iII.drawRoundedRect(var7, var16, var17, 20.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(14.0F, 14.0F, 18.0F).withAlpha(230.0F * var102));
      iII.drawRoundedBorder(var7, var16, var17, 20.0F, 0.5F, IIiii_Class8.I_method_893b2757(5.0F), ColorRGBA.WHITE.withAlpha(55.0F * var102));
      String var18 = this.I_input.toString();
      boolean var19 = System.currentTimeMillis() % 1000L < 500L;
      if (var18.isEmpty()) {
         iII.drawText(var4, "ник...", var7 + 7.0F, var16 + (20.0F - var4.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(90.0F * var102));
      } else {
         iII.drawText(var4, var18 + (var19 ? "_" : ""), var7 + 7.0F, var16 + (20.0F - var4.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(255.0F * var102));
      }

      float var20 = var7 + var17 + 6.0F;
      float var21 = 54.0F;
      boolean var22 = mouseX >= var20 && mouseX <= var20 + var21 && mouseY >= var16 && mouseY <= var16 + 20.0F;
      iII.drawRoundedRect(var20, var16, var21, 20.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(37.0F, 99.0F, 235.0F).withAlpha((var22 ? 255.0F : 225.0F) * var102));
      iII.drawCenteredText(var4, "добавить", var20 + var21 / 2.0F, var16 + (20.0F - var4.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(255.0F * var102));

      if (this.I_error != null) {
         if (System.currentTimeMillis() > this.I_errorUntil) {
            this.I_error = null;
         } else {
            iII.drawCenteredText(var4, this.I_error, this.I_cardX + this.I_cardW / 2.0F, var16 + 26.0F, new ColorRGBA(255.0F, 110.0F, 110.0F).withAlpha(255.0F * var102));
         }
      }

      iIiiiIIiI_Class371.I_method_10503b11(iII.getMatrices());
   }

   public boolean onMouseClicked(net.minecraft.client.MinecraftClient var0, double mx, double my, int button) {
      if (!this.I_target || button != 0) {
         return this.open;
      }

      float var1 = this.I_cardX + this.I_cardW - 24.0F;
      if (mx >= var1 && mx <= var1 + 16.0 && my >= this.I_cardY + 10.0 && my <= this.I_cardY + 26.0) {
         this.I_closeWidget();
         return true;
      }

      float var2 = this.I_cardX + 16.0F;
      float var3 = this.I_cardW - 32.0F;

      for (int var4 = 0; var4 < this.I_accounts.size(); ++var4) {
         float var5 = this.I_rowY(var4);
         float var6 = var2 + var3 - 18.0F;
         if (mx >= var6 && mx <= var6 + 18.0 && my >= var5 && my <= var5 + 18.0) {
            this.I_remove(var4);
            return true;
         }
         if (mx >= var2 && mx <= var6 - 2.0 && my >= var5 && my <= var5 + 18.0) {
            this.I_switchTo(var0, this.I_accounts.get(var4));
            return true;
         }
      }

      float var7 = this.I_rowY(this.I_accounts.size()) + 4.0F;
      float var8 = var3 - 60.0F;
      float var9 = var2 + var8 + 6.0F;
      float var10 = 54.0F;
      if (mx >= var9 && mx <= var9 + var10 && my >= var7 && my <= var7 + 20.0) {
         this.I_addCurrentInput();
         return true;
      }

      if (mx < this.I_cardX || mx > this.I_cardX + this.I_cardW || my < this.I_cardY || my > this.I_cardY + this.I_cardH) {
         this.I_closeWidget();
      }
      return true;
   }

   public boolean onCharTyped(char chr) {
      if (!this.I_target) {
         return false;
      }
      if (this.I_input.length() < 16 && (Character.isLetterOrDigit(chr) || chr == '_') && chr < 128) {
         this.I_input.append(chr);
      }
      return true;
   }

   public boolean onKeyPressed(int keyCode) {
      if (!this.I_target) {
         return false;
      }
      if (keyCode == 256) {
         this.I_closeWidget();
         return true;
      }
      if (keyCode == 259 && this.I_input.length() > 0) {
         this.I_input.deleteCharAt(this.I_input.length() - 1);
         return true;
      }
      if (keyCode == 257) {
         this.I_addCurrentInput();
         return true;
      }
      return true;
   }
}
