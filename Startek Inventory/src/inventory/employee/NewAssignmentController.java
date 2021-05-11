package inventory.employee;

import java.io.IOException;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.ResourceBundle;

import inventory.inventory.ProductController;
import inventory.login.LoginController;
import inventory.utilities.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewAssignmentController implements Initializable {

	Connection con = null;
    CallableStatement preparedStatement = null;
    PreparedStatement ps = null;
    CallableStatement preparedcall = null;
    ResultSet resultSet = null;
    java.sql.Date sqldate;
    private ObservableList<String> listcmbstatus = FXCollections.observableArrayList("In use","At home");
	@FXML
    private AnchorPane parent;
	@FXML
    private Button btnnewprod;
    @FXML
    private Button btnassign;
    @FXML
    private Button btncancel;
    @FXML
    private Label lbleeid;
    @FXML
    private Label lbldate;
    @FXML
    private TextField txtidproduct;
    @FXML
    private ComboBox<String> cmbstatus;

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
    	lbldate.setText(date.toString());


	}
	public void setParameters(String eeid){
		lbleeid.setText(eeid);
	}
	@FXML
    public void Button_Action(ActionEvent event) throws IOException{
		if (event.getSource() == btnnewprod) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/inventory/Product.fxml"));
    		Parent root = loader.load();
    		@SuppressWarnings("unused")
			ProductController controller = loader.getController();
    		Scene scene =new Scene(root);
    		Stage stage = new Stage();
    		stage.initStyle(StageStyle.UNDECORATED);
    		stage.setScene(scene);
    		stage.showAndWait();
		}
		if (event.getSource() == btnassign) {
			try{
				String stat = cmbstatus.getSelectionModel().getSelectedItem().toString();
				int eeid =Integer.parseInt(lbleeid.getText());
				String idprod = txtidproduct.getText();
				String spassign = "{CALL inventory.sp_insert_assignation(?,?,?,?,?)}";

					try {
						preparedStatement = con.prepareCall(spassign);
						preparedStatement.setInt(1, eeid);
						preparedStatement.setString(2, idprod);
						preparedStatement.setString(3, stat);
						preparedStatement.setDate(4, sqldate);
						preparedStatement.registerOutParameter(5, Types.VARCHAR);
						preparedStatement.execute();

						String message = preparedStatement.getString(5);

						Alert a = new Alert(AlertType.INFORMATION);
						a.setContentText(message);
						a.showAndWait();

						if(message.equals("Product assigned succesfully")){
							String type ="Assignment";
							String splog = "{CALL inventory.sp_auto_activity_log(?,?,?,?,?)}";

							try {

								preparedcall = con.prepareCall(splog);
								preparedcall.setString(1, idprod);
								preparedcall.setInt(2, eeid);
								preparedcall.setDate(3, sqldate);
								preparedcall.setString(4, type);
								preparedcall.setString(5, LoginController.USER_NAME);
								preparedcall.execute();

							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

					} catch (SQLException e) {
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
	public NewAssignmentController(){
    	con = DBConn.conDB();

    }

}