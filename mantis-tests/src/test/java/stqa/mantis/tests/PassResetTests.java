package stqa.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.mantis.model.MailMessage;
import stqa.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PassResetTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() throws IOException, MessagingException {
        if (app.db().selectAllUsers().size() == 0) {
            String user = app.registration().randomUsername();
            String email = app.registration().randomEmail();
            String pass = "password";
            app.registration().start(user, email);
            List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
            String confirmationLink = app.registration().findConfirmationLink(mailMessages, email);
            app.registration().finish(confirmationLink, pass);
        }
    }

    @Test
    public void passResetTest() throws IOException, MessagingException {
        UserData user = app.db().selectAllUsers().iterator().next();
        String pass = "password";
        app.goTo().homeAsAdmin();
        app.goTo().manageUsers();
        app.users().resetPass(user.getId(), user.getUsername());
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String resetLink = app.registration().findResetLink(mailMessages, user.getEmail());
        app.registration().finish(resetLink, pass);
        assertTrue(app.newSession().login(user.getUsername(), pass));
    }

}
