package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData().withName("Test"));
        }
    }

    @Test
    public void groupDeletionTest() {
        app.goTo().groupPage();
        Set<GroupData> initialGroups = app.group().all();
        GroupData deletedGroup = initialGroups.iterator().next();
        app.group().delete(deletedGroup);
        app.goTo().groupPage();
        Set<GroupData> finalGroups = app.group().all();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() - 1, "invalid group count");
        app.session().logout();

        initialGroups.remove(deletedGroup);
        Assert.assertEquals(initialGroups, finalGroups, "elements don't match");
    }

}
