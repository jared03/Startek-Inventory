/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConn {
    Connection conn = null;
    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://JALFARO-TGH:3306/inventory", "admin", "admin");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("Database Connection : "+ex.getMessage());
           return null;
        }
    }
    //make sure you add the lib
}
