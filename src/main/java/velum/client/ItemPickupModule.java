package velum.client;

import net.minecraft.item.ItemStack;
import pyvelum.events.game.PickupEvent;
import pyvelum.events.window.ContainerClickEvent;
import velum.client.IiIIIiII_Class69;
import velum.client.IiIiIIII_Class81;
import velum.client.ModuleCategory;
import velum.client.VelumClient;
import velum.client.ModuleInfo;
import velum.client.IiiiiIiii_Class248;
import velum.client.Module;
import velum.client.iiiIiIi_Class118;

@ModuleInfo(name="Item Pickup", category=ModuleCategory.OTHER, iI_method_476ab839=true, III_method_a89e5834="modules.descriptions.item_pickup")
public class ItemPickupModule
extends Module {
    private final IiIIIiII_Class69<PickupEvent> I_field_3d936f41 = pickupEvent -> {
        ItemStack itemStack = pickupEvent.getItemStack();
        if (pickupEvent.getEntity() != ItemPickupModule.I_field_3a9bda27.player) {
            return;
        }
        IiiiiIiii_Class248.Nested1_95187080 nested1_95187080 = IiiiiIiii_Class248.I_method_7613ca72(itemStack);
        if (nested1_95187080 != null) {
            String string = nested1_95187080.I_method_2310f504(itemStack);
            VelumClient.getInstance().I_method_5cb1af22().I_method_67864747(new iiiIiIi_Class118(IiIiIIII_Class81.I_method_f25a980a("alerts.donate_picked") + string, itemStack).I_method_7ab92a7f(string).I_method_c9efc756(nested1_95187080.I_method_40d7118b(itemStack)));
        }
    };
    private final IiIIIiII_Class69<ContainerClickEvent> i_field_3d936f41 = containerClickEvent -> {};
}

