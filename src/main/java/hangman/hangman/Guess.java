package hangman.hangman;

/**
 * Acts as AJAX container for the message from client to server containing new guess letter.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
public class Guess {

    private char value;

    public Guess(char value) {
        this.value = value;
    }

    public Guess() {

    }

    public char getValue() {
        return value;
    }

    public void setValue(char newGuess) {
        this.value = newGuess;
    }

}

