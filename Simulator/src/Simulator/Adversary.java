package Simulator;

import Protocol.Player;

import java.util.HashMap;
import java.util.LinkedList;

public interface Adversary {
    /**
     * After Simulation engine generates all faulty players, it will pass all faulty players to the adversary.
     * @param faulty_players_given A linked list of all faulty players
     * @param f_id A linked list of all faulty players' ids, the sequence of the two linked lists matches, which means
     *             f_id.get(i) is the public id of player faulty_players_given.get(i)
     */
    void setFaultyPlayers(LinkedList<Player> faulty_players_given, LinkedList<Integer> f_id);

    /**
     * The authenticated channel will call this function when someone tries to receive a message.
     * @param round_number Self explanatory
     * @return A hash map which maps the public id of the player to a linked list of messages she will
     *      * receive in the given round.
     */
    HashMap<Integer, LinkedList<Message>> sendInThisRound(int round_number);

    /**
     * The authenticated channel will call this function when a player sends a message
     * @param message The message created by authenticated channel
     */
    void receive(Message message);

    /**
     * Adversary's attack
     */
    void attack();
}
