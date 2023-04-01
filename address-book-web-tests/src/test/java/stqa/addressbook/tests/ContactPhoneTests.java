package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().groupPage();
            if (app.db().groups().size() == 0) {
                app.group().create(new GroupData().withName("Test").withHeader("Test header").withFooter("Test footer"));
            }
            Groups groups = app.db().groups();
            app.goTo().homePage();
            app.contact().create(new ContactData()
                    .withFirstName("Test")
                    .withLastName("Test")
                    .withCompany("Test LTD")
                    .withAddress("Test st., 123")
                    .withHome("123-123-123")
                    .withMobile("+111111111")
                    .withWork("9(999)99999")
                    .withHome2("555555555")
                    .withEmail("email@test.com")
                    .withEmail2("2.email@test.com")
                    .withEmail3("3.email@test.com")
                    .withGroup(groups.iterator().next())
            );
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData infoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat("phones don't match", contact.getAllPhones(), equalTo(mergePhones(infoFromEditForm)));
        assertThat("address doesn't match", contact.getAddress(), equalTo(infoFromEditForm.getAddress()));
        assertThat("emails don't match", contact.getAllEmails(), equalTo(mergeEmails(infoFromEditForm)));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork(), contact.getHome2())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeEmails(ContactData contact) {
        return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining("\n"));
    }

}
