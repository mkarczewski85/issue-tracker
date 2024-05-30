package pl.wsb.issuetracker.api.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.wsb.issuetracker.common.pagination.PageWrapperDTO;
import pl.wsb.issuetracker.issue.IssueClient;
import pl.wsb.issuetracker.issue.dto.*;

import java.util.UUID;

@RestController
@Validated
@RequestMapping(IssuesController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class IssuesController {
    static final String REST_API_BASE_PATH = "${rest.prefix}${rest.secured.path}" + "/issues";

    private final IssueClient issueClient;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_REPORTER')")
    public IssueDetailsDTO reportIssue(@RequestBody @Valid @NotNull final ReportIssueRequestDTO reqDTO) {
        return issueClient.reportIssue(reqDTO);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_REPORTER') or hasRole('ROLE_TECHNICIAN')")
    public PageWrapperDTO<IssueDisplayDTO> getPagedUserIssues(@RequestParam(defaultValue = "0") @Min(0) final int offset,
                                                              @RequestParam(defaultValue = "20") @Min(20) final int limit,
                                                              @RequestBody(required = false) final IssueFiltersDTO filters) {
        return issueClient.getUserIssues(filters, offset, limit);
    }

    @GetMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_REPORTER') or hasRole('ROLE_TECHNICIAN')")
    public IssueDetailsDTO getIssueDetails(@PathVariable("uuid") final String uuid) {
        return issueClient.getIssue(UUID.fromString(uuid));
    }

    @PatchMapping("/{uuid}")
    @PreAuthorize("hasRole('ROLE_TECHNICIAN')")
    public IssueDetailsDTO patchIssue(@PathVariable("uuid") final String uuid,
                                      @RequestBody @NotNull PatchIssueRequestDTO reqDTO) {
        return issueClient.patchIssue(UUID.fromString(uuid), reqDTO);
    }

}
