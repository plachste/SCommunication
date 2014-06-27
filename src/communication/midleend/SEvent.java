package communication.midleend;

import java.awt.event.ActionEvent;

/**
 *
 * @author Skarab
 */
public class SEvent extends ActionEvent {

    public SEvent(Object source, int id, SEventType command) {
        super(source, id, command.toString());
    }

    static enum SEventType {

        CONNECT("Connect"),
        DISCONNECT("Disonnect");
        String name;

        private SEventType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
