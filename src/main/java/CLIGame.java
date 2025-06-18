import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import leaderboard.LeaderBoardEntry;
import model.Directions;
import model.PlayableSlideMazeGame;
import model.SlideMazeState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * CLI playable version of the game.
 * Implement {@link PlayableSlideMazeGame}.
 */
public class CLIGame implements PlayableSlideMazeGame {
    private static final Logger LOGGER = LogManager.getLogger(CLIGame.class);
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Request all needed information from the console,
     * and prints all relevant information to the console.
     */
    public static void main(String[] args) {
        CLIGame game = new CLIGame();
        game.init();
        while(scanner.hasNextLine()){
            String line = scanner.nextLine();
            try {
                game.makeMove(Directions.Direction.valueOf(line.toUpperCase()));
            } catch (IllegalArgumentException e) {
                System.out.println("No such direction: " + line);
            }
        }
    }

    /**
     * Initializes the game.
     * Asks for the name of the player, sets the player name in the {@link SlideMazeState game state},
     * and prints tha starting state.
     */
    @Override
    public void start() {
        System.out.println("Your name:");
        state.setPlayerName(scanner.nextLine());
        System.out.println(state);
        System.out.println("Next move:");
    }

    /**
     * Prints the new state to the console.
     */
    @Override
    public void afterMove() {
        System.out.println(state);
        System.out.println("Next move:");
    }

    /**
     * Prints the number of steps taken to the console, restart the game if asked for.
     */
    @Override
    public void gameOver() {
        System.out.println("You won!");
        System.out.println("Number of steps taken: " + state.getNrOfSteps());
        System.out.println("Type restart to play again or leaderboard to see the leaderboard!");
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("restart")){
                main(new String[0]);
                break;
            }
            if (line.equalsIgnoreCase("leaderboard")){
                showLeaderBoard();
            }
        }
    }

    private void showLeaderBoard() {
        ObjectMapper mapper = new ObjectMapper();
        List<LeaderBoardEntry> leaderBoard;
        try {
             leaderBoard = mapper.readValue(
                    new File("leaderboard/leaderboard.json"),
                    new TypeReference<>(){}
            );
        } catch (IOException e) {
            LOGGER.error("Cannot open leaderboard.json.{}{}", System.lineSeparator(), e);
            throw new RuntimeException(e);
        }

        leaderBoard.stream()
                .sorted(Comparator.comparingInt(LeaderBoardEntry::getNrOfSteps))
                .forEach(System.out::println);

        System.out.println("Type restart to play again!");
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            if (line.equalsIgnoreCase("restart")){
                main(new String[0]);
            }
        }
    }
}
