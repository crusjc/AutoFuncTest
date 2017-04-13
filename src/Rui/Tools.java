package Rui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Tools {
	
	 public static void snapShot(TakesScreenshot drivername, File file) {
	    try { 
            File srcFile = ((TakesScreenshot)drivername).getScreenshotAs(OutputType.FILE); 
            FileUtils.copyFile (srcFile,file); 
        } catch (IOException e) {
            e.printStackTrace(); 
        } finally {
	    }
	  }
	 
		public static File createLog() {
			File reportFolder =  new File(System.getProperty("user.dir") + File.separator + "Report" + File.separator + 
		    		String.valueOf(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())));
		    File reportLog = new File(reportFolder,"SystemOut.txt");
		    reportFolder.mkdir();
		    try {
				reportLog.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   
			try {
				PrintStream ps = new PrintStream(reportLog);
				System.setOut(ps);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
			return reportFolder;
		}
}
