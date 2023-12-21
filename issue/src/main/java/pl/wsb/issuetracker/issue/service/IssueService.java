package pl.wsb.issuetracker.issue.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
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
    private final DeleteIssueComponent deleteIssueComponent;

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
    public PageWrapperDTO<IssueDisplayDTO> getUserIssues(final IssueFiltersDTO filter,
                                                         final int offset,
                                                         final int limit) {
        final Page<IssueDisplayDTO> page = queryIssueComponent.getUserIssues(filter, offset, limit)
                .map(MappingHelper::toIssueDisplayDTO);
        return PageWrapperDTO.from(page);
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

    @Override
    @Transactional
    public void deleteByUuid(final UUID uuid) {
        deleteIssueComponent.deleteIssue(uuid);
    }

}
