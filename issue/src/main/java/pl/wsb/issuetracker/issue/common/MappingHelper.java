package pl.wsb.issuetracker.issue.common;

import pl.wsb.issuetracker.issue.dto.IssueCommentDTO;
import pl.wsb.issuetracker.issue.dto.IssueDetailsDTO;
import pl.wsb.issuetracker.issue.dto.IssueDisplayDTO;
import pl.wsb.issuetracker.jpa.entity.Issue;
import pl.wsb.issuetracker.jpa.entity.IssueComment;
import pl.wsb.issuetracker.jpa.entity.User;

public final class MappingHelper {

    private MappingHelper() {
    }

    public static IssueDetailsDTO toIssueDetailsDTO(final Issue issue) {
        return IssueDetailsDTO.builder()
                .summary(issue.getSummary())
                .description(issue.getDescription())
                .uuid(issue.getUuid())
                .severity(issue.getSeverity().name())
                .status(issue.getStatus().name())
                .createdAt(issue.getCreatedAt())
                .updatedAt(issue.getUpdatedAt())
                .assignedTo(toUserDisplayName(issue.getAssignedTo()))
                .createdBy(toUserDisplayName(issue.getReportedBy()))
                .build();
    }

    public static IssueDisplayDTO toIssueDisplayDTO(final Issue issue) {
        return IssueDisplayDTO.builder()
                .uuid(issue.getUuid().toString())
                .summary(issue.getSummary())
                .status(issue.getStatus().name())
                .severity(issue.getSeverity().name())
                .createdAt(issue.getCreatedAt())
                .build();
    }

    public static IssueCommentDTO toIssueCommentDTO(final IssueComment issueComment) {
        return IssueCommentDTO.builder()
                .comment(issueComment.getComment())
                .publishedAt(issueComment.getPublishedAt())
                .publishedBy(toUserDisplayName(issueComment.getPublishedBy()))
                .build();
    }

    private static String toUserDisplayName(final User user) {
        return user.getFirstName() + " " + user.getLastName();
    }

}
