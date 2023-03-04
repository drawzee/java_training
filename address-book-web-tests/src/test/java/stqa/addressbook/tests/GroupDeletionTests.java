package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

    @Test
    public void groupDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        int initialCount = app.getGroupHelper().getGroupCount();
        if (!app.getGroupHelper().groupExists()) {
            app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        }
        app.getGroupHelper().selectGroup(initialCount - 1);
        app.getGroupHelper().deleteGroup();
        app.getNavigationHelper().goToGroupPage();
        int finalCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(finalCount, initialCount - 1, "invalid group count");
        app.getSessionHelper().logout();
    }

}
