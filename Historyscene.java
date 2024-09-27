package main;

import java.sql.Date;

import Model.TransactionHeader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Historyscene {
	Stage stage;
	Scene scene;

	BorderPane bp;
	GridPane gp;
	ScrollPane thSp;
	ScrollPane tdSp;

	MenuBar menuBar;
	Menu pageMenu;
	MenuItem homePage, CartPage, historyPage, logoutPageMenu;

	Label myTransLbl, transDetailLbl, totalPriceLbl;
	ObservableList<TransactionHeader> thList;
	TableView<TransactionHeader> thView;
	TableView<TransactionHeader> tdView;

	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 900, 400);

		menuBar = new MenuBar();
		pageMenu = new Menu("Page");

		homePage = new MenuItem("Home");
		CartPage = new MenuItem("Cart");
		historyPage = new MenuItem("History");
		logoutPageMenu = new MenuItem("Logout");

		myTransLbl = new Label("My Transaction");
		transDetailLbl = new Label("Transaction Detail");
		totalPriceLbl = new Label("Total Price :");

		thList = FXCollections.observableArrayList();
		thView = new TableView<>();
		tdView = new TableView<>();
		
		thSp = new ScrollPane(thView);
		tdSp = new ScrollPane(tdView);
	}

	public void initTbHeader() {
		TableColumn<TransactionHeader, String> colID = new TableColumn<>("ID");
		TableColumn<TransactionHeader, String> colEmail = new TableColumn<>("Email");
		TableColumn<TransactionHeader, Date> colDate = new TableColumn<>("Date");

		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

		colID.setPrefWidth(50);
		colEmail.setPrefWidth(50);
		colDate.setPrefWidth(50);

		thView.getColumns().addAll(colID, colEmail, colDate);
		thView.setItems(thList);

		thView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//		getData();
	}

	public void initTdView() {
		TableColumn<TransactionHeader, String> colID = new TableColumn<>("ID");
		TableColumn<TransactionHeader, String> colProduct = new TableColumn<>("Product");
		TableColumn<TransactionHeader, String> colPrice = new TableColumn<>("Price");
		TableColumn<TransactionHeader, String> colQty = new TableColumn<>("Quantity");
		TableColumn<TransactionHeader, String> colTotalPrice = new TableColumn<>("Total Price");

		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colProduct.setCellValueFactory(new PropertyValueFactory<>("Product"));
		colPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		colQty.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		colTotalPrice.setCellValueFactory(new PropertyValueFactory<>("Total Price"));

		colID.setPrefWidth(100);
		colProduct.setPrefWidth(100);
		colPrice.setPrefWidth(100);
		colQty.setPrefWidth(100);
		colTotalPrice.setPrefWidth(100);

		tdView.getColumns().addAll(colID, colProduct, colPrice, colQty, colTotalPrice);
		tdView.setItems(thList);

		tdView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//		getData();
	}

	public void setOnAction() {
		this.logoutPageMenu.setOnAction(e -> {
			new LoginPage(stage);
		});
		
		this.homePage.setOnAction(e -> {
			new Homescene(stage);
		});
		
		this.CartPage.setOnAction(e -> {
			new Cartscene(stage);
		});
	}

	public void setComponent() {
		bp.setCenter(gp);
		bp.setTop(menuBar);
		gp.setPadding(new Insets(10));
		gp.setVgap(10);
		gp.setHgap(10);

		thSp.setFitToWidth(true);
		thSp.setFitToHeight(true);
		
		tdSp.setFitToWidth(true);
		tdSp.setFitToHeight(true);
		
		pageMenu.getItems().addAll(homePage, CartPage, historyPage, logoutPageMenu);
		menuBar.getMenus().add(pageMenu);
		
		gp.add(myTransLbl, 0, 0);
		gp.add(thSp, 0, 1);
		gp.add(transDetailLbl, 1, 0);
		gp.add(tdSp, 1, 1);
		gp.add(totalPriceLbl, 1, 2);
	}

	public Historyscene (Stage stage) {
		initialize();
		initTbHeader();
		initTdView();
		setOnAction();
		setComponent();
		this.stage = stage;
		stage.setTitle("Cart");
		stage.setScene(scene);
		stage.show();
	}
}
