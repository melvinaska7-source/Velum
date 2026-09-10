package velum.client;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.net.MalformedURLException;
import velum.client.IIIIIii_Class4;
import velum.client.IiiIii_Class28;
import velum.client.IiiiIi_Class30;
import velum.client.iIIIiI_Class35;
import velum.client.iIiiIIi_Class90;
import velum.client.iiIIIII_Class97;
import velum.client.iiIIIiI_Class99;
import velum.client.iiIIIii_Class100;
import velum.client.iiIIiII_Class101;
import velum.client.iiIiIiI_Class107;

public class iiIiIIi_Class106
extends IIIIIii_Class4
implements iiIiIiI_Class107<iiIIiII_Class101> {
    public iiIiIIi_Class106(iiIIIII_Class97 iiIIIII_Class972, iiIIIii_Class100 iiIIIii_Class1002, iiIIIiI_Class99 iiIIIiI_Class992, String string) throws MalformedURLException {
        super("https://xsts.auth.xboxlive.com/xsts/authorize");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("SandboxId", "RETAIL");
        if (iiIIIII_Class972 != null) {
            jsonObject.addProperty("DeviceToken", iiIIIII_Class972.I_method_c9babde1());
        }
        jsonObject.add("UserTokens", (JsonElement)new IiiIii_Class28().I_method_77232d41(iiIIIii_Class1002.I_method_901fc1e1()).I_method_3d533548());
        if (iiIIIiI_Class992 != null) {
            jsonObject.addProperty("TitleToken", iiIIIiI_Class992.I_method_49ec99c1());
        }
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.add("Properties", (JsonElement)jsonObject);
        jsonObject2.addProperty("RelyingParty", string);
        jsonObject2.addProperty("TokenType", "JWT");
        this.I_method_ef32890a(new iIiiIIi_Class90(jsonObject2));
        this.i_method_33057dd7("x-xbl-contract-version", "1");
    }

    @Override
    public iiIIiII_Class101 I_method_b7becc11(iIIIiI_Class35 iIIIiI_Class352, IiiiIi_Class30 iiiiIi_Class30) {
        return iiIIiII_Class101.i_method_9a1f5176(iiiiIi_Class30);
    }
}

