import java.util.LinkedList;

public class Test_Protocol implements Protocol {

    @Override
    public String input(int id, int round_number) {
        return null;
    }

    @Override
    public void action(Player player) {
        player.output(0);
    }

    @Override
    public boolean check_output(int designated_sender, LinkedList<Integer> honest_players, int[] outputs) {
        return outputs[honest_players.get(0)]==0;
    }
}
//class Memory{
//
//}
