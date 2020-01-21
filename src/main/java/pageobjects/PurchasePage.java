package pageobjects;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
//import utils.ScreenshotMaker;


public class PurchasePage extends BasePage{

    // Локатор для добавления еще одного продукта (+)
    private By addMoreBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._2bK5pi8G8K._1zYszmgEzn > div > div:nth-child(3) > div > div > div > div > div > div > div > div > div > div > div > div._3iuuBXIa23 > div._3iuuBXIa23._1GqGzm7LjG > div._3iuuBXIa23._1fqzkmCCu3 > div > div > div > div > div > button._4qhIn2-ESi._2sJs248D-A._18c2gUxCdP._3hWhO4rvmA");
    // Сумма до бесплатной доставки
    private By freeDeliveryBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div._2bi8z0hv1H._3fK_DbJKvJ > div._3AlSA6AOKL > div._3F8LPwxb3s > div > div > div._3yDgi6ylNe > span > span");
    // Значение поля "Товары"
    private By productCostBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div._2bi8z0hv1H._3fK_DbJKvJ > div._3AlSA6AOKL > div:nth-child(3) > div > div:nth-child(2) > span:nth-child(2)");
    // Значение поля "Доставка"
    private By deliveryCostBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div._2bi8z0hv1H._3fK_DbJKvJ > div._3AlSA6AOKL > div._3F8LPwxb3s > div > div > div._3yDgi6ylNe > span");
    //  Значение поля "Скидка на товары"
    private By discountBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div._2bi8z0hv1H._3fK_DbJKvJ > div._3AlSA6AOKL > div:nth-child(3) > div > div:nth-child(3) > span._3l-uEDOaBN._180wuXkZuy._3HJsMt3YC_");
    // Значение поля "Итого"
    private By priceBy = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div._2bi8z0hv1H._3fK_DbJKvJ > div._3AlSA6AOKL > div:nth-child(3) > div > div._1Q9ASvPbPN._2wL0LFKDDY > span._1oBlNqVHPq");
    // Значение кнопки "Перейти к оформлению"
    private By goTo = By.cssSelector("body > div:nth-child(1) > div > div:nth-child(1) > div:nth-child(3) > div > div > div > div > div > div > div:nth-child(2) > div > div > div > div._3jL0_TN4Nh._1zYszmgEzn > div > div > div > div > div > div > div.bLjj5ddV9I > div > div > div > span > button");
    // Стоимость товара с учетом скидки
    private Double curPrice;
    // Загрузка
    private By byLoader = By.className("A2ZAPkIo1a _1VIlfX-f_O");

    public static double productPrice;
    public static double deliveryPrice;
    public static double discount;

    public PurchasePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // Метод получения цены
    public double getPrice(By by) {
        return getPrice(driver.findElement(by));
    }

    public double getPrice(WebElement elem) {
        String priceStr = read(elem).replaceAll("[^0-9]", "");
        if (priceStr.isEmpty()) {
            return 0;
        } else {
            return Double.parseDouble(priceStr);
        }
    }


    // Проверка того, что итоговая цена равна <стоимость щетки> - <скидка>
    // и сумма до бесплатной доставки равна <2499> - <стоимость щетки> - <скидка>
    public void checkPrice(double productPrice, double deliveryPrice, double discount) {
        curPrice = productPrice - discount;
        //double wholeSum = getPrice(priceBy);
        Assert.assertTrue( getPrice(priceBy) == (curPrice), "Price doesn't correct");
        try {
            driver.findElement(freeDeliveryBy);
            double sum = getPrice(freeDeliveryBy);
            Assert.assertTrue( sum == (2499 - curPrice), "Price doesn't correct help");
        } catch(Exception ex) {System.out.println("No delivery for money");}
    }

    // Метод добавления щеток, необходимых до бесплатной доставки
    public void addProducts(String priceTo) {
        double product =  getPrice(productCostBy);
        double maxPrice = Double.parseDouble(priceTo);
        WebElement plusBtn = driver.findElement(addMoreBy);

        while (curPrice < maxPrice) {
            wait.until(ExpectedConditions.elementToBeClickable(addMoreBy));
            plusBtn.click();
            curPrice += product;
        }
    }

    // Метод, возвращаюший значение до бесплатной доставки
    public double isFreeDelivery() {
        wait.until(ExpectedConditions.elementToBeClickable(goTo));
        if (this.read(deliveryCostBy).contains("Вы получили бесплатную доставку")) {
            return 0;
        } else {
            return getPrice(freeDeliveryBy);
        }
    }

    // Проверка значения "До бесплатной доставки осталось"
    public void checkToFreeDelivery() {
        Assert.assertTrue(0.0 != isFreeDelivery(), "Delivery is already free");
    }

    public void checkIsFreeDelivery() {
        Assert.assertEquals(0.0, isFreeDelivery(), "Delivery doesn't free");
    }

    // Получение суммы скидки
    public void getPrices() {
        productPrice = getPrice(productCostBy);
        deliveryPrice = getPrice(deliveryCostBy);
        discount = 0;
        try {
            WebElement discountExist = driver.findElement(discountBy);
            discount = getPrice(discountExist);
        } catch(Exception ex) {System.out.println("No discount");};
    }

}
