package Rui;

public class Test {
	public static void main(String[] args) throws Exception{
		//获取属性，设置属性
		//执行js
        //上传文件 (Upload File) 上传文件的元素操作
		//使用log
		//浏览器信息、驱动定制化
		//重试机制
        //debug机制
		//分布式
        //发送邮件
        //解决日期插件问题


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
