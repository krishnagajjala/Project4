//import ...
import java.io.File;

/**
 * Initialize gradebook with specified name and generate a key.
 */
public class setup {

  /* test whether the file exists */
  private static boolean file_test(String filename) {
    //TODO complete
    String inputName = new String(filename);
    File seek = new File("./"+inputName);
    if (seek.exists()) {
        return false;
    }
    return true;
  }

  public static void main(String[] args) {
    String key = null;
    if (args.length < 2) {
      System.out.println("Usage: setup <logfile pathname>");
      System.exit(1);
    }

  /* add your code here*/
    if (args[0] != "-N") {
        System.out.println("invalid");
        System.exit(255);
    }
    if (args.length > 3) {
        System.out.println("invalid");
        System.exit(255);
    }
    String requestedName = new String(args[1]);
    if (file_test(requestedName) != true) {
        System.out.println("invalid");
        System.exit(255);
    }  

    Gradebook created = new Gradebook(requestedName);
    key = created.getPlainKeyOneTime();

    System.out.println("Key is: " + key);

    return;
  }
}
