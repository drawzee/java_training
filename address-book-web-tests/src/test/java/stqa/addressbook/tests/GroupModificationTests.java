package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupModificationTests extends TestBase {

    @Test
    public void groupModificationTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        int initialCount = app.getGroupHelper().getGroupCount();
        if (!app.getGroupHelper().groupExists()) {
            app.getGroupHelper().createGroup(new GroupData("Test", null, null));
        }
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().modifyGroup(new GroupData("Test1", "New header", "New footer"));
        app.getNavigationHelper().goToGroupPage();
        int finalCount = app.getGroupHelper().getGroupCount();
        Assert.assertEquals(finalCount, initialCount, "invalid group count");
        app.getSessionHelper().logout();
    }

}
