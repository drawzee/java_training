package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
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
        List<GroupData> initialGroups = app.getGroupHelper().getGroupList();
        app.getNavigationHelper().goToGroupPage();
        app.getGroupHelper().selectGroup(initialGroups.size() - 1);
        GroupData group = new GroupData(initialGroups.get(initialGroups.size() - 1).getId(), "Test1", "New header", "New footer");
        app.getGroupHelper().modifyGroup(group);
        app.getNavigationHelper().goToGroupPage();
        List<GroupData> finalGroups = app.getGroupHelper().getGroupList();
        Assert.assertEquals(finalGroups.size(), initialGroups.size(), "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.remove(initialGroups.size() - 1);
        initialGroups.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        initialGroups.sort(byId);
        finalGroups.sort(byId);
        Assert.assertEquals(new HashSet<>(initialGroups), new HashSet<>(finalGroups), "elements don't match");
    }

}
