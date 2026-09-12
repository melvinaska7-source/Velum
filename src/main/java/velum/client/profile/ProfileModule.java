package velum.client.profile;

import velum.client.BooleanSetting;
import velum.client.ModeSetting;
import velum.client.Module;
import velum.client.ModuleCategory;
import velum.client.ModuleInfo;

@ModuleInfo(
    name = "Profile",
    category = ModuleCategory.PLAYER,
    iI_method_476ab839 = true,
    III_method_a89e5834 = "modules.descriptions.profile"
)
public final class ProfileModule extends Module {
    private final ProfileManager profileManager = ProfileManager.getInstance();
    private final PetManager petManager = new PetManager();
    private final ModelManager modelManager = new ModelManager();

    private final ModeSetting playerModel = new ModeSetting(this, "modules.settings.profile.player_model");
    private final ModeSetting pets = new ModeSetting(this, "modules.settings.profile.pets");
    private final ModeSetting accessories = new ModeSetting(this, "modules.settings.profile.accessories");
    private final ModeSetting cosmetics = new ModeSetting(this, "modules.settings.profile.cosmetics");
    private final BooleanSetting animatePets = new BooleanSetting(this, "modules.settings.profile.animate_pets");
    private final BooleanSetting showInThirdPerson = new BooleanSetting(this, "modules.settings.profile.show_in_third_person");

    public ProfileModule() {
        profileManager.setModule(this);
        playerModel.I_method_15e42ca0("Default", "Amogus", "Rabbit", "Demon", "Freddy");
        pets.I_method_15e42ca0("None", "Jellie", "T-Rex");
        accessories.I_method_15e42ca0("None", "Back Sword");
        cosmetics.I_method_15e42ca0("None", "Halo");
        animatePets.I_method_4e1408b5(true);
        showInThirdPerson.I_method_4e1408b5(true);
    }

    @Override
    public void onEnable() {
        profileManager.register();
    }

    @Override
    public void onDisable() {
        profileManager.unregister();
    }

    public ModeSetting getPlayerModelSetting() {
        return playerModel;
    }

    public ModeSetting getPetsSetting() {
        return pets;
    }

    public ModeSetting getAccessoriesSetting() {
        return accessories;
    }

    public ModeSetting getCosmeticsSetting() {
        return cosmetics;
    }

    public BooleanSetting getAnimatePetsSetting() {
        return animatePets;
    }

    public BooleanSetting getShowInThirdPersonSetting() {
        return showInThirdPerson;
    }

    public PetManager getPetManager() {
        return petManager;
    }

    public ModelManager getModelManager() {
        return modelManager;
    }
}
