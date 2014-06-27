/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.midleend;

import java.io.Serializable;

/**
 * Interface of action capable of performing itself on remote host.
 *
 * @author plach_000
 * @see java.sapi.backend.client.actions.SActionClient
 * @see java.sapi.backend.server.actions.SActionServer
 * @since SAPI Communication 1.0
 */
public interface Performable extends Serializable {
    public void perform();
}
