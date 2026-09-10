package moscow.rockstar.mixin.minecraft.client.gui.screen.multiplayer;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerServerListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import rockstar.client.RockstarClient;
import rockstar.client.iIIIiiIiI_Class283;

@Mixin(value={MultiplayerScreen.class})
public abstract class MultiplayerScreenMixin
extends Screen {
    @Shadow
    protected MultiplayerServerListWidget field_3043;
    @Shadow
    private ButtonWidget field_3041;
    @Shadow
    private ButtonWidget field_3047;

    @Shadow
    public abstract ServerList method_2529();

    @Shadow
    protected abstract void method_20121();

    protected MultiplayerScreenMixin(Text text) {
        super(text);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void rockstar$syncPinnedServer(CallbackInfo callbackInfo) {
        if (this.client == null || RockstarClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        iIIIiiIiI_Class283.I_method_35a51568(this.client, this.method_2529(), this::rockstar$reloadServerListWidget);
    }

    @Inject(method={"updateButtonActivationStates"}, at={@At(value="TAIL")})
    private void rockstar$lockPinnedButtons(CallbackInfo callbackInfo) {
        if (!this.rockstar$isPinnedServerSelected()) {
            return;
        }
        this.field_3041.active = false;
        this.field_3047.active = false;
    }

    @Inject(method={"removeEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void rockstar$preventPinnedRemoval(boolean bl, CallbackInfo callbackInfo) {
        if (!bl || this.client == null || !this.rockstar$isPinnedServerSelected() || RockstarClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        this.client.setScreen((Screen)((MultiplayerScreen)(Object)this));
        callbackInfo.cancel();
    }

    @Inject(method={"editEntry"}, at={@At(value="HEAD")}, cancellable=true)
    private void rockstar$preventPinnedEdit(boolean bl, CallbackInfo callbackInfo) {
        if (!bl || this.client == null || !this.rockstar$isPinnedServerSelected()) {
            return;
        }
        this.client.setScreen((Screen)((MultiplayerScreen)(Object)this));
        callbackInfo.cancel();
    }

    @Unique
    private void rockstar$reloadServerListWidget() {
        if (this.client == null || this.client.currentScreen != this || this.field_3043 == null || RockstarClient.I_field_502d1741.i_method_e8604970()) {
            return;
        }
        this.field_3043.setServers(this.method_2529());
        this.method_20121();
    }

    @Unique
    private boolean rockstar$isPinnedServerSelected() {
        if (this.field_3043 == null) {
            return false;
        }
        MultiplayerServerListWidget.Entry entry = (MultiplayerServerListWidget.Entry)(Object)this.field_3043.getSelectedOrNull();
        if (!(entry instanceof MultiplayerServerListWidget.ServerEntry)) {
            return false;
        }
        MultiplayerServerListWidget.ServerEntry serverEntry = (MultiplayerServerListWidget.ServerEntry)entry;
        ServerInfo serverInfo = serverEntry.getServer();
        return iIIIiiIiI_Class283.I_method_8f9b3f84(serverInfo);
    }
}

