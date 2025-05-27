package model.persistence;

import model.PlayingSurface;
import model.Wall;

import java.util.List;

public class PlayingSurfaceReader {
    public static PlayingSurface readPlayingSurface(String path){
        List<Wall> walls = JsonReader.readJsonFromResources(path);

        var playingSurface = new PlayingSurface();

        for(Wall wall: walls){
            playingSurface.placeWall(wall.position(), wall.side());
        }

        return playingSurface;
    }

    /*public static void main(String[] args) {
        System.out.println(PlayingSurfaceReader.readPlayingSurface("walls.json"));
    }*/
}
