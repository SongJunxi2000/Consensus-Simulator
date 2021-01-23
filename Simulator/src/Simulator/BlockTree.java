package Simulator;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.LinkedList;

public class BlockTree{
    Block gensis;
    int longest;
    LinkedList<Block> last;
    LinkedList<Block> log;
    HashMap<String, Block> map;
    public BlockTree (){
        gensis = new Block();
        longest = 0;
        last = new LinkedList<>();
        map = new HashMap<>();
        log = new LinkedList<>();
        last.add(gensis);
    }
    public LinkedList<Block> getLongest(){
        return last;
    }
    public void add(String m, Block notarized){
        Block block = new Block(m, notarized.height+1);
        map.put(Integer.toString(m.hashCode()),block);
        notarized.add(block);
        Block prev = notarized.h == null ? null : map.get(notarized.h);
        finalize(prev, notarized, block);
        if (block.getHeight()>longest) {
            last = new LinkedList<>();;
            last.add(block);
            longest = block.getHeight();
        } else if (block.getHeight() == longest){
            last.add(block);
        }
    }
    public void finalize(Block first, Block second, Block last){
        if (first == null) return;
        if (first.getEpoch() + 1  == second.e && second.getEpoch() + 1 ==last.getEpoch()){
            Block current = second;
            Block after = last;
            while (!log.contains(current)){
                log.add(current);
                current.finalize(after);
                after = current;
                current = map.get(current.getHash());
            }
            this.last = new LinkedList<>();
        }
    }
}
