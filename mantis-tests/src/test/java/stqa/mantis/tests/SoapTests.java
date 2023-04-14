package stqa.mantis.tests;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.annotations.Test;
import stqa.mantis.model.Issue;
import stqa.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.Assert.*;

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

        assertEquals(issue.getSummary(), created.getSummary(), "summaries don't match");
    }

    @Test //assume creating is impossible until bug #0000002 is fixed
    public void createProjectTest() throws MalformedURLException, ServiceException, RemoteException {
        int issueId = 0000002;
        String name = RandomStringUtils.randomAlphabetic(5) + " test";
        skipIfNotFixed(issueId);
        Set<Project> initialProjects = app.soap().getProjects();
        app.soap().addProject(name);
        Set<Project> finalProjects = app.soap().getProjects();

        assertEquals(finalProjects.size(), initialProjects.size() + 1, "invalid project count");
    }

}
