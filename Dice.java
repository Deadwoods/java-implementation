import java.util.Random;
public class Dice{
    
    //act creates random number from 1 to 6 and adds rehearsal value passed to it and returns that value 
    public int act(int rehearsals){
        Random dice = new Random();
        int total=dice.nextInt(6)+1;
        return (total+rehearsals);
    }
    
    
    //this takes the budget roles for the amount of times equal to budget then store those value in array
    //it then uses insertion sort to sort values
    //after that it uses the number of star toles to determine the values of the final array it returns which 
    //will contain the payout corresponding to the highest to lowest for each starrole on that card   
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