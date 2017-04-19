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
						Thread.sleep(Integer.parseInt(par));
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

				default:
					System.out.println("step " + stepNo + " ------> "
                            +" has no correct method");
					snapShot((TakesScreenshot)driver, snapcase);
					Assert.fail();
			}
		} else {
            Elements eles = new Elements();
			WebElement we = eles.webEle(driver,step,snapcase);
			By by = eles.getBy(step);
			Actions action = new Actions(driver);
				switch (_action) {
				    /*
				    * 光标从元素开始滑动
				    * */
					case "perform":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " perform ");
						action.moveToElement(we).perform();
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
							new Select(we).selectByVisibleText(varMap.get(par.split("::")[1]));
						} else {
							new Select(we).selectByVisibleText(par);
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
							we.sendKeys(varMap.get(par.split("::")[1]));
						} else {
							we.sendKeys(par);
						}
						break;
                    /*
                    * 点击
                    * */
					case "click":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " click ");
						we.click();
						break;
                    /*
                    * 清空
                    * */
					case "clear":
						System.out.println("step " + stepNo + " ------> "
								+ obj + " clear ");
						we.clear();
						break;
                    /*
                    * 断言对比
                    * */
					case "validate":
						System.out.println("step " + stepNo + " ------> validate "
								+ obj + " expected result : " 
								+ Expect + " actual result : "
								+ we.getText());
						snapShot((TakesScreenshot)driver, snapcase);
						if(Expect.startsWith("::")) {
							assertEquals(varMap.get(Expect.split("::")[1]),we.getText());
						} else {
							assertEquals(Expect,we.getText());
						}
						break;
                    /*
                    * 获取元素的内容，赋予一个变量
                    * */
					case "setUIValue":
						System.out.println("step " + stepNo + " ------> "
                                +"set UIValue for " + obj + " : " + we.getText());
						varMap.put(obj,we.getText());
						break;
                    case "waitElementClickable":
                        System.out.println("step " + stepNo + " ------> "
                                + par+"秒内,待"+we.toString()+"可点击");
                        WebDriverWait wait = new WebDriverWait(driver, Long.parseLong((par)));
                        wait.until(ExpectedConditions.elementToBeClickable(we));
                        break;
                    case "waitElementSelected":
                        System.out.println("step " + stepNo + " ------> "
                                + par+"秒内,待"+we.toString()+"可被选中");
                        wait = new WebDriverWait(driver, Long.parseLong((par)));
                        wait.until(ExpectedConditions.elementToBeSelected(we));
                        break;
                    case "waitElementVisible":
                        System.out.println("step " + stepNo + " ------> "
                                + par+"秒内,待"+we.toString()+"显示");
                        wait = new WebDriverWait(driver, Long.parseLong((par)));
                        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
                        break;
                    case "waitElementPresence":
                        System.out.println("step " + stepNo + " ------> "
                                + par+"秒内,待"+we.toString()+"出现");
                        wait = new WebDriverWait(driver, Long.parseLong((par)));
                        wait.until(ExpectedConditions.presenceOfElementLocated(by));
                        break;
                    case "waitTextLoading":
                        System.out.println("step " + stepNo + " ------> "
                                + par+" 秒内,待 "+we.toString()+ " 改变初始值 "+ Expect);
                        int sleepTime = 1;
                        while ((we.getText().equals(par))) {
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
                                + "跳转到第 "+ Integer.parseInt(obj) + " 个iframe");
                    	driver.switchTo().frame(Integer.parseInt(obj));
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
					default:
						System.out.println("step " + stepNo + " ------> "
                                + we.toString() + "has no correct method");
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