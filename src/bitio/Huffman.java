/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bitio;

import java.io.*;                              
import java.net.URL;                            
import hjelpeklasser.*;                        

public class Huffman {
    private static class Node {
        private int frekvens;                       
        private Node venstre;                       
        private Node høyre;                         

        private Node() {}                          

        private Node(int frekvens, Node v, Node h) {
            this.frekvens = frekvens;
            venstre = v;
            høyre = h;
        }
    } 

    private static class BladNode extends Node  {
        private final char tegn;                   

        private BladNode(char tegn, int frekvens) {
            super(frekvens, null, null);    
            this.tegn = tegn;
        }
    } 
    
    private static Node byggHuffmanTre(int[] frekvens) {
        PrioritetsKø<Node> kø = new HeapPrioritetsKø<>((p,q) -> p.frekvens - q.frekvens);

        for (int i = 0; i < frekvens.length; i++) {
            if (frekvens[i] > 0)         
                kø.leggInn(new BladNode((char)i, frekvens[i]));
        }
        
        if (kø.antall() < 2)           
            throw new IllegalArgumentException("Det er for få tegn!");

        while (kø.antall() > 1) {
            Node v = kø.taUt();                 
            Node h = kø.taUt();                 
            int sum = v.frekvens + h.frekvens;  

            kø.leggInn(new Node(sum, v, h));     
        }

        return kø.taUt();                     
    }
    
    private static void finnBitkoder(Node p, String kode, String[] koder) {
        if (p instanceof BladNode) {
            koder[((BladNode)p).tegn] = kode;
        } else {
            finnBitkoder(p.venstre, kode + '0', koder);  
            finnBitkoder(p.høyre, kode + '1', koder);   
        }
    }
    
    public static String[] stringBitkoder(int[] frekvens) {
        Node rot = byggHuffmanTre(frekvens);               

        String[] bitkoder = new String[frekvens.length];   
        finnBitkoder(rot,"",bitkoder);                      

        return bitkoder;     
    }
    
    public static int[] stringFrekvens(String tekst) {
        int[] frekvens = new int[256];

        for (int i = 0; i < tekst.length(); i++) {
            frekvens[tekst.charAt(i)]++;
        }

        return frekvens;
    }
    
    public static int[] streamFrekvens(InputStream inn) throws IOException {
        int[] frekvens = new int[256];
        int tegn = 0;
        
        while ((tegn = inn.read()) != -1) {
            frekvens[tegn]++;
        }
        
        inn.close();
        return frekvens;
    }
    
    public static String[] ascii = {
        "NUL","SOH","STX","ETX","EOT","ENQ","ACK","BEL","BS","HT","LF",
        "VT","FF","CR","SO","SI","DLE","DC1","DC2","DC3","DC4","NAK",
        "SYN","ETB","CAN","EM","SUB","ESC","FS","GS","RS","US"
    };

}  
