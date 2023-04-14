package stqa.rest.tests;

import org.testng.SkipException;
import stqa.rest.appmanager.AppManager;

import java.io.IOException;

public class TestBase {

    protected static final AppManager app = new AppManager();

    boolean isIssueOpen(int issueId) throws IOException {
        if (app.rest().getIssueStatus(issueId).equals("Resolved") || app.rest().getIssueStatus(issueId).equals("Closed")) {
            return false;
        }
        else return true;
    }

    public void skipIfNotFixed(int issueId) throws IOException {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }

}
