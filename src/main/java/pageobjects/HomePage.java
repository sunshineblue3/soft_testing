package pageobjects;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.qameta.allure.Step;
import pageobjects.SettingsPage;

import pageobjects.BasePage;
import pageobjects.SignInPage;
import pageobjects.SearchPage;

public class HomePage extends BasePage {

    // Локатор для кнопки "Войти в аккаунт"
    private By signInBtn = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div.Mvy4Zvr556 > div:nth-child(1) > div > div > a");
    // Локатор для кнопки "Мой профиль"
    private By myProfile = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div.Mvy4Zvr556 > div:nth-child(1) > div > div > div._3odNv2Dw2n > button");
    // Локатор для e-mail во всплывающем окне "Мой профиль"
    private By loginBy = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div.Mvy4Zvr556 > div:nth-child(1) > div > div > div._3odNv2Dw2n > div._2ubPaMe58x._3ZZzYB8tbn.root_arrow_none._1J8-Ybuzc_ > div > div > div.T3jKK6NbAR > div > div._2SFylIV5m5 > div.QLvVwuf9uh > span");
    // Локатор для выхода
    private By logOut = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div.Mvy4Zvr556 > div:nth-child(1) > div > div > div._3odNv2Dw2n > div._2ubPaMe58x._3ZZzYB8tbn.root_arrow_none._1J8-Ybuzc_ > div > div > ul:nth-child(6) > ul > li:nth-child(2) > a");

    // Локаторы для второго теста
    // Ссылка на изменение города
    private By cityLinkBy = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2cfUiVAU6V > div > div > div > div > div > div > span > span.zB1fta3NQ5");
    // Поле ввода нового города
    private By inputCityBy = By.xpath("*//div[@class='_3Dpt0M8dmP']//input");
    // Кнопка удаления города из поля ввода
    private By cityDelete = By.cssSelector("._8iW7gwBP58 button");
    // Список городов при вводе нового города
    private By citiesList = By.cssSelector("#react-autowhatever-region");
    private By cityWhatINeed = By.cssSelector("#react-autowhatever-region--item-0");
    // Кнопка подтверждения выбора нового города
    private By submitCityBy = By.cssSelector("._4qhIn2-ESi.Pjv3h3YbYr.THqSbzx07u");
    // Категория "Настройки" во всплывающем окне "Мой профиль"
    private By settingsBy = By.cssSelector("[href=\"/my/settings?track=menu\"]");

    // Локаторы для третьего теста
    // Локатор для поискового поля
    private By searchBoxBy = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div._1QpP2zubQ8 > div._3_X3wAW8TQ > div > div > form > div._222GUZq18C > div > span > span > input");
    // Кнопка "Найти" при поиске
    private By searchBtnBy = By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div > div > div > div > div > div > div > div > div._1SEkJje5GJ > div._2BUQxcqKF7 > div > div._1QpP2zubQ8 > div._3_X3wAW8TQ > div > div > form > div._1ADiseGyrF > button");

    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        this.url = "https://beru.ru/";
    }

    // Метод, осуществляющий нажатие на кнопку "Войти в аккаунт"
    public SignInPage clickSignInBtn() {
        click(signInBtn);
        return new SignInPage(driver, wait);
    }

    // Метод, проверяющий, что кнопка "Войти в аккаунт" изменилась на "Мой профиль"
    public boolean phraseChange() {
        String str = this.read(myProfile);
        return str.contains("Мой профиль");
    }

    // Метод, возвращающий значение логина после авторизации
    public String getLogin() {
        Actions action = new Actions(driver);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(myProfile));
        action.moveToElement(driver.findElement(myProfile)).perform();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(loginBy));
        return driver.findElement(loginBy).getText();
    }

    // Метод изменения названия города
    public void changeCity(String newCity) {
        click(cityLinkBy);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inputCityBy));
        WebElement inputCity = driver.findElement(inputCityBy);
        click(inputCity);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityDelete));
        click(driver.findElement(cityDelete));
        inputCity.sendKeys(newCity);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(citiesList));
        //inputCity.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
        wait.until(ExpectedConditions.visibilityOfElementLocated(cityWhatINeed));
        click(cityWhatINeed);
        click(submitCityBy);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(inputCityBy));
    }

    // Метод, возвращающий текущий город
    public String getCity() {
        return driver.findElement(cityLinkBy).getText();
    }

    // Переход к странице "Настройки"
    public SettingsPage goToSettings() {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(myProfile));
        Actions action = new Actions(driver);
        action.moveToElement(driver.findElement(myProfile)).perform();
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(settingsBy));
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
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(myProfile));
        if (driver.findElement(myProfile).getText().contains("Мой профиль")) {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(myProfile)).perform();
            click(logOut);
        }
    }

    // Авторизация
    @Step("Sign in")
    public void signIn() {
        SignInPage signInPage = clickSignInBtn();
        signInPage.signIn("testBeruRu@yandex.ru", "kulikandlake");
    }

    // Проверка, что после входа отображается логин
    @Step("Verify that login is displayed on the main page")
    public void checkLogin(String login) {
        Assert.assertEquals(getLogin(), login, "Check login error");
    }

    // Проверка, что кнопка "Войти в аккаунт" изменилась на "Мой профиль"
    @Step("Checking that the \"Login to Account\" button has changed to \"My Profile\"")
    public void checkButtonText() {
        Assert.assertTrue(phraseChange(), "Check ButtonText \"My Profile\" error");
    }

    // Проверка, что название города изменилось
    @Step("Check that the city name has changed")
    public void checkCityHasChanged(String newCity) {
        Assert.assertEquals(getCity().trim(), newCity.trim(), "City name hasn't changed");
    }

    // Сравнение значения города в верхнем углу и города доставки на странице "Настройки"
    @Step("Check that after authorization the city name in the upper corner and the city of delivery match")
    public void compareCityNames() {
        Assert.assertEquals(goToSettings().getDeliveryCity().trim(), getCity().trim(),
                "Delivery city and current city doesn't match");
    }
}
