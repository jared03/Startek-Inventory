package inventory.employee;

import java.io.IOException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class NewLogController implements Initializable {

	Connection con = null;
    CallableStatement preparedcall = null;
    java.sql.Date sqldate;
    private ObservableList<String> listcmbtype = FXCollections.observableArrayList("Change","Repair","Other");
	@FXML
    private AnchorPane parent;
    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
    @FXML
    private Label lbleeid;
    @FXML
    private Label lbldate;
    @FXML
    private TextField txtcomment;
    @FXML
    private ComboBox<String> cmbtype;

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

    	cmbtype.setItems(listcmbtype);
    	Date date = new java.util.Date();
    	sqldate = new java.sql.Date(date.getTime());
    	lbldate.setText(date.toString());


	}
	public void setParameters(String eeid){
		lbleeid.setText(eeid);
	}
	@FXML
    public void Button_Action(ActionEvent event) throws IOException{
		if (event.getSource() == btnsave) {
			try{
				String type = cmbtype.getSelectionModel().getSelectedItem().toString();
				int eeid =Integer.parseInt(lbleeid.getText());
				String comment = txtcomment.getText();
				String splog = "{CALL inventory.sp_new_activity_log(?,?,?,?,?,?)}";

					try {
						preparedcall = con.prepareCall(splog);
						preparedcall.setInt(1, eeid);
						preparedcall.setDate(2, sqldate);
						preparedcall.setString(3, comment);
						preparedcall.setString(4, type);
						preparedcall.setString(5, LoginController.USER_NAME);
						preparedcall.registerOutParameter(6, Types.VARCHAR);
						preparedcall.execute();

						String message = preparedcall.getString(6);

						Alert a = new Alert(AlertType.INFORMATION);
						a.setHeaderText(null);
						a.setContentText(message);
						a.showAndWait();

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			catch(NullPointerException e){
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("Please select the log type");
				a.showAndWait();
			}
		}
		if (event.getSource() == btncancel) {
			Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
		}
	}
	public NewLogController(){
    	con = DBConn.conDB();

    }

}