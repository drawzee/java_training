package stqa.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        app.getSessionHelper().login("admin", "secret");
        app.goTo().groupPage();
        if (!app.group().exists()) {
            app.group().create(new GroupData().withName("Test"));
        }
    }

    @Test
    public void groupModificationTest() {
        app.goTo().groupPage();
        List<GroupData> initialGroups = app.group().list();
        int index = initialGroups.size() - 1;
        GroupData group = new GroupData()
                .withId(initialGroups.get(index).getId()).withName("Test1").withHeader("New header").withFooter("New footer");
        app.group().modify(index, group);
        app.goTo().groupPage();
        List<GroupData> finalGroups = app.group().list();
        Assert.assertEquals(finalGroups.size(), initialGroups.size(), "invalid group count");
        app.getSessionHelper().logout();

        initialGroups.remove(index);
        initialGroups.add(group);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        initialGroups.sort(byId);
        finalGroups.sort(byId);
        Assert.assertEquals(new HashSet<>(initialGroups), new HashSet<>(finalGroups), "elements don't match");
    }

}
