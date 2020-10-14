import java.util.LinkedList;

class Message{
    String msg;
    int receiver;
    int sender;
    boolean readyForDelivery;
    int sendRound;
    long sig;
};
public class Player {
    private int player_private_key;
    public int player_id;
    public Player(int key, int id){
        player_private_key = key;
        player_id = id;

    }
    public void send(Message msg){
        Fauth.send(msg.sender = player_id, player_private_key);
    }
    public LinkedList<Message> receive(){

        return Simulation_engine.Fauth.receive(player_private_key, player_id);
    }
    public void endRound(){

        Simulation_engine.endRound(player_private_key);
    }
    public void terminate(){
        Simulation_engine.terminate(player_private_key);
    }
}
