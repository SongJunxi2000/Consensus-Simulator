import java.util.HashMap;
import java.util.LinkedList;

public class Fauth {
    HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver
    LinkedList<Message> unready_message;
    Fsign sign;
    Adversary adv;
    private int[] players_key;

    public Fauth(Adversary adversary,Fsign signature) {
        sign = signature;
        adv = adversary;
    }

    public void setAdKeys( int[] keys) {
        players_key = keys;
    }

    public void send(Message msg, int private_key) {
        if (players_key[msg.getSender()] == private_key ) {
            adv.receive(msg);
        }
    }

    public LinkedList<Message> receive(int key, int id) {
        if (players_key[id] != key) return null;
        return ready_messages.get(key);
    }

    public void update() {
        ready_messages = adv.sendInThisRound();
    }
}

