package stqa.rest.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestTests extends TestBase {

    @Test //assume creating is impossible until bug #169 is fixed
    public void createIssueTest() throws IOException {
        skipIfNotFixed(169);

        Set<Issue> initialIssues = app.rest().getIssues();
        Issue newIssue = new Issue().withSubject("Test subject").withDescription("Test description");
        int issueId = app.rest().createIssue(newIssue);
        Set<Issue> finalIssues = app.rest().getIssues();
        initialIssues.add(newIssue.withId(issueId));

        Assert.assertEquals(finalIssues, initialIssues, "elements don't match");
    }

}
