import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Random;


public class Fsign {
    private int[] players_key;
    Gson gson = new Gson();

    public void setKeys(int[] keys) {
        players_key = keys;
    }
    //Integer: sender ID; Long: signature; Message: message to be signed
    private HashMap<Integer, HashMap<Long, String>> signed_messages;
    Random rand = new Random();

    protected String sign(String msg, int sender, int private_key) {
        if (players_key[sender] != private_key) return null;
        long sig = rand.nextLong();
        if (!signed_messages.containsKey(sender)) {
            HashMap<Long, String> player_all_signed_msg = new HashMap<Long, String>();
            player_all_signed_msg.put(sig, msg);
            signed_messages.put(sender, player_all_signed_msg);
        } else {
            HashMap<Long, String> player_all_signed_msg = signed_messages.get(sender);
            while (signed_messages.containsKey(sig)) {
                sig = rand.nextLong();
            }
            player_all_signed_msg.put(sig, msg);
        }
        long finalSig = sig;
        class signedM{
            private String message = msg;
            private long signature = finalSig;
            private int player = sender;
        }
        signedM m = new signedM();
        return gson.toJson(m);
    }

    public boolean verification(String m) {
        MyType obj = gson.fromJson(m, MyType.class);
        HashMap<Long, String> player_all_signed_msg = signed_messages.get(m.player);
        String message = player_all_signed_msg.get(m.sig);
        return message == m.msg;
    }
}

