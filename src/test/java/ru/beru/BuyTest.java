package ru.beru;

import org.testng.annotations.Test;

import pageobjects.HomePage;
import pageobjects.PurchasePage;
import pageobjects.SearchPage;
import ru.beru.BaseTest;

public class BuyTest extends BaseTest {

    @Test(description="Check buy product functionality", groups = {"Search"})
    public void buyElem() {
        HomePage homePage = new HomePage(driver, wait);
        homePage.goToUrl();
        SearchPage searchPage = homePage.search("Электрические зубные щетки");
        searchPage.inputPriceLimits(); //ввод диапазона цен
        searchPage.checkPricesInRange(); //проверка попадания цен результатов поиска в хаданный диапазон
        searchPage.checkIfCanBuy(); //проверка, что результатов больше одного --> кидаем в корзину предпоследний товар
        PurchasePage purchasePage = new PurchasePage(driver, wait);
        purchasePage.checkToFreeDelivery(); //проверка, бесплатна ли доставка, если нет - возвращает сумму до бесплатной доставки
        purchasePage.getPrices(); //получение суммы скидки
        purchasePage.checkPrice(purchasePage.productPrice,
                purchasePage.deliveryPrice, purchasePage.discount); //проверка корректности сумм
        // Увеличение количества щеток
        purchasePage.addProducts("2499");
        purchasePage.checkToFreeDelivery();
        purchasePage.getPrices();
        purchasePage.checkPrice(purchasePage.productPrice,
                purchasePage.deliveryPrice, purchasePage.discount);
    }

}
