package com.kenzie.streams.listprocessing.resources;


import java.util.Objects;

public class Card {
    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Card)) {
            return false;
        }
        Card otherCard = (Card) other;
        return this.rank == otherCard.rank && this.suit == otherCard.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
