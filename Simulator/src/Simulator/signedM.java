package Simulator;

/**
 * This is the class for Gson object used when a player sends a message.
 */
public class signedM {
        public signedM(String m, int p, long s){
            msg = m;
            player=  p;
            sig = s;
        }
        public String msg;
        public int player;
        public long sig;
}
