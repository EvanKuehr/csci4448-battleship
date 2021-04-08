package edu.colorado.group18.battleship;

import java.util.Vector;

public class AbilityPlayer extends DualBoardPlayer {
    private int balance;
    private Vector<Card> deck;
    private int numSonars;
    private int maxCards;

    public AbilityPlayer(Ship fleet[], int maxCardNum) {
        super(fleet);
        this.balance = 0;
        this.deck = new Vector<Card>();
        this.numSonars = 2;
        this.maxCards = maxCardNum;
    }

    public AbilityPlayer(Ship fleet[]) {
        super(fleet);
        this.balance = 0;
        this.deck = new Vector<Card>();
        this.numSonars = 2;
        this.maxCards = 3;
    }

    public int incrementBalance(int num) {
        balance += num;
        return balance;
    }

    public boolean decrementSonars() {
        if (numSonars > 0) {
            numSonars -= 1;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean useCard(String name) {
        Card card = null;
        for (Card c : deck) {
            if (c.getName() == name) {
                card = c;
            }
        }
        if (card != null) {
            deck.remove(card);
            //TODO: Implement the ability to use each card once user input is added (can then take parameters)
            return true;
        }
        else {
            return false;
        }
    }

    public boolean buyCard(Card card) {
        boolean success = true;
        if (balance >= card.getCost()) {
            if (deck.size()+1 > maxCards) {
                success = false;
            }
            else {
                balance -= card.getCost();
                deck.add(card);
            }
        }
        else {
            success = false;
        }
        return success;
    }
}
