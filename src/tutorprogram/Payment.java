/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorprogram;

/**
 *
 * @author gteuw
 */
public class Payment {
    private int paymentID;
    private int studentID;
    private double amount;
    private String date;

    public Payment(int paymentID, int studentID, double amount, String date) {
        this.paymentID = paymentID;
        this.studentID = studentID;
        this.amount = amount;
        this.date = date;
    }

    public int getPaymentID() {
        return paymentID;
    }

    public int getStudentID() {
        return studentID;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }
    
    
}
