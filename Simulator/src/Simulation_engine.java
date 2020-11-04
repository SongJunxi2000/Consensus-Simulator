import java.util.*;

public class Simulation_engine {

    //TODO: check consistencity and validity
    public int numOfPlayers, numOfFaultyPlayers, delay, maxRound;
    public int roundNumber = 0;
    private HashMap<Integer, Player> players;
    private int[] players_key;
    public Fauth auth;
    public Fsign sign;
    public Adversary adv;
    LinkedList<Player> faulty_players;
    Random rand = new Random();
    HashSet<Player> this_round;
    HashSet<Player> active_players;
    private int[] players_output;

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound) {
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlayers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        players = new HashMap<>();
        players_key = new int[numOfPlayers];
        faulty_players = new LinkedList<>();
        players_output = new int[numOfPlayers];


        adv = new Adversary(numOfPlayers,numOfFaultyPlayers, delay, maxRound);
        sign = new Fsign();
        auth = new Fauth(adv,sign);

        for (int i = 0; i < numOfPlayers; i++) {
            int key = rand.nextInt();
            Player player = new Player(key, i, auth, sign, this, numOfPlayers);
            players.put(key, player);
            players_key[i] = key;
            if (i < numOfFaultyPlayers) {
                faulty_players.add(player);
            }
        }
        adv.setFaultyPlayers(faulty_players);
        auth.setAdKeys( players_key);
        sign.setKeys(players_key);

        runProtocol();
    }

    public void runProtocol(){
        active_players = new HashSet<>(players.values());
        this_round = (HashSet)active_players.clone();
        Iterator<Player> iterable_players = active_players.iterator();
        for(int i=0;i<maxRound;i++){
            roundNumber = i+1;
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

}

