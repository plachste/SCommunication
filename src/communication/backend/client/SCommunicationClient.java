package communication.backend.client;

import communication.backend.utilities.SAsynchronousPacket;
import communication.backend.utilities.SCommunicationInformation;
import communication.backend.utilities.SPacket;
import communication.backend.utilities.SSynchronousPacket;
import communication.frontend.utilities.Performable;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Communication class on client side.
 *
 * @author Štěpán Plachý
 * @author Václav Blažej
 */
public class SCommunicationClient {

    private Socket communicationSocket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private SCommunicationInformation information;
    private boolean running;
    private final Performable repairAction;

    public SCommunicationClient() {
        this(null);
    }

    public SCommunicationClient(final Performable repairAction) {
        this.repairAction = repairAction;
        communicationSocket = new Socket();
        os = null;
        is = null;
        running = true;
    }

    public void connect(String ip, int port) throws IOException {
        System.out.println("Client: starting");
        System.out.println("Client: connecting to: " + ip + " " + port);
        communicationSocket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
        System.out.println("Client: got server socket");
    }

    public void bind(Socket socket) throws IOException {
        communicationSocket = socket;
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
    }

    public synchronized void send(Performable action) throws IOException {
        os.writeObject(new SAsynchronousPacket(action));
    }

    private SPacket receive() throws IOException, ClassNotFoundException {
        Object o = is.readObject();
        if (o instanceof SPacket) {
            return (SPacket) o;
        } else {
            return null; // NullPacket - neutral packet on every action
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SPacket packet;
                int currentCount = 0;
                while (running) {
                    try {
                        packet = receive();
                        if (packet.isAsynchronous() || ((SSynchronousPacket) packet).checkSynchronization(currentCount)) {
                            System.out.println("Client: packet " + packet);
                            packet.performAction();
                        } else if (repairAction != null) {
                            send(repairAction);
                        }
                        if (!packet.isAsynchronous()) {
                            currentCount = ((SSynchronousPacket) packet).getCount();
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(SCommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.out.println("Client: terminating socket");
            }
        }).start();
    }

    public void stop() {
        running = false;
        try {
            communicationSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(SCommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
