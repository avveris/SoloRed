package cs3500.solored.controller;

import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.Card;
import java.util.List;

/**
 * Interface for our controller, contains the parameters necessary to
 * have a game.  Including model, deck, shuffle, numpalettes, and handsize.
 */
public interface RedGameController {
  <C extends Card> void playGame(RedGameModel<C> model,
                                 List<C> deck, boolean shuffle,
                                 int numPalettes, int handSize);
}
