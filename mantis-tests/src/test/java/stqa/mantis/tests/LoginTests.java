package stqa.mantis.tests;

import org.testng.annotations.Test;
import stqa.mantis.appmanager.HttpSession;

import java.io.IOException;

import static org.testng.Assert.*;

public class LoginTests extends TestBase {

    @Test
    public void loginTest() throws IOException {
        HttpSession session = app.newSession();
        assertTrue(session.login("administrator", "root"));
        assertTrue(session.loggedInAs("administrator"));
    }

}
