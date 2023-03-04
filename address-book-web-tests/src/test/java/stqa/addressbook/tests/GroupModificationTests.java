package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.List;

public class GroupModificationTests extends TestBase {

    @Test
    public void groupModificationTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        if (!app.getGroupHelper().groupExists()) {
            app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        }
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> initialCount = app.getGroupHelper().getGroupList();
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup(initialCount.size() - 1);
        app.getGroupHelper().modifyGroup(new GroupData("Test1", "New header", "New footer"));
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalCount = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalCount.size(), initialCount.size(), "invalid group count");
        app.getSessionHelper().logout();
    }

}
