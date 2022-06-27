package Game;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

public class MenuController {

    private static ObservableList<Player> resultList;
    private static ArrayList<Player> resultListArray = new ArrayList<>();
    private static boolean initialized = false;

    @FXML
    Button gameSettings;

    @FXML
    Button closeButton;

    @FXML
    Button highScores;

    public static ObservableList<Player> getResultList() {
        return resultList;
    }

    public static ArrayList<Player> getResultListArray() {
        return resultListArray;
    }

    public void initialize() throws FileNotFoundException {
        if (!initialized) {
            File file = new File("highScores.txt");
            if (file.exists()) {
                FileInputStream oldResults = new FileInputStream(file);
                try {
                    ObjectInputStream input = new ObjectInputStream(oldResults);
                    resultListArray.addAll((ArrayList<Player>) input.readObject());
                    resultList = FXCollections.observableList(resultListArray);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                resultList = FXCollections.observableList(resultListArray);
            }
            initialized = true;
        } else {
            initialized = true;
        }
    }


    @FXML
    private void gameSettingsScene() throws IOException {
        Scene boardSettings = gameSettings.getScene();
        Stage window = (Stage) boardSettings.getWindow();
        boardSettings = new Scene(FXMLLoader.load(getClass().getResource("scenes/boardSettings.fxml")));
        window.setScene(boardSettings);
    }

    @FXML
    private void highScoresTable() throws IOException {
        Scene highScoreScene = highScores.getScene();
        Stage window = (Stage) highScoreScene.getWindow();
        highScoreScene = new Scene(FXMLLoader.load(getClass().getResource("scenes/highScores.fxml")));
        window.setScene(highScoreScene);
    }

    public void exitApp() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}