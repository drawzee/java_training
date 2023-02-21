package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.login("admin", "secret");
        app.gotoGroupPage();
        app.initGroupCreation();
        app.fillGroupForm(new GroupData("Test", "Test Header", "Test Footer"));
        app.submitGroupForm();
        app.returnToGroupPage();
        app.logout();
    }

}
