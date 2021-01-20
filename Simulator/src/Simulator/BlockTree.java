package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class BlockTree{
    class Block{
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
        public Block(String h, int e, String txs, int height){
            this.h = h;
            this.e = e;
            this.txs = txs;
            this.height = height;
            m = "(" + h + "," + String.valueOf(e) + "," + txs + ")";
            children = new LinkedList<>();
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
    }
    Block gensis;
    int longest;
    LinkedList<Block> last;
    public BlockTree (){
        gensis = new Block();
        longest = 0;
        last = new LinkedList<>();
    }
    public LinkedList<Block> getLongest(){
        return last;
    }
    public void notarized (Block block, LinkedList<Block> blocks){
        blocks.add(block);

    }
}
