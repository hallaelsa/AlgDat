
package algdat;


import hjelpeklasser.*;
import java.util.*;
import java.util.stream.Stream;


  public class Program {
      

    public static void main(String ... args){
        Integer[] a = {2,8,6,1,7,4,3,9,5,10};
        SBinTre<Integer> tre = SBinTre.sbintre(Stream.of(a)); 
        
    }
    
    public static <T> void sorter(Kø<T> kø, Stakk<T> stakk, Comparator<? super T> c) {
        int n = kø.antall();

        while (n > 0) {
            stakk.leggInn(kø.taUt());       // kandidat for å være den største

            for (int i = 1; i < n; i++) {
                T verdi = kø.taUt();
                if (c.compare(verdi, stakk.kikk()) > 0) {
                    kø.leggInn(stakk.taUt());   // fant en som var større - den
                    stakk.leggInn(verdi);       // legges øverst på stakken
                } else {
                    kø.leggInn(verdi);
                }
            }
            n--;
        }

        while (!stakk.tom()) kø.leggInn(stakk.taUt());  // flytter fra stakk til kø
    }
    

}
