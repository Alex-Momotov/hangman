package hangman.hangman;

/**
 * Acts as AJAX container for the message from server to client containing information about a single game session,
 * whether ongoing or finished. Returned as part of the array list of previous played games, needed for recreation
 * of the management page.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
public class HistoryResponse {

    private String secretWord;
    private String status;
    private String guessProgress;

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGuessProgress() {
        return guessProgress;
    }

    public void setGuessProgress(String guessProgress) {
        this.guessProgress = guessProgress;
    }
}
