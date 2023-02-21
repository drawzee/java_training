package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.time.Duration;

public class AppManager {

    public WebDriver wd;

    public void init() {
        wd = new ChromeDriver();
        wd.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    public void login(String username, String pass) {
        wd.get("http://localhost/addressbook/edit.php");
        wd.findElement(By.name("user")).clear();
        wd.findElement(By.name("user")).sendKeys(username);
        wd.findElement(By.name("pass")).clear();
        wd.findElement(By.name("pass")).sendKeys(pass);
        wd.findElement(By.xpath("//input[@value='Login']")).click();
    }

    public void gotoGroupPage() {
        wd.findElement(By.linkText("groups")).click();
        wd.get("http://localhost/addressbook/group.php");
    }

    public void initGroupCreation() {
        wd.findElement(By.name("new")).click();
    }

    public void fillGroupForm(GroupData groupData) {
        wd.findElement(By.name("group_name")).click();
        wd.findElement(By.name("group_name")).clear();
        wd.findElement(By.name("group_name")).sendKeys(groupData.getName());
        wd.findElement(By.name("group_header")).clear();
        wd.findElement(By.name("group_header")).sendKeys(groupData.getHeader());
        wd.findElement(By.name("group_footer")).clear();
        wd.findElement(By.name("group_footer")).sendKeys(groupData.getFooter());
    }

    public void submitGroupForm() {
        wd.findElement(By.name("submit")).click();
    }

    public void returnToGroupPage() {
        wd.findElement(By.linkText("groups")).click();
    }

    public void logout() {
        wd.findElement(By.linkText("Logout")).click();
    }

    public void stop() {
        wd.quit();
    }

    public boolean isElementPresent(By by) {
        try {
            wd.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public boolean isAlertPresent() {
        try {
            wd.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public void initContactAdding() {
        wd.findElement(By.linkText("add new")).click();
        wd.get("http://localhost/addressbook/edit.php");
    }

    public void fillContactForm(ContactData contactData) {
        wd.findElement(By.name("firstname")).clear();
        wd.findElement(By.name("firstname")).sendKeys(contactData.getFirstname());
        wd.findElement(By.name("lastname")).clear();
        wd.findElement(By.name("lastname")).sendKeys(contactData.getLastname());
        wd.findElement(By.name("company")).clear();
        wd.findElement(By.name("company")).sendKeys(contactData.getCompany());
        wd.findElement(By.name("address")).clear();
        wd.findElement(By.name("address")).sendKeys(contactData.getAddress());
        wd.findElement(By.name("home")).clear();
        wd.findElement(By.name("home")).sendKeys(contactData.getHome());
        wd.findElement(By.name("email")).clear();
        wd.findElement(By.name("email")).sendKeys(contactData.getEmail());
    }

    public void submitContactForm() {
        wd.findElement(By.xpath("//div[@id='content']/form/input[21]")).click();
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home")).click();
        wd.get("http://localhost/addressbook/");
    }

}
