package stqa.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroups() throws IOException {
        List<Object[]> list = new ArrayList<Object[]>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.csv")));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(",");
            list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
            line = reader.readLine();
        }
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
