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
    void placeWall() {
        //Given
        PlayingSurface playingSurface = new PlayingSurface();
        PlayingSurface playingSurface1;

        //When
        playingSurface.placeWall(playingSurface.getBallPosition(),
                Directions.Direction.RIGHT);
        playingSurface1 = playingSurface.clone();
        playingSurface1.placeWall(
                Position.of(playingSurface1.getBallPosition().x(), 6),
                Directions.Direction.UP);
        playingSurface1.moveBall(Directions.Direction.DOWN);
        playingSurface1.moveBall(Directions.Direction.RIGHT);
        playingSurface1.moveBall(Directions.Direction.UP);
        playingSurface1.moveBall(Directions.Direction.LEFT);

        //Then
        assertFalse(playingSurface.canMove(Directions.Direction.RIGHT));
        for(Directions.Direction direction: Directions.directions()){
            if(direction.equals(Directions.Direction.RIGHT)){
                break;
            }
            assertTrue(playingSurface.canMove(direction));
        }
        assertFalse(playingSurface1.canMove(Directions.Direction.LEFT));
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