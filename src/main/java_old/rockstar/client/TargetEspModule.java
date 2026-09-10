package rockstar.client;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.joml.Vector4f;
import pyrock.events.render.Render3DEvent;
import pyrock.utility.render.ColorRGBA;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
   name = "Target ESP",
   category = ModuleCategory.VISUALS,
   iI_method_476ab839 = true,
   III_method_a89e5834 = "modules.descriptions.target_esp"
)
public class TargetEspModule extends Module {
   private static final int I_field_49 = 60;
   private static final int i_field_49 = 30;
   private static final float I_field_46 = 1.0F;
   private static final float i_field_46 = 3.5F;
   private static final float II_field_46 = 1.0F;
   private ModeSetting I_field_bbe33e6c;
   private ModeSetting.Nested1_42765c60 I_field_500d0627;
   private ModeSetting.Nested1_42765c60 i_field_500d0627;
   private ModeSetting.Nested1_42765c60 II_field_500d0627;
   private ModeSetting.Nested1_42765c60 Ii_field_500d0627;
   private BooleanSetting I_field_ba20ca4c;
   private BooleanSetting i_field_ba20ca4c;
   private SliderSetting I_field_73178e8c;
   private BooleanSetting II_field_ba20ca4c;
   private ColorSetting I_field_bbd4b66c;
   private BooleanSetting Ii_field_ba20ca4c;
   private final IiiiIiIii_Class236 I_field_dc7facc = new IiiiIiIii_Class236(300L, 0.0F, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 i_field_dc7facc = new IiiiIiIii_Class236(70L, 0.0F, IiiiIiiII_Class237.Iii_field_dd60aac);
   private final IiiiIiIii_Class236 II_field_dc7facc = new IiiiIiIii_Class236(100L, 0.0F, IiiiIiiII_Class237.iIiI_field_dd60aac);
   private final IiiiIiIii_Class236 Ii_field_dc7facc = new IiiiIiIii_Class236(250L, 0.0F, IiiiIiiII_Class237.IiI_field_dd60aac);
   private float Ii_field_46 = 1.0F;
   private LivingEntity I_field_966247a3;
   private final IIIiII_Class5 I_field_83b89a01 = new IIIiII_Class5();
   private Vec2f I_field_5bba09cd = Vec2f.ZERO;
   private final IiiiIiIii_Class236 iI_field_dc7facc = new IiiiIiIii_Class236(250L, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 ii_field_dc7facc = new IiiiIiIii_Class236(150L, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 III_field_dc7facc = new IiiiIiIii_Class236(150L, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 IIi_field_dc7facc = new IiiiIiIii_Class236(350L, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 IiI_field_dc7facc = new IiiiIiIii_Class236(350L, IiiiIiiII_Class237.IiI_field_dd60aac);
   private final IiiiIiIii_Class236 Iii_field_dc7facc = new IiiiIiIii_Class236(250L, IiiiIiiII_Class237.I_field_dd60aac);
   private final IiIIIiII_Class69<Render3DEvent> I_field_3d936f41 = var1 -> {
      if (IiiiiiiII_Class253.i_method_1cc4aa23()) {
         Entity var2 = RockstarClient.getInstance().I_method_9a638462().I_method_9c10f815();
         LivingEntity var3 = this.I_field_ba20ca4c.i_method_9b12da03() && I_field_3a9bda27.targetedEntity instanceof LivingEntity var5
            ? var5
            : (var2 instanceof LivingEntity var4 ? var4 : null);
         this.Ii_field_dc7facc.I_method_df2f9087(IiiiIiiII_Class237.IiI_field_dd60aac);
         this.Ii_field_dc7facc.I_method_edd6ec25(350L);
         this.I_field_dc7facc.I_method_df2f9087(IiiiIiiII_Class237.III_field_dd60aac);
         this.I_field_dc7facc.I_method_edd72835(var3 != null);
         this.i_field_dc7facc.I_method_edd6dd11(this.i_field_dc7facc.I_method_6ac4da6f() + 10.0F + 50.0F);
         if (this.i_field_dc7facc.I_method_6ac4da6f() > Float.MAX_VALUE || Float.isNaN(this.i_field_dc7facc.I_method_6ac4da6f())) {
            this.i_field_dc7facc.I_method_edd6dd21(0.0F);
         }

         if (var3 != null) {
            this.I_field_966247a3 = var3;
         }

         if (this.I_field_966247a3 != null && this.I_field_dc7facc.I_method_6ac4da6f() != 0.0F) {
            this.II_field_dc7facc.I_method_edd72835(this.I_field_966247a3.hurtTime > 0);
            this.i_method_ca75e9ff(var1);
            MatrixStack var7 = var1.getMatrices();
            var7.push();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
            boolean var8 = I_field_3a9bda27.world
                     .raycast(
                        new RaycastContext(
                           I_field_3a9bda27.gameRenderer.getCamera().getPos(),
                           this.I_field_966247a3.getEyePos(),
                           ShapeType.COLLIDER,
                           FluidHandling.NONE,
                           I_field_3a9bda27.player
                        )
                     )
                     .getType()
                  != Type.MISS
               || this.I_field_966247a3.isTouchingWater();
            this.Ii_field_dc7facc.I_method_edd72835(var8);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            this.Ii_field_46 = 1.0F;
            var7.push();
            this.I_method_469aa82c(var7, this.I_field_966247a3);
            var7.pop();
            float var9 = this.Ii_field_dc7facc.I_method_6ac4da6f();
            if (var9 > 0.01F) {
               RenderSystem.depthFunc(516);
               this.Ii_field_46 = var9;
               var7.push();
               this.I_method_469aa82c(var7, this.I_field_966247a3);
               var7.pop();
               RenderSystem.depthFunc(515);
            }

            this.Ii_field_46 = 1.0F;
            RenderSystem.depthMask(true);
            RenderSystem.setShaderTexture(0, 0);
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.disableDepthTest();
            var7.pop();
            this.I_method_e6b7ce1f(var1);
         }
      }
   };

   @Compile(
      obfuscation = 4
   )
   private void IiI_method_a798725f() {
      this.I_field_bbe33e6c = new ModeSetting(this, "modules.settings.target_esp.mode");
      this.I_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.target_esp.mode.souls");
      this.i_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.target_esp.mode.crystals").select();
      this.II_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.target_esp.mode.circle");
      this.Ii_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.target_esp.mode.jello");
      this.I_field_ba20ca4c = new BooleanSetting(this, "modules.settings.target_esp.ray_trace");
      this.Ii_field_ba20ca4c = new BooleanSetting(this, "modules.settings.target_esp.lighting");
      this.i_field_ba20ca4c = new BooleanSetting(this, "modules.settings.target_esp.distortion", () -> !this.i_field_500d0627.isSelected())
         .I_method_decd82b5();
      this.I_field_73178e8c = new SliderSetting(
            this, "modules.settings.target_esp.distortion_strength", () -> !this.i_field_500d0627.isSelected() || !this.i_field_ba20ca4c.i_method_9b12da03()
         )
         .I_method_c8c9a7d7(0.05F)
         .i_method_65e2aff7(0.15F)
         .II_method_b0f56334(0.01F)
         .Ii_method_4e0e6b54(0.05F);
      this.II_field_ba20ca4c = new BooleanSetting(this, "theme.sync").I_method_decd82b5();
      this.I_field_bbd4b66c = new ColorSetting(this, "modules.settings.target_esp.color", this.II_field_ba20ca4c::i_method_9b12da03)
         .I_method_a62f5dd6(IiiiiIIIi_Class242.i_method_5dfec6e());
   }

   public TargetEspModule() {
      this.IiI_method_a798725f();
      this.I_field_83b89a01.I_method_e3461a4c();
   }

   private void I_method_e6b7ce1f(Render3DEvent var1) {
      if (this.i_field_ba20ca4c.i_method_9b12da03()) {
         if (this.i_field_500d0627.isSelected()) {
            float var2 = this.I_field_dc7facc.I_method_6ac4da6f();
            if (!(var2 <= 0.01F)) {
               float var3 = this.I_field_73178e8c.Ii_method_a20abcd2() * var2;
               if (!(var3 <= 0.001F)) {
                  Vec3d var4 = var1.getCamera().getPos();
                  Vec3d var5 = this.I_method_acb0d958(this.I_field_966247a3);
                  float var6 = this.I_field_966247a3.getWidth() * 1.5F;
                  float var7 = this.I_field_966247a3.getHeight();
                  float var8 = this.i_field_dc7facc.I_method_6ac4da6f();
                  Matrix4f var9 = var1.getProjectionMatrix();
                  Matrix4f var10 = new Matrix4f(var9).mul(var1.getPositionMatrix());
                  float var11 = var9.m11() / var9.m00();
                  ArrayList var12 = new ArrayList();

                  for (int var13 = 0; var13 < 360; var13 += 20) {
                     float var14 = 1.2F - 0.5F * var2;
                     float var15 = (float)(iIIiiiiiI_Class319.I_method_a586e9cd((float)Math.toRadians(var13 + var8 * 0.3F)) * var6 * var14);
                     float var16 = (float)(iIIiiiiiI_Class319.i_method_a749d9ed((float)Math.toRadians(var13 + var8 * 0.3F)) * var6 * var14);
                     float var17 = 0.1F + var7 * (float)Math.abs(iIIiiiiiI_Class319.I_method_a586e9cd(var13));
                     this.I_method_c70084d0(var12, var10, var9, var5, var4, var15, var17, var16, 0.45F);
                  }

                  if (!var12.isEmpty()) {
                     this.I_field_83b89a01.I_method_dc54243d(var11, var3, var12);
                  }
               }
            }
         }
      }
   }

   private void I_method_c70084d0(
      List<IIIiII_Class5.Nested1_b503c753> var1, Matrix4f var2, Matrix4f var3, Vec3d var4, Vec3d var5, float var6, float var7, float var8, float var9
   ) {
      Vector4f var10 = var2.transform(new Vector4f((float)(var4.x + var6 - var5.x), (float)(var4.y + var7 - var5.y), (float)(var4.z + var8 - var5.z), 1.0F));
      if (!(var10.w <= 0.05F)) {
         float var11 = var10.x / var10.w * 0.5F + 0.5F;
         float var12 = var10.y / var10.w * 0.5F + 0.5F;
         float var13 = var10.z / var10.w * 0.5F + 0.5F;
         float var14 = Math.min(var9 * var3.m11() / var10.w * 0.5F, 0.35F);
         if (!(var11 < -var14 * 2.0F) && !(var11 > 1.0F + var14 * 2.0F) && !(var12 < -var14 * 2.0F) && !(var12 > 1.0F + var14 * 2.0F)) {
            var1.add(new IIIiII_Class5.Nested1_b503c753(var11, var12, var13, var14));
         }
      }
   }

   private void I_method_469aa82c(MatrixStack var1, LivingEntity var2) {
      if (this.II_field_500d0627.isSelected()) {
         this.II_method_f085da49(var1, var2);
      } else if (this.i_field_500d0627.isSelected()) {
         this.Ii_method_e0011269(var1, var2);
      } else if (this.Ii_field_500d0627.isSelected()) {
         this.i_method_3615e04c(var1, var2);
      } else if (this.I_field_500d0627.isSelected()) {
         this.iI_method_f071a629(var1, var2);
      }
   }

   private void i_method_3615e04c(MatrixStack var1, LivingEntity var2) {
      Camera var3 = I_field_3a9bda27.gameRenderer.getCamera();
      ColorRGBA var4 = this.II_field_ba20ca4c.i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e() : this.I_field_bbd4b66c.I_method_bde5f08e();
      Identifier var5 = RockstarClient.id("textures/bloom.png");
      float var6 = this.I_field_966247a3.getWidth() * 1.45F;
      var1.push();
      iIiiiIIiI_Class371.I_method_7d461059(var1, this.I_method_acb0d958(this.I_field_966247a3));
      RenderSystem.setShaderTexture(0, var5);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder var7 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float var8 = 0.0F;
      float var9 = this.I_field_dc7facc.I_method_6ac4da6f();

      for (int var10 = 0; var10 < 360; var10 += 2) {
         float var11 = Math.max(0.5F, 0.7F - 0.2F * var8 + 0.2F - 0.2F * var9);
         double var12 = (var10 + this.i_field_dc7facc.I_method_6ac4da6f()) * (float) (Math.PI / 180.0);
         float var14 = (float)(iIIiiiiiI_Class319.I_method_a586e9cd(var12) * var6 * var11);
         float var15 = (float)(iIIiiiiiI_Class319.i_method_a749d9ed(var12) * var6 * var11);
         float var16 = var2.getHeight() / 1.75F
            + (float)(var2.getHeight() / 2.0F * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(this.i_field_dc7facc.I_method_6ac4da6f() / 1.5F + 30.0F)));
         float var17 = 0.2F;
         float var18 = 0.8F;
         float var19 = 0.2F;

         for (int var20 = 0; var20 < 15; var20++) {
            var16 = var2.getHeight() / 1.75F
               + (float)(
                  var2.getHeight() / 2.0F
                     * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(this.i_field_dc7facc.I_method_6ac4da6f() / 1.5F + var20 * 2.0F))
               );
            var1.push();
            var1.translate(var14, var16, var15);
            var1.multiply(var3.getRotation());
            iIiiIIiii_Class360.I_method_a9f124df(
               var1,
               var7,
               -var19 / 2.0F,
               -var19 / 2.0F,
               -var17 / 2.0F,
               var19,
               var19,
               var4.withAlpha(var4.getAlpha() * var9 * (var20 / 15.0F) * 0.05F * this.Ii_field_46)
            );
            var1.pop();
         }
      }

      iIiiiIIiI_Class371.I_method_c59155f0(var7);
      var1.pop();
      var1.push();
      iIiiiIIiI_Class371.I_method_7d461059(var1, this.I_method_acb0d958(this.I_field_966247a3));
      RenderSystem.setShaderTexture(0, RockstarClient.id("textures/glowing.png"));
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      var7 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (int var22 = 0; var22 < 360; var22 += 2) {
         float var23 = Math.max(0.5F, 0.7F - 0.2F * var8 + 0.2F - 0.2F * var9);
         double var24 = (var22 + this.i_field_dc7facc.I_method_6ac4da6f()) * (float) (Math.PI / 180.0);
         float var25 = (float)(iIIiiiiiI_Class319.I_method_a586e9cd(var24) * var6 * var23);
         float var26 = (float)(iIIiiiiiI_Class319.i_method_a749d9ed(var24) * var6 * var23);
         float var28 = var2.getHeight() / 1.75F
            + (float)(var2.getHeight() / 2.0F * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(this.i_field_dc7facc.I_method_6ac4da6f() / 1.5F + 30.0F)));
         float var29 = 0.2F;
         float var30 = 0.2F;
         float var31 = 0.2F;
         var1.push();
         var1.translate(var25, var28, var26);
         var1.multiply(var3.getRotation());
         iIiiIIiii_Class360.I_method_a9f124df(
            var1, var7, -var30 / 2.0F, -var30 / 2.0F, -var29 / 2.0F, var30, var30, var4.withAlpha(var4.getAlpha() * var9 * 0.2F * this.Ii_field_46)
         );
         var1.pop();
      }

      iIiiiIIiI_Class371.I_method_c59155f0(var7);
      var1.pop();
   }

   private void II_method_f085da49(MatrixStack var1, LivingEntity var2) {
      Camera var3 = I_field_3a9bda27.gameRenderer.getCamera();
      ColorRGBA var4 = this.II_field_ba20ca4c.i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e() : this.I_field_bbd4b66c.I_method_bde5f08e();
      Identifier var5 = RockstarClient.id("textures/glowing.png");
      float var6 = this.I_field_966247a3.getWidth() * 1.5F;
      iIiiiIIiI_Class371.I_method_7d461059(var1, this.I_method_acb0d958(this.I_field_966247a3));
      RenderSystem.setShaderTexture(0, var5);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder var7 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float var8 = this.II_field_dc7facc.I_method_6ac4da6f();
      float var9 = this.I_field_dc7facc.I_method_6ac4da6f();

      for (int var10 = 0; var10 < 360; var10 += 2) {
         if (var10 / 45 % 2 != 0) {
            float var11 = Math.max(0.5F, 0.7F - 0.2F * var8 + 0.2F - 0.2F * var9);
            double var12 = (var10 + this.i_field_dc7facc.I_method_6ac4da6f()) * (float) (Math.PI / 180.0);
            float var14 = (float)(iIIiiiiiI_Class319.I_method_a586e9cd(var12) * var6 * var11);
            float var15 = (float)(iIIiiiiiI_Class319.i_method_a749d9ed(var12) * var6 * var11);
            float var16 = var2.getHeight() / 1.75F
               + (float)(var2.getHeight() / 2.0F * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(this.i_field_dc7facc.I_method_6ac4da6f() / 2.0F)));
            float var17 = 0.3F;
            float var18 = 0.8F;
            var1.push();
            var1.translate(var14, var16, var15);
            var1.multiply(var3.getRotation());
            iIiiIIiii_Class360.I_method_a9f124df(
               var1, var7, -var18 / 2.0F, -var18 / 2.0F, -var17 / 2.0F, var18, var18, var4.withAlpha(var4.getAlpha() * var9 * 0.03F * this.Ii_field_46)
            );
            iIiiIIiii_Class360.I_method_a9f124df(
               var1, var7, -var17 / 2.0F, -var17 / 2.0F, -var17 / 2.0F, var17, var17, var4.withAlpha(var4.getAlpha() * var9 * 0.7F * this.Ii_field_46)
            );
            var1.pop();
         }
      }

      iIiiiIIiI_Class371.I_method_c59155f0(var7);
   }

   private void Ii_method_e0011269(MatrixStack var1, LivingEntity var2) {
      Camera var3 = I_field_3a9bda27.gameRenderer.getCamera();
      Vec3d var4 = var3.getPos();
      ColorRGBA var5 = this.II_field_ba20ca4c.i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e() : this.I_field_bbd4b66c.I_method_bde5f08e();
      float var6 = this.I_field_966247a3.getWidth() * 1.5F;
      iIiiiIIiI_Class371.I_method_7d461059(var1, this.I_method_acb0d958(this.I_field_966247a3));
      BufferBuilder var7 = iIiiIIIii_Class356.I_method_c596047e();

      for (int var8 = 0; var8 < 360; var8 += 20) {
         float var9 = 1.2F - 0.5F * this.I_field_dc7facc.I_method_6ac4da6f();
         float var10 = (float)(
            iIIiiiiiI_Class319.I_method_a586e9cd((float)Math.toRadians(var8 + this.i_field_dc7facc.I_method_6ac4da6f() * 0.3F)) * var6 * var9
         );
         float var11 = (float)(
            iIIiiiiiI_Class319.i_method_a749d9ed((float)Math.toRadians(var8 + this.i_field_dc7facc.I_method_6ac4da6f() * 0.3F)) * var6 * var9
         );
         float var12 = 0.1F;
         var1.push();
         var1.translate(var10, 0.1F + var2.getHeight() * Math.abs(iIIiiiiiI_Class319.I_method_a586e9cd(var8)), var11);
         Vec3d var13 = this.I_method_acb0d958(this.I_field_966247a3).add(var10, 1.0, var11);
         Vec3d var14 = var2.getPos().add(0.0, var2.getHeight() / 2.0, 0.0);
         Vector3f var15 = new Vector3f((float)(var14.x - var13.x), (float)(var14.y - var13.y), (float)(var14.z - var13.z)).normalize();
         Vector3f var16 = new Vector3f(0.0F, 1.0F, 0.0F);
         Quaternionf var17 = new Quaternionf().rotationTo(var16, var15);
         var1.multiply(var17);
         iIiiIIIii_Class356.I_method_8d941781(
            var1, var7, 0.0F, 0.0F, 0.0F, var12, var5.withAlpha(255.0F * this.I_field_dc7facc.I_method_6ac4da6f() * this.Ii_field_46)
         );
         var1.pop();
      }

      BufferRenderer.drawWithGlobalProgram(var7.end());
      Identifier var18 = RockstarClient.id("textures/bloom.png");
      RenderSystem.setShaderTexture(0, var18);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder var19 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      float var20 = 1.0F;

      for (int var21 = 0; var21 < 360; var21 += 20) {
         float var22 = 1.2F - 0.5F * this.I_field_dc7facc.I_method_6ac4da6f();
         float var23 = (float)(
            iIIiiiiiI_Class319.I_method_a586e9cd((float)Math.toRadians(var21 + this.i_field_dc7facc.I_method_6ac4da6f() * 0.3F)) * var6 * var22
         );
         float var24 = (float)(
            iIIiiiiiI_Class319.i_method_a749d9ed((float)Math.toRadians(var21 + this.i_field_dc7facc.I_method_6ac4da6f() * 0.3F)) * var6 * var22
         );
         float var25 = 0.1F;
         var1.push();
         var1.translate(var23, 0.1F + var2.getHeight() * Math.abs(iIIiiiiiI_Class319.I_method_a586e9cd(var21)), var24);
         var1.multiply(var3.getRotation());
         iIiiIIiii_Class360.I_method_a9f124df(
            var1,
            var19,
            -var20 / 2.0F,
            -var20 / 2.0F,
            0.0,
            var20,
            var20,
            var5.withAlpha(255.0F * this.I_field_dc7facc.I_method_6ac4da6f() * 0.2F * this.Ii_field_46)
         );
         var1.pop();
      }

      iIiiiIIiI_Class371.I_method_c59155f0(var19);
   }

   private void iI_method_f071a629(MatrixStack var1, LivingEntity var2) {
      Camera var3 = I_field_3a9bda27.gameRenderer.getCamera();
      ColorRGBA var4 = this.II_field_ba20ca4c.i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e() : this.I_field_bbd4b66c.I_method_bde5f08e();
      Identifier var5 = RockstarClient.id("textures/glowing.png");
      float var6 = this.I_field_966247a3.getWidth() * 1.5F;
      RenderSystem.setShaderTexture(0, var5);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder var7 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
      iIiiiIIiI_Class371.I_method_7d461059(var1, this.I_method_acb0d958(this.I_field_966247a3));
      byte var8 = 2;
      byte var9 = 0;
      byte var10 = 0;
      int var11 = 0;

      for (int var12 = 0; var12 < 360; var12 += var8) {
         float var13 = 0.23F + 0.005F * var9;
         float var14 = 0.7F + 0.005F * var9;
         if (var10 > 0) {
            var10 -= var8;
         } else {
            var9 += var8;
            if (var9 > 50) {
               var10 = 100;
               var9 = 0;
               var11++;
            } else {
               float var15 = Math.max(0.5F, 1.2F - 0.5F * this.I_field_dc7facc.I_method_6ac4da6f());
               float var16 = (float)(
                  iIIiiiiiI_Class319.I_method_a586e9cd((float)Math.toRadians(var12 + this.i_field_dc7facc.I_method_6ac4da6f() * 1.0F)) * var6 * var15
               );
               float var17 = (float)(
                  iIIiiiiiI_Class319.i_method_a749d9ed((float)Math.toRadians(var12 + this.i_field_dc7facc.I_method_6ac4da6f() * 1.0F)) * var6 * var15
               );
               var1.push();
               var1.translate(
                  var16,
                  this.I_field_966247a3.getHeight() / 1.5F
                     + this.I_field_966247a3.getHeight() / 3.0F
                        * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(var12 / 2.0F + this.i_field_dc7facc.I_method_6ac4da6f() / 5.0F)),
                  var17
               );
               var1.multiply(var3.getRotation());
               iIiiIIiii_Class360.I_method_a9f124df(
                  var1,
                  var7,
                  -var14 / 2.0F,
                  -var14 / 2.0F,
                  -var13 / 2.0F,
                  var14,
                  var14,
                  var4.withAlpha(var4.getAlpha() * this.I_field_dc7facc.I_method_6ac4da6f() * 0.02F * this.Ii_field_46)
               );
               iIiiIIiii_Class360.I_method_a9f124df(
                  var1,
                  var7,
                  -var13 / 2.0F,
                  -var13 / 2.0F,
                  -var13 / 2.0F,
                  var13,
                  var13,
                  var4.withAlpha(var4.getAlpha() * this.I_field_dc7facc.I_method_6ac4da6f() * 0.5F * this.Ii_field_46)
               );
               var1.pop();
            }
         }
      }

      iIiiiIIiI_Class371.I_method_c59155f0(var7);
   }

