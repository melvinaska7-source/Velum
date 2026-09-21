package velum.client.wexside;

import java.util.Objects;
import ru.wexside.event.EventBus;
import ru.wexside.module.ModuleCategory;
import ru.wexside.setting.BooleanSetting;
import velum.client.Module;

/** A Wexside module-shaped view backed by one live Velum module. */
public final class VelumWexsideModule extends ru.wexside.module.Module {
    private final Module velum;
    private final BooleanSetting toggle;
    private boolean syncing;

    public VelumWexsideModule(EventBus bus, Module velum) {
        super(bus, "velum_" + velum.getName().replaceAll("[^A-Za-z0-9_]+", "_"),
                "", velum.getName(), category(velum), velum.getName());
        this.velum = Objects.requireNonNull(velum);
        this.toggle = registerToggle(BooleanSetting.builder()
                .id("enabled")
                .name(velum.getName())
                .description(velum.getName())
                .value(velum.isEnabled())
                .build());
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
