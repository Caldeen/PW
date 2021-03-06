package mainC;

import java.util.Random;

public class Pisarz extends Thread {
    private int timeToWrite;
    public volatile boolean isWriting=false;
    public boolean canCome=false;
    public boolean isRun;
    public int timeWaiting=0;
    private Library lib;
    public Pisarz(Library lib){
        Random rand=new Random();
        this.lib=lib;
        isRun=true;
        timeToWrite=rand.nextInt(2)+1;
    }

    public void decrTime(){
        timeToWrite--;
        if(timeToWrite==0)
            isRun=false;
    }

    @Override
    public void run() {
        while(isRun)
            if(!isWriting)
                lib.zajmij(this);
    }


}
