package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void groupPage() {
        if (isElementPresent(By.tagName("h1"))
                && wd.findElement(By.tagName("h1")).getText().equals("Groups")
                && isElementPresent(By.name("new"))) {
            return;
        } else {
            click(By.linkText("groups"));
        }
        wd.get("http://localhost/addressbook/group.php");
    }

    public void homePage() {
        if (isElementPresent(By.id("maintable"))) {
            return;
        } else {
            click(By.linkText("home"));
        }
        wd.get("http://localhost/addressbook/");
    }

}
