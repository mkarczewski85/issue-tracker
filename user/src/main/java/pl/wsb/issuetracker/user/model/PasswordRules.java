package pl.wsb.issuetracker.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRules {
    private int amountOfSpecialChars;
    private int amountOfNumbers;
    private int amountOfUppercaseLetters;
    private int amountOfLowercaseLetters;
}
