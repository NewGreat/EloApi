package main.api;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * Main class of EloApi
 * @author Nicolas Mauger
 * @date 01 mar. 2017
 * https://github.com/maugern/EloApi
 * Released under the WTFPL license
 */
public class Elo {

    @Getter (AccessLevel.PUBLIC) private double elo;
    @Getter (AccessLevel.PUBLIC) private int gamesPlayed;

    /**
     * 
     * Constructs a new default Elo rank.
     * By default player will had 1000 points and 0 gamePlayed.
     */ 
    public Elo() {
        this.elo = 1000;
        this.gamesPlayed = 0;
    }

    /**
     * Constructs a new Player whose Elo rank and and whose number of played
     * games are specified.
     * 
     * @param elo
     *            Player's Elo points
     * @param gamesPlayed
     *            number of ranked played games
     */
    public Elo(double elo, int gamesPlayed) {
        if (elo < 0 || gamesPlayed < 0)
            throw new IllegalArgumentException("The number of game played or elo score must be superior to zero");
        this.elo = elo;
        this.gamesPlayed = gamesPlayed;
    }

    /**
     * Play a match between two player. Elo points of these two player will be
     * calculate depending on the result of the match, their previous rank and
     * the development coefficient K. The new Elo rank is given by the formula
     * En+1 = En + K * (result - p(D)) If the player has less than 9 played
     * game, the Elo rank is given by the average of the games played.
     * 
     * @param player
     *            the opponent
     * @param result
     *            value higher than zero for a win, equals to zero for a draw
     *            and less than zero for a lose to opponent
     */
    public void versus(Elo player, int result) {
        if (result > 0) {
            this.winFrom(player);
            player.loseFrom(this);
        } else if (result == 0) {
            this.drawFrom(player);
            player.drawFrom(this);
        } else {
            this.loseFrom(player);
            player.winFrom(this);
        }
        this.addPlayedMatch();
        player.addPlayedMatch();
    }

    private void winFrom(Elo player) {
        if (gamesPlayed < 9)
            elo = (elo * gamesPlayed + (player.elo * 1.15)) / (gamesPlayed + 1);
        else
            elo = (elo + developmentCoefficient() * (1 - probability(this, player)));
    }

    private void loseFrom(Elo player) {
        if (gamesPlayed < 9)
            elo = (elo * gamesPlayed + (player.elo * 0.85)) / (gamesPlayed + 1);
        else
            elo = (elo + developmentCoefficient() * (0 - probability(this, player)));
    }

    private void drawFrom(Elo player) {
        if (gamesPlayed < 9)
            elo = (elo * gamesPlayed + player.elo) / (gamesPlayed + 1);
        else
            elo = (elo + developmentCoefficient() * (0.5 - probability(this, player)));
    }

    private void addPlayedMatch() {
        ++gamesPlayed;
    }

    private int developmentCoefficient() {
        if (gamesPlayed < 30)
            return 40;
        else if (elo < 2400)
            return 20;
        else
            return 10;
    }

    /**
     * The inverse function p(D) gives the probability of player 1 win depending
     * on the difference D (D = Elo points of player1 - Elo points of player 2)
     * Formula: p(D) = 1 / ( 1 + 1O^(-D/400)) Reminder: p(D) ∈ [0;1]
     * 
     * @param p1
     *            The player 1
     * @param p1
     *            The player 2
     * @return The probability of player 1 winning
     */
    private static double probability(Elo p1, Elo p2) {
        return 1 / (1 + Math.pow(10, (-(p1.elo - p2.elo)) / 400.0));
    }

}
