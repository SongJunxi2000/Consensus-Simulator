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
    HashSet<Player> this_round;
    HashSet<Player> active_players;

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound) {
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlyaers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;

        adv = new Adversary(numOfPlayers,numOfFaultyPlayers, delay, maxRound);
        sign = new Fsign();
        auth = new Fauth(adv,sign);

        for (int i = 0; i < numOfPlayers; i++) {
            int key = rand.nextInt();
            Player player = new Player(key, i, auth, sign);
            players.put(key, player);
            players_key[i] = key;
            if (i < numOfFaultyPlayers) {
                faulty_players.add(player);
            }
        }
        adv.setFaultyPlayers(faulty_players);
        auth.setAdKeys( players_key);
        sign.setKeys(players_key);
    }

    public void runProtocol(){
        active_players = new HashSet<>(players.values());
        this_round = (HashSet)active_players.clone();
        Iterator<Player> iterable_players = active_players.iterator();
        for(int i=0;i<maxRound;i++){
            while(iterable_players.hasNext()){
                iterable_players.next().action();
            }
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

public class Fauth {
    HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver
    LinkedList<Message> unready_message;
    Fsign sign;
    Adversary adv;
    private int[] players_key;

    public Fauth(Adversary adversary,Fsign signature) {
        sign = signature;
        adv = adversary;
    }

    public void setAdKeys( int[] keys) {
        players_key = keys;
    }

    public void send(Message msg, int private_key) {
        if (players_key[msg.getSender()] == private_key ) {
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

    private HashMap<Integer, HashMap<Long, Message>> signed_messages;
    Random rand = new Random();

    protected Message sign(String msg, int receiver, int sender, int RN, int private_key) {
        if (players_key[sender] != private_key) return null;

        if(!signed_messages.containsKey(sender)){
            HashMap<Long, Message> player_all_signed_msg = new  HashMap<Long, Message>();
            long sig = rand.nextLong();
            Message tem = new Message(msg, receiver, sender, RN, sig);
            player_all_signed_msg.put(sig,tem);
            signed_messages.put(private_key,player_all_signed_msg);
            return tem;
        }
        else{
            HashMap<Long, Message> player_all_signed_msg = signed_messages.get(sender);
            long sig = rand.nextLong();
            while (signed_messages.containsKey(sig)) {
                sig = rand.nextLong();
            }
            Message tem = new Message(msg, receiver, sender, RN, sig);
            player_all_signed_msg.put(sig, tem);
            return tem;
        }
    }

    public boolean verification(Message msg, int public_key, long sig ) {
        HashMap<Long, Message> player_all_signed_msg = signed_messages.get(public_key);
        Message message = player_all_signed_msg.get(sig);
        return message == msg;
    }
}

