package hangman.hangman;

/**
 * Acts as AJAX container for the message from server to client. Communicates outcome of last submitted guess,
 * holds progress of the secret word guessing, and an optional message to the user.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
public class Response {

    private String guessProgress;
    private String userMessage;
    private String attemptedString;
    private int wrongGuessCount;

    /**
     * Getter for secret word progress string.
     * @return String representing current guess progress.
     * */
    public String getGuessProgress() {
        return guessProgress;
    }

    /**
     * Setter for updating guess progress.
     * @param guessProgress new guess progress string.
     * */
    public void setGuessProgress(String guessProgress) {
        this.guessProgress = guessProgress;
    }

    /**
     * Getter for user message.
     * @return String containing user message.
     * */
    public String getUserMessage() {
        return userMessage;
    }

    /**
     * Setter for changing user message.
     * @param userMessage new user message.
     * */
    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    /**
     * Getter for letters already attempted in current game session.
     * @return String representing all letters already attempted.
     * */
    public String getAttemptedString() {
        return attemptedString;
    }

    /**
     * Setter for changing string of attempted letters.
     * @param attemptedString new string of attempted letters.
     * */
    public void setAttemptedString(String attemptedString) {
        this.attemptedString = attemptedString;
    }

    /**
     * Getter for current count of wrong guesses.
     * @return int of current wrong guess count.
     * */
    public int getWrongGuessCount() {
        return wrongGuessCount;
    }

    /**
     * Setter for changing count of wrong guesses for current game session.
     * @param wrongGuessCount new wrong guess count.
     * */
    public void setWrongGuessCount(int wrongGuessCount) {
        this.wrongGuessCount = wrongGuessCount;
    }
}
