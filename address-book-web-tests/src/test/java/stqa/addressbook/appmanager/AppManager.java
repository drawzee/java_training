package stqa.addressbook.appmanager;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class AppManager {

    private final Properties properties;
    public WebDriver wd;

    public SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private HelperBase baseHelper;
    private String browser;
    private DbHelper dbHelper;

    public AppManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }

    public void init() throws IOException {
        String target = System.getProperty("target", "local");
        properties.load(new FileReader(new File(String.format("src/test/resources/%s.properties", target))));
        System.setProperty("webdriver.gecko.driver", properties.getProperty("driver"));

        dbHelper = new DbHelper();

        if ("".equals(properties.getProperty("selenium.server"))) {
            switch (browser) {
                case BrowserType.FIREFOX -> wd = new FirefoxDriver();
                case BrowserType.CHROME -> wd = new ChromeDriver();
                case BrowserType.EDGE -> wd = new EdgeDriver();
                default -> throw new IllegalStateException("Unexpected value: " + browser);
            }
        } else {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser);
            wd = new RemoteWebDriver(new URL(properties.getProperty("selenium.server")), capabilities);
        }

        wd.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        wd.get(properties.getProperty("web.baseUrl"));
        sessionHelper = new SessionHelper(wd);
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        baseHelper = new HelperBase(wd);
        session().login(properties.getProperty("web.adminLogin"), properties.getProperty("web.adminPass"));
    }

    public void stop() {
        wd.quit();
    }

    public SessionHelper session() {
        return sessionHelper;
    }

    public GroupHelper group() {
        return groupHelper;
    }

    public ContactHelper contact() {
        return contactHelper;
    }

    public NavigationHelper goTo() {
        return navigationHelper;
    }

    public HelperBase base() {
        return baseHelper;
    }

    public DbHelper db() {
        return dbHelper;
    }

}
