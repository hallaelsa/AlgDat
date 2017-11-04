/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Stream;

public class SBinTre<T> implements Beholder<T>{

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
    private int endringer;   

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
        for (T verdi : a) {
            tre.leggInn(verdi);
        }
        return tre;
    }

    public static <T extends Comparable<? super T>> SBinTre<T> naturligOrdenTre(T[] a) {
        return komparatorTre(a, Comparator.naturalOrder());
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
    public void nullstill() {
        rot = null;
        antall = 0;
        endringer++;
    }

    /*
    ////// Annen metode
    
    public void nullstill() {
        if (!tom()) 
            nullstill(rot);   
        rot = null; 
        antall = 0;      
    }

    private void nullstill(Node<T> p) {
        if (p.venstre != null) {
            nullstill(p.venstre);       
            p.venstre = null;           
        }
        if (p.høyre != null) {
            nullstill(p.høyre);        
            p.høyre = null;            
        }
        p.verdi = null;              
    }
    
     */
    
    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ulovlig med nullverdier!");

        Node<T> p = rot;
        Node<T> q = null;
        int cmp = 0;

        while (p != null) {
            q = p;
            cmp = comp.compare(verdi, p.verdi);
            p = cmp < 0 ? p.venstre : p.høyre;
        }

        p = new Node<>(verdi);

        if (q == null) {
            rot = p;
        } else if (cmp < 0) {
            q.venstre = p;
        } else {
            q.høyre = p;
        }

        endringer++;
        antall++;
        return true;
    }

    private static int høyde(Node<?> p) {
        if (p == null) {
            return -1;
        }

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append('[');
        if (!tom()) {
            toString(rot, s);
        }
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
        if (v > h) {
            return null;
        }

        int m = (v + h) / 2;
        T verdi = a[m];

        while (v < m && verdi.equals(a[m - 1])) {
            m--;
        }

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

    @Override
    public boolean inneholder(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> node = rot;
        Node<T> parent = null;

        while (node != null) {
            if (comp.compare(verdi, node.verdi) < 0) {
                node = node.venstre;
            } else {
                parent = node;
                node = node.høyre;
            }
        }

        return parent == null ? false : (comp.compare(verdi, node.verdi) == 0);
    }

    public int antall(T verdi) {
        Node<T> node = rot;
        int antallVerdier = 0;

        while (node != null) {
            if (comp.compare(verdi, node.verdi) < 0) {
                node = node.venstre;
            } else {
                if (comp.compare(verdi, node.verdi) == 0) {
                    antallVerdier++;
                }
                node = node.høyre;
            }
        }

        return antallVerdier;
    }

    public Liste<T> intervallsøk(T fraverdi, T tilverdi) {
        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> node = rot;

        while (node != null) {
            int cmp = comp.compare(fraverdi, node.verdi);
            if (cmp < 0) {
                stakk.leggInn(node);
                node = node.venstre;
            } else if (cmp > 0) {
                node = node.høyre;
            } else {
                break;
            }
        }

        if (node == null) {
            node = stakk.taUt();
        }

        Liste liste = new TabellListe<>();

        while (node != null && comp.compare(node.verdi, tilverdi) < 0) {
            liste.leggInn(node.verdi);

            if (node.høyre != null) {
                node = node.høyre;

                while (node.venstre != null) {
                    stakk.leggInn(node);
                    node = node.venstre;
                }
            } else if (!stakk.tom()) {
                node = stakk.taUt();
            } else {
                node = null;
            }
        }

        return liste;
    }

    public T min() {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }

        Node<T> node = rot;

        while (node.venstre != null) {
            node = node.venstre;
        }

        return node.verdi;
    }

    public T maks() {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt");
        }

        Node<T> node = rot;
        Node<T> node2 = rot;

        while (node.høyre != null) {
            node = node.høyre;
            if (comp.compare(node.verdi, node2.verdi) > 0) {
                node2 = node;
            }
        }

        return node2.verdi;
    }

    public T gulv(T verdi) {
        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }

        Node<T> p = rot;
        T gulv = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) {
                p = p.venstre;
            } else {
                gulv = p.verdi;
                p = p.høyre;
            }
        }
        return gulv;
    }

    public T tak(T verdi) {
        Objects.requireNonNull(verdi, "Nullverdier ikke tillatt");
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt");
        }

        Node<T> node = rot;
        T tak = null;

        while (node != null) {
            int cmp = comp.compare(verdi, node.verdi);

            if (cmp > 0) {
                node = node.høyre;
            } else {
                tak = node.verdi;
                node = node.venstre;
            }
        }

        return tak;
    }

    public T større(T verdi) {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }
        if (verdi == null) {
            throw new NullPointerException("Ulovlig nullverdi!");
        }

        Node<T> p = rot;
        T større = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) {
                større = p.verdi;  // en kandidat
                p = p.venstre;
            } else {
                p = p.høyre;
            }
        }

        return større;
    }

    public T mindre(T verdi) {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }
        if (verdi == null) {
            throw new NullPointerException("Ulovlig nullverdi!");
        }

        Node<T> node = rot;
        T mindre = null;

        while (node != null) {
            int cmp = comp.compare(verdi, node.verdi);

            if (cmp <= 0) {
                node = node.venstre;
            } else {
                mindre = node.verdi;
                node = node.høyre;
            }
        }

        return mindre;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> node = rot;
        Node<T> parent = null;

        while (node != null) {
            int cmp = comp.compare(verdi, node.verdi);
            if (cmp < 0) {
                parent = node;
                node = node.venstre;
            } else if (cmp > 0) {
                parent = node;
                node = node.høyre;
            } else {
                break;
            }
        }

        if (node == null) {
            return false;
        }

        if (node.venstre == null || node.høyre == null) {
            Node<T> child = node.venstre != null ? node.venstre : node.høyre;
            if (node == rot) {
                rot = child;
            } else if (node == parent.venstre) {
                parent.venstre = child;
            } else {
                parent.høyre = child;
            }
        } else {
            Node<T> parentOfRemovable = node;
            Node<T> removable = node.høyre;

            while (removable.venstre != null) {
                parentOfRemovable = removable;
                removable = removable.venstre;
            }

            node.verdi = removable.verdi;

            if (parentOfRemovable != node) {
                parentOfRemovable.venstre = removable.høyre;
            } else {
                parentOfRemovable.høyre = removable.høyre;
            }
        }
        
        endringer++;
        antall--;
        return true;
    }

    public void fjernMin() {
        if (tom()) {
            throw new NoSuchElementException("Treet er tomt!");
        }

        if (rot.venstre == null) {
            rot = rot.høyre;
        } else {
            Node<T> node = rot.venstre;
            Node<T> parent = rot;

            while (node.venstre != null) {
                parent = node;
                node = node.venstre;
            }

            parent.venstre = node.høyre;
        }
        endringer++;
        antall--;
    }

    public int fjernAlle(T verdi) {
        int antallFjern = 0;

        while (fjern(verdi)) {
            antallFjern++;
        }

        return antallFjern;
    }

    public void fjernMaks() {
        if (tom()) {
            throw new NoSuchElementException("tomt tre");
        }

        if (rot.høyre == null) {
            rot = rot.venstre;
        } else {
            Node<T> node = rot.høyre;
            Node<T> parent = rot;

            while (node.høyre != null) {
                parent = node;
                node = node.høyre;
            }

            parent.høyre = node.venstre;
        }
        
        endringer++;
        antall--;
    }

    public int fjernAlleMaks() {
        if (tom()) {
            return 0;
        }

        int antallFjernet = 1;
        Node<T> node = rot;
        Node<T> parent = null;

        while (node.høyre != null) {
            if (comp.compare(node.høyre.verdi, node.verdi) > 0) {
                parent = node;
                antallFjernet = 1;
            } else {
                antallFjernet++;
            }

            node = node.høyre;
        }

        if (parent == null) {
            rot = rot.venstre;
        } else {
            parent.høyre = parent.høyre.venstre;
        }
        
        endringer++;
        antall -= antallFjernet;
        return antallFjernet;
    }
    
    @Override
    public Iterator<T> iterator() {
        return new InordenIterator();
    }

    private class InordenIterator implements Iterator<T> {

        private Stakk<Node<T>> stakk = new TabellStakk<>();
        private Node<T> node = null;
        private int iteratorendringer; 

        private Node<T> først(Node<T> firstNode) {
            while (firstNode.venstre != null) {
                stakk.leggInn(firstNode);
                firstNode = firstNode.venstre;
            }
            return firstNode;
        }

        private InordenIterator() {
            if (tom()) {
                return;
            }
            node = først(rot);
            iteratorendringer = endringer;
        }
        
        private InordenIterator(T verdi) {
            if (verdi == null)
                throw new IllegalArgumentException("nullverdi");
            
            Node<T> parent = rot;
            
            while(parent != null) {
                int cmp = comp.compare(verdi, parent.verdi);
                
                if(cmp < 0) {
                    stakk.leggInn(parent);
                    parent = parent.venstre;
                } else if (cmp > 0) {
                    parent = parent.høyre;
                } else {
                    break;
                }
            }
            
            if(parent != null) {
                node = parent;
            } else if (!stakk.tom()) {
                node = stakk.taUt();
            }
            
            iteratorendringer = endringer;
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer) {
                   throw new ConcurrentModificationException();
            }
            
            if (!hasNext()) {
                throw new NoSuchElementException("Ingen verdier!");
            }

            T verdi = node.verdi;

            if (node.høyre != null) {
                node = først(node.høyre);
            } else if (stakk.tom()) {
                node = null;
            } else {
                node = stakk.taUt();
            }

            return verdi;
        }

        @Override
        public boolean hasNext() {
            return node != null;
        }

    } 
    
    public Iterator<T> iterator(T verdi) {
        return new InordenIterator(verdi);
    }
    
    private class OmvendtInordenIterator implements Iterator<T>{
        private Stakk<Node<T>> stakk = new TabellStakk<>();
        private Node<T> node = null;
        private Node<T> parent = null;
        private int iteratorendringer;
        
        private Node<T> sist(Node<T> parent) {
            while(parent.høyre != null) {
                stakk.leggInn(parent);
                parent = parent.høyre;
            }
            
            return parent;
        }
        
        public OmvendtInordenIterator() {
            if(rot == null)
                return;
            
            node = sist(rot);
            iteratorendringer = endringer;
        }
        
        public OmvendtInordenIterator(T verdi) {
            if(verdi == null)
                throw new IllegalArgumentException("verdi er null");
            
            Node<T> parentNode = rot;
            
            while(parentNode != null) {
                int cmp = comp.compare(verdi, parentNode.verdi);
                
                if(cmp < 0) {
                    parentNode = parentNode.venstre;
                } else {
                    stakk.leggInn(parentNode);
                    parentNode = parentNode.høyre;
                }
            }
            
            if(!stakk.tom()) {
                node = stakk.taUt();
            }
            
            iteratorendringer = endringer;
        }
        
        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            if (iteratorendringer != endringer)
                throw new ConcurrentModificationException();

            if (!hasNext()) 
                throw new NoSuchElementException();

            T verdi = node.verdi;                
            parent = node;                          


            if (node.venstre != null) 
                node = sist(node.venstre);   
            else if (!stakk.tom()) 
                node = stakk.taUt();              
            else 
                node = null;                               

            return verdi;     
        }
        
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    
    }
    
    public Iterator<T> riterator() {
        return new OmvendtInordenIterator();
    }
    
    public Iterator<T> riterator(T verdi) {
        return new OmvendtInordenIterator(verdi);
    }

}
