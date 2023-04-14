package stqa.rest.appmanager;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import stqa.rest.model.Issue;

import java.io.IOException;
import java.util.Set;

public class RestHelper {

    private final AppManager app;

    public RestHelper(AppManager app) {
        this.app = app;
    }

    private Executor getExecutor() throws IOException {
        return Executor.newInstance().auth(app.getProperty("api.token"), "");
    }

    public Set<Issue> getIssues() throws IOException {
        String json = getExecutor().execute(Request.Get(app.getProperty("api.baseUrl") + "/issues.json?pageSize=500")).returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        return new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
    }

    public int createIssue(Issue newIssue) throws IOException {
        String json = getExecutor().execute(Request.Post(app.getProperty("api.baseUrl") + "/issues.json")
                        .bodyForm(
                                new BasicNameValuePair("subject", newIssue.getSubject()),
                                new BasicNameValuePair("description", newIssue.getDescription()))
                )
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json);
        return parsed.getAsJsonObject().get("issue_id").getAsInt();
    }

    public String getIssueStatus(int issueId) throws IOException {
        String json = getExecutor().execute(Request.Get(String.format(app.getProperty("api.baseUrl") + "/issues/%s.json", issueId)))
                .returnContent().asString();
        JsonElement parsed = new JsonParser().parse(json).getAsJsonObject();
        JsonObject obj = parsed.getAsJsonObject();
        JsonArray arr = obj.getAsJsonArray("issues");
        return arr.get(0).getAsJsonObject().get("state_name").getAsString();
    }

}
