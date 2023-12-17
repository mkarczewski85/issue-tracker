package pl.wsb.issuetracker.api.controllers;

import lombok.RequiredArgsConstructor;
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
    public IssueDetailsDTO reportIssue(@RequestBody final ReportIssueRequestDTO reqDTO) {
        return issueClient.reportIssue(reqDTO);
    }

    @GetMapping
    public Collection<IssueDisplayDTO> getUserIssues(@RequestBody final IssueFiltersDTO filters) {
        return issueClient.getUserIssues(filters);
    }

    @GetMapping("/{uuid}")
    public IssueDetailsDTO getIssueDetails(@PathVariable("uuid") final String uuid) {
        return issueClient.getIssue(UUID.fromString(uuid));
    }

    @PatchMapping("/{uuid}")
    public IssueDetailsDTO patchIssue(@PathVariable("uuid") final String uuid,
                                      @RequestBody PatchIssueRequestDTO reqDTO) {
        return issueClient.patchIssue(UUID.fromString(uuid), reqDTO);
    }

}
