package pl.wsb.issuetracker.administration.component;

import pl.wsb.issuetracker.administration.component.model.EmailMessageModel;

public interface EmailMessageSender {

    void sendEmail(EmailMessageModel email);

}
