import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.awt.event.KeyEvent;

public class main {
    public static void main(String[] args) throws InterruptedException, IOException {
        new MerunasDriver();
    }
}

class MerunasDriver {
    WebDriver driver;

    public MerunasDriver() throws InterruptedException, IOException {
        // To disable notification permission popups
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        System.setProperty("webdriver.chrome.driver", "/Users/merunas/Documents/chromedriver");
        driver = new ChromeDriver(options);

        mediumArticleExtractor();
    }

    void mediumArticleExtractor() throws InterruptedException, IOException {
        driver.get("https://medium.com/@samajammin/how-to-interact-with-ethereums-mainnet-in-a-development-environment-with-ganache-3d8649df0876");
        try {
            Robot robot = new Robot();
            robot.mouseMove(500, 200);
            WebElement img = driver.findElement(By.cssSelector("img"));
            String logoSrc = img.getAttribute("src");
            driver.get(logoSrc);
            Thread.sleep(1000);

            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_META);
            Thread.sleep(10000);
        } catch (AWTException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Done");
            driver.close();
        }
    }

    void reddit() throws InterruptedException {
        driver.get("https://reddit.com");
        try {
            WebElement loginButton = driver.findElement(By.xpath("//header//a[text()=\"log in\"]"));
            Thread.sleep(1000);
            loginButton.click();
        } finally {
            System.out.println("Done");
            driver.close();
        }
    }

    void tinder() throws InterruptedException {
        driver.get("https://tinder.com/app/recs");
        try {
            List<WebElement> matches = driver.findElements(By.cssSelector(".matchListItem"));
            Thread.sleep(1000);
            for (int i = 0; i < matches.size(); i++) {
                System.out.println(matches.get(i));
            }
        } finally {
            Thread.sleep(5000);
            System.out.println("Done");
//            driver.close();
        }
    }
}