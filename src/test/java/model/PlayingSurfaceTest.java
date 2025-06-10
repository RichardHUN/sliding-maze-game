package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayingSurfaceTest {

    @Test
    void testClone() {
        PlayingSurface playingSurface1 = new PlayingSurface("walls.json");
        PlayingSurface playingSurface2 = playingSurface1.clone();

        assertTrue(playingSurface1 != playingSurface2);
        assertTrue(playingSurface1.getClass().equals(playingSurface2.getClass()));
        assertTrue(playingSurface2.equals(playingSurface1));
    }

    @Test
    void startingBallPosition() {
        PlayingSurface playingSurface = new PlayingSurface();

        assertEquals(Position.of(1,4), playingSurface.getBallPosition());
    }

    @Test
    void canMoveInAnyDirection() {
        PlayingSurface playingSurface = new PlayingSurface();

        for(Directions.Direction direction: Directions.directions()){
            assertTrue(playingSurface.canMove(direction));
        }
    }

    @Test
    void cantMoveInOneDirection(){
        for(Directions.Direction direction: Directions.directions()){
            //Given
            PlayingSurface playingSurface = new PlayingSurface();

            //When
            playingSurface.moveBall(direction);

            //Then
            assertFalse(playingSurface.canMove(direction));
            for(Directions.Direction givenDirection: Directions.directions()){
                if(givenDirection.equals(direction)){
                    break;
                }
                assertTrue(playingSurface.canMove(givenDirection));
            }
        }

    }

    @Test
    void placeWallEachDirection() {
        for (Directions.Direction direction: Directions.directions()){
            PlayingSurface playingSurface = new PlayingSurface();
            playingSurface.placeWall(playingSurface.getBallPosition(), direction);
            assertFalse(playingSurface.canMove(direction));
        }
    }

    @Test
    void placeWallBothSides(){
        //Given
        PlayingSurface playingSurface = new PlayingSurface();

        //When
        playingSurface.placeWall(playingSurface.getBallPosition(),
                Directions.Direction.RIGHT);
        playingSurface.placeWall(
                Position.of(playingSurface.getBallPosition().x(), 6),
                Directions.Direction.UP
        );
        playingSurface.moveBall(Directions.Direction.DOWN);
        playingSurface.moveBall(Directions.Direction.RIGHT);
        playingSurface.moveBall(Directions.Direction.UP);
        playingSurface.moveBall(Directions.Direction.LEFT);

        //Then
        assertFalse(playingSurface.canMove(Directions.Direction.LEFT));
        for(Directions.Direction direction: Directions.directions()){
            if(direction.equals(Directions.Direction.LEFT)){
                break;
            }
            assertTrue(playingSurface.canMove(direction));
        }
    }

    @Test
    void moveBall() {
        for (Directions.Direction direction: Directions.directions()){
            //Given
            PlayingSurface playingSurface = new PlayingSurface();
            Position initialBallPosition = playingSurface.getBallPosition();

            //When
            playingSurface.moveBall(direction);

            //Then
            assertEquals(
                    direction.equals(Directions.Direction.UP) ?
                            Position.of(0, initialBallPosition.y()) : (
                                direction.equals(Directions.Direction.RIGHT) ?
                                    Position.of(initialBallPosition.x(), 6) : (
                                            direction.equals(Directions.Direction.DOWN) ?
                                                Position.of(6, initialBallPosition.y()) :
                                                Position.of(initialBallPosition.x(), 0)
                                        )
                            )
                    ,
                    playingSurface.getBallPosition()
            );
        }
    }

}