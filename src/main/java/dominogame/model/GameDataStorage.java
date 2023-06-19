package dominogame.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The GameDataStorage class is responsible for saving game data to a file.
 */
public class GameDataStorage {
    private static final Logger logger = LogManager.getLogger();

    /**
     * Saves the game data to a file.
     *
     * @param game The game data to be saved.
     */
    public void saveGameData(GameData game,String fileName) {
        try {
            List<GameData> gameDataList;
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<List<GameData>> typeReference = new TypeReference<>() {};

            // Read the content of the current file
            InputStream inputStream = getClass().getResourceAsStream("/"+fileName);
            if (inputStream != null && inputStream.available() > 0) {
                gameDataList = objectMapper.readValue(inputStream, typeReference);
            } else {
                gameDataList = new ArrayList<>();
            }
            inputStream.close();

            // Add the new game data to the list
            gameDataList.add(game);

            // Open a file writer to overwrite the current file
            FileWriter fileWriter = new FileWriter(getClass().getResource("/"+fileName).getPath());
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            objectMapper.writeValue(fileWriter, gameDataList);

            fileWriter.close();

            logger.info("Game data was Saved Succesfully...");

        } catch (IOException e) {
            logger.error("Error occurred while saving game data", e);

        }
    }
}
