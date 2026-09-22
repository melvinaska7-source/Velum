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
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.session.Session;
import net.minecraft.text.Text;
import pyvelum.utility.render.ColorRGBA;

/**
 * Простой оффлайн (пиратка) менеджер ников. Хранит список ников в
 * Velum/accounts.json и переключает MinecraftClient.session при клике.
 * НЕ имеет ничего общего с настоящими Microsoft-аккаунтами: это просто
 * смена отображаемого ника/UUID, как это делает любой оффлайн-лаунчер.
 */
public class AccountsScreen extends ii_Class4 implements iIIiIIiIi_Class294 {
   private static final Pattern I_namePattern = Pattern.compile("^[A-Za-z0-9_]{3,16}$");
   private static final File I_saveFile = new File(IiIIiIII_Class73.I_field_3a58077, "accounts.json");
   private static final Type I_listType = new TypeToken<List<String>>() {}.getType();

   private final Screen I_parent;
   private final List<String> I_accounts = new ArrayList<>();
   private final StringBuilder I_input = new StringBuilder();
   private String I_error;
   private long I_errorUntil;

   public AccountsScreen(Screen parent) {
      this.I_parent = parent;
      this.I_load();
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
            VelumClient.I_field_ab0f6068.warn("[Accounts] \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0437\u0430\u0433\u0440\u0443\u0437\u0438\u0442\u044c accounts.json: {}", var3.toString());
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
         VelumClient.I_field_ab0f6068.warn("[Accounts] \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u0441\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c accounts.json: {}", var2.toString());
      }
   }

   private void I_showError(String var1) {
      this.I_error = var1;
      this.I_errorUntil = System.currentTimeMillis() + 2500L;
   }

   private void I_addCurrentInput() {
      String var1 = this.I_input.toString().trim();
      if (!I_namePattern.matcher(var1).matches()) {
         this.I_showError("\u043d\u0438\u043a: 3-16 \u0441\u0438\u043c\u0432\u043e\u043b\u043e\u0432, \u0442\u043e\u043b\u044c\u043a\u043e A-Z 0-9 _");
         return;
      }
      for (String var2 : this.I_accounts) {
         if (var2.equalsIgnoreCase(var1)) {
            this.I_showError("\u0443\u0436\u0435 \u0435\u0441\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043a\u0435");
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

   private void I_switchTo(String var1) {
      Session var2 = new Session(var1, I_offlineUuid(var1), "0", Optional.empty(), Optional.empty(), Session.AccountType.LEGACY);
      ((IMinecraftClient) I_field_3a9bda27).setSession(var2);
      VelumClient.I_field_ab0f6068.info("[Accounts] \u0430\u043a\u043a\u0430\u0443\u043d\u0442 \u043f\u0435\u0440\u0435\u043a\u043b\u044e\u0447\u0451\u043d \u043d\u0430: {}", var1);
      this.close();
   }

   private float I_rowY(int var1) {
      return 68.0F + var1 * 26.0F;
   }

   @Override
   public void render(III iII) {
      IIiIIi_Class10 var1 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(12.0F);
      IIiIIi_Class10 var2 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(10.0F);
      IIiIIi_Class10 var3 = IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F);

      iII.drawRoundedRect(0.0F, 0.0F, this.width, this.height, IIiii_Class8.I_field_2d98a52c, new ColorRGBA(16.0F, 16.0F, 20.0F).withAlpha(255.0F));
      iII.drawCenteredText(var1, "\u0410\u043a\u043a\u0430\u0443\u043d\u0442\u044b", this.width / 2.0F, 24.0F, ColorRGBA.WHITE);

      float var4 = this.width / 2.0F - 130.0F;
      float var5 = 260.0F;
      int var100 = iII.I_method_b1c3e152();
      int var101 = iII.i_method_b1d26d32();

      if (this.I_accounts.isEmpty()) {
         iII.drawCenteredText(var2, "\u0441\u043f\u0438\u0441\u043e\u043a \u043f\u0443\u0441\u0442. \u0434\u043e\u0431\u0430\u0432\u044c \u043d\u0438\u043a \u043d\u0438\u0436\u0435", this.width / 2.0F, this.I_rowY(0) + 6.0F, ColorRGBA.WHITE.withAlpha(140.0F));
      }

      for (int var6 = 0; var6 < this.I_accounts.size(); ++var6) {
         String var7 = this.I_accounts.get(var6);
         float var8 = this.I_rowY(var6);
         float var9 = var5 - 22.0F;
         boolean var10 = var100 >= var4 && var100 <= var4 + var9 && var101 >= var8 && var101 <= var8 + 20.0F;
         iII.drawRoundedRect(var4, var8, var9, 20.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(32.0F, 32.0F, 40.0F).withAlpha(var10 ? 210.0F : 150.0F));
         iII.drawText(var2, var7, var4 + 8.0F, var8 + (20.0F - var2.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE);

         float var11 = var4 + var5 - 20.0F;
         boolean var12 = var100 >= var11 && var100 <= var11 + 20.0F && var101 >= var8 && var101 <= var8 + 20.0F;
         iII.drawRoundedRect(var11, var8, 20.0F, 20.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(64.0F, 24.0F, 24.0F).withAlpha(var12 ? 210.0F : 120.0F));
         iII.drawCenteredText(var2, "x", var11 + 10.0F, var8 + (20.0F - var2.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE);
      }

      float var13 = this.I_rowY(this.I_accounts.size()) + 6.0F;
      float var14 = var5 - 72.0F;
      iII.drawRoundedRect(var4, var13, var14, 22.0F, IIiii_Class8.I_method_893b2757(5.0F), new ColorRGBA(24.0F, 24.0F, 30.0F).withAlpha(220.0F));
      iII.drawRoundedBorder(var4, var13, var14, 22.0F, 0.6F, IIiii_Class8.I_method_893b2757(5.0F), ColorRGBA.WHITE.withAlpha(70.0F));
      String var15 = this.I_input.toString();
      boolean var16 = System.currentTimeMillis() % 1000L < 500L;
      iII.drawText(var2, var15 + (var16 ? "_" : ""), var4 + 8.0F, var13 + (22.0F - var2.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE);
      if (var15.isEmpty()) {
         iII.drawText(var2, "\u043d\u0438\u043a...", var4 + 8.0F, var13 + (22.0F - var2.I_method_a649725c()) / 2.0F, ColorRGBA.WHITE.withAlpha(90.0F));
      }

      float var17 = var4 + var14 + 6.0F;
      float var18 = 66.0F;
      boolean var19 = var100 >= var17 && var100 <= var17 + var18 && var101 >= var13 && var101 <= var13 + 22.0F;
      iII.drawRoundedRect(var17, var13, var18, 22.0F, IIiii_Class8.I_method_893b2757(5.0F), ColorRGBA.WHITE.withAlpha(var19 ? 255.0F : 225.0F));
      iII.drawCenteredText(var2, "\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c", var17 + var18 / 2.0F, var13 + (22.0F - var2.I_method_a649725c()) / 2.0F, new ColorRGBA(18.0F, 18.0F, 24.0F));

      if (this.I_error != null) {
         if (System.currentTimeMillis() > this.I_errorUntil) {
            this.I_error = null;
         } else {
            iII.drawCenteredText(var3, this.I_error, this.width / 2.0F, var13 + 30.0F, new ColorRGBA(255.0F, 110.0F, 110.0F));
         }
      }

      iII.drawCenteredText(var3, "Esc \u2014 \u043d\u0430\u0437\u0430\u0434, \u043a\u043b\u0438\u043a \u043f\u043e \u043d\u0438\u043a\u0443 \u2014 \u0432\u043e\u0439\u0442\u0438 \u043f\u043e\u0434 \u043d\u0438\u043c", this.width / 2.0F, this.height - 16.0F, ColorRGBA.WHITE.withAlpha(90.0F));
   }

   @Override
   public void onMouseClicked(double var1, double var3, IiIII_Class9 var5) {
      if (var5.I_method_6d899712() != 0) {
         return;
      }
      float var6 = this.width / 2.0F - 130.0F;
      float var7 = 260.0F;

      for (int var8 = 0; var8 < this.I_accounts.size(); ++var8) {
         float var9 = this.I_rowY(var8);
         float var10 = var6 + var7 - 20.0F;
         if (var1 >= var10 && var1 <= var10 + 20.0 && var3 >= var9 && var3 <= var9 + 20.0) {
            this.I_remove(var8);
            return;
         }
         if (var1 >= var6 && var1 <= var10 - 2.0 && var3 >= var9 && var3 <= var9 + 20.0) {
            this.I_switchTo(this.I_accounts.get(var8));
            return;
         }
      }

      float var11 = this.I_rowY(this.I_accounts.size()) + 6.0F;
      float var12 = var7 - 72.0F;
      float var13 = var6 + var12 + 6.0F;
      float var14 = 66.0F;
      if (var1 >= var13 && var1 <= var13 + var14 && var3 >= var11 && var3 <= var11 + 22.0) {
         this.I_addCurrentInput();
      }
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 259 && this.I_input.length() > 0) {
         this.I_input.deleteCharAt(this.I_input.length() - 1);
         return true;
      }
      if (keyCode == 257) {
         this.I_addCurrentInput();
         return true;
      }
      return super.keyPressed(keyCode, scanCode, modifiers);
   }

   @Override
   public boolean charTyped(char chr, int modifiers) {
      if (this.I_input.length() < 16 && (Character.isLetterOrDigit(chr) || chr == '_') && chr < 128) {
         this.I_input.append(chr);
         return true;
      }
      return super.charTyped(chr, modifiers);
   }

   @Override
   public void close() {
      I_field_3a9bda27.setScreen(this.I_parent);
   }

   @Override
   public boolean shouldCloseOnEsc() {
      return true;
   }
}
