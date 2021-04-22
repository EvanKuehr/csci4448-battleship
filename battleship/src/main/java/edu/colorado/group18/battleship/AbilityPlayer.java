package edu.colorado.group18.battleship;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Vector;

public class AbilityPlayer extends DualBoardPlayer {
    private Vector<Card> deck;
    private int numSonars;
    private int maxCards;

    public AbilityPlayer() {
        super();
        this.deck = new Vector<Card>();
        this.numSonars = 2;
        this.maxCards = 3;
    }

    public AbilityPlayer(Ship fleet[], int maxCardNum) {
        super(fleet);
        this.deck = new Vector<Card>();
        this.numSonars = 2;
        this.maxCards = maxCardNum;
    }

    public AbilityPlayer(Ship fleet[]) {
        super(fleet);
        this.deck = new Vector<Card>();
        this.numSonars = 2;
        this.maxCards = 3;
    }

    public String toJson() {
        String json = "";
        try {
            json = new ObjectMapper().writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
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

    public boolean removeCard(String name) {
        Card card = null;
        for (Card c : deck) {
            if (c.getName() == name) {
                card = c;
            }
        }
        if (card != null) {
            deck.remove(card);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean buyCard(Card card) {
        boolean success = true;
            if (deck.size()+1 > maxCards) {
                success = false;
            }
            else {
                deck.add(card);
            }
        return success;
    }
}
