package inventory.employee;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import inventory.utilities.DBConn;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import inventory.employee.NewAssignmentController;
import inventory.login.LoginController;


public class EmployeeController {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    CallableStatement preparedcall = null;
    ResultSet resultSet = null;
    private ObservableList<Employee> list= FXCollections.observableArrayList();
    private ObservableList<Assignation> listassign = FXCollections.observableArrayList();
    private ObservableList<ActivityLog> listactivity = FXCollections.observableArrayList();
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
    private Label lbleeid;
    @FXML
    private Label lblempstat;
    @FXML
    private ImageView imgemployee;
    @FXML
    private TableView<Assignation> tvassignations;
    @FXML
    private TableView<ActivityLog> tvactivitylog;
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
    @FXML
    private TableColumn<ActivityLog, Date> tcolactdate;
    @FXML
    private TableColumn<ActivityLog, String> tcolactcomment;
    @FXML
    private TableColumn<ActivityLog, String> tcolacttype;
    @FXML
    private TableColumn<ActivityLog, String> tcolactperformed;

    private double xOffset = 0;
    private double yOffset = 0;

    //@Override
	public void initialize() {

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

    	tcolactdate.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
    	tcolactcomment.setCellValueFactory(cellData -> cellData.getValue().commentProperty());
    	tcolacttype.setCellValueFactory(cellData -> cellData.getValue().typeProperty());
    	tcolactperformed.setCellValueFactory(cellData -> cellData.getValue().performedProperty());

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
    	    			lbleeid.setText(newValue.getidemployee());

    	    			if(newValue.getstatus().contentEquals("AF")  || newValue.getstatus().contentEquals("AP") || newValue.getstatus().contentEquals("AT")){
    	    				lblempstat.setTextFill(Color.GREEN);
    	    				lblempstat.setText("Employee is active");
    	    			}
    	    			else if(newValue.getstatus().contentEquals("LP")  || newValue.getstatus().contentEquals("LU")){
    	    				lblempstat.setTextFill(Color.YELLOW);
    	    				lblempstat.setText("Employee it's on LOA");
    	    			}
    	    			else if(newValue.getstatus().contentEquals("TP")  || newValue.getstatus().contentEquals("TF")){
    	    				lblempstat.setTextFill(Color.RED);
    	    				lblempstat.setText("Employee it's termed");
    	    			}
    	    			loadAssignations(newValue.getidemployee());
    	    			loadActivity(newValue.getidemployee());
    	    			tvassignations.setItems(listassign);
    	    			tvactivitylog.setItems(listactivity);

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
    @FXML
    public void Button_Action(ActionEvent event) throws IOException{
    	if (event.getSource() == btnAssign) {
    		if((LoginController.PRIVILEGE.contentEquals("Administrator") || LoginController.PRIVILEGE.contentEquals("Technician"))){
		    		if(lbleeid.getText()==null || lbleeid.getText()=="" || lbleeid.getText()==" "){
		    			Alert a = new Alert(AlertType.INFORMATION);
						a.setHeaderText(null);
						a.setContentText("Please select an employee to assign a product");
						a.showAndWait();
		    		}
		    		else if (lblstatus.getText().contentEquals("TF") || lblstatus.getText().contentEquals("TP")){
		    			Alert a = new Alert(AlertType.INFORMATION);
						a.setHeaderText(null);
						a.setContentText("Employee is termed, can't assign a product");
						a.showAndWait();
		    		}
		    		else{
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/employee/NewAssignment.fxml"));
			    		Parent root = loader.load();
			    		NewAssignmentController controller = loader.getController();
			    		controller.setParameters(lbleeid.getText());
			    		Scene scene =new Scene(root);
			    		Stage stage = new Stage();
			    		stage.initStyle(StageStyle.UNDECORATED);
			    		stage.setScene(scene);
			    		stage.showAndWait();
		    		}
    		}
    		else{
    			Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText(null);
				a.setContentText("You don't have enough permissions to complete this task");
				a.showAndWait();
    		}
    	}
    	if (event.getSource() == btnReceive) {

    		String idprod = "";
    		String eeid = lbleeid.getText();
    		if(!listassign.isEmpty()){
    			try{
					idprod = tvassignations.getSelectionModel().getSelectedItem().getidproduct();
	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/employee/Receive.fxml"));
	        		Parent root = loader.load();
	        		ReceiveController controller = loader.getController();
	    			controller.setParameters(idprod, eeid);
	        		Scene scene =new Scene(root);
	        		Stage stage = new Stage();
	        		stage.initStyle(StageStyle.UNDECORATED);
	        		stage.setScene(scene);
	        		stage.showAndWait();
    			}
    			catch(NullPointerException e){
    				Alert a = new Alert(AlertType.INFORMATION);
    				a.setHeaderText(null);
    				a.setContentText("Please select a product being returned");
    				a.showAndWait();
    			}
    		}
    		else{
    			Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("Please select a product being returned");
				a.showAndWait();
    		}

    	}
    	if (event.getSource() == btnDecomm) {
    		try{
    			java.sql.Date sqldate;
    			Date date = new java.util.Date();
    			sqldate = new java.sql.Date(date.getTime());
    			String idprod = tvassignations.getSelectionModel().getSelectedItem().getidproduct();
    			int eeid = Integer.parseInt(lbleeid.getText());

    			String spdecomm = "{CALL sp_decommision_product(?,?,?,?)}";
    			try{
    				preparedcall = con.prepareCall(spdecomm);
    				preparedcall.setInt(1, eeid);
    				preparedcall.setString(2, idprod);
    				preparedcall.setDate(3, sqldate);
    				preparedcall.registerOutParameter(4, Types.VARCHAR);
    				preparedcall.execute();

    				String message = preparedcall.getString(4);
    				Alert a = new Alert(AlertType.INFORMATION);
					a.setHeaderText(null);
					a.setContentText(message);
					a.showAndWait();

					if(message.equals("Product decommisioned succesfully")){
						String type ="Decommision";
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

    			}
    			catch(SQLException e){
    				e.printStackTrace();
    			}


    		}
    		catch(NullPointerException e){
    			Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("Please select a product being returned");
				a.showAndWait();
    		}
    	}
    	if (event.getSource() == btnLog) {
    		if(!lbleeid.getText().isEmpty()){
	    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/employee/NewLog.fxml"));
	    		Parent root = loader.load();
	    		NewLogController controller = loader.getController();
	    		controller.setParameters(lbleeid.getText());
	    		Scene scene =new Scene(root);
	    		Stage stage = new Stage();
	    		stage.initStyle(StageStyle.UNDECORATED);
	    		stage.setScene(scene);
	    		stage.showAndWait();
    		}
    		else{
    			Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText(null);
				a.setContentText("Please select an employee to create a log");
				a.showAndWait();
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

    public ObservableList<ActivityLog> loadActivity(String idemployee){
    	int eeid =Integer.parseInt(idemployee);
    	listactivity.clear();
    	String sql2 = "SELECT * FROM activity_logs WHERE idemployee = ?";
    	try {
			preparedStatement = con.prepareStatement(sql2);
			preparedStatement.setInt(1, eeid);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ActivityLog a = new ActivityLog(
						resultSet.getDate("date"),
						resultSet.getString("comment"),
						resultSet.getString("type"),
						resultSet.getString("performed"));
				listactivity.add(a);

			}
			}catch (SQLException e) {
				e.printStackTrace();
			}
    	return listactivity;
    }
    public void loadalert(String eeid){
    	for(Employee emp : list){
    		if(emp.getidemployee().equals(eeid)){
    			lblfname.setText(emp.getfname());
    			lbllname.setText(emp.getlname());
    			lblemail.setText(emp.getemail());
    			lblstatus.setText(emp.getstatus());
    			lblhiredate.setText(emp.gethiredate().toString());
    			lblsupervisor.setText(emp.getsupervisor());
    			lbldepartment.setText(emp.getdescription());
    			lbljobtitle.setText(emp.getjobtitle());
    			lblcountry.setText(emp.getcountry());
    			lblsite.setText(emp.getsite());
    			lblclockid.setText(emp.getclockid());
    			lbleeid.setText(emp.getidemployee());

    			if(emp.getstatus().contentEquals("AF")  || emp.getstatus().contentEquals("AP") || emp.getstatus().contentEquals("AT")){
    				lblempstat.setTextFill(Color.GREEN);
    				lblempstat.setText("Employee is active");
    			}
    			else if(emp.getstatus().contentEquals("LP")  || emp.getstatus().contentEquals("LU")){
    				lblempstat.setTextFill(Color.YELLOW);
    				lblempstat.setText("Employee it's on LOA");
    			}
    			else if(emp.getstatus().contentEquals("TP")  || emp.getstatus().contentEquals("TF")){
    				lblempstat.setTextFill(Color.RED);
    				lblempstat.setText("Employee it's termed");
    			}
    			loadAssignations(emp.getidemployee());
    			loadActivity(emp.getidemployee());
    			tvassignations.setItems(listassign);
    			tvactivitylog.setItems(listactivity);

    		}
    	}

    }

}

