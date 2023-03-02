package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void contactModificationTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        String CurrentGroup = app.wd.findElement(By.className("group")).getText();
        app.getNavigationHelper().goToHomePage();
        if (!app.getContactHelper().contactExists()) {
            app.getContactHelper().createContact(new ContactData(
                            "Test",
                            "Test",
                            "Test LTD",
                            "Test st., 123",
                            "123123123",
                            "email@test.com",
                            CurrentGroup
                    )
            );
        }
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData(
                        "Name",
                        "Lastname",
                        "New Test LTD",
                        "New Test st., 123",
                        "999999999",
                        "new_email@test.com",
                        null
                ), false
        );
        app.getContactHelper().submitContactModification();
        app.getSessionHelper().logout();
    }

}
