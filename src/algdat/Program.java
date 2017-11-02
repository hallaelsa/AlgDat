
package algdat;



import algdat.eksempelklasser.Funksjon;
import algdat.eksempelklasser.Heltall;
import algdat.eksempelklasser.Komparator;
import algdat.eksempelklasser.Person;
import algdat.eksempelklasser.Student;
import algdat.eksempelklasser.Studium;
import hjelpeklasser.*;
import java.math.BigInteger;
import java.text.Collator;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

  public class Program {
      

    public static void main(String ... args){
  Integer[] a = {5,10,3,8,13,7,16,16,16,11};

  SBinTre<Integer> tre = SBinTre.sbintre(Stream.of(a));  // Programkode 5.2.3 c)

  System.out.println(tre.tak(1));  // Utskrift: 11
  System.out.println(tre.tak(2));  // Utskrift: 13
  System.out.println(tre.tak(16));
        System.out.println(tre.maks());
        
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
