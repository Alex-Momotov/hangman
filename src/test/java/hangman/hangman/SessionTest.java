package hangman.hangman;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Some, but not all unit tests for the Session class. More unit and integration tests should be added, given more time.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
public class SessionTest {

    Session session = new Session();

    @Before
    public void setUp() throws Exception {
        session.setSecretWord("Cat");
        session.setGuessProgress("_ _ _");
    }

    /**
     * Check that correct guesses update guess-progress string.
     * Check that once all letters are guessed, game status turns to finished.
     * */
    @Test
    public void goodGuessWorks() throws Exception {
        Guess goodGuess = new Guess('c');
        session.hangmanLogic(goodGuess);
        assertEquals("C _ _", session.getGuessProgress());
        Guess goodGuess1 = new Guess('a');
        session.hangmanLogic(goodGuess1);
        assertEquals("C a _", session.getGuessProgress());
        Guess goodGuess2 = new Guess('t');
        session.hangmanLogic(goodGuess2);
        assertEquals("C a t", session.getGuessProgress());
        assertTrue(session.isGameFinished());
    }

    /**
     * Check incorrect guesses increment the wrong-guess-counter.
     * Check incorrect guesses do not update the guess-progress string.
     * */
    @Test
    public void badGuessWorks() throws Exception {
        assertEquals(0, session.getWrongGuessCount());
        assertEquals("_ _ _", session.getGuessProgress());
        Guess badGuess = new Guess('x');
        session.hangmanLogic(badGuess);
        assertEquals(1, session.getWrongGuessCount());
        assertEquals("_ _ _", session.getGuessProgress());
    }

    /**
     * Check correct guesses result in the right user message.
     * Check correct guesses update attempted-letters-string in the response message.
     * Check correct guesses update guess-progress-string in the response message.
     * */
    @Test
    public void responseGoodGuess() throws Exception {
        Guess goodGuess = new Guess('t');
        Response response = session.hangmanLogic(goodGuess);
        assertEquals("Correct!", response.getUserMessage());
        assertEquals("_ _ t", response.getGuessProgress());
        assertEquals("[t]", response.getAttemptedString());
    }

    /**
     * Check incorrect guesses result in the right user message.
     * Check incorrect guesses update attempted-letters-string in the response message.
     * Check incorrect guesses do not change guess-progress-string in the response message.
     * */
    @Test
    public void responseBadGuess() throws Exception {
        Guess goodGuess = new Guess('x');
        Response response = session.hangmanLogic(goodGuess);
        assertEquals("Nope...", response.getUserMessage());
        assertEquals("_ _ _", response.getGuessProgress());
        assertEquals("[x]", response.getAttemptedString());
        assertEquals(1, response.getWrongGuessCount());
    }

    /**
     * Check game status turns to finished after six incorrect guesses.
     * */
    @Test
    public void gameOverWorks() throws Exception {
        assertEquals(0, session.getWrongGuessCount());
        char[] badLetters = {'z', 'x', 'v', 'b', 'm', 'k'};
        for (int i = 0; i < 6; ++i) {
            Guess badGuess = new Guess(badLetters[i]);
            session.hangmanLogic(badGuess);
        }
        assertEquals(6, session.getWrongGuessCount());
        assertTrue(session.isGameFinished());
    }

    /**
     * Check user message within the response is correct when game is won.
     * */
    @Test
    public void responseGameWon() throws Exception {
        Guess goodGuess1 = new Guess('c');
        Guess goodGuess2 = new Guess('a');
        Guess goodGuess3 = new Guess('t');
        session.hangmanLogic(goodGuess1);
        session.hangmanLogic(goodGuess2);
        Response response = session.hangmanLogic(goodGuess3);
        assertEquals("Congratulations, you won!", response.getUserMessage());
    }

    /**
     * Check user message within the response is correct when game is lost.
     * */
    @Test
    public void responseGameLost() throws Exception {
        char[] badLetters = {'z', 'x', 'v', 'b', 'm'};
        for (int i = 0; i < 5; ++i) {
            Guess badGuess = new Guess(badLetters[i]);
            session.hangmanLogic(badGuess);
        }
        Guess lastBadGuess = new Guess('k');
        Response response = session.hangmanLogic(lastBadGuess);
        assertEquals("Game Over.", response.getUserMessage());
    }
}