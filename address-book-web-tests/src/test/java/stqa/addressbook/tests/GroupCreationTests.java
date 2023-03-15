package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        Set<GroupData> initialGroups = app.group().all();
        GroupData group = new GroupData().withName("Test");
        app.group().create(group);
        app.goTo().groupPage();
        Set<GroupData> finalGroups = app.group().all();
        Assert.assertEquals(finalGroups.size(), initialGroups.size() + 1, "invalid group count");
        app.session().logout();

        group.withId(finalGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt());
        initialGroups.add(group);
        Assert.assertEquals(initialGroups, finalGroups, "elements don't match");
    }

}
