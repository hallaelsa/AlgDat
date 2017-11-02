/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat;

/**
 *
 * @author Miina
 */
public class AndreOppgaver {
    
    public static void main(String ... args){
            System.out.println(fib(10));     // Utskrift: 55
            System.out.println(fib(20));  
    }
    
    
    public static int a(int n) {
        if(n < 0)
            throw new IllegalArgumentException("n er negativ..");
        
        int x = 0;
        int y = 1;
        int z = 1;
        
        for(int i = 0; i < n; i++) {
            z = 2*y + 3*x;
            x = y;
            y = z;
        }
        return z;
    }
    
    public static int tverrsum(int n) {
        if(n < 10)
            return n;
        
        int sum = 0;
        
        while(n > 0) {
            sum =+ n%10;
            n /= 10;
        }
        
        return sum;
    }
    
    public static int sifferrot(int n) {
        while(n >= 10){
            n = tverrsum(n);
        }
        return n;
    }
    
    public static int kvadratsum(int n) {
        if(n == 1)
            return 1;
        else
            return kvadratsum(n-1)+n*n;
    }
    
    public static int sum(int k, int n) {
        if(n == k)
            return n;
        
        int m = (n + k)/2;
        return sum(n, m) + sum(m + 1, k);
    }
    
    public static int maks(int[] a, int v, int h) {
        if(h == v) return v;
        int m = (v + h)/2;
        int mv = maks(a,v,m);
        int mh = maks(a,m+1,h);
        
        return a[mv] > a[mh] ? mv : mh;
    }
    
    public static int fakultet(int n) {
        if(n < 2)
            return 1;
        return fakultet(n-1)*n;
    }
    
    public static int fib(int n) {
        if (n <= 1) 
            return n;        
        else return fib(n-1) + fib(n-2);  
    }
}
