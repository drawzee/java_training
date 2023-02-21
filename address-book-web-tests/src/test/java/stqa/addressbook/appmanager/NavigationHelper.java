package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper {

    public WebDriver wd;

    public NavigationHelper(WebDriver wd) {
        this.wd = wd;
    }

    public void gotoGroupPage() {
        wd.findElement(By.linkText("groups")).click();
        wd.get("http://localhost/addressbook/group.php");
    }

    public void returnToHomePage() {
        wd.findElement(By.linkText("home")).click();
        wd.get("http://localhost/addressbook/");
    }

}
