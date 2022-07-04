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
public class TableData extends Student{
    
    private double owed;
    private double payed;
    
    public TableData(int ID, String name, String surname, String dateOfBirth, String email, String phone,double owed,double payed) {
        super(ID, name, surname, dateOfBirth, email, phone);
        this.owed=owed;
        this.payed=payed;
    }

    public double getOwed() {
        return owed;
    }

    public double getPayed() {
        return payed;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    
}
