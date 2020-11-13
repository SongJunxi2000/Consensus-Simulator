import java.util.HashSet;
import java.util.LinkedList;

public class Dolev_Strong_Protocol i{
    LinkedList<Player> honest_players;
    Player sender;
    Fauth authenticate;
    Fsign signature;
    Simulation_engine engine;
    public Dolev_Strong_Protocol(LinkedList<Player> honest_players, Player sender,
    Fauth authenticate, Fsign signature, Simulation_engine engine){
        this.honest_players = honest_players;
        this.sender = sender;
        this.authenticate = authenticate;
        this.signature = signature;
        this.engine = engine;

    }
    HashSet<String> EXTR = new HashSet<String>();
    public void action(){
        LinkedList<Message> messages = receive();
        while (messages.peek()!=null){
            Message msg = messages.remove();
            if (!EXTR.contains(msg.getMsg())){
                EXTR.add(msg.getMsg());
                for (int i = 0; i<engine.numOfPlayers;i++)
                    send(sign(null, i, engine.roundNumber, msg),i);
            }
        }
        if (round_number == engine.maxRound){
            if (EXTR.size()==1) output( Integer.parseInt(EXTR.iterator().next()));
            else output(0);
        }
    }
}
