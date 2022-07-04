/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author gteuw
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField rateFld;
    @FXML
    private Label errLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void saveRate(ActionEvent event) {
        try {
      FileWriter myWriter = new FileWriter("rate.txt");
      myWriter.write(rateFld.getText());
      myWriter.close();
      errLbl.setText("Rate Saved");
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    }
    
}
