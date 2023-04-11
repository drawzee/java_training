package stqa.mantis.appmanager;

import org.openqa.selenium.By;

import java.io.IOException;

public class NavigationHelper extends HelperBase {

    public NavigationHelper(AppManager app) throws IOException {
        super(app);
        wd = app.getDriver();
    }

    public void homeAsAdmin() {
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
        type(By.name("username"), app.getProperty("web.adminLogin"));
        click(By.cssSelector("input[type=submit]"));
        type(By.name("password"), app.getProperty("web.adminPass"));
        click(By.cssSelector("input[type=submit]"));
        wd.get(app.getProperty("web.baseUrl") + "/account_page.php");
    }

    public void manageUsers() {
        wd.get(app.getProperty("web.baseUrl") + "/my_view_page.php");
        click(By.xpath("//a[@href='/" + app.getProperty("web.domain") + "/manage_overview_page.php']"));
        click(By.xpath("//a[@href='/" + app.getProperty("web.domain") + "/manage_user_page.php']"));
    }

    public void logout() {
        click(By.cssSelector("span.user-info"));
        click(By.linkText("Logout"));
        wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    }

}
