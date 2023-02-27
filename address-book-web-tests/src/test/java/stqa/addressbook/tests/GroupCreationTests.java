package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        app.getNavigationHelper().goToGroupPage();
        app.getSessionHelper().logout();
    }

}
