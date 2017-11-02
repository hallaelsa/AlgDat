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
import java.util.Random;
import java.util.StringJoiner;

public class BinTre<T> implements Iterable<T>{
    private static final class Node<T> {
        private T verdi;            
        private Node<T> venstre;    
        private Node<T> høyre;      

        private Node(T verdi, Node<T> v, Node<T> h) {
            this.verdi = verdi; venstre = v; høyre = h;
        }

        private Node(T verdi) { 
            this.verdi = verdi; 
        }  
    } 

    private Node<T> rot;     
    private int antall;   
    private int endringer;

    public BinTre() { 
        rot = null; 
        antall = 0; 
        endringer = 0;
    }      

    public BinTre(int[] posisjon, T[] verdi) {
        rot = null; 
        antall = 0; 
        endringer = 0;
        if (posisjon.length > verdi.length) 
            throw new IllegalArgumentException("Verditabellen har for få elementer!");

        for (int i = 0; i < posisjon.length; i++) 
            leggInn(posisjon[i],verdi[i]);
    }
      
    public final void leggInn(int posisjon, T verdi) {
        if (posisjon < 1) 
            throw new IllegalArgumentException("Posisjon (" + posisjon 
                    + ") < 1!");

        Node<T> p = rot, q = null;   

        int filter = Integer.highestOneBit(posisjon) >> 1;  

        while (p != null && filter > 0) {
            q = p;
            p = (posisjon & filter) == 0 ? p.venstre : p.høyre;
            filter >>= 1;  
        }

        if (filter > 0) throw new
          IllegalArgumentException("Posisjon (" + posisjon 
                  + ") mangler forelder!");
        else if (p != null) throw new
          IllegalArgumentException("Posisjon (" + posisjon 
                  + ") finnes fra før!");

        p = new Node<>(verdi);        

        if (q == null) 
            rot = p;        
        else if ((posisjon & 1) == 0)  
            q.venstre = p;               
        else
            q.høyre = p;                  

        antall++; 
        endringer++;
    } 

//    public int antall() { 
//        return antall; 
//    }               

    public boolean tom() { 
        return antall == 0; 
    }  
    
    private Node<T> finnNode(int posisjon) {
        if (posisjon < 1) return null;

        Node<T> p = rot;   
        int filter = Integer.highestOneBit(posisjon >> 1);  

        for (; p != null && filter > 0; filter >>= 1)
          p = (posisjon & filter) == 0 ? p.venstre : p.høyre;

        return p;  
    }

    public boolean finnes(int posisjon) {
        return finnNode(posisjon) != null;
    }

    public T hent(int posisjon) {
        Node<T> p = finnNode(posisjon);

        if (p == null) 
            throw new IllegalArgumentException("Posisjon (" + posisjon 
                    + ") finnes ikke i treet!");

        return p.verdi;
    }

    public T oppdater(int posisjon, T nyverdi) {
        Node<T> p = finnNode(posisjon);

        if (p == null) 
            throw new IllegalArgumentException("Posisjon (" + posisjon 
                    + ") finnes ikke i treet!");

        T gammelverdi = p.verdi;
        p.verdi = nyverdi;
        endringer++;

        return gammelverdi;
    }
    
    public T fjern(int posisjon) {
        if(posisjon < 0)
            throw new IllegalArgumentException("ugyldig posisjon");
        
        Node<T> denneNode = rot;
        Node<T> forrigeNode = null;
        
        int filter = Integer.highestOneBit(posisjon >> 1);
        
        while(denneNode != null) {
            forrigeNode = denneNode;
            denneNode = (filter & posisjon) == 0 ? forrigeNode.venstre : forrigeNode.høyre;
            filter >>= 1;
        }
        
        if(denneNode == null)
            throw new IllegalArgumentException("posisjonen er utenfor treet");
        
        if(denneNode.venstre != null || denneNode.høyre != null)
            throw new IllegalArgumentException("Posisjonen er ikke en bladnode");
        
        if(denneNode == rot) {
            rot = null;
        } else if(denneNode == forrigeNode.venstre) {
            forrigeNode.venstre = null;
        } else {
            forrigeNode.høyre = null;
        }
        
        antall--;
        endringer++;
        return denneNode.verdi;
    }
    
