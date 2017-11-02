/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TabellListe<T> implements Liste<T> {
    private T[] a;
    private int antall;
    private int endringer;

    @SuppressWarnings("unchecked")          
    public TabellListe(int størrelse) {
        a = (T[])new Object[størrelse];      
        antall = 0;   
        
    }
    
    public TabellListe() {
        this(10);                          
    }
    

    public TabellListe(T[] b) {
        this(b.length);                          

        for (T verdi : b) {
            if (verdi != null) a[antall++] = verdi;  
        }
    }
    
    public int antall() {
        return antall;       
    }

    public boolean tom() {
        return antall == 0;    
    }
    
    public T hent(int indeks) {
        indeksKontroll(indeks, false);  
        return a[indeks];                
    }
    
    public int indeksTil(T verdi) {
        for (int i = 0; i < antall; i++) {
            if (a[i].equals(verdi)) return i;  
        }
        return -1;  
    }

    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");

        if (antall == a.length) {
            a = Arrays.copyOf(a,(3*antall)/2 + 1);
        }

        a[antall++] = verdi; 
        endringer++;
        return true;            
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, true);  

        if (antall == a.length) a = Arrays.copyOf(a,(3*antall)/2 + 1);

        System.arraycopy(a, indeks, a, indeks + 1, antall - indeks);

        a[indeks] = verdi;     
        antall++;   
        endringer++;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "null er ulovlig!");
        indeksKontroll(indeks, false);  

        T gammelverdi = a[indeks];     
        a[indeks] = verdi;  
        endringer++;
        return gammelverdi;   
    }

    @Override
    public boolean fjern(T verdi) {
        for (int i = 0; i < antall; i++) {
            if(a[i].equals(verdi)) {
                antall--;
                System.arraycopy(a, i + 1, a, i, antall - i);
                a[antall] = null;
                endringer++;
                
                return true;
            }
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false); 
        T verdi = a[indeks];

        antall--; 
        System.arraycopy(a, indeks + 1, a, indeks, antall - indeks);
        a[antall] = null;  
        endringer++;
        
        return verdi;
    }

    @Override
    public void nullstill() {
        if(a.length > 10) {
            a = (T[])new Object[10];
        } else {
            for (int i = 0; i < a.length; i++) {
                a[i] = null;
            }
        }
        
        antall = 0;
        endringer++;
    }

    @Override
    public Iterator<T> iterator() {
        return new TabellListeIterator();
    }
    
    public String toString() {
        if(antall == 0)
            return "[]";
        
        StringBuilder str = new StringBuilder();
        str.append("[").append(a[0]);
        
        for (int i = 1; i < antall; i++) {
            str.append(", ").append(a[i]);
        }
        str.append("]");
        return str.toString();
    }
    
    
    private class TabellListeIterator implements Iterator<T> {
        private int denne = 0;   
        private boolean fjernOK = false; 
        private int iteratorendringer = endringer;

        public boolean hasNext() {
            return denne < antall;   
        }

        public T next() {
            if (iteratorendringer != endringer) {
              throw new ConcurrentModificationException("Listen er endret!");
            }
            
            if (!hasNext())
                throw new NoSuchElementException("Tomt eller ingen verdier igjen!");
            
            T denneVerdi = a[denne]; 
            denne++;
            fjernOK = true;
            return denneVerdi;
        }
        
        public void remove() {
            if (iteratorendringer != endringer) 
                throw new ConcurrentModificationException("Listen er endret!");
            
            if (!fjernOK) 
                throw new IllegalStateException("Ulovlig tilstand!");

            fjernOK = false;         

            antall--;           
            denne--;            

            System.arraycopy(a, denne + 1, a, denne, antall - denne);  
            a[antall] = null;  
            endringer++;
            iteratorendringer++;
        } 
        
        @Override
        public void forEachRemaining(Consumer<? super T> action) {
            while (denne < antall) {
                action.accept(a[denne++]);
            }
        }
        
    }
    
    public boolean fjernHvis(Predicate<? super T> p) {
        Objects.requireNonNull(p);
        
        int nyttAntall = antall;
        
        for (int i = 0, j = 0; j < antall; j++) {
            if(p.test(a[j]))
                nyttAntall--;
            else
                a[i++] = a[j];
        }
        
        for (int i = nyttAntall; i < antall; i++) {
            a[i] = null;  
        }

        boolean fjernet = nyttAntall < antall;

        if(fjernet)
            endringer++;
        
        antall = nyttAntall;
        return fjernet;
    } 
    
    
    @Override
    public void forEach(Consumer<? super T> action) {
        for (int i = 0; i < antall; i++) {
            action.accept(a[i]);
        }
    }
    

    
}
