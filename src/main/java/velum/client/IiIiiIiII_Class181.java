package velum.client;

import velum.client.III;
import velum.client.IiIiiIIII_Class177;
import velum.client.iIiiiIiII_Class373;
import velum.client.iii_Class8;

class IiIiiIiII_Class181
extends iii_Class8 {
    private final IiIiiIIII_Class177 I_field_467a06ac;

    IiIiiIiII_Class181(IiIiiIIII_Class177 iiIiiIIII_Class177) {
        this.I_field_467a06ac = iiIiiIIII_Class177;
    }

    @Override
    protected void drawSelf(III iII, float f) {
        this.I_field_467a06ac.I_method_a5bd20e1(iII, this, f);
    }

    @Override
    protected void drawChildren(III iII, float f) {
        iIiiiIiII_Class373.I_method_d5a5ee51(iII.getMatrices(), this.x(), this.y(), this.w(), this.h());
        super.drawChildren(iII, f);
        iIiiiIiII_Class373.I_method_ff80a1df();
    }
}

