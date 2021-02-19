package Protocol;

import Simulator.Adversary;
import Simulator.Message;
import Simulator.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * This dummy adversary works as follow:
 * It randomly decides how long the message needs to delay for this round, and if the delay is larger than max delay
 * it will deliver this message, other wise the message is delayed until the next round.
 */
public class Default_Adversary extends Adversary {

    public Default_Adversary (int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        super(numOfPlayers, numOfFaultyPlayers, delay, maxRound);
    }

    @Override
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(int round_number){

        Iterator um_iterator = unready_message.iterator();
        LinkedList<Message> tem = new LinkedList<>();

        while (um_iterator.hasNext()){
            Message msg = (Message) um_iterator.next();
            int delay = (int) (Math.random()*maxDelay);
            if( msg.getSendRound()+delay > round_number){
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
