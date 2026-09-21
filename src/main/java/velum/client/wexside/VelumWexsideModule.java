package velum.client.wexside;

import java.util.Objects;
import ru.wexside.event.EventBus;
import ru.wexside.module.ModuleCategory;
import ru.wexside.setting.BooleanSetting;
import ru.wexside.setting.BooleanSettingBuilder;
import velum.client.Module;

/** A Wexside module-shaped view backed by one live Velum module. */
public final class VelumWexsideModule extends ru.wexside.module.Module {
    private final Module velum;
    private final BooleanSetting toggle;
    private boolean syncing;

    public VelumWexsideModule(EventBus bus, Module velum) {
        super(bus, "velum_" + velum.getName().replaceAll("[^A-Za-z0-9_]+", "_"),
                "", velum.getName(), category(velum.getCategory()), velum.getName());
        this.velum = Objects.requireNonNull(velum);
        BooleanSettingBuilder toggleBuilder = BooleanSetting.builder();
        toggleBuilder.id("enabled");
        toggleBuilder.name(velum.getName());
        toggleBuilder.description(velum.getName());
        toggleBuilder.value(velum.isEnabled());
        this.toggle = registerToggle(toggleBuilder.build());
    }

    @Override
    protected void initialize() {
    }

    public Module getVelumModule() { return velum; }

    public void syncFromVelum() {
        syncing = true;
        try { toggle.setEnabled(velum.isEnabled()); }
        finally { syncing = false; }
    }

    public void syncToVelum() {
        if (syncing) return;
        boolean desired = toggle.isEnabled();
        if (desired != velum.isEnabled()) velum.setEnabled(desired, false);
    }

    private static ModuleCategory category(velum.client.ModuleCategory category) {
        return switch (category) {
            case COMBAT -> ModuleCategory.COMBAT;
            case MOVEMENT -> ModuleCategory.MOVEMENT;
            case PLAYER -> ModuleCategory.PLAYER;
            case VISUALS -> ModuleCategory.RENDER;
            case OTHER -> ModuleCategory.MISC;
        };
    }
}
