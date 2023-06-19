package dominogame.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code LeaderBoardModel} class represents the leaderboard of game statistics.
 */
public class LeaderBoardModel {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Retrieves the player statistics from the game data.
     *
     * @param fileName The name of the file containing the game data.
     * @return The player statistics (Name, Total wins).
     */
    public static Map<String, Integer> getPlayerStatistics(String fileName) {
        Map<String, Integer> playerStatistics = new HashMap<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<GameData>> typeReference = new TypeReference<>() {};

            // Load the resource file from the target directory
            InputStream inputStream = LeaderBoardModel.class.getResourceAsStream("/" + fileName);
            if (inputStream != null && inputStream.available() > 0) {
                List<GameData> gameDataList = objectMapper.readValue(inputStream, typeReference);

                for (GameData gameData : gameDataList) {
                    String winner = gameData.getGameWinner();
                    if (winner != null && !winner.isEmpty()) {
                        playerStatistics.put(winner, playerStatistics.getOrDefault(winner, 0) + 1);
                    }
                }
            }
        } catch (IOException e) {
            logger.error("Error occurred while retrieving player statistics", e);
        }

        return playerStatistics;
    }

    /**
     * Retrieves the top 5 players sorted by total wins.
     *
     * @return The top 5 players sorted by total wins.
     */
    public static List<Map.Entry<String, Integer>> getTop5Sorted() {
        Map<String, Integer> playerStatistics = getPlayerStatistics("games.json");
        return playerStatistics.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5) // Limit to the top 5 players
                .collect(Collectors.toList());
    }
}
