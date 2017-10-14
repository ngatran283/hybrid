package testApps;

import java.io.FileInputStream;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import datatable.ExcelReader;

public class DriverApp {
	private static final int WAIT_TIME_OUT = 20;
	public static Calendar cal = new GregorianCalendar();
	public static int month = cal.get(Calendar.MONTH);
	public static int year = cal.get(Calendar.YEAR);
	public static int sec = cal.get(Calendar.SECOND);
	public static int min = cal.get(Calendar.MINUTE);
	public static int date = cal.get(Calendar.DATE);
	public static int day = cal.get(Calendar.HOUR_OF_DAY);
	public static Random randomGenerator = new Random(); // Random Port Number generation
	public static int nSelPort;
	public static String sSelPort;
	public static String strDate;
	public static Properties CONFIG;
	public static Properties Objects;
	public static ExcelReader core;
	public static ExcelReader testdata;
	public static ExcelReader dbResult;
	public static WebDriver driver = null;
	public static Wait<WebDriver> wait;
	public static String currentTest;
	public static String keyword;
	public static String currentTSID;
	public static String stepDescription;
	public static String proceedOnFail;
	public static String data_column_name;
	public static String result;
	public static int testRepeat;
	public static String testStatus;
	public static String object;

	@BeforeSuite
	public void startTesting() throws Exception {

		CONFIG = new Properties();
		FileInputStream fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\config\\config.properties");
		CONFIG.load(fs);

		// LOAD Objects properties File
		Objects = new Properties();
		fs = new FileInputStream(System.getProperty("user.dir") + "\\src\\config\\Objects.properties");
		Objects.load(fs);

		// Load datatable
		core = new ExcelReader(System.getProperty("user.dir") + "\\src\\data\\Core.xlsx");
		testdata = new ExcelReader(System.getProperty("user.dir") + "\\src\\data\\Suite1.xlsx");
		dbResult = new ExcelReader(System.getProperty("user.dir") + "\\src\\config\\db_data.xlsx");

		System.setProperty("webdriver.chrome.driver", "D:\\filejar\\chromedriver.exe");
		driver = new ChromeDriver();

		// maximize window
		driver.manage().window().maximize();

		// wait for 30 seconds and then fail
		driver.manage().timeouts().implicitlyWait(WAIT_TIME_OUT, TimeUnit.SECONDS);
		wait = new FluentWait<WebDriver>(driver).withTimeout(WAIT_TIME_OUT, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS).ignoring(StaleElementReferenceException.class);

	}

	@Test
	public void testApp() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String startTime = null;

		for (int tcid = 2; tcid <= core.getRowCount("Suite1"); tcid++) {
			System.out.println(tcid);
			if (core.getCellData("Suite1", "Runmode", tcid).equalsIgnoreCase("Y")) {
				currentTest = core.getCellData("Suite1", "TCID", tcid);
				// loop again - rows in test data
				int totalSets = testdata.getRowCount(currentTest + "1");
				// holds total rows in test data sheet. IF sheet does not exist then 2 by
				// default
				if (totalSets <= 1) {
					totalSets = 2; // run atleast once
				}

				for (testRepeat = 2; testRepeat <= totalSets; testRepeat++) {
					startTime = utils.TestUtil.now("dd.MMMMM.yyyy hh.mm.ss aaa");
					for (int tsid = 2; tsid <= core.getRowCount(currentTest); tsid++) {
						System.out.println(tsid);
						keyword = core.getCellData(currentTest, "Keyword", tsid);
						object = core.getCellData(currentTest, "Object", tsid);
						stepDescription = core.getCellData(currentTest, "Description", tsid);
						proceedOnFail = core.getCellData(currentTest, "ProceedOnFail", tsid);
						data_column_name = core.getCellData(currentTest, "Data_Column_Name", tsid);

						Method method = KeywordsApp.class.getMethod(keyword);
						method.invoke(method);
					}
				}
			}
		}
	}

	@AfterSuite
	public static void endScript() {

		driver.quit();

	}

}
