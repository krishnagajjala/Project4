
//import ...
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    //keyType is TBD
    keyType key; //-> initializes then after requested encrypted and must match to decrypt end-file
    digestType digest; //->tracks files integrity
  /* Read a Gradebook from a file */
  public Gradebook(String filename) {
    //create a gradebook object
    bookName = filename;
    studentList = new ArrayList<Student>();
    assignmentList = new ArrayList<Assignment>();

    key = SecureHashRandomThing();
    students = new ArrayList<String>();
    assignments = new LinkedHashMap<String,HashMap<String, Integer>>();
    //pseudo code
    File created = new File(bookName);
    //pseudo create the file in directory output stream
    created.pushToOutputStream;
  }

  /* Returns the keyFirst time, do not call multiple times since the key will be encrypted after first call*/
  public keyType getPlainKeyOneTime() {
      //pseudo code
      keyType plainKey = key;
      key = encrypt(key);
      return plainKey;
  }
  /* return the number of students*/
  public int studentCount() {
    return studentList.size();
  }

  public int assignmentCount() {
      return assignmentList.size();
  }

  /* Adds a student to the gradebook */
  public void addStudent(String fName, String lName) {
        Student toAdd = new Student(fName, lName);
        studentList.add(toAdd);
  }

  /* Adds an assignment to the gradebook */
  public void addAssignment(String aName, int maxPoints, double weight) {
    Assignment toAdd = new Assignment(aName, maxPoints, weight);
    assignmentList.add(toAdd);
    totalWeight += weight;
  }

  /* Adds a grade to the gradebook */
  public void addGrade(String fName, String lName, String aName, int grade) {
    Student search;
    for (Student curr : studentList) {
        if (curr.firstName.equals(fName) && curr.lastName.equals(lName)) {
            curr.addGrade(aName, grade);
        }
    }
  }

  public String toString() {

    return "";
  }
}
