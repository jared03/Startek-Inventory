/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.login;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    //define your offsets here
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) throws Exception {
    	/*Main main =new Main();
    	Thread thread = new Thread(main);
    	thread.start();*/
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        //removes default windows utilites.
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaximized(false);
        stage.getIcons().add(new Image("file:@../images/icon.png"));
        //grab your root here
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        //move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

	/*@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Loading employees...");
		EmployeeController ec = new EmployeeController();
		ec.initialize();
		System.out.println("Employees load to list");

	}*/

}
