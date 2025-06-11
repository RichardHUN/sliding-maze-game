package model;

import leaderboard.LeaderBoardEntry;
import leaderboard.LeaderBoardManager;

/**
 * Represents an abstract playable {@link SlideMazeState Slide Maze} game.
 * Contains three methods that need implementation:
 * {@code start}, {@code afterMove}, and {@code gameOver}.
 * Contains four default methods:
 * {@code init}, {@code makeMove}, {@code writeToLeaderBoard} and {@code reset}.
 * {@code init} calls {@code reset} and {@code start}.
 * After each call of the {@code makeMove}, {@code afterMove} gets called.
 * After that, if the game is won, the {@code writeToLeaderBoard} is called, and after that
 * {@code gameOver} gets called.
 */
public interface PlayableSlideMazeGame {
    SlideMazeState state = new SlideMazeState();
    LeaderBoardManager leaderBoardManager = LeaderBoardManager.getInstance();

    /**
     * Resets the {@link SlideMazeState game state}, and call {@code start()}.
     */
    default void init(){
        reset();
        start();
    }

    /**
     * Initializes the game
     */
    void start();

    /**
     * Makes a move in the given {@link model.Directions.Direction Direction}.
     * After the move is done, calls {@code betweenMoves()}, and checks if the game is over.
     * If it its, {@code gameOver()} gets called.
    * @param direction the {@link model.Directions.Direction direction} in which the move will be made
     * */
    default void makeMove(Directions.Direction direction){
        state.makeMove(direction);
        afterMove();
        if (!state.isSolved()){
            return;
        }
        writeToLeaderBoard();
        gameOver();
    }

    /**
     * Defines what should be done between moves.
     */
    void afterMove();

    /**
     * Writes the players result to the leaderboard.
     */
    default void writeToLeaderBoard(){
        leaderBoardManager.writeEntry(new LeaderBoardEntry(state.getPlayerName(), state.getNrOfSteps()));
    }

    /**
     * Defines what should be done if the game is over.
     */
    void gameOver();

    /**
     * Resets the {@link SlideMazeState game state} to the initial state.
     */
    default void reset() {
        state.resetToInitialState();
    }

}
