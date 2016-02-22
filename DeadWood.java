import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;

//Deadwood is driver class

public class DeadWood{
    public static void main(String[]	args)	{	 
        //Initializes amounts that will be determined by user input
        int numplayer=0;
        int credits=0;
        int numdays=4;
        int dollars=0;
        int rank=1;		  
        boolean valid=false;	
        Dice dice= new Dice();
        
        
        //get the number of players from user
        Scanner console  = new Scanner(System.in);
        System.out.println("Enter number of players: ");
        while(!valid){
            try{
                numplayer = console.nextInt();
                if(numplayer>8	||	numplayer<2){
                    System.out.println("Please enter number between 2 and 8: ");
                }else{
                    valid=true;  
                }               
            }catch(InputMismatchException e){
                System.out.println("Please enter number between 2 and 8 : ");
                console.next();
                
            }			
        }
        console.nextLine();
        //hold all player objects in player array to be passed to board
        Player[] players=new Player[numplayer];
        String name = "";
        //creates players and prompts user for player name
        for(int i=0;i<numplayer;i++){
            System.out.printf("Player %d choose your name:",i);
            name = console.nextLine();
            players[i]=new Player(rank,dollars,credits,name,dice);
        }
        //switch statement using user input to determine starting values 
        switch(numplayer){
            case 2:
            case 3:
                numdays=3;
                break;				
            case 5:				  
                credits=2;
                break;
            case 6:					 
                credits=4;
                break;
            case 7:
            case 8:
                rank=2;
                break;
        }
        //creates board then calls board to create the rooms then calls start game
        Board board = new Board(numdays,players,dice);
        board.createRooms();
        board.createTrailer();
        board.createOffice();
        board.startGame();
    }
}