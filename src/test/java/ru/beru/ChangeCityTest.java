package ru.beru;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import pageobjects.HomePage;

public class ChangeCityTest extends BaseTest {

    @DataProvider(name = "getNewCity")
    public Object[][] getData(){
        return new Object[][] {
                {"Хвалынск"},
                {"Самара"},
                {"Энгельс"} };
    }

    @Description("Check Change city functionality")
    @Test(description="Check Change city functionality", groups = {"Login"}, dataProvider = "getNewCity")
    public void changeCity(String newCity) {
        HomePage homePage = new HomePage(driver, wait);
        homePage.goToUrl();
        homePage.changeCity(newCity); // Вызов метода изменения города
        homePage.checkCityHasChanged(newCity); // Проверка, что название города изменилось
        homePage.signIn(); // Авторизация
        homePage.compareCityNames(); // Сравнение значения города в верхнем углу и города доставки на странице "Настройки"
    }

}

