import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

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

    public class Fauth{
        HashMap<Integer, LinkedList<Message>> ready_messages;// key is the receiver
        LinkedList<Message> unready_message;

        private void checkReady(Message msg){
            msg.readyForDelivery = Adversary.deliverPermission(msg);
            if(msg.readyForDelivery){
                ready_messages.get(msg.sender).add(msg);
            }
            else{
                unready_message.add(msg);
            }
        }
        public void send(Message msg, int player_key){
            if(players_key[msg.sender]!= player_key) return;
            msg.sendRound = roundNumber;
            msg = Fsign.sign(msg);
            checkReady(msg);
        }

        public LinkedList<Message> receive(int key, int id){
            if(players_key[id]!= key) return null;
            return ready_messages.get(key);
        }

        public void update(){
            ListIterator<Message> iterator = unready_message.listIterator();
            while(iterator.hasNext()){
                Message msg = iterator.next();
                msg.readyForDelivery = Adversary.deliverPermission(msg);
                if(msg.readyForDelivery) {
                    ready_messages.get(msg.sender).add(msg);
                    unready_message.remove(msg);
                }
            }
        }

        private class Fsign{
            private HashMap<Long, Message> signed_messages;
            protected Message sign(Message msg){
                long signature = rand.nextLong();
                while(signed_messages.containsKey(signature)) {
                    signature = rand.nextLong();
                }
                msg.sig = signature;
                signed_messages.put(signature,msg);
                return msg;
            }

            public boolean check_sig(Message msg, Long sig){
                return signed_messages.containsKey(sig);
            }
        }
    }



