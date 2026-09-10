package moscow.velum.mixin.minecraft.render.entity;

import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import velum.client.iIiIiiiII_Class349;

@Mixin(value={EntityRenderState.class})
public abstract class EntityRenderStateMixin
implements iIiIiiiII_Class349 {
    @Unique
    private Entity velum$entity;

    @Override
    @Unique
    public void velum$setEntity(Entity entity) {
        this.velum$entity = entity;
    }

    @Override
    @Unique
    public Entity velum$getEntity() {
        return this.velum$entity;
    }
}

