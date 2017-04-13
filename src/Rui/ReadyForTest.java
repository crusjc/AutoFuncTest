package Rui;

import static Rui.TestSuite.testSuite;
import static Rui.GetEnv.getEnv;
import static Rui.GetSuite.getSuite;


import java.util.List;

public class ReadyForTest {

	public static void main(String[] args) {
    	getEnv("env.xls");
	    getSuite("TestSuite.xls");
	    List<String> TestCases = testSuite();
		for(int i=0;i<TestCases.size();i++) {
			GetCase.getCase(TestCases.get(i).split("::")[0], TestCases.get(i).split("::")[1]);
		}
	}
}
