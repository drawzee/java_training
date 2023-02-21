package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }

    public void gotoGroupPage() {
        click(By.linkText("groups"));
        wd.get("http://localhost/addressbook/group.php");
    }

    public void returnToHomePage() {
        click(By.linkText("home"));
        wd.get("http://localhost/addressbook/");
    }

    public void returnToGroupPage() {
        click(By.linkText("groups"));
    }

}
