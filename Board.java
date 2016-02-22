import java.io.File;
import java.util.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.*;

//Board creates all the rooms and the dice and deck classes
//Board will control flow of entire game

public class Board{
    
    private Player[] players;   
    private int numdays;
    private int cards=10;
    private Room[] rooms = new Room[10];
    private Deck deck=new Deck();
    private Dice dice=new Dice();
    private Trailer trailer;
    private Office office;
    
    public Board( int numdays,Player[] players,Dice dice){      
        this.numdays=numdays;
        this.players=players;
        this.dice=dice;  
    }
    //payout for finishing card
    //checks every player if there in the room and acting star role they get included in payout
    //calls dice.payout that returns sorted array of payout values 
    //pays players and sets state to idle
    public void calcPayout(Room room1){
        String roomname=room1.getName();
        int starroles=room1.getNumStarRoles();
        int budget=room1.getBudget();
        String[] x = new String[players.length];
        TreeMap<Integer,Player> playertree = new TreeMap<Integer, Player>();
        for(int i=0;i<players.length;i++){
            x=players[i].getRole();
            String ref = x[0];
            if(ref.equals(roomname)){ 
                players[i].setState(0);
                if(Integer.parseInt(x[1])==2){
                    playertree.put(Integer.parseInt(x[2]),players[i]);
                }
                else if(Integer.parseInt(x[1])==1){
                    System.out.printf("Player %d was on a bit role and recieved %d dollars\n",i,Integer.parseInt(x[2]));
                    players[i].updateDollars(Integer.parseInt(x[2]));
                }
            }
            
        }
        int[] pay = dice.payout(starroles,budget);
        int[] roles=new int[starroles];
        String[] temp=new String[2];
        HashMap<String,String> starroles1=room1.getStarMap();
        int k=0;
        for (String key : starroles1.keySet()){
            temp=key.split(">");
            roles[k]=Integer.parseInt(temp[1]);
            k++;
        }
        
        int key=0;
        int n=0;
        //insertion sort
        for(int m=1;m<starroles;m++){
            key=roles[m];
            n=m-1;
            while(n>=0 && roles[n]<key){
                roles[n+1]=roles[n];
                n-=1;            
            }
            roles[n+1]=key;
        }
        System.out.printf("\nPayout for player:\n");
        int numPays = playertree.size();
        for(int j=0;j<numPays;j++){
            int z=playertree.lastKey();
            for(int w=0;w<roles.length;w++){
                if(z==roles[w]){               
                    System.out.printf("%s was paid %d dollars:\n",playertree.get(z).getName(), pay[w]);
                    playertree.get(z).setRole("");
                    playertree.get(z).updateDollars(pay[w]);
                    playertree.pollLastEntry();              
                }
            }              
            
        }      
        
        
    }
    
    
    //creates the trailer
    //available rooms
    public void createTrailer(){
        try{
            File trailerfile = new File("Trailer.txt");
            Scanner console = new Scanner(trailerfile);
            ArrayList<String> rooms = new ArrayList<String>();
            while(console.hasNext()){
                String line = console.nextLine();
                String[] info = line.split("///");
                for(int i=0;i<info.length;i++){
                    rooms.add(info[i]);
                }
                this.trailer=new Trailer(rooms);
                
            }      
        }catch(FileNotFoundException e){
            System.out.println("Please include file names Trailer.txt");
        }
    }
    
    //creates office
    //available rooms
    public void createOffice(){
        try{
            File officefile = new File("CastingOffice.txt");
            Scanner console = new Scanner(officefile);
            ArrayList<String> rooms = new ArrayList<String>();
            while(console.hasNext()){
                String line = console.nextLine();
                String[] info = line.split("///");
                for(int i=0;i<info.length;i++){
                    rooms.add(info[i]);
                }
                this.office=new Office(rooms);
                
            }      
        }catch(FileNotFoundException e){
            System.out.println("Please include file names CastingOffice.txt");
        }
    }
    
    
    
