import model.Directions;
import model.SlideMazeState;
import puzzle.solver.BreadthFirstSearch;

/**
 * Applies the {@link BreadthFirstSearch} on the {@link SlideMazeState} in command line.
 */
public class CLISearch{
    /**
     * Applies the {@link BreadthFirstSearch} on the {@link SlideMazeState}.
     */
    public static void main() {
        new BreadthFirstSearch<Directions.Direction>().solveAndPrintSolution(new SlideMazeState());
    }
}