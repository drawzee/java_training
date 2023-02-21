package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void contactDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getContactHelper().initContactAdding();
        app.getContactHelper().fillContactForm(new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com")
        );
        app.getContactHelper().submitContactForm();
        app.getNavigationHelper().returnToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getBaseHelper().acceptAlert();
        app.getSessionHelper().logout();
    }

}
