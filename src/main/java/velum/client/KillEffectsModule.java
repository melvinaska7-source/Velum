package velum.client;

import com.mojang.blaze3d.platform.GlStateManager.DstFactor;
import com.mojang.blaze3d.platform.GlStateManager.SrcFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BuiltBuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat.DrawMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import pyvelum.events.game.EntityDeathEvent;
import pyvelum.events.game.WorldChangeEvent;
import pyvelum.events.render.Render3DEvent;
import pyvelum.utility.render.ColorRGBA;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
   name = "Kill Effects",
   category = ModuleCategory.VISUALS,
   iI_method_476ab839 = true,
   III_method_a89e5834 = "modules.descriptions.kill_effects"
)
public class KillEffectsModule extends Module {
   private final List<KillEffectsModule.Nested1_a9895880> I_field_7865b31 = new CopyOnWriteArrayList<>();
   private final List<KillEffectsModule.Nested1_a9895860> i_field_7865b31 = new CopyOnWriteArrayList<>();
   static final Random I_field_6ece06ac = new Random();
   private ModeSetting I_field_bbe33e6c;
   private ModeSetting.Nested1_42765c60 I_field_500d0627;
   private ModeSetting.Nested1_42765c60 i_field_500d0627;
   private ModeSetting i_field_bbe33e6c;
   private ModeSetting.Nested1_42765c60 II_field_500d0627;
   private ModeSetting.Nested1_42765c60 Ii_field_500d0627;
   private BooleanSetting I_field_ba20ca4c;
   private ColorSetting I_field_bbd4b66c;
   private final IiIIIiII_Class69<EntityDeathEvent> I_field_3d936f41 = var1 -> {
      if (!var1.getEntity().isRemoved() && !(var1.getEntity() instanceof ArmorStandEntity)) {
         ColorRGBA var2 = this.I_field_ba20ca4c.i_method_9b12da03() ? IiiiiIIIi_Class242.i_method_5dfec6e() : this.I_field_bbd4b66c.I_method_bde5f08e();
         if (this.I_field_bbe33e6c.I_method_ce989e26(this.I_field_500d0627)) {
            this.I_field_7865b31.add(new KillEffectsModule.Nested1_a9895880(var1.getEntity().getPos(), var2));
         } else if (this.I_field_bbe33e6c.I_method_ce989e26(this.i_field_500d0627)) {
            this.I_method_9937b821(var1.getEntity(), var2);
         }
      }
   };
   private final IiIIIiII_Class69<Render3DEvent> i_field_3d936f41 = var1 -> {
      MatrixStack var2 = var1.getMatrices();
      Camera var3 = I_field_3a9bda27.gameRenderer.getCamera();
      var2.push();
      RenderSystem.enableBlend();
      RenderSystem.blendFunc(SrcFactor.SRC_ALPHA, DstFactor.ONE);
      RenderSystem.enableDepthTest();
      RenderSystem.disableCull();
      RenderSystem.depthMask(false);
      Identifier var4 = VelumClient.id("textures/bloom.png");
      RenderSystem.setShaderTexture(0, var4);
      RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
      BufferBuilder var5 = RenderSystem.renderThreadTesselator().begin(DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

      for (KillEffectsModule.Nested1_a9895880 var7 : this.I_field_7865b31) {
         var7.I_method_b08fd268(var5, var1.getMatrices(), var3);
         if (var7.I_field_dc7facc.I_method_6ac4da6f() == 1.0F) {
            var7.I_field_5a = false;
         }
      }

      for (KillEffectsModule.Nested1_a9895860 var10 : this.i_field_7865b31) {
         if (!var10.I_method_b508e75e()) {
            var10.I_method_b508e75a();
            var10.I_method_c50ff315(var1, var5);
         }
      }

      this.i_field_7865b31.removeIf(KillEffectsModule.Nested1_a9895860::I_method_b508e75e);
      BuiltBuffer var9 = var5.endNullable();
      if (var9 != null) {
         BufferRenderer.drawWithGlobalProgram(var9);
      }

      RenderSystem.depthMask(true);
      RenderSystem.setShaderTexture(0, 0);
      RenderSystem.disableBlend();
      RenderSystem.enableCull();
      RenderSystem.disableDepthTest();
      var2.pop();
      this.I_field_7865b31.removeIf(var0 -> !var0.I_field_5a && var0.I_field_dc7facc.I_method_6ac4da6f() == 0.0F);
   };
   private final IiIIIiII_Class69<WorldChangeEvent> II_field_3d936f41 = var1 -> {
      this.I_field_7865b31.clear();
      this.i_field_7865b31.clear();
   };

   public KillEffectsModule() {
      this.IiI_method_c946a29f();
   }

   @Compile(
      obfuscation = 4
   )
   private void IiI_method_c946a29f() {
      this.I_field_bbe33e6c = new ModeSetting(this, "modules.settings.kill_effects.mode");
      this.I_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.kill_effects.mode.lightning");
      this.i_field_500d0627 = new ModeSetting.Nested1_42765c60(this.I_field_bbe33e6c, "modules.settings.kill_effects.mode.particles").select();
      this.i_field_bbe33e6c = new ModeSetting(
         this, "modules.settings.kill_effects.particlePhysics", () -> !this.I_field_bbe33e6c.I_method_ce989e26(this.i_field_500d0627)
      );
      this.II_field_500d0627 = new ModeSetting.Nested1_42765c60(this.i_field_bbe33e6c, "modules.settings.kill_effects.particlePhysics.gravity");
      this.Ii_field_500d0627 = new ModeSetting.Nested1_42765c60(this.i_field_bbe33e6c, "modules.settings.kill_effects.particlePhysics.scatter").select();
      this.I_field_ba20ca4c = new BooleanSetting(this, "theme.sync").I_method_decd82b5();
      this.I_field_bbd4b66c = new ColorSetting(this, "modules.settings.kill_effects.color", this.I_field_ba20ca4c::i_method_9b12da03)
         .I_method_a62f5dd6(IiiiiIIIi_Class242.i_method_5dfec6e());
   }

   private void I_method_9937b821(LivingEntity var1, ColorRGBA var2) {
      Vec3d var3 = var1.getPos();
      float var4 = var1.getHeight();
      float var5 = var1.getWidth();
      float var6 = (float)Math.toRadians(-var1.bodyYaw + 90.0F);
      boolean var7 = this.i_field_bbe33e6c.I_method_ce989e26(this.II_field_500d0627);
      short var8 = 250;
      float var9 = var4 - 0.2F;
      float var10 = var5 * 0.4F;
      this.I_method_7680cd27(var3.add(0.0, var9, 0.0), var10, var8 / 10, var6, var2, var7);
      float var11 = var4 * 0.85F;
      float var12 = var4 * 0.4F;
      float var13 = var5 * 0.4F;
      float var14 = var5 * 0.2F;
      this.I_method_4cdff743(var3, var12, var11, var13, var14, var6, var8 / 4, var2, var7);
      float var15 = var4 * 0.4F;
      float var16 = var5 * 0.15F;

      for (int var17 = -1; var17 <= 1; var17 += 2) {
         Vec3d var18 = new Vec3d(Math.sin(var6) * var5 * 0.5 * var17, var4 * 0.75F, Math.cos(var6) * var5 * 0.5 * var17);
         this.I_method_9eb5a603(var3.add(var18), var15, var16, var6, var8 / 8, var2, var7);
      }

      float var21 = var4 * 0.45F;
      float var22 = var5 * 0.15F;

      for (int var19 = -1; var19 <= 1; var19 += 2) {
         Vec3d var20 = new Vec3d(Math.sin(var6) * var5 * 0.15F * var19, var4 * 0.4F, Math.cos(var6) * var5 * 0.15F * var19);
         this.I_method_9eb5a603(var3.add(var20), var21, var22, var6, var8 / 6, var2, var7);
      }
   }

   private void I_method_7680cd27(Vec3d var1, float var2, int var3, float var4, ColorRGBA var5, boolean var6) {
      for (int var7 = 0; var7 < var3; var7++) {
         float var8 = I_field_6ece06ac.nextFloat() * (float) Math.PI * 2.0F;
         float var9 = (float)Math.acos(2.0F * I_field_6ece06ac.nextFloat() - 1.0F);
         float var10 = var2 * (float)Math.cbrt(I_field_6ece06ac.nextFloat());
         float var11 = var10 * (float)(Math.sin(var9) * Math.cos(var8));
         float var12 = var10 * (float)(Math.sin(var9) * Math.sin(var8));
         float var13 = var10 * (float)Math.cos(var9);
         float var14 = var6 ? 0.02F : 0.008F;
         float var15 = (I_field_6ece06ac.nextFloat() - 0.5F) * var14;
         float var16 = var6 ? 0.03F + I_field_6ece06ac.nextFloat() * 0.04F : (I_field_6ece06ac.nextFloat() - 0.5F) * 0.008F;
         float var17 = (I_field_6ece06ac.nextFloat() - 0.5F) * var14;
         this.i_field_7865b31.add(new KillEffectsModule.Nested1_a9895860(var1, var11, var12, var13, var15, var16, var17, var5, var6));
      }
   }

   private void I_method_4cdff743(Vec3d var1, float var2, float var3, float var4, float var5, float var6, int var7, ColorRGBA var8, boolean var9) {
      for (int var10 = 0; var10 < var7; var10++) {
         float var11 = (I_field_6ece06ac.nextFloat() - 0.5F) * var4 * 2.0F;
         float var12 = var2 + I_field_6ece06ac.nextFloat() * (var3 - var2);
         float var13 = (I_field_6ece06ac.nextFloat() - 0.5F) * var5 * 2.0F;
         float var14 = (float)(var11 * Math.cos(var6) - var13 * Math.sin(var6));
         float var15 = (float)(var11 * Math.sin(var6) + var13 * Math.cos(var6));
         float var16 = var9 ? 0.025F : 0.01F;
         float var17 = (I_field_6ece06ac.nextFloat() - 0.5F) * var16;
         float var18 = var9 ? 0.04F + I_field_6ece06ac.nextFloat() * 0.05F : (I_field_6ece06ac.nextFloat() - 0.5F) * 0.01F;
         float var19 = (I_field_6ece06ac.nextFloat() - 0.5F) * var16;
         this.i_field_7865b31.add(new KillEffectsModule.Nested1_a9895860(var1, var14, var12, var15, var17, var18, var19, var8, var9));
      }
   }

   private void I_method_9eb5a603(Vec3d var1, float var2, float var3, float var4, int var5, ColorRGBA var6, boolean var7) {
      for (int var8 = 0; var8 < var5; var8++) {
         float var9 = I_field_6ece06ac.nextFloat();
         float var10 = (I_field_6ece06ac.nextFloat() - 0.5F) * var3 * 2.0F;
         float var11 = -var9 * var2;
         float var12 = (I_field_6ece06ac.nextFloat() - 0.5F) * var3 * 2.0F;
         float var13 = var7 ? 0.018F : 0.006F;
         float var14 = (I_field_6ece06ac.nextFloat() - 0.5F) * var13;
         float var15 = var7 ? 0.025F + I_field_6ece06ac.nextFloat() * 0.035F : (I_field_6ece06ac.nextFloat() - 0.5F) * 0.006F;
         float var16 = (I_field_6ece06ac.nextFloat() - 0.5F) * var13;
         this.i_field_7865b31.add(new KillEffectsModule.Nested1_a9895860(var1, var10, var11, var12, var14, var15, var16, var6, var7));
      }
   }

   class Nested1_a9895860 {
      double I_field_44;
      double i_field_44;
      double II_field_44;
      float I_field_46;
      float i_field_46;
      float II_field_46;
      long I_field_4a;
      long i_field_4a;
      float Ii_field_46;
      long II_field_4a;
      boolean I_field_5a;
      float iI_field_46;
      ColorRGBA I_field_d0c8ec5;
      static final float ii_field_46 = 0.5F;

      Nested1_a9895860(Vec3d var2, float var3, float var4, float var5, float var6, float var7, float var8, ColorRGBA var9, boolean var10) {
         this.I_field_44 = var2.x + var3;
         this.i_field_44 = var2.y + var4;
         this.II_field_44 = var2.z + var5;
         this.I_field_d0c8ec5 = var9;
         this.I_field_4a = System.currentTimeMillis();
         this.i_field_4a = this.I_field_4a;
         this.I_field_5a = var10;
         this.Ii_field_46 = 0.005F + KillEffectsModule.I_field_6ece06ac.nextFloat() * 0.005F;
         this.II_field_4a = 1500 + KillEffectsModule.I_field_6ece06ac.nextInt(1500);
         this.iI_field_46 = var10 ? 0.999F : 0.995F;
         float var11 = var10 ? 0.04F : 0.03F;
         this.I_field_46 = (KillEffectsModule.I_field_6ece06ac.nextFloat() - 0.5F) * var11;
         this.i_field_46 = var10
            ? 0.025F + KillEffectsModule.I_field_6ece06ac.nextFloat() * 0.035F
            : (KillEffectsModule.I_field_6ece06ac.nextFloat() - 0.5F) * 0.03F;
         this.II_field_46 = (KillEffectsModule.I_field_6ece06ac.nextFloat() - 0.5F) * var11;
      }

      void I_method_b508e75a() {
         float var1 = 0.5F;
         long var2 = System.currentTimeMillis();
         float var4 = (float)(var2 - this.i_field_4a) / 16.67F;
         this.i_field_4a = var2;
         if (var4 > 5.0F) {
            var4 = 5.0F;
         }

         if (this.I_field_5a) {
            this.i_field_46 = this.i_field_46 - this.Ii_field_46 * var4;
         }

         float var5 = (float)Math.pow(this.iI_field_46, var4);
         this.I_field_46 *= var5;
         this.i_field_46 *= var5;
         this.II_field_46 *= var5;
         double var6 = this.I_field_44 + this.I_field_46 * var4;
         double var8 = this.i_field_44 + this.i_field_46 * var4;
         double var10 = this.II_field_44 + this.II_field_46 * var4;
         if (this.I_field_5a && iIIiIIiIi_Class294.I_field_3a9bda27.world != null) {
            BlockPos var12 = BlockPos.ofFloored(this.I_field_44, var8 - 0.05F, this.II_field_44);
            if (!iIIiIIiIi_Class294.I_field_3a9bda27.world.getBlockState(var12).isAir()) {
               this.i_field_46 = -this.i_field_46 * var1;
               var8 = this.i_field_44;
            }

            BlockPos var13 = BlockPos.ofFloored(var6, this.i_field_44, this.II_field_44);
            if (!iIIiIIiIi_Class294.I_field_3a9bda27.world.getBlockState(var13).isAir()) {
               this.I_field_46 = -this.I_field_46 * var1;
               var6 = this.I_field_44;
            }

            BlockPos var14 = BlockPos.ofFloored(this.I_field_44, this.i_field_44, var10);
            if (!iIIiIIiIi_Class294.I_field_3a9bda27.world.getBlockState(var14).isAir()) {
               this.II_field_46 = -this.II_field_46 * var1;
               var10 = this.II_field_44;
            }
         }

         if (Math.abs(this.i_field_46) <= 1.0E-4F) {
            this.I_field_46 = 0.0F;
            this.II_field_46 = 0.0F;
         }

         this.I_field_44 = var6;
         this.i_field_44 = var8;
         this.II_field_44 = var10;
      }

      float I_method_b508e74a() {
         return MathHelper.clamp((float)(System.currentTimeMillis() - this.I_field_4a) / (float)this.II_field_4a, 0.0F, 1.0F);
      }

      float i_method_b517732a() {
         return 1.0F - this.I_method_b508e74a();
      }

      boolean I_method_b508e75e() {
         return System.currentTimeMillis() - this.I_field_4a > this.II_field_4a;
      }

      void I_method_c50ff315(Render3DEvent var1, BufferBuilder var2) {
         float var3 = 0.1F;
         float var4 = 0.5F;
         MatrixStack var5 = var1.getMatrices();
         Camera var6 = iIIiIIiIi_Class294.I_field_3a9bda27.gameRenderer.getCamera();
         float var7 = this.i_method_b517732a();
         var5.push();
         iIiiiIIiI_Class371.I_method_7d461059(var5, new Vec3d(this.I_field_44, this.i_field_44, this.II_field_44));
         var5.multiply(var6.getRotation());
         iIiiIIiii_Class360.I_method_a9f124df(var5, var2, -var3 / 2.0F, -var3 / 2.0F, 0.0, var3, var3, this.I_field_d0c8ec5.mulAlpha(0.9F * var7));
         iIiiIIiii_Class360.I_method_a9f124df(var5, var2, -var4 / 2.0F, -var4 / 2.0F, 0.0, var4, var4, this.I_field_d0c8ec5.mulAlpha(0.15F * var7));
         var5.pop();
      }
   }

   static class Nested1_a9895880 {
      final Vec3d I_field_5bba0d50;
      final ColorRGBA I_field_d0c8ec5;
      boolean I_field_5a = true;
      final IiiiIiIii_Class236 I_field_dc7facc = new IiiiIiIii_Class236(300L, 0.0F, IiiiIiiII_Class237.III_field_dd60aac);
      final List<Vec3d> I_field_7865b31 = new ArrayList<>();

      public Nested1_a9895880(Vec3d var1, ColorRGBA var2) {
         this.I_field_5bba0d50 = var1;
         this.I_field_d0c8ec5 = var2;
         Vec3d var3 = var1;

         for (int var4 = 0; var4 < 200; var4++) {
            this.I_field_7865b31.add(var3 = var3.add(iIIiiiiiI_Class319.I_method_b56b22f(-0.4F, 0.4F), 0.25, iIIiiiiiI_Class319.I_method_b56b22f(-0.4F, 0.4F)));
         }
      }

      void I_method_b08fd268(BufferBuilder var1, MatrixStack var2, Camera var3) {
         this.I_field_dc7facc.I_method_df2f9087(IiiiIiiII_Class237.IIIiI_field_dd60aac);
         this.I_field_dc7facc.I_method_edd6ec25(500L);
         this.I_field_dc7facc.I_method_edd72835(this.I_field_5a);

         for (Vec3d var5 : this.I_field_7865b31) {
            float var6 = (float)(2.0 + 5.0 * (var5.y - this.I_field_5bba0d50.y) / 50.0);
            var2.push();
            iIiiiIIiI_Class371.I_method_7d461059(var2, var5);
            var2.multiply(var3.getRotation());
            iIiiIIiii_Class360.I_method_a9f124df(
               var2,
               var1,
               -var6 / 2.0F,
               -var6 / 2.0F,
               0.0,
               var6,
               var6,
               this.I_field_d0c8ec5.withAlpha(255.0F * this.I_field_dc7facc.I_method_6ac4da6f() * 0.4F)
            );
            var2.pop();
         }
      }
   }
}
