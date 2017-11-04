/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

public interface PrioritetsKø<T> {
    public void leggInn(T verdi);            // Java: add/offer
    public T kikk();                         // Java: element/peek
    public T taUt();                         // Java: remove/poll
    public boolean taUt(T verdi);            // Java: remove
    public int antall();                     // Java: size
    public boolean tom();                    // Java: isEmpty
    public void nullstill();                 // Java: clear
}
