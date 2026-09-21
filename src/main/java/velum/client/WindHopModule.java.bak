package velum.client;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.PlayerInput;   // <-- новый импорт
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

        if (!mainHand.isEmpty() && mainHand.getItem() == Items.WIND_CHARGE) {
            if (I_field_3a9bda27.options.useKey.isPressed()) {

                // 1.21.4: поля "jumping" у Input больше нет.
                // Берём текущий PlayerInput и создаём новый с jump = true,
                // сохраняя остальные состояния (вперёд/назад/вбок/шифт/спринт).
                PlayerInput current = I_field_3a9bda27.player.input.playerInput;

                I_field_3a9bda27.player.input.playerInput = new PlayerInput(
                    current.forward(),   // forward
                    current.backward(),  // backward
                    current.left(),      // left
                    current.right(),     // right
                    true,                // jump  <-- вот наш прыжок
                    current.shift(),     // sneak
                    current.sprint()     // sprint
                );
            }
        }

        super.II_method_6642fd22();
    }
}