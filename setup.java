//import ...

/**
 * Initialize gradebook with specified name and generate a key.
 */
public class setup {

  /* test whether the file exists */
  private boolean file_test(String filename) {
    //TODO complete
    return true;
  }

  public static void main(String[] args) {
    String key;

    if (args.length < 2) {
      System.out.println("Usage: setup <logfile pathname>");
      System.exit(1);
    }

  /* add your code here */

    System.out.println("Key is: " + key);

    return;
  }
}
