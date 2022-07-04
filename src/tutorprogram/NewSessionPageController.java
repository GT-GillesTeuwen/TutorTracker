/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author gteuw
 */
public class NewSessionPageController implements Initializable {

    @FXML
    private Button addSessionBtn;
    @FXML
    private Button backBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Label errLbl;
    @FXML
    private CheckBox hwAssigned;
    @FXML
    private TextArea hwContentArea;
    @FXML
    private Spinner<Double> durationSpn;
    @FXML
    private DatePicker dateFld;
    @FXML
    private Spinner<Integer> gradeSpn;
    @FXML
    private TextArea sessionContentArea;
    @FXML
    private TextField studentFld;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        SpinnerValueFactory<Double> duration = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 12, 1,0.5);
        SpinnerValueFactory<Integer> grade = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 10,1);
        
        gradeSpn.setValueFactory(grade);
        durationSpn.setValueFactory(duration);
         ObservableList<String> stds;
        try {
            stds = DBManager.getStudents();
            TextFields.bindAutoCompletion(studentFld, stds);
        } catch (SQLException ex) {
            Logger.getLogger(NewSessionPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }    

    @FXML
    private void addSession(ActionEvent event) {
        try {
            
            String hwContent=hwContentArea.getText();
            if (hwContent.equals("")) {
                hwContent="None";
            }
            DBManager.addSession(Integer.parseInt(studentFld.getText().split("_")[2]), fixDate(dateFld.getValue().toString()), durationSpn.getValue(), gradeSpn.getValue(), sessionContentArea.getText(), hwAssigned.isSelected(), hwContent);
            errLbl.setText("Session added");
        } catch (Exception e) {
            errLbl.setText("Failed to add session");
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
        studentFld.setText("");
       SpinnerValueFactory<Double> duration = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 12, 1,0.5);
        SpinnerValueFactory<Integer> grade = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 12, 1,1);
        
        gradeSpn.setValueFactory(grade);
        durationSpn.setValueFactory(duration);
        dateFld.setValue(null);
        sessionContentArea.setText("");
        hwAssigned.setSelected(false);
         hwContentArea.setEditable(false);
            hwContentArea.setDisable(true);
            hwContentArea.setText("");
    }
    
    private String fixDate(String date) {
        String newDate = date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8, 10);
        return newDate;
    }

    @FXML
    private void toggleHwArea(ActionEvent event) {
        if (hwAssigned.isSelected()) {
            hwContentArea.setEditable(true);
            hwContentArea.setDisable(false);
        }else{
             hwContentArea.setEditable(false);
            hwContentArea.setDisable(true);
            hwContentArea.setText("");
        }
    }
    
}
