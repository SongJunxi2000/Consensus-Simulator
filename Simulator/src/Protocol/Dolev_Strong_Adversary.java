package Protocol;

import Simulator.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Dolev_Strong_Adversary extends Adversary {
//    HashMap<Integer, LinkedList<Message>> ready_messages = new HashMap<Integer, LinkedList<Message>>();;
//    LinkedList<Message> unready_message = new LinkedList<Message>();
//    LinkedList<Player> faulty_players;
//    LinkedList<Integer> faulty_players_id;
//    int numOfPlayers;
//    int numOfFaultyPlayers;
//    int delay;
//    int maxRound;
//    Player desig_sender;
    public Dolev_Strong_Adversary(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        super(numOfPlayers,numOfFaultyPlayers,delay,maxRound);

    }
//    public void setFaultyPlayers(LinkedList<Player> faulty_players_given, LinkedList<Integer> f_id){
//        this.faulty_players_id = f_id;
//        this.faulty_players = faulty_players_given;
//        numOfFaultyPlayers = f_id.size();
//    }
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
    public void attack(){
        if(faulty_players == null || faulty_players.size() == 0)
            return;
        Simulation_engine eng = faulty_players.getFirst().engine;
        for (int i = 0;i<numOfFaultyPlayers;i++){
            if(faulty_players_id.get(i) == 0)
                desig_sender = faulty_players.get(i);
        }
        if(desig_sender == null ) return;
        if(eng.roundNumber == 0){
            for(int i=0;i<numOfPlayers;i++){
                desig_sender.send(desig_sender.sign("1"),i,0);
            }
        }
        if( eng.roundNumber!=maxRound-2) return;
        int honest_target = 0;
        while(faulty_players_id.contains(honest_target))
            honest_target++;
        String msg = "";
        for(int i = 0;i<numOfFaultyPlayers;i++){
            msg = msg+","+faulty_players.get(i).sign("0");
        }
        msg = msg.substring(1);

        faulty_players.getFirst().send(msg,honest_target,maxRound-1);
        System.out.println(honest_target+" "+eng.roundNumber);
        System.out.println(msg);

    }
}