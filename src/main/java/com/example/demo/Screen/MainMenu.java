package com.example.demo.Screen;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Objects;

public class MainMenu extends Application {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/mainmenu.png";
    private static final String AUDIO_PATH = "/com/example/demo/audio/background.mp3";

    private static MediaPlayer mediaPlayer;
    private Stage stage;

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        playBackgroundMusic();
        showMainMenu();
    }

    private void playBackgroundMusic() {
        if (mediaPlayer == null) {
            Media media = new Media(Objects.requireNonNull(getClass().getResource(AUDIO_PATH)).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        } else if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
    }

    private void showMainMenu() {
        ImageView background = createBackground();

        Label titleLabel = new Label("Sky Battle ♪");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer = new Region();
        spacer.setMinHeight(20); // Add space between the title and the start button

        Button startButton = new Button("Start Game");
        styleButton(startButton);
        startButton.setOnAction(_ -> showLevelSelection());

        Button settingsButton = new Button("Settings");
        styleButton(settingsButton);
        settingsButton.setOnAction(_ -> showSettings());

        Button exitButton = new Button("Exit");
        styleButton(exitButton);
        exitButton.setOnAction(_ -> stage.close());

        VBox menuBox = new VBox(20, titleLabel, spacer, startButton, settingsButton, exitButton);
        menuBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        StackPane root = new StackPane(background, menuBox);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setTitle("Sky Battle - Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    private void showLevelSelection() {
        LevelSelection levelSelection = new LevelSelection(stage, mediaPlayer);
        levelSelection.show();
    }

    private void showSettings() {
        Scene currentScene = stage.getScene(); // Get the current scene
        SettingsScreen settingsScreen = new SettingsScreen(stage, mediaPlayer, currentScene, null); // Pass the current scene and null for LevelParent
        settingsScreen.show();
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

    public static void main(String[] args) {
        launch();
    }
}