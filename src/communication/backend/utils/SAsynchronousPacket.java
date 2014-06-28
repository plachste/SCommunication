package communication.backend.utils;

import communication.midleend.Performable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SAsynchronousPacket extends SPacket {

    public SAsynchronousPacket(Performable action) {
        super(action);
    }

    @Override
    public boolean isAsynchronous() {
        return true;
    }
}
