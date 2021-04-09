package inventory.parameters;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import inventory.utilities.DBConn;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ParameterController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    @FXML
    private AnchorPane parent;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnMaximize;
    @FXML
    private Button btnMinimize;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnEmployee;
    @FXML
    private Button btnReports;
    @FXML
    private Button btnParameters;
    @FXML
    private Button btnInventory;
    @FXML
    private Button btnNetwork;
    @FXML
    private Button btnimportemployees;

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
    public void Window_Action(MouseEvent event) {

        if (event.getSource() == btnClose) {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                }
        if (event.getSource() == btnMaximize){
        	Node node = (Node) event.getSource();
        	Stage stage = (Stage) node.getScene().getWindow();
            if (stage.isMaximized()== false){
        		stage.setMaximized(true);
        	}
        	else{
        		stage.setMaximized(false);
        	}
        }
        if (event.getSource() == btnMinimize){
        	Node node = (Node) event.getSource();
        	Stage stage = (Stage) node.getScene().getWindow();
        	stage.setIconified(true);
        }
    }

    @FXML
    public void Panel_Action(MouseEvent event){
    	if (event.getSource() == btnDashboard){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/dashboard/Dashboard.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnEmployee){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/employee/Employee.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnReports){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/reports/Report.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnParameters){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/parameters/Parameter.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnInventory){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/inventory/Inventory.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnNetwork){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/network_device/NetworkDevice.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    }

    @FXML
    public void Import_Employees(MouseEvent event){
     	FileChooser file = new FileChooser();
  	Node node = (Node) event.getSource();
  	Stage stage = (Stage) node.getScene().getWindow();
  	if(event.getSource()==btnimportemployees){
  		File selectedfile = file.showOpenDialog(stage);
  		String path = selectedfile.getAbsolutePath();
  		path = path.replace("\\", "\\\\");
  		String sql = "LOAD DATA LOCAL INFILE '" + path + "' REPLACE INTO TABLE temp \n FIELDS TERMINATED BY ',' \n ENCLOSED BY '" + '"' + "' \n LINES TERMINATED BY '\\r\\n' \n IGNORE 1 LINES \n" +
  		" (fname,lname,email,idemployee,statu,@hiredate,idsupervisor,jobtitle,description,country,site,clockid) \n"+
  		"SET hiredate = STR_TO_DATE(@hiredate, '%m/%d/%Y');";
      	String spimp = "CALL `inventory`.`sp_import_employees`();";
      	try {
				preparedStatement = con.prepareStatement(sql);
				resultSet = preparedStatement.executeQuery(sql);
				preparedStatement = con.prepareStatement(spimp);
				resultSet = preparedStatement.executeQuery(spimp);

			} catch (SQLException e) {
				e.printStackTrace();
			}



  	}


  }

    public ParameterController(){
    	con = DBConn.conDB();

    }


}