   private void i_method_ca75e9ff(Render3DEvent var1) {
      if (this.Ii_field_ba20ca4c.i_method_9b12da03()) {
         if (iIiiIIiii_Class360.I_field_65f6a54c != null) {
            float var2 = 1.0F * this.I_field_dc7facc.I_method_6ac4da6f();
            if (!(var2 <= 0.001F)) {
               List var3 = this.I_method_74a20029(this.I_field_966247a3);
               if (!var3.isEmpty()) {
                  ColorRGBA var4 = this.II_field_ba20ca4c.i_method_9b12da03()
                     ? IiiiiIIIi_Class242.i_method_5dfec6e()
                     : this.I_field_bbd4b66c.I_method_bde5f08e();
                  float var5 = var4.getRed() / 255.0F;
                  float var6 = var4.getGreen() / 255.0F;
                  float var7 = var4.getBlue() / 255.0F;
                  float var8 = var2 / var3.size();
                  Vec3d var9 = var1.getCamera().getPos();
                  Vec3d var10 = this.I_method_acb0d958(this.I_field_966247a3);
                  ArrayList var11 = new ArrayList(var3.size());

                  for (Vec3d var13 : (Iterable<Vec3d>)(Iterable<?>)var3) {
                     var11.add(
                        new iiiii_Class32.Nested1_88680fa0(
                           (float)(var10.x + var13.x - var9.x),
                           (float)(var10.y + var13.y - var9.y),
                           (float)(var10.z + var13.z - var9.z),
                           3.5F,
                           var5,
                           var6,
                           var7,
                           var8
                        )
                     );
                  }

                  Matrix4f var14 = new Matrix4f(var1.getProjectionMatrix()).mul(var1.getPositionMatrix()).invert();
                  iIiiIIiii_Class360.I_field_65f6a54c.I_method_6b5b11c2(var14, 1.0F, var11);
               }
            }
         }
      }
   }

