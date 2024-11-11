/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose: 
*/

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadcastAgent implements Runnable{

    private DSM dsm;//so it has access to the DSM that created it
    private BroadcastSystem broadcastSystem;//it has access to the BCS(broadCastSystem) that is put into

    private LocalMemory localMemory;

    
    private ArrayList<Store> broadcast = new ArrayList<>();

    

    //constructor
    public BroadcastAgent(DSM dsm, BroadcastSystem broadcastSystem,LocalMemory localMemory){
        this.dsm = dsm;
        this.broadcastSystem = broadcastSystem;
        this.localMemory = localMemory;
        broadcastSystem.addBroadcastAgent(this);//the BroadCastAgent that was made now is apart of the BroadCastSystem
        
    }


    //In the thread, the broadcastAgent is constantly checking for broadcast from broadcastSystem
    @Override
    public void run() {

        while(true){
            
            if(!broadcast.isEmpty()){//basically says that the broadcastSystem must relay it to all
                //System.out.println("BroadcastAgent: Sending message to DSM...");
                //System.out.println("BroadcastAgent: Broadcast size is now: " + broadcast.size());
                for(int i = 0; i < broadcast.size(); i++) {
                    dsm.addMessage(broadcast.get(i));
                    
                }
                broadcast.clear();
                
            }

            try {
                Thread.sleep(100); // Adjust timing as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        
        }
    
    }


    /*
     * Function used by DSM to update all proccess 
     */
    public void broadcast(Store store){
        //System.out.println("BroadcastAgent: broadcasting!");

        //random delay for sending a broadcast to the broadcastsystem
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        //this - means that it take a ref of the one that sends it so we dont send it back to the same broadcaster
        broadcastSystem.broadcastUpdate(this, store);
    }




    
    public void receive(Store store) {
        if(store != null){
            broadcast.add(store);
            

        }
        
       
    }



   
    
}