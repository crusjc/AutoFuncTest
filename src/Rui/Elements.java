package Rui;

import static Rui.Tools.snapShot;
import org.junit.Assert;
import java.util.HashMap;
import java.util.Iterator;
import java.io.File;
import java.util.List;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;

public class Elements {
	public static HashMap<String, List<Element>> pages = new HashMap<String, List<Element>>();
	@SuppressWarnings("unchecked")
	public static HashMap<String, List<Element>> elements() {
		for(File page:new File("Page").listFiles()) {
			try {
				pages.put(page.getName().split("\\.")[0],new SAXReader().read(page).getRootElement().elements());
			} catch (DocumentException e) {
				System.out.println(e.toString());
			}
		}
		return pages;
	}
	
	public static WebElement webEle(WebDriver driver,Element step,File snapcase) {
		if(step == null) {
			System.out.println("step is null, please check case");
			snapShot((TakesScreenshot)driver, snapcase);
			Assert.fail();
		}		
		WebElement webEle = null;
		if((step2PageElement(driver,step,snapcase).attributeValue("id")!= null) && (!step2PageElement(driver,step,snapcase).attributeValue("id").isEmpty())){
			try {
				webEle = driver.findElement(By.id(step2PageElement(driver,step,snapcase).attributeValue("id")));
			} catch (NoSuchElementException e) {
				System.out.println(e.toString());
				snapShot((TakesScreenshot)driver, snapcase);
				Assert.fail();
			}
		}	else if((step2PageElement(driver,step,snapcase).attributeValue("xpath")!= null) && (!step2PageElement(driver,step,snapcase).attributeValue("xpath").isEmpty())){
			try {
				webEle = driver.findElement(By.xpath(step2PageElement(driver,step,snapcase).attributeValue("xpath")));
			} catch (NoSuchElementException e) {
				System.out.println(e.toString());
				snapShot((TakesScreenshot)driver, snapcase);
				Assert.fail();
			}
		}	else {
			System.out.println("only get the webElement by id or xpath now,please check the xml page");
			snapShot((TakesScreenshot)driver, snapcase);
			Assert.fail();
		}
		
		if(webEle == null) {
			System.out.println("can not find webElement in browser,please check system");
			snapShot((TakesScreenshot)driver, snapcase);
			Assert.fail();
			return null;
		} else {
			if(webEle.isDisplayed()) {
				return webEle;
			} else {
				System.out.println("this element is not displayed,please check system");
				snapShot((TakesScreenshot)driver, snapcase);
				Assert.fail();
				return null;
			}
		}	
	}
	
	public static Element step2PageElement(WebDriver driver,Element step,File snapcase) {
		Element pageElement = null;
		Element target = null;
		if(step.attributeValue("Category").equals("interaction")) {
		for(Iterator<Element> it=elements().get(step.element("Object").attributeValue("PST")).iterator();it.hasNext();){
				 pageElement = (Element) it.next();
                if(pageElement.getText().equals(step.element("Object").getText())) {
                	target = pageElement;
                }	else {
                	continue;
                }
             }
			 
			 if(target == null) {
				 System.out.println("can not find element's configuration in xml page");
				 snapShot((TakesScreenshot)driver, snapcase);
				 Assert.fail();
				 return null;
			 } else {
				 return target;
			 }
			 
		} else {
			System.out.println("not interaction");
			 snapShot((TakesScreenshot)driver, snapcase);
			 Assert.fail();
			return null;
		}
	}
}
