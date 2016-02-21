import java.util.*;
public class Trailer{

   private ArrayList<String> adjRooms = new ArrayList();
   
   //constructor 
   public Trailer(ArrayList<String> adjRooms){
      this.adjRooms = adjRooms;
   }
      
   public ArrayList<String> getAdjacentRooms(){
       return this.adjRooms;
   }
   
}