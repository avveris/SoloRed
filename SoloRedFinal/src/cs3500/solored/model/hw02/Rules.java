package cs3500.solored.model.hw02;

import java.util.List;

/**
 * Represents the rules in the SoloRedGame that are written out in the GameRules class.
 * These determine which palette wins the round, and relays the information to the
 * game model.
 */
public interface Rules {
  boolean isWinningPalette(List<List<Card>> palettes, int paletteIndex, Card canvasCard);

  Card topCard();

  boolean hasHighestCard(List<List<Card>> palettes, int paletteIndex);

  boolean hasMostOfOneNumber(List<List<Card>> palettes, int paletteIndex);

  boolean hasMostDifferentColors(List<List<Card>> palettes, int paletteIndex);

  boolean hasLongestRun(List<List<Card>> palettes, int paletteIndex);

  boolean hasMostBelowFour(List<List<Card>> palettes, int paletteIndex);

  int compareCards(Card card1, Card card2);

  Colors getCardColor(Card card);

  int getCardNumber(Card card);
}
