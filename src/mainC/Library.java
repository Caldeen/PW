package mainC;

import java.util.ArrayList;

public class Library {
    private int priority=0;
    private  boolean isPisarzIn;
    private boolean canReaderCome=true;
    private boolean turn;
    private ArrayList<Czytelnik> czL;
    private ArrayList<Pisarz> piL;
    private ArrayList<Czytelnik> activeReaders;
    private ArrayList<Pisarz> activeWriters;
    public Library(ArrayList<Czytelnik> C,ArrayList<Pisarz> P){
        czL=C;
        piL=P;
        isPisarzIn=false;
        activeReaders=new ArrayList<>();
        activeWriters=new ArrayList<>();
    }
    public void show(){
        System.out.println("czytelnicyt w library: "+activeReaders.size());
        System.out.println("pisarze w library: "+activeWriters.size());
    }
    public void openTurn(){
        turn=true;
    }
    public void passTurn(){
        for(Czytelnik czz:activeReaders)
            czz.decrTime();
        activeReaders.removeIf(n->(!n.isRun));
        for(Pisarz pis:activeWriters)
            pis.decrTime();
        activeWriters.removeIf(n->(!n.isRun));
        turn =false;
    }
    public synchronized void zajmij(Thread thr) {
            if (thr instanceof Pisarz) {
                while (!piL.contains(thr)|| isPisarzIn) {
                    for (Czytelnik c : czL) {
                        try {
                            c.wait();
                        } catch (Exception e) {
                        }
                    }
                    System.out.println("pisarz in");
                    ((Pisarz) thr).isRun = false;
                    piL.remove(thr);
                }
            } else {
                while (!canReaderCome||!czL.contains((Czytelnik)thr))
                    try {
                        thr.wait();
                    } catch (Exception e) {
                    }

                    try {
                        System.out.println("Czyt in");
                        isPisarzIn = false;
                        activeReaders.add((Czytelnik) thr);
                        ((Czytelnik) thr).isReading = true;

                        czL.remove(thr);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
            }
    }

}
