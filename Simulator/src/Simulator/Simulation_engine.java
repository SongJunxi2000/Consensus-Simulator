package Simulator;

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

        adv = new Adversary(numOfPlayers,numOfFaultyPlayers, delay, maxRound);
        sign = new Fsign();
        auth = new Fauth(adv,sign);

        int current_numeber_of_faulty_players = 0;

        for (int i = 0; i < numOfPlayers; i++) {
            int key = rand.nextInt();
            //need to change
            Player player = null;

            if(current_numeber_of_faulty_players<numOfFaultyPlayers){
                int random = rand.nextInt(numOfPlayers);
                if (random < numOfFaultyPlayers) {
                    player = new Player(key,i,auth,sign,this,numOfPlayers);
                    faulty_players.add(player);
                    faulty_players_id.add(i);
                    current_numeber_of_faulty_players++;
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
        return Dolev_Strong_Player.check_output(designated_sender,honest_players_id, players_output);
    }

    //Below are for GUI

    public GUIStepCommunication GUIstep(){
        if (active_players == null){
            active_players = new HashSet<>(honest_players);
        }
        this_round = (HashSet)active_players.clone();
        auth.update_receive(roundNumber);
        Iterator<Player> iterable_players = active_players.iterator();
        while(iterable_players.hasNext()){
            iterable_players.next().action();
        }
        adv.attack();
        roundNumber++;

        GUIStepCommunication returnM = new GUIStepCommunication();
        returnM.roundNumber = roundNumber;
        returnM.messagesReceived = adv.sendInThisRound(roundNumber);
        returnM.faultyPlayers = faulty_players_id;
        returnM.honestPlayers = honest_players_id;
        returnM.honestPlayersEXTR = new LinkedList<>();
        for(int i=0;i<honest_players.size();i++){
            Dolev_Strong_Player curP = (Dolev_Strong_Player) honest_players.get(i);
            returnM.honestPlayersEXTR.add(curP.getEXTR());
        }

        return returnM;
    }

    public GUIOutputCommunication GUIoutput(){
        GUIOutputCommunication returnM = new GUIOutputCommunication();
        returnM.playersOutputs = players_output;
        returnM.validity = Dolev_Strong_Player.check_validity(designated_sender,honest_players_id,players_output);
        returnM.consistency = Dolev_Strong_Player.check_consistency(designated_sender,honest_players_id,players_output);
        return returnM;
    }

}





