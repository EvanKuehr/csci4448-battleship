package edu.colorado.group18;

public class Card {
    private String name;
    private int cost;
    public Ability ability;

    public Card(String n, int c, Ability a) {
        name = n;
        cost = c;
        ability = a;
    }

    public int getCost() { return cost; }
    public String getName() { return name; }
}
