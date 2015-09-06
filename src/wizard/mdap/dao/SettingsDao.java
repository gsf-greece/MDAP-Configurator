/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.mdap.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;

/**
 *
 * @author George Sofianos <gsf.greece at gmail.com>
 */
public class SettingsDao implements BaseDao {
    private Connection conn;
    
    public String getProperty(String prop) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:h2:./settings", "sa", "");
        conn.setAutoCommit(true);
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from settings");
        while (rs.next()) {
            LogManager.getLogger().info("Setting name: " + rs.getString("key"));
            LogManager.getLogger().info("Setting name: " + rs.getString("value"));            
        }
        return "true";
    }
    public String saveProperty(String prop) {
     return "true";   
    }
}
