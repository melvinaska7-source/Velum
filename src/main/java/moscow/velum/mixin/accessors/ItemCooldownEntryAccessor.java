package moscow.velum.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets={"net.minecraft.entity.player.ItemCooldownManager$Entry"})
public interface ItemCooldownEntryAccessor {
    @Accessor(value="endTick")
    public int velum$getEndTick();
}

