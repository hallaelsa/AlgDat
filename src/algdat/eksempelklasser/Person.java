/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat.eksempelklasser;

import java.util.Objects;

/**
 *
 * @author Miina
 */
public class Person implements Comparable<Person> {
    private final String fornavn;         // personens fornavn
    private final String etternavn;       // personens etternavn

    public Person(String fornavn, String etternavn) {
        Objects.requireNonNull(fornavn, "fornavn er null");
        Objects.requireNonNull(etternavn, "etternavn er null");
    
        this.fornavn = fornavn;
        this.etternavn = etternavn;
    }

    public String fornavn() { 
        return fornavn; 
    } 
    
    public String etternavn() { 
        return etternavn; 
    } 

    @Override
    public int compareTo(Person p) {
        int cmp = etternavn.compareTo(p.etternavn);    
        if (cmp != 0) 
            return cmp;   
        
        return fornavn.compareTo(p.fornavn); 
    }

    @Override
    public boolean equals(Object o){
        if (o == this) 
            return true;           
        if (o == null) 
            return false;          
        if (getClass() != o.getClass()) 
            return false; 
        
        final Person p = (Person)o;    
        
        return etternavn.equals(p.etternavn) && fornavn.equals(p.fornavn);
  
    }

    @Override
    public int hashCode() { 
        return Objects.hash(etternavn, fornavn); 
    }

    @Override
    public String toString() { 
        return String.join(" ", fornavn, etternavn);
    }

  } 
