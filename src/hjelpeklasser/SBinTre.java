/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Comparator;
import java.util.Objects;
import java.util.stream.Stream;

public class SBinTre<T> {
    private static final class Node<T> {
        private T verdi;               
        private Node<T> venstre;
        private Node<T> høyre; 

        private Node(T verdi, Node<T> v, Node<T> h) {
            this.verdi = verdi; 
            venstre = v; 
            høyre = h;
        }

        private Node(T verdi) {
            this(verdi, null, null);
        }
    } 

    private Node<T> rot;                     
    private int antall;                        
    private final Comparator<? super T> comp;  

    public SBinTre(Comparator<? super T> c) {
        rot = null; 
        antall = 0; 
        comp = c;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre() {
        return new SBinTre<>(Comparator.naturalOrder());
    }

    public static <T> SBinTre<T> sbintre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }
    
    public static <T> SBinTre<T> sbintre(Stream<T> s, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);             
        s.forEach(tre::leggInn);                       
        return tre;                                    
    }

    public static <T extends Comparable<? super T>> SBinTre<T> sbintre(Stream<T> s) {
        return sbintre(s, Comparator.naturalOrder());  
    }
    
    public static <T> SBinTre<T> komparatorTre(Comparator<? super T> c) {
        return new SBinTre<>(c);
    }
    
    public static <T> SBinTre<T> komparatorTre(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);          
        for (T verdi : a) 
            tre.leggInn(verdi);       
        return tre;                                 
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre(T[] a) {
        return komparatorTre(a, Comparator.naturalOrder());  
    }

    public int antall() {
        return antall;
    }

    public boolean tom() {
        return antall == 0;
    }

    public void nullstill() {
        rot = null; antall = 0;
    }
    
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot;
        Node<T> q = null;              
        int cmp = 0;                            

        while (p != null) {
            q = p;                                 
            cmp = comp.compare(verdi,p.verdi);     
            p = cmp < 0 ? p.venstre : p.høyre;     
        }

        p = new Node<>(verdi);                  

        if (q == null) 
            rot = p;                 
        else if (cmp < 0) 
            q.venstre = p;         
        else 
            q.høyre = p;                        

        antall++;                               
        return true;                             
    }
    
    private static int høyde(Node<?> p) {
        if (p == null) 
            return -1;         

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);               
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();   
        s.append('[');                           
        if (!tom()) 
            toString(rot,s);             
        s.append(']');                           
        return s.toString();                     
    }

    private static <T> void toString(Node<T> p, StringBuilder s) {
        if (p.venstre != null) {
            toString(p.venstre, s);     //rekursiv             
            s.append(',').append(' ');             
        }                                        

        s.append(p.verdi);                      

        if (p.høyre != null) {
            s.append(',').append(' ');            
            toString(p.høyre, s);       //rekursiv         
        }                                        
    }
    
    private static <T> Node<T> balansert(T[] a, int v, int h) {
        if (v > h) 
            return null;                       

        int m = (v + h)/2;                            
        T verdi = a[m];                               

        while (v < m && verdi.equals(a[m-1])) 
            m--;    

        Node<T> p = balansert(a, v, m - 1);           
        Node<T> q = balansert(a, m + 1, h);          

        return new Node<>(verdi, p, q);               
    }
    
    public static <T> SBinTre<T> balansert(T[] a, Comparator<? super T> c) {
        SBinTre<T> tre = new SBinTre<>(c);         
        tre.rot = balansert(a, 0, a.length - 1);    
        tre.antall = a.length;                      
        return tre;                                 
    }

    public static <T extends Comparable<? super T>> SBinTre<T> balansert(T[] a) {
        return balansert(a, Comparator.naturalOrder());
    }
} 

