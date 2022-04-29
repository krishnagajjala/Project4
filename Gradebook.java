
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
    studentList = new ArrayList<Student>();
    assignmentList = new ArrayList<Assignment>();
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
  /* return the number of students */
  public int studentCount() {
    return studentList.size();
  }

  /* return the number of assignments */
  public int assignmentCount() {
      return assignmentList.size();
  }
  /* Return Assignment if exists*/
  public Assignment findAssignment(String name) {
    if (this.assignments.containsKey(name)) {
      return this.assignments.get(name);
    } else {
      return null;
    }
  }

  /*Return Student if exists*/
  public Student findStudent(String first, String last) {
    String name = last + " " + first;

    if (this.students.containsKey(name)) {
      return this.students.get(name);
    } else {
      return null;
    }
  }

  /* Adds a student to the gradebook */

  public void addStudent(String first, String last) {

    // Student is already found
    if (this.findStudent(first, last) != null) {
      System.out.println("student already exists, try again");
    
    }

    // Create a newStudent
    Student newStudent = new Student(first, last);

    // Assignments with a score of 0
    for (Assignment a : this.getAssignments().values()) {
      newStudent.getAssignmentGrades().put(a, 0);
    }

    // Add a new Student
    this.students.put(last + " " + first, newStudent);
  }
    
  /* Deletes student from the gradebook */
  public void delStudent(String first, String last) {

    // Student does not exist
    if (this.findStudent(first, last) == null) {
      System.out.println("student does not exist");
      
    }

    // Remove Student
    this.students.remove(last +" "+ first);
    if (this.students.isEmpty()){
      this.students = new LinkedHashMap<String, Student>();
    }

  }

  /* Adds an assignment to the gradebook,
    Note: This adds to the totalWeight field.
  */
  public void addAssignment(String aName, int maxPoints, double weight) {
    Assignment toAdd = new Assignment(aName, maxPoints, weight);
    assignmentList.add(toAdd);
    totalWeight += weight;
  }


  /* Adds an assinment to the gradebook */
  public void addAssignment(...) {

  }

  /* Adds a grade to the gradebook */
  public void addGrade(String first, String last, String assignment, int grade) {

    Assignment currAssign = findAssignment(assignment);
    Student currStudent = findStudent(first, last);

    // Assignment does not exist
    if (currAssign == null) {
      System.out.println("assignment does not exist, try again");
      
    }

    // Student does not exist
    if (currStudent == null) {
      System.out.println("student does not exist");
      
    }

    // Grade is negative
    if (grade < 0) {
      System.out.println("Grade is invalid");
 
    }
    // Add/overwrite a grade
    currStudent.getAssignmentGrades().put(currAssignment, grade);
  }
  public String toString() {

    return "Gradebook name: " + this.bookName + "\n" + "Student List: " + studentList.toString() + 
    "\n" + "Assignment List: " + assignmentList.toString();
  }
}
