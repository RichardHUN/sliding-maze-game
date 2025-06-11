package leaderboard;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the leaderboard. Can write {@link LeaderBoardEntry LeaderBoardEntries}.
 */
public class LeaderBoardManager {
    private final ObjectMapper objectMapper;
    private final File file;
    private static final Logger LOGGER = LogManager.getLogger();

    private static LeaderBoardManager instance = null;

    /**
     * Gives back the only existing instance of {@link LeaderBoardManager}.
     * If it doesn't exist, it gets instantiated first, then returned.
     * @return the instance of {@link LeaderBoardManager}
     */
    public static LeaderBoardManager getInstance() {
        if(instance == null) {
            instance = new LeaderBoardManager();
        }
        return instance;
    }

    private LeaderBoardManager(){
        objectMapper = new ObjectMapper();
        file = new File("leaderboard" + File.separator + "leaderboard.json");

        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()){
                LOGGER.error("Couldn't create directory {}", file.getParent());
            }
        }
    }

    /**
     * Writes an entry to the leaderboard.
     * @param entry the {@link LeaderBoardEntry} that will be appended to the leaderboard
     */
    public void writeEntry(LeaderBoardEntry entry) {
        LOGGER.info("Writing leaderboard entry: {}", entry);
        try {
            ArrayList<LeaderBoardEntry> entries =
                    file.length() != 0 ?
                    new ArrayList<>(objectMapper.readValue(file, new TypeReference<List<LeaderBoardEntry>>(){})) :
                    new ArrayList<>();
            entries.add(entry);
            objectMapper.writeValue(file, entries);
        } catch (Exception e) {
            LOGGER.error("Couldn't write entry to leaderboard: {}{}{}", entry, System.lineSeparator(), e);
        }
    }
}
