package stqa.addressbook.tests;

import org.testng.annotations.Test;
import stqa.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

    @Test
    public void groupDeletionTest() {
        app.getSessionHelper().login("admin", "secret");
        app.getNavigationHelper().gotoGroupPage();
        app.getGroupHelper().initGroupCreation();
        app.getGroupHelper().fillGroupForm(new GroupData("Test", "Test Header", "Test Footer"));
        app.getNavigationHelper().returnToGroupPage();
        app.getGroupHelper().selectGroup();
        app.getGroupHelper().deleteGroup();
        app.getSessionHelper().logout();
    }

}
