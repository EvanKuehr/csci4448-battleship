package edu.colorado.group18.battleship;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbilityPlayer extends DualBoardPlayer {
    public Card[] deck;
    private int numSonars;
    private int maxCards;
    private int currCard;

    public AbilityPlayer() {
        super();
        this.deck = new Card[3];
        this.numSonars = 100;
        this.maxCards = 3;
        this.currCard = 0;
    }

    public AbilityPlayer(Ship fleet[], int maxCardNum) {
        super(fleet);
        this.deck = new Card[maxCardNum];
        this.numSonars = 100;
        this.maxCards = maxCardNum;
        this.currCard = 0;
    }

    public AbilityPlayer(Ship fleet[]) {
        super(fleet);
        this.deck = new Card[3];
        this.numSonars = 100;
        this.maxCards = 3;
        this.currCard = 0;
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
        int newDeckCardsNum = currCard;
        currCard = 0;
        Card[] newDeck = new Card[maxCards];
        Card card = null;
        for (int i=0; i < newDeckCardsNum; i++) {
            if (deck[i].getName().equals(name)) {
                //System.out.println("Debug: Found card to remove " + name);
                card = deck[i];
            }
            else {
                //System.out.println("Debug: Keeping card " + deck[i].getName());
                newDeck[currCard] = deck[i];
                currCard += 1;
            }
        }
        deck = newDeck;
        if (card != null) {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean buyCard(String name) {
        boolean success = true;
            if (currCard >= maxCards) {
                //System.out.println("Debug: Over max limit");
                success = false;
            }
            else {
                boolean foundDuplicate = false;
                for (int i=0; i < currCard; i++) {
                    if (deck[i].getName().equals(name)) {
                        //System.out.println("Debug: Found duplicate card " + name);
                        foundDuplicate = true;
                    }
                }

                if (foundDuplicate) {
                    success = false;
                }
                else {
                    //System.out.println("Debug: Adding card " + name);
                    deck[currCard] = (new Card(name));
                    currCard += 1;
                }
            }
        return success;
    }
}
