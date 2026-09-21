package ru.wexside;

import ru.wexside.config.LocalConfigCatalog;
import ru.wexside.event.EventBus;
import ru.wexside.event.EventBusImpl;
import ru.wexside.misc.ClientProfile;
import ru.wexside.misc.ClientRole;
import ru.wexside.misc.ConfigRegistry;
import ru.wexside.misc.ContainerDisplaySettings;
import ru.wexside.module.ModuleManager;
import ru.wexside.util.ClickGuiPanel;
import ru.wexside.util.GuiDrawApi;
import velum.client.wexside.VelumWexsideClickGuiPanel;
import velum.client.wexside.VelumWexsideModuleManager;

/**
 * Compatibility facade used by the untouched Wexside GUI classes.
 * It deliberately does not bootstrap the Wexside client or its modules.
 */
public class WexSideClient {
    private static final WexSideClient INSTANCE = new WexSideClient();
    private final EventBus eventBus = new EventBusImpl();
    private final ConfigRegistry configRegistry = new ConfigRegistry();
    private final ContainerDisplaySettings containerDisplaySettings = new ContainerDisplaySettings(configRegistry);
    private final LocalConfigCatalog localConfigCatalog = new LocalConfigCatalog(null);
    private final VelumWexsideModuleManager moduleManager = new VelumWexsideModuleManager();
    private final ClientProfile clientProfile = ClientProfile.fromSerializedFields(
            "Velum", "Velum", "", "0", false, false, new byte[0]);
    private final ClickGuiPanel miscellaneous = new VelumWexsideClickGuiPanel(
            moduleManager, containerDisplaySettings, localConfigCatalog);

    public static WexSideClient getInstance() { return INSTANCE; }
    public ModuleManager getModuleManager() { return moduleManager; }
    public ClickGuiPanel getMiscellaneous() { return miscellaneous; }
    public ClientProfile getClientProfile() { return clientProfile; }
    public LocalConfigCatalog getLocalConfigCatalog() { return localConfigCatalog; }
    public ConfigRegistry getConfigRegistry() { return configRegistry; }
    public static EventBus getEventBus() { return INSTANCE.eventBus; }
    public static GuiDrawApi getGuiRenderer() { return getHudRenderer(); }
    public static GuiDrawApi getHudRenderer() { return GuiDrawApiHolder.get(); }

    private static final class GuiDrawApiHolder {
        private static GuiDrawApi value;
        static GuiDrawApi get() {
            if (value == null) {
                value = new GuiDrawApi(new ru.wexside.render.WexGlobals(ru.wexside.render.ClientRenderPipelines.GUI_BATCH));
            }
            return value;
        }
    }
}
