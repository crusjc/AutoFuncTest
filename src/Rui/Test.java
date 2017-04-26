package Rui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import javax.xml.ws.Service;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Test {
	public static void main(String[] args) throws Exception{
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File("./tools/chromedriver.exe"))
                .usingAnyFreePort()
                .build();
        service.start();
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:/Users/Administrator/AppData/Local/Google/Chrome/Application/chrome.exe");
        WebDriver driver = new ChromeDriver(service,options);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
        driver.get("http://58.215.167.30/anneng/");
        driver.findElement(By.id("email")).sendKeys("admin");
        driver.findElement(By.id("pwd")).sendKeys("ne888888");
        Thread.sleep(5000);
        driver.findElement(By.id("submit_btn")).click();
        driver.findElement(By.xpath("//div[text()='报价管理']/parent::*/div[2]/a")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//div[text()='报价管理']/parent::*/following-sibling::div[1]//li[1]/div")).click();
        Thread.sleep(3000);
        driver.switchTo().frame(driver.findElement(By.xpath("//*[@id='tab-box']/div[2]/div[2]/div/iframe")));
        driver.findElement(By.xpath("//label[text()='使用网点']/following-sibling::span[1]/input[1]")).sendKeys("ne888888");
        Thread.sleep(5000);
        driver.quit();
        service.stop();
		//获取属性，设置属性
		//执行js
		//上传文件 (Upload File) 上传文件的元素操作
		//解决日期插件问题
		//使用log
		//重试机制
		//debug机制
		//分布式
		//发送邮件
        /*
        *13.   双击按钮
WebElement inputBox =driver.findElement(by.id(“kw”))//百度首页文本框
Actions builder = new Actions(driver) //声明actions对象
builder.doubleclick(inputBox).build().perform()
        *
        * 操作多选的选择列表
        *droplist.deselectByIndex(3)//取消选中多选列表第四个选项，从0开始
droplist.deselectByValue(“Alo”)//取消选中多选列表为Alo的选项
droplist.deselectByVisibleText(“But”)//取消选中多选列表文字为”But”的选项
        *
        * 3.      设置一个页面对象的属性值
JavascriptExecutor js(变量名) = (JavascriptExecutor) driver
//实例化JavascriptExecutor对象，,driver为Webdriver

WebElement element =driver.findElement(By.id("text"));
String attributeName = “size”;
String value = “10”;

js.executeScript (“arguments[0].setAttribute(arguments[1],arguments[2])”,element,attributeName,value)
// setAttribute为设置对象属性方法removeAttribute为移除属性方法，也可用上述方法，但可以不传入value.
        *
        *30.   查看页面元素属性
WebElement SearchBox =driver.findElement(By.id("kw")); //将搜索框的id实例化
SearchBox.getAttribute("value");//查看元素Value的属性
        *
        *31.   获取元素CSS属性值
WebElement SearchButton =driver.findElement(By.id("su")); //实例化搜索按钮
SearchButton.getCssValue("height"); //用getCssValue获取按钮的高度
SearchButton.getCssValue("width"); //用getCssValue获取按钮的宽度
        *
        *3.      设置一个页面对象的属性值
JavascriptExecutor js(变量名) = (JavascriptExecutor) driver
//实例化JavascriptExecutor对象，,driver为Webdriver

WebElement element =driver.findElement(By.id("text"));
String attributeName = “size”;
String value = “10”;

js.executeScript (“arguments[0].setAttribute(arguments[1],arguments[2])”,element,attributeName,value)
// setAttribute为设置对象属性方法removeAttribute为移除属性方法，也可用上述方法，但可以不传入value.
        *
        *4.      在日期选择器上进行日期选择:
driver.navigate().to("http://jqueryui.com/resources/demos/datepicker/other-months.html");//转向日期选择器网址
WebElement TextBox =driver.findElement(By.id("datepicker"));//找到日历
TextBox.click();//单击
TextBox.sendKeys("02/29/2016")//用sendkeys方法输入指定日期
        *
        *
        *
        *
        *
        *
        *
        *8.      操作富文本框
//打开网站，搜狐邮箱：mail.sohu.com
((JavascriptExecutor)driver).executeScript(documents.getElementsByTagName(‘body’)[0].innerhtml=‘<b>邮件要发送的内容<b>’);
// documents.getElementsByTagName(‘body’)[0]获取富文本框的区域对象,
Innerhtml属性可以设定Html格式文本的内容
        *
        *
        *
        * */
	}

}
