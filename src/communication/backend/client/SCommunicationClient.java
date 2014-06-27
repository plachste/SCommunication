package communication.backend.client;

import communication.backend.utils.SAsynchronousPacket;
import communication.backend.utils.SPacket;
import communication.backend.utils.SCommunicationInformation;
import communication.backend.utils.SSynchronousPacket;
import communication.midleend.Performable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Skarab
 */
public class SCommunicationClient {

    private Socket communicationSocket;
    private ObjectOutputStream os;
    private ObjectInputStream is;
    private SCommunicationInformation information;
    private boolean running = true;
    private final Performable repairAction;
    private SAsynchronousPacket asynchronousPacket;
    
    public SCommunicationClient() {
        this(null);
    }

    public SCommunicationClient(final Performable repairAction) {
        this.repairAction = repairAction;
        communicationSocket = new Socket();
    }

    public void connect(String ip, int port) throws IOException {
        System.out.println("Client: starting");
        System.out.println("Client: connecting to: " + ip + " " + port);
        communicationSocket.connect(new InetSocketAddress(InetAddress.getByName(ip), port));
        os = new ObjectOutputStream(communicationSocket.getOutputStream());
        is = new ObjectInputStream(communicationSocket.getInputStream());
        System.out.println("Client: got server socket");
    }
    
    public void bind(Socket socket) {
        communicationSocket = socket;
    }
    
    public synchronized void send(Performable action) throws IOException {
        asynchronousPacket.setAction(action);
        os.writeObject(asynchronousPacket);
    }

    private SPacket receive() {
        try {
            return (SPacket) is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SCommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SPacket packet;
                int currentCount = 0;
                while (running) {
                    packet = receive();
                    if (packet.isAsynchonous() || ((SSynchronousPacket) packet).checkSynchronization(currentCount)) {
                        System.out.println("Client: packet " + packet);
                        packet.getAction().perform();
                    } else if (repairAction != null) {
                        try {
                            send(repairAction);
                        } catch (IOException ex) {
                            Logger.getLogger(SCommunicationClient.class.getName()).log(Level.SEVERE, null, ex);
                            continue;
                        }
                    }
                    if(!packet.isAsynchonous()) {
                        currentCount = ((SSynchronousPacket) packet).getCount();
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
