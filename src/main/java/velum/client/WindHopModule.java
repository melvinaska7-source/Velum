package velum.client;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import ua.mintantileak.spk.Compile;

@ModuleInfo(
    name = "WindHop",
    III_method_a89e5834 = "modules.descriptions.wind_hop",
    category = ModuleCategory.MOVEMENT
)
public class WindHopModule extends Module {

    @Override
    @Compile(obfuscation = 4)
    public void II_method_6642fd22() {
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.options == null) return;

        ItemStack mainHand = I_field_3a9bda27.player.getMainHandStack();
        if (mainHand.isEmpty() || mainHand.getItem() != Items.WIND_CHARGE) return;

        // ПКМ зажата → прыгаем
        if (I_field_3a9bda27.options.useKey.isPressed()
                && I_field_3a9bda27.player.isOnGround()) {
            I_field_3a9bda27.player.jump();   // velocity.y = 0.42 автоматически
        }

        super.II_method_6642fd22();
    }
}