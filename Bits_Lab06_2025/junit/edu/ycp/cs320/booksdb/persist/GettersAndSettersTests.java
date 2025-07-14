package edu.ycp.cs320.booksdb.persist;

import static org.junit.Assert.*;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;

import edu.ycp.cs320.booksdb.model.Shot;
import edu.ycp.cs320.booksdb.model.Session;
import edu.ycp.cs320.booksdb.model.Game;
import edu.ycp.cs320.booksdb.model.Frame;
import edu.ycp.cs320.booksdb.model.Event;
import edu.ycp.cs320.booksdb.model.Establishment;

public class GettersAndSettersTests {

	private Shot shot;
	private Session session;
	private Game game;
	private Frame frame;
	private Event event;
	private Establishment establishment;

	@Before
	public void setUp() {
		shot = new Shot();
		session = new Session();
		game = new Game();
		frame = new Frame();
		event = new Event();
		establishment = new Establishment();
	}

	@Test
	public void testSetAndGetShotNumber() {
		shot.setShotNumber("1");
		assertEquals("1", shot.getShotNumber());
	}

	@Test
	public void testSetAndGetGameID() {
		shot.setGameID(101);
		assertEquals(101, shot.getGameID());
	}

	@Test
	public void testSetAndGetFrameNumber() {
		shot.setFrameNumber(5);
		assertEquals(5, shot.getFrameNumber());
	}

	@Test
	public void testSetAndGetCount() {
		shot.setCount("9");
		assertEquals("9", shot.getCount());
	}

	@Test
	public void testSetAndGetLeave() {
		shot.setLeave("7-10 split");
		assertEquals("7-10 split", shot.getLeave());
	}

	@Test
	public void testSetAndGetScore() {
		shot.setScore("X");
		assertEquals("X", shot.getScore());
	}

	@Test
	public void testSetAndGetType() {
		shot.setType("Strike");
		assertEquals("Strike", shot.getType());
	}

	@Test
	public void testSetAndGetBoard() {
		shot.setBoard("15");
		assertEquals("15", shot.getBoard());
	}

	@Test
	public void testSetAndGetLane() {
		shot.setLane("Lane 4");
		assertEquals("Lane 4", shot.getLane());
	}

	@Test
	public void testSetAndGetBall() {
		shot.setBall("Storm Nova");
		assertEquals("Storm Nova", shot.getBall());
	}

	@Test
	public void testSetAndGetLeague() {
		session.setLeague("York League");
		assertEquals("York League", session.getLeague());
	}

	@Test
	public void testSetAndGetSeason() {
		session.setSeason("Winter 2025");
		assertEquals("Winter 2025", session.getSeason());
	}

	@Test
	public void testSetAndGetHouse() {
		session.setHouse("Suburban Lanes");
		assertEquals("Suburban Lanes", session.getHouse());
	}

	@Test
	public void testSetAndGetWeek() {
		session.setWeek(12);
		assertEquals(12, session.getWeek());
	}

	@Test
	public void testSetAndGetScheduled() {
		Date date = Date.valueOf("2025-01-15");
		session.setScheduled(date);
		assertEquals(date, session.getScheduled());
	}

	@Test
	public void testSetAndGetBowled() {
		Date date = Date.valueOf("2025-01-16");
		session.setBowled(date);
		assertEquals(date, session.getBowled());
	}

	@Test
	public void testSetAndGetOpponent() {
		session.setOpponent("Team Rocket");
		assertEquals("Team Rocket", session.getOpponent());
	}

	@Test
	public void testSetAndGetStart() {
		session.setStart(5);
		assertEquals(5, session.getStart());
	}

	@Test
	public void testSetAndGetBallSession() {
		session.setBall("Storm Fate");
		assertEquals("Storm Fate", session.getBall());
	}

	@Test
	public void testSetAndGetGameScores() {
		session.setGameOneScore(200);
		session.setGameTwoScore(215);
		session.setGameThreeScore(180);
		assertEquals(200, session.getGameOneScore());
		assertEquals(215, session.getGameTwoScore());
		assertEquals(180, session.getGameThreeScore());
	}

	@Test
	public void testSetAndGetLgStats() {
		session.setLgTotal(600);
		session.setLgGames(3);
		session.setLgAve(200.0);
		assertEquals(600, session.getLgTotal());
		assertEquals(3, session.getLgGames());
		assertEquals(200.0, session.getLgAve(), 0.001);
	}

