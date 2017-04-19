package Rui;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class TestSuite {
    @SuppressWarnings("unchecked")
    public List<String> testSuite() {
        List<String> cases = new ArrayList<String>();
        try {
            Document suiteXml = new SAXReader().read(new File("TestSuite", "TestSuite.xml"));
            Element currentEnv = suiteXml.getRootElement().element(new Env().env().get("env"));
            List<Element> syss = currentEnv.elements();
            List<Element> sys;
            Element syssIt;
            Element toBecase;
            for (Iterator<Element> i = syss.iterator(); i.hasNext(); ) {
                syssIt = i.next();
                sys = syssIt.elements();
                if (syssIt.attributeValue("status").equals("1")) {
                    for (Iterator<Element> j = sys.iterator(); j.hasNext(); ) {
                        toBecase = j.next();
                        cases.add(syssIt.getName() + "::" + toBecase.getText());
                    }
                } else {
                    for (Iterator<Element> j = sys.iterator(); j.hasNext(); ) {
                        toBecase = j.next();
                        if(toBecase.attributeValue("status").equals("1")) {
                            cases.add(syssIt.getName() + "::" + toBecase.getText());
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return cases;
    }
}
