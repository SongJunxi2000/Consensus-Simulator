package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class Streamlet_Player extends Player {
    public Streamlet_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
    }

    int epochN;
    BlockTree log;
    Gson gson = new Gson();
    Block proposed;

    //based on the current longest notarized, the player decide whether to vote on the proposed block.
    // If the propose block is legal, the player signs the block and send it to everyone
    public void vote(){
        LinkedList<Message> voteBlocks = receive();
        for(int i=0;i<voteBlocks.size();i++){
            Message cBlock = voteBlocks.get(i);
            if(cBlock.getSender()== engine.designated_sender && sign.verification(cBlock.getMsg())){
                proposed = new Block(cBlock.getMsg());
                LinkedList<Block> longest = log.getLongest();
                for(int j=0;j<longest.size();j++){
                    int curHash = longest.get(j).m.hashCode();
                    if(curHash == Integer.valueOf(proposed.h)){
                        for(int k = 0;k<total_num_of_players;k++){
                            send(sign(cBlock.getMsg()),i,engine.roundNumber);
                        }
                        break;
                    }
                }
                break;
            }

        }



    }
    //if the proposed block receives more than 2n/3 votes, add it to the longest notarized chain
    public void notraize(){
        LinkedList<Message> votes = receive();


    }
    //if the longest noatarized chain has 3 consective blocks, finalize in the block
    public void stream_finalize(){

    }
    // choose the longest notarized chain, and propose the block after it.
    public void propose(String input){
        Block longest = log.getLongest().getFirst();
        String h = Integer.toString(longest.m.hashCode());
        int e = engine.roundNumber;
        StremletM proposed = new StremletM(h,e,input);
        String msg = gson.toJson(proposed);
        for(int i = 0;i<total_num_of_players;i++){
            send(sign(msg),i,e);
        }
    }
}


