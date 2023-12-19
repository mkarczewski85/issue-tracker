package pl.wsb.issuetracker.administration.component.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessageModel {
    private String message;
    private String[] to;
    private String subject;
}
