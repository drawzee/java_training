package stqa.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import stqa.addressbook.appmanager.AppManager;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.Contacts;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;

import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {

    Logger logger = LoggerFactory.getLogger(TestBase.class);

    protected static final AppManager app = new AppManager(System.getProperty("browser", BrowserType.FIREFOX));

    @BeforeSuite
    public void setUp() throws Exception {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

    @BeforeMethod
    public void logTestStart(Method m, Object[] p) {
        logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method m, Object[] p) {
        logger.info("Stop test " + m.getName());
    }

    public void verifyGroupListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Groups groupsFromDB = app.db().groups();
            Groups groupsFromUI = app.group().all();
            assertThat("Group lists don't match", groupsFromUI, equalTo(groupsFromDB
                    .stream().map((g) -> new GroupData().withId(g.getId()).withName(g.getName())).collect(toSet())
            ));
        }
    }

    public void verifyContactListInUI() {
        if (Boolean.getBoolean("verifyUI")) {
            Contacts contactsFromDB = app.db().contacts();
            Contacts contactsFromUI = app.contact().all();
            assertThat("Contact lists don't match", contactsFromUI, equalTo(contactsFromDB
                    .stream().map((c) -> new ContactData()
                            .withId(c.getId())
                            .withFirstName(c.getFirstname())
                            .withLastName(c.getLastname())
                            .withAddress(c.getAddress()))
                    .collect(toSet())
            ));
        }
    }

}
