package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Factory class for our advanceSoloRedGameModel. Help with the
 * initialization of either basic or advanced game for RedGame.
 */
public class RedGameCreator {
  /**
   * These are the two options for gametype, basic / advanced.
   * Basic is the OG game while advanced has different rules in play, but
   * the card rules are the same.
   */
  public enum GameType {
    BASIC,
    ADVANCED
  }

  /**
   * CreateGame method for our factory , creates the two types of games.
   * Extends the redGameModel class to utilize the necessary methods to build
   * a proper game.
   */
  public static <C extends Card> RedGameModel<C> createGame(GameType type) {
    switch (type) {
      case BASIC:
        return (RedGameModel<C>) new SoloRedGameModel<>();
      case ADVANCED:
        return (RedGameModel<C>) new AdvancedSoloRedGameModel();
      default:
        throw new IllegalArgumentException("OOPS! Not a valid game type!" + type);
    }
  }
}
