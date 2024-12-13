package com.example.demo.controller;

import com.example.demo.Level.LevelOne;
import com.example.demo.Level.LevelTwo;
import com.example.demo.Level.LevelThree;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.media.MediaPlayer;

public class Controller {

	private final Stage stage;
	private final MediaPlayer mediaPlayer;

	/**
	 * Constructs a Controller object.
	 *
	 * @param stage The JavaFX Stage used for scene management.
	 * @param mediaPlayer The MediaPlayer used for controlling background music or sound effects.
	 */
	public Controller(Stage stage, MediaPlayer mediaPlayer) {
		this.stage = stage;
		this.mediaPlayer = mediaPlayer;
	}

	/**
	 * Launches Level One of the game.
	 * Initializes the scene, sets it on the stage, and starts the game.
	 */
	public void launchLevelOne() {
		System.out.println("Launching com.example.demo.Level One...");
		LevelOne levelOne = new LevelOne(750, 1300, stage, mediaPlayer);
		Scene scene = levelOne.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelOne.startGame();
	}

	/**
	 * Launches Level Two of the game.
	 * Initializes the scene, sets it on the stage, and starts the game.
	 */
	public void launchLevelTwo() {
		System.out.println("Launching com.example.demo.Level Two...");
		LevelTwo levelTwo = new LevelTwo(750, 1300, stage, mediaPlayer);
		Scene scene = levelTwo.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelTwo.startGame();
	}

	/**
	 * Launches Level Three of the game.
	 * Initializes the scene, sets it on the stage, and starts the game.
	 */
	public void launchLevelThree() {
		System.out.println("Launching com.example.demo.Level Three...");
		LevelThree levelThree = new LevelThree(750, 1300, stage, mediaPlayer);
		Scene scene = levelThree.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelThree.startGame();
	}
}
