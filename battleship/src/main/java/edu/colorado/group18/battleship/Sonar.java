package edu.colorado.group18.battleship;

public class Sonar implements Ability {
    public Board use(AbilityPlayer self, Player opponent, int x, int y) {
        Board oppBoard = opponent.getBoard();
        Board retBoard = new Board(oppBoard.getY(), oppBoard.getX());
        //if (opponent.hasSunkenShip()) { //removed this check for the actual game
            if (self.decrementSonars()) { ;
                for (int j = y-2; j <= y+2; j++) {
                    for (int i = x-2; i <= x+2; i++) {
                        if (j >= 0 && i >= 0) { //if not out of bounds
                            if (Math.abs(j - y) + Math.abs(i - x) <= 2) { //if at most 2 cells away from the sonar's origin
                                if (oppBoard.getCell(j,i) instanceof ShipCell) { //if the cell contains part of a ship
                                    retBoard.SetCell(new SonarCell(1), j, i);
                                }
                                else {
                                    retBoard.SetCell(new SonarCell(0), j, i);
                                }
                            }
                        }
                    }
                }
            }
        //}
        return retBoard;
    }
}
