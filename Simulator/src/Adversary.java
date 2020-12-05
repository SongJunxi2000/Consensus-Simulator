import java.util.HashMap;
import java.util.Iterator;
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
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(int round_number){
        attack();

        Iterator um_iterator = unready_message.iterator();
        LinkedList<Message> tem = new LinkedList<>();

        while (um_iterator.hasNext()){
            Message msg = (Message) um_iterator.next();
            if(msg.getSendRound()>round_number){
                tem.add(msg);
            }
            else{
                LinkedList<Message> list = ready_messages.getOrDefault(msg.getReceiver(),new LinkedList<Message>());
                list.add(msg);
                ready_messages.put(msg.getReceiver(), list);
            }
        }
        unready_message = (LinkedList<Message>) tem.clone();
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