public class Player{

   private String room;
   private int rank;
   private int credits;
   private int dollars;
   //private hashtable rehearsals;
   private String state;
   
   public Player(int rank,int credits,int dollars){
      this.rank=rank;
      this.credits=credits;
      this.dollars=dollars;
      this.room="trailer";
      this.state="move";
   }
   
   public void updateDollars(int amount){
      this.dollars+=amount;
   }
   
   public void updateCredits(int amount){
      this.credits+=amount;
   }
   
   public void updateRank(int amount){
      this.rank+=amount;
   }
   
   public void updateRoom(String curroom){
      this.room=curroom;
   }
   
   
   
   

}