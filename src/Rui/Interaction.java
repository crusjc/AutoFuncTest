package Rui;

import org.junit.Assert;
import static org.junit.Assert.*;
import java.io.File;
import java.util.*;

import org.dom4j.Element;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Rui.Tools.snapShot;

public class Interaction {

	public static void interaction(WebDriver driver,Element step,File snapcase) {
        HashMap<String,String> varMap = new HashMap<String,String>();
	    HashMap ev = new Env().env();
		String stepNo = step.attributeValue("index");
		String obj = step.element("Object").getText();
        String _action = step.element("Action").getText();
        String par;
		String Expect;
        try {
            par =  step.element("parameter").getText();
        } catch(Exception e) {
            par = "";
        }

        try {
            Expect = step.element("ExpectedResult").getText();
        } catch(Exception e) {
            Expect = "";
        }

		if(obj.isEmpty()) {
			switch (_action) {
				/*
				* 打开网页
				* */
				case "get":
					System.out.println("step " + stepNo + " ------> "
							+ "login " + par);
					driver.get((String) ev.get(par));
					break;
				/*
				* 强制等待
				* */
				case "sleep":
					try {
						System.out.println("step " + stepNo + " ------> sleep "
								+ par);
						Thread.sleep(Long.parseLong(par));
					} catch (Exception e) {
						System.out.println(e.toString());
						snapShot((TakesScreenshot)driver, snapcase);
						Assert.fail();
					}
					break;
				/*
				 *给一个变量赋值，之后的步骤可以调用
				 **/
				case "setValue":
					System.out.println("step " + stepNo + " ------> "
                            + "set value for " + par + " : " + Expect);
					if(Expect.equals("now")) {
						varMap.put(par,String.valueOf(new Date().getTime()));
					} else {
						varMap.put(par,Expect);
					}
					break;
                /*
                * 原生弹框点击确认
                * */
				case "acceptAlert":
				    try {
                        System.out.println("step " + stepNo + " ------> "
                                +"弹框点击确认");
                        driver.switchTo().alert().accept();
                    } catch (NoAlertPresentException e) {
                        System.out.println("step " + stepNo + " ------> "
                                +"没有弹出框");
                        snapShot((TakesScreenshot)driver, snapcase);
                        Assert.fail();
                    }
					break;
                /*
                * 原生弹框点击取消
                * */
				case "dismissAlert":
                    try {
                        System.out.println("step " + stepNo + " ------> "
                                +"弹框点击取消");
                        driver.switchTo().alert().dismiss();
                    } catch (NoAlertPresentException e) {
                        System.out.println("step " + stepNo + " ------> "
                                +"没有弹出框");
                        snapShot((TakesScreenshot)driver, snapcase);
                        Assert.fail();
                    }
					break;
				case "switchToParent":
					System.out.println("step " + stepNo + " ------> "
							+ "跳回上层iframe");
					driver.switchTo().parentFrame();
					break;
				case "switchToDefault":
					System.out.println("step " + stepNo + " ------> "
							+ "跳回主页面");
					driver.switchTo().defaultContent();
					break;
				case "waitPageTitle":
                    System.out.println("step " + stepNo + " ------> "
                            + par + "秒内,待标题显示");
                    new WebDriverWait(driver, Long.parseLong((par))).until(ExpectedConditions.titleIs(Expect));
					break;
				default:
					System.out.println("step " + stepNo + " ------> "
                            +" has no correct method");
					snapShot((TakesScreenshot)driver, snapcase);
					Assert.fail();
			}
		} else {
            Elements eles = new Elements();
			Actions action = new Actions(driver);
				switch (_action) {
				    /*
				    * 光标从元素开始滑动
				    * */
					case "perform":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " perform ");
						action.moveToElement(eles.webEle(driver,step,snapcase)).perform();
						break;
                    /*
                    * 下拉框选择
                    * */
					case "select":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " "
								+ _action + " "
								+ par);
						if(par.startsWith("::")) {
							new Select(eles.webEle(driver,step,snapcase)).selectByVisibleText(varMap.get(par.split("::")[1]));
						} else {
							new Select(eles.webEle(driver,step,snapcase)).selectByVisibleText(par);
						}
						break;
                    /*
                    * 输入框输入
                    * */
					case "sendkeys":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " "
								+ _action + " "
								+ par);
						if(par.startsWith("::")) {
                            eles.webEle(driver,step,snapcase).sendKeys(varMap.get(par.split("::")[1]));
						} else {
                            eles.webEle(driver,step,snapcase).sendKeys(par);
						}
						break;
                    /*
                    * 点击
                    * */
					case "click":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " click ");
                        eles.webEle(driver,step,snapcase).click();
						break;
                    /*
                    * 清空
                    * */
					case "clear":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " clear ");
                        eles.webEle(driver,step,snapcase).clear();
						break;
                    /*
                    * 断言对比
                    * */
					case "assertEquals":
						System.out.println("step " + stepNo + " ------> validate "
								+ obj + " expected result : " 
								+ Expect + " actual result : "
								+ eles.webEle(driver,step,snapcase).getText());
						snapShot((TakesScreenshot)driver, snapcase);
						if(Expect.startsWith("::")) {
							assertEquals(varMap.get(Expect.split("::")[1]),eles.webEle(driver,step,snapcase).getText());
						} else {
							assertEquals(Expect,eles.webEle(driver,step,snapcase).getText());
						}
						break;
                    /*
                    * 获取元素的内容，赋予一个变量
                    * */
					case "setUIText":
						System.out.println("step " + stepNo + " ------> "
                                +"set setUIText for " + obj + " : " + eles.webEle(driver,step,snapcase).getText());
						varMap.put(obj,eles.webEle(driver,step,snapcase).getText());
						break;
					case "setUIValue":
						System.out.println("step " + stepNo + " ------> "
								+"set setUIValue for " + obj + " : " + eles.webEle(driver,step,snapcase).getAttribute("value"));
						varMap.put(obj,eles.webEle(driver,step,snapcase).getAttribute("value"));
						break;
                    case "waitElementClickable":
                        System.out.println("step " + stepNo + " ------> "
                                + par + "秒内,待" + obj + "可点击");
                        new WebDriverWait(driver, Long.parseLong((par))).until(ExpectedConditions.elementToBeClickable(eles.webEle(driver,step,snapcase)));
                        break;
                    case "waitElementSelected":
                        System.out.println("step " + stepNo + " ------> "
                                + par + "秒内,待" + obj + "可被选中");
                        new WebDriverWait(driver, Long.parseLong((par))).until(ExpectedConditions.elementToBeSelected(eles.webEle(driver,step,snapcase)));
                        break;
                    case "waitElementVisible":
                        System.out.println("step " + stepNo + " ------> "
                                + par + "秒内,待" + obj + "显示");
                        new WebDriverWait(driver, Long.parseLong((par))).until(ExpectedConditions.visibilityOfElementLocated(eles.getBy(step)));
                        break;
                    case "waitElementPresence":
                        System.out.println("step " + stepNo + " ------> "
                                + par + "秒内,待" + obj + "出现");
                        new WebDriverWait(driver, Long.parseLong((par))).until(ExpectedConditions.presenceOfElementLocated(eles.getBy(step)));
                        break;
                    case "waitTextLoading":
                        System.out.println("step " + stepNo + " ------> "
                                + par +" 秒内,待 " + obj + " 改变初始值 "+ Expect);
                        int sleepTime = 1;
                        while ((eles.webEle(driver,step,snapcase).getText().equals(Expect))) {
                            if (sleepTime > Integer.parseInt(par))
                                break;
                            try
                            {
                                Thread.sleep(1000);
                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                            sleepTime++;
                        }
                        break;
                    case "switchToFrame":
                        System.out.println("step " + stepNo + " ------> "
                                + "跳转到 " + obj + " 的iframe");
                    	driver.switchTo().frame(eles.webEle(driver,step,snapcase));
                        break;
					default:
						System.out.println("step " + stepNo + " ------> "
                                + obj + "has no correct method");
						snapShot((TakesScreenshot)driver, snapcase);
						Assert.fail();
				}	
		}
	}
}


//action.moveToElement(we1).moveToElement(we2).click().build().perform();		//pass

//action.moveToElement(we1).moveToElement(we2).click().perform();				//pass

//action.moveToElement(we1).perform();											//pass
//we2.click();										//pass