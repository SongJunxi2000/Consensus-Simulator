import java.util.LinkedList;

public class main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        //Simulation_engine engine = new Simulation_engine(30,10,1,10);

        //Fsign test code starts
//        Fsign sig = new Fsign();
//        sig.setKeys(new int[]{0,0,0,15});
//        String singedM1 = sig.sign("haha",3,15);
//        String singedM2 = sig.sign("hahaha",3,15);
//        String singedM3 = sig.sign("hahahahah",3,15);
//        String singedM4 = sig.sign("haha",3,15);
//        System.out.println(singedM1);
//        System.out.println(singedM2);
//        System.out.println(singedM3);
//        System.out.println(singedM4);
//        System.out.println(sig.verification(singedM1));
//        System.out.println(sig.verification(singedM2));
//        System.out.println(sig.verification(singedM3));
//        System.out.println(sig.verification(singedM4));
//        System.out.println(sig.verification("singedM1"));
        //Fsign test code ends


        //Fauth test starts
//        Adversary adv = new Adversary(30,10,10,20);
//        Fsign sig = new Fsign();
//        Fauth channel = new Fauth(adv,sig);
//        sig.setKeys(new int[]{0,1,2,3,4});
//        channel.setAdKeys(new int[]{0,1,2,3,4});
//        channel.send("m",1,0,0,1);
//        channel.send("m",1,1,1,1);
//        channel.send("m",1,2,2,1);
//        channel.send("m",1,3,3,1);
//        channel.send("m",1,0,0,2);
//        channel.update_receive(1);
//        LinkedList<Message> received = channel.receive(1,1);
//        System.out.println(received.getFirst().getMsg());
//        System.out.println(received.get(1).getSender());
//        System.out.println(received.get(2).getSender());
//        System.out.println(received.get(3).getSender());
//        channel.update_receive(2);
//        System.out.println(received.get(0).getSender());
        //Fatuh test ends

        //Player test starts
        Adversary adv = new Adversary(30,10,10,20);
        Fsign sig = new Fsign();
        Fauth channel = new Fauth(adv,sig);
        Simulation_engine engine = new Simulation_engine(30, 10, 10, 20);
        Player player = new Player(0,0,channel, sig, engine, 30);
        String test = "(0,0,1212)(0,0,12122)";
        LinkedList<signedM> lst = player.parse(test);
        for (signedM m : lst){
            System.out.println(m.msg+" "+m.player+" "+m.sig);
        }
        //Player test ends
    }
}
