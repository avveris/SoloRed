package cs3500.solored.model.hw02;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Red: highest number wins, if tie then R O B I V wins.
 *  Orange: any cards with the same number, the palette with the most same numbers wins.
 *  Blue: the palette with the most variety in colors wins.
 *  Indigo: the palette with the most consecutive numbers wins forward || backwards.
 *  Violet: The palette with the most cards under the value 4 wins.
 */
public class GameRules implements Rules {

  @Override
  public boolean isWinningPalette(List<List<Card>> palettes, int paletteIndex, Card canvasCard) {
    Colors canvasColor = getCardColor(canvasCard);

    if (canvasColor == Colors.RED) {
      return hasHighestCard(palettes, paletteIndex);
    } else if (canvasColor == Colors.ORANGE) {
      return hasMostOfOneNumber(palettes, paletteIndex);
    } else if (canvasColor == Colors.BLUE) {
      return hasMostDifferentColors(palettes, paletteIndex);
    } else if (canvasColor == Colors.INDIGO) {
      return hasLongestRun(palettes, paletteIndex);
    } else if (canvasColor == Colors.VIOLET) {
      return hasMostBelowFour(palettes, paletteIndex);
    } else {
      throw new IllegalStateException("OOPS ! Invalid canvas color.");
    }
  }

  @Override
  public Card topCard() {
    return new CardImpl(Colors.RED, 1);
  }

  // using a lambda to find the highest card
  @Override
  public boolean hasHighestCard(List<List<Card>> palettes, int paletteIndex) {
    if (palettes.isEmpty() || palettes.get(paletteIndex).isEmpty()) {
      return false;
    }
    Card highestCard = Collections.max(palettes.get(paletteIndex), this::compareCards);
    for (int i = 0; i < palettes.size(); i++) {
      if (i != paletteIndex && !palettes.get(i).isEmpty()) {
        Card otherHighest = Collections.max(palettes.get(i), this::compareCards);
        if (compareCards(otherHighest, highestCard) > 0) {
          return false;
        }
      }
    }
    return true;
  }

  // tells you which palette has the most of one single number
  @Override
  public boolean hasMostOfOneNumber(List<List<Card>> palettes, int paletteIndex) {
    if (palettes.isEmpty() || palettes.get(paletteIndex).isEmpty()) {
      return false;
    }
    Map<Integer, Integer> numberCount = new HashMap<>();
    for (Card card : palettes.get(paletteIndex)) {
      int number = getCardNumber(card);
      numberCount.put(number, numberCount.getOrDefault(number, 0) + 1);
    }
    int maxCount = Collections.max(numberCount.values());
    for (int i = 0; i < palettes.size(); i++) {
      if (i != paletteIndex && !palettes.get(i).isEmpty()) {
        Map<Integer, Integer> otherCount = new HashMap<>();
        for (Card card : palettes.get(i)) {
          int number = getCardNumber(card);
          otherCount.put(number, otherCount.getOrDefault(number, 0) + 1);
        }
        if (!otherCount.isEmpty() && Collections.max(otherCount.values()) > maxCount) {
          return false;
        }
      }
    }
    return true;
  }

  // tells you which palette has the most variation in colors
  @Override
  public boolean hasMostDifferentColors(List<List<Card>> palettes, int paletteIndex) {
    if (paletteIndex < 0 || paletteIndex >= palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index");
    }

    int targetColorCount = countUniqueColors(palettes.get(paletteIndex));

    for (int i = 0; i < palettes.size(); i++) {
      if (i != paletteIndex) {
        int otherColorCount = countUniqueColors(palettes.get(i));
        if (otherColorCount > targetColorCount) {
          return false;
        }
      }
    }
    return true;
  }

  // helper method for our hasMostDifferentColors under blue rule
  private int countUniqueColors(List<Card> palette) {
    return (int) palette.stream()
            .map(this::getCardColor)
            .distinct()
            .count();
  }

  // identifies which palette has the longest consecutive run
  @Override
  public boolean hasLongestRun(List<List<Card>> palettes, int paletteIndex) {
    List<Integer> numbers = new ArrayList<>();
    for (Card card : palettes.get(paletteIndex)) {
      numbers.add(getCardNumber(card));
    }
    Collections.sort(numbers);
    List<Integer> distinctNumbers = new ArrayList<>();
    for (Integer number : numbers) {
      if (!distinctNumbers.contains(number)) {
        distinctNumbers.add(number);
      }
    }
    int maxRun = getLongestRun(distinctNumbers);

    for (int i = 0; i < palettes.size(); i++) {
      if (i != paletteIndex) {
        List<Integer> otherNumbers = new ArrayList<>();
        for (Card card : palettes.get(i)) {
          otherNumbers.add(getCardNumber(card));
        }
        Collections.sort(otherNumbers);
        List<Integer> otherDistinctNumbers = new ArrayList<>();
        for (Integer number : otherNumbers) {
          if (!otherDistinctNumbers.contains(number)) {
            otherDistinctNumbers.add(number);
          }
        }
        if (getLongestRun(otherDistinctNumbers) > maxRun) {
          return false;
        }
      }
    }
    return true;
  }

  // finds the length of the run in a palette
  private int getLongestRun(List<Integer> numbers) {
    int maxRun = 0;
    int currentRun = 1;
    for (int i = 1; i < numbers.size(); i++) {
      if (numbers.get(i) == numbers.get(i - 1) + 1) {
        currentRun++;
      } else {
        maxRun = Math.max(maxRun, currentRun);
        currentRun = 1;
      }
    }
    return Math.max(maxRun, currentRun);
  }

  // finds which palette has the most cards below 4
  @Override
  public boolean hasMostBelowFour(List<List<Card>> palettes, int paletteIndex) {
    int count = 0;
    for (Card card : palettes.get(paletteIndex)) {
      if (getCardNumber(card) < 4) {
        count++;
      }
    }

    for (int i = 0; i < palettes.size(); i++) {
      if (i != paletteIndex) {
        int otherCount = 0;
        for (Card card : palettes.get(i)) {
          if (getCardNumber(card) < 4) {
            otherCount++;
          }
        }
        if (otherCount > count) {
          return false;
        }
      }
    }
    return true;
  }

  // compares the cards to one another
  @Override
  public int compareCards(Card card1, Card card2) {
    int numberComparison = Integer.compare(getCardNumber(card1), getCardNumber(card2));
    if (numberComparison != 0) {
      return numberComparison;
    }
    return -Integer.compare(getCardColor(card1).ordinal(), getCardColor(card2).ordinal());
  }

  @Override
  public Colors getCardColor(Card card) {
    return ((CardImpl)card).getCol();
  }

  @Override
  public int getCardNumber(Card card) {
    return ((CardImpl)card).getNum();
  }
}