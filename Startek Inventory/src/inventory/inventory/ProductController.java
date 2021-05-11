package inventory.inventory;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Types;
import java.util.ResourceBundle;

import inventory.employee.Assignation;
import inventory.employee.Employee;
import inventory.utilities.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ProductController implements Initializable {
	private ObservableList<String> listcmbstatus = FXCollections.observableArrayList("In use","In stock","Damaged","Decommisioned","Lost","In production","At home","In repair internally","In repair externally","Recycled");
	private ObservableList<String> listcmbcategory = FXCollections.observableArrayList();
	private ObservableList<String> listcmbsupplier = FXCollections.observableArrayList();
	private ObservableList<String> listcmbstorage = FXCollections.observableArrayList();
	private ObservableList<String> listcmbpurchase = FXCollections.observableArrayList();

	Connection con = null;
	PreparedStatement preparedStatement = null;
    CallableStatement preparedcall = null;
    ResultSet resultSet = null;
    @FXML
    private AnchorPane parent;
    @FXML
    private TextField txtidproduct;
    @FXML
    private RadioButton rbwarrantyes;
    @FXML
    private ToggleGroup warrantgp;
    @FXML
    private RadioButton rbwarrantyno;
    @FXML
    private ComboBox<String> cmbstatus;
    @FXML
    private ComboBox<String> cmbcategory;
    @FXML
    private ComboBox<String> cmbsupplier;
    @FXML
    private ComboBox<String> cmbstorage;
    @FXML
    private ComboBox<String> cmbpurchase;
    @FXML
    private RadioButton rbtaxyes;
    @FXML
    private ToggleGroup taxgp;
    @FXML
    private RadioButton rbtaxno;
    @FXML
    private TextField txtprice;
    @FXML
    private Button btnaddprod;
    @FXML
    private Button btncancel;


    private double xOffset = 0;
    private double yOffset = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		parent.setOnMousePressed((event) -> {
    		xOffset = event.getSceneX();
    		yOffset = event.getSceneY();
    	});
    	parent.setOnMouseDragged((event) -> {
    		Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setX(event.getScreenX()- xOffset);
            stage.setY(event.getScreenY()- yOffset);
    	});
    	rbwarrantyes.setUserData("yes");
    	rbwarrantyno.setUserData("no");
    	rbtaxno.setUserData("no");
    	rbtaxyes.setUserData("yes");
    	listcmbcategory=addCategory();
    	listcmbsupplier=addSupplier();
    	listcmbstorage=addStorageroom();
    	listcmbpurchase=addPurchase();

    	cmbstatus.setItems(listcmbstatus);
    	cmbcategory.setItems(listcmbcategory);
    	cmbsupplier.setItems(listcmbsupplier);
    	cmbstorage.setItems(listcmbstorage);
    	cmbpurchase.setItems(listcmbpurchase);

	}

	@FXML
	public void Button_Action(ActionEvent event){
		if (event.getSource() == btnaddprod) {
			try{
				String idprod = txtidproduct.getText();
				String warrant = warrantgp.getSelectedToggle().getUserData().toString();
				String stat = cmbstatus.getSelectionModel().getSelectedItem().toString();
				String cat = cmbcategory.getSelectionModel().getSelectedItem().toString();
				String suppl = cmbsupplier.getSelectionModel().getSelectedItem().toString();
				String stor = cmbstorage.getSelectionModel().getSelectedItem().toString();
				String purch = cmbpurchase.getSelectionModel().getSelectedItem().toString();
				String tx = taxgp.getSelectedToggle().getUserData().toString();
				int tax;
				if(tx=="yes"){
					tax=1;
				}
				else{
					tax=0;
				}
				double price = Double.parseDouble(txtprice.getText());

				String spinsertprod = "{CALL inventory.sp_insert_product(?,?,?,?,?,?,?,?,?,?)}";
				try {
					preparedcall = con.prepareCall(spinsertprod);
					preparedcall.setString(1, idprod);
					preparedcall.setString(2, warrant);
					preparedcall.setString(3, stat);
					preparedcall.setString(4, cat);
					preparedcall.setString(5, suppl);
					preparedcall.setString(6, stor);
					preparedcall.setString(7, purch);
					preparedcall.setInt(8, tax);
					preparedcall.setDouble(9, price);
					preparedcall.registerOutParameter(10, Types.VARCHAR);
					preparedcall.execute();

					String message = preparedcall.getString(10);

					Alert a = new Alert(AlertType.INFORMATION);
					a.setHeaderText(null);
					a.setContentText(message);
					a.showAndWait();

				}
				catch (SQLIntegrityConstraintViolationException e) {
				    // Duplicate entry
					Alert a = new Alert(AlertType.ERROR);
					a.setHeaderText(null);
					a.setContentText("Product is already added");
					a.showAndWait();
				}
				catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
			catch(NullPointerException e){

				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Input error");
				a.setHeaderText(null);
				a.setContentText("Please check values");
				a.showAndWait();
			}
		}
		if (event.getSource() == btncancel) {
			Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
		}
	 }
	public ObservableList<String> addCategory(){
		String sql = "SELECT model FROM category";

		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				listcmbcategory.add(resultSet.getString("model"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcmbcategory;

	}
	public ObservableList<String> addSupplier(){
		String sql = "SELECT supname FROM supplier";

		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				listcmbsupplier.add(resultSet.getString("supname"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcmbsupplier;
	}
	public ObservableList<String> addStorageroom(){
		String sql = "SELECT name FROM storage_room";

		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				listcmbstorage.add(resultSet.getString("name"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcmbstorage;
	}
	public ObservableList<String> addPurchase() {
		listcmbpurchase.add(" ");
		String sql = "SELECT purchaseorder FROM purchase";

		try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				listcmbpurchase.add(resultSet.getString("purchaseorder"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listcmbpurchase;
	}

	public ProductController(){
    	con = DBConn.conDB();

    }

}
