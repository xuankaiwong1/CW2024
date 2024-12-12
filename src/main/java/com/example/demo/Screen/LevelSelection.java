package com.example.demo.Screen;

import com.example.demo.Level.GameState;
import com.example.demo.controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.media.MediaPlayer;

public class LevelSelection {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/mainmenu.png";

    private final Stage stage;
    private final Controller controller;

    public LevelSelection(Stage stage, MediaPlayer mediaPlayer) {
        this.stage = stage;
        this.controller = new Controller(stage, mediaPlayer); // Initialize the controller with stage and mediaPlayer
    }

    public void show() {
        ImageView background = createBackground();

        Label titleLabel = createLabel("Select Level ♪", 36, FontWeight.BOLD);
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer1 = new Region();
        spacer1.setMinHeight(20); // Add space between the title and the level 1 button

        Button levelOneButton = createButton("Level 1", e -> startLevelOne());
        Button levelTwoButton = createButton("Level 2", e -> startLevelTwo());
        levelTwoButton.setDisable(!GameState.getInstance().isLevelOneCompleted()); // Disable if level 1 not completed
        Button levelThreeButton = createButton("Level 3", e -> startLevelThree());
        levelThreeButton.setDisable(!GameState.getInstance().isLevelTwoCompleted()); // Disable if level 2 not completed

        Region spacer2 = new Region();
        spacer2.setMinHeight(20);

        Button backButton = createButton("Back to Main Menu", e -> showMainMenu());

        VBox levelSelectionBox = new VBox(20, titleLabel, spacer1, levelOneButton, levelTwoButton, levelThreeButton, spacer2, backButton);
        levelSelectionBox.setStyle("-fx-alignment: center; -fx-padding: 20;");

        StackPane root = new StackPane(background, levelSelectionBox);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        stage.setTitle("Sky Battle - Level Selection");
        stage.setScene(scene);
        stage.show();
    }

    private void startLevelOne() {
        controller.launchLevelOne();
    }

    private void startLevelTwo() {
        controller.launchLevelTwo();
    }

    private void startLevelThree() {
        controller.launchLevelThree();
    }

    private void showMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(stage);
    }

    private ImageView createBackground() {
        try {
            Image backgroundImage = new Image(getClass().getResource(BACKGROUND_IMAGE_PATH).toExternalForm());
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
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #333333; -fx-text-fill: white; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: black; -fx-text-fill: white; " +
                "-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }

    private Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        styleButton(button);
        button.setOnAction(eventHandler);
        return button;
    }

    private Label createLabel(String text, int fontSize, FontWeight fontWeight) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", fontWeight, fontSize));
        label.setStyle("-fx-text-fill: white;");
        return label;
    }
}