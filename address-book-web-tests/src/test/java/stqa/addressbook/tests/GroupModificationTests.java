package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Set;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData().withName("Test"));
        }
    }

    @Test
    public void groupModificationTest() {
        app.goTo().groupPage();
        Set<GroupData> initialGroups = app.group().all();
        GroupData modifiedGroup = initialGroups.iterator().next();
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId()).withName("Test1").withHeader("New header").withFooter("New footer");
        app.group().modify(group);
        app.goTo().groupPage();
        Set<GroupData> finalGroups = app.group().all();
        Assert.assertEquals(finalGroups.size(), initialGroups.size(), "invalid group count");
        app.session().logout();

        initialGroups.remove(modifiedGroup);
        initialGroups.add(group);
        Assert.assertEquals(initialGroups, finalGroups, "elements don't match");
    }

}
