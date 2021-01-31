package Simulator;

public class Message {
    private String msg;
    private int receiver;
    private int sender;
    private int sendRound;

    /**
     * Message class used by authenticated channel, which records the message sends by the player, the actual receiver,
     * actual sender and in which round the sender sends this message.
     * @param msg The string send by the player
     * @param receiver The receiver of the message
     * @param sender The actual sender call the send function
     * @param sendRound In which round the sender sends the message
     */
    public Message(String msg, int receiver, int sender, int sendRound){
        this.msg = msg;
        this.receiver = receiver;
        this.sender = sender;
        this.sendRound = sendRound;
    }
    public int getReceiver() {
        return receiver;
    }

    public int getSender() {
        return sender;
    }

    public int getSendRound() {
        return sendRound;
    }

    public String getMsg() {
        return msg;
    }

}