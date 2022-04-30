import java.io.File;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

//import ...

/**
 * Prints out a gradebook in a few ways Some skeleton functions are included
 */
public class gradebookdisplay {

	private static boolean file_test(String filename) {
		String inputName = new String(filename);
		File seek = new File("./" + inputName);
		return seek.exists();
	}

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
		if (!args[4].equals("-PA") && !args[4].equals("-PS") && !args[4].equals("-PF")) {
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
			myGB = Gradebook.deserializeGradebook((setup.decryptData(args[3], setup.readFile(inputName))));
		} catch (InvalidKeyException | NoSuchPaddingException | NoSuchAlgorithmException | InvalidAlgorithmParameterException | BadPaddingException | IllegalBlockSizeException | InvalidKeySpecException | IOException e) {
			System.out.println("invalid");
			System.exit(255);
		} 
		
		
//		System.out.println("\nNumber Of Arguments Passed: %d" + args.length);
//		System.out.println("----Following Are The Command Line Arguments Passed----");
//		for (int counter = 0; counter < args.length; counter++)
//			System.out.println("args[" + counter + "]: " + args[counter]);
		
		
		// Decide what is the setting we are in
		
		if (args[4].equals("-PA")) {
			if (Arrays.asList(args).lastIndexOf("-PS") != -1 || Arrays.asList(args).lastIndexOf("-PF") != -1 
					|| Arrays.asList(args).lastIndexOf("-FN") != -1 || Arrays.asList(args).lastIndexOf("-LN") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ian = Arrays.asList(args).lastIndexOf("-AN");
			int  ia = Arrays.asList(args).lastIndexOf("-A");
			int ig = Arrays.asList(args).lastIndexOf("-G");
			if (ian == -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			if ((ia == -1 && ig == -1) || (ia != -1 && ig != -1)) {
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
			
			if (ia == -1) {
				test.studentGrades.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println("(" +k.getKey().lastName + ", " + k.getKey().firstName+ ", "+ k.getValue()+ ")" ));
				//System.out.println("grade");
			} else {
				test.studentGrades.entrySet().stream()
                .sorted((k1, k2) -> k1.getKey().firstName.compareTo(k2.getKey().firstName))
                .sorted((k1, k2) -> k1.getKey().lastName.compareTo(k2.getKey().lastName))
                .forEach(k -> System.out.println("(" +k.getKey().lastName + ", " + k.getKey().firstName+ ", "+ k.getValue()+ ")" ));
				//System.out.println("alpha");
			}
			
		}
		if (args[4].equals("-PS")) {
			if (Arrays.asList(args).lastIndexOf("-PF") != -1 || Arrays.asList(args).lastIndexOf("-PA") != -1
					|| Arrays.asList(args).lastIndexOf("-AN") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int ifn = Arrays.asList(args).lastIndexOf("-FN");
			int  iln = Arrays.asList(args).lastIndexOf("-LN");
			if ((ifn == -1 && iln == -1)) {
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
			// checking for space in name for assignment
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
		    
		    
			for (Assignment assign : myGB.assignmentList) {
				System.out.println("(" +assign.name + ", "+ assign.studentGrades.get(test)+ ")" );
			}
//		    test.grades.entrySet().stream()
//            .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
//            .forEach(k -> System.out.println("(" +k.getKey() + ", "+ k.getValue()+ ")" ));
			
		}
		if (args[4].equals("-PF")) {
			if (Arrays.asList(args).lastIndexOf("-PS") != -1 || Arrays.asList(args).lastIndexOf("-PA") != -1 
					|| Arrays.asList(args).lastIndexOf("-FN") != -1 || Arrays.asList(args).lastIndexOf("-LN") != -1 
					|| Arrays.asList(args).lastIndexOf("-AN") != -1) {
				System.out.println("invalid");
				System.exit(255);
			}
			int  ia = Arrays.asList(args).lastIndexOf("-A");
			int ig = Arrays.asList(args).lastIndexOf("-G");
			if ((ia == -1 && ig == -1) || (ia != -1 && ig != -1)) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			HashMap<Student,Double> finalgrades = new HashMap<Student,Double>();
			for (Assignment assign : myGB.assignmentList) {
				for (Entry<Student, Integer> entry : assign.studentGrades.entrySet()) {
				    Student key = entry.getKey();
				    int value = entry.getValue();
				    if(finalgrades.containsKey(key)) {
				    	finalgrades.put(key, finalgrades.get(key) + (((double)value/(double)assign.maxPoints)*assign.weight));
				    } else {
				    	finalgrades.put(key, (((double)value/(double)assign.maxPoints)*assign.weight));
				    }
				}
			}
			
			if (ia == -1) {
				finalgrades.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> System.out.println("(" +k.getKey().lastName + ", " + k.getKey().firstName+ ", "+ k.getValue()+ ")" ));
				//System.out.println("grade");
			} else {
				finalgrades.entrySet().stream()
                .sorted((k1, k2) -> k1.getKey().firstName.compareTo(k2.getKey().firstName))
                .sorted((k1, k2) -> k1.getKey().lastName.compareTo(k2.getKey().lastName))
                .forEach(k -> System.out.println("(" +k.getKey().lastName + ", " + k.getKey().firstName+ ", "+ k.getValue()+ ")" ));
				//System.out.println("alpha");
			}
			
		}
		

	}

}
