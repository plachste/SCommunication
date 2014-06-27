package communication.backend.utils;

import java.io.Serializable;

/**
 *
 * @author Skarab
 */
public class SCommunicationInformation implements Serializable {

    protected int id;

    public SCommunicationInformation(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
