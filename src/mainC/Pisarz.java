package mainC;

public class Pisarz extends Thread {
    private final long timeToWrite=1300;
    public boolean isRun;
    private Library lib;
    public Pisarz(Library lib){
        this.lib=lib;
        isRun=true;
    }
    public void sleep() {
        try {
            this.sleep(timeToWrite);
        } catch (Exception e) {

        }
    }

    public long getTimeToWrite() {
        return timeToWrite;
    }

    @Override
    public void run() {
        while(isRun)
        lib.zajmij(this);
    }


}
