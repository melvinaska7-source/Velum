package velum.client.profile;

/** State holder for selectable custom player models. */
public final class ModelManager {
    public enum PlayerModel {
        DEFAULT("Default"),
        AMOGUS("Amogus"),
        RABBIT("Rabbit"),
        DEMON("Demon"),
        FREDDY("Freddy");

        private final String displayName;

        PlayerModel(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private PlayerModel selected = PlayerModel.DEFAULT;

    public PlayerModel getSelected() {
        return selected;
    }

    public void setSelected(PlayerModel model) {
        selected = model == null ? PlayerModel.DEFAULT : model;
    }
}
