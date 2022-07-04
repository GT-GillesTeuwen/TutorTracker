/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gteuw
 */
public class NewStudentPageController implements Initializable {

    @FXML
    private TextField nameFld;
    @FXML
    private TextField surnameFld;
    @FXML
    private DatePicker dobFld;
    @FXML
    private TextField phoneFld;
    @FXML
    private TextField emailFld;
    @FXML
    private Button addStudentBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button clearBtn;
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
    private void addStudent(ActionEvent event) {
        try {
            DBManager.addStudent(nameFld.getText(), surnameFld.getText(), fixDate(dobFld.getValue().toString()), emailFld.getText(), phoneFld.getText());
        errLbl.setText("Student Added");
        } catch (Exception e) {
            errLbl.setTextFill(Color.web("#ff0000", 0.8));
            errLbl.setText("Failed to Add Student");
        }
        
    }

    @FXML
    private void backToHome(ActionEvent event) throws IOException {
        Stage homeStage = (Stage) backBtn.getScene().getWindow();
        homeStage.close();

        FXMLLoader loadHomeHelp = new FXMLLoader(getClass().getResource("StudentPage.fxml"));
        Parent root = (Parent) loadHomeHelp.load();
        StudentPageController help = loadHomeHelp.getController();

        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("homescreen.css");
         // Image icon = new Image(getClass().getResourceAsStream("/icons/LogoForToolbar.png"));
       // newStage.getIcons().add(icon);

        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void clearFields(ActionEvent event) {
        nameFld.setText("");
        surnameFld.setText("");
        dobFld.setValue(null);
        emailFld.setText("");
        phoneFld.setText("");
        
    }
    
    private String fixDate(String date) {
        String newDate = date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8, 10);
        return newDate;
    }
}
