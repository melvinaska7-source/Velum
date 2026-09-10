package moscow.velum.mixin.accessors;

import net.minecraft.world.biome.source.BiomeAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={BiomeAccess.class})
public interface BiomeAccesAccessor {
    @Accessor(value="seed")
    public long velum$getSeed();
}

