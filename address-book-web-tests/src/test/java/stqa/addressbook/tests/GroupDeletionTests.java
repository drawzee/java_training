package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @Test
    public void groupDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().groupExists()) {
            app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        }
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> initialCount = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(initialCount.size() - 1);
        app.getGroupHelper().deleteGroup();
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalCount = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalCount.size(), initialCount.size() - 1, "invalid group count");
        app.getSessionHelper().logout();

        initialCount.remove(initialCount.size() - 1);
        Assert.assertEquals(initialCount, finalCount, "elements don't match");
    }

}
