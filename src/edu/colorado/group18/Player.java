package edu.colorado.group18;

public class Player {
    private double balance;
    private Card[] deck;
    private Ship[] fleet;
    private Board board;

    public Player(Ship[] fleet) {
        this.balance = 0.0;
        this.deck = new Card[5];
        this.fleet = fleet;
        this.board = new Board(10, 10);
    }

    // getters
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

    public void placeShip(Ship ship, int x, int y, char orient) {
        if (!ship.placed) {
            if (orient == 'h') {
                if (x < board.getX() && y < board.getY() - ship.getLength()) {
                    board.placeShip(ship, x, y, orient);
                    ship.placed = true;
                }
            } else if (orient == 'v') {
                if (x < board.getX() - ship.getLength() && y < board.getY()) {
                    board.placeShip(ship, x, y, orient);
                    ship.placed = true;
                }
            }
        }
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
    public boolean strike(Player opponent, int x, int y) {
        return opponent.receiveStrike(x, y);
    }
    public boolean strike(Player opponent, int x, int y, Card card) {
        // TODO: attack using special ability
        System.out.println(card.ability);
        return false;
    }
    public boolean receiveStrike(int x, int y) {
        return board.strike(x, y);
    }
    // TODO: implement receiveStrike() for strike() special overload function
}
