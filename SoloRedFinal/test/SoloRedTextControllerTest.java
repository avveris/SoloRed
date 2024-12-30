import org.junit.Before;
import org.junit.Test;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.CardImpl;
import cs3500.solored.model.hw02.Colors;
import cs3500.solored.model.hw02.SoloRedGameModel;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Updated controller test class incorperating the use of Mocks,
 * to promote isolation in testing.  Uses MockRedGameInputs as source for data.
 */
public class SoloRedTextControllerTest {
  private List<Card> deck;

  @Before
  public void setUp() {
    deck = new ArrayList<>();
    for (Colors color : Colors.values()) {
      for (int i = 1; i <= 7; i++) {
        deck.add(new CardImpl(color, i));
      }
    }
    StringWriter output = new StringWriter();
  }

  // tests the game parameters
  @Test
  public void testValidPara() {
    List<String> inputs = Arrays.asList("palette", "3", "2", "canvas", "1", "q");
    MockRedGameInputs mockController = new MockRedGameInputs(inputs);
    mockController.playGame(new SoloRedGameModel<>(), deck,
            false, 5, 3);

    String log = mockController.getLog();
    assertTrue(log.contains("game started ! yay!"));
    assertTrue(log.contains("command: palette"));
    assertTrue(log.contains("command: canvas"));
    assertTrue(log.contains("awh ! game quit !!!"));
  }

  // tests if the game quits correctly with q
  @Test
  public void testGameQuit() {
    List<String> inputs = Arrays.asList("q");
    MockRedGameInputs mockController = new MockRedGameInputs(inputs);
    mockController.playGame(new SoloRedGameModel<>(), deck,
            false, 2, 6);

    String log = mockController.getLog();
    assertTrue(log.contains("awh ! game quit !!!"));
    assertFalse(log.contains("game over !!!"));
  }

  // tests the lines for invalid input
  @Test
  public void testInvInput() {
    List<String> inputs = Arrays.asList("invalid", "q");
    MockRedGameInputs mockController = new MockRedGameInputs(inputs);
    mockController.playGame(new SoloRedGameModel<>(), deck,
            false, 6, 1);

    String log = mockController.getLog();
    assertTrue(log.contains("BAD ! invalid command"));
    assertTrue(log.contains("awh ! game quit !!!"));
  }

  // tests the game for invalid moves
  @Test
  public void testInvMove() {
    List<String> inputs = Arrays.asList("palette", "7", "4", "q");
    MockRedGameInputs mockController = new MockRedGameInputs(inputs);
    mockController.playGame(new SoloRedGameModel<>(), deck,
            false, 3, 5);

    String log = mockController.getLog();
    assertTrue(log.contains("BAD ! invalid move"));
  }

  // tests if the game will end when its over
  @Test
  public void testGameEnds() {
    List<String> inputs = Arrays.asList("palette", "1", "1", "canvas", "1");
    MockRedGameInputs mockController = new MockRedGameInputs(inputs);
    mockController.playGame(new SoloRedGameModel<>(), deck,
            false, 3, 5);

    String log = mockController.getLog();
    assertTrue(log.contains("awh ! game quit !!!"));
    assertFalse(log.contains("game over !!!"));
  }
}