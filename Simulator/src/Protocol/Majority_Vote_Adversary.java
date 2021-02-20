package Protocol;

import Simulator.Adversary;
import Simulator.Message;
import Simulator.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class Majority_Vote_Adversary extends Adversary {

    LinkedList<Integer> set0 = new LinkedList<>();
    LinkedList<Integer> set1 = new LinkedList<>();
    public Majority_Vote_Adversary (int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        super(numOfPlayers, numOfFaultyPlayers, delay, maxRound);
    }

    @Override
    public HashMap<Integer, LinkedList<Message>> sendInThisRound(int round_number){

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
    public void send(Player player, LinkedList<Integer> set, String message, int round_number){
        for (Integer i : set){
            player.send(player.sign(message),i,round_number);
        }
    }
    public void attack(int round_number) {
        if (!faulty_players_id.contains(0)) {
            return;
        }
        desig_sender = faulty_players.get(0);
        if (round_number == 0) {
            int numOfhonest = numOfPlayers - numOfFaultyPlayers;
            System.out.println(numOfhonest);
            int numOf0 = numOfhonest / 2;
            int numOf1 = numOfhonest - numOf0;
            for (int i = 0; i < numOfPlayers; i++) {
                if (!faulty_players_id.contains(i)) {
                    String str = numOf0 > 0 ? "0" : "1";
                    desig_sender.send(desig_sender.sign(str), i, 1);
                    if (numOf0 > 0) set0.add(i);
                    else set1.add(i);
                    numOf0--;
                }
            }
        }
        if (round_number == 1) {
            for (Player player : faulty_players) {
                System.out.println("I am here");
                if (player != desig_sender) {
                    send(player, set0, "0", 2);
                    send(player, set1, "1", 2);
                }
            }
            if (set0.size() > set1.size()) {
                send(desig_sender, set1, "1", 2);
            } else if (set1.size() > set0.size()) {
                send(desig_sender, set0, "0", 2);
            } else {
                send(desig_sender, set1, "1", 2);
                send(desig_sender, set0, "0", 2);
            }
        }
    }
}
