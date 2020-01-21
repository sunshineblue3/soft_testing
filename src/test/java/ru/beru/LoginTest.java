package ru.beru;

import org.testng.annotations.Test;
import pageobjects.HomePage;

public class LoginTest extends BaseTest {

	@Test(description="Verify login functionality", groups = {"Login"})
	public void validSignIn() {
		System.out.println("Login test started");
		HomePage homePage = new HomePage(driver, wait);
		homePage.goToUrl();
		homePage.signIn(); // Авторизация
		homePage.checkLogin("testBeruRu@yandex.ru"); // Проверка, что после входа отображается логин
		homePage.checkButtonText(); // Проверка, что кнопка "Войти в аккаунт" изменилась на "Мой профиль"
		System.out.println("Login test finished");
	}	
	
}
