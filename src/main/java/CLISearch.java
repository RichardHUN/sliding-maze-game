import model.Directions;
import model.SlideMazeState;
import puzzle.solver.BreadthFirstSearch;

public class CLISearch{
    public static void main(String[] args) {
        new BreadthFirstSearch<Directions.Direction>().solveAndPrintSolution(new SlideMazeState());
        //new BreadthFirstSearch<Directions.Direction>().solve(new SlideMazeState());
    }
}