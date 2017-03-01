package test.api;

/**
 * Test class of {@link main.api.Player Player class}
 * @author Nicolas Mauger
 * @date 01 mar. 2017
 * https://github.com/maugern/EloApi
 * Released under the WTFPL license
 */

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import main.api.Player;

public class PlayerTest {

	@Test
	public void test_draw() {
		// Testing the draw case
		Player player1 = new Player(1800, 55);
		Player player2 = new Player(2005, 100);

		player1.versus(player2, 0);
		
		assertEquals(1805,player1.getElo(),0.5);
		assertEquals(2000,player2.getElo(),0.5);
	}
	
	@Test
	public void test_win() {
		// Testing the win case
		Player player1 = new Player(1800, 55);
		Player player2 = new Player(2005, 100);
		
		player1.versus(player2, 1);
		
		assertEquals(1815,player1.getElo(),0.5);
		assertEquals(1990,player2.getElo(),0.5);
	}
	
	@Test
	public void test_lose() {
		// Testing the lose case
		Player player1 = new Player(1800, 55);
		Player player2 = new Player(2005, 100);

		player1.versus(player2, -1);
		
		assertEquals(1795,player1.getElo(),0.5);
		assertEquals(2010,player2.getElo(),0.5);
	}
	
	@Test
	public void test_not_yet_ranked() {
		// Testing the case where a player is unrated
		Player player1 = new Player();
		Player player2 = new Player(2005, 100);

		player1.versus(player2, 0);
		assertEquals(2005,player1.getElo(),0.5);

		player1.versus(player2, 1);
		assertEquals(2155,player1.getElo(),0.5);
		
		player1.versus(player2, -1);
		assertEquals(2003,player1.getElo(),0.5);
	}
	
	@Test
	public void test_not_yet_ranked_players() {
		// Testing the case where two players are unrated
		Player player1 = new Player();
		Player player2 = new Player();
		
		player1.versus(player2, 0);
		assertEquals(1000,player1.getElo(),0.5);
	}
}
