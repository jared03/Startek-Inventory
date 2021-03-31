package inventory.employee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import inventory.utilities.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EmployeeController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private ObservableList<Employee> list= FXCollections.observableArrayList();
    private FilteredList<Employee> filterItems;
    @FXML
    private TextField idfilter;
    @FXML
    private ListView<Employee> lsEID;
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
    private Label lblfname;
    @FXML
    private Label lbllname;
    @FXML
    private Label lblemail;
    @FXML
    private Label lblstatus;
    @FXML
    private Label lblhiredate;
    @FXML
    private Label lblsupervisor;
    @FXML
    private Label lbldepartment;
    @FXML
    private Label lbljobtitle;
    @FXML
    private Label lblcountry;
    @FXML
    private Label lblsite;
    @FXML
    private Label lblclockid;
    @FXML
    private ImageView imgemployee;
    @FXML
    private ListView<String> lvassignations;


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
    	//Retrieves all employees into the listview
    	list = Employee_List();
    	filterItems = new FilteredList<>(list, e-> true);
    	lsEID.setItems(filterItems);

    	//Filters Employee's ID
    	idfilter.textProperty().addListener((observable, oldValue, newValue) ->
        filterItems.setPredicate(str -> {
            if (newValue == null || newValue.isEmpty())
               return true;
            if (str.getidemployee().toLowerCase().contains
                  (newValue.toLowerCase()))
               return true;
            return false;
        }));

    	lsEID.getSelectionModel().setSelectionMode
        (SelectionMode.SINGLE);

    	lsEID.setCellFactory(lv -> new ListCell<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getidemployee());
            }
        });
    	//Loads data from each employee into the labels
    	lsEID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
    	    @Override
    	    public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {

    	        lblfname.setText(newValue.getfname());
    	        lbllname.setText(newValue.getlname());
	    		lblemail.setText(newValue.getemail());
	    		lblstatus.setText(newValue.getstatus());
	    		lblhiredate.setText(newValue.gethiredate().toString());
	    		lblsupervisor.setText(newValue.getsupervisor());
	    		lbldepartment.setText(newValue.getdescription());
	    		lbljobtitle.setText(newValue.getjobtitle());
	    		lblcountry.setText(newValue.getcountry());
	    		lblsite.setText(newValue.getsite());
	    		lblclockid.setText(newValue.getclockid());
    	    }
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

    public EmployeeController(){
    	con = DBConn.conDB();

    }
    public ObservableList<Employee> Employee_List(){
    	String sql = "SELECT * FROM employees";

    	try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				Employee e = createEmployee(resultSet);
               list.add(e);
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;

    }


    private Employee createEmployee(ResultSet rs) {
        Employee e = new Employee();
        try {
           e.setfname(rs.getString("fname"));
           e.setlname(rs.getString("lname"));
           e.setemail(rs.getString("email"));
           e.setidemployee(rs.getString("idemployee"));
           e.setstatus(rs.getString("status"));
           e.sethiredate(rs.getDate("hiredate"));
           e.setsupervisor(rs.getString("supervisor"));
           e.setjobtitle(rs.getString("jobtitle"));
           e.setdescription(rs.getString("description"));
           e.setcountry(rs.getString("country"));
           e.setsite(rs.getString("site"));
           e.setclockid(rs.getString("clockid"));
        } catch (SQLException ex) {
        }
        return e;
     }

//    @FXML
//    public void Import_Employees(MouseEvent event){
//       	FileChooser file = new FileChooser();
//    	Node node = (Node) event.getSource();
//    	Stage stage = (Stage) node.getScene().getWindow();
//    	if(event.getSource()==btnimportemployees){
//    		File selectedfile = file.showOpenDialog(stage);
//    		String path = selectedfile.getAbsolutePath();
//    		path = path.replace("\\", "\\\\");
//    		String sql = "LOAD DATA LOCAL INFILE '" + path + "' REPLACE INTO TABLE temp \n FIELDS TERMINATED BY ',' \n ENCLOSED BY '" + '"' + "' \n LINES TERMINATED BY '\\r\\n' \n IGNORE 1 LINES \n" +
//    		" (fname,lname,email,idemployee,statu,@hiredate,idsupervisor,jobtitle,description,country,site,clockid) \n"+
//    		"SET hiredate = STR_TO_DATE(@hiredate, '%m/%d/%Y');";
//        	String spimp = "CALL `inventory`.`sp_import_employees`();";
//        	try {
//				preparedStatement = con.prepareStatement(sql);
//				resultSet = preparedStatement.executeQuery(sql);
//				preparedStatement = con.prepareStatement(spimp);
//				resultSet = preparedStatement.executeQuery(spimp);
//
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//
//
//    	}
//
//
//    }


}

