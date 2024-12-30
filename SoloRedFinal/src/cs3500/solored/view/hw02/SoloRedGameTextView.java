package cs3500.solored.view.hw02;

import java.io.IOException;
import java.util.List;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.RedGameModel;

/**
 * Holds the view of the game, with the functions in the RedGameModel
 * and implementing the interface RedGameView.
 */
public class SoloRedGameTextView implements RedGameView {
  private final RedGameModel<? extends Card> model;
  private final Appendable out;

  /**
   * Constructs a new SoloRedGameTextView with the specified game model and default StringBuilder.
   *
   * @param model The game model to be represented by this view.
   * @throws IllegalArgumentException if the provided model is null.
   */
  public SoloRedGameTextView(RedGameModel<? extends Card> model) {
    this(model, new StringBuilder());
  }

  /**
   * Constructs a new SoloRedGameTextView with the specified game model and Appendable object.
   *
   * @param model The game model to be represented by this view.
   * @param out The Appendable object for the view's output.
   * @throws IllegalArgumentException if either model or out is null.
   */
  public SoloRedGameTextView(RedGameModel<? extends Card> model, Appendable out) {
    if (model == null || out == null) {
      throw new IllegalArgumentException("Model and Appendable cannot be null");
    }
    this.model = model;
    this.out = out;
  }

  @Override
  public String toString() {
    StringBuilder view = new StringBuilder();
    try {
      appendGameState(view);
    } catch (IOException e) {
      throw new RuntimeException("BAD !!! IOException", e);
    }
    return view.toString().trim();
  }

  @Override
  public void render() throws IOException {
    appendGameState(out);
  }

  private void appendGameState(Appendable appendable) throws IOException {
    appendCanvas(appendable);
    appendPalettes(appendable);
    appendHand(appendable);
  }

  private void appendCanvas(Appendable appendable) throws IOException {
    appendable.append("Canvas: ").append(model.getCanvas().toString()).append("\n");
  }

  private void appendPalettes(Appendable appendable) throws IOException {
    int winningIndex = model.winningPaletteIndex();
    for (int i = 0; i < model.numPalettes(); i++) {
      appendable.append(i == winningIndex ? "> " : "  ");
      appendable.append("P").append(Integer.toString(i + 1)).append(": ");
      appendCards(appendable, model.getPalette(i));
      appendable.append("\n");
    }
  }

  private void appendHand(Appendable appendable) throws IOException {
    appendable.append("Hand: ");
    appendCards(appendable, model.getHand());
  }

  private void appendCards(Appendable appendable, List<? extends Card> cards) throws IOException {
    for (Card card : cards) {
      appendable.append(card.toString()).append(" ");
    }
  }
}