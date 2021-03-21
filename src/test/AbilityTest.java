package test;

import edu.colorado.group18.*;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;


public class AbilityTest {
    private DualBoardPlayer player;

    @Before
    public void setUp() {
        Ship[] fleet = {
                new Ship("ship1",2,-1),
                new Submarine()
        };
        this.player = new DualBoardPlayer(fleet);
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

        Ship[] fleet = { new Minesweeper() };
        Player opponent = new DualBoardPlayer(fleet);
        opponent.placeShip(fleet[0], 0, 0, 'h');
        opponent.receiveStrike(0,0);
        opponent.receiveStrike(0,1);

        Sonar sonar = new Sonar();
        Board board = sonar.use(this.player, opponent, 0, 0);
        assertTrue(board.getCell(0, 0) instanceof SonarCell);
        assertTrue(board.getCell(1, 1) instanceof SonarCell);
        SonarCell firstCell = (SonarCell) board.getCell(0, 0);
        SonarCell secondCell = (SonarCell) board.getCell(1, 1);
        assertTrue(firstCell.hasShip == 1);
        assertTrue(secondCell.hasShip == 0);
    }

    private void moveFleetInit() {
        Ship[] fleet = {new Destroyer(), new Destroyer()};
        player = new DualBoardPlayer(fleet);
        player.getBoard().placeShip(fleet[0], 1, 1, 'v');
        player.getBoard().placeShip(fleet[1], 6, 8, 'v');
    }

    @Test
    public void testMoveFleetNorth() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet();
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(player, 'n');
        assertTrue(cell.equals(boards[0].getCell(0,1)));

        Board firstBoard = boards[0];
        boards = mf.use(player, 'n'); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetEast() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet();
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(player, 'e');
        assertTrue(cell.equals(boards[0].getCell(1,2)));

        Board firstBoard = boards[0];
        boards = mf.use(player, 'e'); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetSouth() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet();
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(player, 's');
        assertTrue(cell.equals(boards[0].getCell(2,1)));

        Board firstBoard = boards[0];
        boards = mf.use(player, 's'); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetWest() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet();
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(player, 'w');
        assertTrue(cell.equals(boards[0].getCell(1,0)));

        Board firstBoard = boards[0];
        boards = mf.use(player, 'w'); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }
}
