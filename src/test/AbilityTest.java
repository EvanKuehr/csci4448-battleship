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
                new Battleship(),
                new Submarine()
        };
        this.player = new DualBoardPlayer(fleet);
        this.player.placeShip(this.player.getFleet()[0], 1, 1, 'h', false);
        this.player.placeShip(this.player.getFleet()[1], 1, 2, 'v', true);
    }

    @Test
    public void placeSub() {
        assertTrue(this.player.getSubBoard().getCell(1, 2) instanceof ShipCell);
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
        spacelazer.use(this.player, 1, 2);
        Boolean surfaceShipHit = this.player.getFleet()[0].getCells()[1];
        Boolean subShipHit = this.player.getFleet()[1].getCells()[0];
        assertTrue("sub-surface ship not hit", subShipHit);
        assertTrue("surface ship not hit", surfaceShipHit);
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
        MoveFleet mf = new MoveFleet(player);
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(new MoveCommand(mf, Direction.NORTH));
        mf.undoMove();
        mf.redoMove();
        assertTrue(cell.equals(boards[0].getCell(0,1)));

        Board firstBoard = boards[0];
        boards = mf.use(new MoveCommand(mf, Direction.NORTH)); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetEast() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet(player);
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(new MoveCommand(mf, Direction.EAST));
        mf.undoMove();
        mf.redoMove();
        assertTrue(cell.equals(boards[0].getCell(1,2)));

        Board firstBoard = boards[0];
        boards = mf.use(new MoveCommand(mf, Direction.EAST)); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetSouth() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet(player);
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(new MoveCommand(mf, Direction.SOUTH));
        mf.undoMove();
        mf.redoMove();
        assertTrue(cell.equals(boards[0].getCell(2,1)));

        Board firstBoard = boards[0];
        boards = mf.use(new MoveCommand(mf, Direction.SOUTH)); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testMoveFleetWest() {
        moveFleetInit();
        MoveFleet mf = new MoveFleet(player);
        ShipCell cell = (ShipCell) player.getBoard().getCell(1, 1);
        Board[] boards = mf.use(new MoveCommand(mf, Direction.WEST));
        mf.undoMove();
        mf.redoMove();
        assertTrue(cell.equals(boards[0].getCell(1,0)));

        Board firstBoard = boards[0];
        boards = mf.use(new MoveCommand(mf, Direction.WEST)); //Can't move fleet, it would be out of bounds
        assertEquals(firstBoard, boards[0]);
    }

    @Test
    public void testSurfaceBoardMiss() {
        int row = 8;
        Torpedo torpedo = new Torpedo();
        Board board = torpedo.use(this.player, row, true);
        Cell cell = board.getCell(row, board.getY()-1);
        assertTrue("cell wasn't hit", cell.getHitStatus());
        assertFalse("there shouldn't be a ship there", cell instanceof ShipCell);
    }

    @Test
    public void testSubBoardMiss() {
        int row = 8;
        Torpedo torpedo = new Torpedo();
        Board board = torpedo.use(this.player, row, false);
        Cell cell = board.getCell(row, board.getY()-1);
        assertTrue("cell wasn't hit", cell.getHitStatus());
        assertFalse("there shouldn't be a ship there", cell instanceof ShipCell);
    }

    @Test
    public void testSurfaceBoardHit() {
        int row = 1;
        Torpedo torpedo = new Torpedo();
        Board board = torpedo.use(this.player, row, true);
        Cell cell = board.getCell(row, 1);
        assertTrue("cell wasn't hit", cell.getHitStatus());
        assertTrue("there should be a ship there", cell instanceof ShipCell);
    }

    @Test
    public void testSubBoardHit() {
        int row = 2;
        Torpedo torpedo = new Torpedo();
        Board board = torpedo.use(this.player, row, false);
        Cell cell = board.getCell(row, 2);
        assertTrue("cell wasn't hit", cell.getHitStatus());
        assertTrue("there should be a ship there", cell instanceof ShipCell);
    }
}
