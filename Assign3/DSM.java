/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose:
*/

import java.util.ArrayList;
import java.util.HashMap;

public class DSM implements Runnable{


    private LocalMemory localMemory;
    private BroadcastAgent broadcastAgent;
    private BroadcastSystem broadcastSystem;//reference created to hold the address of the main broadcastsystem in Main to use it
    private Store store; // store message
    private ArrayList<Store> messages = new ArrayList<>();

    private Thread broadcastThread;
    private TokenRingAgent tAgent;


    //we want to create a broadcastagen and a local memory for the process
    public DSM(BroadcastSystem broadcastSystem){
        this.localMemory = new LocalMemory();
        this.broadcastAgent = new BroadcastAgent(this,broadcastSystem,localMemory);
        this.broadcastSystem = broadcastSystem;
        this.store = store;
        this.broadcastThread = new Thread(broadcastAgent);
        
 
    }

    public void addTAgent(TokenRingAgent tokenRingAgent){
        tAgent = tokenRingAgent;
    }


    /*
     * What does this thread do: check if it has an store message, if it does, store it to local memory
     *  store message - from other processes 
     */
    @Override
    public void run() {

        while(true){
            if(!messages.isEmpty()){//check if there is a store inside
                for (int i = 0; i < messages.size(); i++) {
                    localMemory.store(messages.get(i).getVariable(),messages.get(i).getValue());//stores the stored message recieved in memory
                }
                messages.clear();
            }
        }
        
    }



    //this is to load from Local memory
    public synchronized int load(int variable){
        return localMemory.load(variable);
    }


    //sends a store request to the broadcastSystem
    public synchronized void store(int variable, int value){
        while(!tAgent.tAgentHasToken() && tAgent.getActivity()){}
        localMemory.store(variable,value);
        broadcastAgent.broadcast(new Store(variable, value));
    }

    public void addMessage(Store store){
        messages.add(store);
    }

    public void startBroadcastThread(){
        broadcastThread.start();
        
    }

    public void joinBroadcastThread() throws InterruptedException {
        broadcastThread.join();
    }

  





}