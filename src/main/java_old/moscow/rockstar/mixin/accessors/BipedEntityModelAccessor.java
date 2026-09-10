package moscow.rockstar.mixin.accessors;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BipedEntityModel.class})
public interface BipedEntityModelAccessor {
    @Accessor(value="head")
    public ModelPart rockstar$getHead();

    @Accessor(value="hat")
    public ModelPart rockstar$getHat();
}

