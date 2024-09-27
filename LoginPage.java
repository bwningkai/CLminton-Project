package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Vector;
import Model.MsUser;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginPage {

	PreparedStatement ps;

	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	private TextField emailField;
	private PasswordField passwordField;
	private Label emailLbl;
	private Label passwordLbl;
	private Button loginBtn;

	MenuBar menuBar;
	Menu menu;
	MenuItem loginPage, registerPage;

	ObservableList<MsUser> msusers;

	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 400, 200);

		emailField = new TextField();
		passwordField = new PasswordField();

		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		loginBtn = new Button("Login");

		menuBar = new MenuBar();
		menu = new Menu("Page");
		loginPage = new MenuItem("Login");
		registerPage = new MenuItem("Register");
		msusers = FXCollections.observableArrayList();
	}

	private void setComponent() {
		bp.setTop(menuBar);
		bp.setCenter(gp);
		gp.add(emailLbl, 0, 0);
		gp.add(emailField, 0, 1);
		gp.add(passwordLbl, 0, 2);
		gp.add(passwordField, 0, 3);
		gp.add(loginBtn, 0, 4);
		gp.setVgap(10);

		menuBar.getMenus().add(menu);
		menu.getItems().addAll(loginPage, registerPage);

		gp.setAlignment(Pos.CENTER);

	}

	private void setAction() {
		this.registerPage.setOnAction(e -> {
			new RegisterPage(stage);
		});

		this.loginBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Alert alert = new Alert(AlertType.WARNING);

				if (emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
					alert.setContentText("Email or Password must be filled!");
					alert.show();
					return;
				}
			}

		});

		loginBtn.setOnAction(e -> {
			String UserEmail = emailField.getText();
			String UserPassword = passwordField.getText();

			if (validatelogin(UserEmail, UserPassword)) {
				String UserRole = getUserRole(UserEmail);

				if ("User".equals(UserRole)) {
					new Homescene(stage);

				} else if ("Admin".equals(UserRole)) {
					new ManageProductScene(stage);

				}

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Wrong email or password!");
				alert.show();
				return;
			}

		});

	}

	private void getData() {
		String query = "SELECT * FROM msuser";
		Connection con = DatabaseConnections.getInstance().getConnection();
		ResultSet rs = DatabaseConnections.getInstance().execQuery(query);
		
		try {
			while (rs.next()) {
				String email = rs.getString("UserEmail");
				String password = rs.getString("UserPassword");
				String userRole = rs.getString("UserRole");
				msusers.add(new MsUser(email, password, userRole));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private boolean validatelogin(String UserEmail, String UserPassword) {
		try {
			Connection connection = DatabaseConnections.getInstance().getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM msuser WHERE UserEmail = ? AND UserPassword = ?");
			preparedStatement.setString(1, UserEmail);
			preparedStatement.setString(2, UserPassword);

			ResultSet resultSet = preparedStatement.executeQuery();
			return resultSet.next();

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public String getUserRole(String UserEmail) {

		try {
			Connection connection = DatabaseConnections.getInstance().getConnection();
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT UserRole FROM msuser WHERE UserEmail = ?");
			preparedStatement.setString(1, UserEmail);

			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("UserRole");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public LoginPage(Stage stage) {
		this.stage = stage;
		initialize();
		setComponent();
		setAction();
		stage.setTitle("Login");
		stage.setScene(scene);
		stage.show();
	}

}
