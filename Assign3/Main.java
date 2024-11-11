/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose: 
*/

import java.util.ArrayList;
import java.util.List;

public class Main {




    public static void main(String[] args) throws InterruptedException {


        

        int numProccesses = 5;//allows you to change how many processes are created
        
        Processes[] processesArray = createDsmAndProcesses(numProccesses);//what creates the equal processor to dsms


        List<Thread> processThreads = new ArrayList<>();
        for (int i = 0; i < processesArray.length; i++) {
            
            Processes process = processesArray[i];

            Thread p = new Thread(process);
            
            process.startDsmThread();
            p.start();
            processThreads.add(p);
            
            System.out.println("Main: Started everything!");
            
            
            System.out.println("Main: Joined everything!");
        }
        

        for (int i = 0; i < processesArray.length; i++) {
            Processes process = processesArray[i];
            process.joinDsmThread();
            processThreads.get(i).join();
            System.out.println("Main: Joined everything for process: " + i);
        }
        
        




       
    }




     /*
        function create a dsm equal to the amount of processors/processes
        numEqual - number of processes you want to create
     */
    public static Processes[] createDsmAndProcesses(int numProccesses) {

        BroadcastSystem broadcastSystem = new BroadcastSystem();//create the BroadcastSystem for all the dsms
        //create a TokenRing for all the proccessors
        Token token = new Token(30086618);
        TokenRing tokenRing = new TokenRing(token);

        CriticalSection cs = new CriticalSection();
        
        int[] flags = new int[numProccesses];//create an array to hold the flags of processes created 


        //assuming every processor is made in order of 0 to n-1
        for(int i=0;i<numProccesses;i++){
            flags[i]= -1;//every flag starts as false
        }
        DSM[] dsmArray = new DSM[numProccesses];//dsm being created
        for (int i = 0; i < numProccesses; i++) {
            dsmArray[i] = new DSM(broadcastSystem);//creating a dsm with the same broadcastSystem 
        }

        //creating the processes and adding the dsms to them
        Processes[] processesArray = new Processes[numProccesses];
        for (int i = 0; i < numProccesses; i++) {
            processesArray[i] = new Processes(i,dsmArray[i],tokenRing,cs,flags,numProccesses);
        }

        System.out.println("finished creating the process/dsm!");
        //then returning to access all the process 
        return processesArray;
    }

}