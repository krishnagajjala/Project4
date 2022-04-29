import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

//import ...

/**
 * A helper class for your gradebook
 * Some of these methods may be useful for your program
 * You can remove methods you do not need
 * If you do not wiish to use a Gradebook object, don't
 */
public class Gradebook {

    String bookName;
    ArrayList<String> students;
    /* each assignment has a string containing "name, max-points, weight"; pick these fields up by deliminating on 
     * commas. the hashMap is of string -> integer. where string is name of student and integer is their
     * grade. 
    */
    LinkedHashMap<String,HashMap<String, Integer>> assignments; 
    //keyType is TBD
    keyType key; //-> initializes then after requested encrypted and must match to decrypt end-file
  
  /* Read a Gradebook from a file */
  public Gradebook(String filename) {
    //create a gradebook object
    bookName = filename;
    /*make sure to input the name as "Last, First" -> then check for duplicate names
    //deliminate based on commas
    */
    key = SecureHashRandomThing();
    students = new ArrayList<String>();
    assignments = new LinkedHashMap<String,HashMap<String, Integer>>();
    //pseudo code
    File created = new File(bookName);
    created.pushToOutputStream;
  }

  /* Create a new gradebook */
  public Gradebook() {
    bookName = "";
    key = SecureHashRandomThing();
    students = new ArrayList<String>();
    assignments = new LinkedHashMap<String,HashMap<String, Integer>>();
    //pseudo code
    File created = new File(bookName);
    created.pushToOutputStream; //create the file in directory output stream
  }

  /* Returns the keyFirst time, do not call multiple times since the key will be encrypted after first call*/
  public keyType getPlainKeyOneTime(Gradebook requested) {
      //pseudo code
      keyType plainKey = requested.key;
      requested.key = encrypt(key);
      return plainKey;
  }
  /* return the size of the gradebook */
  public int size() {

    return len;
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

  /* Adds an assinment to the gradebook */
  public void addAssignment(...) {

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

    return "";
  }
}
