import java.util.HashSet;
import java.util.LinkedList;

public class Dolev_Strong_Player extends Player {
    HashSet<String> EXTR = new HashSet<String>();

    public Dolev_Strong_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        super(key, id, authenticate, signature, engine, num);
    }

    public void action() {
        LinkedList<Message> messages = receive();
        while (messages.peek() != null) {
            Message msg = messages.remove();
            if (!EXTR.contains(msg.getMsg())) {
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
