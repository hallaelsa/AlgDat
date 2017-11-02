/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;

public class TabellKø<T> implements Kø<T> {
    private T[] a;      
    private int fra;    
    private int til;     

    private T[] utvidTabell(int lengde) {
        T[] b = (T[])new Object[lengde];   
        System.arraycopy(a,fra,b,0,a.length - fra);
        System.arraycopy(a,0,b,a.length - fra, fra);
        fra = 0; til = a.length;
        return b;
    }

    public TabellKø(int lengde) {
        if (lengde < 1)
          throw new IllegalArgumentException("Må ha positiv lengde!");

        a = (T[])new Object[lengde];
        fra = til = 0; 
    }

    public TabellKø() {
        this(8);
    }

    public boolean leggInn(T verdi) {
        a[til] = verdi;                                  
        til++;                                         
        if (til == a.length) 
            til = 0;                   
        if (fra == til) 
            a = utvidTabell(2*a.length);  
        return true;                                     
    }

    public T kikk() {
        if (fra == til)
          throw new NoSuchElementException("Køen er tom!");

        return a[fra];
    }

    public T taUt() {
        if (fra == til) throw new          
          NoSuchElementException("Køen er tom!");

        T temp = a[fra];                   
        a[fra] = null;                    
        fra++;                             
        if (fra == a.length) 
            fra = 0;     
        return temp;                       
    }

    public int antall() {
        return fra <= til ? til - fra : a.length + til - fra;
    }

    public boolean tom() {
        return til == fra;
    }

    public void nullstill() {
        while (fra != til) {
            a[fra++] = null;
            if (fra == a.length) fra = 0;
        }
    }

    public String toString() {
        if (til == fra) return "[]";

        int sfra = fra, stil = til;

        StringBuilder s = new StringBuilder();
        s.append('[').append(a[sfra]);
        sfra++;
        if (sfra == a.length) sfra = 0;

        while (sfra != stil) {
            s.append(',').append(' ').append(a[sfra]);
            sfra++;
            if (sfra == a.length) 
                sfra = 0;
        }

        s.append(']');

        return s.toString();
    }
    
    public int indeksTil(T verdi) {
        int index = fra;

        while (index != til) {
            if (verdi.equals(a[index])) {
                if(fra <= index)
                    return (index - fra);
                else
                    return (a.length + index - fra);
            }
                
            index++; 

            if (index == a.length) 
                index = 0;
        }
        
        return -1;  
    }
    
    public static <T> void snu(Stakk<T> A) {
        Kø<T> B = new TabellKø<T>();
        while (!A.tom()) 
            B.leggInn(A.taUt());
        while (!B.tom()) 
            A.leggInn(B.taUt());
    }
    
    public static <T> void snu(Kø<T> A) {
        
    }

} 
