package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> initialGroups = app.getGroupHelper().getGroupList();
        GroupData group = new GroupData("Test", null, null);
        app.getGroupHelper().createGroup(group);
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalGroups = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() + 1, "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        initialGroups.sort(byId);
        finalGroups.sort(byId);
        Assert.assertEquals(new HashSet<>(initialGroups), new HashSet<>(finalGroups), "elements don't match");
    }

}
