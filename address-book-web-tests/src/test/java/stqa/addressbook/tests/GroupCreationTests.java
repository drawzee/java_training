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
        app.goTo().groupPage();
        List<GroupData> initialGroups = app.group().list();
        GroupData group = new GroupData().withName("Test");
        app.group().create(group);
        app.goTo().groupPage();
        List<GroupData> finalGroups = app.group().list();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() + 1, "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        initialGroups.sort(byId);
        finalGroups.sort(byId);
        Assert.assertEquals(new HashSet<>(initialGroups), new HashSet<>(finalGroups), "elements don't match");
    }

}
