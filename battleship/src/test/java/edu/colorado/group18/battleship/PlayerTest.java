package edu.colorado.group18.battleship;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

//NOTE: tests for cards, deck, balance, etc. will be added before these features are fully implemented in a future milestone
public class PlayerTest {
    private Player p1;

    @Before
    public void setUp() {
        Ship[] fleet1 = {new Ship("ship1",2,-1), new Ship("ship2", 3,-1)};
        p1 = new Player(fleet1);
    }

    @Test
    public void testGetFleet() {
        assertTrue(p1.getFleet() instanceof Ship[]);
    }

    @Test
    public void testGetBoard() {
        assertTrue(p1.getBoard() instanceof Board);
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
        Ship[] fleet2 = {new Ship("ship3",2,-1)};
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

    @Test
    public void dualBoardPlayerPlaceSubmarines() {
        Ship[] fleet = {new Submarine(), new Submarine(), new Submarine(), new Submarine()};
        DualBoardPlayer p2 = new DualBoardPlayer(fleet);
        assertTrue(p2.placeShip(fleet[0], 2, 2, 'v', true));
        assertTrue(p2.placeShip(fleet[1], 2, 2, 'v', false));
        assertTrue(p2.placeShip(fleet[2], 5, 5, 'h', true));
        assertTrue(p2.placeShip(fleet[3], 5, 5, 'h', false));
    }

    @Test
    public void abilityPlayerRemoveCard() {
        AbilityPlayer aPlayer = new AbilityPlayer(p1.getFleet(),1);
        Card missile = new Card("Strike", 1,new Missile());
        Card torpedo = new Card("Torpedo",2, new Torpedo());

        assertTrue(aPlayer.buyCard(missile)); //buy a card
        assertFalse(aPlayer.buyCard(torpedo)); //can't buy a card, inventory full
        assertTrue(aPlayer.removeCard("Strike")); //Can use the card, in their inventory
        assertFalse(aPlayer.removeCard("Strike")); //Can't use it, no longer in their inventory
    }
}