    //creates all the rooms using Roominfo.txt
    //calls deck to deal card to everyroom
    public void createRooms(){               
        int i=0;  
        deck.loadCards(); 
        try{
            File roomfile = new File("Roominfo.txt");
            Scanner console1=new Scanner(roomfile);
            while(console1.hasNext()){
                String line = console1.nextLine();
                String[] info=line.split(",");
                Cards card = deck.getCard();            
                rooms[i] = new Room(info[0],Integer.parseInt(info[1]),info[2],info[3],card);  
                i+=1;                     
            }
        }catch(FileNotFoundException e){
            System.out.println("Please include file named Roominfo.txt");
        }      
    }
    
    //checks if room that was just worked in has any more shot counters
    //if it doesnt it updates the cards which is the running count of all the cards still out on board
    //it then calls payout for that specific room and then calls flip card that will make rooms star roles and  bitroles unviewable
    public void updateCard(Room room1){
        if(room1.getShotCounter()==0){
            this.cards-=1;
            room1.updateCounter();
            calcPayout(room1); 
            room1.flipCard();        
        }      
    }
    
    //called after cards=1 which signifies new day
    //resets cards to 10, removes star roles for every room and calls deck to deal new card to every room, resets rooms counter and bit roles
    //returns players to trailer in idle state and clears all there rehearsals
    //if days is 0 is calls calcWinner and prints player with highest score
    public void newDay(){
        this.cards = 10;
        numdays-=1;
        if(numdays!=0){         
            for(int i=0;i<rooms.length;i++){
                rooms[i].removeStarRoles();
                rooms[i].getCardInfo(deck.getCard());
                rooms[i].resetCounter();
            }    
            for(int i = 0;i<players.length;i++){
                players[i].updateRoom("trailer");
                players[i].setRole("");
                players[i].setState(0);
                players[i].updateRehearsals(-1);
            }
        }else{
            calcWinner();
        }
        System.out.println();
        System.out.println("--------------------------------------------------------------------");
        System.out.println("Congradulations you have completed a day!");
        System.out.println("--------------------------------------------------------------------");
        System.out.println();
    }
    
    //called by calWinner 
    //calculates players final score 
    private int calcPayOut(Player player){
        int finalScore = player.getDollars() + player.getCredits() + ((player.getRank())*5);
        return finalScore;
    }
    //called at end of game determines player with highest score 
    //or if theres a tie it saves players in array with high score 
    public void calcWinner(){
        String[] winners = new String[this.players.length];
        int max=0;
        int temp=0;
        int j=0;
        for(int i=0;i<this.players.length;i++){
            temp=calcPayOut(this.players[i]);
            if(temp>max){
                Arrays.fill(winners,"");
                winners[0]=players[i].getName();
                j=1;
                max=temp;            
            }else if(temp==max){
                winners[j]=players[i].getName();
                j++;
            }
            
        }
        if(j<=1){
            System.out.println("******************************************************************************************************************************************");
            System.out.println("Player "+winners[0]+" is the winner!\n");
        }  
        else{
            System.out.println("Players ");
            for(int k=0;k<j;k++){
                System.out.print(winners[k]+", ");
            }
            System.out.printf("have tied for the win!\n");
        }
    }
    
    
    //main game loop 
    //loop while days is greater than 0
    //iner loop is for cards greater than 1
    //gets room name from player then calls player turn with the room object there in
    //after every player turn it checks shot counters
    //will call newDay if cards=1 
    public void startGame(){
        int i=0;
        while(numdays>0){
            while(cards>1){
                if(i!=players.length){
                    String name = players[i].displayRoom();
                    if(name.equals("trailer")){
                        players[i].turn(trailer);
                        i++;
                    }
                    else if(name.equals("Casting Office")){
                        players[i].turn(office);
                        i++;
                    }
                    else{
                        for(int j=0;j<rooms.length;j++){ 
                            if(name.equals(rooms[j].getName())){ 
                                players[i].turn(rooms[j]);
                                updateCard(rooms[j]);
                                j=rooms.length;
                            }
                        } 
                        i++;
                    }                    
                    
                }else{
                    i=0;
                }      
            }
            newDay();     
            
        }
    }
    
}