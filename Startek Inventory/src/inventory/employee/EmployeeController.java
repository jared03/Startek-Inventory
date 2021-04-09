package inventory.employee;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

import inventory.utilities.DBConn;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EmployeeController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private ObservableList<Employee> list= FXCollections.observableArrayList();
    private ObservableList<Assignation> listassign = FXCollections.observableArrayList();
    private FilteredList<Employee> filterItems;
    @FXML
    private TextField idfilter;
    @FXML
    private TableView<Employee> tvEID;
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
    private Button btnAssign;
    @FXML
    private Button btnReceive;
    @FXML
    private Button btnDecomm;
    @FXML
    private Button btnLog;
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
    private TableView<Assignation> tvassignations;
    @FXML
    private TableView<String> tvactivitylog;
    @FXML
    private TableColumn<Employee,String> tcolEID;
    @FXML
    private TableColumn<Employee,String> tcolFullName;
    @FXML
    private TableColumn<Assignation,String> tcolidproduct;
    @FXML
    private TableColumn<Assignation,Date> tcolassigndate;
    @FXML
    private TableColumn<Assignation,String> tcolbrand;
    @FXML
    private TableColumn<Assignation,String> tcolmodel;
    @FXML
    private TableColumn<Assignation,String> tcolprice;


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
   	//Retrieves all employees into the tableview
    	tcolEID.setCellValueFactory(cellData -> cellData.getValue().idemployeeProperty());
    	tcolFullName.setCellValueFactory(cellData -> cellData.getValue().fullnameProperty());
    	tcolidproduct.setCellValueFactory(cellData -> cellData.getValue().idproductProperty());
    	tcolassigndate.setCellValueFactory(cellData -> cellData.getValue().assigndateProperty());
    	tcolbrand.setCellValueFactory(cellData -> cellData.getValue().brandProperty());
    	tcolmodel.setCellValueFactory(cellData -> cellData.getValue().modelProperty());
    	tcolprice.setCellValueFactory(cellData -> cellData.getValue().priceProperty());

    	list = Employee_List();
    	filterItems = new FilteredList<>(list, e-> true);

    	//Filters Employee's ID
    	idfilter.textProperty().addListener((observable, oldValue, newValue) ->
        filterItems.setPredicate(employee -> {
            if (newValue == null || newValue.isEmpty()){
               return true;
            }
            else if (employee.getidemployee().toLowerCase().contains
                  (newValue.toLowerCase())){
               return true;
            }
            else if (employee.getfullname().toLowerCase().contains(newValue.toLowerCase())){
            	return true;
            }
            return false;
        }));
    	SortedList<Employee> sortedData = new SortedList<>(filterItems);

    	sortedData.comparatorProperty().bind(tvEID.comparatorProperty());

    	tvEID.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	tvassignations.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    	tvEID.setItems(sortedData);

    	/*tvEID.setRowFactory(lv -> new TableRow<Employee>() {
            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getidemployee());
            }
        });*/
    	//Loads data from each employee into the labels
    	tvEID.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Employee>() {
    	    @Override
    	    public void changed(ObservableValue<? extends Employee> observable, Employee oldValue, Employee newValue) {
    	    	if(newValue != null){
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

    	    			loadAssignations(newValue.getidemployee());
    	    			tvassignations.setItems(listassign);

    	    	}

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
				Employee e = new Employee(resultSet.getString("fname"),
						resultSet.getString("lname"),
						resultSet.getString("email"),
						resultSet.getString("idemployee"),
						resultSet.getString("status"),
						resultSet.getDate("hiredate"),
						resultSet.getString("supervisor"),
						resultSet.getString("jobtitle"),
						resultSet.getString("description"),
						resultSet.getString("country"),
						resultSet.getString("site"),
						resultSet.getString("clockid"),
						resultSet.getString("fullname"));
               list.add(e);
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;

    }
    public ObservableList<Assignation> loadAssignations(String idemployee){
    	int eeid =Integer.parseInt(idemployee);
    	listassign.clear();
    	String sql2 = "SELECT * FROM assignations WHERE idemployee = ?";
    	try {
			preparedStatement = con.prepareStatement(sql2);
			preparedStatement.setInt(1, eeid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Assignation a = new Assignation(resultSet.getString("idproduct"),
						resultSet.getDate("assigndate"),
						resultSet.getString("brand"),
						resultSet.getString("model"),
						resultSet.getString("price"));
				listassign.add(a);

			}
			}catch (SQLException e) {
				e.printStackTrace();
			}
    	return listassign;
    }

}

