package hangman.hangman;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Launches the Handman Spring Boot web application.
 *
 * @author Alex Momotov
 * @version 1.0
 * */
@SpringBootApplication
public class HangmanApplication {

    /**
     * Launches the Handman application.
     * */
    public static void main(String[] args) {
        SpringApplication.run(HangmanApplication.class, args);
    }
}
