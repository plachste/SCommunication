package communication.backend.server;

import communication.backend.utils.SAsynchronousPacket;
import communication.backend.utils.SPacket;
import communication.backend.utils.SSynchronousPacket;
import communication.midleend.Performable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skarab
 */
public class SCommunicationClientHandler implements Runnable {

    private boolean running;
    private Socket communicationSocket;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private SAsynchronousPacket asynchronousPacket;
    private SSynchronousPacket synchronousPacket;

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
        asynchronousPacket.setAction(action);
        os.writeObject(asynchronousPacket);
    }

    public synchronized void sendSynchronous(Performable action) throws IOException {
        synchronousPacket.setAction(action);
        sendObject(synchronousPacket);
        synchronousPacket.inc();
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
                packet.getAction().perform();
            } catch (ClassNotFoundException | IOException ex) {
                Logger.getLogger(SCommunicationClientHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
