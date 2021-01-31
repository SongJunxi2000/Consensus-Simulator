package utils;

public class GUIOutputCommunication {
    int[] playersOutputs;
    String validity;
    String consistency;
    public GUIOutputCommunication(int[] playersOutputs, String validity, String consistency){
        this.playersOutputs = playersOutputs;
        this.validity = validity;
        this.consistency = consistency;
    }

    public int[] getPlayersOutputs() {
        return playersOutputs;
    }

    public String getConsistency() {
        return consistency;
    }

    public String getValidity() {
        return validity;
    }
}
