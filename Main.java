package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		new LoginPage(primaryStage);
//		new Homescene(primaryStage);
		
//		new ManageProductScene().start(primaryStage);
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
