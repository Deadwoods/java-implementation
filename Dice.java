import java.util.Random;
public class Dice{
    
    /*This method lets the user know if they had a correct role (made the budget) 
     *or had an incorrect role (did not make budget). The player class will use the method*/
    public int act(int rehearsals){
        Random dice = new Random();
        int total=dice.nextInt(6)+1;
        return (total+rehearsals);
    }
    
    
    //This method will calculate the payout for the players. The board will use this method.   
    public int[] payout(int starroles, int budget){
        System.out.println("Money role!");
        int[] payout = new int[starroles];
        int[] temp = new int[budget];
        for(int i=0;i<budget;i++){
            int total=act(0);
            temp[i]=total;
            System.out.printf("\nrole %d = %d",i,total);
        }
        
        //sorting using insertion sort
        int key;
        int i=0;
        for(int j=1;j<budget;j++){
            key=temp[j];
            i=j-1;
            while(i>=0 && temp[i]>key){
                temp[i+1]=temp[i];
                i-=1;            
            }
            temp[i+1]=key;
        }
        
        //put into the array and return the payout  
        int k=0;
        int l=budget-1;
        while(l>=0){
            if(k!=starroles){
                payout[k]+=temp[l];
                k+=1;
                l-=1;
            }
            else{
                k=0;
            }         
        }
        return payout;          
    }
}