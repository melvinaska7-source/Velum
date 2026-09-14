package velum.client;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import globals.client.api.RockNetClient;
import globals.shared.proto.Packet;
import globals.shared.proto.Packets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.atomic.AtomicBoolean;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.ClickEvent.Action;
import net.minecraft.util.Formatting;

public class IIiiiiiI_Class63 {
   private static final long I_field_4a = 1500L;
   private static final String I_field_523beb0a = "default";
   private volatile List<Packets.Nested1_f320330b> I_field_7865b31 = List.of();
   private volatile Map<String, Long> I_field_a567c40b = Map.of();
   private volatile Long I_field_6ec681ff;
   private volatile String i_field_523beb0a;
   private final AtomicBoolean I_field_1232aa16 = new AtomicBoolean(false);
   private final AtomicBoolean i_field_1232aa16 = new AtomicBoolean(false);
   private final AtomicBoolean II_field_1232aa16 = new AtomicBoolean(false);
   private volatile boolean I_field_5a = true;
   private final ScheduledExecutorService I_field_47254ffa = Executors.newSingleThreadScheduledExecutor(var0 -> {
      Thread var1 = new Thread(var0, "Config-Autosave");
      var1.setDaemon(true);
      return var1;
   });
   private volatile ScheduledFuture<?> I_field_dd9473d7;
   private volatile ScheduledFuture<?> i_field_dd9473d7;
   private final Path I_field_localConfigDir;
   private static final String I_field_undoSuffix = ".undo.rock";
   private static final GsonBuilder I_field_gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping();

   public IIiiiiiI_Class63() {
      Path runDirectory = I_field_3a9bda27.runDirectory.toPath();
      this.I_field_localConfigDir = runDirectory.resolve("config").resolve("velum");
      this.I_method_4c0d6f21();
   }

   public void I_method_7ce4a4ec() {
   }

   public void i_method_7cf330cc() {
      this.I_method_4c0d6f21();
   }

   public void I_method_ea3d9dd(List<Packets.Nested1_f320330b> var1) {
      List var2 = var1 == null ? List.of() : List.copyOf(var1);
      HashMap var3 = new HashMap();
      Long var4 = null;
      String var5 = null;

      for (Packets.Nested1_f320330b var7 : (Iterable<Packets.Nested1_f320330b>)(Iterable<?>)var2) {
         var3.put(var7.name().toLowerCase(Locale.ROOT), var7.id());
         if (var7.active()) {
            var4 = var7.id();
            var5 = var7.name();
         }
      }

      this.I_field_7865b31 = var2;
      this.I_field_a567c40b = Map.copyOf(var3);
      this.I_field_6ec681ff = var4;
      this.i_field_523beb0a = var5;
      if (var2.isEmpty() && this.II_field_1232aa16.compareAndSet(false, true)) {
         this.I_method_8ed57bdb(new Packets.Nested1_f3200a8a(null, "default", IIiiiiIi_Class62.I_method_50733716()));
      } else if (!var2.isEmpty()) {
         this.II_field_1232aa16.set(true);
      }
   }

   public void I_method_7af2fb3e(Packets.Nested1_f31938b7 var1) {
      if (var1 != null && var1.data() != null) {
         if (var1.active()) {
            this.I_field_6ec681ff = var1.id();
            this.i_field_523beb0a = var1.name();
         }

         this.I_method_13058298(var1.data());
      }
   }

