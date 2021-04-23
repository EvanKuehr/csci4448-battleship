package edu.colorado.group18.battleship;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Card {
    public String name;

    @JsonCreator
    public Card(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() { return name; }
}
