/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;

public class HeapPrioritetsKø<T> implements PrioritetsKø<T> {
    private T[] heap;                          
    private int antall;                        
    private Comparator<? super T> comp;        

    @SuppressWarnings("unchecked")
    public HeapPrioritetsKø(int kapasitet, Comparator<? super T> c) {
        if (kapasitet < 0) 
            throw new IllegalArgumentException("Negativ kapasitet!");

        heap = (T[])new Object[kapasitet + 1];   
        antall = 0;
        comp = c;
    }

    public HeapPrioritetsKø(Comparator<? super T> c) {
        this(8, c); 
    }

    public static <T extends Comparable<? super T>> HeapPrioritetsKø<T> naturligOrden(int kapasitet) {
        return new HeapPrioritetsKø<>(kapasitet, Comparator.naturalOrder());
    }

    public static <T extends Comparable<? super T>> HeapPrioritetsKø<T> naturligOrden() {
        return naturligOrden(8);
    }

    @Override
    public T kikk() {
        if (tom()) 
            throw new NoSuchElementException("Køen er tom!");
        
        return heap[1];
    }

    @Override
    public void nullstill() {
        for (int i = 0; i <= antall; i++) 
            heap[i] = null;
        
        antall = 0;
    }

    @Override
    public void leggInn(T verdi) {
        Objects.requireNonNull(verdi, "verdi er null!");

        if (++antall == heap.length) 
            heap = Arrays.copyOf(heap, 2*antall);

        int k = antall;                    
        heap[0] = verdi;                    

        while (comp.compare(verdi, heap[k/2]) < 0) {
            heap[k] = heap[k/2];              
            k /= 2;                           
        }
        
        heap[0] = null;                 
        heap[k] = verdi;                    
    }

    @Override
    public T taUt() {
        if (tom()) 
            throw new NoSuchElementException("Køen er tom!");

        T min = heap[1];                   
        T verdi = heap[antall];       
        heap[antall] = null;               
        antall--;   
        int k = 1; 
        
        while ((k << 1) < antall) {
            k <<= 1;                        

            if (comp.compare(heap[k + 1], heap[k]) < 0) 
                k++;

            heap[k >>> 1] = heap[k];         
        }

        if (2*k == antall) {
            k *= 2;                       
            heap[k/2] = heap[k];             
        }

        heap[0] = verdi;            

        while (comp.compare(verdi, heap[k/2]) < 0) {
            heap[k] = heap[k/2];            
            k /= 2;                           
        }
        
        heap[k] = verdi;     
        heap[0] = null;                     
        return min;                         
    }

    @Override
    public boolean taUt(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall == 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if (antall > 0) 
            s.append(heap[1]); 

        for (int i = 2; i <= antall; ++i) {
            s.append(',').append(' ').append(heap[i]);
        }

        s.append(']');

        return s.toString();
    }

    public String minimumsGrenen() {
        StringBuilder s = new StringBuilder();
        s.append('[');

        if (antall > 0) 
            s.append(heap[1]);   

        int k = 1;
        while (2*k < antall) {
            k *= 2;   
            if (comp.compare(heap[k+1], heap[k]) < 0) 
                k++;  
            s.append(',').append(' ').append(heap[k]);     
        }

        if (2*k == antall) {
            s.append(',').append(' ').append(heap[2*k]);
        }

        s.append(']');

        return s.toString();
    }

}  