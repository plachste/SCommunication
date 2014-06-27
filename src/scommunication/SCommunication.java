/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scommunication;

import communication.backend.client.SCommunicationClient;
import communication.backend.server.SCommunicationClientHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author plach_000
 */
public class SCommunication {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, IOException {
        String ip = args[1];
        int port = Integer.parseInt(args[2]);
        PrintAction action;
        new BufferedReader()
        if("-s".equals(args[0])) {
            SCommunicationClientHandler handler = new SCommunicationClientHandler(new Socket(ip, port));
            while(true) {
                
            }
        } else if("-c".equals(args[0])) {
            
        }
    }
}
