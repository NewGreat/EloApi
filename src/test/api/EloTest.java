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

import main.api.Elo;
import main.api.EloRank;

public class EloTest {

	@Test
	public void test_draw() {
		// Testing the draw case
		Elo player1 = new Elo(1800, 55);
		Elo player2 = new Elo(2005, 100);

		player1.versus(player2, 0);
		
		assertEquals(1805,player1.getElo(),0.5);
		assertEquals(2000,player2.getElo(),0.5);
	}
	
	@Test
	public void test_win() {
		// Testing the win case
		Elo player1 = new Elo(1800, 55);
		Elo player2 = new Elo(2005, 100);
		
		player1.versus(player2, 1);
		
		assertEquals(1815,player1.getElo(),0.5);
		assertEquals(1990,player2.getElo(),0.5);
	}
	
	@Test
	public void test_lose() {
		// Testing the lose case
		Elo player1 = new Elo(1800, 55);
		Elo player2 = new Elo(2005, 100);

		player1.versus(player2, -1);
		
		assertEquals(1795,player1.getElo(),0.5);
		assertEquals(2010,player2.getElo(),0.5);
	}
	
	@Test
	public void test_not_yet_ranked() {
		// Testing the case where a player is unrated
		Elo player1 = new Elo();
		Elo player2 = new Elo(2005, 100);

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
		Elo player1 = new Elo();
		Elo player2 = new Elo();
		
		player1.versus(player2, 0);
		assertEquals(1000,player1.getElo(),0.5);
	}
	
	@Test
	public void test_enum_rank() {
	    assertEquals(EloRank.getRank(123d), EloRank.J);
	    assertEquals(EloRank.getRank(2700d), EloRank.GrandMaster);
	}
}
