import java.util.LinkedList;

public interface Protocol {
    public void input(int id, int round_number);
    public void action(Player player);
    public boolean check_output(LinkedList<Player> honest_players, int[] outputs);
}