	@Test
	public void testSetAndGetWinsLosses() {
		session.setWins(5.5f);
		session.setLosses(2.5f);
		assertEquals(5.5f, session.getWins(), 0.001);
		assertEquals(2.5f, session.getLosses(), 0.001);
	}

	@Test
	public void testSetAndGetSparesAndMisses() {
		session.setSpares(15);
		session.setMisses(3);
		assertEquals(15, session.getSpares());
		assertEquals(3, session.getMisses());
	}

	@Test
	public void testSetAndGetStrikeStats() {
		session.setStrikes(10);
		session.setStrikePer(50.0);
		session.setCarryPer(66.6);
		assertEquals(10, session.getStrikes());
		assertEquals(50.0, session.getStrikePer(), 0.001);
		assertEquals(66.6, session.getCarryPer(), 0.001);
	}

	@Test
	public void testSetAndGetConversionPer() {
		session.setConversionPer(75.0);
		assertEquals(75.0, session.getConversionPer(), 0.001);
	}

	@Test
	public void testSetAndGetPositions() {
		session.sethPosition(2);
		session.setlPosition("3rd");
		assertEquals(2, session.gethPosition());
		assertEquals("3rd", session.getlPosition());
	}

	@Test
	public void testSetAndGetGameAttributes() {
		game.setLeague("House League");
		game.setSeason("Summer 2025");
		game.setWeek("7");
		game.setDate("2025-07-04");
		game.setGame("1");
		game.setLane("4");
		game.setStrikeball("Storm Nova");
		game.setSpareball("IQ Tour");
		assertEquals("House League", game.getLeague());
		assertEquals("Summer 2025", game.getSeason());
		assertEquals("7", game.getWeek());
		assertEquals("2025-07-04", game.getDate());
		assertEquals("1", game.getGame());
		assertEquals("4", game.getLane());
		assertEquals("Storm Nova", game.getStrikeball());
		assertEquals("IQ Tour", game.getSpareball());
	}

	@Test
	public void testSetAndGetFrames() {
		game.setFrameone("X");
		game.setFrametwo("7/");
		game.setFramethree("9-");
		game.setFramefour("X");
		game.setFramefive("8/");
		game.setFramesix("X");
		game.setFrameseven("X");
		game.setFrameeight("X");
		game.setFramenine("X");
		game.setFrameten("X");
		game.setFrameeleven("X");
		game.setFrametwelve("X");
		assertEquals("X", game.getFrameone());
		assertEquals("7/", game.getFrametwo());
		assertEquals("9-", game.getFramethree());
		assertEquals("X", game.getFramefour());
		assertEquals("8/", game.getFramefive());
		assertEquals("X", game.getFramesix());
		assertEquals("X", game.getFrameseven());
		assertEquals("X", game.getFrameeight());
		assertEquals("X", game.getFramenine());
		assertEquals("X", game.getFrameten());
		assertEquals("X", game.getFrameeleven());
		assertEquals("X", game.getFrametwelve());
	}

	@Test
	public void testSetAndGetShots() {
		game.setShotone("1");
		game.setShottwo("2");
		game.setShotthree("3");
		game.setShotfour("4");
		game.setShotfive("5");
		game.setShotsix("6");
		game.setShotseven("7");
		game.setShoteight("8");
		game.setShotnine("9");
		game.setShotten("10");
		game.setShoteleven("11");
		game.setShottwelve("12");
		game.setShotthirteen("13");
		game.setShotfourteen("14");
		game.setShotfifteen("15");
		game.setShotsixteen("16");
		game.setShotseventeen("17");
		game.setShoteighteen("18");
		game.setShotnineteen("19");
		game.setShottwenty("20");
		game.setShottwentyone("21");
		game.setShottwentytwo("22");
		game.setShottwentythree("23");
		assertEquals("1", game.getShotone());
		assertEquals("2", game.getShottwo());
		assertEquals("3", game.getShotthree());
		assertEquals("4", game.getShotfour());
		assertEquals("5", game.getShotfive());
		assertEquals("6", game.getShotsix());
		assertEquals("7", game.getShotseven());
		assertEquals("8", game.getShoteight());
		assertEquals("9", game.getShotnine());
		assertEquals("10", game.getShotten());
		assertEquals("11", game.getShoteleven());
		assertEquals("12", game.getShottwelve());
		assertEquals("13", game.getShotthirteen());
		assertEquals("14", game.getShotfourteen());
		assertEquals("15", game.getShotfifteen());
		assertEquals("16", game.getShotsixteen());
		assertEquals("17", game.getShotseventeen());
		assertEquals("18", game.getShoteighteen());
		assertEquals("19", game.getShotnineteen());
		assertEquals("20", game.getShottwenty());
		assertEquals("21", game.getShottwentyone());
		assertEquals("22", game.getShottwentytwo());
		assertEquals("23", game.getShottwentythree());
	}
	
