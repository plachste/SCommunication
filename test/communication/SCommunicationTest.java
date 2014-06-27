package communication;

import communication.backend.client.SCommunicationClient;
import communication.backend.server.SCommunicationServer;
import org.junit.Test;
import static org.junit.Assert.*;

public class SCommunicationTest {

    public SCommunicationTest() {
    }

    @Test
    public void initialize() throws Exception {
        System.out.println("initialize");
        String ip = "localhost";
        int port = 4242;
        SCommunicationServer server = new SCommunicationServer(port);
        server.start();
        SCommunicationClient instance = new SCommunicationClient();
        instance.connect(ip, port);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
}