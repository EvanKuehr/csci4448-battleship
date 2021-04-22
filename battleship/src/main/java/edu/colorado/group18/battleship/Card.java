package edu.colorado.group18.battleship;

public class Card {
    private String name;
    public Ability ability;

    public Card(String n, int c, Ability a) {
        name = n;
        ability = a;
    }
    
    public String getName() { return name; }
}
