package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.session().login("admin", "secret");
        app.goTo().homePage();
        if (!app.contact().exists()) {
            app.goTo().groupPage();
            if (!app.group().exists()) {
                app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
            }
            app.goTo().groupPage();
            String CurrentGroup = app.wd.findElement(By.className("group")).getText();
            app.goTo().homePage();
            app.contact().create(new ContactData()
                            .withFirstName("Test")
                            .withLastName("Test")
                            .withCompany("Test LTD")
                            .withAddress("Test st., 123")
                            .withHome("123123123")
                            .withEmail("email@test.com")
                            .withGroup(CurrentGroup)
            );
        }
    }

    @Test()
    public void contactModificationTest() {
        app.goTo().homePage();
        Contacts initialContacts = app.contact().all();
        ContactData modifiedContact = initialContacts.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("Test1")
                .withLastName("Test1")
                .withCompany("Test LTD")
                .withAddress("Test st., 123")
                .withHome("123123123")
                .withEmail("email@test.com");
        app.contact().modify(contact);
        app.goTo().homePage();
        Contacts finalContacts = app.contact().all();
        assertThat("invalid contact count", finalContacts.size(), equalTo(initialContacts.size()));
        app.session().logout();

        assertThat("elements don't match", finalContacts, equalTo(initialContacts.without(modifiedContact).withAdded(contact)));
    }

}
