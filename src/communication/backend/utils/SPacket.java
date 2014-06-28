package communication.backend.utils;

import communication.midleend.Performable;
import java.io.Serializable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public abstract class SPacket implements Serializable {

    public final Performable action;
    protected int id;

    public SPacket(Performable action) {
        this.action = action;
        this.id = 0;
    }

    public void performAction() {
        action.perform();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public abstract boolean isAsynchronous();
}
