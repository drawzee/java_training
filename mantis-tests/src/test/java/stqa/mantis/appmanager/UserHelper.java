package stqa.mantis.appmanager;

import org.openqa.selenium.By;

import java.io.IOException;

    public class UserHelper extends HelperBase {

    public UserHelper(AppManager app) throws IOException {
        super(app);
        wd = app.getDriver();
    }

    public void resetPass(int id, String username) {
        click(By.linkText(username));
        wd.get(app.getProperty("web.baseUrl") + "/manage_user_edit_page.php?user_id=" + String.valueOf(id));
        click(By.xpath("//input[@value='Reset Password']"));
    }

}
