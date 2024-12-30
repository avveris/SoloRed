package cs3500.solored.model.hw02;

/**
 * implements the card interface and the card methods
 * written in the CardPub intertface.  Making the representation
 * of the cards with a color (ROBIV) +(1234567)
 */
public class CardImpl implements CardPub {
  private final Colors color;
  private final int number;

  public CardImpl(Colors color, int number) {
    this.color = color;
    this.number = number;
  }

  // converts the colors and numbers of cards to strings
  @Override
  public String toString() {
    String colorShort;
    if (color == Colors.RED) {
      colorShort = "R";
    } else if (color == Colors.ORANGE) {
      colorShort = "O";
    } else if (color == Colors.BLUE) {
      colorShort = "B";
    } else if (color == Colors.INDIGO) {
      colorShort = "I";
    } else if (color == Colors.VIOLET) {
      colorShort = "V";
    } else {
      throw new IllegalStateException("OOPS ! Not a color.");
    }
    return colorShort + number;
  }

  // Getter for the number of a card
  public int getNum() {
    return number;
  }

  // Getter for the color of a card
  public Colors getCol() {
    return color;
  }
}
