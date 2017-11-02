/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat;

import algdat.eksempelklasser.Komparator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 *
 * @author Miina
 */
public class Tabell {

    private Tabell() {
        
    }

    
    public static void skriv(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        if (til - fra > 0) {
            System.out.print(a[fra]);
        }
        for(int i = fra+1; i < til; i++){
            System.out.print(" " + a[i]);
        }
    }
    
    public static void skriv(int[]a) {
        skriv(a, 0, a.length);
    }
    
    public static void skrivln(int[] a, int fra, int til) {
        skriv(a, fra, til);
        System.out.println();
    }
    
    public static void skrivln(int[] a) {
        skrivln(a, 0, a.length);
    }
    
    public static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0)                                 
          throw new ArrayIndexOutOfBoundsException
            ("fra(" + fra + ") er negativ!");

        if (til > tablengde)                       
          throw new ArrayIndexOutOfBoundsException
            ("til(" + til + ") > tablengde(" + tablengde + ")");

        if (fra > til)                             
          throw new IllegalArgumentException
            ("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        
        if (fra==til) 
          throw new NoSuchElementException
            ("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");
        
    }
    
    public static void vhKontroll(int tablengde, int v, int h) {
        if (v < 0)
          throw new ArrayIndexOutOfBoundsException("v(" + v + ") < 0");

        if (h >= tablengde)
          throw new ArrayIndexOutOfBoundsException
            ("h(" + h + ") >= tablengde(" + tablengde + ")");

        if (v > h + 1)
          throw new IllegalArgumentException
            ("v = " + v + ", h = " + h);
    }
    
    public static void bytt(char[] c, int i, int j) {
        char temp  = c[i];
        c[i] = c[j];
        c[j] = temp;
    }
    
    public static void bytt(int[] a, int i, int j) {
        int temp = a[i]; 
        a[i] = a[j]; 
        a[j] = temp;
    }
  
    public static int[] randPerm(int n) {
        Random r = new Random();        
        int[] a = new int[n];            

        Arrays.setAll(a, i -> i + 1);    

        for (int k = n - 1; k > 0; k--) {
          int i = r.nextInt(k+1);       
          bytt(a,k,i);                  
        }

        return a;                        
    }  
    
    public static void randPerm(int[] a) {
        Random r = new Random();    

        for (int k = a.length - 1; k > 0; k--) {
          int i = r.nextInt(k + 1); 
          bytt(a,k,i);
        }
    }
        
    public static int antallMaks(int[] a) {
        int antall = 0;            
        int maksverdi = a[0];

        for (int i = 1; i < a.length; i++) {
          if (a[i] > maksverdi)    
          {
            antall++;              
            maksverdi = a[i];      
          }
        }

        return antall;    
    }   
    
    public static int min(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);

        int m = fra;             
        int minverdi = a[fra];   

        for (int i = fra + 1; i < til; i++) if (a[i] < minverdi) {
          m = i;               
          minverdi = a[m];    
        }

        return m;  
    }

    public static int min(int[] a) {
        return min(a,0,a.length);  
    }
    
    public static int maks(int[] a, int fra, int til) {
        if(a==null)
            throw new NullPointerException ("Parametertabellen a er null!");
        
        fratilKontroll(a.length, fra, til);

        int m = fra;              
        int maksverdi = a[fra];  

        for (int i = fra + 1; i < til; i++) {
            if (a[i] > maksverdi) {
              m = i;              
              maksverdi = a[m];    
            }
        }

        return m;  
    }
    
    public static int maks(int[] a) {
        return maks(a,0,a.length);   
    }
    