    public void nivåorden(Oppgave<? super T> oppgave) {
        if (tom()) return;                   
        Kø<Node<T>> kø = new TabellKø<>();   
        kø.leggInn(rot);                     

        while (!kø.tom()) {
            Node<T> p = kø.taUt();             
            oppgave.utførOppgave(p.verdi);     

            if (p.venstre != null) 
                kø.leggInn(p.venstre);
            if (p.høyre != null) 
                kø.leggInn(p.høyre);
        }
    } 
    
    public int[] nivåer() {
        if (tom()) 
            return new int[0];       

        int[] a = new int[8];                
        Kø<Node<T>> kø = new TabellKø<>();  
        int nivå = 0;                      

        kø.leggInn(rot);    
        
        while (!kø.tom()) {
            if (nivå == a.length) 
                a = Arrays.copyOf(a,2*nivå);

            int k = a[nivå] = kø.antall();

            for (int i = 0; i < k; i++) {
                Node<T> p = kø.taUt();

                if (p.venstre != null) kø.leggInn(p.venstre);
                if (p.høyre != null) kø.leggInn(p.høyre);
            }

            nivå++;  // fortsetter på neste nivå
        }
      return Arrays.copyOf(a, nivå);  // fjerner det overflødige
    }
    
    private static <T> void preorden(Node<T> p, Oppgave<? super T> oppgave) {
        while (true) {
            oppgave.utførOppgave(p.verdi);
            if (p.venstre != null) 
                preorden(p.venstre,oppgave);
            if (p.høyre == null) 
                return;      
            p = p.høyre;
        }
    }

    public void preorden(Oppgave<? super T> oppgave) {
        if (!tom()) preorden(rot,oppgave);  
    }
    
    private static <T> void inorden(Node<T> p, Oppgave<? super T> oppgave) {
        while (true) {
            if (p.venstre != null) inorden(p.venstre,oppgave);
            oppgave.utførOppgave(p.verdi);
            if (p.høyre == null) return;      
            p = p.høyre;
        }
    }

    public void inorden(Oppgave <? super T> oppgave) {
        if (!tom()) 
            inorden(rot,oppgave);
    }   
    
