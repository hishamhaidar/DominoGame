package dominogame.controller;

import dominogame.model.DominoGameModel;
import dominogame.model.GameData;
import dominogame.model.Square;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DominoGameController {
    @FXML
    GridPane board;

    StackPane[][] squares;

    boolean isPlayer1Turn = true;
    private DominoGameModel gameModel;
    private static final Logger logger = LogManager.getLogger();

    private GameData gameData;

    public void setGameModel(DominoGameModel gameModel) {
        this.gameModel = gameModel;
        this.gameData = gameModel.getGameData();
    }

    @FXML
    private void initialize() {
        squares = new StackPane[DominoGameModel.BOARD_SIZE][DominoGameModel.BOARD_SIZE];
        for (int row = 0; row < DominoGameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < DominoGameModel.BOARD_SIZE; col++) {
                StackPane square = createSquare(row, col);
                board.add(square, col, row);
                squares[row][col] = square;
            }
        }
    }

    private StackPane createSquare(int row, int col) {
        StackPane square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(event -> handleMouseClick(square, row, col));
        return square;
    }

    void handleMouseClick(StackPane square, int row, int col) {
        logger.info("Click on square ({}, {})", row, col);

        Square squareValue = gameModel.getSquare(row, col);
        if (gameModel.isMoveValid(row, col, isPlayer1Turn)) {
            gameModel.placeDomino(row, col, isPlayer1Turn);



            if (squareValue == Square.NONE) {
                squareValue = isPlayer1Turn ? Square.PLAYER1 : Square.PLAYER2;
            }
            // Update style based on the square value
            String dominoStyle = "-fx-background-color: " + getColorForSquare(squareValue) + ";";
            square.setStyle(dominoStyle);
            StackPane adjacentSquare = (isPlayer1Turn) ? getSquare(row, col + 1) : getSquare(row + 1, col);
            if (adjacentSquare != null) {
                adjacentSquare.setStyle(dominoStyle);
            }
            switchPlayerTurn();
            if (!gameModel.hasValidMove(isPlayer1Turn)) {
                endGame();
            }



        }
    logger.info(gameModel.printBoard());
    }


    private void endGame() {
        String winner = gameModel.getWinner();
        gameModel.saveCurrentGame();

        logger.warn(gameModel.getGameData() + " was ended");
        winnerAlert(winner);
    }

    void switchPlayerTurn() {
        if (isPlayer1Turn) {
            logger.info("Player 1 placed his domino ");
        } else {
            logger.info("Player 2 placed his domino ");
        }

        isPlayer1Turn = !isPlayer1Turn;
        logger.info("Player turn  switched");
    }

    public void winnerAlert(String winner) {
        logger.info(winner + " won the game");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText("Congratulations, " + winner + " won the game!");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> Platform.exit());
    }

    public StackPane getSquare(int row, int col) {
        return squares[row][col];
    }

    String getColorForSquare(Square square) {
        switch (square) {
            case NONE:
                return "#FFFFFF"; // Default color for empty square
            case PLAYER1:
                return "#ff0000"; // Color for player 1
            case PLAYER2:
                return "#0000ff"; // Color for player 2
            default:
                return "#FFFFFF"; // Default color for unknown values
        }
    }

}
