package inventory.headset;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import java.sql.CallableStatement;

import inventory.utilities.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddNewController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<String> status = FXCollections.observableArrayList("Active","Defective","Lost");
    private ObservableList<String> model = FXCollections.observableArrayList();
    private ObservableList<String> supplier = FXCollections.observableArrayList();

    private CallableStatement cs = null;
    @FXML
    private AnchorPane parent;
    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
    @FXML
    private TextField txtidproduct;
    @FXML
    private DatePicker purchasedate;
	@FXML
    private ComboBox<String> cmbstatus;
    @FXML
    private ComboBox<String> cmbmodel;
    @FXML
    private ComboBox<String> cmbsupplier;

    private double xOffset = 0;
    private double yOffset = 0;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
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


		getModel();
		getSupplier();
		cmbstatus.setItems(status);
		cmbmodel.setItems(model);
		cmbsupplier.setItems(supplier);

	}
	public AddNewController(){
    	con = DBConn.conDB();


    }
	@FXML
	public void Button_Actions(MouseEvent event){
		if (event.getSource() == btnsave){

			String idproduct = txtidproduct.getText();
			LocalDate purdate = purchasedate.getValue();
			Date pdate = Date.valueOf(purdate);
			String status = cmbstatus.getValue();
			String model = cmbmodel.getValue();
			String supplier = cmbsupplier.getValue();

			String spadd = "{CALL `sp_add_headset`(?,?,?,?,?)}";

			try{
				cs = con.prepareCall(spadd);
				cs.setString(1, idproduct);
				cs.setDate(2, pdate);
				cs.setString(3, status);
				cs.setString(4, model);
				cs.setString(5, supplier);
				resultSet = cs.executeQuery();


			}catch (SQLException e) {
				e.printStackTrace();
			}


		}
		else if (event.getSource() == btncancel){
			Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
		}
	}
	public void getModel(){

    	String sql = "SELECT model from category where description = 'Headset'";

    	try {
    		preparedStatement = con.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery(sql);

    		while (resultSet.next()){
    			String models = resultSet.getString("model");
    			 model.add(models);
    		}
         } catch (SQLException ex) {
         }
    }
    public void getSupplier(){

    	String sql = "SELECT supname from supplier";

    	try {
    		preparedStatement = con.prepareStatement(sql);
    		resultSet = preparedStatement.executeQuery(sql);

    		while (resultSet.next()){
    			String suppliers = resultSet.getString("supname");
    			 supplier.add(suppliers);
    		}
         } catch (SQLException ex) {
         }

    }

}