import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Sberbank {
    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://www.sberbank.ru/ru/person");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void sberbank_test_1() {
        WebElement list = driver.findElement(By.className("region-list__arrow"));
        list.click();

        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath
                (".//div[@class = 'region-search-box']//input"))));

        WebElement enterRegion = driver.findElement(By.xpath
                (".//div[@class = 'region-search-box']//input"));
        enterRegion.sendKeys("Нижегородская область");

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath
                (".//span[@class = 'region-search-box__option']//*[contains(text(), 'Нижегородская область')]"))));

        WebElement regionNizh = driver.findElement(By.xpath
                (".//span[@class = 'region-search-box__option']//*[contains(text(), 'Нижегородская область')]"));
        regionNizh.click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath
                (".//span[@class = 'region-list__name']"))));

        WebElement regionName = driver.findElement(By.xpath
                (".//span[@class = 'region-list__name']"));
        String textRegionName = regionName.getText();
        Assert.assertTrue("Регион не равен Нижегородской области",
                textRegionName.contains("Нижегородская область"));

        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",
                driver.findElement(By.className("footer-info")));

        WebElement socialFB = driver.findElement(By.xpath
                (".//a[@href = 'https://www.facebook.com/bankdruzey']/span"));
        String textSocialFB = socialFB.getText();
        Assert.assertTrue("Нет иконки фейсбука", socialFB.getAttribute("class").equals
                ("social__icon social__icon_type_fb"));
    }
}
