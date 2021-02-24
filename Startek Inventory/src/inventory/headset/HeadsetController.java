package inventory.headset;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class HeadsetController implements Initializable {

	Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    private ObservableList<Headset> list= FXCollections.observableArrayList();
    private FilteredList<Headset> filterItems;
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
    private Button btnHeadset;
    @FXML
    private Button btnLaptop;
    @FXML
    private Button btnDesktop;
    @FXML
    private Button btnMonitor;
    @FXML
    private Button btnNetwork;
    @FXML
    private Button btnPeripheral;
    @FXML
    private Button btnProxy;
    @FXML
    private Button btnCable;
    @FXML
    private Button btnMaterial;
    @FXML
    private Button btnadd;
    @FXML
    private Button btnassign;
    @FXML
    private Button btnaddsupp;
    @FXML
    private ListView<Headset> lvSerial;
    @FXML
    private TextField serialfilter;
    @FXML
    private Label lblproduct;
    @FXML
    private Label lblstatus;
    @FXML
    private Label lblbrand;
    @FXML
    private Label lblmodel;
    @FXML
    private Label lblsupplier;
    @FXML
    private Label lblassigned;





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
    	list = Headset_List();
    	filterItems = new FilteredList<>(list, e-> true);
    	lvSerial.setItems(filterItems);

    	serialfilter.textProperty().addListener((observable, oldValue, newValue) ->
        filterItems.setPredicate(str -> {
            if (newValue == null || newValue.isEmpty())
               return true;
            if (str.getidproduct().toLowerCase().contains
                  (newValue.toLowerCase()))
               return true;
            return false;
        }));

    	lvSerial.getSelectionModel().setSelectionMode
        (SelectionMode.SINGLE);

    	lvSerial.setCellFactory(lv -> new ListCell<Headset>() {
            @Override
            protected void updateItem(Headset item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? "" : item.getidproduct());
            }
        });
    	lvSerial.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Headset>() {
    	    @Override
    	    public void changed(ObservableValue<? extends Headset> observable, Headset oldValue, Headset newValue) {

    	        lblproduct.setText(newValue.getidproduct());
    	        lblstatus.setText(newValue.getstatus());
	    		lblbrand.setText(newValue.getbrand());
	    		lblstatus.setText(newValue.getstatus());
	    		lblmodel.setText(newValue.getmodel());
	    		lblsupplier.setText(newValue.getsupplier());
	    		lblassigned.setText(newValue.getassigned());
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
    	if (event.getSource() == btnHeadset){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/headset/Headset.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnLaptop){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/laptop/Laptop.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnDesktop){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/Desktop/Desktop.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnMonitor){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/monitor/Monitor.fxml")));
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
    	if (event.getSource() == btnPeripheral){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/peripheral/Peripheral.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnProxy){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/proxy_card/ProxyCard.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnCable){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/cable_adapter/CableAdapter.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnMaterial){
    		try{
    			Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/material/Material.fxml")));
                stage.setScene(scene);
                stage.show();
    		}catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    }

    public HeadsetController(){
    	con = DBConn.conDB();
    }

    @FXML
    public void Side_Panel(MouseEvent event){
    	if (event.getSource() == btnadd){
    		try {
    		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/headset/Add_New.fxml"));
    	        Parent root1 = (Parent) fxmlLoader.load();
    	        Stage stage = new Stage();
    	        stage.initModality(Modality.APPLICATION_MODAL);
    	        stage.initStyle(StageStyle.UNDECORATED);
    	        stage.setScene(new Scene(root1));
    	        stage.show();
    			}
    		catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnassign){
    		try {
    		    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/headset/Assign_New.fxml"));
    	        Parent root1 = (Parent) fxmlLoader.load();
    	        Stage stage = new Stage();
    	        stage.initModality(Modality.APPLICATION_MODAL);
    	        stage.initStyle(StageStyle.UNDECORATED);
    	        stage.setScene(new Scene(root1));
    	        stage.show();
    			}
    		catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
    	}
    	if (event.getSource() == btnaddsupp){

    	}



    }

    public ObservableList<Headset> Headset_List(){
    	String sql = "SELECT * FROM headsets";

    	try {
			preparedStatement = con.prepareStatement(sql);
			resultSet = preparedStatement.executeQuery(sql);
			while (resultSet.next()) {
				Headset h = createHeadset(resultSet);
               list.add(h);
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}
    	return list;
    }

    private Headset createHeadset(ResultSet rs) {
		Headset h = new Headset();
        try {
           h.setassigned(rs.getString("assigned"));
           h.setidproduct(rs.getString("idproduct"));
           h.setpurchasedate(rs.getDate("purchasedate"));
           h.setstatus(rs.getString("status"));
           h.setmodel(rs.getString("model"));
           h.setbrand(rs.getString("brand"));
           h.setsupplier(rs.getString("supname"));

        } catch (SQLException ex) {
        }
        return h;
	}



}

