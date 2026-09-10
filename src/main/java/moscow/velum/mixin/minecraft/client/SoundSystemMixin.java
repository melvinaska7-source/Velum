package moscow.velum.mixin.minecraft.client;

import net.minecraft.client.sound.ElytraSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import velum.client.IiiIII_Class25;
import velum.client.SpeedModule;

@Mixin(value={SoundSystem.class})
public class SoundSystemMixin {
    @Inject(method={"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at={@At(value="HEAD")}, cancellable=true)
    private void onPlaySound(SoundInstance soundInstance, CallbackInfo callbackInfo) {
        if (soundInstance instanceof ElytraSoundInstance && SpeedModule.IiI_method_2bf339f0()) {
            callbackInfo.cancel();
            return;
        }
        IiiIII_Class25.I_method_41814ae7(soundInstance, callbackInfo);
    }
}

