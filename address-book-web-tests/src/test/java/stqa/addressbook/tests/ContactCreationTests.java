package stqa.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> contactsJson() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")))) {
            String json = "";
            String line = reader.readLine();
            while (line != null) {
                json += line;
                line = reader.readLine();
            }
            Gson gson = new Gson();
            List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>() {}.getType()); //List<ContactData>.class
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator(); //Wrapping object in an array
        }
    }

    @DataProvider
    public Iterator<Object[]> contactsXml() throws IOException {
        try(BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.xml")))) {
            String xml = "";
            String line = reader.readLine();
            while (line != null) {
                xml += line;
                line = reader.readLine();
            }
            XStream xstream = new XStream();
            xstream.processAnnotations(ContactData.class);
            xstream.allowTypes(new Class[]{ContactData.class});
            List<ContactData> contacts = (List<ContactData>) xstream.fromXML(xml);
            return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
        }
    }

    @BeforeMethod
    public void checkPreconditions() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists() || !app.wd.findElement(By.className("group")).getText().equals("Test 0")) {
            app.group().create(new GroupData().withName("Test 0").withHeader("Test header").withFooter("Test footer"));
        }
    }

    @Test(dataProvider = "contactsXml")
    public void testAddContactTests(ContactData contact) {
        //app.goTo().groupPage();
        //String CurrentGroup = app.wd.findElement(By.className("group")).getText();
        app.goTo().homePage();
        Contacts initialContacts = app.contact().all();
        /*
        File photo = new File("src/test/resources/contact_photo.png");
        ContactData contact = new ContactData()
                .withFirstName("Test")
                .withLastName("Test")
                .withAddress("Test st., 123")
                .withHome("123123123")
                .withEmail("email@test.com")
                .withGroup(CurrentGroup)
                .withPhoto(photo);
         */
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
