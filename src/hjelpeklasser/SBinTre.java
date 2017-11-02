/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.Comparator;
import java.util.NoSuchElementException;
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
    
    public boolean inneholder(T verdi) {
        if (verdi == null) 
            return false;          

        Node<T> p = rot; 
        Node<T> q = null;
        
        while (p != null) {
            if(comp.compare(verdi, p.verdi) < 0) {
                p = p.venstre;
            } else {
                q = p;
                p = p.høyre;
            }                         
        }
        
        return q == null ? false : (comp.compare(verdi, p.verdi) == 0);                                
    }
    
    public int antall(T verdi) {
        Node<T> node = rot;
        int antallVerdier = 0;
        
        while(node != null) {
            if(comp.compare(verdi, node.verdi) < 0) {
                node = node.venstre;
            } else {
                if(comp.compare(verdi, node.verdi) == 0) {
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
        
        while(node != null) {
            int cmp = comp.compare(fraverdi, node.verdi);
            if(cmp < 0) {
                stakk.leggInn(node);
                node = node.venstre;
            } else if(cmp > 0) {
                node = node.høyre;
            } else {
                break;
            }
        }
        
        if(node == null)
            node = stakk.taUt();
        
        Liste liste = new TabellListe<>();
        
        while(node != null && comp.compare(node.verdi, tilverdi) < 0) {
            liste.leggInn(node.verdi);
            
            if(node.høyre != null) {
                node = node.høyre;
                
                while(node.venstre != null) {
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
        if(tom()) 
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> node =  rot; 
        
        while (node.venstre != null) 
            node = node.venstre; 
        
        return node.verdi;                           
    }
    
    public T maks() {
        if(tom())
            throw new NoSuchElementException("Treet er tomt");
        
        Node<T> node = rot;
        Node<T> node2 = rot;
        
        while(node.høyre != null) {
            node = node.høyre;
            if(comp.compare(node.verdi, node2.verdi) > 0)
                node2 = node;
        }
        
        return node2.verdi;
    }
    
    public T gulv(T verdi) {
        Objects.requireNonNull(verdi, "Treet har ingen nullverdier!");
        if (tom()) 
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot; 
        T gulv = null;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);

            if (cmp < 0) 
                p = p.venstre;  
            else {
                gulv = p.verdi;           
                p = p.høyre;
            } 
        }
        return gulv;
    }
    
    public T tak(T verdi) {
        Objects.requireNonNull(verdi, "Nullverdier ikke tillatt");
        if(tom())
            throw new NoSuchElementException("Treet er tomt");
        
        Node<T> node = rot;
        T tak = null;
        
        while(node != null) {
            int cmp = comp.compare(verdi, node.verdi);
            
            if(cmp > 0) {
                node = node.høyre;
            } else {
                tak = node.verdi;
                node = node.venstre;
            }
        }
        
        return tak;
    }
    
    public T større(T verdi) {
        if (tom()) 
            throw new NoSuchElementException("Treet er tomt!");
        if (verdi == null) 
            throw new NullPointerException("Ulovlig nullverdi!");

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
        if (tom()) 
            throw new NoSuchElementException("Treet er tomt!");
        if (verdi == null) 
            throw new NullPointerException("Ulovlig nullverdi!");
        
        Node<T> node = rot;
        T mindre = null;
        
        while(node != null) {
            int cmp = comp.compare(verdi, node.verdi);
            
            if(cmp <= 0) {
                node = node.venstre;
            } else {
                mindre = node.verdi;
                node = node.høyre;
            }
        }
        
        return mindre;
    }
    
    
} 

