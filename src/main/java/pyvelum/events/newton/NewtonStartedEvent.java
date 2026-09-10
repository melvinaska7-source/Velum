package pyvelum.events.newton;

import lombok.Generated;
import velum.client.IiIIIIIi_Class66;
import velum.client.IiIIIIiI_Class67;

@IiIIIIiI_Class67(I_method_80b3cd54="newton_started")
public class NewtonStartedEvent
extends IiIIIIIi_Class66 {
    private final String process;

    @Generated
    public String getProcess() {
        return this.process;
    }

    @Generated
    public NewtonStartedEvent(String string) {
        this.process = string;
    }
}

