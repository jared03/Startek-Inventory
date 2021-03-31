package inventory.login;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import inventory.utilities.DBConn;


public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private Label lblSvrError;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnClose;

    /// --
    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success")) {
                try {

                    //add you loading or delays - ;-)
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/inventory/dashboard/Dashboard.fxml")));
                    stage.setScene(scene);
                    stage.show();


                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
        if (event.getSource() == btnClose){
        	Node node = (Node) event.getSource();
        	Stage stage = (Stage) node.getScene().getWindow();
        	stage.close();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblSvrError.setTextFill(Color.TOMATO);
            lblSvrError.setText("Server Offline!");
        } else {
            lblSvrError.setTextFill(Color.GREEN);
            lblSvrError.setText("Server Online");
        }
    }

    public LoginController() {

    	con = DBConn.conDB();
    }

    //we gonna use string to check for status
    private String logIn() {

        String user = txtUsername.getText();
        String password = txtPassword.getText();

        //query
        String sql = "SELECT * FROM login Where username = ? and password = ?";

        try {
        	if (!user.isEmpty()) {
        		if (!password.isEmpty()){
        			preparedStatement = con.prepareStatement(sql);
        			preparedStatement.setString(1, user);
        			preparedStatement.setString(2, password);
        			resultSet = preparedStatement.executeQuery();
        			if (!resultSet.next()) {
        				lblErrors.setTextFill(Color.TOMATO);
        				lblErrors.setText("Enter Correct username or password");
        				System.err.println("Wrong Logins --///");
        				return "Error";

        			} else {
        				lblErrors.setTextFill(Color.GREEN);
        				lblErrors.setText("Login Successful..Redirecting..");
        				System.out.println("Successfull Login");
        				return "Success";
        			}
        	    }
        		else{
        			lblErrors.setTextFill(Color.TOMATO);
                    lblErrors.setText("Enter your password");
                    return "Error";
        		}
          }
        else {
        	lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Enter a username");
            return "Error";
        }

        }

        catch (SQLException ex) {
            System.err.println(ex.getMessage());
            return "Exception";
        }

    }

}
