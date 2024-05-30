package pl.wsb.issuetracker.administration.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.administration.component.model.EmailMessageModel;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;

@Component
@RequiredArgsConstructor
public class AccountOwnerNotificationComponent {

    private final EmailMessageCreateComponent messageCreateComponent;
    private final EmailMessageSender sendingComponent;

    public void notifyAccountCreated(final User user) {
        final UserCredentials credentials = user.getUserCredentials();
        final EmailMessageModel email = messageCreateComponent.getWelcomeEmail(
                user.getEmail(), credentials.getRawPassword());
        sendingComponent.sendEmail(email);
    }

    public void notifyCredentialsReset(final User user) {
        final UserCredentials credentials = user.getUserCredentials();
        final EmailMessageModel email = messageCreateComponent.getCredentialsChangedEmail(
                user.getEmail(), credentials.getRawPassword());
        sendingComponent.sendEmail(email);
    }

}
