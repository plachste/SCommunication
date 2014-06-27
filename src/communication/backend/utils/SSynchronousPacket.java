package communication.backend.utils;

public class SSynchronousPacket extends SPacket {
    private int count;

    public SSynchronousPacket() {
        super();
        this.count = 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public void inc() {
        count++;
    }
    
    public boolean checkSynchronization(int count) {
        return this.count == count + 1;
    }

    @Override
    public boolean isAsynchonous() {
        return false;
    }
    
    
}
