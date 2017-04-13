package Rui;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Env {
	public static HashMap<String, String> hm = new HashMap<String, String>();
	@SuppressWarnings("unchecked")
	public static HashMap<String, String> env() {
		try {
			Element ele = new SAXReader().read(new File("Env","env.xml")).getRootElement().element("env_info");
			List<Element> envInfo = ele.elements();
			for(Element url:envInfo) {
				if(url.getName().equals("env")) hm.put(url.getName(),url.getText());
				hm.put(url.getName(),url.getText());
			}	
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return hm;
	}
}
