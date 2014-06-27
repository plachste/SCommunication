package communication.backend.utils;

import communication.midleend.Performable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SSynchronousPacket extends SPacket {

    private static volatile int superCount = 0;

    public static void increasePacketId() {
        SSynchronousPacket.superCount++;
    }
    private int count;

    public SSynchronousPacket(Performable action) {
        super(action);
        this.count = 0;
    }

    public int getCount() {
        return superCount;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean checkSynchronization(int count) {
        return this.superCount == count + 1; // functional for overflow
    }

    @Override
    public boolean isAsynchonous() {
        return false;
    }
}
