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
            app.getGroupHelper().createGroup(new GroupData(0, "Test", null, null));
        }
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> initialGroups = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(initialGroups.size() - 1);
        app.getGroupHelper().deleteGroup();
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalGroups = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() - 1, "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.remove(initialGroups.size() - 1);
        Assert.assertEquals(initialGroups, finalGroups, "elements don't match");
    }

}
