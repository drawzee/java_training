package stqa.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import stqa.mantis.model.Issue;
import stqa.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

public class SoapTests extends TestBase {

    @Test
    public void getProjectsTest() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        System.out.println("Projects count: " + projects.size());
        for (Project project : projects) {
            System.out.println("Project name(s): " + project.getName());
        }
    }

    @Test
    public void createIssueTest() throws MalformedURLException, ServiceException, RemoteException {
        Set<Project> projects = app.soap().getProjects();
        Issue issue = new Issue()
                .withSummary("Test issue")
                .withDescription("Test description")
                .withProject(projects.iterator().next());

        Issue created = app.soap().addIssue(issue);
        Assert.assertEquals(issue.getSummary(), created.getSummary(), "summaries don't match");
    }

    /*
    @Test
    public void getIssueStatusTest() throws MalformedURLException, ServiceException, RemoteException {
        System.out.println("Status is " + app.soap().getIssueStatus(0000002));
    }
     */

}
