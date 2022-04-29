
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
    //keyType is TBD
    keyType key; //-> initializes then after requested encrypted and must match to decrypt end-file
    
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
      keyType plainKey = requested.key;
      requested.key = encrypt(key);
      return plainKey;
  }
  /* return the size of the gradebook */
  public int size() {

    return len;
  }

  /* Adds a student to the gradebook */
  public void addStudent(...) {

  }

  /* Adds an assinment to the gradebook */
  public void addAssignment(...) {

  }

  /* Adds a grade to the gradebook */
  public void addGrade(...) {

  }

  public String toString() {

    return "";
  }
}
