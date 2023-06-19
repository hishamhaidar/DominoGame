package dominogame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DominoGameModelTest {
    private DominoGameModel gameModel;

    @BeforeEach
    void setUp() {
        gameModel = new DominoGameModel();
    }

    @Test
    void testStartNewGame() {
        gameModel.startNewGame("Player1", "Player2");

        GameData gameData = gameModel.getGameData();
        assertNotNull(gameData);
        assertEquals("Player1", gameData.getPlayer1());
        assertEquals("Player2", gameData.getPlayer2());
        assertEquals(0, gameData.getNumberOfDominosPlacedBy1());
        assertEquals(0, gameData.getNumberOfDominosPlacedBy2());
        assertNull(gameData.getLastPlayer());
        assertNull(gameData.getGameWinner());
    }

    @Test
    void testPlaceDomino_ValidMove() {
        gameModel.startNewGame("Player1", "Player2");
        gameModel.placeDomino(0, 0, true);
        assertEquals(Square.PLAYER1, gameModel.getSquare(0, 0));
        assertEquals(Square.PLAYER1, gameModel.getSquare(0, 1));
        assertEquals("Player1", gameModel.getGameData().getLastPlayer());

        gameModel.placeDomino(1, 0, false);
        assertEquals(Square.PLAYER2, gameModel.getSquare(1, 0));
        assertEquals(Square.PLAYER2, gameModel.getSquare(2, 0));
        assertEquals("Player2", gameModel.getGameData().getLastPlayer());
    }


    @Test
    void testIsMoveValid() {
        gameModel.startNewGame("Player1", "Player2");

        assertTrue(gameModel.isMoveValid(0, 0, true));
        gameModel.placeDomino(0, 0, true);
        assertFalse(gameModel.isMoveValid(0, 0, true));
        assertFalse(gameModel.isMoveValid(0, 0, false));
        assertFalse(gameModel.isMoveValid(0, 1, true));
        assertFalse(gameModel.isMoveValid(0, 1, false));
        assertTrue(gameModel.isMoveValid(0, 2, false));
        assertTrue(gameModel.isMoveValid(1, 0, false));
    }

    @Test
    void testHasValidMove() {
        gameModel.startNewGame("Player1", "Player2");

        assertTrue(gameModel.hasValidMove(true));
        assertTrue(gameModel.hasValidMove(false));

        gameModel.placeDomino(0, 0, true);
        assertTrue(gameModel.hasValidMove(false));
        assertTrue(gameModel.hasValidMove(true));


    }

    @Test
    void testGetWinner() {
        gameModel.startNewGame("Player1", "Player2");
        gameModel.placeDomino(0, 0, true);
        gameModel.placeDomino(1, 0, false);
        assertEquals("Player2", gameModel.getWinner());
    }


}
