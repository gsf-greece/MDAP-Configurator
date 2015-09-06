/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizard.ui.about;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author George Sofianos <gsf.greece at gmail.com>
 */
public class AboutController implements Initializable {
    @FXML
    private Button closeDialogButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void closeDialogButton() {
        Window win = closeDialogButton.getScene().getWindow();
        Stage stage = (Stage) win;
        stage.close();
        //win.hide();        
    }
    
}
