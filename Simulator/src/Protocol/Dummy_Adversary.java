package Protocol;

import Simulator.Adversary;
import Simulator.Message;
import Simulator.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Dummy_Adversary extends Adversary {
    HashMap<Integer, LinkedList<Message>> ready_messages = new HashMap<Integer, LinkedList<Message>>();;
    LinkedList<Message> unready_message = new LinkedList<Message>();
    LinkedList<Player> faulty_players;
    LinkedList<Integer> faulty_players_id;
    int numOfPlayers;
    int numOfFaultyPlayers;
    int delay;
    int maxRound;
    Player desig_sender;
    public Dummy_Adversary (int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        super(numOfPlayers, numOfFaultyPlayers, delay, maxRound);
    }
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(int round_number){

        Iterator um_iterator = unready_message.iterator();
        LinkedList<Message> tem = new LinkedList<>();

        while (um_iterator.hasNext()){
            Message msg = (Message) um_iterator.next();
            int delay = (int) (Math.random()*2);
            if(msg.getSendRound()>round_number+delay && msg.getSendRound()+delay < round_number){
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
}
