package velum.client;

import jep.ClassEnquirer;

public final class IIiiIiIii_Class108
implements ClassEnquirer {
    private static final String PACKAGE = "pyvelum";

    public boolean isJavaPackage(String string) {
        if (string == null) {
            return false;
        }
        return string.equals(PACKAGE) || string.startsWith("pyvelum.") || string.equals("java") || string.equals("java.io");
    }

    public String[] getClassNames(String string) {
        return new String[0];
    }

    public String[] getSubPackages(String string) {
        return new String[0];
    }
}

