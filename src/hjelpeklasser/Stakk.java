/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

/**
 *
 * @author Miina
 */
public interface Stakk<T> {
    public void leggInn(T verdi);     
    public T kikk();                  
    public T taUt();                  
    public int antall();              
    public boolean tom();           
    public void nullstill();          
}
