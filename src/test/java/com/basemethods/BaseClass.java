package com.basemethods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
	Select s;
	public static WebDriver driver;
	static TakesScreenshot ts;
	static File screenshotAs;
	JavascriptExecutor js ;
	Actions a;
	static Point p;
	static Dimension d;
	Alert alert;
	WebDriver frame;
	
	
	public static void driverInitiate(String browser) {
		switch (browser) {
		case "chrome":
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
			break;
			
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			break;
			
		default:
			System.out.println("invalid browser name");
			break;
		}
	}
	
	public static void waitFor() {
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);

	}
	public static void maxWindow() {
		driver.manage().window().maximize();
	}
	
	public static void clearField(WebElement ele) {
		ele.clear();
	}
	public static void windowActions(String action, int x, int y) {
		switch (action) {
		case "position":
			p = new Point(x,y);
			driver.manage().window().setPosition(p);
			break;
		case "dimension":
			d = new Dimension(x, y);
			driver.manage().window().setSize(d);
			break;
		default:
			System.out.println("Invalid Window Operation");
			break;
		}
	}
	
	public static void closeBroswer() {
		driver.close();
	}
	
	public static void launchUrl(String url) throws InterruptedException {
		driver.get(url);
		Thread.sleep(3000);
	}
	
	public void gotoWebpage(String url) throws InterruptedException {
		driver.navigate().to(url);
		Thread.sleep(3000);
	}
	
	public String currentPage() {
		String currentUrl = driver.getCurrentUrl();
		return currentUrl;
	}
	
	public String pageTitle() {
		String title = driver.getTitle();
		return title;
	}
	
	public void elementClick(WebElement ele) {
		ele.click();
	}
	
	public String pageSource() {
		String pageSource = driver.getPageSource();
		return pageSource;
	}
	
	public String text(WebElement ele) {
		String text = ele.getText();
		return text;
	}
	
	public String attributeValue(WebElement ele, String name) {
		String attribute = ele.getAttribute(name);
		return attribute;
	}
	
	public void direct(String direction) throws InterruptedException {
		if(direction=="forward") {
			driver.navigate().forward();
			Thread.sleep(3000);
		}
		else if(direction=="backward") {
			driver.navigate().back();
			Thread.sleep(3000);
		}
		else if(direction=="refresh") {
			driver.navigate().refresh();
			Thread.sleep(3000);
		}
	}
	
	public WebElement findElement(String locator, String value) {
		WebElement element = null;
		if(locator=="id") {
			element = driver.findElement(By.id(value));
		}
		else if(locator=="tagname") {
			element = driver.findElement(By.tagName(value));
		}
		else if(locator=="classname") {
			element = driver.findElement(By.className(value));
		}
		else if(locator=="cssSelector") {
			element = driver.findElement(By.cssSelector(value));
		}
		else if(locator=="xpath") {
			element = driver.findElement(By.xpath(value));
		}
		return element;
	}
	
	public List<WebElement> findElements(String locator, String value) {
		List<WebElement> elements = null;
		if(locator=="id") {
			elements = driver.findElements(By.id(value));
		}
		else if(locator=="tagname") {
			elements = driver.findElements(By.tagName(value));
		}
		else if(locator=="classname") {
			elements = driver.findElements(By.className(value));
		}
		else if(locator=="cssSelector") {
			elements = driver.findElements(By.cssSelector(value));
		}
		else if(locator=="xpath") {
			elements = driver.findElements(By.xpath(value));
		}
		return elements;

	}
	
	public void passText(WebElement field, String text) {
		field.sendKeys(text);
	}
	
	public static void capture(File string) throws IOException, WebDriverException {
		ts = (TakesScreenshot) driver;
		screenshotAs = ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(screenshotAs, string);
	}
	
	public void scroll(String direction, WebElement element) {
		js = (JavascriptExecutor)driver;
		if (direction=="down") {
			js.executeScript("arguments[0].scrollIntoView(true)", element);
		}
		else {
			js.executeScript("arguments[0].scrollIntoView(false)", element);
		}	
	}
	
	public void jsActions(String action, WebElement element) {
		js = (JavascriptExecutor)driver;
		if(action=="text") {
			js.executeScript("arguments[0].setAttribute('value','text')", element);
		}
		else if(action=="click") {
			js.executeScript("arguments[0].click()", element);
		}
	}
		
	public boolean dropDownType(WebElement element) {
		s = new Select(element);
		boolean multiple = s.isMultiple();
		return multiple;
	}
	
	public List<WebElement> dropDownOptions(WebElement element) {
		s = new Select(element);
		List<WebElement> options = s.getOptions();
		return options;
	}
	
	public void dropDownSelectOption(WebElement element, String type, String value) {
		s = new Select(element);
		switch (type) {
		case "index":
			s.selectByIndex(Integer.parseInt(value));
			break;
		case "value":
			s.selectByValue(value);
			break;
		case "text":
			s.selectByVisibleText(value);
			break;
		default:
			System.out.println("Invalid Selector");
			break;
		}

	}
	
	public WebElement dropDownFirstSelectedOption(WebElement element) {
		s = new Select(element);
		WebElement option = s.getFirstSelectedOption();
		return option;

	}
	
	public List<WebElement> dropDownSelectedOptions(WebElement element) {
		s = new Select(element);
		List<WebElement> options = s.getAllSelectedOptions();
		return options;
	}

	public void dropDownDeselect(WebElement element, String type, String value) {
		s = new Select(element);
		switch (type) {
		case "index":
			s.deselectByIndex(Integer.parseInt(value));
			break;
		case "value":
			s.deselectByValue(value);
			break;
		case "text":
			s.deselectByVisibleText(value);
			break;
		case "all":
			s.deselectAll();
			break;
		default:
			System.out.println("Invalid Selector");
			break;
		}
	}
	
	public void mouseActions(WebElement ele, String action) {
		a = new Actions(driver);
		switch (action) {
		case "hover":
			a.moveToElement(ele).perform();
			break;
		case "click":
			a.click().perform();
			break;
		case "double click":
			a.doubleClick().perform();
			break;
		case "double click on":
			a.doubleClick(ele).perform();
			break;
		case "right click":
			a.contextClick().perform();
			break;
		case "right click on":
			a.contextClick(ele).perform();
			break;
		default:
			System.out.println("Invalid Mouse Operation");
			break;
		}
	}
	
	public void dragAndDrop(WebElement src, WebElement tgt) {
		a = new Actions(driver);
		a.dragAndDrop(src, tgt).perform();
	}
	
	public void gotoAlert() {
		alert = driver.switchTo().alert();
	}
	
	public void alertAccept() {
		alert.accept();
	}
	
	public void alertCancel() {
		alert.dismiss();
	}
	
	public String alertText() {
		String text = alert.getText();
		return text;
	}
	
	public void alertPassText(String text) {
		alert.sendKeys(text);
	}
	
	public WebDriver jumpFrame(int index) {
		frame = driver.switchTo().frame(index);
		return frame;
	}
	public WebDriver jumpFrame(String name) {
		frame = driver.switchTo().frame(name);
		return frame;
	}
	public WebDriver jumpFrame(WebElement ele) {
		frame = driver.switchTo().frame(ele);
		return frame;
	}
	
	public WebDriver previousFrame() {
		frame = driver.switchTo().parentFrame();
		return frame;
	}
	public WebDriver frameToPage() {
		frame = driver.switchTo().defaultContent();
		return frame;
	}
	public String getCellData(String sheetName, int rowNum, int cellNum) throws IOException {
		File f = new File("C:\\Users\\NITHIN RAJ E\\eclipse-workspace\\MavenTrial\\Project Files\\Book1.xlsx");
		FileInputStream input = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		
		CellType cellType = cell.getCellType();
		String value;
		if (cellType==cellType.STRING) {
			value = cell.getStringCellValue();
			return value;
		}
		else if(cellType==cellType.NUMERIC) {
	
			double c = cell.getNumericCellValue();
			long l = (long)c;
			value = String.valueOf(l);
		}
		else {
			Date date = cell.getDateCellValue();
			SimpleDateFormat format = new SimpleDateFormat("DD/MM/YYYY");
			String formatDate = format.format(date);
			value = String.valueOf(formatDate);
		}
		return value;
	}
	
	public void passCellValue(String sheetName, int rowNum, int cellNum, String setValue) throws IOException {
		File f = new File("C:\\Users\\NITHIN RAJ E\\eclipse-workspace\\MavenTrial\\Project Files\\Book1.xlsx");
		FileInputStream input = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(input);
		Sheet sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.createCell(cellNum);
		cell.setCellValue(setValue);
		
		FileOutputStream output = new FileOutputStream(f);
		wb.write(output);
		wb.close();
		
	}
	
	public String readProperty(String key) throws IOException {
		File f = new File("C:\\Users\\NITHIN RAJ E\\eclipse-workspace\\AdactinTestNG\\src\\test\\resources\\personalData.property");
		FileReader read = new FileReader(f);
		Properties p = new Properties();
		p.load(read);
		String string = p.get(key).toString();
		return string;
	}
	
}
