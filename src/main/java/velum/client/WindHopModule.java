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
        if (I_field_3a9bda27.player == null || I_field_3a9bda27.options == null) {
            return;
        }

        ItemStack mainHand = I_field_3a9bda27.player.getMainHandStack();
        
        // Проверяем, что в основной руке находится Заряд Ветра (1.21.2+)
        if (!mainHand.isEmpty() && mainHand.getItem() == Items.WIND_CHARGE) {
            // Если игрок нажимает ПКМ (использует предмет)
            // (Если options.useKey замаплен иначе в твоём клиенте, замени на нужное поле)
            if (I_field_3a9bda27.options.useKey.isPressed()) {
                // Заставляем игрока прыгнуть (клиентская часть)
                I_field_3a9bda27.player.input.jumping = true;
                
                // Если сервер не видит прыжок, раскомментируй строку ниже, 
                // она отправит серверу факт прыжка напрямую:
                // I_field_3a9bda27.player.jump(); 
            }
        }

        super.II_method_6642fd22();
    }
}