package model.reading;

import model.*;

/**
 * Contains a single method needed to read {@link PlayingSurface PlayingSurface} from file.
 */
public class PlayingSurfaceReader {
    /**
     * Reads a {@link PlayingSurface PlayingSurface} from file.
     * The JSON file should contain an array, the first object being the start
     * @param path path of the file(inside the resources folder)
     * @return the {@link PlayingSurface PlayingSurface} read from the file
     */
    public static PlayingSurface readPlayingSurface(String path){
        PlayingSurfaceData playingSurfaceData = JsonReader.readJsonFromResources(path);

        var playingSurface = new PlayingSurface();

        playingSurface.setStart(playingSurfaceData.startPosition());
        playingSurface.setGoal(playingSurfaceData.goalPosition());

        for (Wall wall : playingSurfaceData.walls()) {
            Position position = wall.position();
            Directions.Direction side = wall.side();
            playingSurface.placeWall(position, side);
        }

        return playingSurface;
    }
}
