/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

import java.util.*;
import java.util.function.Predicate;

public interface Beholder<T> extends Iterable<T> {
    public boolean leggInn(T verdi);    
    public boolean inneholder(T verdi); 
    public boolean fjern(T verdi);      
    public int antall();                
    public boolean tom();               
    public void nullstill();            
    public Iterator<T> iterator();      

    default boolean fjernHvis(Predicate<? super T> p) {
        Objects.requireNonNull(p);                       

        boolean fjernet = false;

        for (Iterator<T> i = iterator(); i.hasNext(); ) {
          if (p.test(i.next())) {
                i.remove(); 
                fjernet = true;                 
          }
        }
      
        return fjernet;
    }
} 
