package Simulator;

import Protocol.Dolev_Strong_Adversary;
import Protocol.Dolev_Strong_Player;

import java.util.*;

public class Simulation_engine {

    public int numOfPlayers, numOfFaultyPlayers, delay, maxRound;
    public int roundNumber = 0;
    private HashMap<Integer, Player> players;//private key -> player
    private int[] players_key;
    public Fauth auth;
    public Fsign sign;
    public Adversary adv;
    LinkedList<Player> faulty_players;
    LinkedList<Integer> faulty_players_id;
    LinkedList<Integer> honest_players_id;
    LinkedList<Player> honest_players;
    Random rand = new Random();
    HashSet<Player> this_round;
    HashSet<Player> active_players;
    private int[] players_output;
    public int designated_sender = 0;

    /**
     * Initialize a new Simulation engine which simulates Dolve-Strong protocol.
     * @param numOfPlayers Total number of players
     * @param numOfFaultyPlayers Maximum number of faulty players, the actual number of faulty players would
     *                           be between 0 and the max value
     * @param delay Maximum delay of messages
     * @param maxRound Maximum number of rounds the simulator will simulate
     */
    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound) {
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlayers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        players = new HashMap<>();
        players_key = new int[numOfPlayers];
        faulty_players = new LinkedList<>();
        faulty_players_id = new LinkedList<>();
        honest_players_id = new LinkedList<>();
        honest_players = new LinkedList<>();
        players_output = new int[numOfPlayers];

        adv = new Dolev_Strong_Adversary(numOfPlayers,numOfFaultyPlayers, delay, maxRound);
        sign = new Fsign();
        auth = new Fauth(adv,sign);

        int current_number_of_faulty_players = 0;

        for (int i = 0; i < numOfPlayers; i++) {
            int key = rand.nextInt();
            Player player = null;

            if(current_number_of_faulty_players<numOfFaultyPlayers){
                int random = rand.nextInt(numOfPlayers);
                if (random < numOfFaultyPlayers) {
                    player = new Player(key,i,auth,sign,this,numOfPlayers);
                    faulty_players.add(player);
                    faulty_players_id.add(i);
                    current_number_of_faulty_players++;
                }
                else {
                    player = new Dolev_Strong_Player(
                            key, i, auth, sign, this, numOfPlayers,i == 0);
                    honest_players_id.add(i);
                    honest_players.add(player);
                }
            }
            else{
                player = new Dolev_Strong_Player(
                        key, i, auth, sign, this, numOfPlayers,i == 0);
                honest_players_id.add(i);
                honest_players.add(player);
            }
            players.put(key, player);
            players_key[i] = key;

        }
        adv.setFaultyPlayers(faulty_players,faulty_players_id);
        auth.setAdKeys( players_key);
        sign.setKeys(players_key);

        runProtocol();
        check_output();
    }

    /**
     * Run the protocol without checking whether the outputs satisfy validity and consistency
     */
    public void runProtocol(){
        active_players = new HashSet<>(honest_players);
        this_round = (HashSet)active_players.clone();

        for(int i=0;i<maxRound;i++){
            roundNumber = i;
            auth.update_receive(roundNumber);
            Iterator<Player> iterable_players = active_players.iterator();
            while(iterable_players.hasNext()){
                iterable_players.next().action();
            }

            adv.attack();

        }
        for (int i : players_output)
            System.out.print(i+" ");
    }

    /**
     * The player should call this function when she tries to output something
     * @param sender The id of the player who wants to output
     * @param private_key The private key of the player who wants to output
     * @param output The actual output
     */
    public void output(int sender, int private_key, int output){
        if(check_actioner(sender, private_key)) {
            players_output[sender] = output;
        }
    }

    /**
     * Simulator checks whether the public id and private key of the player match
     * @param sender The public id of the player
     * @param private_key The private key of the player
     * @return true if the id and key match, false otherwise
     */
    private boolean check_actioner(int sender, int private_key){
        return players_key[sender] == private_key;
    }

    /**
     * Player should call this function when he wants to terminate and leave
     * @param sender The public id og the player
     * @param private_key The private key of the player
     */
    public void terminate(int sender, int private_key){
        if(check_actioner(sender, private_key)){
            active_players.remove(players.get(private_key));
        }
    }

    /**
     * Player should call this function when he wants to end this round
     * @param sender The public id og the player
     * @param private_key The private key of the player
     */
    public void endRound(int sender, int private_key){
        if(check_actioner(sender, private_key)){
            this_round.remove(players.get(private_key));
        }
    }

    /**
     * Check whether the outputs of all players satisfy validity and consistency
     * @return true if both validity and consistency are satisfied, false otherwise
     */
    public boolean check_output(){
        return Dolev_Strong_Player.check_output(designated_sender,honest_players_id, players_output);
    }

}





