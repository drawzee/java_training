package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.goTo().groupPage();
            if (app.db().groups().size() == 0) {
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

    @Test
    public void contactDeletionTest() {
        app.goTo().homePage();
        Contacts initialContacts = app.db().contacts();
        ContactData deletedContact = initialContacts.iterator().next();
        app.contact().delete(deletedContact);
        app.base().acceptAlert();
        app.goTo().homePage();
        Contacts finalContacts = app.db().contacts();
        assertThat("invalid contact count", finalContacts.size(), equalTo(initialContacts.size() - 1));

        assertThat("elements don't match", finalContacts, equalTo(initialContacts.without(deletedContact)));
    }

}
