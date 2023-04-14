package stqa.mantis.appmanager;

import biz.futureware.mantis.rpc.soap.client.*;
import stqa.mantis.model.Issue;
import stqa.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

import static java.util.Arrays.*;
import static java.util.stream.Collectors.*;

public class SoapHelper {

    private final AppManager app;

    public SoapHelper(AppManager app) {
        this.app = app;
    }

    private MantisConnectPortType getMantisConnect() throws ServiceException, MalformedURLException {
        MantisConnectPortType mc = new MantisConnectLocator()
                .getMantisConnectPort(new URL(app.getProperty("api.baseUrl")));
        return mc;
    }

    public Set<Project> getProjects() throws MalformedURLException, ServiceException, RemoteException {
        MantisConnectPortType mc = getMantisConnect();
        ProjectData[] projects = mc.mc_projects_get_user_accessible(app.getProperty("web.adminLogin"), app.getProperty("web.adminPass"));
        return asList(projects).stream()
                .map((p) -> new Project().withId(p.getId().intValue()).withName(p.getName()))
                .collect(toSet());
    }

    public String getIssueStatus(int issueId) throws MalformedURLException, ServiceException, RemoteException {
        String login = app.getProperty("web.adminLogin");
        String pass = app.getProperty("web.adminPass");
        MantisConnectPortType mc = getMantisConnect();
        IssueData issue = mc.mc_issue_get(login, pass, BigInteger.valueOf(issueId));
        return String.valueOf(issue.getStatus().getName());
    }

    public Issue addIssue(Issue issue) throws MalformedURLException, ServiceException, RemoteException {
        String login = app.getProperty("web.adminLogin");
        String pass = app.getProperty("web.adminPass");
        MantisConnectPortType mc = getMantisConnect();
        String[] categories = mc.mc_project_get_categories(login, pass, BigInteger.valueOf(issue.getProject().getId()));
        IssueData issueData = new IssueData();
        issueData.setSummary(issue.getSummary());
        issueData.setDescription(issue.getDescription());
        issueData.setProject(new ObjectRef(BigInteger.valueOf(issue.getProject().getId()), issue.getProject().getName()));
        issueData.setCategory(categories[0]);
        BigInteger issueId = mc.mc_issue_add(login, pass, issueData);
        IssueData createdIssueData = mc.mc_issue_get(login, pass, issueId);
        return new Issue()
                .withId(createdIssueData.getId().intValue())
                .withSummary(createdIssueData.getSummary())
                .withDescription(createdIssueData.getDescription())
                .withProject(new Project().withId(createdIssueData.getProject().getId().intValue())
                        .withName(createdIssueData.getProject().getName())
                );
    }

    public Project addProject(String name) throws MalformedURLException, ServiceException, RemoteException {
        String login = app.getProperty("web.adminLogin");
        String pass = app.getProperty("web.adminPass");
        MantisConnectPortType mc = getMantisConnect();
        ProjectData project = new ProjectData();
        project.setName(name);
        mc.mc_project_add(login, pass, project);
        return new Project().withName(project.getName());
    }

}
