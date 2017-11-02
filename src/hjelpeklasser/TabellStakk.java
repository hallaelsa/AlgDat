/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;

public class TabellStakk<T> implements Stakk<T> {
    private T[] a;              
    private int antall;          

    public TabellStakk() {
        this(8);
    }

    @SuppressWarnings("unchecked")    
    public TabellStakk(int lengde) {
        if (lengde < 0)
          throw new IllegalArgumentException("Negativ tabellengde!");

        a = (T[])new Object[lengde];      
        antall = 0;                      
    }

    @Override
    public void leggInn(T verdi) {
        if (antall == a.length)
            a = Arrays.copyOf(a, antall == 0 ? 1 : 2*antall);   

        a[antall++] = verdi;
    }

    @Override
    public T kikk() {
        if (antall == 0)       
            throw new NoSuchElementException("Stakken er tom!");

        return a[antall-1];   
    }

    @Override
    public T taUt() {
        if (antall == 0)       
            throw new NoSuchElementException("Stakken er tom!");
        
        antall--;              

        T temp = a[antall];   
        a[antall] = null;     

        return temp;           
    }
    
    @Override
    public boolean tom() { 
        return antall == 0; 
    }

    @Override
    public int antall() { 
        return antall; 
    }

    @Override
    public void nullstill() {
        while(antall != 0) {
            a[--antall] = null;
        }    
    }
    
    @Override
    public String toString() {
        if(antall == 0)
            return"[]";
        
        StringBuilder str = new StringBuilder();
        str.append("[").append(a[antall-1]);
        
        for (int i = antall-2; i > 0; i++) {
            str.append(", ").append(a[i]);
        }
        str.append("]");
        
        return str.toString();
    }
    
    public static <T> void snu(Stakk<T> A) {
        Stakk<T> B = new TabellStakk<T>();
        Stakk<T> C = new TabellStakk<T>();
        
        while(!A.tom())
            B.leggInn(A.taUt());
        while(!B.tom())
            C.leggInn(B.taUt());
        while(!C.tom())
            A.leggInn(C.taUt());
    }
    
    public static <T> void kopier(Stakk<T> A, Stakk<T> B) {
        Stakk<T> C = new TabellStakk<T>();
        while(!A.tom())
            C.leggInn(A.taUt());
        while(!C.tom()) {
            T temp = C.taUt();
            A.leggInn(temp);
            B.leggInn(temp);
        }
    }
    
    public static <T> void sorter(Stakk<T> A, Comparator<? super T> c) {
        Stakk<T> hjelpeStakk = new TabellStakk<>();
        T temp;
        int n = 0;
        
        while(!A.tom()) {
            temp = A.taUt();
            n = 0;
            while(!hjelpeStakk.tom() && c.compare(temp, hjelpeStakk.kikk()) < 0) {
                n++;
                A.leggInn(hjelpeStakk.taUt());
            }
            hjelpeStakk.leggInn(temp);
            for (int i = 0; i < n; i++) {
                hjelpeStakk.leggInn(A.taUt());
            }
        }
        
        while(!hjelpeStakk.tom())
            A.leggInn(hjelpeStakk.taUt());
    }

  }
