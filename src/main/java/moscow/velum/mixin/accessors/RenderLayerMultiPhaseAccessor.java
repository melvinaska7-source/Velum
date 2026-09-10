package moscow.velum.mixin.accessors;

import net.minecraft.client.render.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={RenderLayer.MultiPhase.class})
public interface RenderLayerMultiPhaseAccessor {
    @Accessor(value="phases")
    public RenderLayer.MultiPhaseParameters velum$getPhases();
}

