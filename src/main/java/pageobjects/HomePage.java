package pageobjects;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.qameta.allure.Step;
import pageobjects.SettingsPage;

import pageobjects.BasePage;
import pageobjects.SignInPage;
import pageobjects.SearchPage;

public class HomePage extends BasePage {

    // Локатор для кнопки "Войти в аккаунт"
    private By signInBtn = By.className("_3odNv2Dw2n");
    // Локатор для e-mail во всплывающем окне "Мой профиль"
    private By loginBy = By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div/div/div/div/div[3]/div[2]/div/div[3]/div[1]/div/div/div[1]/div[2]/div/div/div[1]/div/div[2]/div[2]/span");
    // Локатор для отмены всплывающего окна с рекламой
    private By cancelBy = By.xpath("//div[@class='modal__cell']/div/div");
    // Локатор для выхода
    private By logOut = By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div/div/div/div/div[3]/div[2]/div/div[3]/div[1]/div/div/div[1]/div[2]/div/div/ul[4]/ul/li[2]/a");

    // Локаторы для второго теста
    // Ссылка на изменение города
    private By cityLinkBy = By.xpath("//span//span[@data-auto='region-form-opener']");
    // Поле ввода нового города
    private By inputCityBy = By.xpath("/html/body/div[10]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/input");
    private By cityDelete = By.xpath("/html/body/div[10]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/div/div/div/div/div[1]/div/div/div");
    // Список городов при вводе нового города
    private By citiesList = By.xpath("//div[@id='react-autowhatever-region']");
    // Кнопка подтверждения выбора нового города
    private By submitCityBy = By.xpath("/html/body/div[10]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div/div/div[2]/div[2]/button");
    // Категория "Настройки" во всплывающем окне "Мой профиль"
    private By settingsBy = By.xpath("/html/body/div[1]/div[1]/div/div/div/div/div/div/div/div/div[3]/div[2]/div/div[3]/div[1]/div/div/div[1]/div[2]/div/div/ul[3]/ul/li[3]/a");

    // Локаторы для третьего теста
    // Локатор для поискового поля
    private By searchBoxBy = By.id("header-search");
    // Кнопка "Найти" при поиске
    private By searchBtnBy = By.cssSelector("form.header2__search button");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.url = "https://beru.ru/";
    }

    // Метод отмены всплывающего окна с рекламой
    public void cancelWindow() {
        try {
            driver.findElement(cancelBy).click();
        } catch (Exception e) {
            System.out.println("Window not found");
        }
    }

    // Метод, осуществляющий нажатие на кнопку "Войти в аккаунт"
    public SignInPage clickSignInBtn() {
        click(signInBtn);
        return new SignInPage(driver, wait);
    }

    // Метод, проверяющий, что кнопка "Войти в аккаунт" изменилась на "Мой профиль"
    public boolean phraseChange() {
        String str = this.read(signInBtn);
        return str.contains("Мой профиль");
    }

    // Метод, возвращающий значение логина после авторизации
    public String getLogin() {
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signInBtn));
        action.moveToElement(driver.findElement(signInBtn)).perform();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginBy));
        return driver.findElement(loginBy).getText();
    }

    // Метод изменения названия города
    public void changeCity(String newCity) {
        click(cityLinkBy);
        click(inputCityBy);
        click(cityDelete);
        wait.until(ExpectedConditions.visibilityOfElementLocated(inputCityBy));
        WebElement inputCity = driver.findElement(inputCityBy);
        inputCity.sendKeys(newCity);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(citiesList));
        inputCity.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        //driver.findElement(submitCityBy).submit();
        click(submitCityBy);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(inputCityBy));
    }

    // Метод, возвращающий текущий город
    public String getCity() {
        return driver.findElement(cityLinkBy).getText();
    }

    // Переход к странице "Настройки"
    public SettingsPage goToSettings() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signInBtn));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(signInBtn)).perform();
        click(settingsBy);

        ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
        if (handles.size() == 2) {
            driver.switchTo().window(handles.get(1));
        }
        return new SettingsPage(driver, wait);
       }

    // Метод поиска товара
    public SearchPage search (String searchValue) {
           enterText(searchBoxBy, searchValue);
           click(searchBtnBy);
           return new SearchPage(driver, wait);
    }

    public void logout() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(signInBtn));
        if (driver.findElement(signInBtn).getText().contains("Мой профиль")) {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(signInBtn)).perform();
            click(logOut);
        }
    }

    @Step("Sign in")
    public void signIn() {
        SignInPage signInPage = clickSignInBtn();
        signInPage.signIn("testBeruRu@yandex.ru", "kulikandlake");
    }

    @Step("Verify that login is displayed on the main page")
    public void checkLogin(String login) {
        Assert.assertEquals(getLogin(), login);
    }

    @Step("Checking that the \"Login to Account\" button has changed to \"My Profile\"")
    public void checkButtonText() {
        Assert.assertTrue(phraseChange());
    }

    @Step("Check that the city name has changed")
    public void checkCityHasChanged(String newCity) {
        Assert.assertEquals(getCity().trim(), newCity.trim(), "City name hasn't changed");
    }

    @Step("Check that after authorization the city name in the upper corner and the city of delivery match")
    public void compareCityNames() {
        // Сравнение значения города в верхнем углу и города доставки
        Assert.assertEquals(goToSettings().getDeliveryCity().trim(), getCity().trim(),
                "Delivery city and current city doesn't match");
    }
}
