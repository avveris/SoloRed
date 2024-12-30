package cs3500.solored.model.hw02;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates the Palette for our redcardgame.  This holds
 * the methods for the palette as well, adding and removing cards, getting
 * count and returns a copy of the palette to protect encapsulation.
 */
public class Palette {
  private final List<Card> cards;

  public Palette() {
    this.cards = new ArrayList<>();
  }

  // adds a card
  public void addCard(Card card) {
    cards.add(card);
  }

  /**
   * Removes and returns the card at the specified index.
   *
   * @param index the index of the card to remove
   * @return the card that was removed, or throws an exception if the index is invalid
   * @throws IndexOutOfBoundsException if the index is invalid
   */
  public Card removeCard(int index) {
    if (index < 0 || index >= cards.size()) {
      throw new IndexOutOfBoundsException("OOPS ! Invalid card index: " + index);
    }
    return cards.remove(index);
  }

  /**
   * Takes a card based on its index.
   * @param index the index of the card to retrieve
   * @return the card at the specified index
   * @throws IndexOutOfBoundsException if the index is invalid
   */
  public Card getCard(int index) {
    if (index < 0 || index >= cards.size()) {
      throw new IndexOutOfBoundsException("OOPS ! Invalid card index: " + index);
    }
    return cards.get(index);
  }

  // returns the total # of cards
  public int getCardCount() {
    return cards.size();
  }

  // returns a copy
  public List<Card> getAllCards() {
    return new ArrayList<>(cards);
  }
}