import java.util.*;
import java.lang.*;

public class Player{

    private String room;
    private int rank;
    private int credits;
    private int dollars;
    private HashMap<String,Integer> rehearsals;
    private String state;
    private int role; //0 = no role, 1 = bit role, 2 = star role

    
    public Player(int rank,int credits,int dollars){
        this.rank=rank;
        this.credits=credits;
        this.dollars=dollars;
        this.room="trailer";
        this.state="idel";
        this.role = 0;
    }
    
    private void updateDollars(int amount){
        this.dollars+=amount;
    }
    
    private void updateCredits(int amount){
        this.credits+=amount;
    }
    
    private void updateRank(int amount){
        this.rank+=amount;
    }
    
    private void updateRoom(String curroom){
        this.room=curroom;
    }
    public String displayRoom(){
        return this.room;
    }
/*
player states:
idel - can move to new room / take role in current room
acting - can move to new room /  atempt role
upgrading - move to new room

*/
    public void updateState(String newState){
        this.state=newState;
    }
    //if 1 increase num rehearsals by 1, if -1 reset map
    public void updateRehearslas(HashMap<String,Integer> rehearsals, int option, String room){
        if(option == 1){
            if(rehearsals.get(room) == null){
                rehearsals.put(room, 1);
            }
            else{
                rehearsals.put(room, (rehearsals.get(room) + 1));
            }
        }
        else{
            rehearsals.clear();
        }
    }
    
    public void turn(Room currroom){
        switch(this.state){
            case "idel":
                idelState();
                break;
            case "acting":
                actingState();
                break;
        }
    }
    public void turn(Office currroom){
        //upgrade the over chance to move
    }
    
    private void idelState(Room currroom){
        String userChoice;
        Scanner console = new Scanner(System.in);
        boolean valid = false;
        String[] starRoles = parseList(curroom.getStarRoles());
        String[] bitRoles = parseList(curroom.getBitRoles());
        
        System.out.printf("Turn options:\nMove: ");
        Arrays.asList(curroom.getAdjacentRooms()).stream().forEach(s -> System.out.printf("%s ",s));
        System.out.printf("\nAvailable roles:\nStar: ");
        
        Arrays.asList(starRoles).stream().forEach(s -> System.out.printf("%s ",s));
        System.out.printf("\nBit: ");
        Arrays.asList(bitRoles).stream().forEach(s -> System.out.printf("%s ",s));
        System.out.printf("\nSelect using m-1 for move 1 ect, s-1 for star role 1 ect, b-1 for bit role 1 ect\n:");
        userChoice = console.nextLine();
        while(!valid){
            valid = true;
            try{
                switch(userChoice){
                    case "m-1":
                        updateRoom(currroom.getAdjacentRooms()[0]);
                        break;
                    case "m-2":
                        updateRoom(currroom.getAdjacentRooms()[1]);
                        break;
                    case "m-3":
                        updateRoom(currroom.getAdjacentRooms()[2]);
                        break;
                    case "s-1":
                    case "s-2":
                    case "s-3":
                        this.role = 2;
                        break;
                    case "b-1":
                    case "b-2":
                    case "b-3":
                    case "b-4":
                        this.role = 1;
                        break;
                    default:
                        System.out.println("Enter valid option.");
                        valid = false;
                        break;
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("Enter valid number.");
                valid = false;
            }
        }
    }

    private void actingState(){
    
    }
    
    private String[] parseList(String[] available){
        String[] parse;
        String[] result;
        int counter = 0;
        for(int i = 0; i<available.length();i++){
            try{
                parse = available.split(">");
                if(Integer.parseInt(parse[1]) == this.rank){
                    result[coutner] = parse[0];
                    x++;
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.prinln("file input wrong");
            }
        }
        return result;
    }
}








