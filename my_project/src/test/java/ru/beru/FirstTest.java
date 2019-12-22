package ru.beru;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTest extends WebdriverSettings{

    @Test
    public void FirstTest() {
        driver.get("https://beru.ru/");
        String title = driver.getTitle();
        Assert.assertTrue(title.equals("Маркетплейс Беру - большой ассортимент товаров из интернет-магазинов с быстрой доставкой и по выгодным ценам"));
    }

}