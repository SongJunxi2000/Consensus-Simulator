import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class Dolev_Strong_Player extends Player {
    HashSet<String> EXTR = new HashSet<String>();

    public Dolev_Strong_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        super(key, id, authenticate, signature, engine, num);
    }
    public ArrayList<String> valid_msg(String message){
        //verify signature
        ArrayList<String> res = new ArrayList<String>();
        signedM msg;
        Gson gson = new Gson();
        LinkedList<signedM> messages = gson.fromJson(message, new TypeToken<LinkedList<signedM>>(){}.getType());
        Iterator<signedM> iter = messages.iterator();
        boolean found_0 = false;
        boolean found_1 = false;
        HashMap<Integer, signedM> map = new HashMap<Integer, signedM>();
        HashSet<Integer> diff = new HashSet<Integer>();
        while (iter.next() != null){
            msg = iter.next();
            if (!sign.verification(gson.toJson(msg)));
                messages.remove(msg);
            if (msg.player == engine.designated_sender){
                if (msg.msg.equals("0"))
                    found_0 = true;
                if (msg.msg.equals("1"))
                    found_1 = true;
            }
            if (map.containsKey(msg.player)) {
                if (!map.get(msg.player).msg.equals(msg.msg))
                    diff.add(msg.player);
            } else map.put(msg.player, msg);
        }
        int count_0 = 0;
        int count_1 = 0;
        iter = messages.iterator();
        while (iter.next()!=null){
            msg = iter.next();
            if (diff.contains(msg.player))
                messages.remove(msg);
            else {
                if (msg.msg.equals("0"))
                    count_0 ++;
                if (msg.msg.equals("1"))
                    count_1 ++;
            }
        }
        if (found_0 && count_0 >= engine.roundNumber) res.add("0");
        if (found_1 && count_1 >= engine.roundNumber) res.add("1");
        return res;
    }
    public void action() {
        LinkedList<Message> messages = receive();
        while (messages.peek() != null) {
            Message msg = messages.remove();
            ArrayList<String> valid_msg = valid_msg(msg.getMsg());
            for (String message : valid_msg)
                if (!EXTR.contains(message)) {
                    EXTR.add(msg.getMsg());
                    for (int i = 0; i < engine.numOfPlayers; i++)
                        send(sign(msg.getMsg()),i,round_number);
                }
        }
        if (round_number == engine.maxRound) {
            if (EXTR.size() == 1) output(Integer.parseInt(EXTR.iterator().next()));
            else output(0);
        }
    }

    static boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        Iterator honest_player = honest_players_id.iterator();
        int bit = outputs[(int)honest_players_id.getFirst()];
        if(honest_players_id.contains(designated_sender)){
            bit = outputs[designated_sender];
        }
        while(honest_player.hasNext()){
            if (outputs[(int)honest_player.next()]!= bit) return false;
        }
        return true;
    }
}
