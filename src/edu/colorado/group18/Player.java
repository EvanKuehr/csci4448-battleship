package edu.colorado.group18;
import java.util.Arrays;

public class Player {
    private double balance;
    private Card[] deck;
    private Ship[] fleet;
    private Board board;
    private int numSonars;

    public Player(Ship[] fleet) {
        this.balance = 0.0;
        this.deck = new Card[5];
        this.fleet = fleet;
        this.board = new Board(10, 10);
        this.numSonars = 2;
    }

    // GET METHODS
    public double getBalance() {
        return balance;
    }

    public String[] getDeck() {
        String[] abilities = new String[5];
        for (int i = 0; i < deck.length; i++) {
            abilities[i] = deck[i].ability;
        }
        return abilities;
    }

    public Ship[] getFleet() {
        return fleet;
    }

    public Board getBoard() {
        return board;
    }

    public boolean placeShip(Ship ship, int y, int x, char orient) {
        boolean success = false;
        if (!ship.placed) {
            if (orient == 'v') {
                if (x < board.getX() && y < board.getY() - ship.getLength()) {
                    if (board.placeShip(ship, y, x, orient)) {
                        ship.placed = true;
                        success = true;
                    }
                }
            } else if (orient == 'h') {
                if (x < board.getX() - ship.getLength() && y < board.getY()) {
                    if(board.placeShip(ship, y, x, orient)) {
                        ship.placed = true;
                        success = true;
                    }
                }
            }
        }
        return success;
    }

    public void buyCard(Card card) {
        if (balance >= card.cost) {
            balance -= card.cost;
            Card[] newDeck = new Card[deck.length + 1];
            int i = 0;
            for (i = 0; i < deck.length; i++) {
                newDeck[i] = deck[i];
            }
            newDeck[i] = card;
            deck = newDeck;
        }
    }

    public Card useCard(int index) {
        return deck[index];
    }

    public boolean[] strike(Player opponent, int y, int x) {
        return opponent.receiveStrike(y, x);
    }

    public boolean[] strike(Player opponent, int y, int x, Card card) {
        // TODO: attack using special ability
        System.out.println(card.ability);
        boolean[] retArray = {false,false};
        return retArray;
    }

    //returns a list of two booleans:
    //index 0: if a ship was hit
    //index 1: if a ship was sunk as a result of the strike
    public boolean[] receiveStrike(int y, int x) {
        return board.strike(y, x);
    }

    // TODO: implement receiveStrike() for strike() special overload function

    public boolean shouldSurrender() {
        boolean retVal = true;
        for (Ship ship : fleet) { //for every ship in the player's fleet
            if (!ship.getSunk()) {
                retVal = false; //one of their ships isn't sunk so they shouldn't surrender
                break;
            }
        }
        return retVal;
    }

    public boolean hasSunkenShip() {
        boolean retVal = false;
        for (Ship ship : fleet) { //for every ship in the player's fleet
            if (ship.getSunk()) {
                retVal = true; //one of their ships is sunk
                break;
            }
        }
        return retVal;
    }

    public boolean canUseSonar(Player opponent) {
        boolean retVal = false;
        if (opponent.hasSunkenShip()) {
            if (numSonars > 0) {
                retVal = true;
            }
        }
        return retVal;
    }

    public int[][] receiveSonar(int y, int x) {
        int yDim = board.getY();
        int xDim = board.getX();
        int[][] retArray = new int[yDim][xDim];

        for (int[] row : retArray) {
            Arrays.fill(row,-1); //-1 represents parts not in the sonar
        }

        for (int j = y-2; j <= y+2; j++) {
            for (int i = x-2; i <= x+2; i++) {
                if (j >= 0 && i >= 0) { //if not out of bounds
                    if (Math.abs(j - y) + Math.abs(i - x) <= 2) { //if at most 2 cells away from the sonar's origin
                        if (board.getCell(j,i) instanceof ShipCell) { //if the cell contains part of a ship
                            retArray[j][i] = 2; //2 represents part of a ship
                        }
                        else {
                            retArray[j][i] = 0; //0 represents a part of the sonar but not a ship
                        }
                    }
                }
            }
        }
        return retArray;
    }

    public int[][] useSonar(Player opponent, int y, int x) {
        numSonars -= 1;
        return opponent.receiveSonar(y,x);
    }
}
