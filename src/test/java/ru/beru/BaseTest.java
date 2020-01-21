package ru.beru;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import pageobjects.HomePage;
import utils.CustomTestListener;

@Listeners(CustomTestListener.class)
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    private static String chromeDriverPath = "src/main/resources/chromedriver.exe";

    public WebDriver getDriver() {
        return driver;
    }

    @BeforeMethod
    public void setup () {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterGroups("Login")
    public void out () {
        if (driver.getCurrentUrl().contains("https://beru.ru/")) {
            HomePage homePage = new HomePage(driver, wait);
            homePage.logout();
        }
        driver.quit();
    }

    @AfterGroups("Search")
    public void teardown () {
        driver.quit();
    }

}