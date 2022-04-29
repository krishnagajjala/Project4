import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import ...

/**
 * Allows the user to add a new student or assignment to a gradebook,
 * or add a grade for an existing student and existing assignment
 */
public class gradebookadd {

	public static void main(String[] args) {
		
		// testing input is correct
		if (args.length < 6) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[0].equals("-N")) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[2].equals("-K")) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[4].equals("-AA") && !args[4].equals("-DA") && !args[4].equals("-AS") 
				&& !args[4].equals("-DS")&& !args[4].equals("-AG")) {
			System.out.println("invalid");
			System.exit(255);
		}
		String inputName = new String(args[1]);
		String mykey = args[3];
		File GB = new File("./" + inputName);
		if (!GB.exists()) {
			System.out.println("invalid");
			System.exit(255);
		}
		Gradebook myGB = null;
		try {
            byte[] decrypted = setup.decryptData(mykey, setup.readFile(inputName));
			myGB = Gradebook.deserializeGradebook(decrypted);
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | IOException e) {
			System.out.println("invalid");
			System.exit(255);
		} 
		
		System.out.println("\nNumber Of Arguments Passed: " + args.length);
		System.out.println("----Following Are The Command Line Arguments Passed----");
		for (int counter = 0; counter < args.length; counter++)
			System.out.println("args[" + counter + "]: " + args[counter]);
		
		
		// Decide what is the setting we are in
		
		if (args[4].equals("-AA")) {
			if (Arrays.asList(args).lastIndexOf("-FN") != -1 || Arrays.asList(args).lastIndexOf("-AS") != -1
					|| Arrays.asList(args).lastIndexOf("-DA") != -1|| Arrays.asList(args).lastIndexOf("-AG") != -1
					|| Arrays.asList(args).lastIndexOf("-G") != -1 	|| Arrays.asList(args).lastIndexOf("-DS") != -1|| Arrays.asList(args).lastIndexOf("-LN") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ian = Arrays.asList(args).lastIndexOf("-AN");
			int  ip = Arrays.asList(args).lastIndexOf("-P");
			int iw = Arrays.asList(args).lastIndexOf("-W");
			if (ian == -1 || ip == -1 || iw == -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			String assignName = args[ian+1];
			int points = Integer.parseInt(args[ip+1]);
			double weight = Double.parseDouble(args[iw+1]);
			
			// checking for space in name for assignment
			if (ian+2 < args.length && args[ian+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
			Assignment test = myGB.findAssignment(assignName);
		    if (test != null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    if (myGB.totalWeight + weight > 1) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    myGB.addAssignment(assignName, points, weight);
			
			
		}
		if (args[4].equals("-DA")) {
			if (Arrays.asList(args).lastIndexOf("-FN") != -1 || Arrays.asList(args).lastIndexOf("-P") != -1|| Arrays.asList(args).lastIndexOf("-W") != -1
					|| Arrays.asList(args).lastIndexOf("-AS") != -1|| Arrays.asList(args).lastIndexOf("-LN") != -1
					|| Arrays.asList(args).lastIndexOf("-AG") != -1|| Arrays.asList(args).lastIndexOf("-AA") != -1
					|| Arrays.asList(args).lastIndexOf("-G") != -1 || Arrays.asList(args).lastIndexOf("-DS") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ian = Arrays.asList(args).lastIndexOf("-AN");

			if (ian == -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			String assignName = args[ian+1];
			
			// checking for space in name for assignment
			if (ian+2 < args.length && args[ian+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
			Assignment test = myGB.findAssignment(assignName);
		    if (test == null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    myGB.delAssignment(test);

			
		}
		if (args[4].equals("-AS")) {
			if (Arrays.asList(args).lastIndexOf("-AG") != -1 || Arrays.asList(args).lastIndexOf("-AA") != -1
					|| Arrays.asList(args).lastIndexOf("-DA") != -1|| Arrays.asList(args).lastIndexOf("-AG") != -1
					|| Arrays.asList(args).lastIndexOf("-AN") != -1|| Arrays.asList(args).lastIndexOf("-P") != -1
					|| Arrays.asList(args).lastIndexOf("-W") != -1|| Arrays.asList(args).lastIndexOf("-G") != -1 || Arrays.asList(args).lastIndexOf("-DS") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ifn = Arrays.asList(args).lastIndexOf("-FN");
			int  iln = Arrays.asList(args).lastIndexOf("-LN");
			if (ifn == -1 || iln == -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			String fName = args[ifn+1];
			// checking for space in name
			if (ifn+2 < args.length && args[ifn+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			String lName = args[iln+1];
			// checking for space in name 
			if (iln+2 < args.length && args[iln+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
			
		    // Student does not exist
			Student test = myGB.findStudent(fName, lName);
		    if (test != null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    // Add Student
		    myGB.studentList.add(new Student(fName, lName));
			

		}
		if (args[4].equals("-DS")) {
			if (Arrays.asList(args).lastIndexOf("-AG") != -1 || Arrays.asList(args).lastIndexOf("-AA") != -1
					|| Arrays.asList(args).lastIndexOf("-DA") != -1|| Arrays.asList(args).lastIndexOf("-AG") != -1
					|| Arrays.asList(args).lastIndexOf("-AN") != -1|| Arrays.asList(args).lastIndexOf("-P") != -1
					|| Arrays.asList(args).lastIndexOf("-W") != -1|| Arrays.asList(args).lastIndexOf("-G") != -1|| Arrays.asList(args).lastIndexOf("-AS") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ifn = Arrays.asList(args).lastIndexOf("-FN");
			int  iln = Arrays.asList(args).lastIndexOf("-LN");
			if ((ifn == -1 || iln == -1)) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			String fName = args[ifn+1];
			// checking for space in name
			if (ifn+2 < args.length && args[ifn+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			String lName = args[iln+1];
			// checking for space in name 
			if (iln+2 < args.length && args[iln+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
			
		    // Student does not exist
			Student test = myGB.findStudent(fName, lName);
		    if (test == null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    // Remove Student
		    myGB.studentList.remove(test);
		}
		if (args[4].equals("-AG")) {
			if (Arrays.asList(args).lastIndexOf("-DS") != -1 || Arrays.asList(args).lastIndexOf("-AA") != -1
					|| Arrays.asList(args).lastIndexOf("-DA") != -1|| Arrays.asList(args).lastIndexOf("-AG") != -1
					|| Arrays.asList(args).lastIndexOf("-P") != -1
					|| Arrays.asList(args).lastIndexOf("-W") != -1|| Arrays.asList(args).lastIndexOf("-AS") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ian = Arrays.asList(args).lastIndexOf("-AN");
			int ifn = Arrays.asList(args).lastIndexOf("-FN");
			int  iln = Arrays.asList(args).lastIndexOf("-LN");
			int ig = Arrays.asList(args).lastIndexOf("-G");
			if (ifn == -1 || iln == -1 || ig == -1 || ian == -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			String fName = args[ifn+1];
			// checking for space in name
			if (ifn+2 < args.length && args[ifn+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			String lName = args[iln+1];
			// checking for space in name 
			if (iln+2 < args.length && args[iln+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
			String assignName = args[ian+1];
			int grade = Integer.parseInt(args[ig+1]);

			
			// checking for space in name for assignment
			if (ian+2 < args.length && args[ian+2].charAt(0) != '-') {
				System.out.println("invalid");
				System.exit(255);
			}
			
		    // Student or assignment does not exist
			Student test = myGB.findStudent(fName, lName);
		    if (test == null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    
			Assignment test2 = myGB.findAssignment(assignName);
		    if (test2 == null) {
				System.out.println("invalid");
				System.exit(255);
		    }
		    test2.studentGrades.put(test, grade);

		}
		byte[] serializedGradebook = Gradebook.serializeGradebook(myGB);
        //surround encryption with try-catch
        try {
            //encrypt the gradebook and write to disk
            byte[] ciphertextGradebookBytes = setup.encryptBytes(mykey, serializedGradebook);
            setup.writeFile(inputName,ciphertextGradebookBytes);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            //something has gone terribly wrong
            System.out.println("I've got a bad feeling about this... \n");
        }
		

	}
}
