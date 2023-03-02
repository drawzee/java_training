package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

public class ContactModificationTests extends TestBase {

    @Test
    public void contactModificationTest() {
        app.getSessionHelper().login("admin", "secret");
        if (!app.getContactHelper().contactExists()) {
            app.getNavigationHelper().goToGroupPage();
            if (!app.getGroupHelper().groupExists()) {
                app.getGroupHelper().createGroup(new GroupData("Test", "Test header", "Test footer"));
            }
            app.getNavigationHelper().goToGroupPage();
            String CurrentGroup = app.wd.findElement(By.className("group")).getText();
            app.getNavigationHelper().goToHomePage();
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
        app.getNavigationHelper().goToHomePage();
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
