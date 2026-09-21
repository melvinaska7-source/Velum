package velum.client.wexside;

import com.mojang.blaze3d.systems.RenderSystem;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import ru.wexside.misc.TextureHandle;
import ru.wexside.util.GuiRenderDriver;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

/**
 * Small 1.21.4-compatible GL backend for the Wexside GUI renderer.
 * It keeps the original 86-byte GUI vertex format and shader modes, but does
 * not depend on the 1.21.5+ RenderPipeline/GpuDevice API.
 */
public final class VelumWexsideRenderDriver implements GuiRenderDriver, AutoCloseable {
    private static final int STRIDE = 86;
    private final int[] textures = new int[16];
    private int framebufferWidth = 1;
    private int framebufferHeight = 1;
    private int scissorX, scissorY, scissorW, scissorH;
    private int stencilMode;
    private int vao, vbo, ibo, program;
    private int uProjection, uViewport, uStencilMode, uScissor, uTextures;
    private final FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private final FloatBuffer viewportBuffer = BufferUtils.createFloatBuffer(2);
    private final IntBuffer textureUnits = BufferUtils.createIntBuffer(16);
    private final Matrix4f projection = new Matrix4f();

    @Override public void setViewportSize(int width, int height) {
        framebufferWidth = Math.max(1, width);
        framebufferHeight = Math.max(1, height);
    }

