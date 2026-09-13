package velum.client;

import pyvelum.utility.render.ColorRGBA;

public final class TrainerDynamicIslandStatus extends IiIiiIiii_Class184 {
    private iiI_Class7 element;

    public TrainerDynamicIslandStatus(MultiSelectSetting parent) {
        super(parent, "trainer", false);
    }

    @Override
    public boolean canShow() {
        return TrainerModule.isRunning() && TrainerModule.timerText() != null;
    }

    @Override
    public iiI_Class7 content(IiIiiIIII_Class177 island) {
        if (element == null) {
            iii_Class8 root = IiIiiIiIi_Class182.I_method_4b294f34(
                15.0F,
                IIII.I_method_12c9a0cb(0.0F, 4.0F, 0.0F, 4.0F),
                3.5F
            ).I_method_483797dd(IIi_Class2.i_field_b5755e8c);

            root.I_method_8939bffd(
                IiIiiIiIi_Class182.I_method_923ba5d7(
                    IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F),
                    () -> "Trainer",
                    () -> IiiiiIIIi_Class242.iI_method_8e08d3b1().withAlpha(255.0F * this.animation.I_method_6ac4da6f()),
                    () -> 10.0F * (1.0F - this.animation.I_method_6ac4da6f())
                )
            );

            root.I_method_8939bffd(
                IiIiiIiIi_Class182.I_method_923ba5d7(
                    IIiIiI_Class11.i_field_857c0621.I_method_3a2d5e3(7.0F),
                    TrainerModule::currentTimer,
                    () -> ColorRGBA.WHITE.withAlpha(255.0F * this.animation.I_method_6ac4da6f()),
                    () -> 0.0F
                )
            );
            element = root;
        }
        return element;
    }
}
