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
        list.add(new Object[]{"Test 1", "Header 1", "Footer 1"});
        list.add(new Object[]{"Test 2", "Header 2", "Footer 2"});
        list.add(new Object[]{"Test 3", "Header 3", "Footer 3"});
        return list.iterator();
    }

    @Test(dataProvider = "validGroups")
    public void testGroupCreation(String name, String header, String footer) {
        app.session().login("admin", "secret");
        GroupData group = new GroupData().withName(name).withHeader(header).withFooter(footer);
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
