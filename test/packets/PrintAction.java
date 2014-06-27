package packets;

import communication.midleend.Performable;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class PrintAction implements Performable {

    final String message;

    public PrintAction(String ident, String message) {
        this.message = "<" + ident + "> " + message;
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
}
