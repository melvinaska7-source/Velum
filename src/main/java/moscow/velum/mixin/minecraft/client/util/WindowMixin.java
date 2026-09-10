package moscow.velum.mixin.minecraft.client.util;

import java.io.InputStream;
import java.util.List;
import net.minecraft.client.util.Icons;
import net.minecraft.client.util.Window;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import velum.client.VelumClient;

@Mixin(value={Window.class})
public class WindowMixin {
    @Redirect(method={"setIcon"}, at=@At(value="INVOKE", target="Lnet/minecraft/client/util/Icons;getIcons(Lnet/minecraft/resource/ResourcePack;)Ljava/util/List;"))
    public List<InputSupplier<InputStream>> setCustomIcon(Icons icons, ResourcePack resourcePack) {
        if (VelumClient.getInstance().i_method_e8604970()) {
            try {
                return icons.getIcons(resourcePack);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        InputStream inputStream = VelumClient.class.getResourceAsStream("/assets/%s/icons/window/icon16x16.png".formatted(VelumClient.II_field_523beb0a));
        InputStream inputStream2 = VelumClient.class.getResourceAsStream("/assets/%s/icons/window/icon32x32.png".formatted(VelumClient.II_field_523beb0a));
        return List.of(() -> inputStream, () -> inputStream2);
    }
}

