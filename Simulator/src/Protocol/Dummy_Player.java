package Protocol;

import Simulator.*;
import com.google.gson.Gson;

import java.util.LinkedList;
import java.util.Random;

public class Dummy_Player extends Player {

    boolean isSender;
    boolean[] votedP;
    int count = 0;

    /**
     * Initialize a player.
     *
     * @param key          The private key of this player
     * @param id           The public id of this player
     * @param authenticate The authenticate channel which the player uses to send and receive messages
     * @param signature    The signature model which the player uses to sign and verify messages
     * @param engine       The simulation engine
     * @param num          Total number of players
     */
    public Dummy_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num, boolean isSender) {
        super(key, id, authenticate, signature, engine, num);
        this.isSender = isSender;
        votedP = new boolean[total_num_of_players];
    }

    @Override
    public void action() {
        if(!isSender) {
            Random rand = new Random();
            int rand_number = rand.nextInt(1000);
            String m;
            if (rand_number < 600) {
                m = "1";
            } else m = "0";
            send(sign(m), 0, 1);
            output(Integer.parseInt(m));
            terminate();
        }
        else{
            update_round();
            if(round_number != 0 && round_number<=3){
                LinkedList<Message> received = receive();
                for(int i=0;i<received.size();i++){
                    Message message = received.get(i);
                    String msg = message.getMsg();
                    if(!votedP[message.getSender()] && sign.verification(msg)){
                        votedP[message.getSender()] = true;
                        Gson gson = new Gson();
                        signedM signM = gson.fromJson(msg,signedM.class);
                        int vote = Integer.parseInt(signM.msg);
                        if(vote == 0) count--;
                        else count++;
                    }
                }
            }
            if(round_number ==3 ){
                if(count>=0) output(1);
                else output(0);
                terminate();
            }
        }
    }

    public static boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        int count = 0;
        for(int i=0;i<honest_players_id.size();i++){
            if(outputs[honest_players_id.get(i)] == 1){
                count++;
            }
            else
                count--;
        }
        if(count>=0) return outputs[designated_sender] == 1;
        return outputs[designated_sender] == 0;
    };
}
