package globals.client;

import globals.client.api.RockNetClient;
import globals.shared.proto.Packet;

/**
 * Minimal listener bridge kept for source compatibility.
 * The original listener implementation is not present in the supplied sources,
 * so this class intentionally does not invent network-side behaviour.
 */
public final class RocknetListener implements RockNetClient.Nested1_fb6c9cf {
    private static volatile int siteOnline;
    private static volatile int online;

    @Override
    public void onPacket(Packet packet) {
        // Intentionally empty: no original listener implementation was supplied.
    }

    public static int getSiteOnline() {
        return siteOnline;
    }

    public static int getOnline() {
        return online;
    }
}
