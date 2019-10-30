package hangman.hangman;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller which handles AJAX POST and GET requests of the guess
 * logic and redirects between game pages.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
@RestController
public class RController {

    private ArrayList<Session> sessions = new ArrayList<Session>();

    /**
     * Acts as landing method handling AJAX requests for a new game session.
     * @return Response object with initial new session parameters, including a random secret word.
     * */
    @RequestMapping(value="/newGameSession", method= RequestMethod.GET)
    public ResponseEntity<Object> newGameSession() {
        if (this.sessions.size() != 0 && !sessions.get(sessions.size()-1).isGameFinished()) {
            this.sessions.remove(sessions.size()-1);
        }
        Session newSession = new Session();
        this.sessions.add(newSession);
        Response response = new Response();
        String userMessage = "";
        response = newSession.updateResponse(response, userMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles AJAX requests for resuming an ongoing game or refreshing browser page.
     * @return Response object with ongoing game session parameters and a saved session progress.
     * */
    @RequestMapping(value="/reloadOrResume", method= RequestMethod.GET)
    public ResponseEntity<Object> reloadOrResume() {
        Session lastSession;
        if (this.sessions.size() != 0 && !this.sessions.get(this.sessions.size()-1).isGameFinished()) {
            lastSession = this.sessions.get(this.sessions.size() - 1);
        } else {
            lastSession = new Session();
            this.sessions.add(lastSession);
        }
        Response response = new Response();
        String userMessage = "";
        response = lastSession.updateResponse(response, userMessage);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Handles AJAX requests for fetching management page info to display finished and ongoing game sessions list.
     * @return array list of HistoryResponse objects, each containing details of one game session.
     * */
    @RequestMapping(value="/gamesHistory", method= RequestMethod.GET)
    public ResponseEntity<Object> gamesHistory() {
        HistoryResponse[] hist = new HistoryResponse[this.sessions.size()];
        for (int i = 0; i < this.sessions.size(); ++i) {
            HistoryResponse h = new HistoryResponse();
            h.setGuessProgress(this.sessions.get(i).getGuessProgress());
            h.setSecretWord(this.sessions.get(i).getSecretWord());
            if (!this.sessions.get(i).isGameFinished()) {
                h.setStatus("Ongoing");
            } else if (this.sessions.get(i).getWrongGuessCount() >= this.sessions.get(i).getWRONG_GUESS_LIMIT()) {
                h.setStatus("Lost");
            } else {
                h.setStatus("Won");
            }
            hist[i] = h;
        }
        return new ResponseEntity<>(hist, HttpStatus.OK);
    }

    /**
     * Handles AJAX requests for submitting new guesses of the ongoing game session.
     * @param input Contains JSON string encoding Guess object with a new guess character.
     * @return Response object containing guess outcome details and message to the user.
     * */
    @RequestMapping(value="/submitGuess", method= RequestMethod.POST)
    public ResponseEntity<Object> submitGuess(@RequestBody String input) {
        Gson gson = new Gson();
        Guess guess = gson.fromJson(input, Guess.class);
        Session lastSession = sessions.get(sessions.size()-1);
        Response response = lastSession.hangmanLogic(guess);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
