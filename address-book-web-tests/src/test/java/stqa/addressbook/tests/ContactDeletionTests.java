package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void contactDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToHomePage();
        if (!app.getContactHelper().contactExists()) {
            app.getContactHelper().createContact(new ContactData(
                    "Test",
                    "Test",
                    "Test LTD",
                    "Test st., 123",
                    "123123123",
                    "email@test.com",
                    "Test"
                    )
            );
        }
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getBaseHelper().acceptAlert();
        app.getSessionHelper().logout();
    }

}
