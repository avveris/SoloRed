package cs3500.solored.model.hw02;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Holds the information for the game, how it should run functionality wise.
 * This includes the setup of the actual game, checking when it's running,
 */
public class SoloRedGameModel<C> implements RedGameModel<Card> {
  private Stack<Card> canvas;
  protected List<List<Card>> palettes;
  protected List<Card> hand;
  private Deque<Card> deck;
  private Random random;
  protected boolean gameStarted;
  protected int maxHandSize;
  private Rules rules;
  private List<Card> allCards;

  // creates a game without random, with default settings
  public SoloRedGameModel() {
    this(new Random(), new GameRules());
  }

  /**
   * Constructs a SoloRedGameModel with the specified random number generator and game rules.
   * random used for generating random numbers , rules for the game rules and
   * IllegalArgumentException if random || rules is null
   */
  public SoloRedGameModel(Random random, GameRules rules) {
    if (random == null) {
      throw new IllegalArgumentException("OOPS ! Random object cannot be null.");
    }
    if (rules == null) {
      throw new IllegalArgumentException("OOPS ! Game rules cannot be null.");
    }

    this.random = random;
    this.rules = rules;
    this.gameStarted = false;

    this.allCards = new ArrayList<>();
    for (Colors color : Colors.values()) {
      for (int number = 1; number <= 7; number++) {
        this.allCards.add(new CardImpl(color, number));
      }
    }
  }

  public SoloRedGameModel(Random random) {
    this(random, new GameRules());
  }

  // returns a list of the cards in play
  @Override
  public List<Card> getAllCards() {
    return new ArrayList<>(allCards);
  }



  // starts a game with the legal parameters
  @Override
  public void startGame(List<Card> deck, boolean shuffle, int numPalettes, int handSize) {
    if (gameStarted) {
      throw new IllegalStateException("Game already started.");
    }
    if (deck == null || deck.isEmpty() || numPalettes < 2 || handSize <= 0) {
      throw new IllegalArgumentException("Invalid game parameters.");
    }

    this.deck = new ArrayDeque<>(deck);
    if (shuffle) {
      List<Card> tempDeck = new ArrayList<>(this.deck);
      Collections.shuffle(tempDeck, random);
      this.deck = new ArrayDeque<>(tempDeck);
    }

    this.maxHandSize = handSize;
    this.palettes = new ArrayList<>(numPalettes);
    for (int i = 0; i < numPalettes; i++) {
      this.palettes.add(new ArrayList<>());
      if (!this.deck.isEmpty()) {
        this.palettes.get(i).add(this.deck.removeFirst());
      }
    }

    this.canvas = new Stack<>();
    this.canvas.push(rules.topCard());
    this.hand = new ArrayList<>();
    this.gameStarted = true;
    drawForHand();
  }




  // plays a card from hand to a palette
  @Override
  public void playToPalette(int paletteIndex, int cardIndex) {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    if (paletteIndex < 0 || paletteIndex >= palettes.size()) {
      throw new IllegalArgumentException("Invalid palette index.");
    }
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      throw new IllegalArgumentException("Invalid card index.");
    }

    Card playedCard = hand.remove(cardIndex);
    palettes.get(paletteIndex).add(playedCard);

    drawForHand();

    if (hand.isEmpty() && deck.isEmpty()) {
      gameStarted = false;
    }
  }

  // plays a card from hand to canvas
  @Override
  public void playToCanvas(int cardIndex) {
    if (!gameStarted) {
      throw new IllegalStateException("OOPS ! Game has not started yet.");
    }
    if (cardIndex < 0 || cardIndex >= hand.size()) {
      throw new IllegalArgumentException("OOPS ! Invalid card index.");
    }

    canvas.push(hand.remove(cardIndex));
    drawForHand();
  }

  // draws cards from the deck up to the maximum hand size.
  @Override
  public void drawForHand() {
    if (!gameStarted) {
      return;
    }
    while (hand.size() < maxHandSize && !deck.isEmpty()) {
      hand.add(deck.removeFirst());
    }
  }

  // returns the card on the top without removing it
  @Override
  public Card getCanvas() {
    return canvas.peek();
  }

  // gets a copy of a palette of a game model
  @Override
  public List<Card> getPalette(int index) {
    if (index < 0 || index >= palettes.size()) {
      throw new IllegalArgumentException("OOPS ! Invalid palette index.");
    }
    return new ArrayList<>(palettes.get(index));
  }

  // checks which palette is winning, and if not returns -1
  @Override
  public int winningPaletteIndex() {
    for (int i = 0; i < palettes.size(); i++) {
      if (rules.isWinningPalette(palettes, i, canvas.peek())) {
        return i;
      }
    }
    return -1;
  }

  // returns a copy of a hand built from an arraylist
  @Override
  public List<Card> getHand() {
    return new ArrayList<>(hand);
  }

  // tells you the number of palettes in the game
  @Override
  public int numPalettes() {
    return palettes.size();
  }

  // tells you the number of cards in the deck
  @Override
  public int numOfCardsInDeck() {
    if (!gameStarted) {
      throw new IllegalStateException("Game has not started yet.");
    }
    return deck.size();
  }

  // is game over
  @Override
  public boolean isGameOver() {
    return !gameStarted || (hand.isEmpty() && deck.isEmpty());
  }

  // is game won
  @Override
  public boolean isGameWon() {
    return isGameOver() && !hand.isEmpty();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Canvas: ").append(
            canvas.isEmpty() ? "" : canvas.peek().toString().substring(0, 1)).append("\n");
    for (int i = 0; i < palettes.size(); i++) {
      sb.append(i == winningPaletteIndex() ? "> " : "  ");
      sb.append("P").append(i + 1).append(": ");
      sb.append(String.join(" ",
              palettes.get(i).stream().map(Card::toString).collect(Collectors.toList())));
      sb.append("\n");
    }
    sb.append("Hand: ");
    sb.append(String.join(" ",
            hand.stream().map(Card::toString).collect(Collectors.toList())));
    return sb.toString();
  }

  public List<C> getDeck() {
    return (List<C>) new ArrayList<>(deck);
  }

  //getter for num
  public int getNum(C card) {
    return Integer.parseInt(card.toString().substring(1));
  }
}