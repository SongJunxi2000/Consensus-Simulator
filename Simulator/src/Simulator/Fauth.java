package Simulator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Fauth {
    HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver id
    Fsign sign;
    Adversary adv;
    private int[] players_key;
    int roundN;

    /**
     * Construct and initialize an authentication channel with the adversary and Fsign objects
     * @param adversary an adversary object
     * @param signature a fauth object
     * @return a newly constructed Fauth object
     */
    public Fauth(Adversary adversary,Fsign signature) {
        sign = signature;
        adv = adversary;

    }

    /**
     * initialize players' keys
     * @param keys keys initialized by players
     */
    public void setAdKeys( int[] keys) {
        players_key = keys;
    }

    /**
     * If the private key matches the sender's key, Fauth wrap the msg with the sender, receiver and
     * round information and sent it to the adversary.
     * @param msg self-explanatory
     * @param receiver self-explanatory
     * @param sender self-explanatory
     * @param private_key self-explanatory
     * @param sendRound the round the message is sent.
     */
    public void send(String msg, int receiver, int sender, int private_key, int sendRound) {
        if (players_key[sender] == private_key ) {
            Message wrapper = new Message(msg, receiver, sender, sendRound);
            adv.receive(wrapper);
        }
    }

    /**
     * Update the current round number of Fauth, receive messages from the adversary and add them to the ready message
     * @param round_number current round number
     */
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
            ready_messages.put(player,temp);
        }
    }

    /**
     * update the ready message; if the player's key matches, return the ready messages for player id.
     * @param key the player's private key
     * @param id the play's id
     * @return a list of messages that is available for the player id in the current round.
     */
    public LinkedList<Message> receive(int key, int id) {
        update_receive(roundN);
        if (players_key[id] != key) return null;
        LinkedList<Message> result = ready_messages.getOrDefault(id,new LinkedList<>());
        ready_messages.remove(id);
        return result;
    }

}

