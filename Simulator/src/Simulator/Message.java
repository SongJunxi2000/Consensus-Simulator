package Simulator;

public class Message {
    private String msg;
    private int receiver;
    private int sender;
    private int sendRound;
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