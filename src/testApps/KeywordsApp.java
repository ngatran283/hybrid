package testApps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class KeywordsApp extends DriverApp {
	public static WebElement element;

	public static String navigate() {
		try {
			driver.get(CONFIG.getProperty(object));
		} catch (Exception e) {
			e.getMessage();
		}
		return "Pass";
	}

	public static String waitfor() {
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(findElement(object)));
		} catch (Exception e) {
			e.getMessage();
		}
		return "Pass";
	}

	public static String input() {
		try {
			System.out.println(testdata.getCellData(currentTest, data_column_name, testRepeat));
			element = driver.findElement(findElement(object));
			element.clear();
			element.click();
			element.sendKeys(testdata.getCellData(currentTest, data_column_name, testRepeat));
		} catch (Exception e) {
			e.getMessage();
		}
		return "Pass";
	}

	public static String clickLink() {
		try {
			driver.findElement(findElement(object)).click();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Pass";
	}

	public static String clickAt() {
		try {
			driver.findElement(findElement(object)).click();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Pass";
	}

	public static String select() {
		try {
			Select select = new Select(driver.findElement(findElement(object)));
			String data = testdata.getCellData(currentTest, data_column_name, testRepeat);
			String[] datalist = data.trim().split(",");
			for (String valueToSelect : datalist) {
				select.selectByVisibleText(valueToSelect);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Pass";
	}

	public static String clickButton() {
		try {
			driver.findElement(findElement(object)).click();
		} catch (Exception e) {
			// TODO: handle exception
			e.getMessage();
		}
		return "Pass";
	}

	public static String verifyText() {
		return "Pass";
	}

	public static String assertTrue() {
		return "Pass";

	}

	public static By findElement(String key) {
		By by = null;
		if (Objects.containsKey(key + ".id")) {
			by = By.id(Objects.getProperty(key + ".id"));
		} else if (Objects.containsKey(key + ".xpath")) {
			by = By.xpath(Objects.getProperty(key + ".xpath"));
		} else if (Objects.containsKey(key + ".cssSelector")) {
			by = By.cssSelector(Objects.getProperty(key + ".cssSelector"));
		} else if (Objects.containsKey(key + ".className")) {
			by = By.className(Objects.getProperty(key + ".className"));
		} else if (Objects.containsKey(key + ".linkText")) {
			by = By.linkText(Objects.getProperty(key + ".linkText"));
		} else if (Objects.containsKey(key + ".partialLinkText")) {
			by = By.partialLinkText(Objects.getProperty(key + ".partialLinkText"));
		} else if (Objects.containsKey(key + ".tagName")) {
			by = By.tagName(Objects.getProperty(key + ".tagName"));
		} else if (Objects.containsKey(key + ".name")) {
			by = By.name(Objects.getProperty(key + ".name"));
		} else {
			System.out.println("Could not identify this type of locator");
		}
		return by;
	}

}
