package Rui;

import java.io.File;
import java.io.FileWriter;
import jxl.Sheet;
import jxl.Workbook;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class GetSuite {
	public static void getSuite(String child) {
		File suiteXls = new File("TestSuite",child);
		File suiteXml = new File("TestSuite",child.replace("xls", "xml"));
		if(suiteXml.exists()) suiteXml.delete();
		XMLWriter writer = null;
		OutputFormat format = OutputFormat.createCompactFormat();
		format.setEncoding("UTF-8");
		try {
			Element env = null;
			Element system = null;
			Element caseRecord = null;
			suiteXml.createNewFile();
			Workbook suiteBook = Workbook.getWorkbook(suiteXls);
			Document _document = DocumentHelper.createDocument();
			Element _root = _document.addElement("TestSuite");
			String[] envs = suiteBook.getSheetNames();
			Sheet[] sheets = suiteBook.getSheets();
			for(int i=0;i<envs.length;i++) {
				 env = _root.addElement(envs[i]);
				for(int j=1;j<sheets[i].getRows();j++) {
					if(sheets[i].getCell(1,j).getContents().isEmpty()) {
					system = env.addElement(sheets[i].getCell(0,j).getContents());
					system.addAttribute("status", sheets[i].getCell(2,j).getContents());
					} else {
						caseRecord = system.addElement("case");
						caseRecord.setText(sheets[i].getCell(1,j).getContents());
						caseRecord.addAttribute("status",sheets[i].getCell(2,j).getContents());
					}
				}
			}
			writer = new XMLWriter(new FileWriter(suiteXml), format);
            writer.write(_document);
            writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
