import java.util.ArrayList;
import java.util.HashMap;


public class Student {
    String firstName;
    String lastName;
    HashMap<String,Integer> grades;

    public Student(String fName, String lName) {
        firstName = new String(fName);
        lastName = new String(lName);
        grades = new ArrayList<Assignment>();
    }

    public void addGrade(String assignmentName, int receivedGrade) {
        grades.put(assignmentName, receivedGrade);
    }
}