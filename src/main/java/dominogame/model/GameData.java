package dominogame.model;


import java.util.List;

/**
 * The {@code GameData} class represents the data of a game.
 */
public class GameData {

    private String player1;
    private String player2;
    private int numberOfDominosPlacedBy1;
    private int numberOfDominosPlacedBy2;
    private String gameTimeandDate;
    private String gameWinner;
    private String lastPlayer;


    /**
     * Constructs a new {@code GameData} object with the specified players.
     *
     * @param player1 The name of player 1.
     * @param player2 The name of player 2.
     */
    public GameData(String player1, String player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.numberOfDominosPlacedBy1 = 0;
        this.numberOfDominosPlacedBy2 = 0;
        this.lastPlayer = null;
        this.gameWinner = null;
        this.gameTimeandDate = new CurrentDateAndTime().getCurrentDateTime().toString();
    }


    /**
     * Default constructor for {@code GameData}.
     *
     * It is needed for proper functioning for the deserialization using the Jackson library
     */
    public GameData() {
    }
    /**
     * Retrieves the last player who made a move.
     *
     * @return The last player who made a move.
     */
    public String getLastPlayer() {
        return lastPlayer;
    }

    /**
     * Sets the last player who made a last move.
     *
     * @param lastPlayer The last player who made a move.
     */
    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    /**
     * Retrieves the name of player 1.
     *
     * @return The name of player 1.
     */
    public String getPlayer1() {
        return player1;
    }

    /**
     * Retrieves the name of player 2.
     *
     * @return The name of player 2.
     */
    public String getPlayer2() {
        return player2;
    }

    /**
     * Retrieves the number of dominos placed by player 1.
     *
     * @return The number of dominos placed by player 1.
     */
    public int getNumberOfDominosPlacedBy1() {
        return numberOfDominosPlacedBy1;
    }

    /**
     * Sets the number of dominos placed by player 1.
     *
     * @param numberOfDominosPlacedBy1 The number of dominos placed by player 1.
     */
    public void setNumberOfDominosPlacedBy1(int numberOfDominosPlacedBy1) {
        this.numberOfDominosPlacedBy1 = numberOfDominosPlacedBy1;
    }

    /**
     * Retrieves the number of dominos placed by player 2.
     *
     * @return The number of dominos placed by player 2.
     */
    public int getNumberOfDominosPlacedBy2() {
        return numberOfDominosPlacedBy2;
    }

    /**
     * Sets the number of dominos placed by player 2.
     *
     * @param numberOfDominosPlacedBy2 The number of dominos placed by player 2.
     */
    public void setNumberOfDominosPlacedBy2(int numberOfDominosPlacedBy2) {
        this.numberOfDominosPlacedBy2 = numberOfDominosPlacedBy2;
    }

    /**
     * Retrieves the game time and date.
     *
     * @return The game time and date.
     */
    public String getGameTimeandDate() {
        return gameTimeandDate;
    }

    /**
     * Retrieves the winner of the game.
     *
     * @return The game winner.
     */
    public String getGameWinner() {
        return gameWinner;
    }

    /**
     * Sets the winner of the game.
     *
     * @param gameWinner The game winner.
     */
    public void setGameWinner(String gameWinner) {
        this.gameWinner = gameWinner;
    }
    @Override
    public String toString() {
        return "GameData{" +
                "player1='" + player1 + '\'' +
                ", player2='" + player2 + '\'' +
                ", numberOfDominosPlacedBy1=" + numberOfDominosPlacedBy1 +
                ", numberOfDominosPlacedBy2=" + numberOfDominosPlacedBy2 +
                ", gameTimeandDate='" + gameTimeandDate + '\'' +
                ", gameWinner='" + gameWinner + '\'' +
                '}';
    }
}
