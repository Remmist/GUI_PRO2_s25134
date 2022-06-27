package Game;

import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;



import static Game.BoardSettingsController.Columns;
import static Game.BoardSettingsController.Rows;

public class BoardController {

    private class Card extends StackPane {
        private ImageView cardImageView;
        private int cardID;

        public Card(String path, int cardID) throws FileNotFoundException {
            Image cardImage = new Image(new FileInputStream(path));
            cardImageView = new ImageView(cardImage);
            cardImageView.setFitHeight(100);
            cardImageView.setFitWidth(100);
            cardImageView.setPreserveRatio(true);
            this.cardID = cardID;
            Rectangle border = new Rectangle(100, 100);
            border.setFill(null);
            border.setStroke(Color.GREEN);
            border.setStrokeWidth(2);

            setAlignment(Pos.CENTER);
            getChildren().addAll(cardImageView, border);
            setOnMouseClicked(event -> {
                if (isOpen() || clickCount == 0)
                    return;

                clickCount--;

                //открыть первую карту
                if (actual == null) {
                    actual = this;
                    open(() -> {});
                    return;
                }
                open(() -> {
                    if(CardsAreEqual(actual))
                        pairsLeft--;
                    else {
                        actual.close();
                        this.close();
                    }

                    if (pairsLeft == 0) {
                        try {
                            endGame();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    actual = null;
                    clickCount = 2;
                });
                totalClicks++;
            });
            close();
        }

        public boolean isOpen() {
            return cardImageView.getOpacity() == 1;
        }

        public void open(Runnable action) {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), cardImageView);
            ft.setToValue(1);
            ft.setOnFinished(e -> action.run());
            ft.play();
        }

        public void close() {
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), cardImageView);
            ft.setToValue(0);
            ft.play();
        }

        public boolean CardsAreEqual(Card other) {
            return cardID == other.cardID;
        }
    }
    private class Timer extends Thread{
        private int sec;

        public void run(){
            System.out.println("Timer: start work");
            while (!isInterrupted()) {
                try {
                    sleep(1000);
                    sec++;
                } catch (InterruptedException e) {
                    System.out.println("Timer: end work, sec - "+sec);
                }
            }
        }

        public int getSec() {
            return sec;
        }
    }


    private Card actual = null;
    private Timer timer = new Timer();
    private static int score = 0;
    private int totalClicks = 0;
    private int clickCount = 2;
    private int pairs = (Columns * Rows) / 2;
    private int pairsLeft = pairs;

    @FXML
    Text appTime;

    @FXML
    BorderPane boardWindow;

    @FXML
    Pane gameBoard;

    public static int getScore() {
        return score;
    }

    public void initialize() throws FileNotFoundException {
        boardWindow.setPrefHeight(Rows * 100);
        boardWindow.setPrefWidth(Columns * 100);

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
//            cards.add(new Card("D:\\GUI_PRO2_s25134\\src\\Game\\images\\p" + (i + 1) + ".jpg", i));
//            cards.add(new Card("D:\\GUI_PRO2_s25134\\src\\Game\\images\\p" + (i + 1) + ".jpg", i));
            cards.add(new Card("/Users/remmist/IdeaProjects/Memory/GUI_PRO2_s25134/src/Game/images/p" + (i + 1) + ".jpg", i));
            cards.add(new Card("/Users/remmist/IdeaProjects/Memory/GUI_PRO2_s25134/src/Game/images/p" + (i + 1) + ".jpg", i));
        }

        Collections.shuffle(cards);

        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            card.setTranslateX(100 * (i % Rows));
            card.setTranslateY(100 * (i / Rows));
            gameBoard.getChildren().add(card);
        }
        timer.start();
    }

    public void endGame() throws IOException {
        timer.interrupt();
        score = (Rows * Columns) * (timer.getSec() / totalClicks);
        Scene menuScene = appTime.getScene();
        Stage window = (Stage) menuScene.getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/saveScore.fxml"));
        menuScene = new Scene(loader.load());
        window.setScene(menuScene);
        window.setWidth(600);
        window.setHeight(400);
        timer.stop();
    }

    public void exitApp() {
        Stage stage = (Stage) appTime.getScene().getWindow();
        stage.close();
    }
}