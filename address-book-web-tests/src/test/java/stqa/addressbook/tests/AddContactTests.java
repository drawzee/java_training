package stqa.addressbook.tests;

import org.openqa.selenium.By;
import org.testng.annotations.Test;
import stqa.addressbook.model.ContactData;
import stqa.addressbook.model.GroupData;

public class AddContactTests extends TestBase {

    @Test
    public void testAddContactTests() throws Exception {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().groupExists()) {
            app.getGroupHelper().createGroup(new GroupData("Test", "Test header", "Test footer"));
        }
        app.getNavigationHelper().goToGroupPage();
        String CurrentGroup = app.wd.findElement(By.className("group")).getText();
        app.getNavigationHelper().goToHomePage();
        app.getContactHelper().initContactAdding();
        app.getContactHelper().fillContactForm(new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com",
                CurrentGroup
                ), true
        );
        app.getContactHelper().submitContactForm();
        app.getNavigationHelper().goToHomePage();
        app.getSessionHelper().logout();
    }

}
