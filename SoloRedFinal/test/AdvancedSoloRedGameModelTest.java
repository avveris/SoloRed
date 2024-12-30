import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardImpl;
import cs3500.solored.model.hw02.Colors;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;

/**
 * Tests for our advanced solo red game model, this
 * includes the advanced version of the game.
 */
public class AdvancedSoloRedGameModelTest {

  private AdvancedSoloRedGameModel game;
  private List<Card> testDeck;

  // sets up our adv game
  @Before
  public void setUp() {
    game = new AdvancedSoloRedGameModel();
    testDeck = new ArrayList<>();
    for (Colors color : Colors.values()) {
      for (int i = 1; i <= 7; i++) {
        testDeck.add(new CardImpl(color, i));
      }
    }
  }

  // tests our playtopalette method for advanced game, making sure it increases by 1
  @Test
  public void testPlayToPalette() {
    game.startGame(testDeck, false, 2, 5);
    int initialHandSize = game.getHand().size();
    game.playToPalette(0, 0);
    assertEquals("the hand size should increase after playing to palette !",
            initialHandSize, game.getHand().size());
  }

  // tests whether the game starts successfully, and can't start again
  @Test
  public void testIsGameStarted() {
    assertFalse("the game can't be started initially !", game.isGameStarted());
    game.startGame(testDeck, false, 2, 5);
    assertTrue("the game can't be started after game is started !", game.isGameStarted());
  }

  // tests the max hand size after setting it
  @Test
  public void testGetMaxHandSize() {
    game.startGame(testDeck, false, 2, 5);
    assertEquals("the max hand should be 5 !", 5, game.getMaxHandSize());
  }

  // tests the deck size after setting it
  @Test
  public void testGetDeckSize() {
    game.startGame(testDeck, false, 2, 5);
    int expectedDeckSize = testDeck.size() - (2 * 1) - 5;
    assertEquals("deck size should be !",
            expectedDeckSize, game.getDeckSize());
  }

  @Test(expected = IllegalStateException.class)
  public void testPlayBeforeGameStart() {
    game.playToPalette(0, 0);
  }
}
