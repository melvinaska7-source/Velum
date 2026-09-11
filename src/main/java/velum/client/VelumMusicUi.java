package velum.client;

import net.minecraft.client.gui.screen.Screen;
import pyvelum.utility.render.ColorRGBA;

/** Shared ClickGUI entry for the local Velum music player. */
public final class VelumMusicUi {
    private VelumMusicUi() {
    }

    public static iii_Class8 button(Screen parent) {
        Iii_Class4 control = new Iii_Class4()
            .fillWidth()
            .height(16.0F)
            .radius(6.0F)
            .background(state -> IiiiiIIIi_Class242.III_field_d0c8ec5.mulAlpha(0.22F + 0.12F * state.hover() + 0.06F * state.press()))
            .image(VelumClient.id("icons/music.png"), 12.0F, 0.0F, ColorRGBA.WHITE)
            .text(
                IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F),
                () -> "Music",
                state -> IiiiiIIIi_Class242.iII_field_d0c8ec5.mulAlpha(0.72F + 0.28F * state.hover())
            )
            .textAlign(IIi_Class2.i_field_b5755e8c)
            .cursor(iIIIiIIIi_Class274.i_field_aa52e62c)
            .onClick(() -> VelumMusicScreen.open(parent));

        return new iii_Class8()
            .I_method_3301fdd(iII_Class5.I_field_b583e68c)
            .I_method_fe5d8d56(IIIi_Class2.i_field_f93600a1)
            .IiI_method_31d4c97(18.0F)
            .I_method_8939bffd(control);
    }
}
