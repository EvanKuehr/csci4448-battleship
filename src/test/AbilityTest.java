package test;

import edu.colorado.group18.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


public class AbilityTest {
    private Player player;

    @Before
    public void setUp() {
        Ship[] fleet = {new Ship("ship1",2,-1), new Ship("ship2", 5,3)};
        fleet[1] = new Submarine();
        this.player = new Player(fleet);
        this.player.placeShip(this.player.getFleet()[0], 1, 1, 'h', false);
        this.player.placeShip(this.player.getFleet()[1], 2, 2, 'h', true);
    }

    @Test
    public void testMissileHit() {
        Missile missile = new Missile();
        Board board = missile.use(this.player, 1, 1);
        assertTrue(board.getCell(1, 1).getHitStatus());
    }

    @Test
    public void testMissileMiss() {
        Missile missile = new Missile();
        Board board = missile.use(this.player, 5, 5);
        assertFalse(board.getCell(1, 1).getHitStatus());
    }

    @Test
    public void testMissileOnSub() {
        Missile missile = new Missile();
        Board board = missile.use(this.player, 2, 2);
        assertTrue(board.getCell(2, 2).getHitStatus());
        assertFalse(this.player.getSubBoard().getCell(2, 2).getHitStatus());
    }

    @Test
    public void testSpaceLazer() {
        SpaceLazer spacelazer = new SpaceLazer();
        Board[] boards = spacelazer.use(this.player, 2, 2);
        assertTrue(boards[1].getCell(2, 2).getHitStatus());
    }

    @Test
    public void testSonar() {
        Sonar sonar = new Sonar();
        Board board = sonar.use(this.player, 0, 0);
        assertTrue(board.getCell(0, 0) instanceof SonarCell);
        assertTrue(board.getCell(1, 1) instanceof SonarCell);
        SonarCell firstCell = (SonarCell) board.getCell(0, 0);
        SonarCell secondCell = (SonarCell) board.getCell(1, 1);
        assertTrue(firstCell.hasShip == 0);
        assertTrue(secondCell.hasShip == 1);
    }

    @Test
    public void testMoveFleet() {
        MoveFleet mf = new MoveFleet();
        Board[] boards = mf.use(this.player, 'e');
        ShipCell firstCell = (ShipCell) boards[0].getCell(1, 2);
        ShipCell secondCell = (ShipCell) boards[1].getCell(2, 3);
        assertTrue(firstCell.getShipRef() == player.getFleet()[0]);
        assertTrue(secondCell.getShipRef() == player.getFleet()[1]);
    }

}
