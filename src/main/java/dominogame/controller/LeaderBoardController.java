package dominogame.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import dominogame.model.LeaderBoardModel;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LeaderBoardController {
    @FXML
    public TableView<Map.Entry<String, Integer>> leaderboardTable;
    @FXML
    public TableColumn<Map.Entry<String, Integer>, String> nameCol;
    @FXML
    public TableColumn<Map.Entry<String, Integer>, Integer> totalWinsCol;


    public void initialize() {
        // Initialize the columns
        nameCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getKey()));
        totalWinsCol.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getValue()).asObject());

        List<Map.Entry<String, Integer>> sortedStatistics = LeaderBoardModel.getTop5Sorted();
        ObservableList<Map.Entry<String, Integer>> data = FXCollections.observableArrayList(sortedStatistics);
        leaderboardTable.setItems(data);
    }

    public void BackToMenu(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/MainMenu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }
}
