package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.getSessionHelper().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData("Test", null, null));
        }
    }

    @Test
    public void groupDeletionTest() {
        app.goTo().groupPage();
        List<GroupData> initialGroups = app.group().list();
        int index = initialGroups.size() - 1;
        app.group().delete(index);
        app.goTo().groupPage();
        List<GroupData> finalGroups = app.group().list();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() - 1, "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.remove(index);
        Assert.assertEquals(initialGroups, finalGroups, "elements don't match");
    }

}
