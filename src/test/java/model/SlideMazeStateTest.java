package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SlideMazeStateTest {
    private static final Logger LOGGER = LogManager.getLogger(SlideMazeStateTest.class);

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
    void nrOfSteps(){
        //Given
        var state = new SlideMazeState();
        int initialRandomNrOfSteps = new Random().nextInt(18);
        int randomNrOfSteps = initialRandomNrOfSteps;

        //When
        while (randomNrOfSteps > 0 ) {
            Directions.Direction side =
                    Directions.directions().stream().toList().get( new Random().nextInt(Directions.directions().size()) );
            if (!state.isLegalMove(side)){
                continue;
            }
            state.makeMove( side );
            randomNrOfSteps--;
        }

        //Then
        assertEquals(initialRandomNrOfSteps, state.getNrOfSteps());
    }

    @Test
    void resetToInitial(){
        //Given
        SlideMazeState state = new SlideMazeState();
        state.setPlayerName("randomNameOfARandomPlayer");
        state.makeMove(Directions.Direction.RIGHT);
        state.makeMove(Directions.Direction.DOWN);

        //When
        state.resetToInitialState();

        //Then
        assertEquals( Position.of(1,4), state.getBallPosition() );
        try {
            Field field = state.getClass().getDeclaredField("playingSurface");
            field.setAccessible(true);

            PlayingSurface playingSurface = (PlayingSurface) field.get(state);
                assertEquals( Position.of(5,2), playingSurface.getGoalPosition());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Couldn't access private field (playingSurface) through reflection.{}", e.getMessage());
        }
        assertEquals(0, state.getNrOfSteps());
        assertEquals("no name given", state.getPlayerName());
    }

    @Test
    void cloneTest(){
        SlideMazeState state1 = new SlideMazeState();
        SlideMazeState state2 = state1.clone();

        assertNotSame(state1, state2);
        assertEquals(state1.getClass(), state2.getClass());
        assertEquals(state2, state1);
    }

}