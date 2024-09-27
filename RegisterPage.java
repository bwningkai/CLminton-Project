package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RegisterPage {

	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	FlowPane genderContainer;

	TextField emailField;
	PasswordField passwordField, confirmPassField;

	Label emailLbl, passwordLbl, confirmPassLbl, ageLbl, genderLbl, nationalityLbl;
	Button registerBtn;
	RadioButton maleRadio, femaleRadio;
	ToggleGroup genderGroup;

	ComboBox<String> countryBox;
	Spinner<Integer> ageSpinner;

	MenuBar menuBar;
	Menu menu;
	MenuItem loginPage, registerPage;

	public RegisterPage(Stage stage) {
		this.stage = stage;
		initialize();
		setComponent();
		setStyle();
		setAction();
		stage.setTitle("Register Page");
		stage.setScene(scene);
		stage.show();
	}

	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 600, 350);
		genderContainer = new FlowPane();

		menuBar = new MenuBar();
		menu = new Menu("Page");
		loginPage = new MenuItem("Login");
		registerPage = new MenuItem("Register");

		emailField = new TextField();
		passwordField = new PasswordField();
		confirmPassField = new PasswordField();

		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		confirmPassLbl = new Label("Confirm Password");
		ageLbl = new Label("Age");
		nationalityLbl = new Label("Nationality");
		genderLbl = new Label("Gender");

		maleRadio = new RadioButton("Male");
		femaleRadio = new RadioButton("Female");
		genderGroup = new ToggleGroup();

		countryBox = new ComboBox<>();
		ageSpinner = new Spinner<>();

		registerBtn = new Button("Register");
	}

	private void setComponent() {
		SpinnerValueFactory<Integer> ageSpinnerVal = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
		ageSpinner.setValueFactory(ageSpinnerVal);

		genderContainer.getChildren().addAll(maleRadio, femaleRadio);
		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);

		countryBox.getItems().add("Indonesia");
		countryBox.getItems().add("Singapore");
		countryBox.getItems().add("Malaysia");
		countryBox.getItems().add("America");
		countryBox.getItems().add("Canada");
		countryBox.getSelectionModel().selectFirst();
	}

	private void setStyle() {
		bp.setCenter(gp);

		gp.add(emailLbl, 0, 0);
		gp.add(emailField, 0, 1);

		gp.add(passwordLbl, 0, 2);
		gp.add(passwordField, 0, 3);

		gp.add(confirmPassLbl, 0, 4);
		gp.add(confirmPassField, 0, 5);

		gp.add(ageLbl, 0, 6);
		gp.add(ageSpinner, 0, 7);

		gp.add(genderLbl, 1, 0);
		gp.add(genderContainer, 1, 1);

		gp.add(nationalityLbl, 1, 2);
		gp.add(countryBox, 1, 3);

		gp.add(registerBtn, 1, 4);

		menuBar.getMenus().add(menu);
		menu.getItems().addAll(loginPage, registerPage);

		bp.setTop(menuBar);
		bp.setCenter(gp);
		gp.setVgap(10);
		gp.setHgap(10);
		gp.setAlignment(Pos.CENTER);
	}

	private void setAction() {
		this.loginPage.setOnAction(e -> {
			new LoginPage(stage);
		});

		registerBtn.setOnAction(e -> {
			// Perform registration logic here
			String email = emailField.getText();
			String password = passwordField.getText();
			String confirmPassword = confirmPassField.getText();
			int age = ageSpinner.getValue();
			String gender = getSelectedGender();
			String nationality = countryBox.getValue();

			if (!email.endsWith("@gmail.com")) {
				showAlert(AlertType.WARNING, "Email must end with '@gmail.com' !");
				return;
			}

			if (!isUsernameUnique(email)) {
				showAlert(AlertType.WARNING, "Username must be unique!");
				return;
			}

			if (password.length() < 6) {
				showAlert(AlertType.WARNING, "Password must contain at least 6 characters!");
				return;
			}

			if (!password.equals(confirmPassword)) {
				showAlert(AlertType.WARNING, "Confirm Password must be the same as Password!");
				return;
			}

			if (age <= 0) {
				showAlert(AlertType.WARNING, "Age must be greater than 0!");
				return;
			}

			if (gender == null) {
				showAlert(AlertType.WARNING, "Gender must be selected!");
				return;
			}

			if (nationality == null) {
				showAlert(AlertType.WARNING, "Nationality must be selected!");
				return;
			}

			String userID = generateUserID(email, password, age, gender, nationality);

			new LoginPage(stage);
		});

	}

	private boolean isUsernameUnique(String email) {
		try {
			Connection connection = DatabaseConnections.getInstance().getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT COUNT(*) FROM msuser WHERE UserEmail = '" + email + "'");
			if (resultSet.next()) {
				int count = resultSet.getInt(1);
				return count == 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	private String getSelectedGender() {
		if (maleRadio.isSelected()) {
			return "Male";
		} else if (femaleRadio.isSelected()) {
			return "Female";
		} else {
			return null;
		}
	}

	private void showAlert(AlertType type, String message) {
		Alert alert = new Alert(type);
		alert.setContentText(message);
		alert.showAndWait();
	}
	
	private String generateUserID(String email, String password, int age, String gender, String nationality) {
		String userID = "";
		try {
			Connection connection = DatabaseConnections.getInstance().getConnection();
			String lastUserID = getLatestUserIDFromDatabase(connection);
			int number = Integer.parseInt(lastUserID.substring(2)) + 1;
			userID = "UA" + String.format("%03d", number);

			String insertQuery = "INSERT INTO msuser (UserID, UserEmail, UserPassword, UserAge, UserGender, UserNationality, UserRole) VALUES (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
			preparedStatement.setString(1, userID);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, password);
			preparedStatement.setInt(4, age);
			preparedStatement.setString(5, gender);
			preparedStatement.setString(6, nationality);
			preparedStatement.setString(7, "User");
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userID;
	}

	private String getLatestUserIDFromDatabase(Connection connection) {
		String lastUserID = "";
		try {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT UserID FROM msuser ORDER BY UserID DESC LIMIT 1");
			if (resultSet.next()) {
				lastUserID = resultSet.getString("UserID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastUserID;
	}
}