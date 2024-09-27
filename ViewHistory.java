package main;

import java.sql.Date;

import Model.Product;
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

public class ViewHistory {

	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane gp;
	ScrollPane allTransSp;
	ScrollPane transDetSp;

	Label allTransLbl, transDetailLbl, totalPriceLbl;

	ObservableList<TransactionHeader> transactionList;
	TableView<TransactionHeader> allTransView;
	TableView<TransactionHeader> transDetailView;

	MenuBar menuBar;
	Menu adminMenu;
	MenuItem logoutadminMenu, manageProductPage, viewHistoryPage;

	public void initialize() {
		bp = new BorderPane();
		gp = new GridPane();
		scene = new Scene(bp, 750, 400);

		menuBar = new MenuBar();
		adminMenu = new Menu("Admin");
		logoutadminMenu = new MenuItem("Logout");
		manageProductPage = new MenuItem("Manage Product");
		viewHistoryPage = new MenuItem("View History");

		allTransLbl = new Label("All Transactions");
		transDetailLbl = new Label("Transaction Detail");
		totalPriceLbl = new Label("Total Price :");

		transactionList = FXCollections.observableArrayList();
		allTransView = new TableView<>();
		transDetailView = new TableView<>();

		allTransSp = new ScrollPane(allTransView);
		transDetSp = new ScrollPane(transDetailView);
	}

	public void initAllTransaction() {
		TableColumn<TransactionHeader, String> colID = new TableColumn<>("ID");
		TableColumn<TransactionHeader, String> colEmail = new TableColumn<>("Email");
		TableColumn<TransactionHeader, Date> colDate = new TableColumn<>("Date");

		colID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		colDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

		colID.setPrefWidth(50);
		colEmail.setPrefWidth(50);
		colDate.setPrefWidth(50);

		allTransView.getColumns().addAll(colID, colEmail, colDate);
		allTransView.setItems(transactionList);

		allTransView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//		getData();
	}

	public void initTransactionDetail() {
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

		transDetailView.getColumns().addAll(colID, colProduct, colPrice, colQty, colTotalPrice);
		transDetailView.setItems(transactionList);

		transDetailView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

//		getData();
	}

	public void setOnAction() {
		this.manageProductPage.setOnAction(e -> {
			new ManageProductScene(stage);
		});

		this.logoutadminMenu.setOnAction(e -> {
			new LoginPage(stage);
		});
	}

	public void setOnStyle() {
		bp.setCenter(gp);
		bp.setTop(menuBar);
		gp.setPadding(new Insets(10));
		gp.setVgap(10);
		gp.setHgap(10);

		allTransSp.setFitToWidth(true);
		allTransSp.setFitToHeight(true);

		transDetSp.setFitToWidth(true);
		transDetSp.setFitToHeight(true);

		adminMenu.getItems().addAll(manageProductPage, viewHistoryPage, logoutadminMenu);
		menuBar.getMenus().add(adminMenu);

		gp.add(allTransLbl, 0, 0);
		gp.add(allTransSp, 0, 1);
		gp.add(transDetailLbl, 1, 0);
		gp.add(transDetSp, 1, 1);
		gp.add(totalPriceLbl, 1, 2);
	}

	public void start(Stage stage) {
		initialize();
		initAllTransaction();
		initTransactionDetail();
		setOnAction();
		setOnStyle();

		this.stage = stage;
		stage.setTitle("Cart");
		stage.setScene(scene);
		stage.show();
	}
}
