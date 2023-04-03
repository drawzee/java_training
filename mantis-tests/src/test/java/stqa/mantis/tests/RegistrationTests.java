package stqa.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase {

    @Test
    public void registrationTest() {
        app.registration().start("user", "test@test.com");
    }

}
