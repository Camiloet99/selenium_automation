package com.camiloecheverri;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ChallengesTests {

    private static final String USER_TEST_FIRSTNAME = "CAMILO";
    private static final String USER_TEST_LASTNAME = "ECHEVERRI";

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 15;

    private WebDriver driver;

    public static String generateRandomText() {
        StringBuilder randomText = new StringBuilder(LENGTH);
        Random random = new Random();

        for (int i = 0; i < LENGTH; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomText.append(CHARACTERS.charAt(index));
        }

        return randomText.toString();
    }

    @BeforeEach
    public void beforeAll() {
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    public void testChallenge1() {

        //ARRANGE
        driver.get("https://teststore.automationtesting.co.uk/index.php");

        //ACT
        driver.findElement(By.xpath("//nav//div[@id='_desktop_user_info']//div[@class='user-info']")).click();
        driver.findElement(By.xpath("//section[@id='main']//div[@id='content']//div[@class='no-account']//a")).click();
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-id_gender-1']")).click();
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-firstname']")).sendKeys(USER_TEST_FIRSTNAME);
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-lastname']")).sendKeys(USER_TEST_LASTNAME);
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-email']")).sendKeys(generateRandomText() + "@gmail.com");
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-password']")).sendKeys("testpassword123*");
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@id='field-birthday']\n")).sendKeys("12/12/2020");
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//input[@name='psgdpr']")).click();
        driver.findElement(By.xpath("//div[@id='content']//section[@class='register-form']//button[contains(text(),'Save')]")).click();
        WebElement userRegistered = driver.findElement(By.xpath("//div[@class='col-md-7 right-nav']//a[@class='account']/span"));

        // Test

        assertTrue(userRegistered.getText().contains(USER_TEST_FIRSTNAME) && userRegistered.getText().contains(USER_TEST_LASTNAME));

        driver.close();
    }

    @Test
    public void extraChallenge1() {
        driver.get("https://automationtesting.co.uk/iframes.html");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        //ARRANGE

        WebElement iframeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//iframe[@src='index.html']")));
        driver.switchTo().frame(iframeElement);

        WebElement toggleSidebarLink = driver.findElement(By.xpath("//div[@id='sidebar']//a[@href='#sidebar']"));
        toggleSidebarLink.click();

        WebElement accordionLink = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='sidebar']//a[@href='accordion.html']")));
        accordionLink.click();

        driver.switchTo().defaultContent();

        WebElement sidebarDefaultContent = driver.findElement(By.xpath("//div[@id='sidebar']//a[@href='#sidebar']"));
        sidebarDefaultContent.click();

        driver.close();
    }

    @Test
    public void extraChallenge2() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get("https://automationtesting.co.uk/popups.html");

        //ARRANGE
        driver.findElement(By.xpath("//div[@id='main']//button[contains(text(), 'Trigger Alert')]")).click();

        wait.until(ExpectedConditions.alertIsPresent());

        Alert alert = driver.switchTo().alert();
        alert.accept();

        driver.close();
    }

}
