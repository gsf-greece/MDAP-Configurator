/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wizard.ui;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import wizard.mdap.client.Client;
import wizard.mdap.server.Server;

/**
 * FXML Controller class
 *
 * @author Luciddream
 */
public class MainController implements Initializable {
        
    private Client client;
    private Server server;
    @FXML
    private Button broadcastButton;
           
    
    static {
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.
                getConnection("jdbc:h2:./settings", "sa", "");
        } catch (ClassNotFoundException ex) {
            LogManager.getLogger().error(ex);
        } catch (SQLException ex) {
            LogManager.getLogger().error(ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO        
    }   
    
    @FXML
    private void aboutMenuDialog() throws IOException {
        openNewDialog("about/About.fxml");
    }
    
    private void openNewDialog(String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void preferencesMenuItem(ActionEvent event) throws IOException {
        openNewDialog("preferences/Preferences.fxml");
    }        
    
    @FXML
    private void broadcastButtonClick(ActionEvent event) throws IOException {
        broadcastButton.setDisable(true);
        server = new Server();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            LogManager.getLogger().error(ex);
        }
        client = new Client();
        client.connect("username", "password");        
        //if (passwordInput != null && usernameInput != null) {
        //    client.connect(usernameInput.getText(), passwordInput.getText());
        //}       
    }
}
