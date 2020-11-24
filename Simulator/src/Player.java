import java.util.HashMap;
import java.util.LinkedList;

public class Player {
    private int player_private_key;
    private int player_id;
    public int round_number;
    public Fauth auth;
    public Fsign sign;
    public Simulation_engine engine;
    public int total_num_of_players;
    static HashMap<Integer, HashMap<Integer, String>> input;

    public Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        player_private_key = key;
        player_id = id;
        auth = authenticate;
        sign = signature;
        this.engine = engine;
        total_num_of_players = num;
    }

    public void update_round(int RN, int private_key) {
        if (private_key == player_private_key) {
            round_number = RN;
        }
    }

    public void send(String msg, int receiver, int round_number) {
        auth.send(msg, player_id, player_private_key, receiver, round_number);
    }

    public LinkedList<Message> receive() {
        return auth.receive(player_private_key, player_id);
    }

    public void endRound() {
        engine.endRound(player_id, player_private_key);
    }

    public void terminate() {
        engine.terminate(player_id, player_private_key);
    }
    public signedM sign(String msg) {
        return sign.sign(msg, player_id, player_private_key);
    }

    public void output(int output){
        engine.output(player_id,player_private_key,output);
    }

    public String receive_input(){
        return null;
    }

    public void action(){}

}
