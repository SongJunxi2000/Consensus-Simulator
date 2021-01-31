package utils;

import Simulator.Message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class GUIStepCommunication {
    public int roundNumber;
    public HashMap<Integer, LinkedList<Message>> messagesReceived;
    public LinkedList<Integer> faultyPlayers;
    public LinkedList<Integer> honestPlayers;
    public LinkedList<HashSet<String>> honestPlayersEXTR;

    public GUIStepCommunication(){

    }

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
