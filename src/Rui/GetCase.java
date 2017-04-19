package Rui;

import  java.io.*;
import java.util.HashMap;

import jxl.Cell;
import  jxl.Sheet;
import  jxl.Workbook;

public class GetCase {

	public void getCase(String parent,String child) {
		File xml = new File("TestCase" + File.separator + parent,child.replace("xls","xml"));
		if(xml.exists()) xml.delete();
		try {
			xml.createNewFile();
			OutputStreamWriter  osw = new OutputStreamWriter(new FileOutputStream(xml, true),"UTF-8");
			osw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\t\n");
			Sheet sheet  =  Workbook.getWorkbook(new File("TestCase" + File.separator + parent,child)).getSheet(0);
	    
			osw.write("<CASE CaseName=\"" + sheet.getCell(1,0).getContents() + "\" author = \"" + sheet.getCell(3,0).getContents() + "\">\t\n");
			osw.write(" <STEPS>" + "\t\n");
	
			for(int i=2;i<sheet.getRows();i++) {
				switch(sheet.getCell(1,i).getContents()) {
					case "interaction":
						getInteraction(osw,sheet,i,5);
						break;
					case "template":
						getTemplate(osw,sheet,i,5);
						break;
					case "sql":
						getSql(osw,sheet,i,5);
						break;
					default:System.out.println("unknown way of step,please check case");
				}
			}
			osw.write(" </STEPS>\t\n");
			osw.write("</CASE>");
	
			osw.close();
		} catch (Exception e) {		
			e.printStackTrace();
		} finally {		
		}
	}

	public void getInteraction(OutputStreamWriter osw,Sheet sheet,int i,int parCol) {
		try {
			osw.write("  <STEP index=\"" + sheet.getCell(0,i).getContents() + "\" Category=\"" + sheet.getCell(1,i).getContents() + "\">\t\n");
			osw.write("   <Object PST=\""+ sheet.getCell(2,i).getContents() +"\">" + sheet.getCell(3,i).getContents() + "</Object>\t\n");
			osw.write("   <Action>" + sheet.getCell(4,i).getContents() + "</Action>\t\n");
			if(sheet.getCell(parCol,i).getContents().isEmpty()) {							
			} else {
				osw.write("   <parameter>" + sheet.getCell(parCol,i).getContents() + "</parameter>\t\n");
			}						
			if(sheet.getCell(parCol+1,i).getContents().isEmpty()) {							
			} else {
				osw.write("   <ExpectedResult>" + sheet.getCell(parCol+1,i).getContents() + "</ExpectedResult>\t\n");						
			}
			osw.write("  </STEP>\t\n");
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void getTemplate(OutputStreamWriter osw,Sheet sheet,int i,int parCol) {
        HashMap hs = new Env().env();
		try {
			File template = new File("Template" + File.separator + sheet.getCell(2,i).getContents(),sheet.getCell(3,i).getContents() + ".xls");
			Sheet templateXls  =  Workbook.getWorkbook(template).getSheet(0);	
			int TempParameterCol = 5;
			String parArrayName = sheet.getCell(parCol,i).getContents();
			Cell[] parArrayNames = templateXls.getRow(0);
			for(int ArrayNames=0;ArrayNames<parArrayNames.length;ArrayNames++) {
				if((!parArrayNames[ArrayNames].getContents().isEmpty())&&parArrayNames[ArrayNames].getContents().equals(hs.get("env") + "::" + parArrayName)) {
					TempParameterCol = ArrayNames;
				} else {
					continue;
				}
			
			}

			
			for(int j=2;j<templateXls.getRows();j++) {
				switch(templateXls.getCell(1,j).getContents()) {
					case "interaction":
						getInteraction(osw,templateXls,j,TempParameterCol);
						break;
					case "template":
						getTemplate(osw,templateXls,j,TempParameterCol);
						break;
					case "sql":
						getSql(osw,templateXls,j,TempParameterCol);
						break;
					default:System.out.println("unknown way of step,please check case");
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	public void getSql(OutputStreamWriter osw,Sheet sheet,int i,int parCol) {
		try {
			osw.write("  <STEP index=\"" + sheet.getCell(0,i).getContents() + "\" Category=\"" + sheet.getCell(1,i).getContents() + "\">\t\n");
			osw.write("   <Object PST=\""+ sheet.getCell(2,i).getContents() +"\">" + sheet.getCell(3,i).getContents() + "</Object>\t\n");
			osw.write("   <Action>" + sheet.getCell(4,i).getContents() + "</Action>\t\n");
			if(sheet.getCell(parCol,i).getContents().isEmpty()) {							
			} else {
				for(int k=0;k<sheet.getCell(parCol,i).getContents().split("::").length;k++) {
					int l = k + 1;
					osw.write("   <parameter" + l + ">" + sheet.getCell(parCol,i).getContents().split("::")[k] + "</parameter" + l + ">\t\n");
				}
			}						
			if(sheet.getCell(parCol+1,i).getContents().isEmpty()) {							
			} else {
				for(int m=0;m<sheet.getCell(parCol+1,i).getContents().split("::").length;m++) {
					int n = m + 1;
					osw.write("   <ExpectedResult" + n + ">" + sheet.getCell(parCol+1,i).getContents().split("::")[m] + "</ExpectedResult" + n + ">\t\n");
				}						
			}
			osw.write("  </STEP>\t\n");		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
