package mainC;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String [] args){
        double wspP,wspC;
        int i=0;
        Random random=new Random();
        Scanner scanner=new Scanner(System.in);

        ArrayList<Czytelnik> czytList=new ArrayList<>();
        ArrayList<Pisarz> pisList=new ArrayList<>();
        Library lib=new Library(czytList,pisList);
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
            boolean xD;
            if(rand>wspP)
                xD=true;
            else
                xD = false;
            i++;
            lib.openTurn();
            System.out.println("                        LOOOOOOOP "+i+"                                   "+rand+" "+wspC+" "+wspP+" "+xD);
            if(wspP>rand){
                System.out.println("Nowy pisarz!");
                Pisarz pi=new Pisarz(lib);
                pisList.add(pi);
                pi.start();
            }
            if(wspC>rand){
                System.out.println("Nowy czytelnik!");
                Czytelnik czyt=new Czytelnik(lib);
                czytList.add(czyt);
                czyt.start();
            }

            System.out.println("Pisarze: "+pisList.toString());
            System.out.println("Czytelnicy: "+czytList.toString());
            lib.show();

        }
    }
}
