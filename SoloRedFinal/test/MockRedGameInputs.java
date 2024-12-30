import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cs3500.solored.controller.RedGameController;
import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * NEW ! Mocks for our Controller tests, these allow us to isolate
 * input, so we can properly test the controller with seperation from model.
 */
public class MockRedGameInputs implements RedGameController {
  private final StringBuilder log;
  private final Queue<String> inputs;

  public MockRedGameInputs(List<String> inputs) {
    this.log = new StringBuilder();
    this.inputs = new LinkedList<>(inputs);
  }

  @Override
  public <C extends Card> void playGame(RedGameModel<C> model, List<C> deck, boolean shuffle,
                                        int numPalettes, int handSize) {
    if (model == null) {
      throw new IllegalArgumentException("OOPS! the model can't be null");
    }

    log.append("game started ! yay!\n");

    try {
      model.startGame(deck, shuffle, numPalettes, handSize);
    } catch (IllegalArgumentException e) {
      log.append("OOPS! Can't start game: ").append(e.getMessage()).append("\n");
      return;
    }

    while (!model.isGameOver() && !inputs.isEmpty()) {
      String command = inputs.poll();
      processCommand(command, model);
    }

    if (model.isGameOver()) {
      log.append("Game over!!! :(\n");
    } else if (inputs.isEmpty()) {
      log.append("awh ! game quit !!!\n");
    }
  }

  private <C extends Card> void processCommand(String command, RedGameModel<C> model) {
    log.append("processing command: ").append(command).append("\n");
    switch (command.toLowerCase()) {
      case "q":
        log.append("awh ! game quit !!!\n");
        break;
      case "palette":
        processPaletteMove(model);
        break;
      case "canvas":
        processCanvasMove(model);
        break;
      default:
        log.append("BAD ! invalid command\n");
    }
  }

  private <C extends Card> void processPaletteMove(RedGameModel<C> model) {
    if (inputs.size() < 2) {
      log.append("BAD ! invalid move (not enough inputs)\n");
      return;
    }
    int paletteIndex = Integer.parseInt(inputs.poll()) - 1;
    int cardIndex = Integer.parseInt(inputs.poll()) - 1;
    log.append("enter palette index: ").append(paletteIndex + 1).append("\n");
    log.append("enter card index: ").append(cardIndex + 1).append("\n");
    try {
      model.playToPalette(paletteIndex, cardIndex);
      log.append("zard played to palette successfully!!\n");
    } catch (IllegalArgumentException e) {
      log.append("BAD ! invalid move: ").append(e.getMessage()).append("\n");
    }
  }

  private <C extends Card> void processCanvasMove(RedGameModel<C> model) {
    if (inputs.isEmpty()) {
      log.append("BAD ! invalid move (not enough inputs)\n");
      return;
    }
    int cardIndex = Integer.parseInt(inputs.poll()) - 1;
    log.append("enter card index: ").append(cardIndex + 1).append("\n");
    try {
      model.playToCanvas(cardIndex);
      log.append("Card played to canvas successfully\n");
    } catch (IllegalArgumentException e) {
      log.append("BAD ! invalid move: ").append(e.getMessage()).append("\n");
    }
  }

  public String getLog() {
    return log.toString();
  }
}