   private List<Vec3d> I_method_74a20029(LivingEntity var1) {
      float var2 = this.I_field_dc7facc.I_method_6ac4da6f();
      float var3 = this.i_field_dc7facc.I_method_6ac4da6f();
      double var4 = Math.max(0.1, (double)var1.getHeight());
      ArrayList var6 = new ArrayList(6);
      if (this.i_field_500d0627.isSelected()) {
         double var21 = var1.getWidth() * 1.5 * (1.2 - 0.5 * var2);

         for (int var22 = 0; var22 < 360; var22 += 60) {
            double var24 = Math.toRadians(var22 + var3 * 0.3);
            var6.add(
               new Vec3d(
                  iIIiiiiiI_Class319.I_method_a586e9cd(var24) * var21,
                  0.1 + var4 * Math.abs(iIIiiiiiI_Class319.I_method_a586e9cd(var22)),
                  iIIiiiiiI_Class319.i_method_a749d9ed(var24) * var21
               )
            );
         }

         return var6;
      } else if (this.I_field_500d0627.isSelected()) {
         double var20 = var1.getWidth() * 1.5 * Math.max(0.5, 1.2 - 0.5 * var2);

         for (int var9 = 0; var9 < 360; var9 += 60) {
            double var23 = Math.toRadians(var9 + var3);
            var6.add(
               new Vec3d(
                  iIIiiiiiI_Class319.I_method_a586e9cd(var23) * var20,
                  var4 / 1.5 + var4 / 3.0 * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(var9 / 2.0 + var3 / 5.0)),
                  iIIiiiiiI_Class319.i_method_a749d9ed(var23) * var20
               )
            );
         }

         return var6;
      } else {
         boolean var7 = this.Ii_field_500d0627.isSelected();
         double var8 = var7 ? 0.0 : this.II_field_dc7facc.I_method_6ac4da6f();
         double var10 = var1.getWidth() * (var7 ? 1.45 : 1.5) * Math.max(0.5, 0.7 - 0.2 * var8 + 0.2 - 0.2 * var2);
         double var12 = var7 ? var3 / 1.5 + 30.0 : var3 / 2.0;
         double var14 = var4 / 1.75 + var4 / 2.0 * iIIiiiiiI_Class319.I_method_a586e9cd(Math.toRadians(var12));
         int var16 = var7 ? 30 : 15;

         for (int var17 = 0; var17 < 360; var17 += var16) {
            if (var7 || var17 / 45 % 2 != 0) {
               double var18 = Math.toRadians(var17 + var3);
               var6.add(new Vec3d(iIIiiiiiI_Class319.I_method_a586e9cd(var18) * var10, var14, iIIiiiiiI_Class319.i_method_a749d9ed(var18) * var10));
            }
         }

         return var6;
      }
   }

   private Vec3d I_method_acb0d958(LivingEntity var1) {
      float var2 = MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false);
      return new Vec3d(
         MathHelper.lerp(var2, var1.prevX, var1.getX()), MathHelper.lerp(var2, var1.prevY, var1.getY()), MathHelper.lerp(var2, var1.prevZ, var1.getZ())
      );
   }

   @Override
   public void II_method_6642fd22() {
      super.II_method_6642fd22();
   }
}
