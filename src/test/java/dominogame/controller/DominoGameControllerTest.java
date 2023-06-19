package dominogame.controller;

import dominogame.model.DominoGameModel;
import dominogame.model.Square;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DominoGameControllerTest {
    private DominoGameModel gameModel;
    private DominoGameController gameController;

    @BeforeEach
    void setup() {
        gameModel = new DominoGameModel();
        gameController = new DominoGameController();
        gameController.setGameModel(gameModel);
    }

    @Test
    void testHandleMouseClick_ValidMove() {
        StackPane[][] squares = new StackPane[DominoGameModel.BOARD_SIZE][DominoGameModel.BOARD_SIZE];
        for (int row = 0; row < DominoGameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < DominoGameModel.BOARD_SIZE; col++) {
                squares[row][col] = new StackPane();
            }
        }

        gameController.board = new GridPane();
        gameController.squares = squares;

        gameModel.startNewGame("Player1", "Player2");
        gameController.handleMouseClick(squares[0][0], 0, 0);

        assertEquals(Square.PLAYER1, gameModel.getSquare(0, 0));
        assertEquals("-fx-background-color: #ff0000;", squares[0][0].getStyle());
        assertEquals("-fx-background-color: #ff0000;", squares[0][1].getStyle());
    }

    @Test
    void testHandleMouseClick_InvalidMove() {
        StackPane[][] squares = new StackPane[DominoGameModel.BOARD_SIZE][DominoGameModel.BOARD_SIZE];
        for (int row = 0; row < DominoGameModel.BOARD_SIZE; row++) {
            for (int col = 0; col < DominoGameModel.BOARD_SIZE; col++) {
                squares[row][col] = new StackPane();
            }
        }

        gameController.board = new GridPane();
        gameController.squares = squares;

        gameModel.startNewGame("Player1", "Player2");
        gameModel.placeDomino(0, 0, true);
        gameController.handleMouseClick(squares[0][0], 0, 0);

        assertEquals(Square.PLAYER1, gameModel.getSquare(0, 0));
        assertNotEquals("#ff0000", squares[0][0].getStyle());
    }

    @Test
    void testSwitchPlayerTurn() {
        assertTrue(gameController.isPlayer1Turn);
        gameController.switchPlayerTurn();
        assertFalse(gameController.isPlayer1Turn);
        gameController.switchPlayerTurn();
        assertTrue(gameController.isPlayer1Turn);
    }

    @Test
    void testGetColorForSquare() {
        assertEquals("#FFFFFF", gameController.getColorForSquare(Square.NONE));
        assertEquals("#ff0000", gameController.getColorForSquare(Square.PLAYER1));
        assertEquals("#0000ff", gameController.getColorForSquare(Square.PLAYER2));
    }
}
