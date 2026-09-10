package moscow.velum.mixin.accessors;

import net.minecraft.client.texture.SpriteAtlasTexture;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={SpriteAtlasTexture.class})
public interface SpriteAtlasTextureAccessor {
    @Accessor(value="width")
    public int velum$getWidth();

    @Accessor(value="height")
    public int velum$getHeight();
}

