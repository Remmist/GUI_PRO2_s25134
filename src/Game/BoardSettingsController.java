package Game;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardSettingsController{

    public static int Rows;
    public static int Columns;

    @FXML
    TextField rows;

    @FXML
    TextField columns;

    @FXML
    Button backButton;

    @FXML
    private void startGame() throws IOException {
        setSettings();
        if ((Columns * Rows) % 2 == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/board.fxml"));
            Parent root = loader.load();
            Stage window = new Stage();
            window.setScene(new Scene(root));
            window.setMinWidth((6 * Columns) + 250);
            window.setMinHeight((6 * Rows) + 250);
            window.show();
        } else {
            ExceptionWrongInput();
        }
    }

    @FXML
    private void backToMenu() throws IOException {
        Scene menuScene = backButton.getScene();;
        Stage window = (Stage) menuScene.getWindow();
        menuScene = new Scene(FXMLLoader.load(getClass().getResource("scenes/mainMenu.fxml")));
        window.setScene(menuScene);
        window.setWidth(600);
        window.setHeight(400);
    }

    public void setSettings() {
        if(rows.getText().equals("") || columns.getText().equals("")) {
            ExceptionInputNotNumber();
            return;
        }
        Rows = Integer.parseInt(rows.getText());
        Columns = Integer.parseInt(columns.getText());
    }

    public void exitApp() {
        Stage stage = (Stage) backButton.getScene().getWindow();
        stage.close();
    }

    public void ExceptionWrongInput() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Can't create game");
        alert.setContentText("You cannot create a playing field from the entered values." + "\nThe product of the numbers of columns and rows must be an even number.");
        alert.show();
    }

    public void ExceptionInputNotNumber() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Can't create game");
        alert.setContentText("You cannot create a playing field from the entered values." + "\nEnter numbers, not symbols.");
        alert.show();
    }
}