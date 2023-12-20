package pl.wsb.issuetracker.issue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wsb.issuetracker.api.controllers.IssuesController;
import pl.wsb.issuetracker.helper.SecurityContextHelper;
import pl.wsb.issuetracker.issue.dto.IssueDetailsDTO;
import pl.wsb.issuetracker.issue.dto.ReportIssueRequestDTO;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class IssueApiIntegrationTest {

    @Autowired
    private IssuesController tested;

    @Test
    public void shouldReportNewIssue() {
        // given

        SecurityContextHelper.loginAs("REPORTER");

        ReportIssueRequestDTO issueRequestDTO = ReportIssueRequestDTO.builder()
                .summary("Test summary")
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
                .severity("LOW")
                .build();

        // when
        IssueDetailsDTO actual = tested.reportIssue(issueRequestDTO);

        // then
        assertThat(actual.getAssignedTo()).isNotBlank();
        assertThat(actual.getCreatedAt()).isBefore(LocalDateTime.now());
    }

}
