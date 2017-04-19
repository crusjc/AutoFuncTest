package Rui;

import java.util.List;

public class ReadyForTest {

	public static void main(String[] args) {
        new GetEnv().getEnv("env.xls");
	    new GetSuite().getSuite("TestSuite.xls");
	    List<String> tc = new TestSuite().testSuite();
	    GetCase gc = new GetCase();
		for(int i=0;i<tc.size();i++) {
			gc.getCase(tc.get(i).split("::")[0], tc.get(i).split("::")[1]);
		}
	}
}
