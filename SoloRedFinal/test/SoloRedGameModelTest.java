import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import cs3500.solored.SoloRed;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardImpl;
import cs3500.solored.model.hw02.Colors;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertThrows;

/**
 * Test file for the SoloRedGameModel. These tests cover the
 * basic functions that make the game run.
 */
public class SoloRedGameModelTest {
  private RedGameModel<Card> gameInstance;
  private List<Card> standardDeck;

  @Before
  public void setGameInstance() {
    gameInstance = new SoloRedGameModel<>();
    standardDeck = createStandardDeck();
  }

  private List<Card> createStandardDeck() {
    return Arrays.asList(
            new CardImpl(Colors.VIOLET, 2),
            new CardImpl(Colors.ORANGE, 4),
            new CardImpl(Colors.INDIGO, 6),
            new CardImpl(Colors.RED, 1),
            new CardImpl(Colors.BLUE, 3),
            new CardImpl(Colors.VIOLET, 5),
            new CardImpl(Colors.ORANGE, 7)
    );
  }

  // test for crashing in solored main method
  @Test
  public void testMainWithBadGameTypeDoesCrash() {
    String[] args = {"blahblah"};
    assertThrows(IllegalArgumentException.class, () -> SoloRed.main(args));
  }

  private void startGame() {
    gameInstance.startGame(standardDeck,
            false, 3, 3);
  }

  @Test
  public void testInitStateExceptions() {

    assertThrows(IllegalStateException.class, () -> gameInstance.isGameOver());
    assertThrows(IllegalStateException.class, () -> gameInstance.numPalettes());
    assertThrows(IllegalStateException.class, () -> gameInstance.getHand());
  }

  // tests whether starting the game will initialize correctly
  @Test
  public void testGameInit() {

    startGame();

    assertEquals("Number of palettes should be 3", 3, gameInstance.numPalettes());
    assertEquals("Hand size should be 3", 3, gameInstance.getHand().size());
    assertEquals("Deck size should be 1", 1, gameInstance.numOfCardsInDeck());
  }

  // tests whether playing to palette will update the game
  @Test
  public void testPTPUpdate() {

    startGame();

    gameInstance.playToPalette(1, 3);

    assertEquals("Palette 2 should have 3 cards",
            2, gameInstance.getPalette(1).size());
    assertEquals("Hand should have 2 cards left",
            2, gameInstance.getHand().size());
  }

  // tests whether the canvas will change and if the hand gets updated
  @Test
  public void testCanvasChangesHandUpdates() {

    startGame();
    Card initialCanvasCard = gameInstance.getCanvas();

    gameInstance.playToCanvas(1);

    assertNotEquals("Canvas should change", initialCanvasCard,
            gameInstance.getCanvas());
    assertEquals("Hand should have 2 cards left", 2,
            gameInstance.getHand().size());
  }


  //tests winning palette's index
  @Test
  public void testWinningPaletteIndexGame() {
    startGame();
    int winningPalette = gameInstance.winningPaletteIndex();

    if (winningPalette < 0 || winningPalette >= 3) {
      throw new IllegalStateException("Invalid winning palette index: " + winningPalette);
    }

    assertTrue("Winning index should be 0, 1, or 2",
            winningPalette >= 0 && winningPalette < 3);
  }

  // tests whether after you draw, what hapens to hand size
  @Test
  public void testHandSizeUpdates() {

    startGame();
    gameInstance.playToPalette(1, 1);

    gameInstance.drawForHand();

    assertEquals("Hand size should be 3 after drawing",
            3, gameInstance.getHand().size());
  }


  // tests if the game will continue with an empty deck, but cards on player
  @Test
  public void testGameOverHuhCardHand() {

    List<Card> limitedDeck = Arrays.asList(
            new CardImpl(Colors.VIOLET, 2), new CardImpl(Colors.ORANGE, 4),
            new CardImpl(Colors.INDIGO, 6), new CardImpl(Colors.RED, 1),
            new CardImpl(Colors.BLUE, 3)
    );
    gameInstance.startGame(limitedDeck, false, 3, 2);
    gameInstance.playToPalette(1, 1);
    gameInstance.drawForHand();

    assertFalse("Game should continue with empty deck but cards in hand",
            gameInstance.isGameOver());
    assertEquals("Deck should be empty", 0, gameInstance.numOfCardsInDeck());
    assertEquals("Hand should have 1 card", 1, gameInstance.getHand().size());
  }



  // tests if the game is already won and can't continue
  @Test
  public void testGameWonHuhReturnT() {

    List<Card> winningDeck = Arrays.asList(
            new CardImpl(Colors.VIOLET, 2), new CardImpl(Colors.ORANGE, 4),
            new CardImpl(Colors.INDIGO, 6), new CardImpl(Colors.RED, 1)
    );
    gameInstance.startGame(winningDeck, false, 3, 1);

    gameInstance.playToPalette(0, 0);

    assertTrue("Game should be over", gameInstance.isGameOver());
    assertTrue("Game should be won", gameInstance.isGameWon());
  }



  // draws when the deck is allready full
  @Test
  public void testForFullDeckState() {
    startGame();
    int initialDeckSize = gameInstance.numOfCardsInDeck();
    gameInstance.drawForHand();
    assertEquals("Deck size should not change",
            initialDeckSize, gameInstance.numOfCardsInDeck());
  }

  // tests the hand and makes sure it still has the right amount of cards
  @Test
  public void testGetTheSameNumberCards() {

    startGame();

    List<Card> hand = gameInstance.getHand();
    hand.clear();
    assertEquals("Hand should still have 3 cards",
            3, gameInstance.getHand().size());
  }

  // tests the game starting shuffled, making sure of new order
  @Test
  public void testStartGameShuffleOrder() {

    gameInstance.startGame(new ArrayList<>(standardDeck), false, 3, 3);
    List<Card> unshuffled = getGameCards(gameInstance);

    RedGameModel<Card> newGameInstance = new SoloRedGameModel();
    newGameInstance.startGame(standardDeck, true, 3, 3);
    List<Card> shuffled = getGameCards(newGameInstance);

    assertNotEquals("The shuffled deck should be in a different order",
            unshuffled, shuffled);
  }

  private List<Card> getGameCards(RedGameModel<?> game) {
    List<Card> cards = new ArrayList<>(game.getHand());
    for (int i = 0; i < game.numPalettes(); i++) {
      cards.addAll(game.getPalette(i));
    }
    return cards;
  }
}