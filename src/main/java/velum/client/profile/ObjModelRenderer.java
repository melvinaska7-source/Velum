package velum.client.profile;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.resource.Resource;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.ShaderProgramKeys;
import org.joml.Matrix4f;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Small, dependency-free OBJ renderer for Profile cosmetics.
 *
 * The model is parsed once and flattened into a compact triangle list. No OBJ
 * parsing, file IO or temporary model objects are performed during rendering.
 */
public final class ObjModelRenderer {
    private static final Map<Identifier, ObjModel> CACHE = new HashMap<>();

    private ObjModelRenderer() {
    }

    public static void render(Identifier modelId, Identifier textureId, MatrixStack matrices, float scale) {
        ObjModel model = get(modelId);
        if (model == null || model.vertexCount == 0) {
            return;
        }

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, textureId);

        Matrix4f matrix = matrices.peek().getPositionMatrix();
        var buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_TEXTURE_COLOR);

        for (int i = 0; i < model.vertexCount; i++) {
            int p = i * 3;
            int t = i * 2;
            buffer.vertex(matrix,
                    model.positions[p] * scale,
                    model.positions[p + 1] * scale,
                    model.positions[p + 2] * scale)
                    .texture(model.uvs[t], model.uvs[t + 1])
                    .color(-1);
        }

        BuiltBuffer built = buffer.endNullable();
        if (built != null) {
            BufferRenderer.drawWithGlobalProgram(built);
        }

        RenderSystem.setShaderTexture(0, 0);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
    }

    private static ObjModel get(Identifier id) {
        ObjModel cached = CACHE.get(id);
        if (cached != null) {
            return cached;
        }

        try {
            MinecraftClient client = MinecraftClient.getInstance();
            Resource resource = client.getResourceManager().getResource(id).orElse(null);
            if (resource == null) {
                return null;
            }
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
                ObjModel parsed = ObjModel.parse(reader);
                CACHE.put(id, parsed);
                return parsed;
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    private static final class ObjModel {
        final float[] positions;
        final float[] uvs;
        final int vertexCount;

        private ObjModel(float[] positions, float[] uvs) {
            this.positions = positions;
            this.uvs = uvs;
            this.vertexCount = positions.length / 3;
        }

        static ObjModel parse(BufferedReader reader) throws Exception {
            List<float[]> vertices = new ArrayList<>();
            List<float[]> texcoords = new ArrayList<>();
            List<int[]> faces = new ArrayList<>();

            String line;
            float minX = Float.POSITIVE_INFINITY;
            float minY = Float.POSITIVE_INFINITY;
            float minZ = Float.POSITIVE_INFINITY;
            float maxX = Float.NEGATIVE_INFINITY;
            float maxY = Float.NEGATIVE_INFINITY;
            float maxZ = Float.NEGATIVE_INFINITY;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                if (line.startsWith("v ")) {
                    String[] p = line.split("\\s+");
                    if (p.length >= 4) {
                        float x = Float.parseFloat(p[1]);
                        float y = Float.parseFloat(p[2]);
                        float z = Float.parseFloat(p[3]);
                        vertices.add(new float[]{x, y, z});
                        minX = Math.min(minX, x);
                        minY = Math.min(minY, y);
                        minZ = Math.min(minZ, z);
                        maxX = Math.max(maxX, x);
                        maxY = Math.max(maxY, y);
                        maxZ = Math.max(maxZ, z);
                    }
                } else if (line.startsWith("vt ")) {
                    String[] p = line.split("\\s+");
                    if (p.length >= 3) {
                        texcoords.add(new float[]{Float.parseFloat(p[1]), 1.0f - Float.parseFloat(p[2])});
                    }
                } else if (line.startsWith("f ")) {
                    String[] p = line.substring(2).trim().split("\\s+");
                    if (p.length >= 3) {
                        // OBJ supports polygons. Fan triangulation keeps the parser small.
                        for (int i = 1; i < p.length - 1; i++) {
                            faces.add(parseIndex(p[0]));
                            faces.add(parseIndex(p[i]));
                            faces.add(parseIndex(p[i + 1]));
                        }
                    }
                }
            }

            if (vertices.isEmpty() || faces.isEmpty()) {
                return new ObjModel(new float[0], new float[0]);
            }

            float centerX = (minX + maxX) * 0.5f;
            float centerZ = (minZ + maxZ) * 0.5f;
            float[] outPos = new float[faces.size() * 3];
            float[] outUv = new float[faces.size() * 2];

            int pi = 0;
            int ti = 0;
            for (int[] face : faces) {
                int vertexIndex = face[0];
                int texIndex = face[1];
                if (vertexIndex < 0) vertexIndex = vertices.size() + vertexIndex + 1;
                if (texIndex < 0) texIndex = texcoords.size() + texIndex + 1;

                if (vertexIndex <= 0 || vertexIndex > vertices.size()) {
                    outPos[pi++] = outPos[pi++] = outPos[pi++] = 0.0f;
                } else {
                    float[] v = vertices.get(vertexIndex - 1);
                    outPos[pi++] = v[0] - centerX;
                    outPos[pi++] = v[1] - minY;
                    outPos[pi++] = v[2] - centerZ;
                }

                if (texIndex > 0 && texIndex <= texcoords.size()) {
                    float[] uv = texcoords.get(texIndex - 1);
                    outUv[ti++] = uv[0];
                    outUv[ti++] = uv[1];
                } else {
                    outUv[ti++] = 0.0f;
                    outUv[ti++] = 0.0f;
                }
            }
            return new ObjModel(outPos, outUv);
        }

        private static int[] parseIndex(String token) {
            String[] parts = token.split("/");
            int vertex = Integer.parseInt(parts[0]);
            int tex = parts.length > 1 && !parts[1].isEmpty() ? Integer.parseInt(parts[1]) : 0;
            return new int[]{vertex, tex};
        }
    }
}
