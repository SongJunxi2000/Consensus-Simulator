package Simulator;

import com.google.gson.Gson;

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
    Gson gson = new Gson();

    public void setKeys(int[] keys) {
        players_key = keys;
    }
    //Integer: sender ID/ public key; Long: signature; Simulator.Message: message to be signed
    private HashMap<Integer, HashMap<Long, String>> signed_messages;
    Random rand = new Random();

    public Fsign(){
        signed_messages = new HashMap<>();
    }

    protected String sign(String msg, int sender, int private_key) {
        //for test
        if (players_key[sender] != private_key) return null;
        long sig = rand.nextLong();
        if (!signed_messages.containsKey(sender)) {
            HashMap<Long, String> player_all_signed_msg = new HashMap<Long, String>();
            player_all_signed_msg.put(sig, msg);
            signed_messages.put(sender, player_all_signed_msg);
        } else {
            HashMap<Long, String> player_all_signed_msg = signed_messages.get(sender);
            while (player_all_signed_msg.containsKey(sig)) {
                sig = rand.nextLong();
            }
            player_all_signed_msg.put(sig, msg);
        }
        signedM m = new signedM(msg, sender,sig);
        return gson.toJson(m);
    }

    public boolean verification(String verified_m) {
        try {
            signedM m = gson.fromJson(verified_m, signedM.class);
            HashMap<Long, String> player_all_signed_msg = signed_messages.get(m.player);
            String message = player_all_signed_msg.get(m.sig);
            //System.out.println(message.equals(m.msg));
            return message.equals(m.msg);
        }
        catch (Exception e){
            return false;
        }
    }
}

