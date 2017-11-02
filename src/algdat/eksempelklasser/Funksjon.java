/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat.eksempelklasser;

/**
 *
 * @author Miina
 */
@FunctionalInterface
public interface Funksjon<T,R> {
      R anvend(T t);
}
