package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() {
        app.session().login("admin", "secret");
        app.goTo().groupPage();
        Groups initialGroups = app.group().all();
        GroupData group = new GroupData().withName("Test");
        app.group().create(group);
        app.goTo().groupPage();
        assertThat("invalid group count", app.group().count(), equalTo(initialGroups.size() + 1));
        Groups finalGroups = app.group().all();
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
