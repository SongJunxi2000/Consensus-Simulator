package Simulator;

import com.google.gson.Gson;

import java.util.LinkedList;

public class BlockTree{
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
