package communication.backend.server;

import communication.backend.utilities.SAsynchronousPacket;
import communication.backend.utilities.SPacket;
import communication.backend.utilities.SSynchronousPacket;
import communication.frontend.utilities.Performable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationClientHandler implements Runnable {

    private boolean running;
    private Socket communicationSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;

    public SCommunicationClientHandler(Socket socket) throws IOException {
        System.out.println("Server: got client socket");
        this.communicationSocket = socket;
        this.running = true;
        is = new ObjectInputStream(communicationSocket.getInputStream());
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
    }

    public void disconnect() throws IOException {
        communicationSocket.close();
    }

    public synchronized void sendAsynchronous(Performable action) throws IOException {
        os.writeObject(new SAsynchronousPacket(action));
    }

    public synchronized void sendSynchronous(Performable action) throws IOException {
        sendObject(new SSynchronousPacket(action));
        SSynchronousPacket.increasePacketId();
    }
    
    private synchronized void sendObject(SPacket o) throws IOException {
        os.writeObject(o);
    }

    private SPacket receive() throws IOException, ClassNotFoundException {
        return (SPacket) is.readObject();
    }

    @Override
    public void run() {
        SPacket packet;
        while (running) {
            try {
                packet = receive();
                System.out.println("Server: packet " + packet);
                packet.performAction();
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(SCommunicationClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
