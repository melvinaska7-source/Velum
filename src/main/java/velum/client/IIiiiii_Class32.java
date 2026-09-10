package velum.client;

import java.io.IOException;
import java.net.MalformedURLException;
import velum.client.IIIIIii_Class4;
import velum.client.IIiiiIi_Class30;
import velum.client.IiIIiIi_Class38;
import velum.client.IiiiIi_Class30;
import velum.client.iIIIiI_Class35;

public class IIiiiii_Class32
extends IIIIIii_Class4
implements IiIIiIi_Class38<IIiiiIi_Class30> {
    public IIiiiii_Class32(String string) throws MalformedURLException {
        super("https://pocket.realms.minecraft.net/invites/v1/link/accept/" + string);
    }

    @Override
    public IIiiiIi_Class30 I_method_b7becc11(iIIIiI_Class35 iIIIiI_Class352, IiiiIi_Class30 iiiiIi_Class30) throws IOException {
        return IIiiiIi_Class30.I_method_3adddd56(iiiiIi_Class30);
    }
}

