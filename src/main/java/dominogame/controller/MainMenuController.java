package dominogame.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    public void startNewGame(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/newGame.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void displayLeaderboard(ActionEvent actionEvent) throws IOException{
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/LeaderBoard.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void quit(ActionEvent actionEvent) {
        Platform.exit();
    }
}
