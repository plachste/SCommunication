package communication.midleend;

import java.io.Serializable;

/**
 * Interface of action capable of performing itself on remote host.
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 * @see communication.backend.utils.SAsynchronousPacket
 * @see communication.backend.utils.SSynchronousPacket
 * @since SAPI Communication 1.0
 */
public interface Performable extends Serializable {

    public void perform();
}
