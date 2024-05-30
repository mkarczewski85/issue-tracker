package pl.wsb.issuetracker.administration.component;

import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.administration.component.model.EmailMessageModel;

@Component
public class EmailMessageCreateComponent {

    private final static String WELCOME_EMAIL_SUBJECT = "[WSB Issue Tracker] Utworzenie konta";
    private final static String WELCOME_EMAIL_BODY = """
            "<p>Dzień dobry,</p><div>utworzono konto z danymi logowania:</div><div><b>Login:</b> %s</div><div><b>Hasło:</b> %s</div>"
            + "<br><div> Zapraszamy do skorzystania z aplikacji.
            """;
    private final static String CREDENTIALS_EMAIL_SUBJECT = "[WSB Issue Tracker] Zmiana hasłą";
    private final static String CREDENTIALS_EMAIL_BODY = """
            "<p>Dzień dobry,</p><div>zmieniono dane logowania:</div><div><b>Login:</b> %s</div><div><b>Hasło:</b> %s</div>"
            + "<br><div> Zapraszamy do skorzystania z aplikacji.
            """;

    public EmailMessageModel getWelcomeEmail(final String login, final String rawPassword) {
        return EmailMessageModel.builder()
                .to(new String[] { login })
                .message(String.format(WELCOME_EMAIL_BODY, login, rawPassword))
                .subject(WELCOME_EMAIL_SUBJECT)
                .build();
    }

    public EmailMessageModel getCredentialsChangedEmail(final String login, final String rawPassword) {
        return EmailMessageModel.builder()
                .to(new String[] { login })
                .message(String.format(CREDENTIALS_EMAIL_BODY, login, rawPassword))
                .subject(CREDENTIALS_EMAIL_SUBJECT)
                .build();
    }

}
