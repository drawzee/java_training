package stqa.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void checkPreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("Test"));
        }
    }

    @Test
    public void groupModificationTest() {
        app.goTo().groupPage();
        Groups initialGroups = app.db().groups();
        GroupData modifiedGroup = initialGroups.iterator().next();
        GroupData group = new GroupData()
                .withId(modifiedGroup.getId()).withName("Test1").withHeader("New header").withFooter("New footer");
        app.group().modify(group);
        app.goTo().groupPage();
        assertThat("invalid group count", app.group().count(), equalTo(initialGroups.size()));
        Groups finalGroups = app.db().groups();

        assertThat("elements don't match", finalGroups, equalTo(initialGroups.without(modifiedGroup).withAdded(group)));
        verifyGroupListInUI();
    }

}
