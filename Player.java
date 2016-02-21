import java.util.*;
import java.lang.*;

public class Player{
    
    private String name;
    private String room;
    private int rank;
    private int credits;
    private int dollars;
    private HashMap<String,Integer> rehearsals = new HashMap<String, Integer>();
    private int idleBitStar; //0 = no role, 1 = bit role, 2 = star role
    private String role;
    private Dice dice;
    
    public Player(int rank,int dollars, int credits, String name, Dice dice){
        this.rank=rank;
        this.credits=credits;
        this.dollars=dollars;
        this.room="trailer";
        this.idleBitStar = 0;
        this.role = "";
        this.name = name;
        this.dice=dice;
    }
    
    public int getDollars(){
        return this.dollars;
    }
    public int getCredits(){
        return this.credits;
    }
    public int getRank(){
        return this.rank;
    }
    public String getName(){
        return this.name;
    }
    public void setRole(String role){
        this.role = role;
    }
    public void updateDollars(int amount){
        this.dollars+=amount;
    }
    
    private void updateCredits(int amount){
        this.credits+=amount;
    }
    
    private void updateRank(int amount){
        this.rank+=amount;
    }
    
    public void updateRoom(String curroom){
        this.room=curroom;
    }
    public String displayRoom(){
        return this.room;
    }
    public String[] getRole(){
        String[] x = new String[3];
        x[0]=this.room;
        x[1]=Integer.toString(this.idleBitStar);
        if(this.role.equals("")){
            x[2]="0";
        }else{
            String[] y=this.role.split(">");        
            x[2]=y[1];
        }
        return x; 
    }    
    
    
    public void setState(int num){
        this.idleBitStar=num;
    }
    
    //if 1 increase num rehearsals by 1, if -1 reset map
    public void updateRehearsals(int option){
        if(option == 1){
            if(this.rehearsals.get(this.role) == null){
                this.rehearsals.put(this.role, 1);
            }
            else{
                this.rehearsals.put(this.role, (this.rehearsals.get(this.role) + 1));
            }
        }
        else{
            this.rehearsals.clear();
        }
    }
    ////////////////////////////////////////////////
    /*
     * if player is in a normal room (not trailer or office)
     * will either be in a state of acting or in an idle state
     * if idle ask if to move and check for valid move
     * if acting call acting method more description bellow
     */
    ///////////////////////
    public void turn(Room curroom){
        printTurn();
        
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        boolean validInput = false;
        
        playerState ();
        if(!stayidle()){
            if(this.idleBitStar == 0){
                while(!validInput){
                    moveOption(curroom.getAdjacentRooms());
                    starRoleOptions(curroom.getStarRoles());
                    bitRoleOption(curroom.getBitRoles());
                    userChoice = console.nextLine();
                    
                    validInput = parseChoice(userChoice, curroom);
                }
                validInput = false;
            }
            else{
                while(!validInput){
                    actingOptions(curroom);
                    userChoice = console.nextLine();
                    validInput = parseActingChoice(curroom, userChoice);
                }
            }
        }
        System.out.println("End of turn");
        printTurn();
    }
    
