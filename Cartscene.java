package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.CartTable;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.Window;

public class Cartscene {
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	ScrollPane sp;
	Stage popupStage;

	Label cartListLbl, totalPriceLbl, nameLbl, brandLbl, priceLbl;
	ObservableList<CartTable> cart;
	TableView<CartTable> tbView;

	Button coBtn;
	Button delBtn;

	MenuBar menuBar;
	Menu pageMenu;
	MenuItem homePage, CartPage, historyPage, logoutPageMenu;

	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		sp = new ScrollPane();
		sp.setContent(bp);

		scene = new Scene(bp, 900, 400);

		cartListLbl = new Label("Your Cart List");
		nameLbl = new Label("Product Name :");
		brandLbl = new Label("Product Brand :");
		priceLbl = new Label("Product Price :");
		totalPriceLbl = new Label("Total Price :");

		menuBar = new MenuBar();
		pageMenu = new Menu("Page");

		homePage = new MenuItem("Home");
		CartPage = new MenuItem("Cart");
		historyPage = new MenuItem("History");
		logoutPageMenu = new MenuItem("Logout");

		coBtn = new Button("Checkout");
		delBtn = new Button("Delete Button");

		cart = FXCollections.observableArrayList();
		tbView = new TableView<>();

	}

	public void initTbView() {
		TableColumn<CartTable, String> colName = new TableColumn<>("Name");
		TableColumn<CartTable, String> colBrand = new TableColumn<>("Brand");
		TableColumn<CartTable, Integer> colPrice = new TableColumn<>("Price");
		TableColumn<CartTable, Integer> colQty = new TableColumn<>("Quantity");
		TableColumn<CartTable, Integer> colTotal = new TableColumn<>("Total");

		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		colTotal.setCellValueFactory(new PropertyValueFactory<>("Total"));

		colName.setPrefWidth(50);
		colBrand.setPrefWidth(50);
		colPrice.setPrefWidth(50);
		colQty.setPrefWidth(50);
		colTotal.setPrefWidth(50);

		tbView.getColumns().addAll(colName, colBrand, colPrice, colQty, colTotal);
		tbView.setItems(cart);

		tbView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// getData();

	}

	public void showTransactionPopUp() {
		// tampilan popup
		popupStage = new Stage();
		popupStage.initModality(Modality.APPLICATION_MODAL);
		popupStage.initOwner(stage);

		Window window = new Window("Transcation Card");
		popupStage.setWidth(700);
		popupStage.setHeight(300);

		window.getContentPane().setStyle("-fx-background-color: #6A90B6;");

		// initialize
		Label listLbl = new Label("List");
		Label courierLbl = new Label("Courier");
		ComboBox<String> courierBox = new ComboBox<>();
		CheckBox insuranceCheck = new CheckBox("Use Insurance");
		Button checkOutBtn = new Button("Checkout");
		Label titleLabel = new Label("Transaction Card");
		Label totalPriceLbl = new Label("Total Price :");

		HBox titleBox = new HBox();
		titleBox.setAlignment(Pos.CENTER);
		titleBox.setStyle("-fx-background-color: black;");
		Label titleLbl = new Label("Transaction Card");
		titleLbl.setStyle("-fx-text-fill: white; -fx-font-size: 18; -fx-font-weight: bold;");

		titleBox.getChildren().add(titleLabel);

		// isi courrierBox
		courierBox.getItems().add("JNE");
		courierBox.getItems().add("Sicepat");
		courierBox.getItems().add("Wahana");
		courierBox.getItems().add("J&T");
		courierBox.getItems().add("TIKI");
		courierBox.getSelectionModel().selectFirst();

		window.getContentPane().getChildren().add(titleBox);

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getChildren().addAll(titleBox, listLbl, courierLbl, courierBox, insuranceCheck, checkOutBtn);

		window.getContentPane().getChildren().add(vbox);

		// checkout button
		checkOutBtn.setOnMouseClicked(e -> {

			boolean useInsurance = insuranceCheck.isSelected();
			int totalPrice = Integer.parseInt(totalPriceLbl.getText());
			if (useInsurance) {
				totalPrice += 90000;
			}

			Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
			confirmationAlert.setTitle("Confirmation");
			confirmationAlert.setHeaderText("Are you sure want to checkout all the item?");
			confirmationAlert.setContentText("Total Price: " + totalPrice);

			confirmationAlert.getButtonTypes().setAll(ButtonType.OK, ButtonType.CANCEL);

			ButtonType result = confirmationAlert.showAndWait().orElse(ButtonType.CANCEL);

			if (result == ButtonType.OK) {
				String transactionID = generateTransactionId();
				clearCart();
				new Cartscene(stage);
			} else {
				popupStage.close();
				new Cartscene(stage);
			}
		});
		popupStage.setScene(new Scene(window.getContentPane()));
		popupStage.showAndWait();
	}

	private static int transactionID = 0;

	private static String generateTransactionId() {
		transactionID++;
		return String.format("TH%03d", transactionID);
	}

	private static void clearCart() {
	}

	private void getData(String userID) {
		DatabaseConnections dbConnections = DatabaseConnections.getInstance();
		Connection con = dbConnections.getConnection();

		String query = String.format("SELECT ProductName, ProductMerk, Quantity, ProductPrice FROM `carttable`\n"
				+ "JOIN msproduct ON msproduct.ProductID = carttable.ProductID WHERE UserID = '%s'", userID);

		ResultSet rs = dbConnections.execQuery(query);

		try {
			while (rs.next()) {
				String name = rs.getString("ProductName");
				String brand = rs.getString("ProductMerk");
				int quantity = rs.getInt("ProductStock");
				int price = rs.getInt("ProductPrice");
				int totalPrice = quantity * price;

				cart.add(new CartTable(name, brand, price, quantity, totalPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbView.setItems(cart);
	}

//	private void storeCartDataInDatabase(String transactionId, String userID) {
//		Connection con = DatabaseConnections.getInstance().getConnection();
//		PreparedStatement statement = null;
//
//		try {
//			String updateQuery = "UPDATE carttable SET Quantity = ? WHERE UserID = ? AND ProductID = ?";
//			statement = con.prepareStatement(updateQuery);
//
//			for (CartTable cartItem : cart) {
//				Product product = cartItem.getProduct();
//				statement.setInt(1, cartItem.getQuantity());
//				statement.setString(2, userID);
//				statement.setString(3, Product.getID());
//
//				statement.executeUpdate();
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}


	public void setComponent() {
		sp.setContent(bp);
		bp.setTop(menuBar);
		bp.setCenter(gp);

		gp.setVgap(10);
		gp.setHgap(10);

		gp.add(tbView, 0, 1);
		gp.setAlignment(Pos.CENTER);

		cartListLbl.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

		menuBar.setPrefWidth(900);
		gp.add(cartListLbl, 0, 0);

		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().addAll(nameLbl, brandLbl, priceLbl, totalPriceLbl);

		coBtn.setPrefWidth(700);
		delBtn.setPrefWidth(700);

		pageMenu.getItems().addAll(homePage, CartPage, historyPage, logoutPageMenu);
		menuBar.getMenus().add(pageMenu);

		gp.setPadding(new Insets(10));
		gp.add(vbox, 1, 1);
		gp.add(coBtn, 0, 3);
		gp.add(delBtn, 0, 4);
	}

	private void setOnAction() {
		coBtn.setOnMouseClicked(e -> showTransactionPopUp());

		this.logoutPageMenu.setOnAction(e -> {
			new LoginPage(stage);
		});

		this.homePage.setOnAction(e -> {
			new Homescene(stage);
		});
		this.historyPage.setOnAction(e -> {
			new Historyscene(stage);
		});

	}

	public Cartscene(Stage stage) {
		initialize();
		initTbView();
		setComponent();
		setOnAction();
		this.stage = stage;
		stage.setTitle("Cart");
		stage.setScene(scene);
		stage.show();
	}
}
