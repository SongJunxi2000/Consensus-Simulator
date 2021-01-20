package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class Streamlet_Player extends Player {
    public Streamlet_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
    }
    //based on the current longest notarized, the player decide whether to vote on the proposed block.
    // If the propose block is legal, the player signs the block and send it to everyone
    public void vote(){

    }
    //if the proposed block receives more than 2n/3 votes, add it to the longest notarized chain
    public void notraize(){

    }
    //if the longest noatarized chain has 3 consective blocks, finalize in the block
    public void stream_finalize(){

    }
    // choose the longest notarized chain, and propose the block after it.
    public void propose(String input){

    }
}
