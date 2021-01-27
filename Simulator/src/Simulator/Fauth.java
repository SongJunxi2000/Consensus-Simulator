package Simulator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Fauth {
    HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver
    LinkedList<Message> unready_message;
    Fsign sign;
    Adversary adv;
    private int[] players_key;
    int roundN;

    public Fauth(Adversary adversary,Fsign signature) {
        sign = signature;
        adv = adversary;

    }

    public void setAdKeys( int[] keys) {
        players_key = keys;
    }

    public void send(String msg, int receiver, int sender, int private_key, int sendRound) {
        if (players_key[sender] == private_key ) {
            //System.out.println(receiver+" "+sender+" "+msg);
            Message wrapper = new Message(msg, receiver, sender, sendRound);
            adv.receive(wrapper);
        }
    }

    public void update_receive(int round_number){
        roundN = round_number;
        HashMap<Integer, LinkedList<Message>> toAdd = adv.sendInThisRound(round_number);
        if (ready_messages == null) ready_messages = toAdd;
        else
        for (Map.Entry<Integer, LinkedList<Message>> entry : toAdd.entrySet()){
            int player = entry.getKey();
            LinkedList<Message> messages = entry.getValue();
            LinkedList<Message> temp = ready_messages.getOrDefault(player, new LinkedList<>());
            temp.addAll((messages));
        }
    }

    public LinkedList<Message> receive(int key, int id) {
        update_receive(roundN);
        if (players_key[id] != key) return null;
//        for (Message m : ready_messages.get(key))
//            System.out.println(m.getMsg());
        LinkedList<Message> result = ready_messages.get(id);
        ready_messages.remove(id);
        return result;
    }

}