   private void I_method_13058298(JsonObject var1) {
      this.i_field_1232aa16.set(true);
      this.Iii_method_d9ff820c();
      ScheduledFuture var2 = this.i_field_dd9473d7;
      if (var2 != null) {
         var2.cancel(false);
         this.i_field_dd9473d7 = null;
      }

      MinecraftClient.getInstance()
         .execute(
            () -> {
               this.I_field_1232aa16.set(true);
               IIiiiiIi_Class62.Nested1_b7c42cf3 var2x = IIiiiiIi_Class62.Nested1_b7c42cf3.II_field_becfa1dc;
               JsonObject var3 = null;

               try {
                  var3 = IIiiiiIi_Class62.I_method_50733716();
                  var2x = IIiiiiIi_Class62.I_method_f332e29a(var1);
                  if (var2x == IIiiiiIi_Class62.Nested1_b7c42cf3.II_field_becfa1dc && var3 != null) {
                     IIiiiiIi_Class62.I_method_f332e29a(var3);
                  }
               } catch (Exception var8) {
                  VelumClient.I_field_ab0f6068
                     .error(
                        "Config: \u043d\u0435 \u0443\u0434\u0430\u043b\u043e\u0441\u044c \u043f\u0440\u0438\u043c\u0435\u043d\u0438\u0442\u044c \u043f\u043e\u043b\u0443\u0447\u0435\u043d\u043d\u044b\u0439 \u043a\u043e\u043d\u0444\u0438\u0433",
                        var8
                     );
                  var2x = IIiiiiIi_Class62.Nested1_b7c42cf3.II_field_becfa1dc;
                  if (var3 != null) {
                     IIiiiiIi_Class62.I_method_f332e29a(var3);
                  }
               } finally {
                  this.I_field_1232aa16.set(false);
               }

               if (var2x == IIiiiiIi_Class62.Nested1_b7c42cf3.II_field_becfa1dc) {
                  VelumClient.I_field_ab0f6068
                     .warn(
                        "Config: \u043a\u043e\u043d\u0444\u0438\u0433 \u043d\u0435 \u043f\u0440\u0438\u043c\u0435\u043d\u0451\u043d, \u0430\u0432\u0442\u043e\u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u0435 \u043f\u0440\u0438\u043e\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u043e \u0434\u043e \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u0439 \u0443\u0441\u043f\u0435\u0448\u043d\u043e\u0439 \u0437\u0430\u0433\u0440\u0443\u0437\u043a\u0438 \u0438\u043b\u0438 \u0440\u0443\u0447\u043d\u043e\u0433\u043e \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u0438\u044f"
                     );
               } else {
                  if (var2x == IIiiiiIi_Class62.Nested1_b7c42cf3.i_field_becfa1dc) {
                     List var4 = IIiiiiIi_Class62.I_method_624941b();
                     if (var4.isEmpty()) {
                        VelumClient.I_field_ab0f6068
                           .warn(
                              "Config: \u043a\u043e\u043d\u0444\u0438\u0433 \u043f\u0440\u0438\u043c\u0435\u043d\u0451\u043d \u0447\u0430\u0441\u0442\u0438\u0447\u043d\u043e \u2014 \u043d\u0430\u0441\u0442\u0440\u043e\u0439\u043a\u0438 \u0434\u0440\u0443\u0433\u043e\u0433\u043e \u0444\u043e\u0440\u043c\u0430\u0442\u0430 \u043f\u0440\u043e\u043f\u0443\u0449\u0435\u043d\u044b"
                           );
                     } else {
                        VelumClient.I_field_ab0f6068
                           .warn(
                              "Config: \u043a\u043e\u043d\u0444\u0438\u0433 \u043f\u0440\u0438\u043c\u0435\u043d\u0451\u043d \u0447\u0430\u0441\u0442\u0438\u0447\u043d\u043e, \u0441\u0435\u043a\u0446\u0438\u0438 \u043c\u043e\u0434\u0443\u043b\u0435\u0439 \u043d\u0435 \u0438\u0437 \u044d\u0442\u043e\u0439 \u0441\u0431\u043e\u0440\u043a\u0438 \u0441\u043e\u0445\u0440\u0430\u043d\u0435\u043d\u044b \u043a\u0430\u043a \u0435\u0441\u0442\u044c: {}",
                              String.join(", ", var4)
                           );
                     }
                  }

                  this.i_field_1232aa16.set(false);
                  this.IIi_method_d83c91ec();
               }
            }
         );
   }

