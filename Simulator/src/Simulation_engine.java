import java.util.LinkedList;

public class Simulation_engine {
    public int numOfPlyaers, numOfFaultyPlayers, delay, maxRound;
    public int roundNumber=0;
    private Player[] players;

    public Simulation_engine(int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.numOfPlyaers = numOfPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
        players = new Player[numOfPlayers];
        for(int i=0;i<numOfPlayers;i++){
            //initialize the player, random int + hashmap? or use the sequence of id
        }
    }

    public class Fauth{
        LinkedList<Message> messages;// maybe need an array? 
        public Fauth(){
            messages = new LinkedList<Message>();
        };
        public void send(Message msg){
            msg.sendRound = roundNumber;
            msg.readyForDelivery = Adversary.deliverPermission(msg);
            if
        }
    }
}
