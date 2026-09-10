package moscow.rockstar.mixin.minecraft.client.gui.screen.multiplayer;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.network.ServerInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rockstar.client.RockstarClient;
import rockstar.client.IiiiiIIIi_Class242;
import rockstar.client.iIIIiiIiI_Class283;

@Mixin(value={MultiplayerServerListWidget.ServerEntry.class})
public abstract class MultiplayerServerListWidgetServerEntryMixin
extends MultiplayerServerListWidget.Entry {
    @Shadow
    @Final
    private ServerInfo field_19120;

    protected MultiplayerServerListWidgetServerEntryMixin() {
    }

    @Inject(method={"swapEntries"}, at={@At(value="HEAD")}, cancellable=true)
    private void rockstar$preventPinnedMove(int n, int n2, CallbackInfo callbackInfo) {
        if (iIIIiiIiI_Class283.I_method_8f9b3f84(this.field_19120)) {
            callbackInfo.cancel();
        }
    }

    @Inject(method={"render"}, at={@At(value="TAIL")})
    private void rockstar$drawPinnedOutline(DrawContext drawContext, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f, CallbackInfo callbackInfo) {
        if (iIIIiiIiI_Class283.I_method_8f9b3f84(this.field_19120) && !RockstarClient.getInstance().i_method_e8604970()) {
            int n8 = IiiiiIIIi_Class242.IiI_field_d0c8ec5.mix(IiiiiIIIi_Class242.i_method_5dfec6e(), bl ? 0.9f : 0.85f).getRGB();
            int n9 = n3 - 1;
            int n10 = n2 - 1;
            int n11 = n3 + n4 + 1;
            int n12 = n2 + n5 + 1;
            drawContext.fill(n9, n10, n11, n10 + 1, n8);
            drawContext.fill(n9, n12 - 1, n11, n12, n8);
            drawContext.fill(n9, n10 + 1, n9 + 1, n12 - 1, n8);
            drawContext.fill(n11 - 1, n10 + 1, n11, n12 - 1, n8);
        }
    }
}

