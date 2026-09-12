package velum.client.profile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyvelum.events.render.Render3DEvent;

/** Renders the currently selected Profile pet near the local player. */
public final class ProfilePetRenderer {
    private static final Identifier JELLIE_MODEL = Identifier.of("velum", "models/pets/jellie.obj");
    private static final Identifier JELLIE_TEXTURE = Identifier.of("velum", "textures/models/pets/jellie.png");
    private static final Identifier TREX_MODEL = Identifier.of("velum", "models/pets/trex.obj");
    private static final Identifier TREX_TEXTURE = Identifier.of("velum", "textures/models/pets/trex1.png");

    private double x;
    private double y;
    private double z;
    private boolean initialized;

    public void render(Render3DEvent event, ProfileModule module) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }
        // Keep the runtime selection synchronized with the GUI before deciding whether there is a pet.
        PetManager.Pet pet;
        if (module.getPetsSetting().I_method_1544ce2d("Jellie")) {
            pet = PetManager.Pet.JELLIE;
        } else if (module.getPetsSetting().I_method_1544ce2d("T-Rex")) {
            pet = PetManager.Pet.TREX;
        } else {
            pet = PetManager.Pet.NONE;
        }
        module.getPetManager().setSelected(pet);

        if (pet == PetManager.Pet.NONE) {
            initialized = false;
            return;
        }
        // This option controls whether the pet is rendered while the camera is in first person.
        // When disabled, the companion is only visible from third person.
        if (client.options.getPerspective() == Perspective.FIRST_PERSON
                && !module.getShowInThirdPersonSetting().i_method_9b12da03()) {
            return;
        }

        float tickDelta = event.getTickDelta();
        Vec3d playerPos = client.player.getLerpedPos(tickDelta);
        float yaw = client.player.getYaw(tickDelta);
        double yawRad = Math.toRadians(yaw);

        // Slightly behind and to the side of the player, like a small companion.
        double back = 1.15;
        double side = 0.65;
        double targetX = playerPos.x - Math.sin(yawRad) * back + Math.cos(yawRad) * side;
        double targetZ = playerPos.z + Math.cos(yawRad) * back + Math.sin(yawRad) * side;
        double targetY = playerPos.y;

        if (!initialized) {
            x = targetX;
            y = targetY;
            z = targetZ;
            initialized = true;
        } else {
            double smoothing = module.getAnimatePetsSetting().i_method_9b12da03() ? 0.18 : 1.0;
            x = MathHelper.lerp(smoothing, x, targetX);
            y = MathHelper.lerp(smoothing, y, targetY);
            z = MathHelper.lerp(smoothing, z, targetZ);
        }

        // Tiny idle bob. It is purely visual and does not allocate anything per frame.
        double bob = module.getAnimatePetsSetting().i_method_9b12da03()
                ? Math.sin((client.player.age + tickDelta) * 0.16D) * 0.035D
                : 0.0D;

        double cameraX = event.getCamera().getPos().x;
        double cameraY = event.getCamera().getPos().y;
        double cameraZ = event.getCamera().getPos().z;

        MatrixStack matrices = event.getMatrices();
        matrices.push();
        try {
            matrices.translate(x - cameraX, y - cameraY + 0.02D + bob, z - cameraZ);
            matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));

            if (pet == PetManager.Pet.JELLIE) {
                ObjModelRenderer.render(JELLIE_MODEL, JELLIE_TEXTURE, matrices, 0.8f / 16.0f);
            } else if (pet == PetManager.Pet.TREX) {
                ObjModelRenderer.render(TREX_MODEL, TREX_TEXTURE, matrices, 0.8f / 16.0f);
            }
        } finally {
            matrices.pop();
        }
    }
}
