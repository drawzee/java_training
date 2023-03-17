package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class ContactPhoneTests extends TestBase {

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
                    .withMobile("111111111")
                    .withWork("999999999")
                    .withEmail("email@test.com")
                    .withGroup(CurrentGroup)
            );
        }
    }

    @Test
    public void testContactPhones() {
        app.goTo().homePage();
        ContactData contact = app.contact().all().iterator().next();
        ContactData infoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat("phones don't match", contact.getAllPhones(), equalTo(mergePhones(infoFromEditForm)));
        assertThat("addresses don't match", contact.getAllAddresses(), equalTo(mergeAddresses(infoFromEditForm)));
    }

    public static String cleaned(String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-+()]", "");
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHome(), contact.getMobile(), contact.getWork())
                .stream().filter((s) -> !s.equals(""))
                .map(ContactPhoneTests::cleaned)
                .collect(Collectors.joining("\n"));
    }

    private String mergeAddresses(ContactData contact) {
        return Arrays.asList(contact.getAddress(), contact.getEmail())
                .stream().filter((s) -> !s.equals(""))
                .collect(Collectors.joining(""));
    }

}
