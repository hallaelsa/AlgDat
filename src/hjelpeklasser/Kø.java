/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hjelpeklasser;

public interface Kø<T> {
    public boolean leggInn(T verdi); 
    public T kikk();                 
    public T taUt();                
    public int antall();           
    public boolean tom();           
    public void nullstill();        
} 
