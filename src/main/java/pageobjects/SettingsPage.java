package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SettingsPage extends BasePage {
	//	Локатор для получения названия города на странице "Настройки"
    private By cityBy = By.xpath("/html/body/div[1]/div[2]/div/div/div/div/div/div/div/div/div/div[2]/div/div[3]/div/div[2]/a/span");
	
	public SettingsPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}
	
	// Метод, возвращающий город доставки
	public String getDeliveryCity() {
		scrollTo(cityBy);
		if (driver.getWindowHandles().size() > 1) {
			driver.close();
			driver.switchTo().window(driver.getWindowHandle());
		}
		return read(cityBy);
	}
}
