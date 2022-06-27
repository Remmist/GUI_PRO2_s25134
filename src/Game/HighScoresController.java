package Game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;



public class HighScoresController {

    @FXML
    ListView<Player> highScoresListView;

    @FXML
    Button backButton;

    public void initialize() {
        MenuController.getResultListArray().sort(new PlayerSorter());
        highScoresListView.setItems(MenuController.getResultList());
    }

    @FXML
    private void backToMenu() throws IOException {
        Scene menuScene = backButton.getScene();;
        Stage window = (Stage) menuScene.getWindow();;
        menuScene = new Scene(FXMLLoader.load(getClass().getResource("scenes/mainMenu.fxml")));
        window.setScene(menuScene);
        window.setWidth(600);
        window.setHeight(400);
    }

    public void exitApp() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }
}
