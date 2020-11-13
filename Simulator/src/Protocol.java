import java.util.LinkedList;

public interface Protocol {
    public String input(int id, int round_number);
    public void action(Player player);
    public boolean check_output(int designated_sender, LinkedList<Integer> honest_players_id, int[] outputs);
}
