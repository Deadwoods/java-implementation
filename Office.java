import java.lang.*;
import java.util.*;

public class Office{
    private ArrayList<String> adjRooms = new ArrayList<String>();
    
    public Office(ArrayList<String> adjRooms){
        this.adjRooms = adjRooms;
    }
    
    public ArrayList<String> getAdjacentRooms(){
        return this.adjRooms;
    }
    //Given a string either "$" or "cr" and an int rank the player wished to upgrade to
    //return the credit or dallar amount for that rank
    public static int checkValid(String $_OR_cr, int rank){
        if($_OR_cr.equals("cr")){
            if(rank == 2){
                return 5;
            }
            else if(rank == 3){
                return 10;
            }
            else if(rank == 4){
                return 15;
            }
            else if(rank == 5){
                return 20;
            }
            else if(rank == 6){
                return 25;
            }
            else{
                return 0;
            }
        }
        
        else if($_OR_cr.equals("$")){
            if(rank == 2){
                return 4;
            }
            else if(rank == 3){
                return 10;
            }
            else if(rank == 4){
                return 18;
            }
            else if(rank == 5){
                return 28;
            }
            else if(rank == 6){
                return 40;
            }
            else{
                return 0;
            }        
        }
        else{
            return 0;
        }
    }
    //Given the players dollar/credit aomount and rank
    //Print any possible upgrades and return true if at least one if found
    public static boolean possibleUpgrades(int cur_Dollar, int cur_Credit, int cur_Rank){
        
        if((cur_Dollar<4 && cur_Credit<5) || cur_Rank > 5){
            System.out.println("There are no possible upgrades.");
            return false;
        }
        
        if(cur_Dollar > 4 || cur_Credit > 5){
            if(cur_Rank < 2){
                System.out.println("You may upgrade your rank to 2. 4 dollars or 5 credits");
            }
        }
        
        if(cur_Dollar > 10 || cur_Credit > 10){
            if(cur_Rank < 3){
                System.out.println("You may upgrade your rank to 3. 10 dollars or 10 credits");
            }     
        }
        
        if(cur_Dollar > 18 || cur_Credit > 15){
            if(cur_Rank < 4){
                System.out.println("You may upgrade your rank to 4. 18 dollars or 15 credits");
            }
        }
        
        if(cur_Dollar > 28 || cur_Credit > 20){
            if(cur_Rank < 5){
                System.out.println("You may upgrade your rank to 5. 28 dollars or 20 credits");
            }
        }
        
        if(cur_Dollar > 40 || cur_Credit >25){
            if(cur_Rank < 6){
                System.out.println("You may upgrade your rank to 6. 40 dollars or 25 credits");
            }
        }
        return true;
    }
}