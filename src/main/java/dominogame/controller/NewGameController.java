package dominogame.controller;

import dominogame.model.DominoGameModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewGameController {
    @FXML
    public TextField player1Name;
    @FXML
    public TextField player2Name;

    public void startGame(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DominoGame.fxml"));
        Parent root = loader.load();

        DominoGameController gameController = loader.getController();

        DominoGameModel gameModel = new DominoGameModel();
        gameModel.startNewGame(player1Name.getText(), player2Name.getText());
        gameController.setGameModel(gameModel);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void backToMainMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
