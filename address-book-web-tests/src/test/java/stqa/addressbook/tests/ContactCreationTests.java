package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
        }
    }

    @Test
    public void testAddContactTests() {
        app.goTo().groupPage();
        String CurrentGroup = app.wd.findElement(By.className("group")).getText();
        app.goTo().homePage();
        Contacts initialContacts = app.contact().all();
        File photo = new File("src/test/resources/contact_photo.png");
        ContactData contact = new ContactData()
                .withFirstName("Test")
                .withLastName("Test")
                .withCompany("Test LTD")
                .withAddress("Test st., 123")
                .withHome("123123123")
                .withEmail("email@test.com")
                .withGroup(CurrentGroup)
                .withPhoto(photo);
        app.contact().create(contact);
        app.goTo().homePage();
        Contacts finalContacts = app.contact().all();
        assertThat("invalid contact count", finalContacts.size(), equalTo(initialContacts.size() + 1));
        app.session().logout();

        assertThat("elements don't match", finalContacts, equalTo(
                initialContacts.withAdded(contact.withId(finalContacts.stream().mapToInt((c) -> c.getId()).max().getAsInt())
        )));
    }

}
