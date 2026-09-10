package velum.client;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.KeyPair;
import java.time.Instant;
import java.util.Base64;
import velum.client.IIIIIii_Class4;
import velum.client.IIiIiII_Class21;
import velum.client.IIiIiIi_Class22;
import velum.client.IIiiIiI_Class27;
import velum.client.IiiiIi_Class30;
import velum.client.iIIIiI_Class35;
import velum.client.iIiiIIi_Class90;

public class IIiIiii_Class24
extends IIIIIii_Class4
implements IIiiIiI_Class27<IIiIiII_Class21> {
    public IIiIiii_Class24(IIiIiIi_Class22 iIiIiVelumClient2, KeyPair keyPair) throws MalformedURLException {
        super("https://authorization.franchise.minecraft-services.net/api/v1.0/multiplayer/session/start");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("publicKey", Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));
        this.I_method_ef32890a(new iIiiIIi_Class90(jsonObject));
        this.i_method_33057dd7("Authorization", iIiIiVelumClient2.I_method_7bfc2241());
    }

    @Override
    public IIiIiII_Class21 I_method_b7becc11(iIIIiI_Class35 iIIIiI_Class352, IiiiIi_Class30 iiiiIi_Class30) throws IOException {
        IiiiIi_Class30 iiiiIi_Class302 = iiiiIi_Class30.i_method_83289561("result");
        return new IIiIiII_Class21(Instant.parse(iiiiIi_Class302.i_method_7caa7e8a("validUntil")).toEpochMilli(), iiiiIi_Class302.i_method_7caa7e8a("signedToken"));
    }
}

