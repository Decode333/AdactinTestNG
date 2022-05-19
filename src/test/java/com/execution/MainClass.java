package com.execution;

import java.io.File;
import java.io.IOException;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.basemethods.BaseClass;
import com.pages.Address;
import com.pages.LoginPage;
import com.pages.OrderID;
import com.pages.SearchHotel;
import com.pages.SelectHotel;

public class MainClass extends BaseClass {
	BaseClass b;

	@Parameters({ "browser", "url" })
	@BeforeClass
	private void launch(String browser, String url) throws InterruptedException {
		driverInitiate(browser);
		launchUrl(url);
		waitFor();
	}

	@AfterClass
	private void lastStep() throws WebDriverException, IOException {
		File f = new File("C:\\Users\\NITHIN RAJ E\\eclipse-workspace\\AdactinTestNG\\test-output\\ss.png");
		capture(f);
		driver.close();
	}

	@Parameters({ "username", "password" })
	@Test(priority = 1)
	private void login(String user, String pass) {
		LoginPage l = new LoginPage();
		l.getUsername().sendKeys(user);
		l.getPassword().sendKeys(pass);
		l.getLoginBtn().click();
	}

	@Parameters({ "droptype", "location", "hotel", "roomtype", "rooms", "checkIn", "checkOut", "adult", "children" })
	@Test(dependsOnMethods = "login")
	private void searchHotel(String droptype, String loc, String hotel, String roomtype, String rooms, String checkIn,
			String checkOut, String adult, String children) {
		b = new BaseClass();
		SearchHotel h = new SearchHotel();
		WebElement location = h.getLocation();
		b.dropDownSelectOption(location, droptype, loc);
		WebElement hotels = h.getHotels();
		b.dropDownSelectOption(hotels, droptype, hotel);
		WebElement roomType = h.getRoomType();
		b.dropDownSelectOption(roomType, droptype, roomtype);
		WebElement noOfRooms = h.getNoOfRooms();
		b.dropDownSelectOption(noOfRooms, droptype, rooms);
		h.getCheckIn().sendKeys(checkIn);
		h.getCheckOut().sendKeys(checkOut);
		WebElement adults = h.getAdults();
		b.dropDownSelectOption(adults, droptype, adult);
		WebElement child = h.getChild();
		b.dropDownSelectOption(child, droptype, children);
		h.getSearch().click();
	}

	@Test(dependsOnMethods = "searchHotel")
	private void selectHotel() {
		SelectHotel h = new SelectHotel();
		h.getHotelBtn().click();
		h.getContinueBtn().click();
	}

	@Test(dependsOnMethods = "selectHotel")
	private void address() throws IOException {
		BaseClass b = new BaseClass();
		Address a = new Address();
		a.getfName().sendKeys(b.readProperty("firstname"));
		a.getlName().sendKeys(b.readProperty("secondname"));
		a.getAddr().sendKeys(b.readProperty("address"));
		a.getCreditCardNo().sendKeys(b.readProperty("creditno"));
		WebElement creditCardType = a.getCreditCardType();
		b.dropDownSelectOption(creditCardType, b.readProperty("droptype"), b.readProperty("creditType"));
		WebElement expiryMonth = a.getExpiryMonth();
		b.dropDownSelectOption(expiryMonth, b.readProperty("droptype"), b.readProperty("month"));
		WebElement expiryYear = a.getExpiryYear();
		b.dropDownSelectOption(expiryYear, b.readProperty("droptype"), b.readProperty("year"));
		a.getCvvNo().sendKeys(b.readProperty("cvv"));
		a.getBookNowBtn().click();
	}
	
	@Test(dependsOnMethods = "selectHotel")
	private void orderID() {
		OrderID o = new OrderID();
		System.out.println(b.attributeValue(o.getOrderNo(), "value"));
	}
	
}
