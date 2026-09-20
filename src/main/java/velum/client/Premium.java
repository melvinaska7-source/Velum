package velum.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import pyvelum.utility.render.ColorRGBA;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

// "Премиум" ClickGUI (стиль Wexside) — построена на UI-движке Velum.
public class Premium
extends IiiIIiiiI_Class207
implements IiiIiIIiI_Class211 {
   private static final IIii_Class4 BIND_ANIM = IIii_Class4.I_method_3682ece9(160L, IiiiIiiII_Class237.IIII_field_dd60aac);
   private static final long OPEN_ANIM_MS = 300L; // длительность анимации открытия

   private final Map<ModuleEntry, iii_Class8> moduleElements = new IdentityHashMap<>();
   private final Map<Setting, iii_Class8> settingElements = new IdentityHashMap<>();
   private ModuleCategory selectedCategory;
   private ModuleEntry selectedModule;   // модуль с открытыми настройками
   private ModuleEntry bindingModule;    // захват клавиши (средний клик)
   private ModuleEntry prevSearchModule;
   private iii_Class8 moduleGrid;        // сетка карточек
   private iii_Class8 settingsList;      // контейнер строк настроек
   private iii_Class8 root;              // корень окна
   private iii_Class8 settingsScroll;    // скролл-контейнер настроек (для подсветки)
   private iii_Class8 highlightElement;  // подсветка "новой" настройки
   private Iii_Class4 searchBar;         // строка поиска
   private IiIiIIIII_Class161 searchField;
   private IIIIi_Class2 searchPalette;   // палитра поиска
   private iiiI_Class15 keybindEditor;   // редактор биндов
   private List<ModuleEntry> moduleCache = List.of();
   private List<Setting> settingCache = List.of();
   private int modulesVersion = -1;
   private String searchCache = "\u0000";
   private float winX, winY, winW, winH;
   private float navWidth;       // ширина левой навигации
   private float topBarHeight;   // высота топ-бара
   private float contentWidth;   // ширина правой части
   private float settingsColWidth;
   private float settingsGroupWidth;
   private float gridScrollHeight = Float.NaN;
   private float prevWinX = Float.NaN;
   private float prevWinY = Float.NaN;
   private long openStartTime;   // старт анимации открытия
   private long frameTime;
   private float hideProgress;
   private long highlightUntil;
   private static boolean I_field_5a;
   private static boolean i_field_5a;
   private static Premium instance;

   @Override
   protected boolean lowDrawBatching() {
      return true;
   }

   public Premium() {
      this.selectedCategory = ModuleCategory.COMBAT;
      this.moduleCache = List.of();
      this.settingCache = List.of();
      this.modulesVersion = -1;
      this.searchCache = "";
   }

   @Compile(obfuscation = 4)
   @Override
   public void init() {
      super.init();
      this.closing = false;
      this.contentAlpha = 1.0F;
      this.bindingModule = null;
      this.hideProgress = 0.0F;
      this.frameTime = 0L;
      this.openStartTime = System.currentTimeMillis(); // анимация открытия
      iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
      this.clearRoots();
      this.winW = Math.min(480.0F, Math.max(380.0F, this.width - 12.0F));
      this.winH = Math.min(310.0F, Math.max(240.0F, this.height - 12.0F));
      this.winX = Math.round((this.width - this.winW) / 2.0F);
      this.winY = Math.round((this.height - this.winH) / 2.0F);
      this.navWidth = Math.max(112.0F, this.winW * 0.26F);
      this.topBarHeight = 30.0F;
      this.contentWidth = this.winW - this.navWidth;
      this.settingsColWidth = Math.min(150.0F, Math.max(110.0F, this.contentWidth * 0.34F));
      this.settingsGroupWidth = Math.max(60.0F, (this.settingsColWidth - 22.0F - 5.0F - 5.0F) / 2.0F);
      if (this.searchField == null) {
         this.searchField = new IiIiIIIII_Class161(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F));
         this.searchField.II_method_e557b20c(IiIiIIII_Class81.I_method_f25a980a("search"));
      }

      iii_Class8 window = (new iii_Class8() {
            @Override
            protected void drawChildren(III var1, float var2) {
               iIiiiIiII_Class373.I_method_d5a5ee51(var1.getMatrices(), this.x(), this.y(), this.w(), this.h());
               super.drawChildren(var1, var2);
               iIiiiIiII_Class373.I_method_ff80a1df();
            }
         })
         .i_method_c88da8d5()
         .I_method_84672d76(IIii_Class4.II_field_f93678c1)
         .i_method_8c7d3515(this.winW, this.winH)
         .I_method_f136b1d8((var1x, var2) -> this.drawWindow(var1x, var2));
      window.iii_method_df275535();
      window.snapSize();
      window.snapAt(this.winX, this.winY);
      window.I_method_8939bffd(this.buildNav());       // навигация слева
      window.I_method_8939bffd(this.buildContent());   // топ-бар + контент
      this.root = window;
      this.add(window);
      this.searchPalette = new IIIIi_Class2(window, this::onSearchNavigation)
         .I_method_5a9bd0bd(this.searchBar)
         .I_method_562751de(this.searchBar, IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F));
      this.add(this.searchPalette);
      this.add(this.searchPalette.IIii_method_d1a53438());
      this.add(this.searchPalette.IiII_method_6941c18());
      this.moduleCache = List.of();
      this.settingCache = List.of();
      this.searchCache = "\u0000";
      this.refreshModules(true);
      this.refreshSettings(true);
      instance = this;
   }

   // ===== ЛЕВАЯ НАВИГАЦИЯ =====
   @Compile(obfuscation = 1)
   private iii_Class8 buildNav() {
      Iii_Class4 logo = new Iii_Class4()
         .width(this.navWidth)
         .height(this.topBarHeight)
         .paint((var0, var1x) -> {
            var0.drawIcon("logo", var1x.x() + 8.0F, var1x.y() + var1x.h() / 2.0F - 5.5F, 11.0F, IiiiiIIIi_Class242.ii_field_d0c8ec5);
            var0.drawText(IIiIiI_Class11.Ii_field_857c0621.I_method_3a2d5e3(8.0F), "Premium", var1x.x() + 24.0F, var1x.y() + var1x.h() / 2.0F - 3.5F, IiiiiIIIi_Class242.ii_field_d0c8ec5);
         })
         .draggable(iIi_Class6.i_field_b583ea6c);
      iii_Class8 catList = new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(1.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(6.0F, 2.0F, 6.0F, 0.0F))
         .IIi_method_4dfc88d7(this.navWidth);

      for (ModuleCategory category : ModuleCategory.values()) {
         catList.I_method_8939bffd(this.buildNavEntry(category));
      }

      iii_Class8 logoBlock = new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(9.0F)
         .I_method_8939bffd(logo)
         .I_method_8939bffd(catList);
      Iii_Class4 settingsBtn = new Iii_Class4()
         .size(17.0F, 17.0F)
         .padding(4.0F)
         .icon("setting", 9.0F, var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.58F + 0.35F * var1x.hover()))
         .background(var1x -> mixHover(IiiiiIIIi_Class242.iII_field_d0c8ec5, IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.025F * var1x.hover()))
         .radius(4.0F)
         .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
         .onClick(this::openSearchPalette);
      return new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.Ii_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .IIi_method_4dfc88d7(this.navWidth)
         .IIi_method_1df35135()
         .I_method_8361fbbd(iIi_Class6.i_field_b583ea6c)
         .I_method_8939bffd(logoBlock)
         .I_method_8939bffd(settingsBtn);
   }

   private iii_Class8 buildNavEntry(ModuleCategory category) {
      return new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(5.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(6.0F, 4.0F, 6.0F, 4.0F))
         .iI_method_e886414(4.0F)
         .I_method_e53800a9(var0 -> mixHover(IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.08F * var0.sig("selected") + 0.025F * var0.hover()))
         .I_method_350d9c12("selected", () -> this.selectedCategory == category, BIND_ANIM)
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_c0ca36f3(() -> this.selectCategory(category))
         .I_method_8939bffd(
            new Iii_Class4()
               .size(9.0F, 9.0F)
               .icon("category/" + category.I_method_b23d1194().toLowerCase(), 9.0F, var1x -> IiiiiIIIi_Class242.iII_field_d0c8ec5.mix(IiiiiIIIi_Class242.ii_field_d0c8ec5, var1x.sig("selected")).mulAlpha(0.62F + 0.38F * var1x.sig("selected")))
               .bind("selected", () -> this.selectedCategory == category, BIND_ANIM)
               .interactive(false))
         .I_method_8939bffd(
            new Iii_Class4()
               .height(7.0F)
               .fillWidth()
               .text(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F), category::I_method_b23d1194, var1x -> IiiiiIIIi_Class242.iII_field_d0c8ec5.mix(IiiiiIIIi_Class242.ii_field_d0c8ec5, var1x.sig("selected")).mulAlpha(0.62F + 0.38F * var1x.sig("selected")))
               .bind("selected", () -> this.selectedCategory == category, BIND_ANIM)
               .interactive(false));
   }

   // ===== ПРАВАЯ ЧАСТЬ: топ-бар + контент =====
   @Compile(obfuscation = 1)
   private iii_Class8 buildContent() {
      iii_Class8 content = new iii_Class8()
         .I_method_485bccf5()
         .IIi_method_4dfc88d7(this.contentWidth)
         .IiI_method_31d4c97(this.winH)
         .I_method_8939bffd(this.buildToolbar())
         .I_method_8939bffd(this.buildBody());
      content.snapSize();
      return content;
   }

   @Compile(obfuscation = 1)
   private iii_Class8 buildToolbar() {
      Iii_Class4 username = new Iii_Class4()
         .height(12.0F)
         .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(9.0F), this::getUsername, var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5)
         .interactive(false);
      Iii_Class4 subtitle = new Iii_Class4()
         .height(5.0F)
         .text(IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(6.0F), () -> "Premium", var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.52F))
         .textAlign(IIi_Class2.II_field_b5755e8c)
         .interactive(false);
      iii_Class8 userBlock = new iii_Class8()
         .I_method_485bccf5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(1.0F)
         .I_method_8939bffd(username)
         .I_method_8939bffd(subtitle);
      this.searchBar = new Iii_Class4()
         .size(81.0F, 12.0F)
         .radius(3.0F)
         .cursor(iIIIiIIIi_Class274.iI_field_aa52e62c)
         .onClick((var1x, var2x, var3x) -> {
            if (var1x == IiIII_Class9.I_field_2f4c8d6c && this.searchPalette != null) {
               if (this.searchPalette.II_method_57518866()) {
                  this.searchPalette.I_method_df8f3415().I_method_400ceaa7(var2x, var3x, var1x);
               } else {
                  this.searchPalette.II_method_57518862();
               }
            }
         })
         .paint((var1x, var2x) -> {
            var1x.drawRoundedRect(var2x.x(), var2x.y(), var2x.w(), 12.0F, IIiii_Class8.I_method_893b2757(3.0F), mixHover(IiiiiIIIi_Class242.Ii_method_a0f56f71().withAlpha(173.4F), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.035F + 0.035F * var2x.hover()));
            var1x.drawIcon("search", var2x.x() + 3.5F, var2x.y() + 3.5F, 5.0F, IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.48F));
            if (this.searchPalette != null) {
               IiIiIIIII_Class161 field = this.searchPalette.I_method_df8f3415();
               field.I_method_b043bcc(var2x.x() + 8.5F, var2x.y(), var2x.w() - 10.5F, 12.0F);
               field.II_method_e557b20c(IiIiIIII_Class81.I_method_f25a980a("search"));
               field.I_method_13a617e0(IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.72F));
               field.iI_method_a8609f1e(1.0F);
               field.I_method_7ae26dda(var1x);
            }
         });
      iii_Class8 toolbar = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(5.0F)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(12.0F, 0.0F, 10.0F, 0.0F))
         .IiI_method_31d4c97(this.topBarHeight)
         .I_method_8361fbbd(iIi_Class6.i_field_b583ea6c)
         .I_method_8939bffd(userBlock)
         .I_method_8939bffd(this.searchBar);
      toolbar.snapSize();
      return toolbar;
   }

   @Compile(obfuscation = 1)
   private iii_Class8 buildBody() {
      float bodyHeight = this.winH - this.topBarHeight;
      float gridWidth = this.contentWidth - 20.0F;
      float gridHeight = bodyHeight - 12.0F;
      float cardWidth = (gridWidth - 6.0F) / 2.0F;

      // сетка карточек модулей (2 колонки, скролл)
      this.moduleGrid = new iii_Class8()
         .I_method_485bccf5()
         .I_method_4f3850da(2)
         .I_method_70a38517(6.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 6.0F, 0.0F, 0.0F))
         .IIi_method_4dfc88d7(gridWidth)
         .IiI_method_31d4c97(gridHeight)
         .Ii_method_bb38d618()
         .II_method_d35e0034(80.0F)
         .I_method_3987f136(IiIi_Class6.I_field_f9448c81)
         .I_method_b65baecb(var0 -> var0.i_method_65326e43(-9.0F).I_method_4f0ff9cb(2.0F, 5.0F));

      // контейнер строк настроек
      this.settingsList = new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(5.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(0.0F, 2.0F, 2.0F, 0.0F))
         .IiI_method_31d4c97(Math.max(60.0F, gridHeight - 46.0F))
         .Ii_method_bb38d618()
         .II_method_d35e0034(60.0F)
         .I_method_3987f136(IiIi_Class6.I_field_f9448c81)
         .I_method_b65baecb(var0 -> var0.i_method_65326e43(-9.0F).I_method_4f0ff9cb(2.0F, 5.0F));
      this.settingsList.snapSize();

      // плавно появляющаяся панель настроек (с блюром, как в Wexside)
      float settingsOffsetX = Math.max(0.0F, gridWidth - this.settingsColWidth);
      iii_Class8 settingsPanel = (new iii_Class8() {
            @Override
            protected void drawChildren(III var1x, float var2x) {
               if (!Premium.this.closing && Premium.this.selectedModule != null) {
                  iIiiiiIii_Class380.I_method_5f4b621f();
                  iIiiIIiii_Class360.I_field_6424b12c.I_method_5711e8c1(1.0F);
                  float bx = this.x() - 8.0F;
                  float by = this.y() - 8.0F;
                  float bw = this.w() + 16.0F;
                  float bh = this.h() + 16.0F;
                  iIiiiIiII_Class373.I_method_d5a5ee51(var1x.getMatrices(), bx - 2.0F, by - 2.0F, bw + 4.0F, bh + 4.0F);
                  var1x.drawBackdropBlur(bx, by, bw, bh, 1.5F, 0.5F, 1.0F,
                     Premium.this.winX + 16.0F, Premium.this.winY + 16.0F,
                     Premium.this.winW - 32.0F, Premium.this.winH - 32.0F,
                     IIiii_Class8.I_method_893b2757(0.0F), IiiiiIIIi_Class242.Ii_field_d0c8ec5);
                  iIiiiIiII_Class373.I_method_ff80a1df();
               }
            }
         })
         .I_method_485bccf5()
         .I_method_70a38517(4.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(settingsOffsetX, 6.0F, 0.0F, 0.0F))
         .i_method_8c7d3515(this.settingsColWidth, gridHeight - 12.0F)
         .I_method_e16e9925(() -> this.selectedModule != null, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .IIiI_method_185ffdf8()
         .I_method_8939bffd(this.buildSettingsHeader())
         .I_method_8939bffd(this.settingsList);

      iii_Class8 body = new iii_Class8()
         .iI_method_c1109a58()
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(10.0F, 0.0F, 10.0F, 10.0F))
         .i_method_8c7d3515(this.contentWidth, bodyHeight)
         .I_method_8939bffd(this.moduleGrid)
         .I_method_8939bffd(settingsPanel);
      body.snapSize();
      return body;
   }

   @Compile(obfuscation = 1)
   private iii_Class8 buildSettingsHeader() {
      Iii_Class4 title = new Iii_Class4()
         .height(12.0F)
         .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(12.0F), this::getSettingsTitle, var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5)
         .interactive(false);
      this.keybindEditor = new iiiI_Class15(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), this::getSelectedKeybind, this::setSelectedKeybind);
      iii_Class8 keybindBlock = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .IiI_method_31d4c97(12.0F)
         .I_method_879bc687(() -> this.selectedModule != null)
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_8939bffd(this.keybindEditor)
         .i_method_5db1deeb(this.keybindEditor::I_method_10341dfa);
      iii_Class8 titleBlock = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(3.0F)
         .I_method_8939bffd(title)
         .I_method_8939bffd(keybindBlock);
      Iii_Class4 closeBtn = new Iii_Class4()
         .size(16.0F, 16.0F)
         .padding(3.5F)
         .icon("xmark", 9.0F, var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.8F + 0.2F * var1x.hover()))
         .radius(4.0F)
         .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
         .visibleWhen(() -> this.selectedModule != null)
         .onClick(() -> this.openModule(null));
      iii_Class8 headerTop = new iii_Class8()
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_fe5d8d56(IIIi_Class2.Ii_field_f93600a1)
         .IiI_method_31d4c97(12.0F)
         .III_method_9dc17555()
         .I_method_8939bffd(titleBlock)
         .I_method_8939bffd(closeBtn);
      iiII_Class13 description = new iiII_Class13(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), this::getSelectedDescription)
         .I_method_6da713d1(var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.58F))
         .i_method_6b618aeb()
         .II_method_4aef31a2();
      description.interactive(false);
      return new iii_Class8()
         .I_method_485bccf5()
         .I_method_70a38517(1.0F)
         .I_method_8939bffd(headerTop)
         .I_method_8939bffd(description);
   }

   // ===== ОТРИСОВКА ОКНА =====
   @Compile(obfuscation = 1)
   private void drawWindow(III var1, iii_Class8 var2) {
      float x = var2.x();
      float y = var2.y();
      float w = var2.w();
      float h = var2.h();
      ColorRGBA border = IiiiiIIIi_Class242.IiI_field_d0c8ec5;
      var1.drawClientRect(x, y, w, h, 0.92F, 0.82F, 2.0F, 14.0F, false, false);
      if (!this.closing) {
         var1.drawRoundedBorder(x, y, w, h, 0.5F, IIiii_Class8.I_method_893b2757(12.0F), border);
      }
   }

   // ===== ДЕЙСТВИЯ =====
   private void openSearchPalette() {
      if (this.searchPalette != null) {
         this.searchPalette.iI_method_59147882();
      }
   }

   @Compile(obfuscation = 1)
   private void selectCategory(ModuleCategory category) {
      if (this.selectedCategory != category) {
         if (this.keybindEditor != null) {
            this.keybindEditor.I_method_d4d8842c();
         }
         this.selectedCategory = category;
         this.bindingModule = null;
         this.moduleCache = List.of();
         this.refreshModules(true);
      }
   }

   @Compile(obfuscation = 1)
   private void onSearchNavigation(ModuleEntry module, Setting setting) {
      if (module != null) {
         if (this.keybindEditor != null) {
            this.keybindEditor.I_method_d4d8842c();
         }
         this.selectedCategory = module.getCategory();
         this.selectedModule = module;
         this.moduleCache = List.of();
         this.settingCache = List.of();
         this.refreshModules(true);
         this.refreshSettings(true);
         if (this.moduleGrid != null) {
            this.moduleGrid.i_method_815a851f();
         }
         iii_Class8 element = setting != null ? this.settingElements.get(setting) : this.moduleElements.get(module);
         this.highlightElement = element;
         this.settingsScroll = this.settingsList;
         this.highlightUntil = element == null ? 0L : System.currentTimeMillis() + 1600L;
      }
   }

   private void openModule(ModuleEntry module) {
      if (this.keybindEditor != null) {
         this.keybindEditor.I_method_d4d8842c();
      }
      this.selectedModule = module;
      this.bindingModule = null;
      this.settingCache = List.of();
      this.refreshSettings(true);
      if (this.settingsList != null) {
         this.settingsList.i_method_815a851f();
      }
   }

   // ===== СПИСОК МОДУЛЕЙ =====
   private List<ModuleEntry> getModulesOfCategory(ModuleCategory category) {
      return this.getAllModules().stream().filter(module -> module.getCategory() == category).toList();
   }

   private List<ModuleEntry> getAllModules() {
      return VelumClient.getInstance()
         .getModuleManager()
         .getModules()
         .stream()
         .filter(ModuleEntry::isAvailable)
         .sorted(Comparator.comparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER))
         .toList();
   }

   @Compile(obfuscation = 1)
   private void refreshModules(boolean force) {
      if (this.moduleGrid != null) {
         this.syncModuleVersion();
         String query = IiiIiiIiI_Class219.I_method_12a37157(this.searchField == null ? "" : this.searchField.I_method_d2579001());
         List<ModuleEntry> modules = query.isEmpty() ? this.getModulesOfCategory(this.selectedCategory) : this.getAllModules();
         if (force || !listsEqual(this.moduleCache, modules) || !query.equals(this.searchCache)) {
            ArrayList<ModuleEntry> sorted = new ArrayList<>(modules);
            if (!query.isEmpty()) {
               IdentityHashMap<ModuleEntry, Integer> scores = new IdentityHashMap<>();
               for (ModuleEntry module : sorted) {
                  scores.put(module, IiiIiiIiI_Class219.i_method_ca495fc6(IiiIiiIiI_Class219.I_method_12a37157(module.getName()), query));
               }
               sorted.removeIf(module -> scores.get(module) == Integer.MAX_VALUE);
               sorted.sort(Comparator.<ModuleEntry>comparingInt(scores::get).thenComparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER));
            }
            this.handleSearchSelection(query, sorted);
            ArrayList<iii_Class8> cards = new ArrayList<>();
            for (ModuleEntry module : sorted) {
               cards.add(this.moduleElements.computeIfAbsent(module, this::buildModuleCard));
            }
            this.moduleGrid.II_method_244860a9(cards);
            this.moduleCache = new ArrayList<>(modules);
            this.searchCache = query;
         }
      }
   }

   @Compile(obfuscation = 1)
   private void handleSearchSelection(String query, List<ModuleEntry> modules) {
      boolean hadQuery = !this.searchCache.isEmpty() && !this.searchCache.equals("\u0000");
      if (query.isEmpty()) {
         if (hadQuery && this.prevSearchModule != null && this.selectedModule != this.prevSearchModule) {
            this.openModule(this.prevSearchModule);
         }
         this.prevSearchModule = null;
      } else {
         if (!hadQuery) {
            this.prevSearchModule = this.selectedModule;
         }
         ModuleEntry first = modules.isEmpty() ? null : modules.getFirst();
         if (this.selectedModule != first) {
            this.openModule(first);
         }
      }
   }

   // карточка модуля (Wexside-стиль: рамка + фон + hover)
   @Compile(obfuscation = 1)
   private iii_Class8 buildModuleCard(ModuleEntry module) {
      Iii_Class4 label = new Iii_Class4()
         .fillWidth()
         .text(
            IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F),
            () -> this.getModuleText(module),
            var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5
               .mix(IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.5F * var1x.sig("enabled"))
               .mulAlpha(0.6F + 0.3F * var1x.sig("enabled") + 0.1F * var1x.sig("selected")))
         .bind("enabled", module::isEnabled, BIND_ANIM)
         .bind("selected", () -> this.selectedModule == module, BIND_ANIM)
         .interactive(false);
      Iii_Class4 check = new Iii_Class4()
         .size(6.0F, 6.0F)
         .icon("check", 6.0F, var1x -> IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(var1x.sig("enabled")))
         .bind("enabled", module::isEnabled, BIND_ANIM)
         .interactive(false);
      return new iii_Class8() {
            @Override
            protected void drawChildren(III var1x, float var2x) {
               var1x.drawRoundedBorder(this.x(), this.y(), this.w(), this.h(), 0.5F, IIiii_Class8.I_method_893b2757(5.0F), IiiiiIIIi_Class242.IiI_field_d0c8ec5);
               super.drawChildren(var1x, var2x);
            }
         }
         .i_method_c88da8d5()
         .I_method_483797dd(IIi_Class2.i_field_b5755e8c)
         .I_method_70a38517(5.0F)
         .i_method_8c7d3515(this.getCardWidth(), 26.0F)
         .I_method_7e2bb176(IIII.I_method_12c9a0cb(8.0F, 0.0F, 8.0F, 0.0F))
         .iI_method_e886414(5.0F)
         .I_method_e53800a9(var0 -> mixHover(IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F), IiiiiIIIi_Class242.ii_field_d0c8ec5, 0.08F * var0.sig("enabled") + 0.018F * var0.sig("selected") + 0.025F * var0.hover()))
         .I_method_350d9c12("enabled", module::isEnabled, BIND_ANIM)
         .I_method_350d9c12("selected", () -> this.selectedModule == module, BIND_ANIM)
         .I_method_9680b4fd(iIIIiIIIi_Class274.i_field_aa52e62c)
         .I_method_8939bffd(label)
         .I_method_8939bffd(check)
         .I_method_b60c4df8((button, mouseX, mouseY) -> this.onModuleClick(module, button));
   }

   private float getCardWidth() {
      return (this.contentWidth - 20.0F - 6.0F) / 2.0F;
   }

   private void onModuleClick(ModuleEntry module, IiIII_Class9 button) {
      if (button == IiIII_Class9.I_field_2f4c8d6c) {
         module.toggle();
      } else if (button == IiIII_Class9.i_field_2f4c8d6c) {
         if (this.selectedModule != module) {
            this.openModule(module);
         }
      } else if (button == IiIII_Class9.II_field_2f4c8d6c) {
         this.bindingModule = this.bindingModule == module ? null : module;
      }
   }

   @Compile(obfuscation = 1)
   private void syncModuleVersion() {
      int version = ModuleManager.I_method_7056a45f();
      if (version != this.modulesVersion) {
         this.modulesVersion = version;
         List<ModuleEntry> modules = VelumClient.getInstance().getModuleManager().getModules();
         if (this.bindingModule != null && !containsIdentity(modules, this.bindingModule)) {
            this.bindingModule = null;
         }
         if (this.prevSearchModule != null && !containsIdentity(modules, this.prevSearchModule)) {
            this.prevSearchModule = findSameModule(modules, this.prevSearchModule);
         }
         IdentityHashMap<ModuleEntry, Boolean> valid = new IdentityHashMap<>();
         for (ModuleEntry module : modules) {
            valid.put(module, Boolean.TRUE);
         }
         this.moduleElements.keySet().removeIf(module -> !valid.containsKey(module));
         if (this.selectedModule != null && !valid.containsKey(this.selectedModule)) {
            for (Setting setting : this.settingCache) {
               this.settingElements.remove(setting);
            }
            this.openModule(findSameModule(modules, this.selectedModule));
         }
      }
   }

   private static boolean containsIdentity(List<ModuleEntry> list, ModuleEntry module) {
      for (ModuleEntry entry : list) {
         if (entry == module) {
            return true;
         }
      }
      return false;
   }

   private static ModuleEntry findSameModule(List<ModuleEntry> list, ModuleEntry module) {
      for (ModuleEntry entry : list) {
         if (entry.getCategory() == module.getCategory() && entry.getName().equals(module.getName())) {
            return entry;
         }
      }
      return null;
   }

   // ===== НАСТРОЙКИ =====
   @Compile(obfuscation = 1)
   private void refreshSettings(boolean force) {
      if (this.settingsList != null) {
         List<Setting> settings = this.selectedModule == null ? List.of() : this.selectedModule.getSettings();
         if (force || !listsEqual(this.settingCache, settings)) {
            if (!this.settingCache.isEmpty()) {
               IdentityHashMap<Setting, Boolean> valid = new IdentityHashMap<>();
               for (Setting setting : settings) {
                  valid.put(setting, Boolean.TRUE);
               }
               for (Setting setting : this.settingCache) {
                  if (!valid.containsKey(setting)) {
                     this.settingElements.remove(setting);
                  }
               }
            }
            ArrayList<iiI_Class7> rows = new ArrayList<>();
            ArrayList<iiI_Class7> colA = new ArrayList<>();
            ArrayList<iiI_Class7> colB = new ArrayList<>();
            ArrayList<Setting> group = new ArrayList<>();
            int index = 0;
            for (Setting setting : settings) {
               if (setting instanceof SectionSetting) {
                  this.flushSettingGroup(rows, colA, colB, group);
                  index = 0;
                  rows.add(this.settingElements.computeIfAbsent(setting, this::buildSettingElement));
               } else {
                  (index++ % 2 == 0 ? colA : colB).add(this.settingElements.computeIfAbsent(setting, this::buildSettingElement));
                  group.add(setting);
               }
            }
            this.flushSettingGroup(rows, colA, colB, group);
            this.settingsList.i_method_d2e03546(rows);
            this.settingCache = new ArrayList<>(settings);
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }
   }

   @Compile(obfuscation = 1)
   private void flushSettingGroup(List<iiI_Class7> rows, List<iiI_Class7> colA, List<iiI_Class7> colB, List<Setting> group) {
      if (!colA.isEmpty() || !colB.isEmpty()) {
         List<Setting> groupCopy = List.copyOf(group);
         iii_Class8 colAEl = new iii_Class8().I_method_485bccf5().I_method_70a38517(5.0F).IIi_method_4dfc88d7(this.settingsGroupWidth).I_method_ef221966(colA);
         iii_Class8 colBEl = new iii_Class8().I_method_485bccf5().I_method_70a38517(5.0F).IIi_method_4dfc88d7(this.settingsGroupWidth).I_method_ef221966(colB);
         colAEl.snapSize();
         colBEl.snapSize();
         iii_Class8 row = new iii_Class8()
            .i_method_c88da8d5()
            .I_method_70a38517(5.0F)
            .I_method_483797dd(IIi_Class2.I_field_b5755e8c)
            .III_method_9dc17555()
            .I_method_e16e9925(() -> anyVisible(groupCopy), IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
            .IIiI_method_185ffdf8()
            .I_method_8939bffd(colAEl)
            .I_method_8939bffd(colBEl);
         row.snapSize();
         rows.add(row);
         colA.clear();
         colB.clear();
         group.clear();
      }
   }

   private static boolean anyVisible(List<Setting> settings) {
      for (Setting setting : settings) {
         if (setting.isVisible()) {
            return true;
         }
      }
      return false;
   }

   private iii_Class8 buildSettingElement(Setting setting) {
      return setting instanceof SectionSetting ? buildSectionElement(setting) : buildNormalElement(setting);
   }

   @Compile(obfuscation = 1)
   private iii_Class8 buildSectionElement(Setting setting) {
      iii_Class8 inner = IiiIiiIIi_Class218.i_method_f520787d(setting);
      inner.I_method_7e2bb176(IIII.I_field_f935fcc1);
      inner.snapSize();
      iii_Class8 wrapper = new iii_Class8()
         .I_method_485bccf5()
         .III_method_9dc17555()
         .I_method_e16e9925(setting::isVisible, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .IIiI_method_185ffdf8()
         .I_method_8939bffd(inner);
      wrapper.snapSize();
      return wrapper;
   }

   @Compile(obfuscation = 1)
   private iii_Class8 buildNormalElement(Setting setting) {
      iii_Class8 inner = IiiIiiIIi_Class218.i_method_f520787d(setting);
      inner.snapSize();
      iii_Class8 wrapper = new iii_Class8()
         .I_method_485bccf5()
         .III_method_9dc17555()
         .I_method_7e2bb176(IIII.II_method_714316ac(4.0F))
         .iI_method_e886414(7.0F)
         .I_method_e53800a9(var0 -> IiiiiIIIi_Class242.Ii_method_a0f56f71().mulAlpha(0.4F))
         .I_method_e16e9925(setting::isVisible, IiiiIiiII_Class237.IIii_field_dd60aac, 220L)
         .IIiI_method_185ffdf8()
         .I_method_8939bffd(inner);
      wrapper.snapSize();
      return wrapper;
   }

   private static boolean listsEqual(List<?> a, List<?> b) {
      if (a.size() != b.size()) {
         return false;
      }
      for (int i = 0; i < a.size(); i++) {
         if (a.get(i) != b.get(i)) {
            return false;
         }
      }
      return true;
   }

   // ===== ТЕКСТЫ =====
   private String getModuleText(ModuleEntry module) {
      if (this.bindingModule != module) {
         return module.getName();
      }
      int pending = iIIIIIIII_Class257.I_method_e761de12();
      if (pending != 0) {
         return IiIiIIII_Class81.I_method_f25a980a("key") + ": " + iIIIIIIII_Class257.I_method_f90bbc5c(pending) + "...";
      }
      return module.getKeybind() == -1
         ? IiIiIIII_Class81.I_method_f25a980a("menu.binding")
         : IiIiIIII_Class81.I_method_f25a980a("key") + ": " + iIIIIiIiI_Class267.I_method_a937749c(module.getKeybind());
   }

   private String getSettingsTitle() {
      return this.selectedModule == null ? "" : IiIiIIII_Class81.I_method_1410d1e5("menu.modern.settings", this.selectedModule.getName());
   }

   private String getSelectedDescription() {
      return this.selectedModule == null ? "" : this.selectedModule.i_method_bf522194();
   }

   private String getUsername() {
      String username = Profile.getUsername();
      return username == null ? "" : username;
   }

   private int getSelectedKeybind() {
      return this.selectedModule == null ? -1 : this.selectedModule.getKeybind();
   }

   private void setSelectedKeybind(int keybind) {
      if (this.selectedModule != null) {
         this.selectedModule.setKeybind(keybind);
         this.refreshUi();
      }
   }

   // ===== ЦВЕТОВОЙ ХЕЛПЕР =====
   private static ColorRGBA mixHover(ColorRGBA base, ColorRGBA target, float amount) {
      return base.mix(target, Math.min(1.0F, Math.max(0.0F, amount)));
   }

   // ===== РЕНДЕР + АНИМАЦИИ =====
   @Compile(obfuscation = 1)
   @Override
   public void render(III var1) {
      this.updateHideAnimation();
      this.updateSpotlight();
      GuiMoveModule.iII_method_9983ebf();
      if (!this.closing) {
         this.refreshModules(false);
         this.refreshSettings(false);
      }
      if (this.moduleGrid != null) {
         float scroll = this.moduleGrid.Iii_method_61e30d8f();
         if (scroll != this.gridScrollHeight) {
            this.gridScrollHeight = scroll;
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }
      if (System.currentTimeMillis() - this.openStartTime < 400L) {
         iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
      }
      if (this.root != null) {
         this.winX = this.root.x();
         this.winY = this.root.y();
         if (this.winX != this.prevWinX || this.winY != this.prevWinY) {
            this.prevWinX = this.winX;
            this.prevWinY = this.winY;
            iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
         }
      }

      // плавное открытие/закрытие: scale 0.7 -> 1.0 вокруг центра экрана
      float scale = this.closing ? 1.0F : 0.7F + 0.3F * this.openProgress();
      boolean scaling = Math.abs(scale - 1.0F) > 1.0E-4F;
      if (scaling) {
         var1.getMatrices().push();
         var1.getMatrices().translate(this.width / 2.0F, this.height / 2.0F, 0.0F);
         var1.getMatrices().scale(scale, scale, 1.0F);
         var1.getMatrices().translate(-this.width / 2.0F, -this.height / 2.0F, 0.0F);
      }

      IiiIiIIIi_Class210.I_method_bd87019f(this, var1);
      super.render(var1);
      IiiIiIIIi_Class210.i_method_789a4d7f(this, var1);
      if (scaling) {
         var1.getMatrices().pop();
      }
   }

   @Override
   protected void afterRender(III var1) {
      this.drawHighlight(var1);
   }

   @Compile(obfuscation = 1)
   private void drawHighlight(III var1) {
      if (this.highlightElement != null) {
         long remaining = this.highlightUntil - System.currentTimeMillis();
         if (remaining > 0L && this.highlightElement.inFlow()) {
            float scrollOffset = this.settingsScroll == null ? 0.0F : this.settingsScroll.Iii_method_61e30d8f();
            float x = this.highlightElement.x();
            float y = this.highlightElement.y() - scrollOffset;
            float w = this.highlightElement.w();
            float h = this.highlightElement.h();
            if (this.settingsScroll != null) {
               float top = this.settingsScroll.y();
               float bottom = this.settingsScroll.y() + this.settingsScroll.h();
               if (y < top) {
                  h -= top - y;
                  y = top;
               }
               if (y + h > bottom) {
                  h = bottom - y;
               }
            }
            if (!(w <= 0.0F) && !(h <= 0.0F)) {
               float fade = Math.min(1.0F, (float)remaining / 400.0F);
               var1.drawRoundedRect(x, y, w, h, IIiii_Class8.I_method_893b2757(5.0F), IiiiiIIIi_Class242.ii_field_d0c8ec5.mulAlpha(0.18F * fade));
            }
         } else {
            this.highlightElement = null;
         }
      }
   }

   private float openProgress() {
      float progress = Math.min(1.0F, Math.max(0.0F, (float)(System.currentTimeMillis() - this.openStartTime) / (float)OPEN_ANIM_MS));
      return IiiiIiiII_Class237.I_field_dd60aac.ease(progress, 0.0F, 1.0F, 1.0F);
   }

   // плавное исчезновение при удержании клавиши скрытия (как в "Новой")
   private void updateHideAnimation() {
      long now = System.currentTimeMillis();
      if (this.closing) {
         this.hideProgress = 0.0F;
         this.frameTime = now;
         this.contentAlpha = 1.0F;
      } else {
         float delta = this.frameTime == 0L ? 16.0F : Math.min(64.0F, (float)(now - this.frameTime));
         this.frameTime = now;
         MenuModule menu = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
         int hideKey = menu != null && menu.I_method_20c0695() != null ? menu.I_method_20c0695().I_method_fa6281d2() : -1;
         float target = isHideKeyHeld(hideKey) ? 1.0F : 0.0F;
         float step = delta / 300.0F;
         if (this.hideProgress < target) {
            this.hideProgress = Math.min(target, this.hideProgress + step);
         } else if (this.hideProgress > target) {
            this.hideProgress = Math.max(target, this.hideProgress - step);
         }
         this.contentAlpha = 1.0F - IiiiIiiII_Class237.III_field_dd60aac.ease(this.hideProgress, 0.0F, 1.0F, 1.0F);
      }
   }

   private void updateSpotlight() {
      if (!this.closing && !(this.contentAlpha >= 0.999F)) {
         long handle = MinecraftClient.getInstance().getWindow().getHandle();
         boolean leftPressed = org.lwjgl.glfw.GLFW.glfwGetMouseButton(handle, 0) == 1;
         if (!leftPressed || iiI_Class7.spotlight() == null) {
            iii_Class8 found = null;
            for (Entry<Setting, iii_Class8> entry : this.settingElements.entrySet()) {
               iii_Class8 element = entry.getValue();
               if (element.inFlow() && element.hovered() && IiiIiiIIi_Class218.I_method_120ab0ab(entry.getKey())) {
                  found = element;
                  break;
               }
            }
            iiI_Class7.spotlight(found);
         }
      } else {
         iiI_Class7.spotlight(null);
      }
   }

   private static boolean isHideKeyHeld(int keybind) {
      return iIIIIIIII_Class257.II_method_98333a25(keybind);
   }

   private void refreshUi() {
      iIiiIIiii_Class360.I_field_6424b12c.i_method_6e3866bf();
   }

   // ===== ВВОД =====
   @Compile(obfuscation = 1)
   @Override
   public void onMouseClicked(double mouseX, double mouseY, IiIII_Class9 button) {
      if (iiiI_Class15.i_method_3a56361e(button)) {
         this.refreshUi();
      } else {
         boolean overOverlay = this.overlays.stream().anyMatch(overlay -> overlay.alive() && overlay.contains((float)mouseX, (float)mouseY));
         if (this.searchPalette != null && !overOverlay) {
            this.searchPalette.I_method_911c871f((float)mouseX, (float)mouseY);
         }
         if (this.bindingModule != null) {
            if (button == IiIII_Class9.I_field_2f4c8d6c) {
               this.bindingModule = null;
               return;
            }
            if (button != IiIII_Class9.II_field_2f4c8d6c) {
               this.bindingModule.setKeybind(iIIIIIIII_Class257.Ii_method_99f62a34(button.I_method_6d899712()));
               this.bindingModule = null;
               this.refreshUi();
               return;
            }
         }
         super.onMouseClicked(mouseX, mouseY, button);
      }
   }

   @Compile(obfuscation = 1)
   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (this.bindingModule != null && !iiiI_Class15.i_method_d4e71010()) {
         if (Screen.hasControlDown() && keyCode == 90 && IIiiiIIIi_Class114.I_method_fe3ce603()) {
            return true;
         }
         if (Screen.hasControlDown() && keyCode == 89 && IIiiiIIIi_Class114.i_method_fe4b71e3()) {
            return true;
         }
      }
      if (this.bindingModule != null) {
         if (keyCode != 256 && keyCode != 261) {
            int keybind = iIIIIIIII_Class257.i_method_cd0c4692(keyCode, modifiers);
            if (keybind == Integer.MIN_VALUE) {
               return true;
            }
            this.bindingModule.setKeybind(keybind);
            this.bindingModule = null;
            this.refreshUi();
            return true;
         }
         this.bindingModule.setKeybind(-1);
         this.bindingModule = null;
         this.refreshUi();
         return true;
      }
      if (super.keyPressed(keyCode, scanCode, modifiers)) {
         return true;
      }
      if (MenuModule.I_method_48514ce8(keyCode)) {
         this.close();
         return true;
      }
      return false;
   }

   @Override
   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      if (this.bindingModule != null) {
         int keybind = iIIIIIIII_Class257.II_method_6e347cf5(keyCode, modifiers);
         if (keybind != Integer.MIN_VALUE) {
            this.bindingModule.setKeybind(keybind);
            this.bindingModule = null;
            this.refreshUi();
            return true;
         }
      }
      return super.keyReleased(keyCode, scanCode, modifiers);
   }

   @Override
   public void tick() {
      GuiMoveModule.iII_method_9983ebf();
      super.tick();
   }

   // ===== IiiIiIIiI_Class211 (анимационный интерфейс — как у двух других гуи) =====
   @Override
   public String I_method_8c872841() {
      return "premium";
   }

   @Override
   public float I_method_9833b22f() {
      return this.openProgress();
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
      return this.closing ? 1.0F : 0.7F + 0.3F * this.openProgress();
   }

   @Override
   public List<IiiIiIIiI_Class211.Nested1_972f6c40> I_method_cb1d7a28() {
      return List.of(new IiiIiIIiI_Class211.Nested1_972f6c40("window", this.winX, this.winY, this.winW, this.winH));
   }

   public static Premium getInstance() {
      return instance;
   }
}