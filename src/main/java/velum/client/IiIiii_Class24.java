package velum.client;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ua.mintantileak.profile.Profile;
import ua.mintantileak.spk.Compile;

public class IiIiii_Class24 {
   @Compile(
      obfuscation = 4
   )
   public static void I_method_3fd6ba2c() {
      VelumClient.I_field_502d1741.initialize();
   }

   public static void i_method_3fe5460c() {
      VelumClient.I_field_502d1741.shutdown();
   }

   public static void I_method_e10e7018(CallbackInfoReturnable<String> var0) {
      if (!VelumClient.I_field_502d1741.i_method_e8604970()) {
         String var1 = "%s %s (%s) \u2014 %s".formatted("Velum", "2.1", "Alpha", Profile.getUsername());
         var0.setReturnValue(var1);
      }
   }
}
