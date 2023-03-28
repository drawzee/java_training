package stqa.addressbook.appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Browser;

import java.time.Duration;

public class AppManager {

    public WebDriver wd;

    public SessionHelper sessionHelper;
    private NavigationHelper navigationHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private HelperBase baseHelper;
    private Browser browser;

    public AppManager(Browser browser) {
        this.browser = browser;
    }

    public void init() {
        if (browser.equals(Browser.FIREFOX)) {
            wd = new FirefoxDriver();
        } else if (browser.equals(Browser.CHROME)) {
            wd = new ChromeDriver();
        } else if (browser.equals(Browser.EDGE)) {
            wd = new EdgeDriver();
        }
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        sessionHelper = new SessionHelper(wd);
        groupHelper = new GroupHelper(wd);
        contactHelper = new ContactHelper(wd);
        navigationHelper = new NavigationHelper(wd);
        baseHelper = new HelperBase(wd);
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

}
