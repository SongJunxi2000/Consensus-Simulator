package Protocol;

import Simulator.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.LinkedList;

public class Player {
    private int player_private_key;
    private int player_id;
    public int round_number;
    public Fauth auth;
    public Fsign sign;
    public Simulation_engine engine;
    public int total_num_of_players;
    static HashMap<Integer, HashMap<Integer, String>> input;

    /**
     * Initialize a player.
     * @param key The private key of this player
     * @param id The public id of this player
     * @param authenticate The authenticate channel which the player uses to send and receive messages
     * @param signature The signature model which the player uses to sign and verify messages
     * @param engine The simulation engine
     * @param num Total number of players
     */
    public Player(int key, int id, Fauth authenticate, Fsign signature, Simulation_engine engine, int num) {
        player_private_key = key;
        player_id = id;
        auth = authenticate;
        sign = signature;
        this.engine = engine;
        total_num_of_players = num;
    }

    /**
     * Update the current round in simulation engine
     */
    public void update_round() {
        round_number = engine.roundNumber;
    }

    /**
     * Send a message. Player should call this function when she wants to send a message.
     * @param msg The string the player is sending
     * @param receiver The receiver
     * @param round_number In which round the player wish to send
     */
    public void send(String msg, int receiver, int round_number) {
        auth.send(msg, receiver, player_id, player_private_key,  round_number);
    }

    /**
     * Receive a message. A player should call this function when he wants to receive messages from authenticated channel
     * @return A linked list of Message objects
     */
    public LinkedList<Message> receive() {
        return auth. receive(player_private_key, player_id);
    }

    /**
     * Terminate this round
     */
    public void endRound() {
        engine.endRound(player_id, player_private_key);
    }

    /**
     * Terminate the protocol and leave
     */
    public void terminate() {
        engine.terminate(player_id, player_private_key);
    }

    /**
     * This will call sign in Fsign model and get a signed string which is a json object of signedM
     * @param msg The actual message a player wants to sign (ex. "hi Bob, this is Alice :)")
     * @return a string which is a json object of signedM, that includes the message, player's public id, and signature
     */
    public String sign(String msg) {
        return sign.sign(msg, player_id, player_private_key);
    }

    /**
     * Player should call this when he wants to output to simulator
     * @param output The message the player wants to output
     */
    public void output(int output){
        engine.output(player_id,player_private_key,output);
    }

    public LinkedList<signedM> parse(String msg){
        StringBuilder sb = new StringBuilder(msg);
        Gson gson = new Gson();
        //LinkedList<signedM> lst = gson.fromJson(msg, new TypeToken<LinkedList<signedM>>(){}.getType());
        LinkedList<signedM> lst = new LinkedList<signedM>();
        while (sb.length()>0) {
            String tuple = sb.substring(sb.indexOf("(")+1,sb.indexOf((")")));
            sb.delete(sb.indexOf("("), sb.indexOf(")")+1);
            String[] arr = tuple.split(",");
            lst.add(new signedM(arr[0], Integer.parseInt(arr[1]),Long.parseLong(arr[2])));
        }
        return lst;
    }

    /**
     * The action the player will take in each round
     */
    public void action(){}

    /**
     * Check whether all players output satisfy the requirements
     * @param designated_sender The designated sender
     * @param honest_players_id a linked list of honest players' ids
     * @param outputs The outputs of all players. outputs[i] is the output of player with public id being i
     * @return true if all requirements are satisfied, false otherwise
     */
    static boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs){
        return false;
    };

}
