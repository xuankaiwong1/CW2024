package com.example.demo.controller;

import com.example.demo.Screen.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	/**
	 * Initializes the main stage and displays the main menu.
	 * This method is called when the JavaFX application is launched.
	 *
	 * @param primaryStage The primary stage for the JavaFX application.
	 */
	@Override
	public void start(Stage primaryStage) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(primaryStage);

		// Disable window resizing
		primaryStage.setResizable(false);
	}

	/**
	 * The main method that launches the JavaFX application.
	 *
	 * @param args The command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
