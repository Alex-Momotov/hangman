package hangman.hangman;
import java.util.HashSet;
import java.util.Random;

/**
 * Represents a single Hangman game session. Contains session parameters such as game status, secret word,
 * wrong guess count, and implements the core Hangman game logic operating on these parameters.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
public class Session {

    private boolean gameFinished;
    private int WRONG_GUESS_LIMIT;
    private int wrongGuessCount;
    private String secretWord;
    private HashSet<Object> attemptedLetters;
    private String attemptedString;
    private String guessProgress;
    private String[] words = {"Concurrency", "Parallelism", "Networking", "Encapsulation", "Development",
            "Incremental", "Connectivity", "Parametric", "Relational", "Distributed", "Theoretical"};

    /**
     * Initialises new game session with random secret word and game parameters.
     * @return Session object containing new game session data.
     * */
    public Session() {
        int randInt = new Random().nextInt(this.words.length);
        this.secretWord = words[randInt];
        this.gameFinished = false;
        this.WRONG_GUESS_LIMIT = 6;
        this.wrongGuessCount = 0;
        this.attemptedLetters = new HashSet<>();
        this.attemptedString = "[]";
        this.guessProgress = "";
        for (int i = 0; i < this.secretWord.length() - 1; ++i) this.guessProgress += "_ ";
        this.guessProgress += "_";
    }

    /**
     * Handles the primary game logic. Processes new user guess and returns response to the client.
     * @param guess Guess letter submitted by the user.
     * @return Response object containing the guess outcome and updated session data.
     * */
    public Response hangmanLogic(Guess guess) {
        Response response = new Response();
        if (this.gameFinished) {
            response = checkGameState(response);
            return response;
        } else if (this.attemptedLetters.contains(guess.getValue())) {
            response = notifyAttempted(response);
            return response;
        } else {
            response = updateAttempted(response, guess);
        }
        response = checkSecretWord(response, guess);
        response = updateGameState(response);
        return response;
    }

    /**
     * Reminds user that the game has ended and adds the appropriate user message.
     * @param response Response object in the process of being updated with new session data.
     * @return Response object with a new user message.
     * */
    private Response checkGameState(Response response) {
        String userMessage;
        if (this.wrongGuessCount >= this.WRONG_GUESS_LIMIT) {
            userMessage = "Game Over.";
            response = updateResponse(response, userMessage);
        } else {
            userMessage = "Congratulations, you won!";
            response = updateResponse(response, userMessage);
        }
        return response;
    }

    /**
     * Updates session status if the game has finished and adds appropriate message to the response.
     * @param response Response object in the process of being updated with new session data.
     * @return Response object with a new user message.
     * */
    private Response updateGameState(Response response) {
        if (this.wrongGuessCount >= this.WRONG_GUESS_LIMIT) {
            this.gameFinished = true;
            response.setUserMessage("Game Over.");
        } else if (!this.guessProgress.contains("_")) {
            this.gameFinished = true;
            response.setUserMessage("Congratulations, you won!");
        }
        return response;
    }

    /**
     * Reminds user that submitted letter has already been attempted by adding the appropriate user message.
     * @param response Response object in the process of being updated with new session data.
     * @return Response object with a new user message.
     * */
    private Response notifyAttempted(Response response) {
        String userMessage = "Letter already attempted. Try a different letter.";
        response = updateResponse(response, userMessage);
        return response;
    }

    /**
     * Updates the response object with most recent game session data and new user message.
     * @param response Response object to be updated.
     * @param userMessage New user message to be added to the response.
     * @return Response object with updated game session data and new user message.
     * */
    public Response updateResponse(Response response, String userMessage) {
        response.setGuessProgress(this.guessProgress);
        response.setAttemptedString(this.attemptedString);
        response.setWrongGuessCount(this.wrongGuessCount);
        response.setUserMessage(userMessage);
        return response;
    }

    /**
     * Adds new guess letter to the set of attempted letters and updates attempted-letters string.
     * @param response Response object in the process of being updated with new session data.
     * @param guess Guess letter submitted by the user.
     * @return Response object with the updated attempted string.
     * */
    private Response updateAttempted(Response response, Guess guess) {
        this.attemptedLetters.add(guess.getValue());
        this.attemptedString = "[";
        for (Object c : this.attemptedLetters) this.attemptedString += c + ", ";
        this.attemptedString = this.attemptedString.substring(0, this.attemptedString.length() - 2) + "]";
        response.setAttemptedString(this.attemptedString);
        return response;
    }

    /**
     * Checks guess against secret word, updates guess-progress string and wrong-guess-count, informs user of outcome.
     * @param response Response object in the process of being updated with new session data.
     * @param guess Guess letter submitted by the user.
     * @return Response object with updated session data and user message about guess outcome.
     * */
    private Response checkSecretWord(Response response, Guess guess) {
        boolean successful_guess = false;
        for (int i = 0; i < this.secretWord.length(); ++i) {
            boolean guessed = this.secretWord.toLowerCase().charAt(i) == guess.getValue();
            if (guessed) {
                successful_guess = true;
                char[] charArr = this.guessProgress.toCharArray();
                charArr[i * 2] = this.secretWord.charAt(i);
                this.guessProgress = String.valueOf(charArr);
            }
        }
        String userMessage;
        if (!successful_guess) {
            userMessage = "Nope...";
            this.wrongGuessCount += 1;
            response = updateResponse(response, userMessage);
        } else {
            userMessage = "Correct!";
            response = updateResponse(response, userMessage);
        }
        return response;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public int getWRONG_GUESS_LIMIT() {
        return WRONG_GUESS_LIMIT;
    }

    public void setWRONG_GUESS_LIMIT(int WRONG_GUESS_LIMIT) {
        this.WRONG_GUESS_LIMIT = WRONG_GUESS_LIMIT;
    }

    public int getWrongGuessCount() {
        return wrongGuessCount;
    }

    public void setWrongGuessCount(int wrongGuessCount) {
        this.wrongGuessCount = wrongGuessCount;
    }

    public String getSecretWord() {
        return secretWord;
    }

    public void setSecretWord(String secretWord) {
        this.secretWord = secretWord;
    }

    public HashSet<Object> getAttemptedLetters() {
        return attemptedLetters;
    }

    public void setAttemptedLetters(HashSet<Object> attemptedLetters) {
        this.attemptedLetters = attemptedLetters;
    }

    public String getAttemptedString() {
        return attemptedString;
    }

    public void setAttemptedString(String attemptedString) {
        this.attemptedString = attemptedString;
    }

    public String getGuessProgress() {
        return guessProgress;
    }

    public void setGuessProgress(String guessProgress) {
        this.guessProgress = guessProgress;
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }
}