	@Test
	public void testSetAndGetStrikeBall() {
		frame.setStrikeBall("X");
		assertEquals("X", frame.getStrikeBall());
	}

	@Test
	public void testSetAndGetSpareBall() {
		frame.setSpareBall("7");
		assertEquals("7", frame.getSpareBall());
	}

	@Test
	public void testSetAndGetFrameNumber1() {
		frame.setFrame("1");
		assertEquals("1", frame.getFrameNumber());
	}

	@Test
	public void testSetAndGetFrameScore() {
		frame.setFrameScore("10");
		assertEquals("10", frame.getFrameScore());
	}

	@Test
	public void testSetAndGetGameID1() {
		frame.setGameID(101);
		assertEquals(101, frame.getGameID());
	}
	
	@Test
	public void testSetAndGetLongname() {
		event.setLongname("Winter Bowling League");
		assertEquals("Winter Bowling League", event.getLongname());
	}

	@Test
	public void testSetAndGetShortname() {
		event.setShortname("WBL");
		assertEquals("WBL", event.getShortname());
	}

	@Test
	public void testSetAndGetType1() {
		event.setType("League");
		assertEquals("League", event.getType());
	}

	@Test
	public void testSetAndGetEstablishment() {
		event.setEstablishment("City Lanes");
		assertEquals("City Lanes", event.getEstablishment());
	}

	@Test
	public void testSetAndGetSeason1() {
		event.setSeason("Winter 2025");
		assertEquals("Winter 2025", event.getSeason());
	}

	@Test
	public void testSetAndGetTeam() {
		event.setTeam(5);
		assertEquals(5, event.getTeam());
	}

	@Test
	public void testSetAndGetComposition() {
		event.setComposition("Mixed");
		assertEquals("Mixed", event.getComposition());
	}

	@Test
	public void testSetAndGetDay() {
		event.setDay("Monday");
		assertEquals("Monday", event.getDay());
	}

	@Test
	public void testSetAndGetTime() {
		event.setTime("7:00 PM");
		assertEquals("7:00 PM", event.getTime());
	}

	@Test
	public void testSetAndGetStart1() {
		event.setStart("2025-01-01");
		assertEquals("2025-01-01", event.getStart());
	}

	@Test
	public void testSetAndGetEnd() {
		event.setEnd("2025-03-31");
		assertEquals("2025-03-31", event.getEnd());
	}

	@Test
	public void testSetAndGetGamesPerSession() {
		event.setGamesPerSession(3);
		assertEquals(3, event.getGamesPerSession());
	}

	@Test
	public void testSetAndGetWeeks() {
		event.setWeeks(12);
		assertEquals(12, event.getWeeks());
	}

	@Test
	public void testSetAndGetPlayoffs() {
		event.setPlayoffs(4);
		assertEquals(4, event.getPlayoffs());
	}
	
	
	@Test
	public void testSetAndGetLongname1() {
		establishment.setLongname("City Lanes Bowling Alley");
		assertEquals("City Lanes Bowling Alley", establishment.getLongname());
	}

	@Test
	public void testSetAndGetShortname1() {
		establishment.setShortname("City Lanes");
		assertEquals("City Lanes", establishment.getShortname());
	}

	@Test
	public void testSetAndGetAddress() {
		establishment.setAddress("123 Main St, Anytown, USA");
		assertEquals("123 Main St, Anytown, USA", establishment.getAddress());
	}

	@Test
	public void testSetAndGetPhone() {
		establishment.setPhone("555-1234");
		assertEquals("555-1234", establishment.getPhone());
	}

	@Test
	public void testSetAndGetLanes() {
		establishment.setLanes(12);
		assertEquals(Integer.valueOf(12), establishment.getLanes());
	}

	@Test
	public void testSetAndGetType2() {
		establishment.setType("Bowling Alley");
		assertEquals("Bowling Alley", establishment.getType());
	}
}
