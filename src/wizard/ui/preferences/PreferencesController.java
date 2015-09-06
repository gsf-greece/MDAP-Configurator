/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.ui.preferences;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import wizard.mdap.client.Client;
import wizard.mdap.server.Server;

/**
 * FXML Controller class
 *
 * @author George Sofianos <gsf.greece at gmail.com>
 */
public class PreferencesController implements Initializable {
    @FXML
    private Button connectButton;
    @FXML
    private Label label;
    @FXML
    private TextField usernameInput;
    @FXML
    private PasswordField passwordInput;
    @FXML
    private CheckBox defaultCheck;
    
    private Client client;
    private Server server;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateFields();
    }    
    
    private void updateFields() {
        if (defaultCheck.isSelected()) {
            usernameInput.setDisable(true);
            passwordInput.setDisable(true);
        } else {
            usernameInput.setDisable(false);
            passwordInput.setDisable(false);
        }        
    }
    
    @FXML
    private void defaultCheckBoxClick(ActionEvent event) {
        updateFields();
    }
    
    @FXML
    private void saveButtonClick(ActionEvent event) throws IOException {              
        
    }    
    
    @FXML
    private void handleDisconnectButtonAction(ActionEvent event) {
        server.disconnect();
    }
    
}
