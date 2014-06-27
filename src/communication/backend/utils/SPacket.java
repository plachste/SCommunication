/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package communication.backend.utils;

import communication.midleend.Performable;
import java.io.Serializable;

/**
 *
 * @author plach_000
 */
public abstract class SPacket implements Serializable {

    public Performable action;
    protected int id;

    public SPacket() {
        this.id = 0;
    }

    public Performable getAction() {
        return action;
    }

    public int getId() {
        return id;
    }

    public void setAction(Performable action) {
        this.action = action;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public abstract boolean isAsynchonous();
    
}
