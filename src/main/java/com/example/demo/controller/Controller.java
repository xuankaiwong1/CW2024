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

	public Controller(Stage stage, MediaPlayer mediaPlayer) {
		this.stage = stage;
		this.mediaPlayer = mediaPlayer;
	}

	public void launchGame() {
		System.out.println("Game launched!");
		LevelOne levelOne = new LevelOne(750, 1300, stage, mediaPlayer);
		Scene scene = levelOne.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelOne.startGame();
	}

	public void launchLevelOne() {
		System.out.println("Launching com.example.demo.Level One...");
		LevelOne levelOne = new LevelOne(750, 1300, stage, mediaPlayer);
		Scene scene = levelOne.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelOne.startGame();
	}

	public void launchLevelTwo() {
		System.out.println("Launching com.example.demo.Level Two...");
		LevelTwo levelTwo = new LevelTwo(750, 1300, stage, mediaPlayer);
		Scene scene = levelTwo.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelTwo.startGame();
	}

	public void launchLevelThree() {
		System.out.println("Launching com.example.demo.Level Three...");
		LevelThree levelThree = new LevelThree(750, 1300, stage, mediaPlayer);
		Scene scene = levelThree.initializeScene();
		stage.setScene(scene);
		stage.show();
		levelThree.startGame();
	}
}