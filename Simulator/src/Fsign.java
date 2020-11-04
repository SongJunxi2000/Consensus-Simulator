import java.util.HashMap;
import java.util.Random;

public class Fsign {
    private int[] players_key;

    public void setKeys(int[] keys) {
        players_key = keys;
    }
    //Integer: sender ID; Long: signature; Message: message to be signed
    private HashMap<Integer, HashMap<Long, Message>> signed_messages;
    Random rand = new Random();

    protected Message sign(String msg, int receiver, int sender, int RN, int private_key, Message msg_object) {
        if (players_key[sender] != private_key) return null;
        if (!signed_messages.containsKey(sender)) {
            HashMap<Long, Message> player_all_signed_msg = new HashMap<Long, Message>();
            long sig = rand.nextLong();
            Message tem = (msg == null) ?
                    new Message(msg_object, receiver, sender, RN, sig) :
                    new Message(msg, receiver, sender, RN, sig);
            player_all_signed_msg.put(sig, tem);
            signed_messages.put(sender, player_all_signed_msg);
            return tem;
        } else {
            HashMap<Long, Message> player_all_signed_msg = signed_messages.get(sender);
            long sig = rand.nextLong();
            while (signed_messages.containsKey(sig)) {
                sig = rand.nextLong();
            }
            Message tem = (msg == null) ?
                    new Message(msg_object, receiver, sender, RN, sig) :
                    new Message(msg, receiver, sender, RN, sig);
            player_all_signed_msg.put(sig, tem);
            return tem;
        }
    }

    public boolean verification(Message msg, int public_key, long sig) {
        HashMap<Long, Message> player_all_signed_msg = signed_messages.get(public_key);
        Message message = player_all_signed_msg.get(sig);
        return message == msg;
    }
}

