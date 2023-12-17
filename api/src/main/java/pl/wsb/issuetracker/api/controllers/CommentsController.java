package pl.wsb.issuetracker.api.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(CommentsController.REST_API_BASE_PATH)
@RequiredArgsConstructor
public class CommentsController {

    static final String REST_API_BASE_PATH = "${rest.prefix}/issues";

    @PostMapping("/{uuid}/comments")
    public void publishIssueComment() {

    }

    @GetMapping("/{uuid}/comments")
    public void getIssueComments() {

    }

}
