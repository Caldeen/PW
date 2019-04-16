package mainC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Library {
    public   boolean canPisarzcome;
    public boolean canReaderCome;
    public int priority=-1;
    private int readerWait;
    private int writerWait;
    private ArrayList<Czytelnik> czL;
    private ArrayList<Pisarz> piL;
    private ArrayList<Czytelnik> activeReaders;
    private ArrayList<Pisarz> activeWriters;
    public Library(ArrayList<Czytelnik> C,ArrayList<Pisarz> P,ArrayList<Czytelnik> AC,ArrayList<Pisarz> AP){
        czL=C;
        piL=P;
        canPisarzcome=false;
        activeReaders=AC;
        activeWriters=AP;
    }
    public void show(){
        System.out.println("czytelnicy w library: "+activeReaders.size());
        System.out.println("pisarze w library: "+activeWriters.size());
    }
    public void passTurn(){
        for(Czytelnik czz:activeReaders) {
            czz.decrTime();
        }
        activeReaders.removeIf(n->(!n.isRun));
        for(Pisarz pis:activeWriters) {
            pis.decrTime();
        }
        activeWriters.removeIf(n->(!n.isRun));
        for(Czytelnik c:czL)
            c.timeWaiting++;
        for(Pisarz p:piL)
            p.timeWaiting++;
        readerWait=0;
        writerWait=0;
        Collections.sort( czL,new roll());
        Collections.sort(piL,new rollp());
        Czytelnik longestWaitingReader;
        Pisarz longestWaitingWriter;
        if(!czL.isEmpty()) {
            longestWaitingReader = czL.get(0);
            readerWait=longestWaitingReader.timeWaiting;
        }
        if(!piL.isEmpty()) {
            longestWaitingWriter = piL.get(0);
            writerWait=longestWaitingWriter.timeWaiting;
        }
    }
    public synchronized void zajmij(Thread thr) {
            if (thr instanceof Pisarz) {
                while (!((Pisarz) thr).canCome||((Pisarz) thr).isWriting ) {
                    try {
                        thr.wait();
                    } catch (Exception e) {
                    }
                }
                System.out.println("pisarz in");
                ((Pisarz) thr).isWriting = true;
            } else {
                while (!((Czytelnik)thr).canCome||(((Czytelnik) thr).isReading))
                    try {
                        thr.wait();
                    } catch (Exception e) {
                    }
                System.out.println("Czyt in");
                ((Czytelnik) thr).isReading = true;
            }
    }
    public void determineOrder(){
        canReaderCome=false;
        canPisarzcome=false;
        if(!activeWriters.isEmpty()){
            canPisarzcome=false;
            canReaderCome=false;
            return;
        }
        if(!activeReaders.isEmpty()){
            if(priority==-1){
                canPisarzcome=false;
                canReaderCome=true;
                return;
            }else{
                canPisarzcome=false;
                canReaderCome=false;
                return;
            }
        }
        if(czL.isEmpty()){
            canPisarzcome=true;
            canReaderCome=false;
            return;
        }
        if(piL.isEmpty()){
            canReaderCome=false;
            canReaderCome=true;
            return;
        }
        if(priority==-1){
            canReaderCome=true;
            canPisarzcome=false;
        }else {
            canReaderCome=false;
            canPisarzcome=true;
        }
    }
}
class roll implements Comparator<Czytelnik>{
    public int compare(Czytelnik c1,Czytelnik c2){
        return c2.timeWaiting-c1.timeWaiting;
    }
}
class rollp implements Comparator<Pisarz>{
    public int compare(Pisarz c1,Pisarz c2){
        return c2.timeWaiting-c1.timeWaiting;
    }
}