   private void IIi_method_d83c91ec() {
      try {
         SoundsModule var1 = VelumClient.getInstance().getModuleManager().getModule(SoundsModule.class);
         if (var1 != null) {
            iiIiIIIII_Class417.i_field_34b2ba6c.I_method_7289b65f(var1.I_method_45ee5ef(), 1.0F);
         }

         VelumClient.getInstance()
            .I_method_5cb1af22()
            .I_method_8ee48d11(iiiIIII_Class113.I_field_c11fcfcc, IiIiIIII_Class81.I_method_f25a980a("configs.loaded"));
      } catch (Exception var2) {
      }
   }

   public boolean I_method_7ce4a4f0() {
      return this.I_field_5a;
   }

   public void I_method_1fb0ab68(boolean var1) {
      this.I_field_5a = var1;
      if (!var1) {
         ScheduledFuture var2 = this.I_field_dd9473d7;
         if (var2 != null) {
            var2.cancel(false);
         }

         this.I_field_dd9473d7 = null;
      }
   }

   public void II_method_1fbeeff5() {
      if (this.I_field_5a) {
         if (!this.I_field_1232aa16.get() && !this.i_field_1232aa16.get()) {
            this.Iii_method_d9ff820c();
            this.I_field_dd9473d7 = this.I_field_47254ffa.schedule(this::IiI_method_d9f0f62c, 1500L, TimeUnit.MILLISECONDS);
         }
      }
   }

   public void Ii_method_1fcd7bd5() {
      if (this.I_field_5a) {
         this.Iii_method_d9ff820c();
         this.IiI_method_d9f0f62c();
      }
   }

   private void IiI_method_d9f0f62c() {
      if (!this.I_field_1232aa16.get() && !this.i_field_1232aa16.get() && this.i_field_523beb0a != null) {
         String name = this.i_field_523beb0a;
         try {
            Files.createDirectories(this.I_field_localConfigDir);
            String data = I_field_gson.create().toJson(IIiiiiIi_Class62.I_method_50733716());
            Files.writeString(this.I_method_0f4c5a2d(name), data, StandardCharsets.UTF_8);
            this.I_method_4c0d6f21();
         } catch (Exception e) {
            VelumClient.I_field_ab0f6068.warn("Config: не удалось выполнить автосохранение {}", name, e);
         }
      }
   }

   public void I_method_ce5aebb6(String var1) {
      String name = this.I_method_1d9a0f7c(var1);
      if (name == null) {
         return;
      }

      try {
         this.I_method_4c0d6f21();
         Path target = this.I_method_0f4c5a2d(name);
         if (Files.exists(target)) {
            Files.copy(target, this.I_method_undoPath(name), StandardCopyOption.REPLACE_EXISTING);
         }
         String data = I_field_gson.create().toJson(IIiiiiIi_Class62.I_method_50733716());
         Files.writeString(target, data, StandardCharsets.UTF_8);
         this.I_method_4c0d6f21();
         this.i_field_523beb0a = name;
         this.I_field_6ec681ff = (long)name.toLowerCase(Locale.ROOT).hashCode();
         iIIIIIIii_Class260.I_method_468cf607(Text.of(IiIiIIII_Class81.I_method_1410d1e5("commands.config.saved", name)));
      } catch (Exception e) {
         VelumClient.I_field_ab0f6068.error("Config: не удалось сохранить локальный конфиг {}", name, e);
         iIIIIIIii_Class260.II_method_e8fd4864(Text.literal("Не удалось сохранить конфиг: " + name));
      }
   }

