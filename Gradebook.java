
//import ...
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * A helper class for your gradebook
 * Some of these methods may be useful for your program
 * You can remove methods you do not need
 * If you do not wiish to use a Gradebook object, don't
 */
public class Gradebook implements Serializable { //going to serialize objects to make it easier to write to files

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

  //takes a gradebook and serializes it to bytes to be encrypted
  public static byte[] serializeGradebook(Gradebook book) {
    // Reference for stream of bytes
    byte[] stream = null;
    // ObjectOutputStream is used to convert a Java object into OutputStream
    try (ByteArrayOutputStream byteArrayOutput = new ByteArrayOutputStream();
            ObjectOutputStream outputDest = new ObjectOutputStream(byteArrayOutput);) {
        outputDest.writeObject(book);
        stream = byteArrayOutput.toByteArray();
    } catch (IOException e) {
        // Error in serialization
        System.err.println("Serialization went wrong.");
    }
    return stream;
  }

  //takes decrypted bytes as input, turns into a gradebook object
  public static Gradebook deserializeGradebook(byte[] decryptedInput) {
    Gradebook book = null;

    try (ByteArrayInputStream byteArrayInput = new ByteArrayInputStream(decryptedInput);
            ObjectInputStream objectInput = new ObjectInputStream(byteArrayInput);) {
        book = (Gradebook) objectInput.readObject();
    } catch (IOException e) {
        // Error in de-serialization
        System.err.println("Deserialization went wrong");
    } catch (ClassNotFoundException e) {
        // You are converting an invalid stream to Gradebook
        System.err.println("Invalid stream, not a Gradebook");
    }
    return book;
}

  public String toString() {

    return "Gradebook name: " + this.bookName + "\n" + "Student List: " + studentList.toString() + 
    "\n" + "Assignment List: " + assignmentList.toString();
  }
}