//    public static int[] nestMaks(int[] a) {
//        int length = a.length;   
//
//        if (length < 2) throw   
//          new java.util.NoSuchElementException("a.length(" + length + ") < 2!");
//
//        int m = 0; 
//        int nm = 1;
//        
//        if(a[nm] > a[m]) {
//            m = 1;
//            nm = 0;
//        }
//        
//        int maks = a[m];
//        int nestmaks = a[nm];
//        
//        for(int i = 2; i < length; i++) {
//            if(a[i] > nestmaks) {
//                if(a[i] > maks) {
//                    nm = m;
//                    nestmaks = maks;
//                    
//                    m = i;
//                    maks = a[m];
//                } else {
//                    nm = i;
//                    nestmaks = a[nm];
//                }
//            }
//        }
//        
//        return new int[] {m,nm};      
//
//    } 
    
    public static int[] nestMin(int[] a) {
        int length = a.length;
        
        if(length < 2)
            throw new IllegalArgumentException ("a.length(" + length + ") < 2!");
        
        int m = min(a);
        int nm = 0;
        
        if(m == 0) {
            nm = min(a, 1, length);
        } else if (m == length-1) {
            min(a, 0, length-1);
        } else {
            int mv = min(a,0,m);
            int mh = min(a, m+1, length);
            nm = a[mh] < a[mv] ? mh : mv;
        }
        
        int[] b = {m, nm};
        return b;
    }
    
    public static void sortering(int[] a) {
        for(int i = a.length; i > 1; i--) {
            int m = maks(a, 0, i);
            bytt(a, i-1, m);
        }
    }
    
    public static int antallNestMaks(int[] a) {
        int antall = 0;
                int length = a.length;   

        if (length < 2) throw   
          new java.util.NoSuchElementException("a.length(" + length + ") < 2!");

        int m = 0; 
        int nm = 1;
        
        if(a[nm] > a[m]) {
            m = 1;
            nm = 0;
        }
        
        int maks = a[m];
        int nestmaks = a[nm];
        
        for(int i = 2; i < length; i++) {
            if(a[i] > nestmaks) {
                if(a[i] > maks) {
                    antall++;
                    nm = m;
                    nestmaks = maks;
                    
                    m = i;
                    maks = a[m];
                } else {
                    nm = i;
                    nestmaks = a[nm];
                }
            }
        }
        return antall;
        
    }
    
    
    public static int[] nestMaks(int[] a) {
        int n = a.length;               

        if (n < 2) 
          throw new IllegalArgumentException("a.length(" + n + ") < 2!");

        int[] b = new int[2*n];         
        System.arraycopy(a,0,b,n,n);     

        for (int k = 2*n-2; k > 1; k -= 2) 
          b[k/2] = Math.max(b[k],b[k+1]);

        int maksverdi = b[1], nestmaksverdi = Integer.MIN_VALUE;

        for (int m = 2*n - 1, k = 2; k < m; k *= 2)
        {
          int tempverdi = b[k+1]; 
          if (maksverdi != b[k]) { tempverdi = b[k]; k++; }
          if (tempverdi > nestmaksverdi) nestmaksverdi = tempverdi;
        }

        return new int[] {maksverdi,nestmaksverdi};

    } 
    
    public static void kopier(int[] a, int i, int[] b, int j, int ant) {
        
        for(int n = i+ant; i < n; ) {
            b[j++] = a[i++];
        }
    }
    
    public static void snu(int[] a, int v) {
        snu(a, v, a.length-1);
    }    
        
    public static void snu(int[] a, int v, int h) {
        vhKontroll(a.length, v, h);
        while(v < h)
            bytt(a, v++, h--);
    }
    
    public static void snu(int[] a) {
        int v = 0;
        int h = a.length-1;
            snu(a, v++, h--);
    }
    
    public static boolean nestePermutasjon(int[] a) {
        int i = a.length - 2;
        while (i >= 0 && a[i] > a[i + 1]) 
            i--;
        if (i < 0) 
            return false; 
        
        int j = a.length - 1;
        while (a[j] < a[i]) j--;
        bytt(a, i, j); snu(a, i + 1); 
        
        return true;
    }
    
    public static int inversjoner(int[] a) {
        int antall = 0; 
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) antall++;
            }
        }
        return antall;
    }
    
    public static boolean erSortert(int[] a) {
        for (int i = 1; i < a.length; i++)
            if (a[i-1] > a[i]) 
                return false;
        return true;
    }
    
    public static int boble(int[] a, int n) {
        int antall = 0; 
        for (int i = 1; i < n; i++){
            if (a[i - 1] > a[i]) {
                bytt(a, i - 1, i); 
                antall++; 
            }
        }
        return antall; 
    }
    
    public static void boblesortering(int[] a) {
        for (int n = a.length; n > 1; n--) {
            if (boble(a, n) == 0) 
                break;
        }
    }
    
    public static void utvalgssortering(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            int min = min(a, i, a.length);
            if(min != i)
                bytt(a, i, min(a, i, a.length)); 
        }
    }
    
    
      public static void selectionSort(int[] a) {
        for (int i = 0; i < a.length - 1; i++) {
          int m = i;             
          int  minverdi = a[i];  

          for (int j = i + 1; j < a.length; j++)
          {
            if (a[j] < minverdi)
            {
              minverdi = a[j]; 
              m = j;       
            }
          }
          
          int temp = a[i];
          a[i] = a[m];
          a[m] = temp;
        }
    }
      
    public static void utvalgssortering(int[] a, int fra, int til){
        for (int i = fra; i < til-1; i++) {
            int m = i;             
            int  minverdi = a[i];  

            for (int j = i + 1; j < til; j++) {
              if (a[j] < minverdi) {
                minverdi = a[j]; 
                m = j;       
              }
            }

            int temp = a[i];
            a[i] = a[m];
            a[m] = temp;
        }
    }
    
    public static <T> void utvalgssortering(T[] a, Comparator<? super T> c) {
        for(int k = a.length; k > 1; k--) {
            bytt(a, maks(a, 0, k, c), k-1);
        }
    }
    
    public static int lineærsøk(int[] a, int verdi) {
        if (a.length == 0 || verdi < a[0])
            return -1; 

        int i = a.length-1; 
        //for( ; a[i] > verdi; i--); 
        while(a[i] > verdi) {
            i--;
        }

        return verdi == a[i] ? i : -(i + 2); 
    }
    
    public static int lineærsøk(int[] a, int k, int verdi) {
        if(k < 1) 
            throw new IllegalArgumentException("intervall too small");
        
        int j = k-1;
        //for(; j < a.length && verdi > a[j]; j+=k);
        while(j < a.length && verdi > a[j]) {
            j+=k;
        }
        
        int i = j-k+1;
        //for(; i < a.length && verdi > a[i]; i++);
        while(i < a.length && verdi > a[i]) {
            i++;
        }
        
        if(i<a.length && a[i] == verdi)
            return i;
        else return -(i+1);
        
        
    }
    
    public static int kvadratrotsøk(int[] a, int verdi) {
        return lineærsøk(a,(int)Math.sqrt(a.length),verdi);
    }
    
    public static int binærsøk(int[] a, int fra, int til, int verdi) {
        Tabell.fratilKontroll(a.length,fra,til);  
        int v = fra, h = til - 1;  

        while (v < h) {
            int m = (v + h)/2; 

            if (verdi > a[m]) 
                v = m + 1;  
            else  
                h = m;                  
        }
        if (h < v || verdi < a[v]) 
            return -(v + 1);  
        else if (verdi == a[v]) 
            return v;           
        else  
            return -(v + 2);                      
    }
    
    public static <T> int binærsøk(T[] a, int fra, int til, T verdi, Comparator<? super T> c) {
        fratilKontroll(a.length, fra, til);
        int v = fra;
        int h = til -1;
        
        while( v <= h) {
            int m = (v + h)/2;
            T midtverdi = a[m];
            
            int cmp = c.compare(verdi,midtverdi);
            
            if(cmp > 0)
                v = m + 1;
            else if(cmp < 0) 
                h = m - 1;
            else 
                return m;
        }
        
        return -(v + 1);
        
    }
    
    public static <T> int binærsøk(T[] a, T verdi, Comparator<? super T> c) {
        return binærsøk(a,0,a.length,verdi,c);  
    }
    
    public static void innsettingssortering(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int verdi = a[i], j = i- 1;
            
//            for (; j >= 0 && verdi < a[j]; j--)
//                a[j+1] = a[j]; 
            while(j >= 0 && verdi < a[j]) {
                a[j+1] = a[j];
                j--;
            }
            
            a[j + 1] = verdi; 
        }
    }
    
    public static void innsettingssortering(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til);
        
        for (int i = fra+1; i < til; i++) {
            int verdi = a[i];
            int j = i-1;
            
            while(j >= 0 && verdi < a[j]) {
                a[j+1] = a[j];
                j--;
            }
            
            a[j+1] = verdi;
        }
    }
    
    public static void shell(int[] a, int k) {
        for (int i = k; i < a.length; i++) {
            int temp = a[i];
            int j = i - k;
            for ( ; j >= 0 && temp < a[j]; j -= k) {
                a[j + k] = a[j];
            }
            a[j + k] = temp;
            
        }
    }
    
    private static int parter0(int[] a, int v, int h, int skilleverdi) {
        while (true) {
            while (v <= h && a[v] < skilleverdi) 
                v++; 
            while (v <= h && a[h] >= skilleverdi) 
                h--;
           
            if (v < h) 
                bytt(a,v++,h--);
           
            else return v; 
        }
    }
    
    public static int parter(int[] a, int fra, int til, int skilleverdi) {
        fratilKontroll(a.length, fra, til);
        return parter0(a, fra, til - 1, skilleverdi);
    }

    public static int parter(int[] a, int skilleverdi) {
        return parter0(a, 0, a.length - 1, skilleverdi);
    }
    
    public static int antallOmbyttinger(int[] a, int s) {
        int antall = 0;
        int m = s - 1;
        for (int i = 0; i < m; i++) 
            if (a[i] > m) 
                antall++;
        
        return antall;
    }
    
    private static int sParter0(int[] a, int v, int h, int indeks) { 
        bytt(a, indeks, h);           // skilleverdi a[indeks] flyttes bakerst
        int pos = parter0(a, v, h - 1, a[h]);  // partisjonerer a[v:h − 1]
        bytt(a, pos, h);              // bytter for å få skilleverdien på rett plass
        return pos;                   // returnerer posisjonen til skilleverdien
    }
    
    public static int sParter(int[] a, int fra, int til, int indeks) {
        fratilKontroll(a.length, fra, til);

        if (fra == til) throw new
          IllegalArgumentException("Intervallet a[" + fra + ":" + til + "> er tomt!");

        if (indeks < fra || indeks >= til) throw new
          IllegalArgumentException("indeks(" + indeks + ") er utenfor intervallet!");

        return sParter0(a, fra, til - 1, indeks);
    }

    public static int sParter(int[] a, int indeks) {
        if (indeks < 0 || indeks >= a.length) throw new
          IllegalArgumentException("indeks(" + indeks + ") er utenfor tabellen!");

        return sParter0(a, 0, a.length - 1, indeks);
    }
    
    private static void kvikksortering0(int[] a, int v, int h) {
        if (v >= h) 
            return; 
        int k = sParter0(a, v, h, (v + h)/2);  
        kvikksortering0(a, v, k - 1);    
        kvikksortering0(a, k + 1, h);    
    }

    public static void kvikksortering(int[] a, int fra, int til) {
        fratilKontroll(a.length, fra, til); 
        kvikksortering0(a, fra, til - 1);  
        innsettingssortering(a, fra, til);
    }

    public static void kvikksortering(int[] a) {
        kvikksortering0(a, 0, a.length - 1);
        innsettingssortering(a);
    }
    
    public static int[] enkelFletting(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];  
        int k = Math.min(a.length, b.length);   

        for (int i = 0; i < k; i++) {
            c[2*i] = a[i];        
            c[2*i + 1] = b[i];   
        }
       
        System.arraycopy(a, k, c, 2*k, a.length - k);
        System.arraycopy(b, k, c, 2*k, b.length - k);

        return c;
    }
    
    public static String enkelFletting(String a, String b) {
        int k = Math.min(a.length(), b.length());   
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < k; i++) {
          s.append(a.charAt(i)).append(b.charAt(i));
        }

        s.append(a.substring(k)).append(b.substring(k));

        return s.toString();
    }
    
    public static int flett(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0;
        while (i < m && j < n) 
            c[k++] = a[i] <= b[j] ? a[i++] : b[j++];

        while (i < m) 
            c[k++] = a[i++];   
        while (j < n) 
            c[k++] = b[j++];   

        return k;  
    }
    
    public static int flett(int[] a, int[] b, int[] c) {
        return flett(a, a.length, b, b.length, c);
    }
    
    private static void flett(int[] a, int[] b, int fra, int m, int til) {
        int n = m - fra;               
        System.arraycopy(a,fra,b,0,n);  

        int i = 0, j = m, k = fra;      

        while (i < n && j < til) {                               
          a[k++] = b[i] <= a[j] ? b[i++] : a[j++];
        }

        while (i < n) 
            a[k++] = b[i++];  
    }
    
    private static void flettesortering(int[] a, int[] b, int fra, int til) {
        if (til - fra <= 1) 
            return;   
        
        int m = (fra + til)/2;       

        flettesortering(a,b,fra,m);   
        flettesortering(a,b,m,til);   

        if (a[m-1] > a[m]) 
            flett(a,b,fra,m,til);  
    }
    
    public static void flettesortering(int[] a) {
        int[] b = Arrays.copyOf(a, a.length/2);  
        flettesortering(a,b,0,a.length);        
    }
    
    public static int union(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0; // indekser for a, b og c
        while (i < m && j < n) {
            if (a[i] < b[j]) 
                c[k++] = a[i++]; 
            else if (a[i] == b[j]) {
            c[k++] = a[i++]; j++; 
            }
            else 
                c[k++] = b[j++]; 
        }
        
        while (i < m) 
            c[k++] = a[i++]; 
        while (j < n) 
            c[k++] = b[j++]; 
        return k; 
    }
    
    public static int union(int[] a, int[] b, int[] c) {
        return union(a, a.length, b, b.length, c);
    }
    
    public static int snitt(int[] a, int m, int[] b, int n, int[] c) {
        int i = 0, j = 0, k = 0;           

        while (i < m && j < n) {
            if (a[i] < b[j]) 
                i++;              // hopper over a[i]
            else if (a[i] == b[j]) {
                c[k++] = a[i++]; j++;            // tar med a[i], men ikke b[j]
            } else  
                j++;                         // hopper over b[j]
        }

        return k;                            // antall i snittet
    }

    public static int snitt(int[] a, int[] b, int[] c) {
        return snitt(a, a.length, b, b.length, c);
    }
    
    public static int differens(int[] a, int m, int[] b, int n, int[] c) {
        if (m < 0 || m > a.length)
          throw new IllegalArgumentException("a[0:m> er ulovlig!");

        if (n < 0 || n > b.length)
          throw new IllegalArgumentException("b[0:n> er ulovlig!");

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
          if (a[i] < b[j]) 
              c[k++] = a[i++];
          else if (a[i] == b[j]) { 
              i++; 
              j++;
          } else 
              j++;
        }

        while (i < m) 
            c[k++] = a[i++];

        return k;
    }

    public static int differens(int[] a, int[] b, int[] c) {
        return differens(a, a.length, b, b.length,c);
    }
    
    public static boolean inklusjon(int[] a, int m, int[] b, int n) {
        if (m < 0 || m > a.length)
          throw new IllegalArgumentException("a[0:m> er ulovlig!");

        if (n < 0 || n > b.length)
          throw new IllegalArgumentException("b[0:n> er ulovlig!");

        int i = 0, j = 0;

        while (i < m && j < n) {
          if (a[i] < b[j]) 
              i++;
          else if (a[i] == b[j]) {
              i++; 
              j++;
          } else 
              return false;
        }

        return j == n;
    }

    public static boolean inklusjon(int[] a, int[] b) {
        return inklusjon(a, a.length, b, b.length);
    }
    
    public static int xunion(int[] a, int m, int[] b, int n, int[] c) {
        if (m < 0 || m > a.length)
            throw new IllegalArgumentException("a[0:m> er ulovlig!");

        if (n < 0 || n > b.length)
            throw new IllegalArgumentException("b[0:n> er ulovlig!");

        int i = 0, j = 0, k = 0;

        while (i < m && j < n) {
            if (a[i] < b[j]) 
                c[k++] = a[i++];
            else if (a[i] == b[j]) { 
                i++; 
                j++;
            } else 
                c[k++] = b[j++];
        }
        
        while (i < m) 
            c[k++] = a[i++];
        while (j < n) 
            c[k++] = b[j++];

        return k;
    }

    public static int xunion(int[] a, int[] b, int[] c) {
        return xunion(a, a.length, b, b.length,c);
    }
    
    public static int maks(double[] a) {
        int m = 0;                           
        double maksverdi = a[0];            

        for (int i = 1; i < a.length; i++) if (a[i] > maksverdi) {
          maksverdi = a[i];    
          m = i;               
        }
        return m;    
    }
    
    public static int maks(String[] a) {
        int m = 0;                          
        String maksverdi = a[0];          

        for (int i = 1; i < a.length; i++) if (a[i].compareTo(maksverdi) > 0) {
          maksverdi = a[i]; 
          m = i;             
        }
        return m; 
    }
    
    public static <T extends Comparable<? super T>> int maks(T[] a) {
        int m = 0;                     
        T maksverdi = a[0];            

        for (int i = 1; i < a.length; i++) if (a[i].compareTo(maksverdi) > 0) {
            maksverdi = a[i];  
            m = i;           
        }
        return m; 
    } 
    
    public static <T extends Comparable<? super T>> void innsettingssortering(T[] a) {
        for (int i = 1; i < a.length; i++) {
            T verdi = a[i]; 
            int j = i - 1;
            
            
            for (; j >= 0 && verdi.compareTo(a[j]) < 0 ; j--)
                a[j+1] = a[j];
            
            a[j + 1] = verdi; 
        }
    }
    
    public static void bytt(Object[] a, int i, int j) {
        Object temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static Integer[] randPermInteger(int n) {
        Integer[] a = new Integer[n];            
        Arrays.setAll(a, i -> i + 1);             

        Random r = new Random();  

        for (int k = n - 1; k > 0; k--) {
            int i = r.nextInt(k+1); 
            bytt(a,k,i);           
        }
        return a; 
    }
    
    public static void skriv(Object[] a, int fra, int til) {
        fratilKontroll(a.length,fra,til);

        for (int i = fra; i < til; i++) 
            System.out.print(a[i] + " ");
    }

    public static void skriv(Object[] a) {
        skriv(a,0,a.length);
    }

    public static void skrivln(Object[] a, int fra, int til) {
        skriv(a,fra,til);
        System.out.println();
    }

    public static void skrivln(Object[] a) {
         skrivln(a,0,a.length);
    }
    
    public static <T> void innsettingssortering(T[] a, Comparator<? super T> c) {
        for (int i = 1; i < a.length; i++) {
          T verdi = a[i];         
          int  j = i - 1;        
           
          for (; j >= 0 && c.compare(verdi,a[j]) < 0 ; j--) {
              a[j+1] = a[j];
          }

          a[j + 1] = verdi;   
        }
    }
    
    public static <T> int maks(T[] a, Comparator<? super T> c) {
        return maks(a, 0, a.length, c);  
    }

    public static <T> int maks(T[] a, int fra, int til, Comparator<? super T> c) {
        fratilKontroll(a.length,fra,til);

        if (fra == til) 
            throw new NoSuchElementException("fra(" + fra + ") = til(" + til + ") - tomt tabellintervall!");

        int m = fra;               
        T maksverdi = a[fra];       

        for (int i = fra + 1; i < til; i++) {
          if (c.compare(a[i],maksverdi) > 0) {
            maksverdi = a[i];     
            m = i;                
          }
        }
        return m;                 

    }  
    
    public static <T> int parter(T[] a, int v, int h, T skilleverdi, Comparator<? super T> c) {
        while (v <= h && c.compare(a[v],skilleverdi) < 0) 
            v++;
        while (v <= h && c.compare(skilleverdi,a[h]) <= 0) 
            h--;

        while (true) {
          if (v < h) 
              Tabell.bytt(a,v++,h--); 
          else return v;
          
          while (c.compare(a[v],skilleverdi) < 0) 
              v++;
          while (c.compare(skilleverdi,a[h]) <= 0) 
              h--;
        }
    }

    public static <T> int parter(T[] a, T skilleverdi, Comparator<? super T> c) {
        return parter(a,0,a.length-1,skilleverdi,c); 
    }

    public static <T> int sParter(T[] a, int v, int h, int k, Comparator<? super T> c) {
        if (v < 0 || h >= a.length || k < v || k > h) 
            throw new IllegalArgumentException("Ulovlig parameterverdi");

        bytt(a, k, h);  
        int p = parter(a, v, h-1, a[h], c);  
        bytt(a, p, h); 

        return p;   
    }

    public static <T> int sParter(T[] a, int k, Comparator<? super T> c) {
        return sParter(a, 0, a.length-1, k, c); 
    }

    private static <T> void kvikksortering(T[] a, int v, int h, Comparator<? super T> c) {
        if (v >= h) 
            return;  

        int p = sParter(a, v, h, (v + h)/2, c);
        kvikksortering(a, v, p-1, c);
        kvikksortering(a, p+1, h, c);
    }

    public static <T> void kvikksortering(T[] a, Comparator<? super T> c) {
        kvikksortering(a, 0, a.length-1, c);
    }
    
    private static <T> void flett(T[] a, T[] b, int fra, int m, int til, Comparator<? super T> c) {
        int n = m - fra;  
        System.arraycopy(a,fra,b,0,n); 

        int i = 0, j = m, k = fra;   

        while (i < n && j < til)  
            a[k++] = c.compare(b[i], a[j]) <= 0 ? b[i++] : a[j++];  

        while (i < n) 
            a[k++] = b[i++]; 
    }

    public static <T> void flettesortering(T[] a, T[] b, int fra, int til, Comparator<? super T> c) {
        if (til - fra <= 1) 
            return;     

        int m = (fra + til)/2;         

        flettesortering(a, b, fra, m, c);  
        flettesortering(a, b, m, til, c);   

        flett(a, b, fra, m, til, c);        
    }

    public static <T> void flettesortering(T[] a, Comparator<? super T> c) {
        T[] b = Arrays.copyOf(a, a.length/2);
        flettesortering(a, b, 0, a.length, c); 
    } 

}
