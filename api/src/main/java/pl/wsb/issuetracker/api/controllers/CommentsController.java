package pl.wsb.issuetracker.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.issue.IssueClient;
import pl.wsb.issuetracker.issue.dto.IssueCommentDTO;
import pl.wsb.issuetracker.issue.dto.PublishIssueCommentRequestDTO;

import java.util.Collection;
import java.util.UUID;

@RestController
@Validated
@PreAuthorize("hasRole('ROLE_REPORTER') or hasRole('ROLE_TECHNICIAN')")
@RequestMapping(CommentsController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class CommentsController {

    static final String REST_API_BASE_PATH = "${rest.prefix}${rest.secured.path}/issues";

    private final IssueClient issueClient;

    @PostMapping("/{uuid}/comments")
    public IssueCommentDTO publishIssueComment(@PathVariable("uuid") final String uuid,
                                               @RequestBody @Valid @NotNull final PublishIssueCommentRequestDTO reqDTO) {
        return issueClient.publishIssueComment(UUID.fromString(uuid), reqDTO);
    }

    @GetMapping("/{uuid}/comments")
    public Collection<IssueCommentDTO> getIssueComments(@PathVariable("uuid") final String uuid) {
        return issueClient.getIssueComments(UUID.fromString(uuid));
    }

}
