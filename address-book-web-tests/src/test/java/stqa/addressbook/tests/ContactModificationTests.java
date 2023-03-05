package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

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
        List<ContactData> initialContacts = app.getContactHelper().getContactList();
        ContactData contact = new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com",
                null
        );
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().goToHomePage();
        List<ContactData> finalContacts = app.getContactHelper().getContactList();
        Assert.assertEquals(finalContacts.size(), initialContacts.size(), "invalid contact count");
        app.getSessionHelper().logout();

        initialContacts.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        initialContacts.sort(byId);
        finalContacts.sort(byId);
        Assert.assertEquals(new HashSet<>(initialContacts), new HashSet<>(finalContacts), "elements don't match");
    }

}
