package Rui;
import static Rui.Interaction.interaction;
import java.io.File;
import java.util.*;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

@RunWith(Parameterized.class)
public class Mummy {

	private static ChromeDriverService service;
	private static List<String> TestCases = new TestSuite().testSuite();

	private  File reportFolder = Tools.createLog();
	private WebDriver driver;
	private List<Element> step;
	private String exeCase;
	private String sys;
	private String caseName;
	private File snapcase;
	private ChromeOptions options;
	private Element stepEle;
	
	 public Mummy(String _case,String number) {
		 sys = _case.split("::")[0];
		 exeCase = _case.replace("xls","xml");
		 caseName = exeCase.split("::")[1];
     }
	
	@Parameters
    public static Collection<String[]> prepareData(){
		String[][] str = new String[TestCases.size()][2];
		for(int i=0;i<TestCases.size();i++) {
			str[i][0] = TestCases.get(i);
			str[i][1] = String.valueOf(i);
		}
        return Arrays.asList(str);
    }
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	    service = new ChromeDriverService.Builder()
        .usingDriverExecutable(new File("./tools/chromedriver.exe"))
        .usingAnyFreePort()
        .build();
	    service.start();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		service.stop();
	}
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		options = new ChromeOptions();
		options.setBinary("C:/Users/Administrator/AppData/Local/Google/Chrome/Application/chrome.exe");
		driver = new ChromeDriver(service,options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	    snapcase = new File(reportFolder,exeCase.split("::")[1].split("xml")[0] + "png");
	    step = new SAXReader().read(new File("TestCase/"+ sys,caseName)).getRootElement().element("STEPS").elements();
	    System.out.println("caseName : " + exeCase.split("::")[1].split("\\.")[0]);
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}


	@Test
	public void test() {
		for(Iterator<Element> i = step.iterator(); i.hasNext(); ) {
			stepEle = i.next();
			interaction(driver,stepEle,snapcase);
		}
	}

}
