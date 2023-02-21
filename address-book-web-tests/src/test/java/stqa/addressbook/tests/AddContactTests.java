package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class AddContactTests extends TestBase {

    @Test
    public void testAddContactTests() throws Exception {
        app.login("admin", "secret");
        app.initContactAdding();
        app.fillContactForm(new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com")
        );
        app.submitContactForm();
        app.returnToHomePage();
        app.logout();
    }

}
