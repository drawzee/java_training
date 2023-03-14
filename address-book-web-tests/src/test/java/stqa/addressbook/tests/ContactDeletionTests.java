package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void contactDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.goTo().goToHomePage();
        if (!app.getContactHelper().contactExists()) {
            app.goTo().groupPage();
            if (!app.group().exists()) {
                app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
            }
            app.goTo().groupPage();
            String CurrentGroup = app.wd.findElement(By.className("group")).getText();
            app.goTo().goToHomePage();
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
        app.goTo().goToHomePage();
        List<ContactData> initialContacts = app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(initialContacts.size() - 1);
        app.getContactHelper().deleteContact();
        app.getBaseHelper().acceptAlert();
        app.goTo().goToHomePage();
        List<ContactData> finalContacts = app.getContactHelper().getContactList();
        Assert.assertEquals(finalContacts.size(), initialContacts.size() - 1, "invalid contact count");
        app.getSessionHelper().logout();

        initialContacts.remove(initialContacts.size() - 1);
        Assert.assertEquals(initialContacts, finalContacts, "elements don't match");
    }

}
