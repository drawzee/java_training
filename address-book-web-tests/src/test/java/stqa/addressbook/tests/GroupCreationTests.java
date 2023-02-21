package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().initGroupCreation();
        app.getGroupHelper().fillGroupForm(new GroupData("Test", "Test Header", "Test Footer"));
        app.getGroupHelper().submitGroupForm();
        app.getGroupHelper().returnToGroupPage();
        app.getSessionHelper().logout();
    }

}
