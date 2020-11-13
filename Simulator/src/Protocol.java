import java.util.LinkedList;

public interface Protocol {
    public void input();
    public void action(LinkedList<Player> players);
    public boolean check_output();
}
