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
public enum Studium  {
    Data ("Ingeniørfag - data"),        
    IT ("Informasjonsteknologi"),        
    Anvendt ("Anvendt datateknologi"),  
    Elektro ("Ingeniørfag - elektronikk og informasjonsteknologi"),
    Enkeltemne ("Enkeltemnestudent");    

    private final String fulltnavn;   
    
    private Studium(String fulltnavn) { 
        this.fulltnavn = fulltnavn; 
    }

    @Override
    public String toString() { 
        return fulltnavn; 
    }
}
