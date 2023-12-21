package pl.wsb.issuetracker.issue;

import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.issue.dto.*;

import java.util.Collection;
import java.util.UUID;

public interface IssueClient {

    IssueDetailsDTO reportIssue(ReportIssueRequestDTO reqDTO);

    IssueDetailsDTO getIssue(UUID uuid);

    IssueDetailsDTO patchIssue(UUID uuid, PatchIssueRequestDTO reqDTO);

    PageWrapperDTO<IssueDisplayDTO> getUserIssues(IssueFiltersDTO filters, int offset, int limit);

    IssueCommentDTO publishIssueComment(UUID uuid, PublishIssueCommentRequestDTO reqDTO);

    Collection<IssueCommentDTO> getIssueComments(UUID uuid);

    void deleteByUuid(UUID uuid);

}
