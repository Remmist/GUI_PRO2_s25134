package Game;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ScoreSaverController {

    @FXML
    Button saveResult;

    @FXML
    TextField playerName;

    public void saveResult() throws IOException {
        if (playerName.getText().equals("")) {
            ExeptionWrongInput();
        } else {
            Player player = new Player(playerName.getText(), BoardController.getScore());

            MenuController.getResultListArray().add(player);

            File resultFile = new File("highScores.txt");

            if (!resultFile.exists()) {
                resultFile.createNewFile();
            }

            try {
                FileOutputStream fs = new FileOutputStream(resultFile, true);
                ObjectOutputStream os = new ObjectOutputStream(fs);
                os.writeObject(MenuController.getResultListArray());
                os.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
            cleanUserInput();
            exitApp();
        }
    }

    public void cleanUserInput() {
        playerName.setText("");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Result has saved successfully");
        alert.show();
    }

    public void ExeptionWrongInput() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setHeaderText("Enter Name");
        alert.show();
    }


    public void exitApp() {
        Stage stage = (Stage) saveResult.getScene().getWindow();
        stage.close();
    }
}
