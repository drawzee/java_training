package stqa.addressbook;

import org.testng.annotations.Test;

public class GroupCreationTests extends TestBase {

    @Test
    public void testGroupCreation() throws Exception {
        login("admin", "secret");
        gotoGroupPage();
        initGroupCreation();
        fillGroupForm(new GroupData("Test", "Test Header", "Test Footer"));
        submitGroupForm();
        returnToGroupPage();
        logout();
    }

}