    @Override public void beginFrame(int width, int height) {
        setViewportSize(width, height);
        ensureGl();
        Arrays.fill(textures, 0);
        stencilMode = 0;
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    @Override public void endFrame() { flushState(); }
    @Override public void endLayerFrame() { flushState(); }
    @Override public void beginLayer(TextureHandle layer) {}
    @Override public void close() { destroyGl(); }

    @Override public void setProjectionMatrix(Matrix4f matrix) {
        projection.set(matrix);
    }

    @Override public void setStencilMode(int mode) { stencilMode = mode; }
    @Override public void setScissor(int x, int y, int width, int height) {
        scissorX = x; scissorY = y; scissorW = width; scissorH = height;
        if (width <= 0 || height <= 0) {
            GL11.glDisable(GL11.GL_SCISSOR_TEST);
        } else {
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
            GL11.glScissor(x, framebufferHeight - y - height, width, height);
        }
    }
    @Override public void resetTextureBindings() { Arrays.fill(textures, 0); }

    @Override public int bindTexture(int textureId, int width, int height) {
        for (int i = 0; i < textures.length; i++) if (textures[i] == textureId) return i;
        for (int i = 0; i < textures.length; i++) {
            if (textures[i] == 0) { textures[i] = textureId; return i; }
        }
        return -1;
    }

    @Override public void drawIndexed(ByteBuffer vertexData, int vertexBytes, ByteBuffer indexData, int indexBytes) {
        if (vertexBytes <= 0 || indexBytes <= 0) return;
        ensureGl();
        ByteBuffer vertices = vertexData.duplicate();
        vertices.clear();
        vertices.limit(Math.min(vertexBytes, vertices.capacity()));
        ByteBuffer indices = indexData.duplicate();
        indices.clear();
        indices.limit(Math.min(indexBytes, indices.capacity()));

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STREAM_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ibo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indices, GL15.GL_STREAM_DRAW);

        GL20.glUseProgram(program);
        matrixBuffer.clear(); projection.get(matrixBuffer); matrixBuffer.flip();
        GL20.glUniformMatrix4fv(uProjection, false, matrixBuffer);
        viewportBuffer.clear().put((float) framebufferWidth).put((float) framebufferHeight).flip();
        GL20.glUniform2fv(uViewport, viewportBuffer);
        GL20.glUniform1i(uStencilMode, stencilMode);
        GL20.glUniform4i(uScissor, scissorX, scissorY, scissorW, scissorH);
        for (int i = 0; i < 16; i++) {
            GL20.glActiveTexture(GL20.GL_TEXTURE0 + i);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures[i]);
        }
        textureUnits.clear(); for (int i = 0; i < 16; i++) textureUnits.put(i); textureUnits.flip();
        GL20.glUniform1iv(uTextures, textureUnits);
        GL11.glDrawElements(GL11.GL_TRIANGLES, indexBytes / 4, GL11.GL_UNSIGNED_INT, 0L);
        GL20.glUseProgram(0);
        GL30.glBindVertexArray(0);
    }

    @Override public void beginStencil(int mode) { stencilMode = 1; }
    @Override public void endStencil() { stencilMode = 0; }
    @Override public void applyStencil(int mode) { stencilMode = mode; }
    @Override public TextureHandle acquireDedicatedLayer(int width, int height) { return null; }

    @Override public void resetFrameResources() {}
    @Override public void prepareBackdrop() {}
    @Override public void setBackdropBlurRadius(float radius) {}
    @Override public int getBackdropTextureSlot() { return 0; }

    private void ensureGl() {
        if (program != 0) return;
        program = link(VERTEX, FRAGMENT);
        uProjection = GL20.glGetUniformLocation(program, "uProjection");
        uViewport = GL20.glGetUniformLocation(program, "uViewportSize");
        uStencilMode = GL20.glGetUniformLocation(program, "uStencilMode");
        uScissor = GL20.glGetUniformLocation(program, "uScissorRect");
        uTextures = GL20.glGetUniformLocation(program, "textureSampler");
        vao = GL30.glGenVertexArrays(); vbo = GL15.glGenBuffers(); ibo = GL15.glGenBuffers();
        GL30.glBindVertexArray(vao);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        attribute(0, 2, GL11.GL_FLOAT, false, 0);
        attribute(1, 2, GL11.GL_FLOAT, false, 8);
        attribute(2, 2, GL11.GL_FLOAT, false, 16);
        attribute(3, 2, GL11.GL_FLOAT, false, 24);
        attribute(4, 4, GL11.GL_FLOAT, false, 32);
        attribute(5, 4, GL11.GL_UNSIGNED_BYTE, true, 48);
        attribute(6, 4, GL11.GL_UNSIGNED_BYTE, true, 52);
        attribute(7, 1, GL11.GL_FLOAT, false, 56);
        attribute(8, 1, GL11.GL_FLOAT, false, 60);
        attribute(9, 1, GL11.GL_FLOAT, false, 64);
        attribute(10, 1, GL11.GL_FLOAT, false, 68);
        attribute(11, 1, GL11.GL_FLOAT, false, 72);
        attribute(12, 2, GL11.GL_FLOAT, false, 76);
        GL20.glEnableVertexAttribArray(13); GL20.glVertexAttribIPointer(13, 1, GL11.GL_BYTE, STRIDE, 84L);
        GL20.glEnableVertexAttribArray(14); GL20.glVertexAttribIPointer(14, 1, GL11.GL_BYTE, STRIDE, 85L);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); GL30.glBindVertexArray(0);
    }

    private static void attribute(int index, int size, int type, boolean normalized, int offset) {
        GL20.glEnableVertexAttribArray(index);
        GL20.glVertexAttribPointer(index, size, type, normalized, STRIDE, offset);
    }

    private static int link(String vs, String fs) {
        int v = compile(GL20.GL_VERTEX_SHADER, vs), f = compile(GL20.GL_FRAGMENT_SHADER, fs);
        int p = GL20.glCreateProgram(); GL20.glAttachShader(p, v); GL20.glAttachShader(p, f);
        GL20.glLinkProgram(p); if (GL20.glGetProgrami(p, GL20.GL_LINK_STATUS) == 0) throw new IllegalStateException(GL20.glGetProgramInfoLog(p));
        GL20.glDeleteShader(v); GL20.glDeleteShader(f); return p;
    }
    private static int compile(int type, String source) {
        int s = GL20.glCreateShader(type); GL20.glShaderSource(s, source); GL20.glCompileShader(s);
        if (GL20.glGetShaderi(s, GL20.GL_COMPILE_STATUS) == 0) throw new IllegalStateException(GL20.glGetShaderInfoLog(s));
        return s;
    }
    private void flushState() { GL11.glDisable(GL11.GL_SCISSOR_TEST); GL30.glBindVertexArray(0); }
    private void destroyGl() { if (program != 0) GL20.glDeleteProgram(program); if (vbo != 0) GL15.glDeleteBuffers(vbo); if (ibo != 0) GL15.glDeleteBuffers(ibo); if (vao != 0) GL30.glDeleteVertexArrays(vao); program=vbo=ibo=vao=0; }

    private static final String VERTEX = """
        #version 150
        uniform mat4 uProjection;
        layout(location=0) in vec2 aPosition;
        layout(location=1) in vec2 aMeshPos;
        layout(location=2) in vec2 aMeshSize;
        layout(location=3) in vec2 aTexCoord;
        layout(location=4) in vec4 aRadius;
        layout(location=5) in vec4 aColor;
        layout(location=6) in vec4 aOutlineColor;
        layout(location=7) in float aThickness;
        layout(location=8) in float aSoftness;
        layout(location=9) in float aMsdfEdge;
        layout(location=10) in float aMsdfTextSize;
        layout(location=11) in float aMsdfRange;
        layout(location=12) in vec2 aMsdfAtlasSize;
        layout(location=13) in int aTexIndex;
        layout(location=14) in int aDrawMode;
        out vec2 vTexCoord; out vec2 meshPosition; out vec2 meshSize; out vec4 radius;
        out vec4 color; out vec4 outlineColor; out float thickness; out float softness;
        out float msdfEdgeAttr; out float msdfTextSizeAttr; out float msdfRangeAttr; out vec2 msdfAtlasSizeAttr;
        flat out int texIndex; flat out int drawMode;
        void main(){gl_Position=uProjection*vec4(aPosition,0.0,1.0);vTexCoord=aTexCoord;meshPosition=aMeshPos;meshSize=aMeshSize;radius=aRadius;color=aColor;outlineColor=aOutlineColor;thickness=aThickness;softness=aSoftness;msdfEdgeAttr=aMsdfEdge;msdfTextSizeAttr=aMsdfTextSize;msdfRangeAttr=aMsdfRange;msdfAtlasSizeAttr=aMsdfAtlasSize;texIndex=aTexIndex;drawMode=aDrawMode;}
        """;
    private static final String FRAGMENT = """
        #version 150
        uniform vec2 uViewportSize; uniform int uStencilMode; uniform ivec4 uScissorRect;
        uniform sampler2D textureSampler[16];
        in vec2 vTexCoord; in vec2 meshPosition; in vec2 meshSize; in vec4 radius; in vec4 color; in vec4 outlineColor;
        in float thickness; in float softness; in float msdfEdgeAttr; in float msdfTextSizeAttr; in float msdfRangeAttr; in vec2 msdfAtlasSizeAttr;
        flat in int texIndex; flat in int drawMode; out vec4 fragColor;
        float sdBox(vec2 p,vec2 b){vec2 d=abs(p)-b;return length(max(d,0.0))+min(max(d.x,d.y),0.0);}
        float sdRound(vec2 p,vec2 b,vec4 r){r.xy=(p.x>0.0)?r.xy:r.zw;r.x=(p.y>0.0)?r.x:r.y;vec2 q=abs(p)-b+r.x;return min(max(q.x,q.y),0.0)+length(max(q,0.0))-r.x;}
        float med(vec3 v){return max(min(v.r,v.g),min(max(v.r,v.g),v.b));}
        vec4 tex(vec2 uv){if(texIndex==0)return texture(textureSampler[0],uv);if(texIndex==1)return texture(textureSampler[1],uv);if(texIndex==2)return texture(textureSampler[2],uv);if(texIndex==3)return texture(textureSampler[3],uv);if(texIndex==4)return texture(textureSampler[4],uv);if(texIndex==5)return texture(textureSampler[5],uv);if(texIndex==6)return texture(textureSampler[6],uv);if(texIndex==7)return texture(textureSampler[7],uv);if(texIndex==8)return texture(textureSampler[8],uv);if(texIndex==9)return texture(textureSampler[9],uv);if(texIndex==10)return texture(textureSampler[10],uv);if(texIndex==11)return texture(textureSampler[11],uv);if(texIndex==12)return texture(textureSampler[12],uv);if(texIndex==13)return texture(textureSampler[13],uv);if(texIndex==14)return texture(textureSampler[14],uv);if(texIndex==15)return texture(textureSampler[15],uv);return vec4(0);}
        void main(){vec2 p=gl_FragCoord.xy; p.y=uViewportSize.y-p.y; vec2 c=p-(meshPosition+meshSize*0.5); float d; vec4 t;
          if(drawMode==1){t=tex(vTexCoord);fragColor=t*color;return;}
          if(drawMode==3){t=tex(vTexCoord);d=sdRound(c,meshSize*0.5,radius);float a=1.0-smoothstep(-1.0,softness+1.0,d);fragColor=t*color*a;return;}
          if(drawMode==4){t=tex(vTexCoord);float m=med(t.rgb);float w=fwidth(m);float a=smoothstep(0.5-w,0.5+w,m)*color.a;fragColor=vec4(color.rgb,a);return;}
          if(drawMode==6){d=sdRound(c,meshSize*0.5,radius);float a=1.0-smoothstep(-1.0,softness+1.0,d);vec4 g=mix(color,outlineColor,clamp((p.x-meshPosition.x)/max(meshSize.x,1.0),0.0,1.0));fragColor=g*a;return;}
          if(drawMode==7||drawMode==16){d=length(c)-min(meshSize.x,meshSize.y)*0.5;float a=1.0-smoothstep(-1.0,1.0,d);fragColor=color*a;return;}
          if(drawMode==9){fragColor=color;return;}
          d=sdRound(c,meshSize*0.5,radius);float a=1.0-smoothstep(-1.0,softness+1.0,d);fragColor=color*a; }
        """;
}
