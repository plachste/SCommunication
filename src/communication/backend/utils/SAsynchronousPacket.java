/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.backend.utils;

/**
 *
 * @author plach_000
 */
public class SAsynchronousPacket extends SPacket {

    public SAsynchronousPacket() {
        super();
    }
    
    @Override
    public boolean isAsynchonous() {
        return true;
    }
}
