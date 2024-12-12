package com.example.demo.Level;

import com.example.demo.Image.GameOverImage;
import com.example.demo.Image.WinImage;
import com.example.demo.Actor.Enemy.Boss;
import com.example.demo.Screen.LevelSelection;
import com.example.demo.Screen.MainMenu; // Add this import statement
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.media.MediaPlayer;

public class LevelThree extends LevelParent {

    private static final String BACKGROUND_IMAGE_NAME = "/com/example/demo/images/background3.png";
    private static final int PLAYER_INITIAL_HEALTH = 5;
    private final Boss boss;
    private LevelViewLevelTwo levelView;

    private double screenHeight;
    private double screenWidth;

    public LevelThree(double screenHeight, double screenWidth, Stage stage, MediaPlayer mediaPlayer) {
        super(BACKGROUND_IMAGE_NAME, screenHeight, screenWidth, PLAYER_INITIAL_HEALTH, stage, mediaPlayer);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        boss = new Boss();
    }

    @Override
    protected void initializeFriendlyUnits() {
        getRoot().getChildren().add(getUser());
    }

    @Override
    protected void checkIfGameOver() {
        if (userIsDestroyed()) {
            loseGame();
        } else if (boss.isDestroyed()) {
            winGame();
        }
    }

    @Override
    protected void spawnEnemyUnits() {
        if (getCurrentNumberOfEnemies() == 0) {
            addEnemyUnit(boss);
            getRoot().getChildren().add(boss.getShieldImage());
        }
    }

    @Override
    protected LevelView instantiateLevelView() {
        levelView = new LevelViewLevelTwo(getRoot(), PLAYER_INITIAL_HEALTH);
        return levelView;
    }

    @Override
    protected void winGame() {
        super.winGame();
        displayWinScreen(screenHeight, screenWidth);
        levelComplete(); // Notify that the level is complete
    }

    @Override
    protected void loseGame() {
        super.loseGame();
        displayGameOverScreen(screenHeight, screenWidth);
    }

    private void displayWinScreen(double screenHeight, double screenWidth) {
        // Create a dark overlay
        Rectangle overlay = new Rectangle(screenWidth, screenHeight);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.7);

        // Create a "You Win" image
        WinImage winImage = new WinImage(screenWidth / 2 - 300, screenHeight / 2 - 250);
        winImage.showWinImage();

        // Create buttons
        Button mainMenuButton = createStyledButton("Return to Main Menu", e -> returnToMainMenu());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, mainMenuButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a VBox to hold the win image and buttons
        VBox layout = new VBox(-50, winImage, buttonLayout);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a stack pane to overlay the win image and buttons
        StackPane winPane = new StackPane();
        winPane.getChildren().addAll(overlay, layout);
        StackPane.setAlignment(layout, javafx.geometry.Pos.CENTER);

        getRoot().getChildren().add(winPane);
    }

    private void displayGameOverScreen(double screenHeight, double screenWidth) {
        // Create a dark overlay
        Rectangle overlay = new Rectangle(screenWidth, screenHeight);
        overlay.setFill(Color.BLACK);
        overlay.setOpacity(0.7);

        // Create a "Game Over" image
        GameOverImage gameOverImage = new GameOverImage(screenWidth / 2 - 350, screenHeight / 2 - 300);

        // Create buttons
        Button mainMenuButton = createStyledButton("Return to Main Menu", e -> returnToMainMenu());
        Button restartButton = createStyledButton("Restart Game ♪", e -> restartGame());

        // Layout for buttons
        HBox buttonLayout = new HBox(20, mainMenuButton, restartButton);
        buttonLayout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a VBox to hold the game over image and buttons
        VBox layout = new VBox(-50, gameOverImage, buttonLayout);
        layout.setAlignment(javafx.geometry.Pos.CENTER);

        // Create a stack pane to overlay the game over image and buttons
        StackPane gameOverPane = new StackPane();
        gameOverPane.getChildren().addAll(overlay, layout);
        StackPane.setAlignment(layout, javafx.geometry.Pos.CENTER);

        getRoot().getChildren().add(gameOverPane);
    }

    private void returnToMainMenu() {
        MainMenu mainMenu = new MainMenu();
        mainMenu.start(stage);
    }

    private void restartGame() {
        LevelThree levelThree = new LevelThree(screenHeight, screenWidth, stage, mediaPlayer);
        Scene scene = levelThree.initializeScene();
        stage.setScene(scene);
        levelThree.startGame();
    }

    private Button createStyledButton(String text, EventHandler<ActionEvent> eventHandler) {
        Button button = new Button(text);
        styleButton(button);
        button.setOnAction(eventHandler);
        return button;
    }

    private void styleButton(Button button) {
        button.setPrefSize(200, 50);
        button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: #ff69b4; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 18px; -fx-background-color: pink; -fx-text-fill: black; " +
                "-fx-border-color: pink; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;"));
    }
}