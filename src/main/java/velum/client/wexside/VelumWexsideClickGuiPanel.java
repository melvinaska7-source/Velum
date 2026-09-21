package velum.client.wexside;

import ru.wexside.config.LocalConfigCatalog;
import ru.wexside.misc.ContainerDisplaySettings;
import ru.wexside.util.ClickGuiPanel;

/** Keeps the original Wexside ClickGuiPanel/rendering intact; only syncs its backend. */
public final class VelumWexsideClickGuiPanel extends ClickGuiPanel {
    private final VelumWexsideModuleManager manager;

    public VelumWexsideClickGuiPanel(VelumWexsideModuleManager manager,
                                     ContainerDisplaySettings display,
                                     LocalConfigCatalog configs) {
        super(manager, display, configs);
        this.manager = manager;
    }

    @Override
    public void update() {
        syncToVelum();
        super.update();
        syncFromVelum();
    }

    @Override
    public void update2() {
        syncToVelum();
        super.update2();
        syncFromVelum();
    }

    private void syncToVelum() {
        for (VelumWexsideModule m : manager.getBridgeModules()) m.syncToVelum();
    }

    private void syncFromVelum() {
        manager.sync();
    }
}
