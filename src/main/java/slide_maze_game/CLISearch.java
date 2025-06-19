package slide_maze_game;

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
    public static void main(String[] args) {
        new BreadthFirstSearch<Directions.Direction>().solveAndPrintSolution(new SlideMazeState());
    }
}