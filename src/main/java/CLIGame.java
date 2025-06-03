import model.Directions;
import model.SlideMazeState;

import java.util.Scanner;

public class CLIGame {
    public static void main(String[] args) {
        SlideMazeState state = new SlideMazeState();
        Scanner scanner = new Scanner(System.in);
        String line;

        System.out.println(state);
        while(scanner.hasNextLine()){
            line = scanner.nextLine();
            switch (line.toLowerCase()){
                case "up":
                    state.makeMove(Directions.Direction.UP);
                    break;
                case "down":
                    state.makeMove(Directions.Direction.DOWN);
                    break;
                case "right":
                    state.makeMove(Directions.Direction.RIGHT);
                    break;
                case "left":
                    state.makeMove(Directions.Direction.LEFT);
                    break;
                default:
                    System.out.println("No such direction: " + line);
            }
            System.out.println(state);
            if(state.isSolved()){
                System.out.println("You won!");
                return;
            }
        }
    }
}
