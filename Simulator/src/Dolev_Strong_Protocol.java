import java.util.HashSet;
import java.util.LinkedList;

public class Dolev_Strong_Protocol implements Protocol{
    Player sender;
    Fauth authenticate;
    Fsign signature;
    Simulation_engine engine;
    public void action(Player player){
        LinkedList<Message> messages = player.receive();
        HashSet<String> EXTR = player.getMem().EXTR;
        while (messages.peek()!=null){
            Message msg = messages.remove();
            if (!EXTR.contains(msg.getMsg())){
                EXTR.add(msg.getMsg());
                for (int i = 0; i<engine.numOfPlayers;i++)
                    player.send(player.sign(null, i, engine.roundNumber, msg),i);
            }
        }
        if (player.round_number == player.engine.maxRound){
            if (EXTR.size()==1) player.output( Integer.parseInt(EXTR.iterator().next()));
            else player.output(0);
        }
    }
    public String input(int id, int round_number){
        return "0";
    }
    public boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        int prev = outputs[designated_sender];
        for (Integer id: honest_players_id){
            if (!(outputs[designated_sender]==outputs[id])||!(outputs[id]==prev))
                return false;
            prev = outputs[id];
        }
        return true;
    }
}
class Memory {
    HashSet<String> EXTR = new HashSet<String>();
}
