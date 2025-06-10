package model.reading;

import model.PlayingSurface;
import model.PlayingSurfaceData;
import model.Wall;

import java.util.List;

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
        //List<Wall> walls = JsonReader.readJsonFromResources(path);
        PlayingSurfaceData playingSurfaceData = JsonReader.readJsonFromResources(path);

        var playingSurface = new PlayingSurface();

        playingSurface.setStart(playingSurfaceData.getStartPosition());
        playingSurface.setGoal(playingSurfaceData.getGoalPosition());
        for(Wall wall: playingSurfaceData.getWalls()){
            playingSurface.placeWall(wall.position(), wall.side());
        }

        return playingSurface;
    }
}
