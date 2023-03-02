package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class AddContactTests extends TestBase {

    @Test
    public void testAddContactTests() throws Exception {
        app.getSessionHelper().login("admin", "secret");
        app.getContactHelper().initContactAdding();
        app.getContactHelper().fillContactForm(new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com",
                "qwe"
                ), true
        );
        app.getContactHelper().submitContactForm();
        app.getNavigationHelper().goToHomePage();
        app.getSessionHelper().logout();
    }

}
