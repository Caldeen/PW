package mainC;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String [] args){
        double wspP,wspC;
        int i=0;
        Random random=new Random();
        Scanner scanner=new Scanner(System.in);
        int priorityDecider=0;
        ArrayList<Czytelnik> czytList=new ArrayList<>();
        ArrayList<Pisarz> pisList=new ArrayList<>();
        ArrayList<Pisarz> activeWriters=new ArrayList<>();
        ArrayList<Czytelnik> activeReader=new ArrayList<>();
        Library lib=new Library(czytList,pisList,activeReader,activeWriters);
        System.out.println("Podaj wspolczynnik dla pisarza");
        wspP=scanner.nextDouble();
        System.out.println("Podaj wspolczynnik dla czytelnika ");
        wspC=scanner.nextDouble();

        while(true){
            double rand=random.nextDouble();
            try {
                Thread.sleep(600);
            }catch(Exception e)
            {}
            i++;
            lib.passTurn();
            System.out.println("                        LOOOOOOOP "+i+"                                   "+rand+" "+wspC+" "+wspP);
            if(wspP>rand){
                System.out.println("Nowy pisarz!");
                Pisarz pi=new Pisarz(lib);
                pisList.add(pi);
            }
            if(wspC>rand){
                System.out.println("Nowy czytelnik!");
                Czytelnik czyt=new Czytelnik(lib);
                czytList.add(czyt);
            }
            priorityDecider+=lib.priority;
            if(priorityDecider<-6||priorityDecider>20) {
                lib.priority *= -1;
                priorityDecider=0;
            }

            lib.determineOrder();
            if(!pisList.isEmpty()){
                Pisarz pi=pisList.get(0);
                pi.canCome=lib.canPisarzcome;
                if(pi.canCome){
                    pi.start();
                    activeWriters.add(pi);
                }
            }
            pisList.removeIf(n->(n.canCome));
            for(Czytelnik cz:czytList){
                cz.canCome=lib.canReaderCome;
                if(cz.canCome){
                    cz.start();
                    activeReader.add(cz);
                }
            }
            czytList.removeIf(m->(m.canCome));
            lib.show();
            System.out.println("Pisarze: "+pisList.size()+" "+pisList.toString());
            System.out.println("Czytelnicy: "+czytList.size()+" "+czytList.toString());

        }
    }
}
