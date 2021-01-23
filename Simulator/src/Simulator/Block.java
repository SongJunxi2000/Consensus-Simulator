package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class Block {
    class Streamlet_msg {
        String h;
        int e;
        String txs;
        public Streamlet_msg(String h, int e, String txs){
            this.h = h;
            this.e = e;
            this.txs = txs;
        }
    }
    Gson gson = new Gson();
    String h;
    int e;
    String txs;
    int height;
    String m;
    LinkedList<Block> children;
    public Block(){
        e = 0;
        h = null;
        txs = null;
        height = 0;
        m = "(null, 0, null)";
        children = new LinkedList<>();
    }
    public Block(String m, int height){
        try {
            Streamlet_msg  msg = gson.fromJson(m, Streamlet_msg.class);
            this.h = msg.h;
            this.e = msg.e;
            this.txs = msg.txs;
            this.height = height;
            this.m = m;
            children = new LinkedList<>();
        } catch (Error e){
            System.out.println("Failed to create a new Block" + e);
        }

    }
    public void add(Block block){
        children.add(block);
    }
    public int getHeight(){
        return height;
    }
    public int getEpoch(){
        return e;
    }
    public String getHash(){
        return h;
    }
    public String getTxs(){
        return txs;
    }
    public String toString(){
        return m;
    }
    public LinkedList<Block> getChildren(){
        return children;
    }
    public void finalize(Block block){
        children = new LinkedList<>();
        children.add(block);
    }
}
