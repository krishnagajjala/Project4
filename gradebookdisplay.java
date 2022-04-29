import java.io.File;
import java.util.Arrays;

//import ...

/**
 * Prints out a gradebook in a few ways Some skeleton functions are included
 */
public class gradebookdisplay {

	private static boolean file_test(String filename) {
		String inputName = new String(filename);
		File seek = new File("./" + inputName);
		if (seek.exists()) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		
		// testing input is correct
		if (args.length < 6) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[1].equals("-N")) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[3].equals("-K")) {
			System.out.println("invalid");
			System.exit(255);
		}
		if (!args[5].equals("-PA") && !args[5].equals("-PS") && !args[5].equals("-PF")) {
			System.out.println("invalid");
			System.exit(255);
		}
		String requestedName = new String(args[2]);
		if (file_test(requestedName) != true) {
			System.out.println("invalid");
			System.exit(255);
		}

		
		
		System.out.println("\nNumber Of Arguments Passed: %d" + args.length);
		System.out.println("----Following Are The Command Line Arguments Passed----");
		for (int counter = 0; counter < args.length; counter++)
			System.out.println("args[" + counter + "]: " + args[counter]);
		
		
		// Decide what is the setting we are in
		
		if (args[5].equals("-PA")) {
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
			
			//search for assignment in the gradebook here
			if(assignName not in gradebook) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			if (ia == -1) {
				//order by grade here and print output
				return answer;
			} else {
				//order by alphabetical here and print output
				return answer;
			}
			
		}
		if (args[5].equals("-PS")) {
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
			
			
			Student found = NULL;
			//search for student in the gradebook here
			if(fName+lName not in gradebook) {
				System.out.println("invalid");
				System.exit(255);
			}
			
			//print output here
			return answer;
		}
		if (args[5].equals("-PF")) {
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
			if (ia == -1) {
				//order by grade here and print output
				return answer;
			} else {
				//order by alphabetical here and print output
				return answer;
			}
			
		}
		

	}
}
