package velum.client.profile;

/**
 * Selection/state holder for Profile pets.
 * Rendering is added separately so changing a setting never creates render
 * resources or parses model files on the game thread.
 */
public final class PetManager {
    public enum Pet {
        NONE("None"),
        JELLIE("Jellie"),
        TREX("T-Rex");

        private final String displayName;

        Pet(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    private Pet selected = Pet.NONE;

    public Pet getSelected() {
        return selected;
    }

    public void setSelected(Pet pet) {
        selected = pet == null ? Pet.NONE : pet;
    }
}
