
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
        SBinTre<String> tre = SBinTre.balansert("ABCDDEFFGH".split(""));
        System.out.println(tre.antall() + " " + tre.høyde() + " " + tre);
        
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
