package anna.iAnnaOMG.listeners;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

public class GlobalDriver {
    public static WebDriver getWebDriver() {
        FirefoxProfile profile = new FirefoxProfile();
        // Download files to the last known directory
        profile.setPreference("browser.download.folderList",2);
        // Set the last known directory
        profile.setPreference("browser.download.dir", "C:\\Users\\wagee\\Documents\\TikToks\\");
        // Tell the browser to automatically download (skip dialog) for mp4 files
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "video/mp4");
        profile.setPreference("pdfjs.disabled", true);

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(profile);
        // Make the browser invisible
        options.addArguments("-headless");

        // Create and return the webdriver
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\wagee\\Documents\\geckodriver.exe");
        WebDriver driver = new FirefoxDriver(options);
        System.out.println("Firefox Driver created");
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        System.out.println("Returning");
        return driver;
    }
}