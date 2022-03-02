package anna.iAnnaOMG.listeners;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TikTokDL {

    public static void download(String url) {
        WebDriver driver = GlobalDriver.getWebDriver();
        downloadFromElement(url, driver);
    }

    private static void downloadFromElement(String video, WebDriver driver) {
        String url = "https://dltik.com/";

        try {
            driver.get(url);
            driver.findElement(By.id("txt-input-url")).click();
            driver.findElement(By.id("txt-input-url")).sendKeys(video);
            System.out.println("Input URL");
            driver.findElement(By.id("btn-submit-link")).click();
            Thread.sleep(3000);
            driver.findElement(By.xpath("//a[span='Download Server 1']")).click();
            System.out.println("Downloading");
        } catch (Exception e){
            e.printStackTrace();
        }
        driver.quit();
    }
}
