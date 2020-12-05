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
    LinkedList<Integer> honest_players;
    Random rand = new Random();
    HashSet<Player> this_round;
    HashSet<Player> active_players;
    private int[] players_output;
    public int designated_sender;

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound) {
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlayers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        players = new HashMap<>();
        players_key = new int[numOfPlayers];
        faulty_players = new LinkedList<>();
        honest_players = new LinkedList<>();
        players_output = new int[numOfPlayers];

        adv = new Adversary(numOfPlayers,numOfFaultyPlayers, delay, maxRound);
        sign = new Fsign();
        auth = new Fauth(adv,sign);

        int current_numeber_of_faulty_players = 0;

        for (int i = 0; i < numOfPlayers; i++) {
            int key = rand.nextInt();
            //need to change
            Player player = new Player(key, i, auth, sign, this, numOfPlayers);
            players.put(key, player);
            players_key[i] = key;
            if(current_numeber_of_faulty_players<numOfFaultyPlayers){
                int random = rand.nextInt(numOfPlayers);
                if (random < numOfFaultyPlayers) {
                    faulty_players.add(player);
                    current_numeber_of_faulty_players++;
                }
                else
                    honest_players.add(i);
            }
            else
                honest_players.add(i);

        }
        adv.setFaultyPlayers(faulty_players);
        auth.setAdKeys( players_key);
        sign.setKeys(players_key);

        runProtocol();
        check_output();
    }

    public void runProtocol(){
        active_players = new HashSet<>(players.values());
        this_round = (HashSet)active_players.clone();
        Iterator<Player> iterable_players = active_players.iterator();
        for(int i=0;i<maxRound;i++){
            roundNumber = i+1;
            auth.update_receive(roundNumber);
            while(iterable_players.hasNext()){
                iterable_players.next().action();
            }
        }
    }

    public void output(int sender, int private_key, int output){
        if(check_actioner(sender, private_key)) {
            players_output[sender] = output;
        }
    }

    private boolean check_actioner(int sender, int private_key){
        return players_key[sender] == private_key;
    }

    public void terminate(int sender, int private_key){
        if(check_actioner(sender, private_key)){
            active_players.remove(players.get(private_key));
        }
    }

    public void endRound(int sender, int private_key){
        if(check_actioner(sender, private_key)){
            this_round.remove(players.get(private_key));
        }
    }


    public boolean check_output(){

        return Player.check_output(designated_sender,honest_players, players_output);
    }

}