    private boolean parseChoice(String userChoice, Room curroom){
        String turnOption = "";
        String roomOrRole = "";
        try{
            turnOption = userChoice.substring(0,userChoice.indexOf(' '));
            roomOrRole = userChoice.substring(userChoice.indexOf(' ')+1);                
            if(turnOption.equals("move")){
                return validRoom(roomOrRole, curroom.getAdjacentRooms());
            }
            else if(turnOption.equals("work")) {                     
                return validWork(roomOrRole, curroom);                    
            }
            
            else{
                System.out.println("Not a valid turn option please choose move,work");
                return false;
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("INVALID INPUT");
            return false;
        }
        
    }
    
    private boolean parseActingChoice(Room curroom, String userChoice){
        try{
            if(userChoice.equals("act")){
                actingState(curroom, 1);
                return true;
            }
            else if(userChoice.equals("Rehearse")) {
                actingState(curroom, 0);
                return true;
            }
            
            else{
                System.out.println("Not a valid turn option please choose act or Rehearse");
                return false;
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("INVALID INPUT");
            return false;
        }
        
    }
    ////////////////////////////////////////////////
    /*
     * if player is in the trailer he can only move or stay idle
     * if he wants to move check for a valid move
     */
    ///////////////////////
    public void turn (Trailer curroom){
        printTurn();
        
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        boolean validInput = false;
        
        playerState ();
        if(!stayidle()){
            while(!validInput){
                moveOption(curroom.getAdjacentRooms());
                userChoice = console.nextLine();
                
                validInput = parseChoice(userChoice, curroom);
            }
        }
        System.out.println("End of turn");        
        printTurn();
    }
    
    private boolean parseChoice(String userChoice, Trailer curroom){
        String turnOption = "";        
        String roomChoice = "";
        try{
            turnOption = userChoice.substring(0,userChoice.indexOf(' '));
            roomChoice = userChoice.substring(userChoice.indexOf(' ')+1);
            if(turnOption.equals("move")){
                return validRoom(roomChoice, curroom.getAdjacentRooms());
            }
            else{
                System.out.println("Not a valid turn option or room please choose move room name");
                return false;
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("INVALID INPUT");
            return false;
        }
        
    }
    ////////////////////////////////////////////////
    /*
     * if player is in the casting office at the begining of the turn
     * ask if he wants to stay idle if no
     * ask to upgrade if yes ask for an upgrade and check if it is valid
     * ask after upgrading if player wants to move
     * if yes check for valid move
     */
    ///////////////////////
    public void turn (Office curroom){
        printTurn();
        
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        boolean validInput = false;
        
        playerState ();
        while(!validInput){
            if(chooseUpgrade()){
                validInput = upgradeRank(curroom);
            }
            else{
                validInput = true;
            }
        }
        validInput = false;
        if(!stayidle()){
            while(!validInput){
                moveOption(curroom.getAdjacentRooms());
                userChoice = console.nextLine();
                validInput = parseChoice(userChoice, curroom);
            }
        }
        System.out.println("End of turn");   
        printTurn();
    }
    
    private boolean parseChoice(String userChoice, Office curroom){
        String turnOption = "";        
        String roomChoice = "";
        try{
            turnOption = userChoice.substring(0,userChoice.indexOf(' '));
            roomChoice = userChoice.substring(userChoice.indexOf(' ')+1);
            if(turnOption.equals("move")){
                return validRoom(roomChoice, curroom.getAdjacentRooms());
            }
            else{
                System.out.println("Not a valid turn option or room please choose move room name");
                return false;
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("INVALID INPUT");
            return false;
        }
    }
    ////////////////////////////////////////////////
    /*
     * these functions are for printing turn options and the state at the begining of the turn
     */
    ///////////////////////
    private void printTurn(){
        System.out.println("--------------------------------------------------------------------");
    }
    private void actingOptions(Room curroom){
        System.out.printf("You are currently acting and must continue untill the card has been completed.\nRole: %s\n",this.role);
        System.out.printf("Total rehearlas for current role: %d, Budget for role: %s, Remaining Shot counters: %d\n", rehearsals.get(this.role),curroom.getBudget(), curroom.getShotCounter());
        System.out.println("Would you like to act or Rehearse.");
    }
    private void moveOption(ArrayList<String>availableMoves){
        System.out.printf("\nTurn options:\nMove: ");
        for (String s : availableMoves)
            System.out.printf("|%s| ", s);
        System.out.println();
    }
    private void starRoleOptions(ArrayList<String>starRoles){
        System.out.printf("\nAvailable roles:\nStar: ");
        for (String s : starRoles)
            if(s!=null){
                System.out.printf("|%s| ", s);
            }
            
    }
    private void bitRoleOption(ArrayList<String>bitRoles){
        System.out.printf("\nBit: ");
        for (String s : bitRoles)
            if(s!=null){
                System.out.printf("|%s| ", s);
            }
            System.out.println();
    }
    private void playerState (){
        System.out.printf("%s you are in the %s room\nRole: %s\nCredits: %d\nDollars: %d\nRank: %d\n",this.name, this.room, this.role, this.credits, this.dollars, this.rank);
    }
    ////////////////////////////////////////////////
    /*
     * check if the player wants to stay idle or not 
     * if yes return true will cause turn method to skip other options
     */
    ///////////////////////
    private boolean stayidle(){
        boolean validInput = false;
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        
        while(!validInput){
            System.out.printf("Would you like to stay idle in this room (y - n): ");
            userChoice = console.nextLine().toLowerCase();
            if(userChoice.equals("y")){
                return true;
            }
            else if(userChoice.equals("n")){
                return false;
            }
            else{
                System.out.println("Please enter y or n");
            }
        }
        return false;
    }
    ////////////////////////////////////////////////
    /*
     * similar to stayidle above but asking if they would like to upgrade or not
     * will only be called in the office
     */
    ///////////////////////
    private boolean chooseUpgrade(){
        boolean validInput = false;
        String userChoice = "";
        Scanner console = new Scanner(System.in);
        
        while(!validInput){
            System.out.println("Do you wish to upgrade (y-n) or are you just pasing through on you way to bigger and better things");
            userChoice = console.nextLine().toLowerCase();
            if(userChoice.equals("y")){
                return true;
            }
            else if(userChoice.equals("n")){
                return false;
            }
        }
        return false;
    }
    ////////////////////////////////////////////////
    /*
     * if the player chooses to upgrade their rank
     * check if the upgrade is valid then increase rank 
     * and decrease credits or dollars
     */
    ///////////////////////
    private boolean upgradeRank(Office curroom){
        String userChoice = "";
        String inputSplit[];
        int upgradeCost = 0;
        Scanner console = new Scanner(System.in);
        if(curroom.possibleUpgrades(this.dollars, this.credits, this.rank)){
            try{
                System.out.println("Choose an upgrade and currency");
                userChoice = console.nextLine();
                inputSplit = userChoice.split("\\s+");
                upgradeCost = curroom.checkValid(inputSplit[1], Integer.parseInt(inputSplit[2]));
                if(inputSplit[0].equals("upgrade")){
                    if(inputSplit[1].equals("$")){
                        if(this.dollars >= upgradeCost){
                            this.rank = Integer.parseInt(inputSplit[2]);
                            this.dollars -= upgradeCost;
                            System.out.printf("Congratulations new rank %d\n", this.rank);
                            return true;
                        }
                    }
                    else if(inputSplit[1].equals("cr")){
                        if(this.credits >= upgradeCost){
                            this.rank = Integer.parseInt(inputSplit[2]);
                            this.credits -= upgradeCost;
                            System.out.printf("Congratulations new rank %d\n", this.rank);
                            return true;
                        }
                    }
                    else{
                        return false;
                    }
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("INVALID INPUT");
                return false;
            }
        }
        else{
            return true;
        }
        return false;
    }   
    ////////////////////////////////////////////////
    /*
     * check if the role they want to take is valid
     * and their rank is high enough
     * idleBitStar coresponds to weather they are idle = 0 have a bit role = 1 or a star = 2 
     */
    ///////////////////////    
    private boolean validWork(String chooseRole, Room curroom){
        String fullName = searchRoles(curroom.getStarRoles(), chooseRole);
        if(!fullName.equals("")){
            this.idleBitStar = 2;
            curroom.takeStarRole(fullName);
            return true;
        }
        else{
            fullName = searchRoles(curroom.getBitRoles(), chooseRole);
            if(!fullName.equals("")){
                this.idleBitStar = 1;
                curroom.takeBitRole(fullName);
                return true;
            }
            else{
                System.out.println("Not a valid role please re-enter role name exactly as it apears above and that is equal to or lower than rank.");
            }
        }    
        return false;
    }
    
    ////////////////////////////////////////////////
    /*
     * check if the room they chose to move to is a valid room
     */
    ///////////////////////
    private boolean validRoom(String room, ArrayList<String>availableMoves){
        if(availableMoves.contains(room)){
            updateRoom(room);
            return true;
        }
        else{
            System.out.println("Not a valid room please re-enter room name exactly as it apears above.");
            return false;
        }
    }
    ////////////////////////////////////////////////
    /*
     * the room keeps track of the role "name of role"+">"+"rank of role"
     * this method returns an array list of all the roles that the player can take
     * 
     * ///////////////////////    
     *    private ArrayList<String> parseList(ArrayList<String> available){
     *        String[] parse;
     *        ArrayList<String> result = new ArrayList<String>();
     *        for(String role : available){
     *            try{
     *                parse = role.split(">");
     *                if(Integer.parseInt(parse[1]) <= this.rank){
     *                    result.add(parse[0]);
}
}
catch(IndexOutOfBoundsException e){
System.out.println("file input wrong");
}
}
return result;
}
*/
    ////////////////////////////////////////////////
    /*
     * if a player chooses a role to take search for it in the rooms roles 
     * if it exists and thier rank is high enough the return true
     */
    ///////////////////////
    private String searchRoles(ArrayList<String> available, String chooseRole){
        String[] parse;
        for(String role : available){
            try{
                parse = role.split(">");
                if(parse[0].equals(chooseRole)){
                    if(Integer.parseInt(parse[1]) <= this.rank){
                        this.role = role;
                        return role;
                    }
                }
            }
            catch(IndexOutOfBoundsException e){
                System.out.println("file input wrong");
            }
        }
        return "";
    }
    private void actingState(Room curroom,int choice){ 
        int num=0;     
        if(choice==0){
            updateRehearsals(1);
        }
        else{
            if(rehearsals.get(this.role)!=null){
                num=rehearsals.get(this.role);
            }
            int sum = dice.act(num);
            int budget=curroom.getBudget();
            if(idleBitStar==1){  
                if(sum>=budget){               
                    curroom.updateCounter();
                    if(curroom.getShotCounter()==0){
                        curroom.returnBitRole(this.role);
                        this.idleBitStar = 0;
                    }
                    System.out.println("You rolled a "+sum+"!"+" Congratulations you earned 1 credit and 1 dollar!");
                    updateDollars(1);
                    updateCredits(1);
                }
                else{
                    System.out.println("You rolled a "+sum+"!"+" Sorry you didnt make budget, but you still get 1 dollar!");
                    updateDollars(1);
                } 
            }
            else{
                if(sum>=budget){
                    curroom.updateCounter();
                    if(curroom.getShotCounter()==0){
                        curroom.returnStarRole(this.role);
                    }
                    System.out.println("You rolled a "+sum+"!"+" Congratulations you earned 2 credits!");
                    updateCredits(2);
                }
                else{
                    System.out.println("You rolled a "+sum+"!"+" Sorry your acting skills suck you get nothing");
                }     
            }
        }
    }
}

//call office with dollar credit rank





