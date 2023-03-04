package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        int initialCount = app.getGroupHelper().getGroupCount();
        app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        app.getNavigationHelper().goToGroupPage();
        int finalCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(finalCount, initialCount + 1, "invalid group count");
        app.getSessionHelper().logout();
    }

}
