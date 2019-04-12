package mainC;

import java.util.Random;

public class Czytelnik extends Thread {
    private  int timeToRead;
    private Library lib;
    public boolean isRun;
    public boolean isReading=false;
    public Czytelnik(Library lib){
        Random rand=new Random();
        timeToRead=rand.nextInt(7)+1;
        this.lib=lib;
        isRun=true;

    }

    public void decrTime(){
        timeToRead--;
        if(timeToRead==0)
            isRun=false;
    }
    @Override
    public void run() {
        while(isRun) {
            if (!isReading)
                lib.zajmij(this);
        }
    }
}
