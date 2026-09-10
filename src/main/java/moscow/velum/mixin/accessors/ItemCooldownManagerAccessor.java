package moscow.velum.mixin.accessors;

import java.util.Map;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={ItemCooldownManager.class})
public interface ItemCooldownManagerAccessor {
    @Accessor(value="entries")
    public Map<Identifier, Object> velum$getEntries();

    @Accessor(value="tick")
    public int velum$getTick();

    @Invoker(value="getGroup")
    public Identifier velum$getGroup(ItemStack var1);
}

