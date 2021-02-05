package Protocol;

import Simulator.Fauth;
import Simulator.Fsign;
import Simulator.Player;
import Simulator.Simulation_engine;

import java.util.LinkedList;
import java.util.Random;

public class Dummy_Player extends Player {
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
    public Dummy_Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        super(key, id, authenticate, signature, engine, num);
    }

    @Override
    public void action() {
        Random rand = new Random();
        int rand_number =  rand.nextInt(1000);
        String m;
        if(rand_number<600){
            m = "1";
        }
        else m = "0";
        send(sign(m),0,1);
        output(Integer.parseInt(m));
    }

    static boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        int count = 0;
        for(int i=0;i<honest_players_id.size();i++){
            if(outputs[honest_players_id.get(i)] == 1){
                count++;
            }
            else
                count--;
        }
        if(count>=0) return true;
        return false;
    };
}
