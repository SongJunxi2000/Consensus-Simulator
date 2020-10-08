import java.util.LinkedList;

class Message{
    String msg;
    int receiver;
    int sender;
    boolean readyForDelivery;
    int sendRound;
};
public abstract class Player {
    private int player_id;
    public Player(int ID){
        player_id = ID;
    }
    public void send(Message msg){ Simulation_engine.Fauth.send(msg.sender = player_id);
    }
    public LinkedList<Message> receive(){
        return Simulation_engine.Fauth.receive(player_id);
    }
    public void endRound(){Simulation_engine.endRound(player_id);}
    public void terminate(){Simulation_engine.terminate(player_id);}
}
