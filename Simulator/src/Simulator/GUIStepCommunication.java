package Simulator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GUIStepCommunication {
    int roundNumber;
    HashMap<Integer, LinkedList<Message>> messagesReceived;
    LinkedList<Integer> faultyPlayers;
    LinkedList<Integer> honestPlayers;
    LinkedList<HashSet<String>> honestPlayersEXTR;

    public int getRoundNumber() {
        return roundNumber;
    }

    public LinkedList<Integer> getFaultyPlayers() {
        return faultyPlayers;
    }

    public LinkedList<Integer> getHonestPlayers() {
        return honestPlayers;
    }

    public LinkedList<HashSet<String>> getHonestPlayersEXTR() {
        return honestPlayersEXTR;
    }

    public HashMap<Integer, LinkedList<Message>> getMessagesReceived() {
        return messagesReceived;
    }
}
