package pl.wsb.issuetracker.administration.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.administration.component.model.EmailMessageModel;

@Profile("test")
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailMessageSendingMockComponent implements EmailMessageSender {
    @Override
    public void sendEmail(EmailMessageModel email) {
        log.info("Message sent details: " + email);
    }
}
