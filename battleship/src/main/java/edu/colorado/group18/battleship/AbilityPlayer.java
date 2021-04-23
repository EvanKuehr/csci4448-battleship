package edu.colorado.group18.battleship;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Vector;

public class AbilityPlayer extends DualBoardPlayer {
    public Vector<Card> deck;
    private int numSonars;
    private int maxCards;

    public AbilityPlayer() {
        super();
        this.deck = new Vector<Card>();
        this.numSonars = 100;
        this.maxCards = 3;
    }

    public AbilityPlayer(Ship fleet[], int maxCardNum) {
        super(fleet);
        this.deck = new Vector<Card>();
        this.numSonars = 100;
        this.maxCards = maxCardNum;
    }

    public AbilityPlayer(Ship fleet[]) {
        super(fleet);
        this.deck = new Vector<Card>();
        this.numSonars = 100;
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
            if (c.getName().equals(name)) {
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

    public boolean buyCard(String name) {
        boolean success = true;
            if (deck.size()+1 > maxCards) {
                //System.out.println("Debug: Over max limit");
                success = false;
            }
            else {
                boolean foundDuplicate = false;
                for (Card c: deck) {
                    if (c.getName().equals(name)) {
                        //System.out.println("Debug: Found duplicate card " + name);
                        foundDuplicate = true;
                    }
                }

                if (foundDuplicate) {
                    success = false;
                }
                else {
                    //System.out.println("Debug: Adding card " + name);
                    deck.add(new Card(name));
                }
            }
        return success;
    }
}
