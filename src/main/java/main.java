import org.openqa.selenium.*;
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
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\merun\\Documents\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver(options);

        mediumArticleExtractor();
    }

    void mediumArticleExtractor() throws InterruptedException, IOException {
        driver.get("https://medium.com/@samajammin/how-to-interact-with-ethereums-mainnet-in-a-development-environment-with-ganache-3d8649df0876");
        try {
            // 1 Extract title
            WebElement titleElement = driver.findElement(By.cssSelector("h1"));
            String title = titleElement.getText();
            System.out.println("Title " + title);
            Thread.sleep(1000);

            // 2 Extract subtitle
            JavascriptExecutor js = (JavascriptExecutor) driver;
            String subtitle = js.executeScript("return document.querySelector('h1').parentNode.parentNode.querySelector('p').textContent").toString();
            System.out.println("Subtitle " + subtitle);
            Thread.sleep(1000);

            // 3 Resize images
            System.out.println("Resizing images...");
            String resizeImagesScript = String.join("\n",
                "document.querySelectorAll('noscript').forEach(noscript => noscript.remove())",
                    "document.querySelectorAll('img').forEach(img => {",
                        "if (!img.hasAttribute('src') || img.hasAttribute('srcSet') || img.hasAttribute('srcset') || img.hasAttribute('sizes')) {",
                            "img.remove()",
                        "} else {",
                            "img.src = img.src.replace(/(https:\\/\\/miro\\.medium\\.com\\/max\\/).*\\/(.*)/, '$11280/$2').replace('?q=20', '')",
                            "img.removeAttribute('width')",
                            "img.removeAttribute('height')",
                        "}",
                    "})",
                    "window.scrollTo(0, 0)"
                );
            js.executeScript(resizeImagesScript);
            System.out.println("Image resizing complete");
            Thread.sleep(1000);

            // 4 Extract content
            System.out.println("Extracting content...");
            WebElement container = driver.findElement(By.xpath("//p/../.."));
            System.out.println("Container content -> " + container.getAttribute("innerHTML"));
            String contentHTML = container.getAttribute("innerHTML");
            String filteredContentHTML = js.executeScript("return arguments[0].replace(/(<div.+?>)(.*?)(<p )/, '$1$3')", contentHTML).toString();
            System.out.println("Filtered -> " + filteredContentHTML);
            System.out.println("Done extracting article content");

            // 5 Save image
            System.out.println("Saving image...");
            List<WebElement> imgs = driver.findElements(By.cssSelector("img"));
            String logoSrc = imgs.get(4).getAttribute("src");
            saveImage(logoSrc);
            System.out.println("Image saving complete");
            Thread.sleep(5000);
        } catch (Exception e) {
            System.out.println("Exception " + e);
        } finally {
            System.out.println("Done");
            driver.close();
        }
    }

    void saveImage(String url) throws InterruptedException, IOException {
        try {
            driver.get(url);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            Thread.sleep(10000);
        } catch (AWTException e) {
            e.printStackTrace();
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