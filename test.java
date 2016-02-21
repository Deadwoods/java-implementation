import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;

public class test{
    public static void main(String[]	args)	{	 
        int[] roles = {1,5,2,8,4};
        int key=0;
        int n=0;
        int starroles = 5;
        for(int m=1;m<starroles;m++){
            key=roles[m];
            n=m-1;
            while(n>=0 && roles[n]<key){
                roles[n+1]=roles[n];
                n-=1;            
            }
            roles[n+1]=key;
        }
        System.out.printf("%d %d %d %d %d", roles[0], roles[1], roles[2], roles[3], roles[4]);
    }     
    
    
    
    
}