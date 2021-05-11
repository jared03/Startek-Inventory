package inventory.employee;

import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.ResourceBundle;

import inventory.login.LoginController;
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
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ReceiveController implements Initializable {

	Connection con = null;
	CallableStatement preparedcall = null;
	private ObservableList<String> listcmbstatus = FXCollections.observableArrayList("In stock","Damaged","Lost","In repair internally","In repair externally","Recycled");
    @FXML
    private ComboBox<String> cmbstatus;
    @FXML
    private Button btnreceive;
    @FXML
    private Button btncancel;
    @FXML
    private Label lblidproduct;
    @FXML
    private AnchorPane parent;

    private String empid;
    java.sql.Date sqldate;


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

    	cmbstatus.setItems(listcmbstatus);

    	Date date = new java.util.Date();
    	sqldate = new java.sql.Date(date.getTime());

	}

    @FXML
    void Button_Action(ActionEvent event) {
    	if (event.getSource() == btnreceive) {
    		try{
    			String stat = cmbstatus.getSelectionModel().getSelectedItem().toString();
    			String idprod = lblidproduct.getText();
    			String spreceiveprod = "{CALL inventory.sp_receive_product(?,?,?)}";
    			try {
    				preparedcall = con.prepareCall(spreceiveprod);
    				preparedcall.setString(1, idprod);
    				preparedcall.setString(2, stat);
    				preparedcall.registerOutParameter(3, Types.VARCHAR);
    				preparedcall.execute();

    				String message = preparedcall.getString(3);

					Alert a = new Alert(AlertType.INFORMATION);
					a.setHeaderText(null);
					a.setContentText(message);
					a.showAndWait();

					if(message.equals("Product returned succesfully")){
						String type ="Received";
						int eid = Integer.parseInt(empid);
						String splog = "{CALL inventory.sp_auto_activity_log(?,?,?,?,?)}";

						try {

							preparedcall = con.prepareCall(splog);
							preparedcall.setString(1, idprod);
							preparedcall.setInt(2, eid);
							preparedcall.setDate(3, sqldate);
							preparedcall.setString(4, type);
							preparedcall.setString(5, LoginController.USER_NAME);
							preparedcall.execute();

						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
    			}catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    		catch(NullPointerException e){
    			Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("Please select the status of the product");
				a.showAndWait();
    		}
    	}
    	if (event.getSource() == btncancel) {
    		Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
    	}
    }
    public void setParameters(String idprod, String eeid){
		lblidproduct.setText(idprod);
		empid = eeid;
	}
    public ReceiveController(){
    	con = DBConn.conDB();

    }
}