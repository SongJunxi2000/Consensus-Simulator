import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

public class Dolev_Strong_Player extends Player {
    HashSet<String> EXTR = new HashSet<String>();

    public Dolev_Strong_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        super(key, id, authenticate, signature, engine, num);
    }
    public boolean valid_msg(LinkedList<signedM> messages){
        //verify signature
        signedM msg;
        Iterator<signedM> iter = messages.iterator();
        boolean found = false;
        HashMap<Integer, signedM> map = new HashMap<Integer, signedM>();
        HashSet<Integer> diff = new HashSet<Integer>();
        while (iter.next() != null){
            msg = iter.next();
            if (!sign.verification(msg.msg))
                messages.remove(msg);
            if (msg.player == engine.designated_sender)
                found = true;
            if (map.containsKey(msg.player)) {
                if (!map.get(msg.player).msg.equals(msg.msg))
                    diff.add(msg.player);
            } else map.put(msg.player, msg);
        }
        HashMap<String,Integer> count = new HashMap<String, Integer>();
        String str=messages.get(0).msg;
        int max = -1;
        iter = messages.iterator();
        while (iter.next()!=null){
            msg = iter.next();
            if (diff.contains(msg.player))
                messages.remove(msg);
            else {
                count.put(msg.msg, count.getOrDefault(msg.msg, 0)+1);
                if (max<count.get(msg.msg)) {
                    max = count.get(msg.msg);
                    str = msg.msg;
                }
            }
        }
        if (!found) return false;
        return max>engine.roundNumber;
    }
    public void action() {
        LinkedList<Message> messages = receive();
        while (messages.peek() != null) {
            Message msg = messages.remove();
            if (!EXTR.contains(msg.getMsg())&&valid_msg(parse(msg.getMsg()))) {
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
}
