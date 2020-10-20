public class Adversary {
    HashMap<Integer, LinkedList<Message>> ready_messages;
    LinkedList<Message> unready_message;
    List<Player> faulty_players;
    int numOfPlayers;
    int numOfFaultyPlayers;
    int delay;
    int maxRound;
    public Adversary(List<Player> faulty_players, int numOfPlayers, int numOfFaultyPlayers, int delay, int maxRound){
        this.faulty_players = faulty_players;
        this.numOfPlayers = numOfPlayers;
        this.numOfFaultyPlayers = numOfFaultyPlayers;
        this.delay = delay;
        this.maxRound = maxRound;
    }
    public sendInThisRound(){
        attack()
        HashMap<Integer, LinkedList<Message>> temp = new HashMap<Integer, LinkedList<Message>>();
        temp = ready_messages.clone();
        ready_messages = new HashMap<Integer, LinkedList<Message>>();
        return temp;
    }
    public receive(Message message){
        unready_message.add(message);
    }
    public attack(){

    }
}