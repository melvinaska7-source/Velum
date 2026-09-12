package velum.client;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.screen.Screen;
import pyvelum.utility.render.ColorRGBA;

/**
 * Third Menu GUI: a centered "window" layout inspired by the reference UI,
 * but built entirely on Velum's own component/settings system.
 */
public final class WindowMenuScreen extends IIiI_Class3 implements IiiIiIIiI_Class211 {
    private static final float WIDTH = 820.0F;
    private static final float HEIGHT = 500.0F;

    private ModuleCategory selectedCategory = ModuleCategory.COMBAT;
    private ModuleEntry selectedModule;
    private iii_Class8 root;

    @Override
    public void init() {
        super.init();
        this.clearRoots();
        this.selectedModule = null;

        final ModuleManager manager = VelumClient.getInstance().getModuleManager();
        final List<ModuleEntry> modules = manager.getModules().stream()
            .filter(ModuleEntry::isAvailable)
            .sorted(Comparator.comparing(ModuleEntry::getName, String.CASE_INSENSITIVE_ORDER))
            .toList();

        this.root = new iii_Class8()
            .I_method_3301fdd(iII_Class5.I_field_b583e68c)
            .IIi_method_4dfc88d7(WIDTH)
            .IiI_method_31d4c97(HEIGHT)
            .I_method_70a38517(10.0F)
            .I_method_f136b1d8((ctx, box) -> {
                ctx.drawShadow(box.x(), box.y(), box.w(), box.h(), 26.0F,
                    IIiii_Class8.I_method_893b2757(12.0F), new ColorRGBA(0.0F, 0.0F, 0.0F, 55.0F));
                ctx.drawRoundedRect(box.x(), box.y(), box.w(), box.h(),
                    IIiii_Class8.I_method_893b2757(12.0F), new ColorRGBA(12.0F, 12.0F, 16.0F, 235.0F));
                ctx.drawRoundedBorder(box.x(), box.y(), box.w(), box.h(), 1.0F,
                    IIiii_Class8.I_method_893b2757(12.0F), new ColorRGBA(255.0F, 255.0F, 255.0F, 24.0F));
            });

        this.root.I_method_8939bffd(new Iii_Class4()
            .height(34.0F)
            .fillWidth()
            .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(10.0F), "Velum",
                c -> IiiiiIIIi_Class242.iI_method_8e08d3b1())
            .textAlign(IIi_Class2.Ii_field_b5755e8c)
            .padding(IIII.I_method_14640aa3(8.0F)));

        iii_Class8 content = new iii_Class8()
            .I_method_3301fdd(iII_Class5.II_field_b583e68c)
            .fillWidth()
            .height(440.0F)
            .I_method_70a38517(8.0F);

        iii_Class8 categories = new iii_Class8()
            .I_method_3301fdd(iII_Class5.I_field_b583e68c)
            .IIi_method_4dfc88d7(118.0F)
            .fillHeight()
            ;

