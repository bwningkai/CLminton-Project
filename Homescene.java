package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableSelectionModel;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Homescene {
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	ScrollPane sp;

	MenuBar menuBar;
	Menu pageMenu;
	MenuItem homePage, CartPage, historyPage, logoutPageMenu;

	Label productList, productName, productBrand, productPrice, totalPrice;
	Button addBtn;
	Spinner<Integer> spQty;

	ObservableList<Product> products;
	TableView<Product> tbView;

	private Connection con = DatabaseConnections.getInstance().getConnection();

	public Homescene(Stage stage) {
		this.stage = stage;

		initialize();
		setComponent();
		setStyle();
		initTbView();
		this.stage = stage;
		stage.setTitle("Home");
		stage.setScene(scene);
		stage.show();
	}

	private void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		sp = new ScrollPane();
		sp.setContent(bp);

		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(sp);

		scene = new Scene(stackPane, 800, 600);

		menuBar = new MenuBar();
		pageMenu = new Menu("Page");

		homePage = new MenuItem("Home");
		CartPage = new MenuItem("Cart");
		historyPage = new MenuItem("History");
		logoutPageMenu = new MenuItem("Logout");

		productList = new Label("Product List :");
		productName = new Label("Product Name :");
		productBrand = new Label("Product Brand :");
		productPrice = new Label("Price :");
		totalPrice = new Label("Total Price :");

		addBtn = new Button("Add to Cart");
		spQty = new Spinner<>(1, 100, 1);

		products = FXCollections.observableArrayList();
		tbView = new TableView<>();

	}

	private void setComponent() {
		pageMenu.getItems().addAll(homePage, CartPage, historyPage, logoutPageMenu);
		menuBar.getMenus().addAll(pageMenu);
	}

	private void setStyle() {
		sp.setContent(bp);
		bp.setTop(menuBar);
		bp.setCenter(gp);

		gp.setVgap(10);
		gp.setHgap(10);

		productList.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");

		menuBar.setPrefWidth(800);
		gp.add(productList, 0, 0);

		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().addAll(productName, productBrand, productPrice, spQty, totalPrice, addBtn);

		gp.setPadding(new Insets(10));
		gp.add(tbView, 0, 1);
		gp.add(vbox, 1, 1);
		gp.setAlignment(Pos.CENTER);

	}

	private void initTbView() {
		TableColumn<Product, String> colId = new TableColumn<>("ID");
		TableColumn<Product, String> colName = new TableColumn<>("Name");
		TableColumn<Product, String> colBrand = new TableColumn<>("Brand");
		TableColumn<Product, Integer> colStock = new TableColumn<>("Stock");
		TableColumn<Product, Integer> colPrice = new TableColumn<>("Price");

		colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colBrand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		colStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));

		colId.setPrefWidth(50);
		colName.setPrefWidth(100);
		colBrand.setPrefWidth(100);
		colStock.setPrefWidth(50);
		colPrice.setPrefWidth(100);

		tbView.getColumns().addAll(colId, colName, colBrand, colStock, colPrice);
		tbView.setItems(products);

		getData();
		setOnAction();
	}

	private void setOnAction() {
		tbView.setOnMouseClicked(e -> {
			int quantity = spQty.getValue();
			int totalPriceValue;

			TableSelectionModel<Product> tableSelectionModel = tbView.getSelectionModel();
			tableSelectionModel.setSelectionMode(SelectionMode.SINGLE);

			Product products = tableSelectionModel.getSelectedItem();
			productName.setText("Product Name: " + products.getName());
			productBrand.setText("Product Brand: " + products.getBrand());
			productPrice.setText("Price: " + products.getPrice());

			totalPriceValue = products.getPrice() * quantity;
			String totalPriceText = "Total Price: " + totalPriceValue;
			totalPrice.setText(totalPriceText);
		});

		spQty.valueProperty().addListener((observable, oldValue, newValue) -> {
			Product selectedProduct = tbView.getSelectionModel().getSelectedItem();

			if (selectedProduct != null) {
				int newQuantity = newValue.intValue();
				totalPrice(selectedProduct, newValue.intValue());

				SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,
						selectedProduct.getStock(), newQuantity);
				spQty.setValueFactory(valueFactory);
			}
		});

		addBtn.setOnMouseClicked(event -> {
			Product selectedProduct = tbView.getSelectionModel().getSelectedItem();

			if (selectedProduct == null) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText("Please choose 1 item!");
				alert.show();
			} else {
				String ID = selectedProduct.getID();
				int quantity = spQty.getValue();
				addData(ID, ID, quantity);
				reduceProductStock(selectedProduct, quantity);

			}
		});

		this.logoutPageMenu.setOnAction(e -> {
			new LoginPage(stage);
		});

		this.CartPage.setOnAction(e -> {
			new Cartscene(stage);
		});

		this.historyPage.setOnAction(e -> {
			new Historyscene(stage);
		});
	}

	private void totalPrice(Product selectedProduct, int quantity) {
		int totalPriceValue = selectedProduct.getPrice() * quantity;
		String totalPriceText = "Total Price: " + totalPriceValue;
		totalPrice.setText(totalPriceText);
	}
	
	private void addData(String user, String ID, int quantity) {
		String query = String.format("INSERT INTO carttable VALUES('%s', '%s', '%d')", user, ID, quantity);
		try {
	        PreparedStatement statement = con.prepareStatement(query);
	        statement.setString(1, user);
	        statement.setString(2, ID);
	        statement.setInt(3, quantity);
	        statement.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	private void getData() {
		String query = "SELECT * FROM msproduct";

		DatabaseConnections con = DatabaseConnections.getInstance();
		con.execQuery(query);
		try {
			while (con.rs.next()) {
				String ID = con.rs.getString("ProductID");
				String name = con.rs.getString("ProductName");
				String brand = con.rs.getString("ProductMerk");
				int stock = con.rs.getInt("ProductStock");
				int price = con.rs.getInt("ProductPrice");

				products.add(new Product(ID, name, brand, stock, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tbView.setItems(products);
	}
	
	private void reduceProductStock(Product product, int quantity) {
		String query = "UPDATE msproduct SET ProductStock = ProductStock - ? WHERE ProductID = ?";

		try {
			PreparedStatement statement = DatabaseConnections.getInstance().getConnection().prepareStatement(query);
			statement.setInt(1, quantity);
			statement.setString(2, product.getID());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
