package cs3500.solored.model.hw04;

import cs3500.solored.model.hw02.Card;
import cs3500.solored.model.hw02.SoloRedGameModel;

/**
 * Holds a new version of our redGame, this one is more advanced than the previous basic version.
 * Player picks a card after playing to palette, can draw another card after playing to a palette
 * if they play a card to the canvas and the number on the card played to the canvas is strictly
 * greater than the number of cards in the now currently winning palette.
 */
public class AdvancedSoloRedGameModel extends SoloRedGameModel<Card> {
  private int cardsAutoDraw = 1;

  public AdvancedSoloRedGameModel() {
    super();
  }

  @Override
  public void playToPalette(int paletteIdx, int cardIdxInHand) {
    super.playToPalette(paletteIdx, cardIdxInHand);
    drawForHand();
  }

  @Override
  public void playToCanvas(int cardIdxInHand) {
    Card playedCard = getHand().get(cardIdxInHand);
    super.playToCanvas(cardIdxInHand);

    int winningPaletteSize = getPalette(winningPaletteIndex()).size();
    if (getNum(playedCard) > winningPaletteSize) {
      cardsAutoDraw = 2;
    }
  }

  @Override
  public void drawForHand() {
    for (int i = 0; i < cardsAutoDraw; i++) {
      if (getHand().size() < maxHandSize && numOfCardsInDeck() > 0) {
        super.drawForHand();
      }
    }
    cardsAutoDraw = 1;
  }

  public boolean isGameStarted() {
    return gameStarted;
  }

  public int getMaxHandSize() {
    return maxHandSize;
  }

  public int getDeckSize() {
    return getDeck().size();
  }
}