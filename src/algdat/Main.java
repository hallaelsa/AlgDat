/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat;

import java.util.Arrays;
import java.util.Random;

/**
Lag en metode public static int[] minmaks(int[] a). Den skal ved hjelp av en
int-tabell med lengde 2 returnere posisjonene til minste og største verdi i tabellen a. Hvis
du har funnet at m1 er posisjonen til den minste og m2 til den største, kan du returnere
tabellen b definert ved: int[] b = {m1, m2}; Hvor mange sammenligninger bruker
metoden din?
 */
public class Main {

    public static long fakultet(int n) {
        long fakultet = n;
        while(n > 1) {
            fakultet *= --n;
        }
        
        return fakultet;
    }
    
    public static int[] minmaks(int[] a) {
        return new int[]{min(a), maks(a)};
    }
    
    public static int min(int[] a) {
        int m = 0;
        int minsteVerdi = a[0];
        int sammenligning = 0;
        for (int i = 0; i < a.length; i++ ) {
            if(a[i] < minsteVerdi) {
                m = i;
                minsteVerdi = a[i];
                sammenligning++;
            }
        }
        System.out.println("Sammenligning min: " + sammenligning);
        return m;
    }
    
    public static int maks(int[] a) {
        if(a.length < 1) {
            throw new java.util.NoSuchElementException("Feilmelding: arrayet er tomt");
        }
        
        int m = 0;
        for(int i = 1; i < a.length; i++) {
            if(a[i] > a[m]) {
                m = i;
            }
        }
        return m;
    }
    
    public static void makstest() {
        int[] a = { 8, 3, 5, 7, 9, 6, 10, 2, 1, 4 };
        int antallFeil = 0;
        
        if(maks(a) != 6) {
            System.out.println("Test 1: Feil positisjon");
            antallFeil++;
        }
        
        a = new int[] { 10, 9, 8, 7, 6 };
        if (maks(a) != 0) {
            System.out.println("Test 2: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] { 1, 2, 3, 4, 5 };
        if (maks(a) != 4) {
            System.out.println("Test 3: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] { 2, 3, 3, 5, 3, 5, 4 };
        if (maks(a) != 3) {
            System.out.println("Test 4: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] {2};
        if (maks(a) != 0) {
            System.out.println("Test 5: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] { 1, 2 };
        if (maks(a) != 1) {
            System.out.println("Test 6: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] { 2, 1 };
        if (maks(a) != 0) {
            System.out.println("Test 7: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[] { -5, -5 };
        if (maks(a) != 0) {
            System.out.println("Test 8: Feil posisjon");
            antallFeil++;
        }
        
        a = new int[0];
        boolean unntak = false;
        
        try{
            maks(a);
        }catch(Exception e) {
            unntak = true;
            if(!(e instanceof java.util.NoSuchElementException)){
                System.out.println("Test: Kaster feil unntak for en tom tabell");
            }
        }
        
        if(!unntak) {
            System.out.println("Test: det skal kastes unntak for en tom tabell");
        }
    }
    
    public static int[] randPerm(int n)  // virker, men er svært ineffektiv
  {
    Random r = new Random();      // en randomgenerator
    int[] a = new int[n];         // en tabell med plass til n tall

    for (int i = 0; i < n; )      // vi skal legge inn n tall
    {
      int k = r.nextInt(n) + 1;   // trekker et nytt tall k

      int j = 0;
      for ( ; j < i; j++)         // leter i intervallet a[0:i>
      {
        if (a[j] == k) break;     // stopper hvis vi har k fra før
      }
      if (i == j) a[i++] = k;     // legger inn k og øker i
    }
    return a;                     // tabellen returneres
  }
    
      public static int[] randPerm2(int n)  // virker, men er ineffektiv
  {
    Random r = new Random();         // en randomgenerator
    int[] a = new int[n];            // en tabell med plass til n tall
    boolean[] har = new boolean[n];  // en boolsk tabell

    for (int i = 0; i < n; )         // vi skal legge inn n tall
    {
      int k = r.nextInt(n);          // trekker en indeks k
      if (har[k] == false)           // sjekker
      {
        har[k] = true;               // oppdaterer den boolske tabellen
        a[i++] = k + 1;              // legger inn k + 1 i a
      }
    }
    return a;                        // tabellen returneres
  }
      
      public static int[] randPerm3(int n)  // en effektiv versjon
  {
    Random r = new Random();         // en randomgenerator
    int[] a = new int[n];            // en tabell med plass til n tall

    Arrays.setAll(a, i -> i + 1);    // legger inn tallene 1, 2, . , n

    for (int k = n - 1; k > 0; k--)  // løkke som går n - 1 ganger
    {
      int i = r.nextInt(k+1);        // en tilfeldig tall fra 0 til k
      bytt(a,k,i);                   // bytter om
    }

    return a;                        // permutasjonen returneres
  }
      
        public static void bytt(int[] a, int i, int j)
  {
    int temp = a[i]; a[i] = a[j]; a[j] = temp;
  }
            
    public static void main(String[] args) {
//        int[] a = {8,4,17,10,6,20,1,11,15,3,18,9,2,7,19};
//        int[] resultat = minmaks(a);
//        for(int res : resultat) {
//            System.out.println("index: " + res);
//        }
//        
//        System.out.println("maks = " + maks(a));
//        
//        System.out.println("10! = " + fakultet(10));
//        
//        makstest();
//        
//        int[] random = randPerm(10);
//        for(int tall : random) {
//            System.out.println(tall);
//        }
        
        int n = 60000;
        long tid = System.currentTimeMillis();
        int[] b = randPerm3(n);
        tid = System.currentTimeMillis() - tid;
        System.out.println(tid);
        
        
 System.out.println(Arrays.toString(randPerm3(10)));
        

    }
    
}
