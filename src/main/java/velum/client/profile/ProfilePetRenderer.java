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
    private float petYaw;
    private boolean initialized;
    private float followYaw;

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

        // Do not rotate/reposition the pet just because the player turns while standing still.
        // The companion only receives a new follow direction while the player is actually moving.
        Vec3d velocity = client.player.getVelocity();
        boolean moving = velocity.x * velocity.x + velocity.z * velocity.z > 0.00035D;
        if (!initialized) {
            followYaw = yaw;
        } else if (moving) {
            followYaw = yaw;
        }
        double yawRad = Math.toRadians(followYaw);

        // Keep the companion clearly BEHIND the player.  Minecraft's forward vector is
        // (-sin(yaw), cos(yaw)), so the backward vector is its opposite.
        // A small side offset keeps the pet visible when looking around in first person.
        double back = 1.55;
        double side = 0.38;
        double targetX = playerPos.x + Math.sin(yawRad) * back + Math.cos(yawRad) * side;
        double targetZ = playerPos.z - Math.cos(yawRad) * back + Math.sin(yawRad) * side;
        double targetY = playerPos.y;

        if (!initialized) {
            x = targetX;
            y = targetY;
            z = targetZ;
            petYaw = followYaw + 180.0f;
            initialized = true;
        } else {
            // Strong positional lag: the pet visibly trails behind while moving.
            // While the player is standing still, targetX/Z remain unchanged, so turning
            // the camera does not drag the pet around the player.
            double smoothing = module.getAnimatePetsSetting().i_method_9b12da03() ? 0.045 : 1.0;
            x = MathHelper.lerp(smoothing, x, targetX);
            y = MathHelper.lerp(smoothing, y, targetY);
            z = MathHelper.lerp(smoothing, z, targetZ);

            if (module.getAnimatePetsSetting().i_method_9b12da03() && moving) {
                // Rotation follows even more slowly than position, so the pet can actually
                // remain visible when the player changes direction.
                float targetPetYaw = followYaw + 180.0f;
                petYaw = lerpAngle(petYaw, targetPetYaw, 0.018f);
            }
        }

        // Tiny idle bob. It is purely visual and does not allocate anything per frame.
        double bob = module.getAnimatePetsSetting().i_method_9b12da03() && moving
                ? Math.sin((client.player.age + tickDelta) * 0.16D) * 0.035D
                : 0.0D;

        double cameraX = event.getCamera().getPos().x;
        double cameraY = event.getCamera().getPos().y;
        double cameraZ = event.getCamera().getPos().z;

        MatrixStack matrices = event.getMatrices();
        matrices.push();
        try {
            matrices.translate(x - cameraX, y - cameraY + 0.02D + bob, z - cameraZ);
            // Pet models use the opposite forward direction from the player.
            matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(-petYaw));

            // Jellie was authored in Blockbench with Z as its vertical axis.
            // The generic OBJ loader grounds models using their original Y axis, so
            // Jellie needs a model-specific axis correction after that normalization.
            if (pet == PetManager.Pet.JELLIE) {
                float scale = 0.8f / 16.0f;
                matrices.translate(0.0D, 22.28602849D * scale, 0.0D);
                matrices.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_X.rotationDegrees(-90.0f));
                ObjModelRenderer.render(JELLIE_MODEL, JELLIE_TEXTURE, matrices, scale);
            } else if (pet == PetManager.Pet.TREX) {
                ObjModelRenderer.render(TREX_MODEL, TREX_TEXTURE, matrices, 0.8f / 16.0f);
            }
        } finally {
            matrices.pop();
        }
    }

    private static float lerpAngle(float current, float target, float amount) {
        float delta = MathHelper.wrapDegrees(target - current);
        return current + delta * amount;
    }
}