   public void i_method_9f123f96(String var1) {
      String name = this.I_method_1d9a0f7c(var1);
      if (name == null) return;
      Path file = this.I_method_0f4c5a2d(name);
      try {
         if (!Files.exists(file)) {
            this.ii_method_6b40627f(name);
            return;
         }
         JsonObject data = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
         this.i_field_523beb0a = name;
         this.I_field_6ec681ff = (long)name.toLowerCase(Locale.ROOT).hashCode();
         this.I_method_4c0d6f21();
         this.I_method_13058298(data);
      } catch (Exception e) {
         VelumClient.I_field_ab0f6068.error("Config: не удалось загрузить локальный конфиг {}", name, e);
         iIIIIIIii_Class260.II_method_e8fd4864(Text.literal("Не удалось загрузить конфиг: " + name));
      }
   }

   public void II_method_5455e67f(String var1) {
      String name = this.I_method_1d9a0f7c(var1);
      if (name == null) return;
      try {
         Path undo = this.I_method_undoPath(name);
         Path target = this.I_method_0f4c5a2d(name);
         if (!Files.exists(undo)) {
            iIIIIIIii_Class260.II_method_e8fd4864(Text.of(IiIiIIII_Class81.I_method_f25a980a("config.undo.empty")));
            return;
         }
         Files.copy(undo, target, StandardCopyOption.REPLACE_EXISTING);
         Files.deleteIfExists(undo);
         this.i_field_523beb0a = name;
         JsonObject data = JsonParser.parseString(Files.readString(target, StandardCharsets.UTF_8)).getAsJsonObject();
         this.I_method_13058298(data);
      } catch (Exception e) {
         VelumClient.I_field_ab0f6068.error("Config: не удалось откатить конфиг {}", name, e);
      }
   }

   public void I_method_a9011662(String var1, JsonObject var2) {
      if (!i_method_9f123f9a(var1) && var2 != null) {
         this.I_method_8ed57bdb(new Packets.Nested1_9addad72(var1.trim(), var2));
      }
   }

   public void iI_method_2181e015() {
      this.I_field_1232aa16.set(true);

      try {
         IIiiiiIi_Class62.I_method_16b850ac();

         for (ModuleEntry var2 : VelumClient.getInstance().getModuleManager().getModules()) {
            var2.setEnabled(var2.isEnabledByDefault(), true);
         }
      } finally {
         this.I_field_1232aa16.set(false);
      }

      this.i_field_1232aa16.set(false);
      this.IiI_method_d9f0f62c();
      iIIIIIIii_Class260.I_method_468cf607(Text.of(IiIiIIII_Class81.I_method_f25a980a("commands.config.reset")));
   }

