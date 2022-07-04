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
public class Session {
    private int sessionId;
    private int studentId;
    private String date;
    private double timeTaken;
    private int grade;
    private String content;
    private String homework;
    private String homeworkContent;

    public Session(int sessionId, int studentId, String date, double timeTaken, int grade, String content, int homework, String homeworkContent) {
        this.sessionId = sessionId;
        this.studentId = studentId;
        this.date = date;
        this.timeTaken = timeTaken;
        this.grade = grade;
        this.content = content;
        if (homework==1) {
            this.homework = "Yes";
        }else{
            this.homework="No";
        }
        
        this.homeworkContent = homeworkContent;
    }

    public int getSessionId() {
        return sessionId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getDate() {
        return date;
    }

    public double getTimeTaken() {
        return timeTaken;
    }

    public int getGrade() {
        return grade;
    }

    public String getContent() {
        return content;
    }

    public String getHomework() {
        return homework;
    }

    public String getHomeworkContent() {
        return homeworkContent;
    }

   
    
}
