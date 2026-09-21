package velum.client.wexside;

import java.util.ArrayList;
import java.util.List;
import velum.client.Module;
import velum.client.ModuleCategory;
import velum.client.ModuleEntry;
import velum.client.VelumClient;
import ru.wexside.event.EventBusImpl;
import ru.wexside.module.ModuleManager;

/**
 * Wexside GUI-facing module manager. The Wexside GUI still receives its own
 * Module objects; those objects are thin views over Velum modules.
 */
public final class VelumWexsideModuleManager extends ModuleManager {
    private final List<VelumWexsideModule> bridgeModules = new ArrayList<>();

    public VelumWexsideModuleManager() {
        super();
        rebuild();
    }

    public void rebuild() {
        bridgeModules.clear();
        if (VelumClient.getInstance() == null || VelumClient.getInstance().getModuleManager() == null) return;
        for (ModuleEntry entry : VelumClient.getInstance().getModuleManager().getModules()) {
            if (entry instanceof Module module) {
                bridgeModules.add(new VelumWexsideModule(new EventBusImpl(), module));
            }
        }
    }

    @Override
    public List<ru.wexside.module.Module> getModules() {
        sync();
        return new ArrayList<>(bridgeModules);
    }

    public void sync() {
        for (VelumWexsideModule module : bridgeModules) module.syncFromVelum();
    }

    public List<VelumWexsideModule> getBridgeModules() {
        return bridgeModules;
    }

    private static ru.wexside.module.ModuleCategory category(ModuleCategory category) {
        return switch (category) {
            case COMBAT -> ru.wexside.module.ModuleCategory.COMBAT;
            case MOVEMENT -> ru.wexside.module.ModuleCategory.MOVEMENT;
            case PLAYER -> ru.wexside.module.ModuleCategory.PLAYER;
            case VISUALS -> ru.wexside.module.ModuleCategory.RENDER;
            case OTHER -> ru.wexside.module.ModuleCategory.MISC;
        };
    }
}
