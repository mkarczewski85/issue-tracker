package pl.wsb.issuetracker.user.components;

import org.springframework.stereotype.Component;
import pl.wsb.issuetracker.user.model.PasswordRules;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Component
public class UserPasswordGenerateComponent {

    private static final Random RANDOM = new SecureRandom();

    String generatePassword(final PasswordRules rules) {
        final List<Character> charList = Stream
                .concat(getRandomSpecialCharacters(rules.getAmountOfSpecialChars()),
                        Stream.concat(getRandomNumbers(rules.getAmountOfNumbers()), Stream.concat(
                                getUppercaseLetter(rules.getAmountOfUppercaseLetters()),
                                getLowercaseLetter(rules.getAmountOfLowercaseLetters()))))
                .collect(toList());
        Collections.shuffle(charList);
        return charList.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    private Stream<Character> getRandomSpecialCharacters(int amount) {
        final IntStream integers = RANDOM.ints(amount, 33, 45);
        return integers.mapToObj(i -> (char) i);
    }

    private Stream<Character> getRandomNumbers(int amount) {
        final IntStream integers = RANDOM.ints(amount, 48, 58);
        return integers.mapToObj(i -> (char) i);
    }

    private Stream<Character> getUppercaseLetter(int amount) {
        final IntStream integers = RANDOM.ints(amount, 65, 91);
        return integers.mapToObj(i -> (char) i);
    }

    private Stream<Character> getLowercaseLetter(int amount) {
        final IntStream integers = RANDOM.ints(amount, 97, 123);
        return integers.mapToObj(i -> (char) i);
    }

}
