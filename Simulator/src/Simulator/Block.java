package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class Block {
    String h;
    int e;
    String txs;
    String m;
    public Block(String m){
            this.m = m;
            Gson gson = new Gson();
    }
}
