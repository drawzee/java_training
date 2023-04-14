package stqa.mantis.model;

public class Issue {

    private int id;
    private String summary;
    private String status;
    private String description;
    private Project project;


    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public Project getProject() {
        return project;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public Issue withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Issue withStatus(String status) {
        this.status = status;
        return this;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    public Issue withProject(Project project) {
        this.project = project;
        return this;
    }

}
