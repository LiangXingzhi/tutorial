package auto.test.selenium;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StrSubstitutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Browser {

	static final String ACTION_GET = "get";
	static final String ACTION_REFRESH = "refresh";
	static final String ACTION_DOUBLECLICK = "doubleClick";
	static final String ACTION_CLICK = "click";
	static final String ACTION_EVAL = "eval";
	static final String BY_XPATH = "xpath";
	static final String BY_CLASSNAME = "className";
	static final String BY_CSSSELECTOR = "cssSelector";

	static Logger logger = LoggerFactory.getLogger(Browser.class);
	private WebDriver driver;
	private Map<String, String> context;

	private int timeout;
	private int wait;

	public Browser(String driverType, int wait, int timeout) {
		this.timeout = timeout;
		this.wait = wait;
		driver = null;
		switch (driverType) {
		case "gecko":
			// FirefoxProfile firefoxProfile = new FirefoxProfile();
			// firefoxProfile.setPreference("network.proxy.type",1);
			// firefoxProfile.setPreference("network.proxy.http",yourProxy);
			// firefoxProfile.setPreference("network.proxy.http_port",yourPort);
			// firefoxProfile.setPreference("network.proxy.no_proxies_on","");
			driver = new FirefoxDriver();
			break;
		case "chrome":
			driver = new ChromeDriver();
			break;
		default:
			driver = new HtmlUnitDriver(true);
			break;
		}
		context = new HashMap<>();
	}

	public void waitBeforeAction() {
		try {
			Thread.sleep(wait * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			logger.error("sleep exception, ", e);
		}
	}

	public void get(String url) {
		waitBeforeAction();
		// driver.get("http://admin:admin123@localhost:8088");
		// driver.get("http://vnfuser:vnfuser@http://10.218.252.249/index.html#workflows");
		logger.info("goto url : " + url);
		driver.get(url);
	}

	public String title() {
		return driver.getTitle();
	}

	public void close() {
		waitBeforeAction();
		driver.navigate().refresh();
		waitBeforeAction();
		driver.quit();
	}

	public void refresh() {
		waitBeforeAction();
		driver.navigate().refresh();
	}

	public void doubleClick(By byCondition) {
		waitBeforeAction();
		logger.info("By: " + byCondition.toString());
		logger.info("wiat for condition start...");
		wait(byCondition);
		logger.info("wiat for condition end");
		Actions action = new Actions(driver);
		logger.info("Perform double click action start...");
		// XPath : //div[contains(text(), "Click here")]
		action.moveToElement(driver.findElement(byCondition)).doubleClick().perform();
		logger.info("Perform double click action end.");
	}

	public void click(By byCondition) {
		waitBeforeAction();
		logger.info("By: " + byCondition.toString());
		logger.info("wiat for condition start...");
		wait(byCondition);
		logger.info("wiat for condition end");

		logger.info("Perform click action start...");
		// Actions action = new Actions(driver);

		WebElement targetElement = this.driver.findElement(byCondition);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", targetElement);

		// XPath : //div[contains(text(), "Click here")]
		// action.moveToElement(driver.findElement(byCondition)).click().perform();

		logger.info("Perform click action end.");
	}

	public void wait(By byCondition) {
		WebDriverWait wait = new WebDriverWait(driver, getTimeout());
		WebElement until = wait.until(ExpectedConditions.visibilityOfElementLocated(byCondition));
	}

	public String eval(By byCondition, String defaultValue) {
		waitBeforeAction();
		logger.info("By: " + byCondition.toString());
		logger.info("wiat for condition start...");
		wait(byCondition);
		logger.info("wiat for condition end");
		logger.info("Perform eval action start...");
		WebElement element = driver.findElement(byCondition);
		if (StringUtils.isEmpty(element.getAttribute("value"))) {
			System.out.println("use default value: " + defaultValue);
			element.sendKeys(defaultValue);
		}
		String value = element.getAttribute("value");
		logger.info("Perform eval action end with value " + value + ".");
		return value;
	}

	public boolean execute(OperationStep operationStep) {
		logger.info("step [" + operationStep.getStepName() + "] start...");
		boolean result = false;
		// process by condition
		String by = operationStep.getByType();
		String target = operationStep.getByCondition().replace("{{", "${").replace("}}", "}");
		if (target.matches(".*\\$\\{.*\\}.*")) {
			target = StrSubstitutor.replace(target, context);
		}
		By byCondition = null;
		switch (by) {
		case BY_XPATH:
			byCondition = By.xpath(target);
			break;
		case BY_CLASSNAME:
			byCondition = By.className(target);
			break;
		case BY_CSSSELECTOR:
			byCondition = By.cssSelector(target);
			break;
		}
		// process value
		String value = operationStep.getStepValue();

		// process action
		String action = operationStep.getOperationType();

		if (ACTION_GET.equals(action)) {
			get(target);
			result = true;
		} else if (ACTION_REFRESH.equals(action)) {
			refresh();
			result = true;
		} else if (ACTION_CLICK.equals(action)) {
			click(byCondition);
			result = true;
		} else if (ACTION_DOUBLECLICK.equals(action)) {
			doubleClick(byCondition);
			result = true;
		} else if (ACTION_EVAL.equals(action)) {
			String resultValue = eval(byCondition, value);
			context.put(operationStep.getStepName(), resultValue);
			result = true;
		}
		logger.info("step [" + operationStep.getStepName() + "] end, result is " + result);
		return result;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public Map<String, String> getContext() {
		return context;
	}

	public void setContext(Map<String, String> context) {
		this.context = context;
	}

	// public static void main(String[] args) {
	// Map<String, String> context = new HashMap<>();
	// context.put("eval_instanceName", "eval_instanceName");
	// String s = "//button[contains(text(),'{{eval_instanceName}}')]";
	//
	// String action = s.replace("{{", "${").replace("}}", "}");
	// if (action.matches(".*\\$\\{.*\\}.*")) {
	// action = StrSubstitutor.replace(action, context);
	// }
	//
	// if(action.matches(".*\\$\\{.*\\}.*")) {
	// action = StrSubstitutor.replace(action, context);
	// }
	// System.out.println(action);
	// }

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getWait() {
		return wait;
	}

	public void setWait(int wait) {
		this.wait = wait;
	}

}
