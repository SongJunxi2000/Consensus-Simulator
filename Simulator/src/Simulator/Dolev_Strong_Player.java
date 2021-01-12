package Simulator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class Dolev_Strong_Player extends Player {
    HashSet<String> EXTR = new HashSet<String>();
    boolean isSender;
    public Dolev_Strong_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
        this.isSender = isSender;
    }
    public void receive_input(){
        if (isSender && engine.roundNumber == 0){
            EXTR.add("1");
            for (int i = 0; i < engine.numOfPlayers; i++)
                send(sign("1"),i,round_number);
        }
    }
    public ArrayList<String> valid_msg(String message){
        //verify signature
        ArrayList<String> res = new ArrayList<String>();
        signedM msg;
        Gson gson = new Gson();
        LinkedList<signedM> messages = gson.fromJson('['+message+']', new TypeToken<LinkedList<signedM>>(){}.getType());
        Iterator<signedM> iter = messages.iterator();
        boolean found_0 = false;
        boolean found_1 = false;
        HashMap<Integer, signedM> map = new HashMap<Integer, signedM>();
        HashSet<Integer> diff = new HashSet<Integer>();
        while (iter.hasNext()){
            msg = iter.next();
            if (!sign.verification(gson.toJson(msg)))
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
        while (iter.hasNext()){
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
        receive_input();
        update_round();
        LinkedList<Message> messages = receive();
        //if (messages == null) System.out.print(round_number);
        while (messages!=null&&messages.peek() != null) {
            Message msg = messages.remove();
            ArrayList<String> valid_msg = valid_msg(msg.getMsg());
            //System.out.println(valid_msg.size());
            for (String message : valid_msg)
                if (!EXTR.contains(message)) {
                    EXTR.add(message);
                    for (int i = 0; i < engine.numOfPlayers; i++)
                        send(msg.getMsg()+','+sign(message),i,round_number);
                }
        }
        if (round_number == engine.maxRound - 1) {
            //System.out.println(EXTR.size());
            if (EXTR.size() == 1) output(Integer.parseInt(EXTR.iterator().next()));
            else output(0);
        }
    }

    public HashSet<String> getEXTR(){
        return EXTR;
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

    static String check_validity(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        if(!honest_players_id.contains(designated_sender)) return "NA";
        return Boolean.toString(check_output(designated_sender, honest_players_id, outputs));
    }

    static String check_consistency(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        if(honest_players_id == null) return "false";
        int bit = outputs[honest_players_id.getFirst()];
        for(int i = 1;i<honest_players_id.size();i++){
            if(bit!= honest_players_id.get(i)) return "false";
        }
        return "true";
    }
}
