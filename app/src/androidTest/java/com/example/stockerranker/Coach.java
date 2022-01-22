import java.lang.Math;

public class Coach {
    private String first_name;
    private String last_name;
    private String school;
    private double winLossRatio;
    private int numberOfGames;
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
