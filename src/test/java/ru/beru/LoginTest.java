package ru.beru;

import io.qameta.allure.Description;
import org.testng.annotations.Test;
import pageobjects.HomePage;

public class LoginTest extends BaseTest {
	
	@Description("Verify login functionality")
	@Test(description="Verify login functionality", groups = {"LoginTest"})
	public void validSignIn() {
		System.out.println("First test");	
		HomePage homePage = new HomePage(driver, wait);
		homePage.goToUrl();
		//homePage.cancelWindow();
		homePage.signIn();
		homePage.checkLogin("testBeruRu@yandex.ru");
		homePage.checkButtonText();
	}	
	
}
