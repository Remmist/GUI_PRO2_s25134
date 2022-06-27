package Game;

import java.util.Comparator;

public class PlayerSorter implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        if (player1.getPlayerResult() == player2.getPlayerResult()) {
            return 0;
        } else if (player1.getPlayerResult() > player2.getPlayerResult()) {
            return -1;
        } else {
            return 1;
        }
    }
}
