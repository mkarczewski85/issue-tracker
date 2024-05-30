package pl.wsb.issuetracker.user.components;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.jpa.entity.User;
import pl.wsb.issuetracker.jpa.entity.UserCredentials;
import pl.wsb.issuetracker.user.model.PasswordRules;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class UserCredentialsCreateComponent {

    private static final int AMOUNT_OF_LOWERCASE_LETTERS = 2;
    private static final int AMOUNT_OF_NUMBERS = 2;
    private static final int AMOUNT_OF_SPECIAL_CHARS = 2;
    private static final int AMOUNT_OF_UPPERCASE_LETTERS = 2;
    public static final PasswordRules PASSWORD_RULES = PasswordRules.builder()
            .amountOfLowercaseLetters(AMOUNT_OF_LOWERCASE_LETTERS)
            .amountOfNumbers(AMOUNT_OF_NUMBERS)
            .amountOfSpecialChars(AMOUNT_OF_SPECIAL_CHARS)
            .amountOfUppercaseLetters(AMOUNT_OF_UPPERCASE_LETTERS).build();
    private static final int NUMBER_OF_SALT_BYTES = 32;
    private static final int NUMBER_OF_ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    private static final String HASH_ALGORITHM = "PBKDF2WithHmacSHA512";

    private final UserPasswordGenerateComponent passwordGenerateComponent;

    public UserCredentials createUserCredentials(final User user) {
        final String password = passwordGenerateComponent.generatePassword(PASSWORD_RULES);
        byte[] salt = generateSecureRandomSalt();
        final String base64Salt = Base64.getEncoder().encodeToString(salt);
        final String passwordHash = generateSaltedHash(password, salt);
        return UserCredentials.builder()
                .passwordHash(passwordHash).rawPassword(password).salt(base64Salt).user(user).build();
    }

    public void recreateUserCredentials(final User user) {
        final String newPassword = passwordGenerateComponent.generatePassword(PASSWORD_RULES);
        final UserCredentials userCredentials = user.getUserCredentials();
        byte[] salt = generateSecureRandomSalt();
        final String base64Salt = Base64.getEncoder().encodeToString(salt);
        final String passwordHash = generateSaltedHash(newPassword, salt);
        userCredentials.setPasswordHash(passwordHash);
        userCredentials.setSalt(base64Salt);
    }

    private byte[] generateSecureRandomSalt() {
        return SecureRandom.getSeed(NUMBER_OF_SALT_BYTES);
    }

    String generateSaltedHash(final String rawPassword, byte[] saltPhrase) {
        try {
            final SecretKeyFactory skf = SecretKeyFactory.getInstance(HASH_ALGORITHM);
            final PBEKeySpec spec = new PBEKeySpec(rawPassword.toCharArray(), saltPhrase,
                    NUMBER_OF_ITERATIONS, KEY_LENGTH);
            final SecretKey key = skf.generateSecret(spec);
            byte[] bytesOfHash = key.getEncoded();
            return Base64.getEncoder().encodeToString(bytesOfHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
