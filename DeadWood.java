import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;

public class DeadWood{
    public static void main(String[]	args)	{	 
        
        int numplayer=0;
        int credits=0;
        int numdays=4;
        int dollars=0;
        int rank=1;		  
        boolean valid=false;	
        Dice dice= new Dice();
        
        
        
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
        Player[] players=new Player[numplayer];
        String name = "";
        for(int i=0;i<numplayer;i++){
            System.out.printf("Player %d choose your name:",i);
            name = console.nextLine();
            players[i]=new Player(rank,dollars,credits,name,dice);
        }
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
        
        Board board = new Board(numdays,players,dice);
        board.createRooms();
        board.createTrailer();
        board.createOffice();
        board.startGame();
    }
}