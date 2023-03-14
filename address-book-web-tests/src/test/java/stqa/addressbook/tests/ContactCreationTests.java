package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testAddContactTests() {
        app.getSessionHelper().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
        }
        app.goTo().groupPage();
        String CurrentGroup = app.wd.findElement(By.className("group")).getText();
        app.goTo().goToHomePage();
        List<ContactData> initialContacts = app.getContactHelper().getContactList();
        ContactData contact = new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com",
                CurrentGroup
        );
        app.getContactHelper().initContactAdding();
        app.getContactHelper().fillContactForm(contact, true);
        app.getContactHelper().submitContactForm();
        app.goTo().goToHomePage();
        List<ContactData> finalContacts = app.getContactHelper().getContactList();
        Assert.assertEquals(finalContacts.size(), initialContacts.size() + 1, "invalid contact count");
        app.getSessionHelper().logout();

        initialContacts.add(contact);
        Comparator<? super ContactData> byId = (c1, c2) -> Integer.compare(c1.getId(), c2.getId());
        initialContacts.sort(byId);
        finalContacts.sort(byId);
        Assert.assertEquals(new HashSet<>(initialContacts), new HashSet<>(finalContacts), "elements don't match");
    }

}
