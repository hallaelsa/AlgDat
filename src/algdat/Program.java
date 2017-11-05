
package algdat;


import bitio.Huffman;
import hjelpeklasser.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;


  public class Program {
      

    public static void main(String ... args) throws MalformedURLException, IOException{
        String url = "http://www.cs.hioa.no/~ulfu/appolonius/kap1/3/kap13.html";
        InputStream inn =
          new BufferedInputStream((new URL(url)).openStream());

        int[] frekvens = Huffman.streamFrekvens(inn);
        String[] bitkoder = Huffman.stringBitkoder(frekvens);

        for (int i = 0; i < bitkoder.length; i++)
          if (bitkoder[i] != null)
          {
            String ut = (i < 32) ? Huffman.ascii[i] : "" + (char)i;
            System.out.printf("%-3s =  %s %d\n",ut,bitkoder[i],frekvens[i]);
          }
        
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
