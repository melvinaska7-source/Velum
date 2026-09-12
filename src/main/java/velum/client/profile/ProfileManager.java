package velum.client.profile;

import pyvelum.events.render.Render3DEvent;
import velum.client.IiIIIiII_Class69;
import velum.client.VelumClient;

/** Runtime controller for the visual Profile module. */
public final class ProfileManager {
    private static ProfileManager instance;

    private final ProfilePetRenderer petRenderer = new ProfilePetRenderer();
    private final ProfileAccessoryRenderer accessoryRenderer = new ProfileAccessoryRenderer();
    private final IiIIIiII_Class69<Render3DEvent> onRender3D = this::render3D;
    private ProfileModule module;
    private boolean registered;

    private ProfileManager() {
    }

    public static ProfileManager getInstance() {
        if (instance == null) {
            instance = new ProfileManager();
        }
        return instance;
    }

    public void setModule(ProfileModule module) {
        this.module = module;
    }

    public void register() {
        if (registered) {
            return;
        }
        VelumClient.getInstance().I_method_7897deab().I_method_2257cd48(this);
        registered = true;
    }

    public void unregister() {
        if (!registered) {
            return;
        }
        VelumClient.getInstance().I_method_7897deab().i_method_f30f2128(this);
        registered = false;
    }

    private void render3D(Render3DEvent event) {
        if (module == null || !module.isEnabled()) {
            return;
        }
        petRenderer.render(event, module);
        accessoryRenderer.render(event, module);
    }
}
