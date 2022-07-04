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
public class Student {
    protected int ID;
    protected String name;
    protected String surname;
    protected String dateOfBirth;
    protected String email;
    protected String phone;

    public Student(int ID, String name, String surname, String dateOfBirth, String email, String phone) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phone = phone;
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
