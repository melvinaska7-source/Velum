package pyvelum.events.render;

import lombok.Generated;
import pyvelum.utility.render.CustomDrawContext;
import velum.client.IiIIIIIi_Class66;

public class ChatRenderEvent
extends IiIIIIIi_Class66 {
    private final CustomDrawContext context;
    private final float tickDelta;

    @Generated
    public CustomDrawContext getContext() {
        return this.context;
    }

    @Generated
    public float getTickDelta() {
        return this.tickDelta;
    }

    @Generated
    public ChatRenderEvent(CustomDrawContext customDrawContext, float f) {
        this.context = customDrawContext;
        this.tickDelta = f;
    }
}

