
/**
 * EloApi
 * @author Nicolas Mauger
 * @date 09/01/2017
 * https://github.com/maugern/EloApi
 * Released under the WTFPL license
 */
package api;

public class Player {

	private double elo;
	private int gamesPlayed;

	/**
	 * Constructs a new Player, who never play a game and who have 1000 Elo
	 * points by default.
	 */
	public Player() {
		setElo(1000);
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
	public Player(double elo, int gamesPlayed) {
		setElo(elo);
		this.gamesPlayed = gamesPlayed;
	}

	/**
	 * Play a match between to player. Elo points of these two player will be
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
	public void versus(Player player, int result) {
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

	private void winFrom(Player player) {
		if (gamesPlayed < 9)
			setElo((elo * gamesPlayed + (player.getElo() * 1.15)) / (gamesPlayed + 1));
		else
			setElo(elo + developmentCoefficient() * (1 - probability(this, player)));
	}

	private void loseFrom(Player player) {
		if (gamesPlayed < 9)
			setElo((elo * gamesPlayed + (player.getElo() * 0.85)) / (gamesPlayed + 1));
		else
			setElo(elo + developmentCoefficient() * (0 - probability(this, player)));
	}

	private void drawFrom(Player player) {
		if (gamesPlayed < 9)
			setElo((elo * gamesPlayed + player.getElo()) / (gamesPlayed + 1));
		else
			setElo(elo + developmentCoefficient() * (0.5 - probability(this, player)));
	}

	/**
	 * Returns the Elo rank of this player in double precision
	 * 
	 * @return the Elo rank of this player
	 */
	public double getElo() {
		return elo;
	}

	private void setElo(double elo) {
		this.elo = elo;
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
	private static double probability(Player p1, Player p2) {
		return 1 / (1 + Math.pow(10, (-(p1.getElo() - p2.getElo())) / 400.0));
	}

	/**
	 * Returns a String representing this Player and its values.
	 * 
	 * @return a String representing player's Elo points, FIDE rating and the
	 *         number of games he played.
	 */
	@Override
	public String toString() {
		return elo + " elo points with " + gamesPlayed + " games played";
	}
}
