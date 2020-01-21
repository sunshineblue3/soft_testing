package pageobjects;

import io.qameta.allure.Step;
import utils.ScreenshotMaker;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pageobjects.PurchasePage;

import java.util.List;

public class SearchPage extends BasePage {
	
	// Локатор для поля ввода нижней границы цены
	private By priceFromBy = By.cssSelector("[data-auto='filter-range-min'] input");
	// Локатор для поля ввода верхней границы цены
	private By priceToBy = By.cssSelector("[data-auto='filter-range-max'] input");
	// Div товаров
	private By productsBy = By.cssSelector("[data-tid = '2bd11028 6c57eda1']");
	// Цена товара
	private By priceBy = By.cssSelector("._1u3j_pk1db span [data-tid='c3eaad93']");
	// Кнопка добавления товара в корзину
	private By buyBy = By.cssSelector("._1qP8FdsgOD button");
	// Ссылка на переход к корзине
	private By goToBasketBy = By.cssSelector("[data-auto='executed-cart-button']");
	// Кнопка "Показать еще"
	private By byShowMoreBtn = By.cssSelector("body > div:nth-child(1) > div:nth-child(5) > div > div > div > div > div._3GNaczqaFf._1zYszmgEzn > div > div:nth-child(6) > div > div > div > div > div > div:nth-child(1) > button");
	// Спиннер 
	private By bySpinner = By.cssSelector("div.spin.spin_js_inited.spin_progress_yes");
	// Сообщение 'Найдено n щеток'
	private By byFind = By.cssSelector("[data-auto='tooltip__content'] span span");

	SearchPage(WebDriver driver, WebDriverWait wait) {
		super(driver, wait);
	}

	public void setPriceFrom(String priceFrom) {
		enterText(priceFromBy, priceFrom);
	}
	
	public void setPriceTo(String priceTo) {
		enterText(priceToBy, priceTo);
	}
	
	public double getPriceFrom() {
		return Double.parseDouble(this.getValue(priceFromBy));
	}
	
	public double getPriceTo() {
		return Double.parseDouble(this.getValue(priceToBy));
	}
	
	public List<WebElement> getProductList() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(productsBy));
		return driver.findElements(productsBy);
	}
		
	
	public boolean checkPrice() {
		wait.until(ExpectedConditions.visibilityOfElementLocated(byFind));
		// click on button Show more
		try {
			WebElement ShowMore = driver.findElement(byShowMoreBtn);
			click(byShowMoreBtn);
		} catch(Exception ex) {System.out.println("No show more");};
		double from = getPriceFrom();
		double to = getPriceTo();	
		List<WebElement> productList = getProductList();
		for (WebElement element : productList) {
			double cost = Double.parseDouble(element.findElement(priceBy).getText().replaceAll("\\s",""));
			//System.out.println(cost);
			if ((cost > to) || (cost < from)) {
				return false;
			}
		}
		return true;
	}
	
	public Boolean buyProduct() {	
		List<WebElement> productList = this.getProductList();
		if (productList.size() <= 1) {
			System.out.println(productList.size());
			return false;
		}
		int i = productList.size() - (productList.size() - 2);
		WebElement buyBtn = productList.get(i).findElement(buyBy);
		scrollTo(buyBtn);
		click(buyBtn);
		wait.until(ExpectedConditions.visibilityOfElementLocated(goToBasketBy));
		wait.until(ExpectedConditions.elementToBeClickable(goToBasketBy));
		click(goToBasketBy);
		return true;
	}

	@Step("Input price limits")
	public void inputPriceLimits() {
		setPriceFrom("999");
		setPriceTo("1999");
	}
	
	// Проверка того, что отображаются щетки с ценами в заданном диапазоне
	@Step("Check that brushes are displayed with prices in the specified range")
	public void checkPricesInRange() {
		Assert.assertTrue(checkPrice(), "Price doesn't match");
	}
	
	// Проверка того, что список содержит по крайней мере 2 элемента,
	// т.е. существует возможность купить предпоследнюю щетку
	@Step("Check that the list contains at least 2 elements and it possible to buy the last but one")
	public void checkIfCanBuy() {
		Assert.assertTrue(buyProduct(), "List length is not correct");
	}
	
}
