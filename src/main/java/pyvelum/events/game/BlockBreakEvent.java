package pyvelum.events.game;

import lombok.Generated;
import net.minecraft.util.math.BlockPos;
import pyvelum.events.EventCancellable;
import velum.client.IiIIIIiI_Class67;

@IiIIIIiI_Class67(I_method_80b3cd54="block_break")
public class BlockBreakEvent
extends EventCancellable {
    private final BlockPos blockPos;

    public BlockBreakEvent(BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    @Generated
    public BlockPos getBlockPos() {
        return this.blockPos;
    }
}

