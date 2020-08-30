package challenge;

import java.util.List;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class Main {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		TestListenerAdapter tla = new TestListenerAdapter();

		TestNG testng = new TestNG();

		List<String> suites = Lists.newArrayList();
		try {
			// Passing the xml "Path" to the jar through the cmd while executing
			// the project fat jar
			String filePass = args[0];
			/*
			 * path to xml..in src we may pass a file or set of files
			 * suites.add(filePass1); suites.add(filePass2);
			 * suites.add(filePass3); ......
			 */
			suites.add(filePass);

			testng.setTestSuites(suites);

			testng.addListener(tla);
			testng.run();
		} catch (Exception e) {
			System.out.println("Please provide a valid TestNG XML file & Run Again");
		}
	}

}
