package stqa.mantis.tests;

import org.testng.annotations.Test;
import stqa.mantis.model.Users;

public class DbConnectionTests extends TestBase {

    @Test
    public void dbConnection() {
        Users users = app.db().selectAllUsers();
        System.out.println(users);
    }

}
