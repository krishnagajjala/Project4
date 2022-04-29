//import ...
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.*; //we're gonna use AES-GCM from here
/**
 * Initialize gradebook with specified name and generate a key.
 */

 //inspiration to choose this algorithm and design helper methods comes from this link:
 //https://nullbeans.com/how-to-encrypt-decrypt-files-byte-arrays-in-java-using-aes-gcm/
public class setup {

    public static final int AES_KEY_SIZE = 128; //using 256bit length key
    public static final int GCM_IV_LENGTH = 96; //using IV 96bit length
    public static final int GCM_TAG_LENGTH = 128; //using 128bit tag for integrity checking
    public static final int KEY_LENGTH = 12; //key is 12 chars long in plaintext; according to web even if unlucky
                                            //all lowercase chosen, takes several weeks -> 2 years if one cap letter, 2kyears
                                            //if one number chosen by the SecureRandom (one symbol? 63k years)

  /* test whether the file exists */
  private static boolean file_test(String filename) {
    //TODO complete
    String inputName = new String(filename);
    File seek = new File("./"+inputName);
    if (seek.exists()) {
        return false;
    }
    for (int i = 0; i< filename.length(); i++) {
        Character c = filename.charAt(i);
        if (Character.isLetterOrDigit(c) == false && c != '.' && c != '_') { //only allow alphanumeric, '.' and '_' in filenames
            return false;
        }
    }
    return true;
  }

    public static void main(String[] args) {

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

        //valid args and good file name, let's select a random key in the
        //ascii range, using secure random
        String key = "";
        String ascii = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom passwordPicker = new SecureRandom();
        for (int i = 0; i < KEY_LENGTH; i++) {
            key += ascii.charAt(passwordPicker.nextInt(ascii.length()));
        }
        System.out.println("Key is: " + key + "\n");

        //make the gradebook
        Gradebook created = new Gradebook(requestedName);

        return;

    }

    //helper method to read file and transform into bytes
    public static byte[] readFile(String fileName) throws IOException {
        File toRead = new File("./"+fileName); //ASSUMES FILE IS IN CURRENT DIRECTORY

        //.length() returns long, cast to int to make raw array
        byte[] fileData = new byte[(int) toRead.length()]; 

        try (FileInputStream fileInputStream = new FileInputStream(toRead)) {
            fileInputStream.read(fileData);
        }
        
        return fileData;

    }

    //helper method to write to a file
    public static void writeFile(String filename, byte[] data) throws IOException {

        try(FileOutputStream fileOutputStream = new FileOutputStream("./"+filename)) {
            fileOutputStream.write(data);
        }

    }

}
