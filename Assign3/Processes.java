/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose: 

*/

import java.util.List;

public class Processes implements Runnable {

    private int id;//id of the processes

    //classes the process creates
    private DSM dsm;
  


    
    private volatile int[] flags;//contians the flags it self and others

    private int n; //each process will know how many processes are created

    private CriticalSection cs;

    private Thread dsmThread;

    private TokenRingAgent tokenRingAgent;
    

    public Processes(int id,DSM dsm, TokenRing tokenRing, CriticalSection cs, int[] flags, int n){
        this.id = id;
        this.dsm = dsm;
        this.dsmThread = new Thread(dsm);
        this.flags = flags;
        this.n = n;// number of processes there are
        this.cs = cs;
        this.tokenRingAgent = new TokenRingAgent(tokenRing, id, n);
        dsm.addTAgent(tokenRingAgent);

    }


    /*
     * What does this thread do:
     * 
     */
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) { 
            
            iWantToEnterCS();
            
            try {
                Thread.sleep(100); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            exitCS();
            
        }
    }

    public void iWantToEnterCS() {
        System.out.println("Processes: Processor " + id + " want to enter CS.");
        for (int k = 0; k < n - 1; k++) {
            flags[id] = k; //flag[i] = k, says process id want to enter CS at level k
            dsm.store(k, id); // turn[k] = i, says that it is id turn at level k 
            
            
            while (checkAboveLevel(k) && dsm.load(k) == id) {
                //Thread.yield();
                //System.out.println("Processes: Processor " + id + " is stuck!"); 
            }
            
        }
        cs.enter(id); 
    }

    public void exitCS() {
        cs.exit(id); 
        flags[id] = -1; 
    }

    private boolean checkAboveLevel(int level) {
        level = level +1;
        for (int j = 0; j < n; j++) {
            
            //System.out.println("Processes: ID: "+id);
            //System.out.println("Processes: Flag: "+flags[j]);
            if ((j != id) && (flags[j] >= level)) {// uh oh

                //System.out.println("Processes: id: " + id + " j: "+j);
                return true;
            }
        }
        
        return false;
    }

    public void startDsmThread() {
        dsmThread.start();
        dsm.startBroadcastThread();
    }
    public void joinDsmThread() throws InterruptedException {
		dsmThread.join();
        dsm.joinBroadcastThread();
        
	}



    //gets the id of the processor
    public int getID(){
        return id;
    }

    //sets the id of the processor
    public void setID(int change){
        id = change;
    }


	








}