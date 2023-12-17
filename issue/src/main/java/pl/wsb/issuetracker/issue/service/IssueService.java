package pl.wsb.issuetracker.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.issuetracker.issue.IssueClient;
import pl.wsb.issuetracker.issue.common.MappingHelper;
import pl.wsb.issuetracker.issue.component.*;
import pl.wsb.issuetracker.issue.dto.*;

import java.util.Collection;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueService implements IssueClient {

    private final ReportIssueComponent reportIssueComponent;
    private final QueryIssueComponent queryIssueComponent;
    private final PatchIssueComponent patchIssueComponent;
    private final PublishIssueCommentComponent publishIssueCommentComponent;
    private final QueryIssueCommentComponent queryIssueCommentComponent;

    @Override
    @Transactional
    public IssueDetailsDTO reportIssue(final ReportIssueRequestDTO reqDTO) {
        return MappingHelper.toIssueDetailsDTO(reportIssueComponent.reportIssue(reqDTO));
    }

    @Override
    @Transactional(readOnly = true)
    public IssueDetailsDTO getIssue(final UUID uuid) {
        return MappingHelper.toIssueDetailsDTO(queryIssueComponent.getIssue(uuid));
    }

    @Override
    @Transactional
    public IssueDetailsDTO patchIssue(final UUID uuid,
                                      final PatchIssueRequestDTO reqDTO) {
        return MappingHelper.toIssueDetailsDTO(patchIssueComponent.patchIssue(uuid, reqDTO));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<IssueDisplayDTO> getUserIssues(final IssueFiltersDTO filter) {
        return queryIssueComponent.getUserIssues(filter).stream()
                .map(MappingHelper::toIssueDisplayDTO)
                .toList();
    }

    @Override
    @Transactional
    public IssueCommentDTO publishIssueComment(final UUID uuid,
                                               final PublishIssueCommentRequestDTO reqDTO) {
        return MappingHelper.toIssueCommentDTO(publishIssueCommentComponent.publishIssueComment(uuid, reqDTO));
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<IssueCommentDTO> getIssueComments(UUID uuid) {
        return queryIssueCommentComponent.getIssueComments(uuid).stream()
                .map(MappingHelper::toIssueCommentDTO)
                .toList();
    }

}
