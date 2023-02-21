package stqa.addressbook;

import org.testng.annotations.Test;

public class AddContactTests extends TestBase {

    @Test
    public void testAddContactTests() throws Exception {
        login("admin", "secret");
        initContactAdding();
        fillContactForm(new ContactData(
                "Test",
                "Test",
                "Test LTD",
                "Test st., 123",
                "123123123",
                "email@test.com")
        );
        submitContactForm();
        returnToHomePage();
        logout();
    }

}
