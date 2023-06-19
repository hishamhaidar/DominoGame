package dominogame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class DataSaveAndGetLeaderboardTest {

    private GameDataStorage gameDataStorage;
    private final String testFileName = "gameTest.json";

    @BeforeEach
    public void setup() {
        gameDataStorage = new GameDataStorage();
    }

    @Test
    public void testSaveGameDataAndGetPlayerStatistics() throws URISyntaxException, IOException {
        FileWriter fileWriter = new FileWriter(getClass().getResource("/" + testFileName).getPath());
        fileWriter.write("");
        fileWriter.close();
        // Save game data multiple times with different winners
        GameData gameTest1 = new GameData("Player1","Player2");
        gameTest1.setGameWinner("Player1");
        gameDataStorage.saveGameData(gameTest1,testFileName);
        GameData gameTest2 = new GameData("Player1","Player3");
        gameTest2.setGameWinner("Player1");
        gameDataStorage.saveGameData(gameTest2,testFileName);
        GameData gameTest3 = new GameData("Player2","Player3");
        gameTest3.setGameWinner("Player2");
        gameDataStorage.saveGameData(gameTest3,testFileName);


        // Get player statistics from the saved game data
        Map<String, Integer> playerStatistics = LeaderBoardModel.getPlayerStatistics(testFileName);


        assertEquals(2, playerStatistics.size());
        assertEquals(2, playerStatistics.get("Player1").intValue());
        assertEquals(1, playerStatistics.get("Player2").intValue());
    }
}
