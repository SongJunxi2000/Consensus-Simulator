import java.util.HashMap;
import java.util.LinkedList;

public class Adversary {
    HashMap<Integer, LinkedList<Message>> ready_messages,temp;
    LinkedList<Message> unready_message;
    LinkedList<Player> faulty_players;
    int numOfPlayers;
    int numOfFaultyPlayers;
    int delay;
    int maxRound;
    public Adversary(LinkedList<Player> faulty_players, int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        this.faulty_players = faulty_players;
        this.numOfPlayers = numOfPlayers;
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
    }
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(){
        attack();
        temp = (HashMap<Integer, LinkedList<Message>>)ready_messages.clone();
        ready_messages = new HashMap<Integer, LinkedList<Message>>();
        return temp;
    }
    public void receive(Message message){
        unready_message.add(message);
    }
    public void attack(){

    }
}