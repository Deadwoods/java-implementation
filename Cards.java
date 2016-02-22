public class Cards{
   //card objects get created by deck 
    private String card_info;
    private String scene;
    private int budget;
    private String[] array;
    private String[] rolesArray;
    //split card info by ///
    public Cards(String card_info){
        this.array = card_info.split("///");       
        this.scene = array[0];
        this.rolesArray=new String[array.length-2];
        this.budget = Integer.parseInt(array[1]);
        int j=0;
        for(int i=2;i<array.length;i++){
            this.rolesArray[j] = array[i];
            j++;
        }
        
        
    } 
    
    
    public String getScene(){
        return this.scene;
    }
    public int getBudget(){
        return this.budget;
    }
    public String[] getRolesArray(){
        return this.rolesArray;
    }
    
}