package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactGroupsTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
        }
        if (app.db().contacts().size() == 0) {
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
            );
        }
    }

    @Test
    public void addContactToGroupTest() {
        app.goTo().groupPage();
        Groups groups = app.group().all();
        app.goTo().homePage();
        Contacts contacts = app.contact().all();

        int selectedContactId = contacts.iterator().next().getId();
        int selectedGroupId = groups.iterator().next().getId();
        String selectedGroupName = groups.iterator().next().getName();

        if (app.db().contactHasGroup(selectedContactId, selectedGroupId).size() != 0) {
            app.contact().deleteFromGroup(selectedContactId, selectedGroupName);
            app.goTo().homePage();
        }

        app.contact().addToGroup(selectedContactId, selectedGroupName);
        Groups contactGroup = app.db().contactHasGroup(selectedContactId, selectedGroupId);
        Boolean contactAdded = contactGroup.size() == 0 ? false : true;

        assertThat("contact doesn't have selected group", contactAdded, equalTo(true));
    }

    @Test
    public void deleteContactFromGroupTest() {
        app.goTo().groupPage();
        Groups groups = app.group().all();
        app.goTo().homePage();
        Contacts contacts = app.contact().all();

        int selectedContactId = contacts.iterator().next().getId();
        int selectedGroupId = groups.iterator().next().getId();
        String selectedGroupName = groups.iterator().next().getName();

        if (app.db().contactHasGroup(selectedContactId, selectedGroupId).size() == 0) {
            app.contact().addToGroup(selectedContactId, selectedGroupName);
            app.goTo().homePage();
        }

        app.contact().deleteFromGroup(selectedContactId, selectedGroupName);
        Groups contactGroup = app.db().contactHasGroup(selectedContactId, selectedGroupId);
        Boolean contactDeleted = contactGroup.size() != 0 ? false : true;

        assertThat("contact wasn't deleted from group", contactDeleted, equalTo(true));
    }

}
