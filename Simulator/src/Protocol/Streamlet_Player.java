package Protocol;

import Simulator.*;
import com.google.gson.Gson;

import java.util.LinkedList;

public class Streamlet_Player extends Player {
    int epochN;
    BlockTree log;
    Gson gson = new Gson();
    StremletM proposed;
    Block blockBefore;
    Boolean isSender = false;
    LinkedList<Message> unprocessed_message;


    public Streamlet_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
        log = new BlockTree();
    }

    //based on the current longest notarized, the player decide whether to vote on the proposed block.
    // If the propose block is legal, the player signs the block and send it to everyone
    public void vote(){
        if(isSender) return;
        LinkedList<Message> voteBlocks = receive();
        System.out.println("receive's length"+voteBlocks.size());
        for(int i=0;i<voteBlocks.size();i++){
            //cBlock = (msg,receiver,sender,sendround)
            Message cBlock = voteBlocks.get(i);
//            System.out.println(cBlock.getSender()== engine.designated_sender);
            if(cBlock.getSender()== engine.designated_sender && sign.verification(cBlock.getMsg())){
                voteBlocks.remove(cBlock);
                unprocessed_message = voteBlocks;
                System.out.println("I'm here");
                //signM = ((h,e,txs),p,sig)
                signedM signM = gson.fromJson(cBlock.getMsg(),signedM.class);
//                System.out.println(cBlock.getMsg());
                //block = (h,e,txs)
                String block = signM.msg;
//                System.out.println(block);
                proposed = gson.fromJson(block,StremletM.class);
                LinkedList<Block> longest = log.getLongest();

                for(int j=0;j<longest.size();j++){
                    blockBefore = longest.get(i);
                    int curHash = blockBefore.m.hashCode();
//                    System.out.println(curHash);
//                    System.out.println(Integer.valueOf(proposed.h));
                    if(curHash == Integer.valueOf(proposed.h)){
                        for(int k = 0;k<total_num_of_players;k++){
                            send(sign(cBlock.getMsg()),k,engine.roundNumber);
//                            System.out.println(engine.roundNumber);
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
        LinkedList<Message> votes = unprocessed_message;
        LinkedList<Message> received = receive();
        if(votes!=null) votes.addAll(received);
        else votes = received;
        int[] countVotes = new int[total_num_of_players];
        int count = 0;
        for(int i=0;i<votes.size();i++){
            Message vote = votes.get(i);
//            System.out.println(countVotes[vote.getSender()]==0);
            if(countVotes[vote.getSender()]==0 && sign.verification(vote.getMsg())){
                signedM signM = gson.fromJson(vote.getMsg(),signedM.class);
                signedM pro = gson.fromJson(signM.msg,signedM.class);
                System.out.println(gson.toJson(proposed)+" "+pro.msg);
                if(gson.toJson(proposed).equals(pro.msg))
                {
                    System.out.println("count " +count);
                    countVotes[vote.getSender()]=1;
                    count++;
                }
            }
        }

        if(count*3/2>total_num_of_players){
            System.out.println("what is up");
            log.add(gson.toJson(proposed),blockBefore);
        }

    }
    // choose the longest notarized chain, and propose the block after it.
    public void propose(String input){
        isSender = true;
        blockBefore = log.getLongest().getFirst();
        String h = Integer.toString(blockBefore.m.hashCode());
        int e = engine.roundNumber;
        proposed = new StremletM(h,e,input);
        String msg = gson.toJson(proposed);
        String signedMsg = sign(msg);
        for(int i = 0;i<total_num_of_players;i++){
            send(signedMsg,i,e);
        }
    }

    public void action(String txs){
        if(txs != null){
            propose(txs);
        }
        else
            isSender = false;
        vote();
        notraize();
    }

    public BlockTree getLog() {
        return log;
    }
}
class StremletM{
    String txs;
    String h;
    int e;
    public StremletM(String h, int e, String txs){
        this.h = h;
        this.e = e;
        this.txs = txs;
    }
}


