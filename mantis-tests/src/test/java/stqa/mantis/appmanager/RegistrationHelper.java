package stqa.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(AppManager app) {
        super(app);
        wd = app.getDriver();
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String confirmationLink, String pass, String realName) {
        wd.get(confirmationLink);
        type(By.id("realname"), realName);
        type(By.name("password"), pass);
        type(By.name("password_confirm"), pass);
        click(By.cssSelector("button[type=submit]"));
    }
}
