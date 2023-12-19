package pl.wsb.issuetracker.issue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.wsb.issuetracker.api.controllers.IssuesController;

@SpringBootTest(classes = ApplicationRunner.class)
@ActiveProfiles({"test"})
public class IssueApiIntegrationTest {

    @Autowired
    private IssuesController tested;



}
