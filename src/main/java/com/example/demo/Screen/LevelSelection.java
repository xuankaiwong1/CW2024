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

import java.util.Objects;

/**
 * The LevelSelection class provides the user interface for the level selection screen of the game.
 * It displays buttons for selecting different levels (Level 1, Level 2, and Level 3) and
 * navigates the user to the appropriate level based on their progress in the game.
 *
 * The screen includes:
 * <ul>
 *     <li>A title label that instructs the user to select a level.</li>
 *     <li>Buttons for selecting Level 1, Level 2, and Level 3, with Level 2 and Level 3 buttons disabled
 *         if the previous level has not been completed.</li>
 *     <li>A "Back to Main Menu" button for returning to the main menu.</li>
 *     <li>A background image for the level selection screen.</li>
 * </ul>
 *
 * Constructor:
 * <ul>
 *     <li>{@link #LevelSelection(Stage, MediaPlayer)}: Initializes the LevelSelection screen with the stage and media player.</li>
 * </ul>
 *
 * Methods:
 * <ul>
 *     <li>{@link #show()}: Displays the level selection screen with the title, buttons, and background image.</li>
 *     <li>{@link #startLevelOne()}: Launches Level 1 when the Level 1 button is pressed.</li>
 *     <li>{@link #startLevelTwo()}: Launches Level 2 when the Level 2 button is pressed.</li>
 *     <li>{@link #startLevelThree()}: Launches Level 3 when the Level 3 button is pressed.</li>
 *     <li>{@link #showMainMenu()}: Navigates the user back to the main menu.</li>
 *     <li>{@link #createBackground()}: Creates and returns the background image for the level selection screen.</li>
 *     <li>{@link #styleButton(Button)}: Styles the buttons with a consistent look.</li>
 *     <li>{@link #createButton(String, EventHandler)}: Creates a styled button with a specified label and event handler.</li>
 *     <li>{@link #createLabel()}: Creates and styles the title label for the level selection screen.</li>
 * </ul>
 */
public class LevelSelection {

    private static final int SCREEN_WIDTH = 1300;
    private static final int SCREEN_HEIGHT = 750;
    private static final String BACKGROUND_IMAGE_PATH = "/com/example/demo/images/mainmenu.png";

    private final Stage stage;
    private final Controller controller;

    /**
     * Constructs a LevelSelection instance with the given stage and media player.
     *
     * @param stage the stage on which the level selection screen will be displayed.
     * @param mediaPlayer the media player for handling background music and sound effects.
     */
    public LevelSelection(Stage stage, MediaPlayer mediaPlayer) {
        this.stage = stage;
        this.controller = new Controller(stage, mediaPlayer);
    }

    /**
     * Displays the level selection screen with title, level buttons, and a background image.
     */
    public void show() {
        ImageView background = createBackground();

        Label titleLabel = createLabel();
        titleLabel.setStyle("-fx-text-fill: black;");

        Region spacer1 = new Region();
        spacer1.setMinHeight(20); // Add space between the title and the level 1 button

        Button levelOneButton = createButton("Level 1", _ -> startLevelOne());
        Button levelTwoButton = createButton("Level 2", _ -> startLevelTwo());
        levelTwoButton.setDisable(!GameState.getInstance().isLevelOneCompleted()); // Disable if level 1 not completed
        Button levelThreeButton = createButton("Level 3", _ -> startLevelThree());
        levelThreeButton.setDisable(!GameState.getInstance().isLevelTwoCompleted()); // Disable if level 2 not completed

        Region spacer2 = new Region();
        spacer2.setMinHeight(20);

        Button backButton = createButton("Back to Main Menu", _ -> showMainMenu());

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

    private Button createButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        styleButton(button);
        button.setOnAction(eventHandler);
        return button;
    }

    private Label createLabel() {
        Label label = new Label("Select Level ♪");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        label.setStyle("-fx-text-fill: white;");
        return label;
    }
}
