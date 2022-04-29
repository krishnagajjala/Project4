import java.io.Serializable;
import java.util.HashMap;


public class Assignment implements Serializable{
    String name;
    int maxPoints;
    double weight;
    HashMap<Student,Integer> studentGrades; //fname and lname stored together in the sorted format.
    //example Alice Jones 50 will be formatted as "Jones, Alice" maps to -> 50

    public Assignment(String requestedName, int maxPts, double wght) {
        name = new String(requestedName);
        maxPoints = maxPts;
        weight = wght;
        studentGrades = new HashMap<Student,Integer>();
    }

    public String toString() {
        return name + ", " + maxPoints + ", " + weight + ", " + studentGrades.toString() + "\n";
    }
}