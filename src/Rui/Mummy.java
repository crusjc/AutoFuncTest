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
	private static String chromePath = new Env().env().get("chromePath");
    private static String chromeDriverPath = new Env().env().get("chromeDriverPath");
	private WebDriver driver;
	private List<Element> step;
	private String exeCase;
	private String sys;
	private String caseName;
	private File snapcase;
	private ChromeOptions options;
	private Element stepEle;
	private int _number;
	 public Mummy(String _case,String number) {
		 sys = _case.split("::")[0];
		 exeCase = _case.replace("xls","xml");
		 caseName = exeCase.split("::")[1];
         _number = Integer.parseInt(number);
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
        .usingDriverExecutable(new File(chromeDriverPath))
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
		options.setBinary(chromePath);
		driver = new ChromeDriver(service,options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		//exeCase.split("::")[1].split("xml")[0]          用例名
	    snapcase = new File(Tools.createLog(_number),exeCase.split("::")[1].split("xml")[0] + "png");
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
