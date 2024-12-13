package com.example.demo.Screen;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.media.MediaPlayer;
import com.example.demo.Level.LevelParent;

import java.util.Objects;

public class SettingsScreen {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/mainmenu.png";

    private final Stage stage;
    private final MediaPlayer mediaPlayer;
    private final Scene previousScene;
    private final LevelParent levelParent;

    public SettingsScreen(Stage stage, MediaPlayer mediaPlayer, Scene previousScene, LevelParent levelParent) {
        this.stage = stage;
        this.mediaPlayer = mediaPlayer;
        this.previousScene = previousScene;
        this.levelParent = levelParent;
    }

    public void show() {
        ImageView background = createBackground();

        Label titleLabel = new Label("Settings ♪");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer = new Region();
        spacer.setMinHeight(20);

        Label volumeLabel = new Label("Volume ♪");
        volumeLabel.setFont(Font.font("Arial", 24));
        volumeLabel.setStyle("-fx-text-fill: black;");

        Slider volumeSlider = new Slider(0, 1, mediaPlayer.getVolume());
        volumeSlider.setMaxWidth(200);
        volumeSlider.valueProperty().addListener((_, _, newValue) -> mediaPlayer.setVolume(newValue.doubleValue()));

        Label keyBindingsLabel = new Label("\nKey Bindings ♪");
        keyBindingsLabel.setFont(Font.font("Arial", 24));
        keyBindingsLabel.setStyle("-fx-text-fill: black;");

        Label moveUpLabel = new Label("Move Up: W");
        moveUpLabel.setFont(Font.font("Arial", 18));
        moveUpLabel.setStyle("-fx-text-fill: black;");

        Label moveLeftLabel = new Label("Move Left: A");
        moveLeftLabel.setFont(Font.font("Arial", 18));
        moveLeftLabel.setStyle("-fx-text-fill: black;");

        Label moveDownLabel = new Label("Move Down: S");
        moveDownLabel.setFont(Font.font("Arial", 18));
        moveDownLabel.setStyle("-fx-text-fill: black;");

        Label moveRightLabel = new Label("Move Right: D");
        moveRightLabel.setFont(Font.font("Arial", 18));
        moveRightLabel.setStyle("-fx-text-fill: black;");

        Label fireLabel = new Label("Fire: SPACE");
        fireLabel.setFont(Font.font("Arial", 18));
        fireLabel.setStyle("-fx-text-fill: black;");

        Label pauseLabel = new Label("Pause: ESC");
        pauseLabel.setFont(Font.font("Arial", 18));
        pauseLabel.setStyle("-fx-text-fill: black;");

        Button backButton = new Button("Back ♪");
        styleButton(backButton);
        backButton.setOnAction(_ -> {
            stage.setScene(previousScene);
            levelParent.resumeGameFromSettings();
        });

        VBox settingsBox = new VBox(20, titleLabel, spacer, volumeLabel, volumeSlider, keyBindingsLabel, moveUpLabel, moveLeftLabel, moveDownLabel, moveRightLabel, pauseLabel, backButton);
        settingsBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        StackPane root = new StackPane(background, settingsBox);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setTitle("Sky Battle - Settings");
        stage.setScene(scene);
        stage.show();
    }

    private ImageView createBackground() {
        try {
            Image backgroundImage = new Image(Objects.requireNonNull(getClass().getResource(BACKGROUND_IMAGE_PATH)).toExternalForm());
            ImageView background = new ImageView(backgroundImage);
            background.setFitWidth(SCREEN_WIDTH);
            background.setFitHeight(SCREEN_HEIGHT);
            return background;
        } catch (NullPointerException e) {
            System.err.println("Background image not found at: " + BACKGROUND_IMAGE_PATH);
            return new ImageView();
        }
    }

    private void styleButton(Button button) {
        button.setPrefSize(200, 50);
        button.setStyle("-fx-font-size: 18px; -fx-background-color: black; -fx-text-fill: white; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #333333; -fx-text-fill: white; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(_ -> button.setStyle("-fx-font-size: 18px; -fx-background-color: black; -fx-text-fill: white; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }
}