   public void ii_method_21906bf5() {
      this.I_method_4c0d6f21();
      List<Packets.Nested1_f320330b> var1 = this.I_field_7865b31;
      if (var1.isEmpty()) {
         iIIIIIIii_Class260.I_method_468cf607(Text.of(IiIiIIII_Class81.I_method_f25a980a("config.not_found")));
         return;
      }
      iIIIIIIii_Class260.I_method_468cf607(Text.of(IiIiIIII_Class81.I_method_f25a980a("config.list")));
      int var2 = 1;
      for (Packets.Nested1_f320330b var4 : var1) {
         String var5 = var4.name();
         String var6 = ".cfg load \"" + var5.replace("\"", "\\\"") + "\"";
         MutableText var7 = Text.literal("[" + var2++ + "] ")
            .setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GRAY)))
            .append(Text.literal(var5).setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(var4.active() ? Formatting.GREEN : Formatting.AQUA))
               .withClickEvent(new ClickEvent(Action.RUN_COMMAND, var6))
               .withHoverEvent(new HoverEvent(net.minecraft.text.HoverEvent.Action.SHOW_TEXT, Text.literal(IiIiIIII_Class81.I_method_f25a980a("config.hover_load"))))));
         if (var4.active()) var7.append(Text.literal(" ✔").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GREEN))));
         iIIIIIIii_Class260.Ii_method_12f9d884(var7);
      }
   }

   public List<Packets.Nested1_f320330b> I_method_43bd37db() {
      return this.I_field_7865b31;
   }

   public List<String> i_method_f9096ffb() {
      ArrayList var1 = new ArrayList();

      for (Packets.Nested1_f320330b var3 : this.I_field_7865b31) {
         var1.add(var3.name());
      }

      return var1;
   }

   public String I_method_5c2e4534() {
      return this.i_field_523beb0a;
   }

   public boolean I_method_ce5aebba(String var1) {
      return this.I_method_5e8b6a9f(var1) != null;
   }

   public void III_method_d82e060c() {
      if (this.i_method_7cf330d0()) {
         this.I_method_8ed57bdb(new Packets.Nested1_f32063a8());
      }
   }

   private Long I_method_5e8b6a9f(String var1) {
      if (i_method_9f123f9a(var1)) return null;
      String name = var1.trim();
      return Files.exists(this.I_method_0f4c5a2d(name)) ? (long)name.toLowerCase(Locale.ROOT).hashCode() : null;
   }

   private String I_method_1d9a0f7c(String name) {
      if (i_method_9f123f9a(name)) {
         iIIIIIIii_Class260.II_method_e8fd4864(Text.of(IiIiIIII_Class81.I_method_f25a980a("config.not_found")));
         return null;
      }
      String result = name.trim();
      if (result.length() > 64 || result.equals(".") || result.equals("..") || result.contains("/") || result.contains("\\") || result.indexOf('\0') >= 0) {
         iIIIIIIii_Class260.II_method_e8fd4864(Text.literal("Недопустимое имя конфига"));
         return null;
      }
      return result;
   }

   private Path I_method_0f4c5a2d(String name) {
      return this.I_field_localConfigDir.resolve(name + ".rock");
   }

   private Path I_method_undoPath(String name) {
      return this.I_field_localConfigDir.resolve(name + I_field_undoSuffix);
   }

   private void I_method_4c0d6f21() {
      try {
         Files.createDirectories(this.I_field_localConfigDir);
         ArrayList<Packets.Nested1_f320330b> list = new ArrayList<>();
         HashMap<String, Long> ids = new HashMap<>();
         try (var stream = Files.list(this.I_field_localConfigDir)) {
            stream.filter(path -> path.getFileName().toString().endsWith(".rock"))
               .filter(path -> !path.getFileName().toString().endsWith(I_field_undoSuffix))
               .sorted()
               .forEach(path -> {
                  String fileName = path.getFileName().toString();
                  String name = fileName.substring(0, fileName.length() - 5);
                  long id = name.toLowerCase(Locale.ROOT).hashCode();
                  boolean active = this.i_field_523beb0a != null && name.equalsIgnoreCase(this.i_field_523beb0a);
                  list.add(new Packets.Nested1_f320330b(id, name, active));
                  ids.put(name.toLowerCase(Locale.ROOT), id);
               });
         }
         this.I_field_7865b31 = List.copyOf(list);
         this.I_field_a567c40b = Map.copyOf(ids);
      } catch (Exception e) {
         VelumClient.I_field_ab0f6068.warn("Config: не удалось прочитать локальные конфиги", e);
      }
   }

   private void Iii_method_d9ff820c() {
      ScheduledFuture var1 = this.I_field_dd9473d7;
      if (var1 != null) {
         var1.cancel(false);
      }

      this.I_field_dd9473d7 = null;
   }

   private static boolean i_method_9f123f9a(String var0) {
      return var0 == null || var0.isBlank();
   }

   private void ii_method_6b40627f(String var1) {
      iIIIIIIii_Class260.II_method_e8fd4864(Text.of(IiIiIIII_Class81.I_method_f25a980a("config.not_found")));
   }

   private boolean i_method_7cf330d0() {
      return VelumClient.getInstance().I_method_cd3d46d0() != null;
   }

   private void I_method_8ed57bdb(Packet var1) {
      RockNetClient var2 = VelumClient.getInstance().I_method_cd3d46d0();
      if (var2 != null) {
         var2.send(var1);
      }
   }
}
