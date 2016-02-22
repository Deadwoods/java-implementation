import java.util.Scanner;
import java.io.*;
import java.lang.*;
import java.util.*;

//creates card objects and deals them to rooms

public class Deck{

   
   private HashMap<Cards,String> theCards=new HashMap<Cards,String>();   
   //gets card info via cards.txt
   public void loadCards(){
      try{
         File card_file = new File("cards.txt");
         Scanner numScan = new Scanner(card_file);
         numScan.nextLine();
         while (numScan.hasNextLine()){
            String temp=numScan.nextLine();            
            Cards card=new Cards(temp);
            theCards.put(card,"false");
            
         }
      }catch(FileNotFoundException e){
         System.out.println("Please include cards.txt file");
      }
   }
//return the first valid card in the deck
   public Cards getCard() {
      Cards temp = new Cards("1///1///1///1///1");
      for (Cards key : theCards.keySet()){         
        if(theCards.get(key).equals("false")){          
          theCards.put(key,"true");
          temp = key;
          break;
        }
      }
      return temp;     
      
   }   
}

