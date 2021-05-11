package inventory.dashboard;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import inventory.employee.EmployeeController;
import inventory.utilities.DBConn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class DashboardController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    private ObservableList<Alert> listalert = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pcdatawfh = FXCollections.observableArrayList();
    private ObservableList<PieChart.Data> pcdatalost = FXCollections.observableArrayList();
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
    private TableView<Alert> tvalerts;
    @FXML
    private TableColumn<Alert, String> tcoleeid;
    @FXML
    private TableColumn<Alert, String> tcolfullname;
    @FXML
    private TableColumn<Alert, String> tcolempstat;
    @FXML
    private TableColumn<Alert, String> tcolsupervisor;
    @FXML
    private TableColumn<Alert, String> tcolprogram;
    @FXML
    private TableColumn<Alert, String> tcolalertstatus;
    @FXML
    private PieChart pcwfh;
    @FXML
    private PieChart pclostequipment;
    @FXML
    private BarChart<?, ?> bcinventory;

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

    	tcoleeid.setCellValueFactory(cellData -> cellData.getValue().idemployeeProperty());
    	tcolfullname.setCellValueFactory(cellData -> cellData.getValue().fullnameProperty());
    	tcolempstat.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
    	tcolsupervisor.setCellValueFactory(cellData -> cellData.getValue().supervisorProperty());
    	tcolprogram.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
    	tcolalertstatus.setCellValueFactory(cellData -> cellData.getValue().alert_statusProperty());

    	listalert = loadAlerts();
    	tvalerts.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    	tvalerts.setItems(listalert);

    	tvalerts.setRowFactory(tv -> {
            TableRow<Alert> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    Alert rowData = row.getItem();
                    try{
            			Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/inventory/employee/Employee.fxml"));
                        Parent root = loader.load();
                        EmployeeController controller = loader.getController();
                        controller.loadalert(rowData.getidemployee());
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
            		}catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            });
            return row ;
        });

    	pcdatawfh.addAll(new PieChart.Data("WFH", 30),new PieChart.Data("Production", 100));
    	pcdatalost.addAll(new PieChart.Data("Lost", 40),new PieChart.Data("Returned", 1000));
    	pcwfh.setData(pcdatawfh);
    	pclostequipment.setData(pcdatalost);


	}

    private ObservableList<Alert> loadAlerts() {
    	listalert.clear();
    	String sql = "SELECT * FROM alerts";
    	try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				Alert a = new Alert(
						resultSet.getString("idemployee"),
						resultSet.getString("fullname"),
						resultSet.getString("status"),
						resultSet.getString("supervisor"),
						resultSet.getString("description"),
						resultSet.getString("alert_status"));
				listalert.add(a);
			}
		}
    	catch(SQLException e){

    	}
		return listalert;
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

    public DashboardController(){
    	con = DBConn.conDB();

    }


}

