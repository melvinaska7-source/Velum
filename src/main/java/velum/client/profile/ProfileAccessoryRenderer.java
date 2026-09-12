package velum.client.profile;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.MathHelper;
import pyvelum.events.render.Render3DEvent;

/** Lightweight phone-friendly cosmetic renderer for the Profile module. */
public final class ProfileAccessoryRenderer {
    public void render(Render3DEvent event, ProfileModule module) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) return;

        if (module.getCosmeticsSetting().I_method_1544ce2d("Halo")) {
            renderHalo(event, client.player.getLerpedPos(event.getTickDelta()));
        }
        if (module.getAccessoriesSetting().I_method_1544ce2d("Back Sword")) {
            renderBackSword(event, module);
        }
    }

    private void renderHalo(Render3DEvent event, Vec3d pos) {
        double cx = pos.x - event.getCamera().getPos().x;
        double cy = pos.y - event.getCamera().getPos().y + 2.25D;
        double cz = pos.z - event.getCamera().getPos().z;

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.setShader(net.minecraft.client.gl.ShaderProgramKeys.POSITION_COLOR);

        BufferBuilder buffer = RenderSystem.renderThreadTesselator().begin(
                VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        MatrixStack matrices = event.getMatrices();
        org.joml.Matrix4f matrix = matrices.peek().getPositionMatrix();
        int segments = 40;
        float radius = 0.38f;
        for (int i = 0; i <= segments; i++) {
            double a = Math.PI * 2.0D * i / segments;
            float x = (float) (cx + Math.cos(a) * radius);
            float y = (float) cy;
            float z = (float) (cz + Math.sin(a) * radius);
            buffer.vertex(matrix, x, y, z).color(120, 205, 255, 235);
        }
        BuiltBuffer built = buffer.endNullable();
        if (built != null) BufferRenderer.drawWithGlobalProgram(built);

        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private void renderBackSword(Render3DEvent event, ProfileModule module) {
        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d pos = client.player.getLerpedPos(event.getTickDelta());
        float yaw = client.player.getYaw(event.getTickDelta());
        float yawRad = yaw * MathHelper.RADIANS_PER_DEGREE;
        double back = 0.34D;
        double x = pos.x + Math.sin(yawRad) * back - event.getCamera().getPos().x;
        double y = pos.y + 1.0D - event.getCamera().getPos().y;
        double z = pos.z - Math.cos(yawRad) * back - event.getCamera().getPos().z;

        MatrixStack matrices = event.getMatrices();
        matrices.push();
        try {
            matrices.translate(x, y, z);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-yaw));
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-35.0f));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(8.0f));
            matrices.scale(0.72f, 0.72f, 0.72f);

            ItemStack sword = new ItemStack(Items.DIAMOND_SWORD);
            ItemRenderer renderer = client.getItemRenderer();
            var consumers = client.getBufferBuilders().getEntityVertexConsumers();
            renderer.renderItem(
                    client.player,
                    sword,
                    ModelTransformationMode.THIRD_PERSON_RIGHT_HAND,
                    false,
                    matrices,
                    consumers,
                    client.world,
                    15728880,
                    net.minecraft.client.render.OverlayTexture.DEFAULT_UV,
                    client.player.getId() + 901
            );
            consumers.draw();
        } finally {
            matrices.pop();
        }
    }
}
