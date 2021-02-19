package Protocol;

import Simulator.*;
import com.google.gson.Gson;

import java.util.Iterator;
import java.util.LinkedList;

public class Majority_Vote_Player extends Player {

    static private final int INPUT = 1;

    boolean isSender;
    int count1=0;
    int count0=0;
    boolean[] voted1;
    boolean[] voted0;
    boolean vote0;
    boolean vote1;
    Gson gson = new Gson();
    /**
     * Initialize a player.
     *
     * @param key          The private key of this player
     * @param id           The public id of this player
     * @param authenticate The authenticate channel which the player uses to send and  messages
     * @param signature    The signature model which the player uses to sign and verify messages
     * @param engine       The simulation enginereceive
     * @param num          Total number of players
     */
    public Majority_Vote_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine,
                                int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
        this.isSender = isSender;
        voted1 = new boolean[total_num_of_players];
        voted0 = new boolean[total_num_of_players];
        vote0 = vote1 = false;
    }

    public int receive_input(){
        if(isSender && round_number == 0)
            return INPUT;
        return -1;
    }

    public void action(){
        update_round();


        if(round_number == 0 && isSender){
            String msg = sign(Integer.toString(receive_input()));
            for(int i=0;i<total_num_of_players;i++){
                send(msg,i,1);
            }
        }


        if(round_number == 1){
            LinkedList<Message> received = receive();
            for(int i=0;i<received.size();i++){
                Message message = received.get(i);
                if(message.getSender() == engine.designated_sender && sign.verification(message.getMsg())){
                    signedM signedm = gson.fromJson(message.getMsg(),signedM.class);
                    if(Integer.parseInt(signedm.msg) == 0){
                        vote0 = true;
                    }
                    if(Integer.parseInt(signedm.msg) == 1){
                        vote1 = true;
                    }
                }
            }
            if(vote1 && !vote0){
                String msg = sign(Integer.toString(1));
                for(int j=0;j<total_num_of_players;j++){
                    send(msg,j,2);
                }
                return;
            }
            String msg = sign(Integer.toString(0));
            for(int j=0;j<total_num_of_players;j++){
                send(msg,j,2);
            }
        }


        if(round_number == 2){
            LinkedList<Message> received = receive();
            for(int i=0;i<received.size();i++){
                Message message = received.get(i);
                String msg = message.getMsg();
                signedM signM = gson.fromJson(msg,signedM.class);
                int vote = Integer.parseInt(signM.msg);
                if(vote == 0){
                    if(!voted0[message.getSender()] && sign.verification(msg)) {
                        voted0[message.getSender()] = true;
                        count0++;
                    }
                }
                else{
                    if(!voted1[message.getSender()] && sign.verification(msg)){
                        voted1[message.getSender()] = true;
                        count1++;
                    }
                }
            }


            if(count1*2>=total_num_of_players && count0*2<total_num_of_players){
                output(1);
                terminate();
                return;
            }
            System.out.println(count0+" "+count1+" ");
            output(0);
            terminate();
        }
    }

    public static boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        Iterator honest_player = honest_players_id.iterator();
        int bit = outputs[(int)honest_players_id.getFirst()];
        if(honest_players_id.contains(designated_sender)){
            bit = outputs[designated_sender];
        }
        while(honest_player.hasNext()){
            if (outputs[(int)honest_player.next()]!= bit) return false;
        }
        return true;
    }
}
