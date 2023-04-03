package stqa.mantis.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AppManager {

    private final Properties properties;
    private WebDriver wd;

    private String browser;
    private RegistrationHelper registrationHelper;

    public AppManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        System.setProperty("webdriver.gecko.driver", properties.getProperty("driver"));
    }

    public void stop() {
        if (wd != null) {
            wd.quit();
        }
    }

    public HttpSession newSession() {
        return new HttpSession(this);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public RegistrationHelper registration() {
        if (registrationHelper == null) {
            registrationHelper = new RegistrationHelper(this);
        }
        return registrationHelper;
    }

    public WebDriver getDriver() {
        if (wd == null) {
            switch (browser) {
                case BrowserType.FIREFOX -> wd = new FirefoxDriver();
                case BrowserType.CHROME -> wd = new ChromeDriver();
                case BrowserType.EDGE -> wd = new EdgeDriver();
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            }

            wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            wd.get(properties.getProperty("web.baseUrl"));
        } return wd;
    }

}
