import java.lang.Math;

public class Coach {
    private String first_name;
    private String last_name;
    private String school;
    private double winLossRatio;
    private int numberOfGames;
    private int winMargin;
    private int lossMargin;
    private double playerRating;
    private int numPlayersRated;
    private double finalRating;

    public static String getName() {
        return this.first_name + " " + this.last_name;
    }

    public static String getTeam() {
        return this.school;
    }

    public static double getWinLossRating() {
        return this.winLossRatio * 5.0 / 2.0;
    }

    public static void updateWinLossRatio(int newMargin) {
        int numberOfWins = Math.round(this.winLossRatio * this.numberOfGames);
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
    
    public static void updatePlayerRating(double newRating) {
        double totalRating = ((double) this.numPlayersRated) * this.playerRating;
        this.playerRating = (this.playerRating + newRating) / totalRating;
    }
    
    public static void updateFinalRating(int newMargin) {
        this.finalRating = (getWinLossRating(updateWinLossRatio(newMargin)) 
                + this.playerRating) / 2.0 ;
    }
}
