import java.util.HashMap;
import java.util.*;

public class Room{
    
    private int shotcounter1;
    private int shotcounter;
    private HashMap<String,String> starroles = new HashMap<String, String>();
    private HashMap<String,String> bitroles = new HashMap<String, String>();
    private int budget;   
    private String[] adjacentrooms;
    private String name;
    private Cards card;
    private String scene;
    
    public Room(String name,int shotcounter, String adjacentrooms, String bitroles, Cards card){  
        this.shotcounter1=shotcounter;
        this.shotcounter=shotcounter;
        this.name=name;
        this.adjacentrooms=adjacentrooms.split("///");
        String[] x=bitroles.split("///"); 
        for(int i=0;i<x.length;i++){
            this.bitroles.put(x[i],"true");
        } 
        getCardInfo(card);   
        
    }
    
    public int getBudget(){
        return this.budget;
    }
    
    public ArrayList<String> getAdjacentRooms(){
        ArrayList<String> adjacentrooms1 = new ArrayList<String>();
        for(int i=0;i<this.adjacentrooms.length;i++){
            adjacentrooms1.add(this.adjacentrooms[i]);
        }
        return adjacentrooms1;
    }
    
    public HashMap<String,String> getStarMap(){
        return this.starroles;   
    }
    
    public void takeStarRole(String role){
        this.starroles.put(role,"false");      
    }
    
    public void takeBitRole(String role){
        this.bitroles.put(role,"false");
    }
    
    public void returnBitRole(String role){
        this.bitroles.put(role,"true");
        
    }
    
    public void returnStarRole(String role){
        this.starroles.put(role,"true");
    }
    
    public ArrayList<String> getStarRoles(){
        ArrayList<String> starroles1=new ArrayList<String>();     
        for (String key : this.starroles.keySet()){
            if(this.starroles.get(key).equals("true")){
                starroles1.add(key);            
            }
        }
        return starroles1;
    }
    
    public void flipCard(){
        for (String key : this.starroles.keySet()){
            starroles.put(key,"false");           
        }
        for (String key : this.bitroles.keySet()){
            bitroles.put(key,"false");           
        }
    }
    public void removeStarRoles(){
        this.starroles.clear();
    }
    
    public int getNumStarRoles(){
        return this.starroles.size();
    }
    
    public ArrayList<String> getBitRoles(){
        ArrayList<String> bitroles1=new ArrayList<String>();
        
        for (String key : this.bitroles.keySet()){
            if(this.bitroles.get(key).equals("true")){
                bitroles1.add(key);            
            }
        }
        return bitroles1;
    }
    
    public void getCardInfo(Cards card){
        this.card = card;
        this.budget=card.getBudget();
        this.scene=card.getScene();
        String[] y=card.getRolesArray();
        for(int i=0;i<y.length;i++){
            this.starroles.put(y[i],"true");
        }
    }
    
    public int getShotCounter(){
        return this.shotcounter;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void updateCounter(){
        this.shotcounter-=1;
    }
    
    public void resetCounter(){
        for (String key : this.bitroles.keySet()){
            bitroles.put(key,"true");           
        }
        shotcounter=shotcounter1;      
    }
    
    
    
    
}