        for (ModuleCategory category : ModuleCategory.values()) {
            categories.I_method_8939bffd(new Iii_Class4()
                .height(27.0F)
                .fillWidth()
                .radius(6.0F)
                .padding(IIII.I_method_14640aa3(6.0F))
                .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), category.name(),
                    c -> selectedCategory == category
                        ? new ColorRGBA(255.0F, 255.0F, 255.0F, 255.0F)
                        : new ColorRGBA(190.0F, 190.0F, 200.0F, 205.0F))
                .background(c -> selectedCategory == category
                    ? new ColorRGBA(87.0F, 126.0F, 255.0F, 105.0F)
                    : new ColorRGBA(255.0F, 255.0F, 255.0F, 18.0F))
                .onClick(() -> {
                    selectedCategory = category;
                    selectedModule = null;
                }));
        }

        iii_Class8 moduleColumns = new iii_Class8()
            .I_method_3301fdd(iII_Class5.II_field_b583e68c)
            .fillWidth()
            .fillHeight()
            ;

        List<iii_Class8> columns = List.of(
            new iii_Class8().I_method_3301fdd(iII_Class5.I_field_b583e68c).fillWidth().fillHeight(),
            new iii_Class8().I_method_3301fdd(iII_Class5.I_field_b583e68c).fillWidth().fillHeight(),
            new iii_Class8().I_method_3301fdd(iII_Class5.I_field_b583e68c).fillWidth().fillHeight()
        );
        columns.forEach(moduleColumns::I_method_8939bffd);

        for (ModuleEntry module : modules) {
            int column = Math.floorMod(module.getCategory().ordinal(), 3);
            Iii_Class4 card = new Iii_Class4()
                .height(31.0F)
                .fillWidth()
                .radius(7.0F)
                .padding(IIII.I_method_14640aa3(7.0F))
                .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(7.0F), module.getName(),
                    c -> module.isEnabled()
                        ? new ColorRGBA(255.0F, 255.0F, 255.0F, 255.0F)
                        : new ColorRGBA(205.0F, 205.0F, 215.0F, 220.0F))
                .background(c -> module.isEnabled()
                    ? new ColorRGBA(87.0F, 126.0F, 255.0F, 100.0F)
                    : new ColorRGBA(255.0F, 255.0F, 255.0F, 16.0F))
                .visibleWhen(() -> module.getCategory() == selectedCategory)
                .onClick(() -> selectedModule = module);
            columns.get(column).I_method_8939bffd(card);
        }

        iii_Class8 moduleArea = new iii_Class8()
            .I_method_3301fdd(iII_Class5.I_field_b583e68c)
            .fillWidth()
            .fillHeight()
            ;
        moduleArea.I_method_8939bffd(new Iii_Class4()
            .height(20.0F).fillWidth()
            .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F), "Modules",
                c -> IiiiiIIIi_Class242.iI_method_8e08d3b1())
            .padding(IIII.I_method_14640aa3(3.0F)));
        moduleArea.I_method_8939bffd(moduleColumns);

        iii_Class8 settings = new iii_Class8()
            .I_method_3301fdd(iII_Class5.I_field_b583e68c)
            .IIi_method_4dfc88d7(250.0F)
            .fillHeight()
            
            .I_method_f136b1d8((ctx, box) -> ctx.drawRoundedRect(box.x(), box.y(), box.w(), box.h(),
                IIiii_Class8.I_method_893b2757(8.0F), new ColorRGBA(255.0F, 255.0F, 255.0F, 10.0F)));

        settings.I_method_8939bffd(new Iii_Class4()
            .height(28.0F).fillWidth()
            .text(IIiIiI_Class11.II_field_857c0621.I_method_3a2d5e3(8.0F),
                () -> selectedModule == null ? "Settings" : selectedModule.getName(),
                c -> IiiiiIIIi_Class242.iI_method_8e08d3b1())
            .padding(IIII.I_method_14640aa3(7.0F)));

        for (ModuleEntry module : modules) {
            for (Setting setting : module.getSettings()) {
                iii_Class8 component = setting.createComponent();
                if (component == null) continue;
                settings.I_method_8939bffd(component.visibleWhen(() -> selectedModule == module && setting.isVisible()));
            }
        }

        content.I_method_8939bffd(categories);
        content.I_method_8939bffd(moduleArea);
        content.I_method_8939bffd(settings);
        this.root.I_method_8939bffd(content);
        this.add(this.root);
    }

    @Override
    public void render(III context) {
        IiiIiIIIi_Class210.I_method_bd87019f(this, context);
        super.render(context);
        IiiIiIIIi_Class210.i_method_789a4d7f(this, context);
    }

    @Override
    public void removed() {
        VelumClient.getInstance().I_method_5198232b().II_method_1fbeeff5();
        super.removed();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (MenuModule.I_method_48514ce8(keyCode)) {
            this.close();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public String I_method_8c872841() {
        return "window";
    }

    @Override
    public float I_method_9833b22f() {
        return 1.0F;
    }

    @Override
    public float i_method_98423e0f() {
        return 0.0F;
    }

    @Override
    public boolean I_method_9833b243() {
        return false;
    }

    @Override
    public float II_method_6e518cf2() {
        return this.contentAlpha;
    }

    @Override
    public float Ii_method_6e6018d2() {
        return 1.0F;
    }

    @Override
    public List<IiiIiIIiI_Class211.Nested1_972f6c40> I_method_cb1d7a28() {
        return List.of(new IiiIiIIiI_Class211.Nested1_972f6c40("window", 0.0F, 0.0F, WIDTH, HEIGHT));
    }
}
