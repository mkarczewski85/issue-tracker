package pl.wsb.issuetracker.issue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wsb.issuetracker.api.controllers.IssuesController;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.helper.SecurityContextHelper;
import pl.wsb.issuetracker.issue.dto.IssueDetailsDTO;
import pl.wsb.issuetracker.issue.dto.IssueDisplayDTO;
import pl.wsb.issuetracker.issue.dto.PatchIssueRequestDTO;
import pl.wsb.issuetracker.issue.dto.ReportIssueRequestDTO;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static pl.wsb.issuetracker.helper.TestDataHelper.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class IssueApiIntegrationTest {

    private static final String SUMMARY = "Test summary";
    private static final String DESCRIPTION = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor 
            incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud 
            exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure 
            dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. 
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit 
            anim id est laborum.""";

    public static final ReportIssueRequestDTO REPORT_REQ_DTO = ReportIssueRequestDTO.builder()
            .summary(SUMMARY)
            .description(DESCRIPTION)
            .severity(LOW_SEVERITY)
            .build();
    public static final PatchIssueRequestDTO PATCH_REQ_DTO = PatchIssueRequestDTO.builder()
            .status(IN_PROGRESS_STATUS)
            .severity(MEDIUM_SEVERITY)
            .build();

    @Autowired
    private IssuesController tested;

    @Autowired
    private IssueClient issueClient;

    private UUID issueUuid;

    @AfterEach
    public void cleanup() {
        if (this.issueUuid != null) {
            issueClient.deleteByUuid(issueUuid);
        }
    }

    @Test
    public void shouldReportNewIssue() {
        // given
        SecurityContextHelper.loginAs(REPORTER);

        // when
        IssueDetailsDTO actual = tested.reportIssue(REPORT_REQ_DTO);

        // then
        assertThat(actual.getCreatedBy()).isEqualTo(getUserDisplayName(REPORTER));
        assertThat(actual.getAssignedTo()).isEqualTo(getUserDisplayName(TECHNICIAN));
        assertThat(actual.getCreatedAt()).isBefore(LocalDateTime.now());
        assertThat(actual.getSeverity()).isEqualTo(LOW_SEVERITY);
        assertThat(actual.getDescription()).isEqualTo(DESCRIPTION);
        assertThat(actual.getSummary()).isEqualTo(SUMMARY);
        assertThat(actual.getStatus()).isEqualTo(ASSIGNED_STATUS);
        assertThat(actual.getUuid()).isNotNull();
        assertThat(actual.getUpdatedAt()).isNull();

        this.issueUuid = actual.getUuid();
    }

    @Test
    public void shouldRetrieveAndPatchIssue() {
        // given 1
        SecurityContextHelper.loginAs(REPORTER);
        tested.reportIssue(REPORT_REQ_DTO);

        SecurityContextHelper.loginAs(TECHNICIAN);

        // when 1
        PageWrapperDTO<IssueDisplayDTO> issues = tested.getPagedUserIssues(0, 20, null);
        Collection<IssueDisplayDTO> actual1 = issues.getData();

        // then 2
        assertThat(actual1).hasSize(1);

        // given 2
        IssueDisplayDTO issue = actual1.stream().findFirst().get();

        IssueDetailsDTO actual2 = tested.getIssueDetails(issue.getUuid());

        // then 2
        assertThat(actual2.getStatus()).isEqualTo(ASSIGNED_STATUS);
        assertThat(actual2.getAssignedTo()).isEqualTo(getUserDisplayName(TECHNICIAN));

        // when 3
        IssueDetailsDTO actual3 = tested.patchIssue(actual2.getUuid().toString(), PATCH_REQ_DTO);

        assertThat(actual3.getStatus()).isEqualTo(IN_PROGRESS_STATUS);
        assertThat(actual3.getSeverity()).isEqualTo(MEDIUM_SEVERITY);
        assertThat(actual3.getUpdatedAt()).isNotNull();
    }

}
