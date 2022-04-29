
//import ...
import java.util.ArrayList;
/**
 * A helper class for your gradebook
 * Some of these methods may be useful for your program
 * You can remove methods you do not need
 * If you do not wiish to use a Gradebook object, don't
 */
public class Gradebook {

    String bookName;
    ArrayList<Student> studentList;
    ArrayList<Assignment> assignmentList;
    double totalWeight; //CANNOT SURPASS 1

  /* Read a Gradebook from a file */
  public Gradebook(String filename) {
    //create a gradebook object
    bookName = filename;
    studentList = new ArrayList<Student>();
    assignmentList = new ArrayList<Assignment>();
  }

  /* return the number of students */
  public int studentCount() {
    return studentList.size();
  }

  /* return the number of assignments */
  public int assignmentCount() {
      return assignmentList.size();
  }

  /* Adds a student to the gradebook */
  public void addStudent(String fName, String lName) {
        Student toAdd = new Student(fName, lName);
        studentList.add(toAdd);
  }

  /* Adds an assignment to the gradebook,
    Note: This adds to the totalWeight field.
  */
  public void addAssignment(String aName, int maxPoints, double weight) {
    Assignment toAdd = new Assignment(aName, maxPoints, weight);
    assignmentList.add(toAdd);
    totalWeight += weight;
  }

  /* Adds a grade to the gradebook */
  public void addGrade(String fName, String lName, String aName, int grade) {
    for (Student curr : studentList) {
        if (curr.firstName.equals(fName) && curr.lastName.equals(lName)) {
            curr.addGrade(aName, grade);
        }
    }
    for (Assignment curr : assignmentList) {
        if (curr.name == aName) {
            curr.addStudentGrade(fName, lName, grade);
        }
    }
  }

  public String toString() {

    return "Gradebook name: " + this.bookName + "\n" + "Student List: " + studentList.toString() + 
    "\n" + "Assignment List: " + assignmentList.toString();
  }
}
