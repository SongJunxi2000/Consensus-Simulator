class Message {
    private String msg;
    private Message prev;
    private int receiver;
    private int sender;
    private int sendRound;
    private long sig;
    public Message(String msg, int receiver, int sender, int sendRound, long sig){
        this.msg = msg;
        this.receiver = receiver;
        this.sender = sender;
        this.sendRound = sendRound;
        this.sig = sig;
        prev = null;
    }
    public Message(Message msg, int receiver, int sender, int sendRound, long sig){
        this.prev = msg;
        this.receiver = receiver;
        this.sender = sender;
        this.sendRound = sendRound;
        this.sig = sig;
        this.msg = null;
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

    public long getSig() {
        return sig;
    }

    public String getMsg() {
        return msg;
    }

    public Message getPrev(){
        return prev;
    }
}