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
public final class Heltall implements Comparable<Heltall> {
    private final int verdi;    

    public Heltall(int verdi) { 
        this.verdi = verdi; 
    }   

    public int intVerdi() { 
        return verdi; 
    }          

    @Override
    public int compareTo(Heltall h) {
        return verdi < h.verdi ? -1 : (verdi == h.verdi ? 0 : 1);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) 
            return true;   
        if (!(o instanceof Heltall)) 
            return false;  
        return verdi == ((Heltall)o).verdi;
    }

    public boolean equals(Heltall h) { 
        return verdi == h.verdi; 
    }

    @Override
    public int hashCode() { 
        return 31 + verdi; 
    }

    @Override
    public String toString() { 
        return Integer.toString(verdi); 
    }

  }
