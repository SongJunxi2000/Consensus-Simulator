import java.util.LinkedList;

public class Player {
    private int player_private_key;
    public int player_id;
    public int round_number;
    public Fauth auth;
    public Fsign sign;
    public Simulation_engine engine;
    public int total_num_of_players;

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

    public void send(Message message, int receiver) {
        auth.send(message, player_private_key);
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
    public Message sign(String msg, int receiver, int RN, Message msg_object) {
        return sign.sign(msg, receiver, player_id, round_number, player_private_key, msg_object);
    }

    public void output(int output){
        engine.output(player_id,player_private_key,output);
    }

    public void action(){}
}