    @Override
    public String toString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        if (!tom()) 
            inorden(x -> s.add(x != null ? x.toString() : "null"));
        return s.toString();
    }
    
    public T førstInorden() {
        if (tom()) 
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.venstre != null) 
            p = p.venstre;

        return p.verdi;
    }
    
    public T førstPostorden() {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (true) {
            if (p.venstre != null) 
                p = p.venstre;
            else if (p.høyre != null) 
                p = p.høyre;
            else 
                return p.verdi;
        }
    }
    
    private static <T> Node<T> trePreorden(T[] preorden, 
            int rot, T[] inorden, int v, int h) {
        if (v > h) 
            return null;  
        int k = v; 
        T verdi = preorden[rot];
        while (!verdi.equals(inorden[k])) 
            k++;  

        Node<T> venstre = trePreorden(preorden, rot 
                + 1, inorden, v, k - 1);
        Node<T> høyre   = trePreorden(preorden, rot 
                + 1 + k - v, inorden, k + 1, h);

        return new Node<>(verdi, venstre, høyre);
    }
    
    public static <T> BinTre<T> trePreorden(T[] preorden, T[] inorden) {
        BinTre<T> tre = new BinTre<>();
        tre.rot = trePreorden(preorden, 0, inorden, 0, inorden.length - 1);

        tre.antall = preorden.length;
        return tre;
    }
    
    private static <T> void postorden(Node<T> p, Oppgave<? super T> oppgave) {
        if (p.venstre != null) 
            postorden(p.venstre,oppgave);
        if (p.høyre != null) 
            postorden(p.høyre,oppgave);
        oppgave.utførOppgave(p.verdi);
    }

    public void postorden(Oppgave<? super T> oppgave) {
        if (rot != null) 
            postorden(rot,oppgave);
    }
    
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
    
    public T sistInorden() {
        if (tom()) 
            throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (p.høyre != null) 
            p = p.høyre;

        return p.verdi;
    }
    
    public T sistPreorden() {
        if (tom()) throw new NoSuchElementException("Treet er tomt!");

        Node<T> p = rot;
        while (true) {
            if (p.høyre != null) 
                p = p.høyre;
            else if (p.venstre != null) 
                p = p.venstre;
            else 
                return p.verdi;
        }
    }
    
    public void preorden2(Oppgave<? super T> oppgave) {
        if (tom()) 
            return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;  

        while (true) {
            oppgave.utførOppgave(p.verdi);

            if (p.venstre != null) {
                if (p.høyre != null) 
                    stakk.leggInn(p.høyre);
                p = p.venstre;
            } else if (p.høyre != null) {
                p = p.høyre;
            } else if (!stakk.tom()) {
                p = stakk.taUt();
            } else {
                break;    
            }              
        }
    }
    
    public void inorden2(Oppgave<? super T> oppgave) {
        if (tom()) 
            return;        

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;   
        
        while (p.venstre != null) {
            stakk.leggInn(p);
            p = p.venstre;
        }

        while (true) {
            oppgave.utførOppgave(p.verdi);     

            if (p.høyre != null) {
                for (p = p.høyre; p.venstre != null; p = p.venstre) {
                    stakk.leggInn(p);
                }
            } else if (!stakk.tom()) {
                p = stakk.taUt();  
            } else break;         

        }
    }
   
    public void postorden2(Oppgave<? super T> oppgave) {
        if (tom()) 
            return;

        Stakk<Node<T>> stakk = new TabellStakk<>();
        Node<T> p = rot;   
        
        while (p != null) {
            if(p.høyre != null) {
                stakk.leggInn(p.høyre);
            }
            
            stakk.leggInn(p);
            p = p.venstre;
        } 
        
        while (!stakk.tom()) {
            p = stakk.taUt();
            if(!stakk.tom() && p.høyre != null && p.høyre == stakk.kikk()) {
                stakk.taUt();
                stakk.leggInn(p);
                p = p.høyre;
            } else {
                oppgave.utførOppgave(p.verdi);
                p = null;
            }  
            
            while (p != null) {
                if(p.høyre != null) {
                    stakk.leggInn(p.høyre);
                }
                stakk.leggInn(p);
                p = p.venstre;
            } 
            
        }
    }
    
    private static <T> Node<T> random(int n, Random r) {
        if (n == 0) 
            return null;                    
        else if (n == 1) 
            return new Node<>(null);     

        int k = r.nextInt(n);    

        Node<T> venstre = random(k,r);     
        Node<T> høyre = random(n-k-1,r);   

        return new Node<>(null,venstre,høyre);
    }

    public static <T> BinTre<T> random(int n) {
        if (n < 0) 
            throw new IllegalArgumentException("Må ha n >= 0!");

        BinTre<T> tre = new BinTre<>();
        tre.antall = n;

        tre.rot = random(n,new Random()); 

        return tre;
    }
    
    public Iterator<T> iterator() {
        return new InordenIterator();
    }
    
    public Iterator<T> omvendtIterator() {
        return new OmvendtInordenIterator();
    }
    
    private class InordenIterator implements Iterator<T> {
        private Stakk<Node<T>> stakk;   
        private Node<T> p = null;   
        private int iteratorendringer = endringer;

        // en privat hjelpemetoder skal inn her

        private InordenIterator() {
            if (tom()) 
                return;             
            stakk = new TabellStakk<>();    
            p = først(rot);
        }

        public T next() {
            if(iteratorendringer != endringer)
                throw new ConcurrentModificationException();
            if (!hasNext()) 
                throw new NoSuchElementException("Ingen verdier!");

            T verdi = p.verdi;                      

            if (p.høyre != null) 
                p = først(p.høyre); 
            else if (stakk.tom()) 
                p = null;          
            else 
                p = stakk.taUt();                    

            return verdi;             
        }

        public boolean hasNext() {
          return p != null;
        }
        
        private Node<T> først(Node<T> q) {
            while (q.venstre != null) {
              stakk.leggInn(q);              
              q = q.venstre;             
            }
            return q;                      
        }
    }
    
    
    private class OmvendtInordenIterator implements Iterator<T> {
        private final Stakk<Node<T>> s;  
        private Node<T> p;                

        private Node<T> sist(Node<T> q) {
            while (q.høyre != null) {
              s.leggInn(q);                  
              q = q.høyre;                  
            }
            
            return q;                        
        }

        public OmvendtInordenIterator() {
            s = new TabellStakk<>();
            if (tom()) return;               
            p = sist(rot);                   
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;             

            if (p.venstre != null)         
                p = sist(p.venstre);
            else if (s.tom()) 
                p = null;      
            else  
                p = s.taUt();                    

            return verdi;                    
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

    }
    
    private class PreordenIterator implements Iterator<T> {
        private final Stakk<Node<T>> s;
        private Node<T> p;

        private PreordenIterator() {
            s = new TabellStakk<>();
            p = rot;
        }

        @Override
        public boolean hasNext() {
            return p != null;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();

            T verdi = p.verdi;

            if (p.venstre != null) {
                if (p.høyre != null) 
                    s.leggInn(p.høyre);
                p = p.venstre;
            }
            else if (p.høyre != null) 
                p = p.høyre;   
            else if (s.tom()) 
                p = null;              
            else p = 
                    s.taUt();                       

            return verdi;
        }
    } 
    
    private static int antall(Node<?> p) {
        if (p == null) 
            return 0;            // et tomt tre har 0 noder

        return 1 + antall(p.venstre) + antall(p.høyre);
    }

    public int antall() {
        return antall(rot);   
    }

    public Iterator<T> preIterator() {
        return new PreordenIterator();
    }
    
    private static int høyde(Node<?> p) {
        if (p == null) 
            return -1;        

        return 1 + Math.max(høyde(p.venstre), høyde(p.høyre));
    }

    public int høyde() {
        return høyde(rot);                
    }
    
    private static <T> boolean inneholder(Node<T> p, T verdi) {
        if (p == null) 
            return false;   
        
        return verdi.equals(p.verdi) || inneholder(p.venstre,verdi) 
                || inneholder(p.høyre,verdi);
    }

    public boolean inneholder(T verdi) {
        return inneholder(rot,verdi); 
    }
    
    private static <T> int posisjon(Node<T> p, int k, T verdi) {
        if (p == null) 
            return -1;             
        if (verdi.equals(p.verdi)) 
            return k;   
        
        int i = posisjon(p.venstre,2*k,verdi);   
        
        if (i > 0) 
            return i;   
        
        return posisjon(p.høyre,2*k+1,verdi);     
    }

    public int posisjon(T verdi) {
        return posisjon(rot,1,verdi);  
    }
    
    private static int antallBladnoder(Node<?> p) {
        if (p.venstre == null && p.høyre == null) 
            return 1;

        return (p.venstre == null ? 0 : antallBladnoder(p.venstre)) + 
                (p.høyre == null ? 0 : antallBladnoder(p.høyre));
    }

    public int antallBladnoder() {
        return rot == null ? 0 : antallBladnoder(rot);
    }
    
    public int makspos() {
        if (tom()) return 0;

        Kø<Node<T>> kø = new TabellKø<>();
        kø.leggInn(rot);

        Kø<Integer> nr = new TabellKø<>();
        nr.leggInn(1);

        int pos = 1;

        while (!kø.tom()) {
            Node<T> p = kø.taUt(); 
            pos = nr.taUt();

            if (p.venstre != null) {
                kø.leggInn(p.venstre); 
                nr.leggInn(2*pos);
            }
            if (p.høyre != null) {
                kø.leggInn(p.høyre); 
                nr.leggInn(2*pos + 1);
            }
        }
        return pos;
    }

} 
