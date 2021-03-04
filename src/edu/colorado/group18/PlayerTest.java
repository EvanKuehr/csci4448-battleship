package edu.colorado.group18;
import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

//NOTE: tests for cards, deck, balance, etc. will be added before these features are fully implemented in a future milestone
public class PlayerTest {
    private Player p1;

    @Before
    public void setUp() {
        Ship[] fleet1 = {new Ship("ship1",2), new Ship("ship2", 3)};
        p1 = new Player(fleet1);
    }

    @Test
    public void testGetFleet() {
        assertEquals("edu.colorado.group18.Ship", p1.getFleet()[0].getClass().getName());
    }

    @Test
    public void testGetBoard() {
        assertEquals("edu.colorado.group18.Board", p1.getBoard().getClass().getName());
    }

    @Test
    public void checkPlaceValidHorizShip(){
        assertTrue(p1.placeShip(p1.getFleet()[0], 0, 0, 'h'));
    }

    @Test
    public void checkPlaceInvalidHorizShip1(){ //Prevents out of bounds ship
        assertFalse(p1.placeShip(p1.getFleet()[0], 0, 8, 'h'));
    }

    @Test
    public void checkPlaceInvalidHorizShip2(){ //Prevents placing ship on top of another
        p1.placeShip(p1.getFleet()[0], 0, 0, 'h');
        assertFalse(p1.placeShip(p1.getFleet()[1], 0, 0, 'h'));
    }

    @Test
    public void checkPlaceValidVertShip(){
        assertTrue(p1.placeShip(p1.getFleet()[0], 0, 0, 'v'));
    }

    @Test //Prevents out of bounds ship
    public void checkPlaceInvalidVertShip1(){
        assertFalse(p1.placeShip(p1.getFleet()[0], 9, 0, 'v'));
    }

    @Test //Prevents placing ship on top of another
    public void checkPlaceInvalidVertShip2(){
        p1.placeShip(p1.getFleet()[0], 0, 0, 'v');
        assertFalse(p1.placeShip(p1.getFleet()[1], 1, 0, 'v'));
    }

    @Test
    public void checkStrikeOpponent() {
        Ship[] fleet2 = {new Ship("ship3",2)};
        Player p2 = new Player(fleet2);
        p2.placeShip(p2.getFleet()[0], 0, 0, 'h');

        boolean[] results;
        results = p1.strike(p2, 0, 1);

        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status
    }

    @Test
    public void checkMissStrike() {
        boolean[] results;
        p1.placeShip(p1.getFleet()[0], 0, 7, 'v');
        results = p1.receiveStrike(0, 0);
        assertFalse(results[0]); //hit status
        assertFalse(results[1]); //sink status
    }

    @Test
    public void checkHitStrike() {
        boolean[] results;
        p1.placeShip(p1.getFleet()[0], 1, 3, 'h');
        results = p1.receiveStrike(1, 4);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status
    }

    @Test
    public void checkSinkShip() {
        boolean[] results;
        p1.placeShip(p1.getFleet()[0], 0, 9, 'v');

        results = p1.receiveStrike(1, 9);
        assertTrue(results[0]); //hit status
        assertFalse(results[1]); //sink status

        results = p1.receiveStrike(0, 9);
        assertTrue(results[0]); //hit status
        assertTrue(results[1]); //sink status
    }

    @Test
    public void checkShouldSurrender() {
        p1.placeShip(p1.getFleet()[0], 0, 0, 'h');
        p1.placeShip(p1.getFleet()[1], 0, 2, 'h');
        for (int j=0; j < 5; j++) {
            assertFalse(p1.shouldSurrender());
            p1.receiveStrike(0, j);
        }
        assertTrue(p1.shouldSurrender());
    }

    //helper function
    public Player createRadarEnemy() {
        Ship[] fleet = {new Ship("ship3",2), new Ship("ship4", 3), new Ship("ship5", 3)};
        Player p = new Player(fleet);
        p.placeShip(p.getFleet()[0], 3, 2, 'h');
        p.placeShip(p.getFleet()[1], 5, 4, 'v');
        p.placeShip(p.getFleet()[2], 0, 0, 'v');
        return p;
    }

    //helper function
    public void strikeBeforeRadar(Player opponent) {
        p1.strike(opponent, 0, 0);
        p1.strike(opponent, 1, 0);
        p1.strike(opponent, 2, 0);
    }

    @Test
    public void checkCanUseRadar() {
        Player p2 = createRadarEnemy();

        assertFalse(p1.canUseRadar()); //can't use radar before sinking a ship
        strikeBeforeRadar(p2); //sinks an enemy ship
        assertTrue(p1.canUseRadar());
        useRadar(0,0);
        assertTrue(p1.canUseRadar());
        useRadar(0,0);
        assertFalse(p1.canUseRadar()); //radar can only be used up to two times
    }

    @Test
    public void checkUseRadar1() {
        Player p2 = createRadarEnemy();
        int[][] expectedBoard = {
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1, 0,-1,-1,-1,-1,-1},
                {-1,-1,-1, 2, 0, 0,-1,-1,-1,-1},
                {-1,-1, 0, 0, 0, 0, 0,-1,-1,-1},
                {-1,-1,-1, 0, 2, 0,-1,-1,-1,-1},
                {-1,-1,-1,-1, 2,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
        };

        strikeBeforeRadar(p2);
        assertEquals(expectedBoard, useRadar(4,4));
    }

    @Test
    public void checkUseRadar2() {
        Player p2 = createRadarEnemy();
        int[][] expectedBoard = {
                { 2, 0, 0,-1,-1,-1,-1,-1,-1,-1},
                { 2, 0,-1,-1,-1,-1,-1,-1,-1,-1},
                { 2,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1},
                {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}
        };

        strikeBeforeRadar(p2);
        assertEquals(expectedBoard, useRadar(0,0));
    }
}

