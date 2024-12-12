package com.example.demo.controller;

import com.example.demo.Screen.MainMenu;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		MainMenu mainMenu = new MainMenu();
		mainMenu.start(primaryStage);

		primaryStage.setResizable(false);
	}

	public static void main(String[] args) {
		launch(args);
	}
}