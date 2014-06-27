package communication;

import communication.backend.client.SCommunicationClient;
import communication.backend.server.SCommunicationServer;
import org.junit.Test;
import static org.junit.Assert.*;
import packets.PrintAction;

public class SCommunicationTest {

    private static final String ip = "localhost";
    private static final int port = 4242;

    public SCommunicationTest() {
    }

    @Test
    public void initialize() throws Exception {
        System.out.println("initialize");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Test
    public void connect() throws Exception {
        System.out.println("initialize");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        assert server.start();
        instance.connect(ip, port);
        instance.stop();
        server.stop();
    }

    @Test
    public void sendPrintAction() throws Exception {
        System.out.println("initialize");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        server.start();
        instance.connect(ip, port);
        
        instance.send(new PrintAction("Venca", "this is test message"));
        server.send(0, new PrintAction("Server", "acknowledged"));
        //test output
        
        instance.stop();
        server.stop();
    }
}