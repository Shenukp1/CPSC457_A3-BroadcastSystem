/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose: 
*/


import java.util.HashMap;

public class LocalMemory{
    
    
    
    private HashMap<Integer, Integer> leMemory = new HashMap<>();// because we assign a variable to the value

    //we need to load(x)
    public int load(int variable){
        return leMemory.getOrDefault(variable,0);
    }


    //we need to store(x,v) - store value in x
    public void store(int variable, int value){
        leMemory.put(variable,value);
    }


}