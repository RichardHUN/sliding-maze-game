package leaderboard;

/**
 * Represents an entry on the leaderboard.
 * Contains a player name and the number of steps needed to win the game.
 */
public class LeaderBoardEntry {
    private String playerName;
    private int nrOfSteps;


    /**
     * Creates a new {@link LeaderBoardEntry} with the given {@code player name} and {@code number of steps taken}.
     * @param playerName the name of the player
     * @param nrOfSteps the number of steps needed to win
     */
    public LeaderBoardEntry(String playerName, int nrOfSteps) {
        this.playerName = playerName;
        this.nrOfSteps = nrOfSteps;
    }

    /**
     * Creates an empty {@link LeaderBoardEntry}.
     */
    public LeaderBoardEntry() {}

    /**
     * Gives back the name of the player in the {@link LeaderBoardEntry entry}.
     * @return the name of the player
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gives back the number of steps needed to win the game
     * @return the number of steps taken
     */
    public int getNrOfSteps() {
        return nrOfSteps;
    }

    @Override
    public String toString() {
        return "LeaderBoardEntry{" +
                "playerName='" + playerName + '\'' +
                ", nrOfSteps=" + nrOfSteps +
                '}';
    }
}
