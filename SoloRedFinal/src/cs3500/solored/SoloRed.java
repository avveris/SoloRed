package cs3500.solored;

import java.io.InputStreamReader;

import cs3500.solored.controller.SoloRedTextController;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw04.RedGameCreator;

/**
 * main method for our solored game.  This allows us to
 * actually play the game.  USE CONFIGS TO RUN !
 */
public class SoloRed {
  /**
   * this is the actual main method for our solored game
   * takes in the args and outputs the game.
   */
  public static void main(String[] args) {
    if (args.length < 1) {
      throw new IllegalArgumentException("Please provide a game type.");
    }

    RedGameCreator.GameType gameType;
    switch (args[0].toLowerCase()) {
      case "basic":
        gameType = RedGameCreator.GameType.BASIC;
        break;
      case "advanced":
        gameType = RedGameCreator.GameType.ADVANCED;
        break;
      default:
        throw new IllegalArgumentException("Invalid game type: " + args[0]);
    }

    RedGameModel<Card> model = RedGameCreator.createGame(gameType);
    Readable input = new InputStreamReader(System.in);
    Appendable output = System.out;

    SoloRedTextController controller = new SoloRedTextController(input, output);
    controller.playGame(model, model.getAllCards(), true, 3, 5);
  }
}