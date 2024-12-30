import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardImpl;
import cs3500.solored.model.hw02.Colors;
import cs3500.solored.model.hw02.GameRules;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * tests for the game rules in the soloredgame.  each color has a different set of rules
 * so this file will test edge cases for each rule for Red, orange, blue, indigo, violet.
 */
public class GameRulesTest {

  private GameRules rules;
  private List<List<Card>> palettes;

  @Before
  public void setUp() {
    rules = new GameRules();
    palettes = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      palettes.add(new ArrayList<>());
    }
  }

  // creates card for testing
  private Card createCard(Colors color, int number) {
    return new CardImpl(color, number);
  }

  // test for red rules
  @Test
  public void testHasHighestCard() {
    palettes.get(0).add(createCard(Colors.RED, 7));
    palettes.get(1).add(createCard(Colors.BLUE, 7));
    assertTrue(rules.hasHighestCard(palettes, 0));
    assertFalse(rules.hasHighestCard(palettes, 1));
    palettes.get(2).clear();
    assertFalse(rules.hasHighestCard(palettes, 2));
  }

  // test for orange rules
  @Test
  public void testHasMostOfOneNumber() {
    palettes.get(0).add(createCard(Colors.RED, 3));
    palettes.get(0).add(createCard(Colors.BLUE, 3));
    palettes.get(1).add(createCard(Colors.RED, 3));
    palettes.get(1).add(createCard(Colors.ORANGE, 3));
    assertTrue(rules.hasMostOfOneNumber(palettes, 0));
    assertTrue(rules.hasMostOfOneNumber(palettes, 1));
    palettes.clear();
    palettes.add(List.of(createCard(Colors.RED, 1)));
    palettes.add(List.of(createCard(Colors.BLUE, 2)));
    assertTrue(rules.hasMostOfOneNumber(palettes, 0));
    assertTrue(rules.hasMostOfOneNumber(palettes, 1));
  }

  // test for blue rules
  @Test
  public void testHasMostDifferentColors() {
    palettes.get(0).add(createCard(Colors.RED, 1));
    palettes.get(0).add(createCard(Colors.BLUE, 2));
    palettes.get(1).add(createCard(Colors.ORANGE, 3));
    palettes.get(1).add(createCard(Colors.INDIGO, 4));
    assertTrue(rules.hasMostDifferentColors(palettes, 0));
    assertTrue(rules.hasMostDifferentColors(palettes, 1));
    palettes.clear();
    palettes.add(List.of(createCard(Colors.RED, 1),
            createCard(Colors.RED, 2), createCard(Colors.RED, 3)));
    palettes.add(List.of(createCard(Colors.RED, 1),
            createCard(Colors.BLUE, 2)));
    assertFalse(rules.hasMostDifferentColors(palettes, 0));
    assertTrue(rules.hasMostDifferentColors(palettes, 1));
  }

  // test for indigo
  @Test
  public void testHasLongestRun() {
    palettes.get(0).add(createCard(Colors.RED, 3));
    palettes.get(0).add(createCard(Colors.BLUE, 2));
    palettes.get(0).add(createCard(Colors.ORANGE, 1));
    palettes.get(1).add(createCard(Colors.RED, 1));
    palettes.get(1).add(createCard(Colors.BLUE, 2));
    assertTrue(rules.hasLongestRun(palettes, 0));
    assertFalse(rules.hasLongestRun(palettes, 1));
    palettes.clear();
    palettes.add(List.of(createCard(Colors.RED, 1),
            createCard(Colors.BLUE, 3), createCard(Colors.ORANGE, 5)));
    palettes.add(List.of(createCard(Colors.RED, 1), createCard(Colors.BLUE, 2)));
    assertFalse(rules.hasLongestRun(palettes, 0));
    assertTrue(rules.hasLongestRun(palettes, 1));
  }

  // test for violet's rules
  @Test
  public void testHasMostBelowFour() {
    palettes.get(0).add(createCard(Colors.RED, 1));
    palettes.get(0).add(createCard(Colors.BLUE, 2));
    palettes.get(0).add(createCard(Colors.ORANGE, 3));
    palettes.get(1).add(createCard(Colors.RED, 1));
    palettes.get(1).add(createCard(Colors.BLUE, 4));
    palettes.get(1).add(createCard(Colors.ORANGE, 5));
    assertTrue(rules.hasMostBelowFour(palettes, 0));
    assertFalse(rules.hasMostBelowFour(palettes, 1));
    palettes.clear();
    palettes.add(List.of(createCard(Colors.RED, 1), createCard(Colors.BLUE, 2)));
    palettes.add(List.of(createCard(Colors.ORANGE, 3),
            createCard(Colors.INDIGO, 4)));
    assertTrue(rules.hasMostBelowFour(palettes, 0));
  }

  @Test
  public void testIsWinningPalette() {
    palettes.add(List.of(createCard(Colors.RED, 7)));
    palettes.add(List.of(createCard(Colors.BLUE, 7)));
    assertFalse(rules.isWinningPalette(palettes, 1, createCard(Colors.RED, 1)));
    palettes.add(new ArrayList<>());
    assertFalse(rules.isWinningPalette(palettes, 2, createCard(Colors.RED, 1)));
  }
}