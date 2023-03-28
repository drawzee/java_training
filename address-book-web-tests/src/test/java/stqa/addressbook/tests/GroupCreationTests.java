package stqa.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups() {
        List<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{new GroupData().withName("Test 1").withHeader("Header 1").withFooter("Footer 1")});
        list.add(new Object[]{new GroupData().withName("Test 2").withHeader("Header 2").withFooter("Footer 2")});
        list.add(new Object[]{new GroupData().withName("Test 3").withHeader("Header 3").withFooter("Footer 3")});
        return list.iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(GroupData group) {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        Groups initialGroups = app.group().all();
        app.group().create(group);
        app.goTo().groupPage();
        assertThat("invalid group count", app.group().count(), equalTo(initialGroups.size() + 1));
        Groups finalGroups = app.group().all();
        app.session().logout();
        assertThat("elements don't match", finalGroups, equalTo(
                initialGroups.withAdded(group.withId(finalGroups.stream().mapToInt((g) -> g.getId()).max().getAsInt()))
        ));
    }

    @Test
    public void testInvalidGroupCreation() {
        app.goTo().groupPage();
        Groups initialGroups = app.group().all();
        GroupData group = new GroupData().withName("Test'");
        app.group().create(group);
        app.goTo().groupPage();
        assertThat("invalid group count", app.group().count(), equalTo(initialGroups.size()));
        Groups finalGroups = app.group().all();
        app.session().logout();
        assertThat("elements don't match", finalGroups, equalTo(initialGroups));
    }

}
