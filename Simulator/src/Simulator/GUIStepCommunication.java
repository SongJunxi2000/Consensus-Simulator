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
    boolean validity;
    boolean consistency;

}
