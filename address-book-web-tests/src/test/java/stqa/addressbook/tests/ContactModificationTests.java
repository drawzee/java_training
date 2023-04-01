package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().contacts().size() == 0) {
            if (app.db().groups().size() == 0) {
                app.goTo().groupPage();
                app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
            }
            Groups groups = app.db().groups();
            app.goTo().homePage();
            app.contact().create(new ContactData()
                            .withFirstName("Test")
                            .withLastName("Test")
                            .withCompany("Test LTD")
                            .withAddress("Test ave, 111")
                            .withHome("111111111")
                            .withMobile("222222222")
                            .withWork("333333333")
                            .withHome2("444444444")
                            .withEmail("email@test.com")
                            .withEmail2("email2@test.com")
                            .withEmail3("email3@test.com")
                            .withGroup(groups.iterator().next())
            );
        }
    }

    @Test
    public void contactModificationTest() {
        app.goTo().homePage();
        Contacts initialContacts = app.db().contacts();
        ContactData modifiedContact = initialContacts.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedContact.getId())
                .withFirstName("Test1")
                .withLastName("Test1")
                .withCompany("Test-123 LLC")
                .withAddress("Test st., 123")
                .withHome("123123123")
                .withMobile("111222333")
                .withWork("99887766")
                .withHome2("123456789")
                .withEmail("1_email@test.com")
                .withEmail2("2_email@test.com")
                .withEmail3("3_email@test.com");
        app.contact().modify(contact);
        app.goTo().homePage();
        Contacts finalContacts = app.db().contacts();
        assertThat("invalid contact count", finalContacts.size(), equalTo(initialContacts.size()));

        assertThat("elements don't match", finalContacts, equalTo(initialContacts.without(modifiedContact).withAdded(contact)));
        verifyContactListInUI();
    }

}
