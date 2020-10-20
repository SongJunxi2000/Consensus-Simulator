import java.util.*;

public class Simulation_engine {
    public int numOfPlyaers, numOfFaultyPlayers, delay, maxRound;
    public int roundNumber = 0;
    private HashMap<Integer, Player> players;
    private int[] players_key;
    public Fauth auth;
    public Fsign sign;
    public Adversary adv;
    LinkedList<Player> faulty_players;
    Random rand = new Random();

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound) {
        sign = new Fsign();
        auth = new Fauth(sign);

        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlyaers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        for (int i = 0; i < numOfPlayers; i++) {
            //initialize the player, random int + hashmap? or use the sequence of id
            int key = rand.nextInt();
            Player player = new Player(key, i, auth, sign);
            players.put(key, player);
            players_key[i] = key;
            if (i < numOfFaultyPlayers) {
                faulty_players.add(player);
            }
        }
        adv = new Adversary(faulty_players, numOfFaultyPlayers, delay, maxRound);
        auth.setAdKeys(adv, players_key);
        sign.setKeys(players_key);
    }

}

public class Fauth {
    HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver
    LinkedList<Message> unready_message;
    Fsign sign;
    Adversary adv;
    private int[] players_key;

    public Fauth(Fsign signature) {
        sign = signature;
    }

    public void setAdKeys(Adversary adversary, int[] keys) {
        adv = adversary;
        players_key = keys;
    }

    public void send(Message msg) {
        if (sign.check_sig(msg)) {
            Adversary.receive(msg);
        }
    }

    public LinkedList<Message> receive(int key, int id) {
        if (players_key[id] != key) return null;
        return ready_messages.get(key);
    }

    public void update() {
        ready_messages = Adversary.sendInThisRound();
    }
}

public class Fsign {
    private int[] players_key;

    public void setKeys(int[] keys) {
        players_key = keys;
    }

    private HashMap<Long, Message> signed_messages;
    Random rand = new Random();

    protected Message sign(String msg, int receiver, int sender, int RN, int player_key) {
        if (players_key[sender] != player_key) return null;
        long sig = rand.nextLong();
        while (signed_messages.containsKey(sig)) {
            sig = rand.nextLong();
        }
        Message tem = new Message(msg, receiver, sender, RN, sig);
        signed_messages.put(sig, tem);
        return tem;
    }

    public boolean check_sig(Message msg) {
        return signed_messages.containsKey(msg.getSig());
    }
}

