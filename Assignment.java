import java.util.HashMap;


public class Assignment {
    String name;
    double weight;
    int maxPoints;
    HashMap<String,Integer> studentGrades;

    public Assignment(String requestedName, int maxPts, double wght) {
        name = new String(requestedName);
        maxPoints = maxPts;
        weight = wght;
        studentGrades = new HashMap<String,Integer>();
    }
}