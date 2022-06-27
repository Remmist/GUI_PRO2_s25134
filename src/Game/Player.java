package Game;

import java.io.Serializable;

public class Player implements Serializable {
    private String name;
    private int playerResult;

    public Player(String name, int playerResult) {
        this.name = name;
        this.playerResult = playerResult;
    }

    public int getPlayerResult() {
        return playerResult;
    }

    @Override
    public String toString() {
        return name + " - " + playerResult;
    }
}
