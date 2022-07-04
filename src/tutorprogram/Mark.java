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
public class Mark {
    private int markID;
    private int sudentID;
    private int mark;
    private String date;
    private String subject;
    private String type;

    public Mark(int markID, int sudentID, int mark, String date, String subject, String type) {
        this.markID = markID;
        this.sudentID = sudentID;
        this.mark = mark;
        this.date = date;
        this.subject = subject;
        this.type = type;
    }

    

    public int getSudentID() {
        return sudentID;
    }

    public int getMark() {
        return mark;
    }

    public String getDate() {
        return date;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }
    
    
}
