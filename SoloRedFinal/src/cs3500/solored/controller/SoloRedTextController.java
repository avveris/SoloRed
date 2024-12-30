package cs3500.solored.controller;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;
import cs3500.solored.model.hw02.SoloRedGameModel;
import cs3500.solored.view.hw02.RedGameView;
import cs3500.solored.view.hw02.SoloRedGameTextView;

/**
 * Our controller class that contains all of our methods necessary for
 * the game to run.  This is the control and user inputs containing the logic
 * necessary for the game to run.
 */
public class SoloRedTextController implements RedGameController {
  private final Appendable out;
  private final Scanner scanner;
  private RedGameView view;

  /**
   * Constructs a SoloRedTextController with the specified input and output.
   * in the input source
   * out the output destination
   * @throws IllegalArgumentException if either in or out is Null
   */
  public SoloRedTextController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("readable and appendable cant be null.");
    }
    this.out = out;
    this.scanner = new Scanner(in);
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("model cant be null.");
    }
    this.view = new SoloRedGameTextView(model, out);

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException("failed to start game: " + e.getMessage());
    }

    while (!model.isGameOver()) {
      try {
        renderGameState(model);
        String command = getNextCommand();
        if (command.equalsIgnoreCase("q")) {
          quitGame(model);
          return;
        }
        processCommand(command, model);
      } catch (IOException e) {
        throw new IllegalStateException("failed to render game state || read input", e);
      }
    }

    try {
      endGame(model);
    } catch (IOException e) {
      throw new IllegalStateException("failed to render final game state", e);
    }
  }

  private void renderGameState(RedGameModel<?> model) throws IOException {
    view.render();
    out.append("Number of cards in deck: ")
            .append(Integer.toString(((SoloRedGameModel)model).getDeck().size()))
            .append("\n");
  }

  private String getNextCommand() {
    while (true) {
      if (scanner.hasNext()) {
        return scanner.next();
      }
    }
  }

  private <C extends Card> void processCommand(String command,
                                               RedGameModel<C> model) throws IOException {
    switch (command.toLowerCase()) {
      case "palette":
        processPaletteMove(model);
        break;
      case "canvas":
        processCanvasMove(model);
        break;
      default:
        out.append("OOPS! invalid command, use 'palette', 'canvas', or 'q' to quit.\n");
    }
  }

  private <C extends Card> void processPaletteMove(RedGameModel<C> model) throws IOException {
    int paletteIndex = readIndex("enter palette index: ") - 1;
    int cardIndex = readIndex("enter card index: ") - 1;
    try {
      model.playToPalette(paletteIndex, cardIndex);
    } catch (IllegalArgumentException e) {
      out.append("OOPS! invalid move, try again. ").append(e.getMessage()).append("\n");
    }
  }

  private <C extends Card> void processCanvasMove(RedGameModel<C> model) throws IOException {
    int cardIndex = readIndex("enter card index: ") - 1;
    try {
      model.playToCanvas(cardIndex);
    } catch (IllegalArgumentException e) {
      out.append("OOPS! invalid move,try again. ").append(e.getMessage()).append("\n");
    }
  }

  private int readIndex(String prompt) throws IOException {
    while (true) {
      out.append(prompt);
      if (scanner.hasNextInt()) {
        return scanner.nextInt();
      } else {
        String input = scanner.next();
        if (input.equalsIgnoreCase("q")) {
          throw new IllegalStateException("game quit !!");
        }
        out.append("OOPS! invalid input, enter a number.\n");
      }
    }
  }

  private <C extends Card> void quitGame(RedGameModel<C> model) throws IOException {
    out.append("Game quit!\nState of game when quit:\n");
    renderGameState(model);
  }

  private <C extends Card> void endGame(RedGameModel<C> model) throws IOException {
    out.append(model.isGameOver() ? "game over !!! " : "")
            .append(model.isGameWon() ? "you won !!!" : "you lost :C")
            .append("\n");
    renderGameState(model);
  }
}