package stqa.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase {

    public SessionHelper(WebDriver wd) {
        super(wd);
    }

    public void login(String username, String pass) {
        wd.get("http://localhost/addressbook/index.php");
        type(By.name("user"), username);
        type(By.name("pass"), pass);
        click(By.xpath("//input[@value='Login']"));
    }

    public void logout() {
        click(By.linkText("Logout"));
        wd.findElement(By.name("user"));
    }

}
