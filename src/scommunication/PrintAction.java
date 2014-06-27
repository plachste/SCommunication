/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scommunication;

import communication.midleend.Performable;

/**
 *
 * @author plach_000
 */
public class PrintAction implements Performable {
    
    String message;

    public PrintAction(String message) {
        this.message = message;
    }

    @Override
    public void perform() {
        System.out.println(message);
    }
    
}
