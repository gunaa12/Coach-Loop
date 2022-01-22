import java.lang.Math;
import java.util.ArrayList;

public class Coach {
    private String first_name;
    private String last_name;
    private String school;
    private double winLossRatio;
    private int numberOfGames;
    private ArrayList<ArrayList<Integer>> gamesPlayed;
    private int winMargin;
    private int lossMargin;
    private double wnLsMrgnRatio;
    private double playerRating;
    private int numPlayersRated;
    private double finalRating;

    public String getName() {
        return this.first_name + " " + this.last_name;
    }

    public String getTeam() {
        return this.school;
    }

    public double getFinalRating() {
        return this.finalRating;
    }

    public void setFinalRating(double newFinalRating) {
        this.finalRating = newFinalRating;
    }

    public double getWinLossRating() {
        return this.winLossRatio * 5.0 / 2.0;
    }

    public void updateWinLossRatio(int newMargin) {
        if (this.gamesPlayed.size() == 0) {
            ArrayList<Integer> newGameList = new ArrayList<Integer>();
            newGameList.add(newMargin > 0 ? 1:0);
            this.gamesPlayed.add(newGameList);
        }
        else if (this.gamesPlayed.get(this.gamesPlayed.size()-1).size() >= 10) {
            ArrayList<Integer> newGameList = new ArrayList<Integer>();
            newGameList.add(newMargin > 0 ? 1:0);
            this.gamesPlayed.add(newGameList);
        }
        else {
            this.gamesPlayed.get(this.gamesPlayed.size()-1).add(newMargin > 0 ? 1:0);
        }
        int numberOfWins = (int) Math.round(this.winLossRatio * this.numberOfGames);
        this.numberOfGames += 1;
        if (newMargin > 0) {
            numberOfWins += 1;
            this.winMargin += newMargin;
        }
        else {
            this.lossMargin += newMargin;
        }
        this.winLossRatio = ((double) numberOfWins) / ((double) this.numberOfGames);
    }

    public void updatePlayerRating(double newRating) {
        double totalRating = ((double) this.numPlayersRated) * this.playerRating;
        this.playerRating = (this.playerRating + newRating) / totalRating;
    }

    public void updateFinalRating(int newMargin) {
        updateWinLossRatio(newMargin);
        this.wnLsMrgnRatio = ((double) this.winMargin) / (double) (this.winMargin + this.lossMargin);
        this.finalRating = (getWinLossRating()
                + this.playerRating + this.wnLsMrgnRatio) / 3.0 ;
    }
}
