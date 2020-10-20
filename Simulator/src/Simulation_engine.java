import java.util.*;

public class Simulation_engine {
    public int numOfPlyaers, numOfFaultyPlayers, delay, maxRound;
    public int roundNumber=0;
    private HashMap<Integer, Player> players;
    public int[] players_key;

    Random rand = new Random();

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlyaers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        for(int i=0;i<numOfPlayers;i++){
            //initialize the player, random int + hashmap? or use the sequence of id
            int key = rand.nextInt();
            players.put(key, new Player(key, i));
            players_key[i] = key;
        }
    }
}
    // sign in functionality is not part of it
    public class Fauth{

        public void send(Message msg, int player_key){
            if(players_key[msg.sender]!= player_key) return;
            msg.sendRound = roundNumber;
            msg = Fsign.sign(msg);
            unready_message.add(msg);
        }

        public LinkedList<Message> receive(int key, int id){
            if(players_key[id]!= key) return null;
            return ready_messages.get(key);
        }

        public void update(){
            ListIterator<Message> iterator = unready_message.listIterator();
            while(iterator.hasNext()){
                Message msg = iterator.next();
                boolean isReadyForDelivery = Adversary.deliverPermission(msg);
                if(isReadyForDelivery) {
                    ready_messages.get(msg.receiver).add(msg);
                    unready_message.remove(msg);
                }
            }
        }

        private class Fsign{
            //pass the message and the signer,
            private HashSet<Message> signed_messages;
            protected void sign(Message msg){
                signed_messages.add(msg);
            }

            public boolean check_sig(Message msg){
                return signed_messages.contains(msg);
            }
        }
    }



