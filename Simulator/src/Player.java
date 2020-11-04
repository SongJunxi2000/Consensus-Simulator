import java.util.LinkedList;

public class Player {
    private int player_private_key;
    public int player_id;
    public int round_number;
    public Fauth auth;
    public Fsign sign;
    private Simulation_engine engine;

    public Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine){
        player_private_key = key;
        player_id = id;
        auth = authenticate;
        sign = signature;
        this.engine = engine;
    }
    public void update_round(int RN, int private_key){
        if(private_key == player_private_key) {
            round_number = RN;
        }
    }
    public void send(String msg, int receiver){
        Message message = sign.sign(msg, player_id, player_private_key,round_number,player_private_key);
        auth.send(message, player_private_key);
    }
    public LinkedList<Message> receive(){

        return auth.receive(player_private_key, player_id);
    }
    public void endRound(){

        engine.endRound(player_id, player_private_key);
    }
    public void terminate(){
        engine.terminate(player_id, player_private_key);
    }

    public void action(){

    }
}
