/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Observable;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author gteuw
 */
public class DBManager {

    /**
     * Holds the class's connection to the database file
     */
    private static Connection con;

    private static double RATE = 200;
    /**
     * Holds whether or not the database file has been created by querying
     * SQLite
     */
    private static boolean hasData = false;

    /**
     * Connects to the database file if not already connected
     *
     * @throws SQLException
     */
    public void connect() throws SQLException {
        if (con == null) {
            getConnection();
        }
        
        
    }

    /**
     * Gets a connection to the database file and establishes con
     *
     * @throws SQLException
     */
    private static void getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:TutorTracking.db");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initialise();
    }

    /**
     * Creates the required table if they do not already exist
     *
     * @throws SQLException
     */
    private static void initialise() throws SQLException {
        if (!hasData) {
            hasData = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='Students'");

            if (!res.next()) {
                System.out.println("No Database Yet");
                state = con.createStatement();
                //state2.execute("CREATE TABLE user(id integer,fname varchar(60),lname varchar(60),primary key(id));");
                state.execute("CREATE TABLE \"Payments\" (\n"
                        + "	\"PaymentID\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "	\"StudentID\"	INTEGER,\n"
                        + "	\"Amount\"	REAL,\n"
                        + "	\"Date\"	TEXT\n"
                        + ");");
                state.execute("CREATE TABLE \"Sessions\" (\n"
                        + "	\"SessionID\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "	\"StudentID\"	INTEGER,\n"
                        + "	\"Date\"	INTEGER,\n"
                        + "	\"TimeTaken\"	REAL,\n"
                        + "	\"Grade\"	INTEGER,\n"
                        + "	\"Content\"	TEXT,\n"
                        + "	\"Homework\"	INTEGER,\n"
                        + "	\"HomeworkContent\"	TEXT\n"
                        + ");");
                state.execute("CREATE TABLE \"Students\" (\n"
                        + "	\"ID\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "	\"Name\"	TEXT,\n"
                        + "	\"Surname\"	TEXT,\n"
                        + "	\"DateOfBirth\"	TEXT,\n"
                        + "	\"Email\"	TEXT,\n"
                        + "	\"Phone\"	TEXT\n"
                        + ");");
                state.execute("CREATE TABLE \"Marks\" (\n"
                        + "	\"MarkID\"	INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + "	\"StudentID\"	INTEGER,\n"
                        + "	\"Mark\"	INTEGER,\n"
                        + "	\"Date\"	TEXT,\n"
                        + "	\"Subject\"	TEXT,\n"
                        + "	\"Type\"	TEXT\n"
                        + ");");

            }
        }
    }

    public static void addStudent(String name, String surname, String dob, String email, String phone) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO Students values(?,?,?,?,?,?);");
        prep.setString(2, name);
        prep.setString(3, surname);
        prep.setString(4, dob);
        prep.setString(5, email);
        prep.setString(6, phone);
        prep.execute();
    }

    public static void addSession(int studentID, String date, double timeTaken, int grade, String content, boolean homework, String homeworkContent) throws SQLException {
        int hw = 0;
        if (homework) {
            hw = 1;
        }
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO Sessions values(?,?,?,?,?,?,?,?);");
        prep.setString(2, studentID + "");
        prep.setString(3, date);
        prep.setString(4, timeTaken + "");
        prep.setString(5, grade + "");
        prep.setString(6, content);
        prep.setString(7, hw + "");
        prep.setString(8, homeworkContent);
        prep.execute();
    }

    public static void addPayment(int student, double amount, String date) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO Payments values(?,?,?,?);");
        prep.setString(2, student + "");
        prep.setString(3, amount + "");
        prep.setString(4, date + "");
        prep.execute();
    }

    public static void addMark(int student, int mark, String date, String subject, String type) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("INSERT INTO Marks values(?,?,?,?,?,?);");
        prep.setString(2, student + "");
        prep.setString(3, mark + "");
        prep.setString(4, date + "");
        prep.setString(5, subject + "");
        prep.setString(5, type + "");
        prep.execute();
    }

    public static void deleteStudent(int studentID) throws SQLException {
        if (con == null) {
            getConnection();
        }
        deleteAllMarks(studentID);
        deleteAllPayments(studentID);
        deleteAllSessions(studentID);
        PreparedStatement prep = con.prepareStatement("Delete FROM Students WHERE ID= \"" + studentID + "\";");
        prep.execute();

    }

    public static void deleteSession(int sessionID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Sessions WHERE SessionID= \"" + sessionID + "\";");
        prep.execute();
    }

    public static void deleteAllSessions(int sessionID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Sessions WHERE SessionID= \"" + sessionID + "\";");
        prep.execute();
    }

    public static void deletePayment(int paymentID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Payments WHERE PaymentID= \"" + paymentID + "\";");
        prep.execute();
    }

    public static void deleteAllPayments(int StudentID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Payments WHERE StudentID= \"" + StudentID + "\";");
        prep.execute();
    }

    public static void deleteMark(int markID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Payments WHERE MarkID= \"" + markID + "\";");
        prep.execute();
    }

    public static void deleteAllMarks(int studentID) throws SQLException {
        if (con == null) {
            getConnection();
        }

        PreparedStatement prep = con.prepareStatement("Delete FROM Payments WHERE StudentID= \"" + studentID + "\";");
        prep.execute();
    }

    public static void updateStudent(int ID, String name, String surname, String dob, String email, String phone) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("UPDATE Students SET name=?,surname=?,DateOfBirth=?,Email=?,Phone=? WHERE ID=" + ID);
        prep.execute();
    }

    public static void updateSession(int sessionID, int studentID, String date, double timeTaken, int grade, String content) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("UPDATE Students SET StudentID=?,Date=?,TimeTaken=?,Grade=?,Content=? WHERE SessionID=" + sessionID);
        prep.execute();
    }

    public static void updateMark(int makrID, int student, int mark, String date, String subject, String type) throws SQLException {
        if (con == null) {
            getConnection();
        }
        PreparedStatement prep = con.prepareStatement("UPDATE Marks SET StudentID=?,Mark=?,Date=?,Subject=?,Type=? WHERE MarkID=" + makrID);
        prep.execute();
    }

    public static ObservableList<Student> populateStudents() throws SQLException {
        ObservableList<Student> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT * \n"
                    + "FROM Students");

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            Student temp = new Student(Integer.parseInt(res.getString("ID")), res.getString("Name"), res.getString("Surname"), res.getString("DateOfBirth"), res.getString("Email"), res.getString("Phone"));
            info.add(temp);

        }
        return info;
    }

    public static ObservableList<Session> populateSessions(int studentID) throws SQLException {
        ObservableList<Session> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT * \n"
                    + "FROM Sessions "
                    + "Where StudentID=" + studentID);

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {
            System.out.println(res.getString("StudentID"));
            Session temp = new Session(Integer.parseInt(res.getString("SessionID")), Integer.parseInt(res.getString("StudentID")), res.getString("Date"), Double.parseDouble(res.getString("TimeTaken")), Integer.parseInt(res.getString("Grade")), res.getString("Content"), Integer.parseInt(res.getString("Homework")), res.getString("HomeworkContent"));
            info.add(temp);

        }
        return info;
    }

    public static ObservableList<Mark> populateMarksForStudent(int studentID) throws SQLException {
        ObservableList<Mark> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT * \n"
                    + "FROM Marks "
                    + "Where StudentID=" + studentID);

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            Mark temp = new Mark(Integer.parseInt(res.getString("MarkID")), Integer.parseInt(res.getString("StudentID")), Integer.parseInt(res.getString("Mark")), res.getString("Date"), res.getString("Subject"), res.getString("Type"));
            info.add(temp);

        }
        return info;
    }
    public static ObservableList<Payment> populatePaymentsForStudent(int studentID) throws SQLException {
        ObservableList<Payment> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT * \n"
                    + "FROM Payments "
                    + "Where StudentID=" + studentID);

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            Payment temp = new Payment(Integer.parseInt(res.getString("PaymentID")), Integer.parseInt(res.getString("StudentID")), Double.parseDouble(res.getString("Amount")), res.getString("Date"));
            info.add(temp);

        }
        return info;
    }

    public static ObservableList<TableData> populateTableData() throws SQLException {
        ObservableList<TableData> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT * \n"
                    + "FROM Students");

        } catch (SQLException ex) {
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            int currentID = Integer.parseInt(res.getString("ID"));
            TableData temp = new TableData(Integer.parseInt(res.getString("ID")), res.getString("Name"), res.getString("Surname"), res.getString("DateOfBirth"), res.getString("Email"), res.getString("Phone"), getOwed(currentID), getPayed(currentID));
            info.add(temp);

        }
        return info;
    }

    private static double getOwed(int ID) throws SQLException {
        try {
            Scanner file = new Scanner(new FileReader("rate.txt"));
            RATE=Double.parseDouble(file.next());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        double owed = 0.0;
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("Select SUM(TimeTaken*" + RATE + ") as A From Sessions\n"
                    + "GROUP BY StudentID\n"
                    + "Having StudentID=" + ID);
            owed = Double.parseDouble(res.getString("A"));
        } catch (SQLException ex) {
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return owed;
        }

        return owed;
    }

    private static double getPayed(int ID) {
        double owed = 0;
        ResultSet res = null;
        if (con == null) {
            try {
                getConnection();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT SUM(Amount) as A FROM Payments \n"
                    + "Group by StudentID\n"
                    + "Having StudentID=" + ID);
            owed = Double.parseDouble(res.getString("A"));
        } catch (SQLException ex) {
            //Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return owed;
        }

        return owed;
    }
    
    public static ObservableList<String> getStudents() throws SQLException {
        ObservableList<String> info = FXCollections.observableArrayList();
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT Name,Surname,ID\n"
                    + "FROM Students ORDER by ID DESC");

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            String temp = res.getString("Name") + "_" + res.getString("Surname") + "_" + res.getString("ID");

            info.add(temp);

        }
        return info;
    }
    
    public static String getStudent(int id) throws SQLException {
        setRATE();
        String info="";
        ResultSet res = null;
        if (con == null) {
            getConnection();
        }
        try {
            Statement state = con.createStatement();
            res = state.executeQuery("SELECT Name,Surname,ID\n"
                    + "FROM Students WHERE ID="+id);

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (res.next()) {

            String temp = res.getString("Surname") + ", " + res.getString("Name");

            info=temp;

        }
        return info;
    }

    public static double getRATE() {
        return RATE;
    }

    public static void setRATE() {
        try {
            Scanner file = new Scanner(new FileReader("rate.txt"));
            RATE=Double.parseDouble(file.next());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

}
