/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author Miina
 */
public class EnkeltLenketListe<T> implements Liste<T>, Kø<T>{

    @Override
    public T kikk() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return hent(0); 
    }

    @Override
    public T taUt() {
        if (tom()) throw new NoSuchElementException("Køen er tom!");
        return fjern(0);   // returnerer (og fjerner) den første
    }

    private static final class Node<T> {

        private T verdi;
        private Node<T> neste;

        private Node(T verdi, Node<T> neste) {
            this.verdi = verdi;
            this.neste = neste;
        }
    }

    private Node<T> hode, hale;
    private int antall;
    private int endringer;

    public EnkeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }

    public EnkeltLenketListe(T[] a) {
        int n = 0;
        while (n < a.length && a[n] == null) {
            n++;
        }

        if (n < a.length) {
            Node<T> node = hode = new Node<>(a[n], null);
            antall = 1;

            for (int i = n + 1; i < a.length; i++) {
                if (a[i] != null) {
                    node = node.neste = new Node<>(a[i], null);
                    antall++;
                }
            }
            hale = node;
        }
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        if (antall == 0) {
            hode = hale = new Node<>(verdi, null);
        } else {
            hale = hale.neste = new Node<>(verdi, null);
        }

        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");

        indeksKontroll(indeks, true);

        if (indeks == 0) {
            hode = new Node<>(verdi, hode);
            if (antall == 0) {
                hale = hode;
            }
        } else if (indeks == antall) {
            hale = hale.neste = new Node<>(verdi, null);
        } else {
            Node<T> p = hode;

            for (int i = 1; i < indeks; i++) {
                p = p.neste;
            }

            p.neste = new Node<>(verdi, p.neste);
        }

        endringer++;
        antall++;
    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        if (verdi == null) {
            return -1;
        }

        Node<T> node = hode;

        for (int index = 0; index < antall; index++) {
            if (node.verdi.equals(verdi)) {
                return index;
            }

            node = node.neste;
        }

        return -1;
    }

    @Override
    public T oppdater(int indeks, T verdi) {
        Objects.requireNonNull(verdi, "Ikke tillatt med null-verdier!");
        indeksKontroll(indeks, false);

        Node<T> p = finnNode(indeks);
        T gammelVerdi = p.verdi;
        p.verdi = verdi;
        endringer++;

        return gammelVerdi;
    }

    @Override
    public boolean fjern(T verdi) {
        if (verdi == null) {
            return false;
        }

        Node<T> node = hode;
        Node<T> forrigeNode = null;

        while (node != null) {
            if (node.verdi.equals(verdi)) {
                break;
            }

            forrigeNode = node;
            node = node.neste;
        }

        if (node == null) {
            return false;
        } else if (node == hode) {
            hode = hode.neste;
        } else {
            forrigeNode.neste = node.neste;
        }

        if (node == hale) {
            hale = forrigeNode;
        }

        node.verdi = null;
        node.neste = null;
        antall--;
        endringer++;

        return true;
    }

    @Override
    public T fjern(int indeks) {
        indeksKontroll(indeks, false);

        T temp;

        if (indeks == 0) {
            temp = hode.verdi;
            hode = hode.neste;
            if (antall == 1) {
                hale = null;
            }
        } else {
            Node<T> p = finnNode(indeks - 1);
            Node<T> q = p.neste;
            temp = q.verdi;

            if (q == hale) {
                hale = p;
            }

            p.neste = q.neste;
        }

        antall--;
        endringer++;
        return temp;
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        return antall < 1;
    }

    @Override
    public void nullstill() {
        Node<T> denneHode = hode;
        Node<T> nesteHode = null;

        while (denneHode != null) {
            nesteHode = denneHode.neste;
            // sletter referansene og verdiene
            denneHode.neste = null;
            denneHode.verdi = null;
            // gjør det samme for neste
            denneHode = nesteHode;
        }

        hode = hale = null;
        antall = 0;
        endringer++;
    }

    @Override
    public Iterator<T> iterator() {
        return new EnkeltLenketListeIterator();
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("[");

        if (!tom()) {
            Node<T> node = hode;
            str.append(node.verdi);
            node = node.neste;

            while (node != null) {
                str.append(", ").append(node.verdi);
                node = node.neste;
            }
        }

        str.append("]");
        return str.toString();
    }

    private Node<T> finnNode(int indeks) {
        Node<T> p = hode;
        for (int i = 0; i < indeks; i++) {
            p = p.neste;
        }

        return p;
    }

    private class EnkeltLenketListeIterator implements Iterator<T> {

        private Node<T> node = hode;
        private boolean fjernOK = false;
        private int iteratorendringer = endringer;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Ingen verdier!");
            }
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Noden er alt i endring");
            }

            fjernOK = true;
            T denneVerdi = node.verdi;
            node = node.neste;

            return denneVerdi;
        }

        public void remove() {
            if (!fjernOK) {
                throw new IllegalStateException("Ulovlig tilstand!");
            }
            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Noden er alt i endring");
            }
            fjernOK = false;
            Node<T> tmpNode = hode;

            if (hode.neste == node) {
                hode = hode.neste;
                if (node == null) {
                    hale = null;
                }
            } else {
                Node<T> r = hode;

                while (r.neste.neste != node) {
                    r = r.neste;
                }

                tmpNode = r.neste;
                r.neste = node;
                if (node == null) {
                    hale = r;
                }
            }

            tmpNode.verdi = null;
            tmpNode.neste = null;
            iteratorendringer++;
            endringer++;

            antall--;
        }

        public boolean fjernHvis(Predicate<? super T> predikat) {
            Objects.requireNonNull(predikat, "null-predikat!");

            Node<T> node = hode;
            Node<T> forrigeNode = null;
            int antallFjernet = 0;

            while (node != null) {
                if (predikat.test(node.verdi)) {
                    antallFjernet++;
                    endringer++;

                    if (node == hode) {
                        if (node == hale) {
                            hale = null;
                        }
                        hode = hode.neste;
                    } else if (node == hale) {
                        forrigeNode.neste = null;
                    } else {
                        forrigeNode.neste = node.neste;
                    }
                }

                forrigeNode = node;
                node = node.neste;
            }

            antall -= antallFjernet;

            return antallFjernet > 0;
        }
        
        public void forEach(Consumer<? super T> action) {
            Objects.requireNonNull(action, "handling er null!");

            Node<T> node = hode;
            while (node != null) {
                action.accept(node.verdi);
                node = node.neste;
            }
        }
        
        public void forEachRemaining(Consumer<? super T> handling) {
            Objects.requireNonNull(handling, "handling er null!");
            
            while (node != null) {
                handling.accept(node.verdi);
                node = node.neste;
            }
        }

    }

}
