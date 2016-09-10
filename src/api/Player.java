package api;

public class Player {

	private double elo;
	private int games_played;
	
	/**
	 * Constructs a new Player, who never play a game and who have 1000 Elo points by default.
	 */
	public Player() {
		setElo(1000);
		this.games_played = 0;
	}
	
	/**
	 * Constructs a new Player whose Elo rank and and whose number of played games are specified.
	 * @param elo Player's Elo points
	 * @param games_played number of ranked played games
	 */
	public Player(double elo, int games_played) {
		setElo(elo);
		this.games_played = games_played;
	}

	/**
	 * Play a match between to player. Elo points of these two player will be calculate depending on the result of the match, their previous rank and the development coefficient K.
	 * The new Elo rank is given by the formula En+1 = En + K * (result  - p(D))
	 * If the player has less than 9 played game, the Elo rank is given by the average of the games played.
	 * @param player the opponent
	 * @param result value higher than zero for a win, equals to zero for a draw and less than zero for a lose to opponent
	 */
	public void versus(Player player, int result) {
		if (result > 0) {
			this.win_from(player);
			player.lose_from(this);
		}
		else if (result == 0) {
			this.draw_from(player);
			player.draw_from(this);
		}
		else {
			this.lose_from(player);
			player.win_from(this);
		}
		this.addPlayedMatch();
		player.addPlayedMatch();
	}
	
	private void win_from(Player player) {
		if(games_played < 9)
			setElo((elo * games_played + (player.getElo() * 1.15)) / (games_played + 1));
		else
			setElo(elo + development_coefficient() * (1 - probability(this, player)) );
	}
	
	private void lose_from(Player player) {
		if(games_played < 9)
			setElo((elo * games_played + (player.getElo() * 0.85)) / (games_played + 1));
		else
			setElo(elo + development_coefficient() * (0 - probability(this, player)) );
	}
	
	private void draw_from(Player player) {
		if(games_played < 9)
			setElo((elo * games_played + player.getElo()) / (games_played + 1));
		else
			setElo(elo + development_coefficient() * (0.5 - probability(this, player)) );
	}
	
	/**
	 * Returns the Elo rank of this player in double precision
	 * @return the Elo rank of this player
	 */
	public double getElo() {
		return elo;
	}
	
	private void setElo(double elo) {
		this.elo = elo;
	}
	
	private void addPlayedMatch() {
		++games_played;
	}

	private int development_coefficient () {
		if (games_played < 30)
			return 40;
		else if (elo < 2400)
			return 20;
		else
			return 10;
	}
	
	/**
	 * The inverse function p(D) gives the probability of player 1 win depending on the difference D (D = Elo points of player1 - Elo points of player 2)
	 * Formula: p(D) =  1 / ( 1 + 1O^(-D/400))
	 * Reminder: p(D) ∈  [0;1]
	 * @param p1 The player 1
	 * @param p1 The player 2
	 * @return The probability of player 1 winning
	 */
	private static double probability (Player p1, Player p2) {
		return 1 / (1 + Math.pow(10, (-(p1.getElo() - p2.getElo()))/400.0));
	}
	
	/**
	 * Returns a String representing this Player and its values.
	 * @return a String representing player's Elo points, FIDE rating and the number of games he played.
	 */
	public String toString() {
		String s = "Elo points: " + getElo();
		if (elo >= 2400)
			s += " (Senior master)";
		else if (elo >= 2200)
			s += " (National master)";
		else if (elo >= 2000)
			s += " (Expert)";
		else if (elo >= 1800)
			s += " (Class A)";
		else if (elo >= 1600)
			s += " (Class B)";
		else if (elo >= 1400)
			s += " (Class C)";
		else if (elo >= 1200)
			s += " (Class D)";
		else if (elo >= 1000)
			s += " (Class E)";
		else if (elo >= 800)
			s += " (Class F)";
		else if (elo >= 600)
			s += " (Class G)";
		else if (elo >= 400)
			s += " (Class H)";
		else if (elo >= 200)
			s += " (Class I)";
		else
			s += " (Class J)";
		s += " with " + games_played + " games played";
		
		return s;
	}
}
