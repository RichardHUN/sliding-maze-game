package model;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SlideMazeStateTest {

    @Test
    void rightMove() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.RIGHT);

        //Then
        assertEquals(Position.of(1,6), state.getBallPosition());
    }

    @Test
    void leftMove() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.LEFT);

        //Then
        assertEquals(Position.of(1,0), state.getBallPosition());
    }

    @Test
    void upMove() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.UP);

        //Then
        assertEquals(Position.of(0,4), state.getBallPosition());
    }

    @Test
    void downMove() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.DOWN);

        //Then
        assertEquals(Position.of(4,4), state.getBallPosition());
    }

    @Test
    void startingPositionsNotSolved() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //Then
        assertFalse(state.isSolved());
    }

    @Test
    void goalPositionSolved(){
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.RIGHT);
        state.makeMove(Directions.Direction.DOWN);
        state.makeMove(Directions.Direction.LEFT);
        state.makeMove(Directions.Direction.DOWN);
        state.makeMove(Directions.Direction.LEFT);
        state.makeMove(Directions.Direction.UP);
        state.makeMove(Directions.Direction.LEFT);
        state.makeMove(Directions.Direction.DOWN);
        state.makeMove(Directions.Direction.LEFT);
        state.makeMove(Directions.Direction.UP);
        state.makeMove(Directions.Direction.RIGHT);
        state.makeMove(Directions.Direction.UP);
        state.makeMove(Directions.Direction.RIGHT);
        state.makeMove(Directions.Direction.UP);
        state.makeMove(Directions.Direction.LEFT);
        state.makeMove(Directions.Direction.DOWN);
        state.makeMove(Directions.Direction.RIGHT);
        state.makeMove(Directions.Direction.DOWN);

        //Then
        assertTrue(state.isSolved());
    }

    @Test
    void allMovesLegal() {
        //Given
        SlideMazeState state = new SlideMazeState();

        //Then
        assertEquals(Directions.directions(), state.getLegalMoves());
        for(Directions.Direction direction: Directions.directions()){
            assertTrue(state.isLegalMove(direction));
        }
    }

    @Test
    void downMoveNotLegal(){
        //Given
        SlideMazeState state = new SlideMazeState();

        //When
        state.makeMove(Directions.Direction.DOWN);
        Set<Directions.Direction> upMoveIllegal = Directions.directions();
        upMoveIllegal.remove(Directions.Direction.DOWN);

        //Then
        assertEquals(upMoveIllegal, state.getLegalMoves());
        assertFalse(state.isLegalMove(Directions.Direction.DOWN));
    }

    @Test
    void cloneTest(){
        SlideMazeState state1 = new SlideMazeState();
        SlideMazeState state2 = state1.clone();

        assertTrue(state1 != state2);
        assertTrue(state1.getClass().equals(state2.getClass()));
        assertTrue(state2.equals(state1));
    }

}