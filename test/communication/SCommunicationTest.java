package communication;

import communication.backend.client.SCommunicationClient;
import communication.backend.server.SCommunicationServer;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import packets.PrintAction;

public class SCommunicationTest {

    private static final String ip = "localhost";
    private static final int port = 4242;

    public SCommunicationTest() {
    }

    @Ignore
    @Test
    public void initialize() throws Exception {
        System.out.println("initialize");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    @Ignore
    @Test
    public void connect() throws Exception {
        System.out.println("connect");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        assert server.start();
        instance.connect(ip, port);
        instance.stop();
        server.stop();
    }

    @Test
    public void sendPrintAction() throws Exception {
        System.out.println("sendPrintAction");
        SCommunicationServer server = new SCommunicationServer(port);
        SCommunicationClient instance = new SCommunicationClient();
        assert server.start();
        instance.connect(ip, port);
        instance.start();

        for (int i = 0; i < 10; i++) {
            PrintAction clientAction = new PrintAction("Client", String.valueOf(i));
            //System.out.println("?" + clientAction.message + "?");
            instance.send(clientAction);
            PrintAction serverAction = new PrintAction("Server", String.valueOf(i));
            //System.out.println("!" + serverAction.message + "!");
            server.send(0, serverAction);
        }
        // wait for test output
        Thread.sleep(50);
        server.stop();
        instance.stop();
    }
}