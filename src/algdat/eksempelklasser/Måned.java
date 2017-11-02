/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat.eksempelklasser;

import java.util.Arrays;

/**
 *
 * @author Miina
 */
public enum Måned {
    JAN (1, "januar"),
    FEB (2, "februar"),
    MAR (3, "mars"),
    APR (4, "arpil"),
    MAI (5, "mai"),
    JUN (6, "juni"),
    JUL (7, "juli"),
    AUG (8, "august"),
    SEP (9, "september"),
    OKT (10, "oktober"),
    NOV (11, "november"),
    DES (12, "desember");
    
    private final int mndnr;
    private final String fulltnavn;
    
    private Måned(int mndnr, String fulltnavn) {
        this.mndnr = mndnr;
        this.fulltnavn = fulltnavn;
    }
    
    public int mndnr() {
        return mndnr;
    }
    
    @Override
    public String toString() {
        return fulltnavn;
    }
    
    public static String toString(int mndnr) {
        if(mndnr < 1 || mndnr > 12)
            throw new IllegalArgumentException("ikke en gyldig måned");
        // arrayet starter med 0, derfor trekker vi fra 1.
        return values()[mndnr - 1].toString();
    }
    
    public static Måned[] vår() {
        return Arrays.copyOfRange(values(), 3, 5);
    }
    
    public static Måned[] sommer() {
        return Arrays.copyOfRange(values(), 5, 8);
    }
    
    public static Måned[] høst() {
        return Arrays.copyOfRange(values(), 8, 10);
    }
    
    public static Måned[] vinter() {
        return new Måned[] {NOV, DES, JAN, FEB, MAR};
    }
}
