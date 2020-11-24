import java.util.HashMap;
import java.util.Random;

class signedM{
    public signedM(String m, int p, long s){
        msg = m;
        player=  p;
        sig = s;
    }
    String msg;
    int player;
    long sig;
}

public class Fsign {
    private int[] players_key;

    public void setKeys(int[] keys) {
        players_key = keys;
    }
    //Integer: sender ID; Long: signature; Message: message to be signed
    private HashMap<Integer, HashMap<Long, String>> signed_messages;
    Random rand = new Random();

    protected signedM sign(String msg, int sender, int private_key) {
        if (players_key[sender] != private_key) return null;
        if (!signed_messages.containsKey(sender)) {
            HashMap<Long, String> player_all_signed_msg = new HashMap<Long, String>();
            long sig = rand.nextLong();
            player_all_signed_msg.put(sig, msg);
            signed_messages.put(sender, player_all_signed_msg);
            return new signedM(msg,sender,sig);
        } else {
            HashMap<Long, String> player_all_signed_msg = signed_messages.get(sender);
            long sig = rand.nextLong();
            while (signed_messages.containsKey(sig)) {
                sig = rand.nextLong();
            }
            player_all_signed_msg.put(sig, msg);
            return new signedM(msg,sender,sig);
        }
    }

    public boolean verification(signedM m) {
        HashMap<Long, String> player_all_signed_msg = signed_messages.get(m.player);
        String message = player_all_signed_msg.get(m.sig);
        return message == m.msg;
    }
}

