package pl.wsb.issuetracker.issue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import pl.wsb.issuetracker.api.controllers.CommentsController;
import pl.wsb.issuetracker.helper.SecurityContextHelper;
import pl.wsb.issuetracker.issue.dto.IssueCommentDTO;
import pl.wsb.issuetracker.issue.dto.IssueDetailsDTO;
import pl.wsb.issuetracker.issue.dto.PublishIssueCommentRequestDTO;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static pl.wsb.issuetracker.helper.TestDataHelper.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles({"test"})
public class CommentsApiIntegrationTest {

    public static final PublishIssueCommentRequestDTO COMMENT1_REQ_DTO = PublishIssueCommentRequestDTO.builder()
            .comment("Some comment 1")
            .build();

    public static final PublishIssueCommentRequestDTO COMMENT2_REQ_DTO = PublishIssueCommentRequestDTO.builder()
            .comment("Some comment 2")
            .build();

    @Autowired
    private CommentsController tested;

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
    public void shouldAddComments() {
        // given 1
        SecurityContextHelper.loginAs(REPORTER);
        IssueDetailsDTO issue = issueClient.reportIssue(IssueApiIntegrationTest.REPORT_REQ_DTO);
        this.issueUuid = issue.getUuid();

        // when 1
        Collection<IssueCommentDTO> actual1 = tested.getIssueComments(issueUuid.toString());

        // then 1
        assertThat(actual1).isEmpty();

        // when 2
        IssueCommentDTO actual2 = tested.publishIssueComment(issueUuid.toString(), COMMENT1_REQ_DTO);
        assertThat(actual2.getComment()).isEqualTo(COMMENT1_REQ_DTO.getComment());
        assertThat(actual2.getPublishedBy()).isEqualTo(getUserDisplayName(REPORTER));
        assertThat(actual2.getPublishedAt()).isNotNull();

        // given 3
        SecurityContextHelper.loginAs(TECHNICIAN);

        // when 3
        IssueCommentDTO actual3 = tested.publishIssueComment(issueUuid.toString(), COMMENT2_REQ_DTO);

        // then 3
        assertThat(actual3.getComment()).isEqualTo(COMMENT2_REQ_DTO.getComment());
        assertThat(actual3.getPublishedBy()).isEqualTo(getUserDisplayName(TECHNICIAN));
        assertThat(actual3.getPublishedAt()).isNotNull();

        // when 4
        Collection<IssueCommentDTO> actual4 = tested.getIssueComments(issueUuid.toString());

        // then 4
        assertThat(actual4).hasSize(2);

    }

}
