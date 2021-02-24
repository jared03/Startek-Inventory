package inventory.headset;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AssignNewController implements Initializable {
	
	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private CallableStatement cs = null;
    
    @FXML
    private AnchorPane parent;
    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
    @FXML
    private ComboBox<String> cmbemployee;
    @FXML
    private ComboBox<String> cmbproductid;
    @FXML
    private DatePicker assigndate;
    
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
	
	public void getEmployee(){
		
	}
	public void getProduct(){
		
	}
	
}