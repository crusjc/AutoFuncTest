package Rui;

import  java.io.*;
import  jxl.Workbook;

public class GetEnv {
	public static void getEnv(String child) {
		File xls = new File("Env",child);
		File xml = new File("Env",child.replace("xls", "xml"));
		if(xml.exists()) xml.delete();
		
		try {
			xml.createNewFile();
			Workbook envBook = Workbook.getWorkbook(xls);
			OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(xml,true),"UTF-8"); 
			osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n<env>\t\n");
			
			for(int i=0;i<envBook.getSheetNames().length;i++) {
				osw.write("	<"+ envBook.getSheetNames()[i] +">\t\n");
				for(int j=0;j<envBook.getSheet(i).getRows();j++) {
					osw.write("			<"+ envBook.getSheet(i).getCell(0,j).getContents() + ">" + envBook.getSheet(i).getCell(1,j).getContents() + "</" + envBook.getSheet(i).getCell(0,j).getContents() + ">\t\n");
				}	osw.write("	</"+ envBook.getSheetNames()[i] +">\t\n");
			}	osw.write("</env>\t\n");
			osw.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
