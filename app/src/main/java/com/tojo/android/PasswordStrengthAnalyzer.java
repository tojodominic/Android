package com.tojo.android;

public class PasswordStrengthAnalyzer {

    public static int analyzePasswordStrength(String password) {
        int score = 0;

        // Length check
        if (password.length() < 8) {
            score += 10;
        } else if (password.length() >= 8 && password.length() <= 10) {
            score += 20;
        } else {
            score += 30;
        }

        // Letter check
        if (password.matches(".*[a-z].*")) {
            score += 10;
        }
        if (password.matches(".*[A-Z].*")) {
            score += 10;
        }

        // Digit check
        if (password.matches(".*\\d.*")) {
            score += 10;
        }

        // Special character check
        if (password.matches(".*[@#$%^&+=].*")) {
            score += 10;
        }

        // Bonus points for additional characters
        int additionalChars = password.length() - 8;
        if (additionalChars > 0) {
            score += additionalChars * 2;
        }

        return score;
    }
}
