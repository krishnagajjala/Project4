import java.util.HashMap;


public class Assignment {
    String name;
    double weight;
    int maxPoints;
    HashMap<String,Integer> studentGrades; //fname and lname stored together in the sorted format.
    //example Alice Jones 50 will be formatted as "Jones, Alice" maps to -> 50

    public Assignment(String requestedName, int maxPts, double wght) {
        name = new String(requestedName);
        maxPoints = maxPts;
        weight = wght;
        studentGrades = new HashMap<String,Integer>();
    }

    public void addStudentGrade(String studentFName, String studentLName, int grade) {
        String formattedName = studentLName + ", " + studentFName; //note space after this comma
        studentGrades.put(formattedName, grade);
    }
}