import java.util.ArrayList;



public class Student {
    String firstName;
    String lastName;
    ArrayList<Assignment> grades;

    public Student(String fName, String lName) {
        firstName = new String(fName);
        lastName = new String(lName);
        grades = new ArrayList<Assignment>();
    }
}