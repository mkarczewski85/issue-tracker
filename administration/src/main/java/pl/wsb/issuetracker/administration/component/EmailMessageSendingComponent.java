package pl.wsb.issuetracker.administration.component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.administration.component.model.EmailMessageModel;

@Profile("prod")
@Component
@Slf4j
@RequiredArgsConstructor
public class EmailMessageSendingComponent implements EmailMessageSender {

    @Value("${spring.mail.username}")
    private String senderAddress;

    private final JavaMailSender javaMailSender;

    public void sendEmail(final EmailMessageModel email) {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(senderAddress);
            helper.setTo(email.getTo());
            helper.setSubject(email.getSubject());
            helper.setText(email.getMessage(), true);
        } catch (final MessagingException e) {
            log.error(e.getMessage(), e);
        }
        javaMailSender.send(mimeMessage);
    }

}
