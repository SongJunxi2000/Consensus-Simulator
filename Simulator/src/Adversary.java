import java.util.HashMap;
import java.util.LinkedList;

public class Adversary {
    HashMap<Integer, LinkedList<Message>> ready_messages = new HashMap<Integer, LinkedList<Message>>();;
    LinkedList<Message> unready_message = new LinkedList<Message>();
    LinkedList<Player> faulty_players;
    int numOfPlayers;
    int numOfFaultyPlayers;
    int delay;
    int maxRound;
    public Adversary( int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        this.numOfPlayers = numOfPlayers;
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
    }
    public void setFaultyPlayers(LinkedList<Player> faulty_players){
        this.faulty_players = faulty_players;
    }
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(){
        attack();

        while (!unready_message.isEmpty()){
            Message msg = unready_message.remove();
            LinkedList<Message> list = ready_messages.getOrDefault(msg.getReceiver(),new LinkedList<Message>());
            list.add(msg);
            ready_messages.put(msg.getReceiver(), list);
        }
        HashMap<Integer, LinkedList<Message>> temp;
        temp = (HashMap<Integer, LinkedList<Message>>)ready_messages.clone();
        ready_messages = new HashMap<Integer, LinkedList<Message>>();
        return temp;
    }
    public void receive(Message message){
        unready_message.add(message);
    }
    public void attack(){

//TODO: adversary for dolev_strong
    }
}