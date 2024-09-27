package main;

import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ManageProductScene {
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	ScrollPane sp;

	Label productListLbl, productNameLbl, productBrandLbl, productPriceLbl, showNameLbl, addStockLbl, delProductLbl;

	TextField prodNameTf, prodPriceTf;

	Button addProductBtn, addStockBtn, delBtn;

	MenuBar menuBar;
	Menu adminMenu;
	MenuItem logoutadminMenu, manageProductPage, viewHistoryPage;

	ComboBox<String> brandBox;
	Spinner<Integer> stockSpinner;

	ObservableList<Product> prodList;
	TableView<Product> prodTbView;
	
	public ManageProductScene(Stage stage) {
		this.stage = stage;
		
		initialize();
		setStyle();
		initProdTbView();
		setOnAction();
		
		stage.setTitle("Cart");
		stage.setScene(scene);
		stage.show();
	}

	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 800, 600);

		productListLbl = new Label("Product List");
		productNameLbl = new Label("Product Name");
		productBrandLbl = new Label("Product Brand");
		productPriceLbl = new Label("Product Price");
		showNameLbl = new Label("Name :");
		addStockLbl = new Label("Add Stock");
		delProductLbl = new Label("Delete Product");

		prodNameTf = new TextField();
		prodPriceTf = new TextField();

		stockSpinner = new Spinner<>();
		brandBox = new ComboBox<>();

		addProductBtn = new Button("Add Button");
		addStockBtn = new Button("Add Stock");
		delBtn = new Button("Delete");

		menuBar = new MenuBar();
		adminMenu = new Menu("Admin");
		logoutadminMenu = new MenuItem("Logout");
		manageProductPage = new MenuItem("Manage Product");
		viewHistoryPage = new MenuItem("View History");

		prodList = FXCollections.observableArrayList();
		prodTbView = new TableView<>();
	}

	public void initProdTbView() {
		TableColumn<Product, String> colName = new TableColumn<>("Name");
		TableColumn<Product, String> colBrand = new TableColumn<>("Brand");
		TableColumn<Product, String> colStock = new TableColumn<>("Stock");
		TableColumn<Product, String> colPrice = new TableColumn<>("Price");

		colName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		colName.setCellValueFactory(new PropertyValueFactory<>("Brand"));
		colName.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		colName.setCellValueFactory(new PropertyValueFactory<>("Price"));

		colName.setPrefWidth(100);
		colBrand.setPrefWidth(100);
		colStock.setPrefWidth(50);
		colPrice.setPrefWidth(100);

		prodTbView.getColumns().addAll(colName, colBrand, colStock, colPrice);
		prodTbView.setItems(prodList);

//		getData()
//		setOnAction();
	}

	public void setComponent() {
		SpinnerValueFactory<Integer> stockSpinnerVal = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1);
		stockSpinner.setValueFactory(stockSpinnerVal);

		brandBox.getItems().add("Yonex");
		brandBox.getItems().add("Li-Ning");
		brandBox.getItems().add("Victor");
		brandBox.getSelectionModel().selectFirst();
	}

	private void setStyle() {
		bp.setCenter(gp);
		bp.setTop(menuBar);

		gp.setVgap(10);
		gp.setHgap(10);

		productListLbl.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
		gp.add(productListLbl, 0, 0);

		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER_LEFT);
		vbox.getChildren().addAll(productNameLbl, prodNameTf, productBrandLbl, brandBox, productPriceLbl, prodPriceTf,
				addProductBtn);

		gp.add(prodTbView, 0, 1);
		gp.add(vbox, 1, 1);
		gp.setPadding(new Insets(10));
		gp.setAlignment(Pos.CENTER);

		adminMenu.getItems().addAll(manageProductPage, viewHistoryPage, logoutadminMenu);
		menuBar.getMenus().add(adminMenu);

		gp.add(showNameLbl, 0, 2);
		showNameLbl.setStyle("-fx-font-weight: bold;");
		gp.add(addStockLbl, 0, 3);
		gp.add(delProductLbl, 1, 3);

		gp.add(stockSpinner, 0, 4);
		gp.add(addStockBtn, 0, 5);
		gp.add(delBtn, 1, 5);

		addStockBtn.setPrefWidth(200);
		delBtn.setPrefWidth(200);

		GridPane.setValignment(showNameLbl, VPos.TOP);
		GridPane.setHalignment(showNameLbl, HPos.CENTER);
		GridPane.setValignment(addStockLbl, VPos.TOP);
		GridPane.setValignment(delProductLbl, VPos.TOP);
		GridPane.setValignment(stockSpinner, VPos.TOP);
		GridPane.setValignment(addStockBtn, VPos.TOP);
		GridPane.setValignment(delBtn, VPos.TOP);
	}

	public void setOnAction() {
		this.viewHistoryPage.setOnAction(e -> {
			new ViewHistory().start(stage);
		});
		
		this.logoutadminMenu.setOnAction(e -> {
			new LoginPage(stage);
		});

	}

	
}
