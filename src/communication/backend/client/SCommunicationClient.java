package communication.backend.client;

import communication.backend.utilities.SActionNull;
import communication.backend.utilities.SAsynchronousPacket;
import communication.backend.utilities.SCommunicationInformation;
import communication.backend.utilities.SLoggerService;
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
    private SAsynchronousPacket asynchronousPacket;
    private SSynchronousPacket synchronousPacket;

    public SCommunicationClient() {
        this(null);
    }

    public SCommunicationClient(final Performable repairAction) {
        this.repairAction = repairAction;
        communicationSocket = new Socket();
        os = null;
        is = null;
        running = true;
        asynchronousPacket = new SAsynchronousPacket(null);
    }

    public void connect(String ip, int port) throws IOException {
        SLoggerService.print("Client: connecting to: " + ip + " " + port);
        communicationSocket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
        SLoggerService.print("Client: got server socket");
    }

    public void bind(Socket socket) throws IOException {
        communicationSocket = socket;
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
    }

    public synchronized void send(Performable action) throws IOException {
        asynchronousPacket.setAction(action);
        os.reset();
        os.writeObject(asynchronousPacket);
    }

    private SPacket receive() throws IOException, ClassNotFoundException {
        Object o = is.readObject();
        if (o instanceof SPacket) {
            return (SPacket) o;
        } else {
            return new SAsynchronousPacket(new SActionNull());
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
                            SLoggerService.print("Client: packet " + packet);
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
                SLoggerService.print("Client: terminating socket");
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
