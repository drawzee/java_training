package stqa.mantis.appmanager;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import ru.lanwen.verbalregex.VerbalExpression;
import stqa.mantis.model.MailMessage;

import java.io.IOException;
import java.util.List;

public class RegistrationHelper extends HelperBase {

    public RegistrationHelper(AppManager app) throws IOException {
        super(app);
        wd = app.getDriver();
    }

    public void start(String username, String email) {
        wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
        type(By.name("username"), username);
        type(By.name("email"), email);
        click(By.cssSelector("input[value='Signup']"));
    }

    public void finish(String confirmationLink, String pass) {
        wd.get(confirmationLink);
        type(By.name("password"), pass);
        type(By.name("password_confirm"), pass);
        click(By.cssSelector("button[type=submit]"));
    }

    public String findConfirmationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public String findResetLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email) && m.text.contains("password has been reset")).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    public String randomUsername() {
        return "user" + RandomStringUtils.randomNumeric(5);
    }

    public String randomEmail() {
        return RandomStringUtils.randomAlphabetic(5) + "@localhost.localdomain";
    }

}
