/*
Name:Shenuk Perera
UCID:30086618
Name:
UCID:
Class purpose:
*/

public class TokenRingAgent implements Runnable {

    


    private boolean active = false;
    private boolean hasToken = false;
    private int tokenId = -1;
    
    private TokenRing tokenRing; 


    private int tAgent_id;
    private int numAgent;


    //the constructor
    public TokenRingAgent(TokenRing tokenRing, int id, int numProccesses){
        this.tAgent_id = id;
        this.numAgent = numProccesses;
        this.tokenRing = tokenRing;
        tokenRing.registerAgent(this);
        

    }

    public boolean getActivity(){
        return active;
    }
    

    public void isActive(){//says the agent is active on the ring
        active = true;
    }

    public boolean tAgentHasToken(){
        return hasToken;
    }


    @Override
    public void run(){
        //RACE CONDITION
        while(true){

        }
    }


    public int getCurrentId(){// returns the current id of agent
        return tAgent_id;
    }

    public int getRingPredecessor(){//returns the index of the previous agent
        
        if(tAgent_id == 0){
            int ringPredecessor = numAgent - 1;
            return ringPredecessor;

        } else{
            return tAgent_id - 1;
        }
        
    }

    public int getRingSuccesor(){//returns the index of the next agent
        int ringSuccessor = (tAgent_id + 1) % numAgent;
        return ringSuccessor;
    }

    public synchronized int recieveToken(Token t){
        hasToken = true;
        return tokenId = t.getId();
    }

    public synchronized void sendToken(Token t){
        hasToken =false;
        tokenId = -1;
        tokenRing.passToken(t, getRingSuccesor());
    }




}