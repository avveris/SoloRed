import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw04.AdvancedSoloRedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * Tests the redgamecreator class, which is the factory class for the
 * advancedsoloredgamemodel.
 */
public class RedGameCreatorTest {

  // tests the creation of the basic game
  @Test
  public void testBasicGame() {
    RedGameModel<Card> game = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    assertNotNull("basic game cant be null", game);
    assertTrue("basic games are a SoloRedGameModel", game instanceof SoloRedGameModel);
  }

  // tests the creation of the advanced game
  @Test
  public void testAdvancedGame() {
    RedGameModel<Card> game = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    assertNotNull("advanced games cant be null", game);
    assertTrue("advanced games are an AdvancedSoloRedGameModel",
            game instanceof AdvancedSoloRedGameModel);
  }

  // tests that the values of the enum aren't changed
  @Test
  public void testEnumMutation() {
    RedGameCreator.GameType[] types = RedGameCreator.GameType.values();
    assertEquals("there can only be 2 game types", 2, types.length);
    assertEquals("game type should be BASIC",
            RedGameCreator.GameType.BASIC, types[0]);
    assertEquals("game type should be ADVANCED",
            RedGameCreator.GameType.ADVANCED, types[1]);
  }


  // tests that the basic/ advanced game becomes a new game, and not a mutated old game.
  @Test
  public void testGameInstance() {
    RedGameModel<Card> basicOne = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    RedGameModel<Card> basicTwo = RedGameCreator.createGame(RedGameCreator.GameType.BASIC);
    RedGameModel<Card> advOne = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);
    RedGameModel<Card> advTwo = RedGameCreator.createGame(RedGameCreator.GameType.ADVANCED);

    assertNotSame("a new game should make a new instance",
            basicOne, basicTwo);
    assertNotSame("a new game should make a new instance",
            advOne, advTwo);
  }
}
