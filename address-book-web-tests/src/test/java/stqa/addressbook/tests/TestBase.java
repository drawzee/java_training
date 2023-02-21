package stqa.addressbook.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import stqa.addressbook.appmanager.AppManager;

public class TestBase {

    protected final AppManager app = new AppManager();

    @BeforeMethod(alwaysRun = true)
    public void setUp() throws Exception {
        app.init();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() throws Exception {
        app.stop();
    }

}
