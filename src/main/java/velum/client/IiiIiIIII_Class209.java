package velum.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import pyvelum.events.render.HudRenderEvent;
import velum.client.III;
import velum.client.MenuModule;
import velum.client.IiIIIiII_Class69;
import velum.client.VelumClient;
import velum.client.IiiIIiiiI_Class207;
import velum.client.IiiIiIiiI_Class215;
import velum.client.IiiIiIiii_Class216;
import velum.client.iIIiIIiIi_Class294;

public class IiiIiIIII_Class209
implements iIIiIIiIi_Class294 {
    private final IiIIIiII_Class69<HudRenderEvent> I_field_3d936f41 = hudRenderEvent -> {
        boolean bl;
        MenuModule menu = VelumClient.getInstance().getModuleManager().getModule(MenuModule.class);
        IiiIIiiiI_Class207 iiiIIiiiI_Class207 = VelumClient.getInstance().I_method_96982062();
        if (IiiIiIIII_Class209.I_field_3a9bda27.currentScreen == null && menu != null && menu.isEnabled()) {
            Screen current = menu.I_method_83034dbb();
            if (current instanceof IiiIIiiiI_Class207 windowOrMenu) {
                iiiIIiiiI_Class207 = windowOrMenu;
                VelumClient.getInstance().I_method_577f3d5a(windowOrMenu);
                IiiIiIIII_Class209.I_field_3a9bda27.setScreen(current);
            }
        } else if (IiiIiIIII_Class209.I_field_3a9bda27.currentScreen == null && menu != null && menu.I_method_4bdd4450().isSelected() && !(iiiIIiiiI_Class207 instanceof IiiIiIiiI_Class215)) {
            iiiIIiiiI_Class207 = new IiiIiIiiI_Class215();
            VelumClient.getInstance().I_method_577f3d5a(iiiIIiiiI_Class207);
        }
        boolean bl2 = bl = IiiIiIIII_Class209.I_field_3a9bda27.currentScreen instanceof IiiIIiiiI_Class207 || IiiIiIIII_Class209.I_field_3a9bda27.currentScreen instanceof IiiIiIiii_Class216;
        if (!bl && VelumClient.getInstance().getModuleManager().getModule(MenuModule.class).isEnabled()) {
            VelumClient.getInstance().getModuleManager().getModule(MenuModule.class).I_method_ad393368(false);
        }
        if (iiiIIiiiI_Class207 == null) {
            return;
        }
        iiiIIiiiI_Class207.getMenuAnimation().I_method_edd6dd11(iiiIIiiiI_Class207.isClosing() ? 0.0f : 1.0f);
        if (!(iiiIIiiiI_Class207 instanceof IiiIiIiiI_Class215) && iiiIIiiiI_Class207.getMenuAnimation().I_method_6ac4da6f() > 0.1f && !(IiiIiIIII_Class209.I_field_3a9bda27.currentScreen instanceof IiiIIiiiI_Class207) && iiiIIiiiI_Class207.isClosing()) {
            III iII = III.I_method_5728d20f(hudRenderEvent.getContext(), -1, -1, MinecraftClient.getInstance().getRenderTickCounter().getTickDelta(false));
            iiiIIiiiI_Class207.render(iII);
        }
    };

    public IiiIiIIII_Class209() {
        VelumClient.getInstance().I_method_7897deab().I_method_2257cd48(this);
    }
}

