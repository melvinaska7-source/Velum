package moscow.velum.mixin.minecraft.text;

import net.minecraft.text.TextVisitFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import velum.client.NameProtectModule;
import velum.client.IIIiIiIi_Class22;
import velum.client.VelumClient;
import velum.client.iIIiIIiIi_Class294;

@Mixin(value={TextVisitFactory.class})
public class TextVisitFactoryMixin
implements iIIiIIiIi_Class294 {
    @ModifyArg(method={"visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z"}, at=@At(value="INVOKE", target="Lnet/minecraft/text/TextVisitFactory;visitFormatted(Ljava/lang/String;ILnet/minecraft/text/Style;Lnet/minecraft/text/Style;Lnet/minecraft/text/CharacterVisitor;)Z", ordinal=0), index=0)
    private static String patchName(String string) {
        NameProtectModule iIIIiIiII_Class21 = VelumClient.getInstance().getModuleManager().getModule(NameProtectModule.class);
        if (iIIIiIiII_Class21.isEnabled() && TextVisitFactoryMixin.I_field_3a9bda27.world != null && TextVisitFactoryMixin.I_field_3a9bda27.player != null) {
            if (IIIiIiIi_Class22.I_method_f1941870()) {
                return string;
            }
            return iIIIiIiII_Class21.i_method_a8235dd7(string);
        }
        return string;
    }
}

