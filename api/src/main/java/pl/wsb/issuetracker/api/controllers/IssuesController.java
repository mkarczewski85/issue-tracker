package pl.wsb.issuetracker.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.issue.IssueClient;
import pl.wsb.issuetracker.issue.dto.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping(IssuesController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class IssuesController {
    static final String REST_API_BASE_PATH = "${rest.prefix}" + "/issues";

    private final IssueClient issueClient;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REPORTER')")
    public IssueDetailsDTO reportIssue(@RequestBody final ReportIssueRequestDTO reqDTO) {
        return issueClient.reportIssue(reqDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_REPORTER') or hasRole('ROLE_TECHNICIAN')")
    public Collection<IssueDisplayDTO> getUserIssues(@RequestBody final IssueFiltersDTO filters) {
        return issueClient.getUserIssues(filters);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_REPORTER') or hasRole('ROLE_TECHNICIAN')")
    public IssueDetailsDTO getIssueDetails(@PathVariable("uuid") final String uuid) {
        return issueClient.getIssue(UUID.fromString(uuid));
    }

    @PatchMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_TECHNICIAN')")
    public IssueDetailsDTO patchIssue(@PathVariable("uuid") final String uuid,
                                      @RequestBody PatchIssueRequestDTO reqDTO) {
        return issueClient.patchIssue(UUID.fromString(uuid), reqDTO);
    }

}
