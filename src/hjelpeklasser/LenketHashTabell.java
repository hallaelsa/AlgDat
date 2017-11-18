/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

public class LenketHashTabell<T> implements Beholder<T> {
    private static class Node<T> {
        private final T verdi;          
        private final int hashverdi;     
        private Node<T> neste;          

        private Node(T verdi, int hashverdi, Node<T> neste) {
            this.verdi = verdi;
            this.hashverdi = hashverdi;
            this.neste = neste;
        }
    }  

    private Node<T>[] hash;           
    private final float tetthet;      
    private int grense;               
    private int antall;      
    
    @SuppressWarnings({"rawtypes","unchecked"}) 
    public LenketHashTabell(int dimensjon) {
        if (dimensjon < 0) throw new IllegalArgumentException("Negativ dimensjon!");

        hash = new Node[dimensjon];                // bruker raw type
        tetthet = 0.75f;                           // maksimalt 75% full
        grense = (int)(tetthet * hash.length);     // gjør om til int
        antall = 0;   
    }

    public LenketHashTabell() {
        this(13);  
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }
    
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "verdi er null!");

        if (antall >= grense) {
          // her skal metoden utvid() kalles, men det tas opp senere
        }

        int hashverdi = verdi.hashCode() & 0x7fffffff;  // fjerner fortegn
        int indeks = hashverdi % hash.length;           // finner indeksen

        // legger inn først i listen som hører til indeks
        hash[indeks] = new Node<>(verdi, hashverdi, hash[indeks]);  // lagrer hashverdi

        antall++;        // en ny verdi
        return true;     // vellykket innlegging
    }
    
    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");

        for (Node<T> p : hash) {
            for (; p != null; p = p.neste) {
                s.add(p.verdi.toString());
            }
        }
        
        return s.toString();
    }
    
    @Override
    public boolean inneholder(T verdi) {
      throw new UnsupportedOperationException();
    }

    @Override
    public boolean fjern(T verdi) {
      throw new UnsupportedOperationException();
    }

    @Override
    public void nullstill() {
      throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
      throw new UnsupportedOperationException();
    }

}
