package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> initialCount = app.getGroupHelper().getGroupList();
        app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalCount = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalCount.size(), initialCount.size() + 1, "invalid group count");
        app.getSessionHelper().logout();
    }

}
