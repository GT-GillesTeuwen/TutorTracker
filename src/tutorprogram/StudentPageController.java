/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gteuw
 */
public class StudentPageController implements Initializable {
    private int currentStudentID=0;
    @FXML
    private TableView<TableData> studentTbl;
    @FXML
    private TableView<Session> sessionTbl;
    @FXML
    private MenuItem addStudentContextBtn;
    @FXML
    private MenuItem newStudentMenuBtn;
    @FXML
    private MenuItem deleteStudentContextBtn;
    @FXML
    private Button addSessionBtn;
    @FXML
    private TableView<Payment> paymentTbl;
    @FXML
    private Button addPaymentBtn;
    @FXML
    private DatePicker dateFld;
    @FXML
    private TextField amountFld;
    @FXML
    private MenuItem openRate;
    @FXML
    private Button printBtn;
    @FXML
    private Label errLbl;
    @FXML
    private Label stdLbl;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn<TableData, String> IDCol = new TableColumn<>("ID");
        IDCol.setMinWidth(20);
        IDCol.getStyleClass().add("Surname");
        IDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<TableData, String> surnameCol = new TableColumn<>("Surname");
        surnameCol.setMinWidth(80);
        surnameCol.getStyleClass().add("Surname");
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));

        TableColumn<TableData, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(72);
        nameCol.getStyleClass().add("Surname");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<TableData, String> owedCol = new TableColumn<>("Owed");
        owedCol.setMinWidth(65);
        owedCol.getStyleClass().add("Balance");
        owedCol.setCellValueFactory(new PropertyValueFactory<>("owed"));

        TableColumn<TableData, String> paidCol = new TableColumn<>("Paid");
        paidCol.setMinWidth(65);
        paidCol.getStyleClass().add("Balance");
        paidCol.setCellValueFactory(new PropertyValueFactory<>("payed"));

        studentTbl.getColumns().addAll(IDCol, nameCol, surnameCol, owedCol, paidCol);

        try {
            //  progressBar.setProgress(50);
            studentTbl.setItems(DBManager.populateTableData());
        } catch (SQLException ex) {
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void setAll(MouseEvent event) throws SQLException {
        currentStudentID = studentTbl.getSelectionModel().getSelectedItem().getID();
        doSessionTbl();
        doPaymentTbl();
        //refreshStudentTbl();
        errLbl.setText("");
        stdLbl.setText("Selected Student: "+DBManager.getStudent(currentStudentID));
    }

    private void doSessionTbl() {
        sessionTbl.getColumns().clear();

        TableColumn<Session, String> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(20);
        dateCol.getStyleClass().add("Surname");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Session, String> gradeCol = new TableColumn<>("Grade");
        gradeCol.setMinWidth(20);
        gradeCol.getStyleClass().add("Surname");
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));

        TableColumn<Session, String> contentCol = new TableColumn<>("Content");
        contentCol.setMinWidth(86);
        contentCol.getStyleClass().add("Surname");
        contentCol.setCellValueFactory(new PropertyValueFactory<>("content"));

        TableColumn<Session, String> timeCol = new TableColumn<>("Hours");
        timeCol.setMinWidth(20);
        timeCol.getStyleClass().add("Surname");
        timeCol.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));

        TableColumn<Session, String> homeWorkCol = new TableColumn<>("Homework");
        homeWorkCol.setMinWidth(20);
        homeWorkCol.getStyleClass().add("Surname");
        homeWorkCol.setCellValueFactory(new PropertyValueFactory<>("homework"));

        TableColumn<Session, String> hwContentCol = new TableColumn<>("Homework Content");
        hwContentCol.setMinWidth(60);
        hwContentCol.getStyleClass().add("Surname");
        hwContentCol.setCellValueFactory(new PropertyValueFactory<>("homeworkContent"));

        sessionTbl.getColumns().addAll(dateCol, gradeCol, timeCol, contentCol, homeWorkCol, hwContentCol);

        try {

            sessionTbl.setItems(DBManager.populateSessions(currentStudentID));
        } catch (SQLException ex) {
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void doMarks() {

    }

    private void doPaymentTbl() {
        paymentTbl.getColumns().clear();
        

        TableColumn<Payment, String> dateCol = new TableColumn<>("Date");
        dateCol.setMinWidth(98);
        dateCol.getStyleClass().add("Surname");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Payment, String> amountCol = new TableColumn<>("Amount");
        amountCol.setMinWidth(80);
        amountCol.getStyleClass().add("Surname");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        paymentTbl.getColumns().addAll(dateCol, amountCol);

        try {
            paymentTbl.setItems(DBManager.populatePaymentsForStudent(currentStudentID));
        } catch (SQLException ex) {
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void openNewStudentPage(ActionEvent event) throws IOException {
        Stage homeStage = (Stage) studentTbl.getScene().getWindow();
        homeStage.close();

        FXMLLoader loadHomeHelp = new FXMLLoader(getClass().getResource("newStudentPage.fxml"));
        Parent root = (Parent) loadHomeHelp.load();
        NewStudentPageController help = loadHomeHelp.getController();

        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("newstudentpage.css");
        // Image icon = new Image(getClass().getResourceAsStream("/icons/LogoForToolbar.png"));
        // newStage.getIcons().add(icon);

        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void deleteStudent(ActionEvent event) throws SQLException {
        
        DBManager.deleteStudent(currentStudentID);
        refreshStudentTbl();
        refreshSessionTbl(currentStudentID);
    }

    private void refreshStudentTbl() {
        try {
            
            studentTbl.setItems(DBManager.populateTableData());
        } catch (SQLException ex) {
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void refreshSessionTbl(int id) {
        try {

            sessionTbl.setItems(DBManager.populateSessions(id));
        } catch (SQLException ex) {
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    @FXML
    private void openSessionPage(ActionEvent event) throws IOException {
        Stage homeStage = (Stage) studentTbl.getScene().getWindow();
        homeStage.close();

        FXMLLoader loadHomeHelp = new FXMLLoader(getClass().getResource("newSessionPage.fxml"));
        Parent root = (Parent) loadHomeHelp.load();
        NewSessionPageController help = loadHomeHelp.getController();

        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("newsessionpage.css");
        // Image icon = new Image(getClass().getResourceAsStream("/icons/LogoForToolbar.png"));
        // newStage.getIcons().add(icon);

        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void addPayment(ActionEvent event) {
        
        try {
            
            DBManager.addPayment(currentStudentID, Double.parseDouble(amountFld.getText()), fixDate(dateFld.getValue().toString()));
        } catch (Exception e) {
        }
        doPaymentTbl();
        refreshStudentTbl();
    }
    
    private String fixDate(String date) {
        String newDate = date.substring(0, 4) + "-" + date.substring(5, 7) + "-" + date.substring(8, 10);
        return newDate;
    }

    @FXML
    private void deleteSession(ActionEvent event) throws SQLException {
        Session selected =sessionTbl.getSelectionModel().getSelectedItem();
        int id = selected.getSessionId();
        DBManager.deleteSession(id);
        doSessionTbl();
        refreshStudentTbl();
    }

    @FXML
    private void deletePayment(ActionEvent event) throws SQLException {
        Payment selected = paymentTbl.getSelectionModel().getSelectedItem();
        int id = selected.getPaymentID();
        DBManager.deletePayment(id);
        doPaymentTbl();
        refreshStudentTbl();
        
    }

    @FXML
    private void openRate(ActionEvent event) throws IOException {
        FXMLLoader loadHomeHelp = new FXMLLoader(getClass().getResource("Settings.fxml"));
        Parent root = (Parent) loadHomeHelp.load();
        SettingsController help = loadHomeHelp.getController();

        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("settings.css");
        // Image icon = new Image(getClass().getResourceAsStream("/icons/LogoForToolbar.png"));
        // newStage.getIcons().add(icon);

        newStage.setScene(scene);
        newStage.show();
    }

    @FXML
    private void printInvoice(ActionEvent event){
        try {
            PDF.PDFMaker.print(currentStudentID);
            errLbl.setText("Print Success");
        } catch (SQLException ex) {
            errLbl.setText("Print Failed");
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            errLbl.setText("Print Failed");
            Logger.getLogger(StudentPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
