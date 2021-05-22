/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import javax.jms.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Calayo13
 */
public class Conexion {
    Connection con;
    String url="jdbc:mysql://localhost:3306/bd_ventas";
    String user="root";
    String pass="Maximo134.";
    
    public Connection Conexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = (Connection) DriverManager.getConnection(url,user,pass);
        } catch (Exception e) {
            
        }      
        return con;
        
    }
}
