package stqa.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;
import stqa.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

    @DataProvider
    public Iterator<Object[]> validGroupsJson() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<GroupData> groups = gson.fromJson(json, new TypeToken<List<GroupData>>() {}.getType()); //List<GroupData>.class
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator(); //Wrapping object in an array
    }

    @DataProvider
    public Iterator<Object[]> validGroupsXml() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/groups.xml")));
        String xml = "";
        String line = reader.readLine();
        while (line != null) {
            xml += line;
            line = reader.readLine();
        }
        XStream xstream = new XStream();
        xstream.processAnnotations(GroupData.class);
        xstream.allowTypes(new Class[]{GroupData.class});
        List<GroupData> groups = (List<GroupData>) xstream.fromXML(xml);
        return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validGroupsJson")
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
