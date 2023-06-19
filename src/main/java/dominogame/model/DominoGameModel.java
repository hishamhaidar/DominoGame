package dominogame.model;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

import java.awt.*;

/**
 * The `DominoGameModel` class represents the model of the domino game.
 * It manages the game data and the game board.
 */
public class DominoGameModel {
    private GameData gameData;
    private final String dataFile = "games.json";
    private GameDataStorage gameSaver = new GameDataStorage();

    private ReadOnlyObjectWrapper<Square>[][] board ;
    public static final int BOARD_SIZE = 6;

    /**
     * Creates a new instance of `DominoGameModel` and initializes the game board.
     */
    public DominoGameModel() {
        board = new ReadOnlyObjectWrapper[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = new ReadOnlyObjectWrapper<>(Square.NONE);
            }
        }
    }

    /**
     * Retrieves the square at the specified position on the game board.
     *
     * @param row The row index of the square.
     * @param col The column index of the square.
     * @return The square at the specified position.
     */
    public Square getSquare(int row, int col) {
        return board[row][col].get();
    }

    /**
     * Sets the square at the specified position on the game board.
     *
     * @param row    The row index of the square.
     * @param col    The column index of the square.
     * @param square The square to be set.
     */
    public void setSquare(int row, int col, Square square) {
        board[row][col].set(square);
    }

    /**
     * Retrieves the read-only property of the square at the specified position on the game board.
     *
     * @param i The row index of the square.
     * @param j The column index of the square.
     * @return The read-only property of the square.
     */
    public ReadOnlyObjectProperty<Square> squareProperty(int i, int j) {
        return board[i][j].getReadOnlyProperty();
    }

    /**
     * Starts a new game with the specified players.
     *
     * @param player1 The name of player 1.
     * @param player2 The name of player 2.
     */
    public void startNewGame(String player1, String player2) {
        gameData = new GameData(player1, player2);
    }

    /**
     * Retrieves the game data.
     *
     * @return The game data.
     */
    public GameData getGameData() {
        return gameData;
    }

    /**
     * Saves the current game data to a file.
     */
    public void saveCurrentGame() {
        gameSaver.saveGameData(gameData, dataFile);
    }

    /**
     * Places a domino at the specified position on the game board.
     *
     * @param row           The row index of the position.
     * @param col           The column index of the position.
     * @param isPlayer1Turn Indicates whether it's player 1's turn.
     */
    public void placeDomino(int row, int col, boolean isPlayer1Turn) {
        Square square = isPlayer1Turn ? Square.PLAYER1 : Square.PLAYER2;

        // Check if the specified square is empty
        if (getSquare(row, col) != Square.NONE) {
            return; // Square is already occupied, cannot place the domino here
        }

        // Check if there are adjacent squares available
        if (isPlayer1Turn) {
            // Player 1 always places horizontally (to the right)
            if (col + 1 >= BOARD_SIZE || getSquare(row, col + 1) != Square.NONE) {
                return; // Invalid index or adjacent square is not empty
            }
        } else {
            // Player 2 always places vertically (below)
            if (row + 1 >= BOARD_SIZE || getSquare(row + 1, col) != Square.NONE) {
                return; // Invalid index or adjacent square is not empty
            }
        }

        // Place the domino on the specified square and the adjacent square
        setSquare(row, col, square);
        if (isPlayer1Turn) {
            setSquare(row, col + 1, square);
            incrementPlayer1DominoPlaced();
            gameData.setLastPlayer(gameData.getPlayer1());
        } else {
            setSquare(row + 1, col, square);
            incrementPlayer2DominoPlaced();
            gameData.setLastPlayer(gameData.getPlayer2());
        }
    }

    /**
     * Checks if a move at the specified position is valid.
     *
     * @param row           The row index of the position.
     * @param col           The column index of the position.
     * @param isPlayer1Turn Indicates whether it's player 1's turn.
     * @return `true` if the move is valid, `false` otherwise.
     */
    public boolean isMoveValid(int row, int col, boolean isPlayer1Turn) {
        if (getSquare(row, col) != Square.NONE) {
            return false; // Don't allow clicking on already occupied squares
        }

        if (isPlayer1Turn) {
            if (col + 1 >= BOARD_SIZE) {
                return false; // Invalid index for adjacent square
            }
            Square adjacentSquare = getSquare(row, col + 1);
            if (adjacentSquare != Square.NONE) {
                return false; // No adjacent empty square available
            }
        } else {
            if (row + 1 >= BOARD_SIZE) {
                return false; // Invalid index for adjacent square
            }
            Square adjacentSquare = getSquare(row + 1, col);
            if (adjacentSquare != Square.NONE) {
                return false; // No adjacent empty square available
            }
        }

        return true;
    }

    /**
     * Checks if the current player has a valid move available.
     *
     * @param isCurrentPlayer Indicates whether it's the current player's turn.
     * @return `true` if a valid move is available, `false` otherwise.
     */
    public boolean hasValidMove(boolean isCurrentPlayer) {
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int col = 0; col < BOARD_SIZE; col++) {
                Square square = getSquare(row, col);
                if (square == Square.NONE && hasAdjacentEmptySquares(row, col, isCurrentPlayer)) {
                    return true; // Valid move available
                }
            }
        }
        return false;
    }

    /**
     * Retrieves the name of the winner.
     *
     * @return The name of the winner.
     */
    public String getWinner() {
        String winner;
        if (gameData.getLastPlayer().equals(gameData.getPlayer1())) {
            winner = gameData.getPlayer1();
            gameData.setGameWinner(winner);
        } else {
            winner = gameData.getPlayer2();
            gameData.setGameWinner(winner);
        }
        return winner;
    }

    /**
     * Checks if there are adjacent empty squares at the specified position.
     *
     * @param row              The row index of the position.
     * @param col              The column index of the position.
     * @param isCurrentPlayer  Indicates whether it's the current player's turn.
     * @return `true` if there are adjacent empty squares, `false` otherwise.
     */
    private boolean hasAdjacentEmptySquares(int row, int col, boolean isCurrentPlayer) {
        if (isCurrentPlayer) {
            if (col - 1 >= 0 && getSquare(row, col - 1) == Square.NONE) {
                return true; // Left square is empty and within bounds
            }
            if (col + 1 < BOARD_SIZE && getSquare(row, col + 1) == Square.NONE) {
                return true; // Right square is empty and within bounds
            }
        } else {
            if (row - 1 >= 0 && getSquare(row - 1, col) == Square.NONE) {
                return true; // Top square is empty and within bounds
            }
            if (row + 1 < BOARD_SIZE && getSquare(row + 1, col) == Square.NONE) {
                return true; // Bottom square is empty and within bounds
            }
        }
        return false;
    }

    /**
     * Increments the number of dominos placed by player 1.
     */
    public void incrementPlayer1DominoPlaced() {
        gameData.setNumberOfDominosPlacedBy1(gameData.getNumberOfDominosPlacedBy1() + 1);
    }

    /**
     * Increments the number of dominos placed by player 2.
     */
    public void incrementPlayer2DominoPlaced() {
        gameData.setNumberOfDominosPlacedBy2(gameData.getNumberOfDominosPlacedBy2() + 1);
    }

    /**
     * Prints the current game board.
     *
     * @return A string representation of the current game board.
     */
    public String printBoard() {
        StringBuilder boardString = new StringBuilder("Current Board:\n");
        for (int row = 0; row < DominoGameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < DominoGameModel.BOARD_SIZE; col++) {
                Square square = getSquare(row, col);
                boardString.append(square).append("\t");
            }
            boardString.append("\n");
        }
        return boardString.toString();
    }
}
