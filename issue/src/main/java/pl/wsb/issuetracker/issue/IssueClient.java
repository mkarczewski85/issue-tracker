package pl.wsb.issuetracker.issue;

import pl.wsb.issuetracker.issue.dto.*;

import java.util.Collection;
import java.util.UUID;

public interface IssueClient {

    IssueDetailsDTO reportIssue(ReportIssueRequestDTO reqDTO);

    IssueDetailsDTO getIssue(UUID uuid);

    IssueDetailsDTO patchIssue(UUID uuid, PatchIssueRequestDTO reqDTO);

    Collection<IssueDisplayDTO> getUserIssues(IssueFiltersDTO filters);

    IssueCommentDTO publishIssueComment(UUID uuid, PublishIssueCommentRequestDTO reqDTO);

    Collection<IssueCommentDTO> getIssueComments(UUID uuid);

}
