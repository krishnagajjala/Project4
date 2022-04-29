import java.util.HashMap;


public class Student {
    String firstName;
    String lastName;
    HashMap<Assignment,Integer> grades;

    public Student(String fName, String lName) {
        firstName = new String(fName);
        lastName = new String(lName);
        grades = new HashMap<Assignment,Integer>();
    }


    public String toString() {
        return firstName + ", " + lastName + ", " + grades.toString() + "\n";
    }
}