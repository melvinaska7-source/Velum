package moscow.velum.mixin.accessors;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BipedEntityModel.class})
public interface BipedEntityModelAccessor {
    @Accessor(value="head")
    public ModelPart velum$getHead();

    @Accessor(value="hat")
    public ModelPart velum$getHat();
}

