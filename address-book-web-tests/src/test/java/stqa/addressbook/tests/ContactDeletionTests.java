package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void contactDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToHomePage();
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
        app.getContactHelper().selectContact();
        app.getContactHelper().initContactDeletion();
        app.getBaseHelper().acceptAlert();
        app.getSessionHelper().logout();
    }

}
