package com.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.basemethods.BaseClass;

public class LoginPage extends BaseClass {
		
		public LoginPage() {
			PageFactory.initElements(driver, this);
		}
		
		@FindBy(id="username")
		private WebElement username;
		
		@FindBy(id="password")
		private WebElement password;

		@FindBy(id="login")
		private WebElement loginBtn;
		
		public WebElement getUsername() {
			return username;
		}

		public WebElement getPassword() {
			return password;
		}
		
		public WebElement getLoginBtn() {
			return loginBtn;
		}
		
	
}
