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
            utvid();
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
    
    private void utvid() {
        @SuppressWarnings({"rawtypes","unchecked"})      
        Node<T>[] nyhash = new Node[2*hash.length + 1];   

        for (int i = 0; i < hash.length; i++) {
            Node<T> p = hash[i];                           

            while (p != null) {
                Node<T> q = p.neste;                          
                int nyindeks = p.hashverdi % nyhash.length;   

                p.neste = nyhash[nyindeks];                  

                nyhash[nyindeks] = p;
                p = q;                                      
            }

            hash[i] = null;                                
        }

        hash = nyhash;                                  
        grense = (int)(tetthet * hash.length);           
    }
    
    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) 
            return false;        
        
        int hashverdi = verdi.hashCode() & 0x7fffffff;  
        Node<T> p = hash[hashverdi % hash.length];      

        while (p != null) {
            if (verdi.equals(p.verdi)) 
                return true;
            p = p.neste;
        }

        return false;
        
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) 
            return false;                
        int hashverdi = verdi.hashCode() & 0x7fffffff;     
        int indeks = hashverdi % hash.length;              

        Node<T> p = hash[indeks];
        Node<T> q = null;               

        while (p != null) {
          if (verdi.equals(p.verdi)) 
              break;               
          p = (q = p).neste;                               
        }

        if (p == null) {
            return false;
        } else {
            if (p == hash[indeks]) {
                hash[indeks] = p.neste;
            } else {
                q.neste = p.neste;
            }   
        }

        antall--;                                          
        return true;   
    }

    @Override
    public void nullstill() {
        if (antall > 0) {
            antall = 0;
            for (int i = 0; i < hash.length; i++) {
                hash[i] = null;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
      throw new UnsupportedOperationException();
    }

}
