//import ...
import java.io.*;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.*; //we're gonna use AES-GCM from here
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
/**
 * Initialize gradebook with specified name and generate a key.
 */

 //inspiration to choose this algorithm and help design majority of helper methods comes from this link:
 //https://nullbeans.com/how-to-encrypt-decrypt-files-byte-arrays-in-java-using-aes-gcm/
 //^^ this was my primary source in getting this set up for our Gradebook env
public class setup {

    public static final int AES_KEY_SIZE = 128; //using 128bit length key
    public static final int GCM_IV_LENGTH = 12; //using IV 12bit length
    public static final int KEY_LENGTH = 12; //plaintext key is 12 chars long in plaintext; according to web even if unlucky
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
        if (!args[0].equals("-N")) {
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
        System.out.println(key);

        //make the gradebook
        Gradebook created = new Gradebook(requestedName);
        //CHANGE TO SERIALIZE
        byte[] serializedGradebook = Gradebook.serializeGradebook(created);
        //surround encryption with try-catch
        try {
            //encrypt the gradebook and write to disk
            byte[] ciphertextGradebookBytes = encryptBytes(key, serializedGradebook);
            setup.writeFile(requestedName,ciphertextGradebookBytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //something has gone terribly wrong
            System.out.println("I've got a bad feeling about this... \n");
        }
        
        return;

    }

    //helper method to read file and transform into bytes; assumes file is in current directory
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
        //explicitly make it overwrite via false parameter to constructor
        try(FileOutputStream fileOutputStream = new FileOutputStream("./"+filename, false)) { 
            fileOutputStream.write(data);
        }

    }

    //generate key so it aligns with AES requirements, source linked above at top of file
    public static SecretKey generateSecretKey(String password, byte[] iv) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), iv, 65536, AES_KEY_SIZE); // AES-128 curr size
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] key = secretKeyFactory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(key, "AES");
    }

    //helper method to encrypt bytes given a key, returns a byte array. from source listed at top of file
    public static byte[] encryptBytes(String key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {

        //Prepare the secure random nonce
        //each time encryption occurs need a new random nonce
        //else whole scheme falls apart
        SecureRandom secureRandom = new SecureRandom();

        //Noonce should be equal to declared constant bytes
        byte[] iv = new byte[GCM_IV_LENGTH];
        secureRandom.nextBytes(iv);

        //Prepare secretKey based on provided password (plaintext key printed to console earlier) and random iv
        SecretKey secretKey = generateSecretKey(key, iv);

        //generate cipher with AES/GCM, no padding
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(AES_KEY_SIZE, iv);

        //Cipher is set to encryption mode
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        //doFinal encrypts the data
        byte[] encryptedData = cipher.doFinal(data);

        //Concatenate information necessary for decryption and return the aggregate data
        //ByteBuffer imported here to make it easier to append and perform operations
        ByteBuffer byteBuffer = ByteBuffer.allocate(4 + iv.length + encryptedData.length);
        byteBuffer.putInt(iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(encryptedData);
        return byteBuffer.array();
    }

    //helper method to decrypt bytes given a key, returns a byte array. from source listed at top of file
    public static byte[] decryptData(String key, byte[] encryptedData) throws NoSuchPaddingException, 
            NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, 
            BadPaddingException, IllegalBlockSizeException, InvalidKeySpecException {


        //Wrap the data into a byte buffer to ease the reading and handling process of byte arrays
        ByteBuffer byteBuffer = ByteBuffer.wrap(encryptedData);

        int noonceSize = byteBuffer.getInt();

        //Make sure that the file was encrypted properly
        if(noonceSize < GCM_IV_LENGTH || noonceSize >= GCM_IV_LENGTH + 4) {
            throw new IllegalArgumentException("Nonce size is incorrect. " +
            "Make sure that the incoming data is an AES encrypted file.");
        }
        byte[] iv = new byte[noonceSize];
        byteBuffer.get(iv);

        //Prepare the key based on user plaintext key and retrieved nonce
        SecretKey secretKey = generateSecretKey(key, iv);

        //get the rest of encrypted data
        byte[] cipherBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(cipherBytes);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(AES_KEY_SIZE, iv);

        //Decryption mode on!
        cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

        //Decrypt the data
        return cipher.doFinal(cipherBytes);

    }

}
